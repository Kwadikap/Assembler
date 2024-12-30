/*
* enum class to represent command type
* A_COMMAND for @xxx whwere xxx is either a symbol or a decimal number
* C_COMMAND for 'dest=comp;jump'
* L_COMMAND for (xxx) where xxx is a symbol
*/   
public enum CommandType {
    A_COMMAND,
    C_COMMAND,
    L_COMMAND,
    INVALID_COMMAND,
}