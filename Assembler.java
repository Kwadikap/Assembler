import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The Assembler class reads Hack assembly language files (.asm) and generates
 * corresponding machine code files (.hack)
 * that can be executed by the Hack CPU.
 * 
 * <p>
 * This class performs the following key tasks:
 * <ul>
 * <li>Reads the input .asm file line by line.</li>
 * <li>Parses each instruction and translates it into binary machine code.</li>
 * <li>Handles labels and symbols by maintaining a symbol table for variable and
 * jump addresses.</li>
 * <li>Generates an output .hack file with the translated machine
 * instructions.</li>
 * </ul>
 * 
 * <p>
 * Usage: The assembler expects the input file to be specified as a command-line
 * argument during execution:
 * 
 * <pre>
 * java Assembler MyProgram.asm
 * </pre>
 * 
 * This will produce an output file named <i>MyProgram.hack</i> in the same
 * directory.
 * 
 * <p>
 * Example: If <i>MyProgram.asm</i> contains:
 * 
 * <pre>
 *   &#64;2
 *   D=A
 *   &#64;3
 *   D=D+A
 *   0;JMP
 * </pre>
 * 
 * The output <i>MyProgram.hack</i> will contain:
 * 
 * <pre>
 *   0000000000000010
 *   1110110000010000
 *   0000000000000011
 *   1110000010010000
 *   1110101010000111
 * </pre>
 * 
 * <p>
 * Error Handling:
 * <ul>
 * <li>Invalid instructions or syntax errors will result in an error message and
 * termination.</li>
 * <li>Missing input files or incorrect file extensions will be flagged
 * appropriately.</li>
 * </ul>
 * 
 * <p>
 * Assumptions:
 * <ul>
 * <li>The input file follows the Hack assembly language specification.</li>
 * <li>Labels and variables are defined according to standard Hack
 * conventions.</li>
 * </ul>
 * 
 * <p>
 * Dependencies:
 * <ul>
 * <li>This class may depend on additional helper classes for parsing, symbol
 * table management, and file handling
 * (e.g., Parser, Code, SymbolTable).</li>
 * </ul>
 * 
 * @author Kwadwo Adusei Poku
 * @date 2024-12-30
 * @version 1.0
 */
public class Assembler {

    /**
     * The Parser instance used to read and parse the Hack assembly code.
     */
    static Parser parser;

    /**
     * The FileWriter used to write the generated machine code to an output file.
     */
    static FileWriter writer;

    /**
     * The SymbolTable instance that stores symbol mappings to memory addresses.
     */
    static SymbolTable symbolTable;

    /**
     * The main method of the Assembler class. It processes the input assembly file
     * and generates the corresponding
     * machine code file.
     * 
     * @param args Command-line arguments that specify the input assembly file.
     */
    public static void main(String[] args) {
        // Check if arguments were passed
        if (args.length < 1) {
            System.out.println("Usage: java MyProgram <filename>");
            return;
        }

        // Extract the file name
        String inputFile = args[0];

        // Initialize the symbol table
        symbolTable = initializeSymbolTable(inputFile);

        // Set the output file name
        String outputFile = inputFile.replace(".asm", ".hack");

        // Initialize the parser for the input file
        parser = new Parser(inputFile);

        // Create the output file
        createOutputFile(outputFile);

        // Open the output file for writing
        openFile(outputFile);

        // Parse and process each command in the assembly file
        while (parser.hasMoreCommands()) {
            parser.advance();

            StringBuilder instruction = new StringBuilder();

            if (parser.commandType() == CommandType.A_COMMAND) {
                String symbol = parser.symbol();

                if (isSymbol(symbol)) {
                    if (!symbolTable.contains(symbol)) {
                        int address = symbolTable.getNextAvailableAddress();
                        symbolTable.addEntry(symbol, address);
                    }
                    symbol = String.valueOf(symbolTable.getAddress(symbol));
                }

                instruction.append(Translator.constant(symbol));
                writeToFile(instruction.toString(), outputFile);
            } else if (parser.commandType() == CommandType.C_COMMAND) {
                instruction.append(Translator.comp(parser.comp()));
                instruction.append(Translator.dest(parser.dest()));
                instruction.append(Translator.jump(parser.jump()));

                writeToFile(instruction.toString(), outputFile);
            }
        }

        // Close the output file and resources used by the parser
        closeFile(outputFile);
        parser.terminate();
    }

    /**
     * Initializes the symbol table by parsing labels and commands from the assembly
     * file.
     * 
     * @param file The name of the input assembly file.
     * @return The initialized SymbolTable object containing label and variable
     *         mappings.
     */
    private static SymbolTable initializeSymbolTable(String file) {
        Parser parser = new Parser(file);
        SymbolTable symbolTable = new SymbolTable();
        int lineNumber = 0;

        while (parser.hasMoreCommands()) {
            parser.advance();

            if (parser.commandType() == CommandType.L_COMMAND) {
                symbolTable.addEntry(parser.label(), lineNumber);
            } else if (parser.commandType() == CommandType.C_COMMAND || parser.commandType() == CommandType.A_COMMAND) {
                lineNumber++;
            }
        }

        parser.terminate();
        return symbolTable;
    }

    /**
     * Creates the output file if it doesn't already exist.
     * 
     * @param file The name of the output file to create.
     */
    private static void createOutputFile(String file) {
        try {
            File outputFile = new File(file);
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error while creating output file " + e.getMessage());
        }
    }

    /**
     * Writes the generated instruction to the output file.
     * 
     * @param instruction The binary instruction to write to the output file.
     * @param file        The name of the output file.
     */
    private static void writeToFile(String instruction, String file) {
        if (writer == null) {
            System.err.println("FileWriter not initialized. Skipping write operation.");
            return;
        }

        try {
            writer.write(instruction);
            writer.write(System.lineSeparator()); // write a new line
        } catch (IOException e) {
            System.err.println("Error while writing to output file " + e.getMessage());
        }
    }

    /**
     * Opens the output file for writing.
     * 
     * @param file The name of the output file to open.
     */
    private static void openFile(String file) {
        try {
            writer = new FileWriter(file);
        } catch (IOException e) {
            System.err.println("Error while opening output file " + e.getMessage());
        }
    }

    /**
     * Closes the output file after writing is complete.
     * 
     * @param file The name of the output file to close.
     */
    private static void closeFile(String file) {
        try {
            writer.close();
        } catch (IOException e) {
            System.err.println("Error while closing output file " + e.getMessage());
        }
    }

    /**
     * Determines if a given mnemonic is a symbol (i.e., starts with a letter).
     * 
     * @param mnemonic The mnemonic to check.
     * @return true if the mnemonic is a symbol, false otherwise.
     */
    private static boolean isSymbol(String mnemonic) {
        return Character.isLetter(mnemonic.charAt(0));
    }
}
