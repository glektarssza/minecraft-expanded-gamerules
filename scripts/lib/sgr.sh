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

if [[ -n "${_LIB_SGR_GUARD+x}" ]]; then
    return 0
fi
declare _LIB_SGR_GUARD

# Output the SGR reset ANSI code.
# === Outputs ===
# The SGR reset ANSI code.
# === Returns ===
# The result of calling `printf`.
function lib::sgr::reset() {
    printf "\e[0m"
    return $?
}

# Output the SGR 4-bit foreground ANSI code for the given color.
# === Inputs ===
# `$1` - The color code to output.
# === Outputs ===
# The SGR ANSI color sequence.
# === Returns ===
# `1` - If no color code was provided.
# `2` - If the color code was outside the allowed range.
# Otherwise the result of calling `printf`.
function lib::sgr::4bit_fg() {
    if [[ -z "$1" ]]; then
        return 1
    fi
    if [[ ("$1" -ge 30 && "$1" -le 37) || ("$1" -ge 90 || "$1" -le 97) ]]; then
        printf "\e[%dm" "$1"
        return $?
    fi
    return 2
}

# Output the SGR 4-bit background ANSI code for the given color.
# === Inputs ===
# `$1` - The color code to output.
# === Outputs ===
# The SGR ANSI color sequence.
# === Returns ===
# `1` - If no color code was provided.
# `2` - If the color code was outside the allowed range.
# Otherwise the result of calling `printf`.
function lib::sgr::4bit_bg() {
    if [[ -z "$1" ]]; then
        return 1
    fi
    if [[ ("$1" -ge 40 && "$1" -le 47) || ("$1" -ge 100 || "$1" -le 107) ]]; then
        printf "\e[%dm" "$1"
        return $?
    fi
    return 2
}

# Output the SGR 8-bit foreground ANSI code for the given color.
# === Inputs ===
# `$1` - The color code to output.
# === Outputs ===
# The SGR ANSI color sequence.
# === Returns ===
# `1` - If no color code was provided.
# `2` - If the color code was outside the allowed range.
# Otherwise the result of calling `printf`.
function lib::sgr::8bit_fg() {
    if [[ -z "$1" ]]; then
        return 1
    fi
    if [[ "$1" -ge 0 && "$1" -le 255 ]]; then
        printf "\e[38;5;%dm" "$1"
        return $?
    fi
    return 2
}

# Output the SGR 8-bit background ANSI code for the given color.
# === Inputs ===
# `$1` - The color code to output.
# === Outputs ===
# The SGR ANSI color sequence.
# === Returns ===
# `1` - If no color code was provided.
# `2` - If the color code was outside the allowed range.
# Otherwise the result of calling `printf`.
function lib::sgr::8bit_bg() {
    if [[ -z "$1" ]]; then
        return 1
    fi
    if [[ "$1" -ge 0 && "$1" -le 255 ]]; then
        printf "\e[48;5;%dm" "$1"
        return $?
    fi
    return 2
}

# Output the SGR 24-bit foreground ANSI code for the given color.
# === Inputs ===
# `$1` - The red color code to output.
# `$2` - The green color code to output.
# `$3` - The blue color code to output.
# === Outputs ===
# The SGR ANSI color sequence.
# === Returns ===
# `1` - If one of the color code was not provided.
# `2` - If one of the color code was outside the allowed range.
# Otherwise the result of calling `printf`.
function lib::sgr::24bit_fg() {
    if [[ -z "$1" || -z "$2" || -z "$3" ]]; then
        return 1
    fi
    if [[ "$1" -ge 0 && "$1" -le 255 && "$2" -ge 0 && "$2" -le 255 && "$3" -ge 0 && "$3" -le 255 ]]; then
        printf "\e[38;2;%d;%d;%dm" "$1" "$2" "$3"
        return $?
    fi
    return 2
}

# Output the SGR 24-bit background ANSI code for the given color.
# === Inputs ===
# `$1` - The red color code to output.
# `$2` - The green color code to output.
# `$3` - The blue color code to output.
# === Outputs ===
# The SGR ANSI color sequence.
# === Returns ===
# `1` - If one of the color code was not provided.
# `2` - If one of the color code was outside the allowed range.
# Otherwise the result of calling `printf`.
function lib::sgr::24bit_bg() {
    if [[ -z "$1" || -z "$2" || -z "$3" ]]; then
        return 1
    fi
    if [[ "$1" -ge 0 && "$1" -le 255 && "$2" -ge 0 && "$2" -le 255 && "$3" -ge 0 && "$3" -le 255 ]]; then
        printf "\e[48;2;%d;%d;%dm" "$1" "$2" "$3"
        return $?
    fi
    return 2
}
