if [[ -z "${_LIB_PATH}" ]]; then
    if ! SCRIPT_DIR="$( (
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
    ))"; then
        return 1
    fi

    if [[ -z "${_LIB_PATH}" ]]; then
        _LIB_PATH="$(readlink -e -- "${SCRIPT_DIR}")"
    fi
fi

if [[ -n "${_LIB_OS_GUARD+x}" ]]; then
    return 0
fi
declare _LIB_OS_GUARD

# shellcheck source=./logging.sh
source "${_LIB_PATH}/logging.sh"

# Get the Linux distro the script is currently being run on.
function lib::os::get_distro() {
    cat /etc/os-release | grep '^ID' | awk -F'=' '{print $2;}' 2> /dev/null
    return $?
}

function lib::os::install_system_package() {
    local -a PACMAN_FLAGS
    local PACMAN PACKAGE_NAME DISTRO STATUS_CODE
    DISTRO="$(lib::os::get_distro)"
    PACKAGE_NAME="$1"
    shift
    if [[ -z "${PACKAGE_NAME}" ]]; then
        lib::log::error "install_system_package: A package name is required!"
    fi
    case "${DISTRO}" in
        arch)
            PACMAN="pacman"
            PACMAN_FLAGS=(-S --noconfirm --needed --asexplicit)
            ;;
        debian | ubuntu)
            PACMAN="apt"
            PACMAN_FLAGS=(install --assume-yes --no-install-recommends)
            ;;
        *)
            lib::logging::error "install_system_package: Unsupported distro \"${DISTRO}\"!"
            ;;
    esac
    read -ra PACMAN_FLAGS > /dev/null 2>&1 < <(echo "${PACMAN_FLAGS[*]} $*")
    lib::log::info "We're going to attempt to install \"${PACKAGE_NAME}\", this will require admin permissions!"
    if [[ ! -x "${PACMAN}" ]]; then
        lib::log::warn "Unable to execute \"${PACMAN}\" as we are, trying to elevate..."
        if command -v sudo >&/dev/null; then
            if ! lib::io::prompt_to_continue "We're about to run 'sudo \"${SHELL}\" -i -c \"${PACMAN} ${PACMAN_FLAGS[*]} ${PACKAGE_NAME}\"'." "n"; then
                lib::log::error "Not authorized, aborting install!"
                STATUS_CODE=1
            else
                lib::log::verbose "Trying to elevate via \"sudo\"..."
                sudo --login eval "${PACMAN} ${PACMAN_FLAGS[*]} ${PACKAGE_NAME}"
                STATUS_CODE=$?
            fi
        elif command -v su >&/dev/null; then
            if ! lib::io::prompt_to_continue "We're about to run 'su --login --command=\n\"${PACMAN} ${PACMAN_FLAGS[*]} ${PACKAGE_NAME}\"'." "n"; then
                lib::log::error "Not authorized, aborting install!"
                STATUS_CODE=1
            else
                lib::log::verbose "Trying to elevate via \"su\"..."
                su --login --command="${PACMAN} ${PACMAN_FLAGS[*]} ${PACKAGE_NAME}"
                STATUS_CODE=$?
            fi

        else
            lib::log::error "Failed to elevate: unable to find compatible suid program!"
            STATUS_CODE=1
        fi
    else
        "${PACMAN}" "${PACMAN_FLAGS[*]}" "${PACKAGE_NAME}"
        STATUS_CODE=$?
    fi
    if [[ "${STATUS_CODE}" != "0" ]]; then
        lib::log::verbose "\"${PACMAN}\" exited with code \"${STATUS_CODE}\"!"
    fi
    return ${STATUS_CODE}
}
