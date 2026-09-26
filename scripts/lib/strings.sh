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

if [[ -n "${_LIB_STRINGS_GUARD+x}" ]]; then
    return 0
fi
declare _LIB_STRINGS_GUARD

# Convert a string to all lower case.
# === Inputs ===
# `$1` - The string to convert.
# === Outputs ===
# The converted string.
# === Returns ===
# `0` - The operation succeeded.
# `*` - The operation failed.
function lib::strings::to_lower_case() {
    if ! echo "$1" | tr '[:upper:]' '[:lower:]'; then
        return 1
    fi
    return 0
}

# Convert a string to all upper case.
# === Inputs ===
# `$1` - The string to convert.
# === Outputs ===
# The converted string.
# === Returns ===
# `0` - The operation succeeded.
# `*` - The operation failed.
function lib::strings::to_upper_case() {
    if ! echo "$1" | tr '[:lower:]' '[:upper:]'; then
        return 1
    fi
    return 0
}
