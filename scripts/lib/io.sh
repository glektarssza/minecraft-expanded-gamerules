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

if [[ -n "${_LIB_IO_GUARD+x}" ]]; then
    return 0
fi
declare _LIB_IO_GUARD

# shellcheck source=./logging.sh
source "${_LIB_PATH}/logging.sh"
# shellcheck source=./strings.sh
source "${_LIB_PATH}/strings.sh"

# Prompt the user if it's okay to continue.
# === Inputs ===
# `$1` - The prompt to display.
# `$2` - The default response. Defaults to `y`.
# === Returns ===
# `0` - Okay to continue.
# `1` - Not okay to continue.
# `2` - Some other error.
function lib::io::prompt_to_continue() {
    local PROMPT="$1"
    local DEFAULT_RESP="${2:-y}"
    local RESP=""
    if [[ -z "${PROMPT}" ]]; then
        lib::logging::error "No prompt provided to 'prompt_to_continue'!"
        return 2
    fi
    if [[ "$(lib::strings::to_lower_case "${DEFAULT_RESP}")" == "y" ]]; then
        PROMPT="${PROMPT}\nIs this okay? [Y/n] "
    else
        PROMPT="${PROMPT}\nIs this okay? [y/N] "
    fi
    while true; do
        printf "%b" "${PROMPT}"
        read -r RESP
        case "$(lib::strings::to_lower_case "${RESP:-${DEFAULT_RESP}}")" in
            y) return 0 ;;
            n) return 1 ;;
            *)
                RESP=""
                lib::logging::error "Invalid response \"${RESP}\"! Please try again!"
                ;;
        esac
    done
}
