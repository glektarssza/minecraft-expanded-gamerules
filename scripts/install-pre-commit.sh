#!/usr/bin/env bash
set +x +e

SCRIPT_DIR="$( (
    # Get the directory the script is running from.
    # === Outputs ===
    # The path to the directory the script is running from.
    # === Returns ===
    # `0` - the function succeeded.
    # `1` - a `cd` call failed.
    # `2` - a `popd` call failed.
    function get_script_dir() {
        pushd . 2>&1 > /dev/null || return 1
        local SCRIPT_PATH="${BASH_SOURCE[0]:-$0}"
        while [[ -L "${SCRIPT_PATH}" ]]; do
            cd "$(dirname -- "${SCRIPT_PATH}")" || return 2
            SCRIPT_PATH="$(readlink -e -- "$SCRIPT_PATH")"
        done
        cd "$(dirname -- "$SCRIPT_PATH")" > /dev/null || return 2
        SCRIPT_PATH="$(pwd)"
        popd 2>&1 > /dev/null || return 3
        echo "${SCRIPT_PATH}"
        return 0
    }
    get_script_dir
))"

_LIB_PATH="$(readlink -e -- "${SCRIPT_DIR}/lib/")"

# shellcheck source=./lib/logging.sh
source "${_LIB_PATH}/logging.sh"
# shellcheck source=./lib/io.sh
source "${_LIB_PATH}/io.sh"
# shellcheck source=./lib/os.sh
source "${_LIB_PATH}/os.sh"

declare -A EXIT_CODES=(
    [SUCCESS]=0
    [RUNNING_IN_CI_ENVIRONMENT]=0
)

declare -A EXIT_MESSAGES=(
    [SUCCESS]="Successfully installed \"pre-commit\"!"
    [RUNNING_IN_CI_ENVIRONMENT]="Running in a CI environment, not setting up Git hooks!"
)

# -- Determine our distribution
DISTRO="$(lib::os::get_distro)"

if [[ -n "${CI}" ]]; then
    if [[ $* =~ .*--force.* ]]; then
        lib::logging::warn "Running in a CI environment but requested bypass of CI flag skip!"
        lib::logging::warn "Hope you know what you're doing!"
    else
        lib::logging::warn "${EXIT_MESSAGES[RUNNING_IN_CI_ENVIRONMENT]}"
        # shellcheck disable=SC2086
        exit ${EXIT_CODES[RUNNING_IN_CI_ENVIRONMENT]}
    fi
fi

lib::logging::info "Starting \"pre-commit\" installation..."
lib::logging::verbose "Checking for \"pre-commit\"..."

# Locate pre-commit
PRE_COMMIT="$(command -v pre-commit 2> /dev/null)"
if [[ -z "${PRE_COMMIT}" ]]; then
    lib::logging::warn "\"pre-commit\" is not installed, attempting to install via \"pipx\"!"
    if ! which pipx > /dev/null 2>&1; then
        lib::logging::warn "\"pipx\" is not installed, attempting to install!"
        case "${DISTRO}" in
            arch)
                PIPX_PACKAGE_NAME="python-pipx"
                ;;
            debian | ubuntu)
                PIPX_PACKAGE_NAME="pipx"
                ;;
            *)
                lib::log::error "Unsupported distro \"${DISTRO}\"!"
                exit 1
                ;;
        esac
        lib::logging::info "We're going to attempt to install \"pipx\", this will require admin permissions!"
        if [[ ! -x "${PACMAN}" ]]; then
            lib::logging::warn "Unable to execute \"${PACMAN}\" as we are, trying to elevate..."
            if command -v sudo >&/dev/null; then
                if ! lib::io::prompt_to_continue "We're about to run 'sudo \"${SHELL}\" -i -c \"${PACMAN} ${PACMAN_FLAGS[*]} ${PACKAGE_NAME}\"'." "n"; then
                    lib::logging::error "Aborting!"
                    exit 1
                fi
                lib::logging::verbose "Trying to elevate via \"sudo\"..."
                sudo --login eval "${PACMAN} ${PACMAN_FLAGS[*]} ${PIPX_PACKAGE_NAME}"
                STATUS_CODE=$?
            elif command -v su >&/dev/null; then
                if ! lib::io::prompt_to_continue "We're about to run 'su --login --command=\n\"${PACMAN} ${PACMAN_FLAGS[*]} ${PIPX_PACKAGE_NAME}\"'." "n"; then
                    lib::logging::error "Aborting!"
                    exit 1
                fi
                lib::logging::verbose "Trying to elevate via \"su\"..."
                su --login --command="${PACMAN} ${PACMAN_FLAGS[*]} ${PIPX_PACKAGE_NAME}"
                STATUS_CODE=$?
            else
                lib::logging::error "Failed to elevate!"
                exit 1
            fi
            "${PACMAN}" "${PACMAN_FLAGS[*]}" pipx
            STATUS_CODE=$?
            lib::log::error "Failed to install \"${PIPX_PACKAGE_NAME}\"!"
            exit ${STATUS_CODE}
        else
            lib::log::info "\"${PIPX_PACKAGE_NAME}\" is already installed!"
        fi
        if [[ "${STATUS_CODE}" != "0" ]]; then
            lib::logging::verbose "\"${PACMAN}\" exited with code \"${STATUS_CODE}\"!"
            lib::logging::error "Failed to install \"pipx\"!"
            # shellcheck disable=SC2086
            exit ${STATUS_CODE}
        fi
        lib::logging::info "\"pipx\" was installed successfully!"
    fi
    pipx install pre-commit
    STATUS_CODE=$?
    if [[ "${STATUS_CODE}" != "0" ]]; then
        lib::logging::verbose "\"pipx\" exited with code \"${STATUS_CODE}\"!"
        lib::logging::error "Failed to install \"pre-commit\"!"
        exit $STATUS_CODE
    fi
    lib::logging::info "\"pre-commit\" was installed successfully!"
    PRE_COMMIT="$(which pre-commit 2> /dev/null)"
    if [[ -z "${PRE_COMMIT}" ]]; then
        lib::logging::warn "Still cannot find \"pre-commit\", trying some well-known locations..."
        mapfile -t PRE_COMMIT_PATHS < <(find ~ -maxdepth 4 \( -type f -or -type l \) -name pre-commit -printf '%p\n')
        if [[ "${#PRE_COMMIT_PATHS[@]}" -le 0 ]]; then
            lib::logging::error "Failed to locate \"pre-commit\"!"
            exit 1
        fi
        echo -e "Found the following \"pre-commit\" executables:\n$(echo "${PRE_COMMIT_PATHS[*]}" | awk -F' ' '{for(i=1;i<=NF;i+=1){print i": "$i;}}')"
        read -rep "Which one do you want to use? [index] " PRE_COMMIT_INDEX
        while [[ ! "${PRE_COMMIT_INDEX}" =~ [[:digit:]]+ || "${PRE_COMMIT_INDEX}" -lt 0 || "${PRE_COMMIT_INDEX}" -gt "${#PRE_COMMIT_PATHS[@]}" ]]; do
            if [[ -z "${PRE_COMMIT_INDEX}" ]]; then
                PRE_COMMIT_INDEX="1"
                break
            fi
            lib::logging::error "Value ${PRE_COMMIT_INDEX} is not valid, try again!"
            echo -e "Found the following \"pre-commit\" executables:\n$(echo "${PRE_COMMIT_PATHS[*]}" | awk -F' ' '{for(i=1;i<=NF;i+=1){print i": "$i;}}')"
            read -rep "Which one do you want to use? [index (default 1)] " PRE_COMMIT_INDEX
        done
        lib::logging::verbose "\"pre-commit\" option index \"${PRE_COMMIT_INDEX}\" picked"
        PRE_COMMIT="${PRE_COMMIT_PATHS[(${PRE_COMMIT_INDEX} - 1)]}"
        lib::logging::verbose "Selected \"pre-commit\" executable \"${PRE_COMMIT}\""
    fi
else
    lib::logging::verbose "\"pre-commit\" found at \"${PRE_COMMIT}\"!"
fi

lib::logging::info "${EXIT_MESSAGES[SUCCESS]}"
# shellcheck disable=SC2086
exit ${EXIT_CODES[SUCCESS]}
