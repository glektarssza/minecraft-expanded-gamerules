#!/usr/bin/env bash
set +x +e

declare -A EXIT_CODES=(
    [SUCCESS]=0
    [RUNNING_IN_CI_ENVIRONMENT]=0
    [HOOKS_ALREADY_INSTALLED]=0
    [ENTER_PROJECT_ROOT_FAILED]=1
    [UNSUPPORTED_SHELL]=2
)

declare -A EXIT_MESSAGES=(
    [SUCCESS]="Successfully set up Git hooks!"
    [RUNNING_IN_CI_ENVIRONMENT]="Running in a CI environment, not setting up Git hooks!"
    [HOOKS_ALREADY_INSTALLED]="Git hooks already installed, skipping!"
    [ENTER_PROJECT_ROOT_FAILED]="Failed to enter project root directory!"
    [GIT_CONFIG_TMP_FAILED]="Failed to create working copy of Git config!"
    [GIT_CONFIG_UPDATE_FAILED]="Failed to modify temporary Git configuration with new hook path!"
    [GIT_CONFIG_OVERWRITE_FAILED]="Failed to overwrite Git configuration with modified version!"
    [UNSUPPORTED_SHELL]="Unsupported shell! Please use bash, ksh93, or zsh."
)

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
        local SCRIPT_PATH
        if [[ -n "${BASH}" ]]; then
            # shellcheck disable=SC2128
            SCRIPT_PATH="${BASH_SOURCE}"
        elif [[ -n "${ZSH_VERSION}" ]]; then
            # shellcheck disable=SC2296
            SCRIPT_PATH="${(%):-%x}"
        elif [[ -n "${TMOUT}" ]]; then
            # shellcheck disable=SC2296
            SCRIPT_PATH="${.sh.file}"
        elif [[ "${0##*/}" == "dash" ]]; then
            local x
            x="$(lsof -p $$ -Fn0 | tail -1)"
            # shellcheck disable=SC2296
            SCRIPT_PATH="${x#n}"
        else
            printf '\e[38;5;196m[ERROR]\e[0m %s' "${EXIT_MESSAGES[UNSUPPORTED_SHELL]}" 1>&2
            # shellcheck disable=SC2086
            return ${EXIT_CODES[UNSUPPORTED_SHELL]}
        fi
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

# -- The path to the project root directory
PROJECT_ROOT="$(readlink -e -- "${SCRIPT_DIR}/../")"

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

lib::logging::info "Starting Git hook installation..."

"${SCRIPT_DIR}/install-pre-commit.sh" "$*"

lib::logging::verbose "\"pre-commit\" ready, changing to project root directory..."

if ! pushd "${PROJECT_ROOT}" > /dev/null 2>&1; then
    lib::logging::error "${EXIT_MESSAGES[ENTER_PROJECT_ROOT_FAILED]}"
    # shellcheck disable=SC2086
    exit ${EXIT_CODES[ENTER_PROJECT_ROOT_FAILED]}
fi
lib::logging::info "Checking if Git hooks need to be installed..."
if [[ -n "$(sed -nE 'N;/\[include\]\n\s*path\s?=\s?("?)\.\.\/\.gitconfig\1/p' ".git/config" 2> /dev/null)" ]]; then
    lib::logging::info "${EXIT_MESSAGES[HOOKS_ALREADY_INSTALLED]}"
    # shellcheck disable=SC2086
    exit ${EXIT_CODES[HOOKS_ALREADY_INSTALLED]}
fi
lib::logging::info "Installing Git hooks..."
lib::logging::verbose "Creating working copy of Git config..."
if lib::logging::is_verbose_enabled; then
    lib::logging::verbose "Git config should be at \"${GIT_CONFIG}\"..."
    cp "${PROJECT_ROOT}/.git/config" "${PROJECT_ROOT}/.git/config.tmp"
else
    cp "${PROJECT_ROOT}/.git/config" "${PROJECT_ROOT}/.git/config.tmp" > /dev/null 2>&1
fi
STATUS_CODE=$?
lib::logging::verbose "\"cp\" exited with code \"${STATUS_CODE}\""
if [[ "${STATUS_CODE}" != "0" ]]; then
    lib::logging::error "${EXIT_MESSAGES[GIT_CONFIG_TMP_FAILED]}"
    exit $STATUS_CODE
fi
lib::logging::verbose "Adding custom hooks directory to Git config via \"sed\"..."
if lib::logging::is_verbose_enabled; then
    sed -i '1i [include]\n    path = ../.gitconfig' "${PROJECT_ROOT}/.git/config.tmp"
else
    sed -i '1i [include]\n    path = ../.gitconfig' "${PROJECT_ROOT}/.git/config.tmp" > /dev/null 2>&1
fi
STATUS_CODE=$?
lib::logging::verbose "\"sed\" exited with code \"${STATUS_CODE}\""
if [[ "${STATUS_CODE}" != "0" ]]; then
    lib::logging::error "${EXIT_MESSAGES[GIT_CONFIG_UPDATE_FAILED]}"
    exit $STATUS_CODE
fi
lib::logging::verbose "Overwriting original Git config with updated working copy..."
if lib::logging::is_verbose_enabled; then
    mv -f "${PROJECT_ROOT}/.git/config.tmp" "${PROJECT_ROOT}/.git/config"
else
    mv -f "${PROJECT_ROOT}/.git/config.tmp" "${PROJECT_ROOT}/.git/config" > /dev/null 2>&1
fi
STATUS_CODE=$?
lib::logging::verbose "\"mv\" exited with code \"${STATUS_CODE}\""
if [[ "${STATUS_CODE}" != "0" ]]; then
    lib::logging::error "${EXIT_MESSAGES[GIT_CONFIG_OVERWRITE_FAILED]}"
    exit $STATUS_CODE
fi
if ! popd > /dev/null 2>&1; then
    lib::logging::warn "Failed to exit project root directory!"
fi
lib::logging::info "${EXIT_MESSAGES[SUCCESS]}"
# shellcheck disable=SC2086
exit ${EXIT_CODES[SUCCESS]}
