import java.util.HashMap;
import java.util.Map;

/**
 * The SymbolTable class represents a table that holds symbols (variable names)
 * and their corresponding memory addresses.
 * This class is typically used in assembly language parsing, where it keeps
 * track of symbols and their memory locations
 * during the compilation or translation process.
 * 
 * <p>
 * It supports adding new entries, checking if a symbol exists, retrieving the
 * address of a symbol, and getting the next
 * available address to be used for a new symbol. It also initializes a set of
 * predefined symbols with fixed addresses.
 * 
 * <p>
 * The predefined symbols include general-purpose registers (R0 to R15),
 * special-purpose symbols like "SP", "LCL", "ARG",
 * "THIS", and "THAT", and system symbols "SCREEN" and "KBD".
 * 
 * <p>
 * The available address starts from 16 and increases as new symbols are added
 * to the table with addresses at or beyond the
 * current available address.
 * 
 * <p>
 * Example usage:
 * 
 * <pre>
 * SymbolTable symbolTable = new SymbolTable();
 * symbolTable.addEntry("MY_VAR", 16);
 * if (symbolTable.contains("MY_VAR")) {
 *     int address = symbolTable.getAddress("MY_VAR");
 * }
 * </pre>
 */
public class SymbolTable {

    /**
     * A map that stores the symbol names as keys and their corresponding memory
     * addresses as values.
     */
    public Map<String, Integer> table;

    /**
     * The next available address to be used for a new symbol. The initial value is
     * set to 16.
     */
    private int availableAddress = 16;

    /**
     * Constructs a new SymbolTable and initializes the table with predefined
     * symbols.
     */
    public SymbolTable() {
        table = new HashMap<String, Integer>();
        initializeTable();
    }

    /**
     * Adds a new entry to the symbol table.
     * 
     * @param symbol  The symbol (variable name) to be added.
     * @param address The address to be associated with the symbol.
     * 
     *                <p>
     *                If the provided address is the current available address, the
     *                available address is incremented.
     */
    public void addEntry(String symbol, int address) {
        table.put(symbol, address);
        // increment available address if it has been occupied
        if (address == availableAddress)
            availableAddress++;
    }

    /**
     * Checks whether the symbol table contains a given symbol.
     * 
     * @param symbol The symbol to check.
     * @return true if the symbol is in the table, false otherwise.
     */
    public boolean contains(String symbol) {
        return table.containsKey(symbol);
    }

    /**
     * Retrieves the address of a symbol.
     * 
     * @param symbol The symbol whose address is to be retrieved.
     * @return The address associated with the symbol.
     * @throws NullPointerException If the symbol is not present in the table.
     */
    public int getAddress(String symbol) {
        return table.get(symbol);
    }

    /**
     * Retrieves the next available address for a new symbol.
     * 
     * @return The next available address.
     */
    public int getNextAvailableAddress() {
        return availableAddress;
    }

    /**
     * Initializes the symbol table with a set of predefined symbols and their
     * addresses.
     * This method is called when the SymbolTable is instantiated.
     * <p>
     * The predefined symbols include:
     * <ul>
     * <li>SP - 0</li>
     * <li>LCL - 1</li>
     * <li>ARG - 2</li>
     * <li>THIS - 3</li>
     * <li>THAT - 4</li>
     * <li>R0 to R15 - 0 to 15</li>
     * <li>SCREEN - 16384</li>
     * <li>KBD - 24576</li>
     * </ul>
     */
    private void initializeTable() {
        table.put("SP", 0);
        table.put("LCL", 1);
        table.put("ARG", 2);
        table.put("THIS", 3);
        table.put("THAT", 4);
        table.put("R0", 0);
        table.put("R1", 1);
        table.put("R2", 2);
        table.put("R3", 3);
        table.put("R4", 4);
        table.put("R5", 5);
        table.put("R6", 6);
        table.put("R7", 7);
        table.put("R8", 8);
        table.put("R9", 9);
        table.put("R10", 10);
        table.put("R11", 11);
        table.put("R12", 12);
        table.put("R13", 13);
        table.put("R14", 14);
        table.put("R15", 15);
        table.put("SCREEN", 16384);
        table.put("KBD", 24576);
    }
}