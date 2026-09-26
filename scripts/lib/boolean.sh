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

if [[ -n "${_LIB_BOOLEAN_GUARD+x}" ]]; then
    return 0
fi
declare _LIB_BOOLEAN_GUARD

# shellcheck source=./strings.sh
source "${_LIB_PATH}/strings.sh"

# The numerical value considered to be "true".
export TRUE=0

# The numerical value considered to be "false".
export FALSE=1

# Get whether the input value is truthy ("1" or the string "true", lower or upper
# case.)
function lib::boolean::is_truthy() {
    if [[ "$(lib::strings::to_lower_case "${1,,}")" =~ 1|true ]]; then
        # shellcheck disable=SC2086
        return ${TRUE}
    fi
    # shellcheck disable=SC2086
    return ${FALSE}
}

# Get whether the input value is truthy (any numeric value other than "1" or any
# string other than the string "true", lower or uppercase.)
function lib::boolean::is_falsy() {
    if ! lib::boolean::is_truthy "$1"; then
        # shellcheck disable=SC2086
        return ${TRUE}
    fi
    # shellcheck disable=SC2086
    return ${FALSE}
}
