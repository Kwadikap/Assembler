import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * The {@code Parser} class reads and processes Hack assembly language (.asm) files,
 * extracting instructions and stripping comments and whitespace.
 * It facilitates the translation of assembly commands into machine code by 
 * providing methods to identify command types and extract components.
 * 
 * <p>This class supports A-instructions, C-instructions, and L-instructions, 
 * handling labels, symbols, and mnemonics. Commands are processed line by line, 
 * with comments and blank lines ignored.
 * 
 * <p>The class expects a valid .asm file path during instantiation and provides 
 * methods to traverse and parse the file.
 *
 * <p>Example usage:
 * {@code
 * Parser parser = new Parser("MyProgram.asm");
 * while (parser.hasMoreCommands()) {
 *     parser.advance();
 *     System.out.println(parser.commandType());
 * }
 * parser.terminate();
 * }
 *
 * @author  [Your Name]
 * @since   1.0
 */
class Parser {

    Scanner reader;
    String currentCommand = " ";

    public Parser(String filePath) {
        File inputFile = new File(filePath);

        // Try to initialize scanner and catch the exception internally
        try {
            if (!inputFile.exists()) {
                throw new FileNotFoundException("Input file does not exist");
            }
            reader = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            // Handle exception gracefully (e.g., log error, set reader to null)
            System.err.println("Error: " + e.getMessage());
            reader = null;
        }
    }

    public boolean hasMoreCommands() {
        return reader.hasNextLine();
    }

    /*
     * Reads the next command from the input file & updates current command.
     * Should only be called if hasMoreCommands() is true
     */
    public void advance() {
        String line = reader.nextLine();

        if (line.isEmpty() || line.startsWith("//")) {
            return;
        } else if (line.contains("//")) {
            line = line.replaceAll("\\s", ""); // strip all white spaces
            int endIndex = line.indexOf('/'); // find beginning of comment
            currentCommand = line.substring(0, endIndex); // extract instruction while ignoring comment
        } else {
            currentCommand = line.trim();
        }
    }

    /*
     * Returns type of current command.
     */
    public CommandType commandType() {
        if (currentCommand.startsWith("@")) {
            return CommandType.A_COMMAND;
        } else if (currentCommand.startsWith("(")) {
            return CommandType.L_COMMAND;
        } else if (currentCommand.length() > 0 && Character.isLetterOrDigit(currentCommand.charAt(0))) {
            return CommandType.C_COMMAND;
        }
        return CommandType.INVALID_COMMAND;
    }

    /*
     * Returns the symbol or decimal xxx of current @xxx command.
     * Should only be called when commandType() is A_COMMAND or L_COMMAND
     */
    public String symbol() {
        return currentCommand.substring(1, currentCommand.length());
    }

    public String label() {
        return currentCommand.substring(1, currentCommand.length() - 1);
    }

    /*
     * Returns the dest mnemonic in the current C-command.
     * Should only be called if commandType() is a C_COMMAND
     * 
     * Note* if dest is empty, the "=" is omitted
     */
    public String dest() {
        if (!currentCommand.contains("=")) {
            return "null";
        }

        return currentCommand.substring(0, currentCommand.indexOf("="));

    }

    /*
     * Returns the comp mnemonic in the current C-command.
     * Should only be called if commandType() is a C_COMMAND
     */
    public String comp() {
        int beginIndex = 0;
        int endIndex = currentCommand.length();

        // if dest is not null then comp should begin after "="
        if (dest() != "null") {
            beginIndex = currentCommand.indexOf("=") + 1;
        }
        // if jump is not null then comp should end at the ";" symbol
        if (jump() != "null") {
            endIndex = currentCommand.indexOf(";");
        }
        // return substring from beginIndex to endIndex
        return currentCommand.substring(beginIndex, endIndex);
    }

    /*
     * Return the jump mnemonic in the current C-command.
     * Should only be called when commandType() is C_COMMAND
     * 
     * Note* if jump is empty, the ";" is omitted
     */
    public String jump() {
        if (!currentCommand.contains(";")) {
            return "null";
        }

        int beginIndex = currentCommand.indexOf(";");

        return currentCommand.substring(beginIndex+1, currentCommand.length());
    }

    /*
     * Closes scanner, remember to use this when done with Parser
     */
    public void terminate() {
        reader.close();
    }

}