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

if [[ -n "${_LIB_LOGGING_GUARD+x}" ]]; then
    return 0
fi
declare _LIB_LOGGING_GUARD

# shellcheck source=./sgr.sh
source "${_LIB_PATH}/sgr.sh"
# shellcheck source=./strings.sh
source "${_LIB_PATH}/strings.sh"
# shellcheck source=./boolean.sh
source "${_LIB_PATH}/boolean.sh"

# Get whether verbose logging is enabled.
function lib::logging::is_verbose_enabled() {
    if [[ -n "${VERBOSE}" ]]; then
        # shellcheck disable=SC2086
        return ${FALSE}
    fi
    # -- eval to ensure we actually pick up post-loaded functions
    eval lib::boolean::is_truthy "${VERBOSE}"
    return $?
}

# Log an error message to the standard error stream.
function lib::logging::error() {
    lib::sgr::8bit_fg "196" >&2 && printf "[ERROR]" >&2 && lib::sgr::reset >&2 && printf " %s\n" "$*" >&2
    return $?
}

# Log a warning message to the standard output stream.
function lib::logging::warn() {
    lib::sgr::8bit_fg "214" && printf "[WARN]" && lib::sgr::reset && printf " %s\n" "$*"
    return $?
}

# Log an information message to the standard output stream.
function lib::logging::info() {
    lib::sgr::8bit_fg "111" && printf "[INFO]" && lib::sgr::reset && printf " %s\n" "$*"
    return $?
}

# Log a verbose message to the standard output stream.
function lib::logging::verbose() {
    if ! lib::logging::is_verbose_enabled; then
        return 0
    fi
    lib::sgr::8bit_fg "171" && printf "[VERBOSE]" && lib::sgr::reset && printf " %s\n" "$*"
    return $?
}
