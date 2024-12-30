import java.util.Map;

/**
 * The Translator class is responsible for converting assembly language
 * mnemonics into their corresponding binary representations.
 * It provides methods to translate "comp", "dest", and "jump" mnemonics into
 * binary code, as well as converting constants
 * into their 16-bit binary equivalent.
 * 
 * <p>
 * The class utilizes three static tables: compTable, destTable, and jumpTable,
 * which store the mappings of mnemonics
 * to their respective binary codes. Additionally, a prefix for the "comp"
 * mnemonics is applied to generate the full binary
 * representation for computation-related operations.
 * 
 * <p>
 * Example usage:
 * 
 * <pre>
 * String destBinary = Translator.dest("M");
 * String compBinary = Translator.comp("D+A");
 * String jumpBinary = Translator.jump("JMP");
 * String constantBinary = Translator.constant("42");
 * </pre>
 */
public class Translator {

    /**
     * Prefix for the "comp" mnemonics binary code.
     */
    static String compPrefix = "111";

    /**
     * A table of "comp" mnemonics and their corresponding binary codes.
     * The "comp" mnemonic represents the computation part of an instruction.
     */
    static Map<String, String> compTable = Map.ofEntries(
            Map.entry("0", "0101010"),
            Map.entry("1", "0111111"),
            Map.entry("-1", "0111010"),
            Map.entry("D", "0001100"),
            Map.entry("A", "0110000"),
            Map.entry("!D", "0001101"),
            Map.entry("!A", "0110001"),
            Map.entry("-D", "0001111"),
            Map.entry("-A", "0110011"),
            Map.entry("D+1", "0011111"),
            Map.entry("A+1", "0110111"),
            Map.entry("D-1", "0001110"),
            Map.entry("A-1", "0110010"),
            Map.entry("D+A", "0000010"),
            Map.entry("D-A", "0010011"),
            Map.entry("A-D", "0000111"),
            Map.entry("D&A", "0000000"),
            Map.entry("D|A", "0010101"),
            Map.entry("M", "1110000"),
            Map.entry("!M", "1110001"),
            Map.entry("-M", "1110011"),
            Map.entry("M+1", "1110111"),
            Map.entry("M-1", "1110010"),
            Map.entry("D+M", "1000010"),
            Map.entry("D-M", "1010011"),
            Map.entry("M-D", "1000111"),
            Map.entry("D&M", "1000000"),
            Map.entry("D|M", "1010101"));

    /**
     * A table of "dest" mnemonics and their corresponding binary codes.
     * The "dest" mnemonic represents the destination register(s) of an instruction.
     */
    static Map<String, String> destTable = Map.ofEntries(
            Map.entry("null", "000"),
            Map.entry("M", "001"),
            Map.entry("D", "010"),
            Map.entry("MD", "011"),
            Map.entry("A", "100"),
            Map.entry("AM", "101"),
            Map.entry("AD", "110"),
            Map.entry("AMD", "111"));

    /**
     * A table of "jump" mnemonics and their corresponding binary codes.
     * The "jump" mnemonic represents the jump condition of an instruction.
     */
    static Map<String, String> jumpTable = Map.ofEntries(
            Map.entry("null", "000"),
            Map.entry("JGT", "001"),
            Map.entry("JEQ", "010"),
            Map.entry("JGE", "011"),
            Map.entry("JLT", "100"),
            Map.entry("JNE", "101"),
            Map.entry("JLE", "110"),
            Map.entry("JMP", "111"));

    /**
     * Returns the binary code (as a string) of the "dest" mnemonic.
     * 
     * @param mnemonic The "dest" mnemonic to be translated.
     * @return The binary code corresponding to the "dest" mnemonic.
     * @throws NullPointerException If the mnemonic is not found in the table.
     */
    public static String dest(String mnemonic) {
        return destTable.get(mnemonic);
    }

    /**
     * Returns the binary code (as a string) of the "comp" mnemonic.
     * The "comp" mnemonic represents the computation to be performed.
     * 
     * @param mnemonic The "comp" mnemonic to be translated.
     * @return The binary code corresponding to the "comp" mnemonic, prefixed with
     *         "111".
     * @throws NullPointerException If the mnemonic is not found in the table.
     */
    public static String comp(String mnemonic) {
        return compPrefix + compTable.get(mnemonic);
    }

    /**
     * Returns the binary code (as a string) of the "jump" mnemonic.
     * 
     * @param mnemonic The "jump" mnemonic to be translated.
     * @return The binary code corresponding to the "jump" mnemonic.
     * @throws NullPointerException If the mnemonic is not found in the table.
     */
    public static String jump(String mnemonic) {
        return jumpTable.get(mnemonic);
    }

    /**
     * Returns a 16-bit binary code for a given constant.
     * 
     * @param mnemonic The constant to be converted into binary.
     * @return A 16-bit binary representation of the constant.
     */
    public static String constant(String mnemonic) {
        return to16BitBinary(mnemonic);
    }

    /**
     * Converts a string representation of a number into its 16-bit binary
     * equivalent.
     * 
     * @param input The string representing the number.
     * @return The 16-bit binary representation of the number.
     */
    private static String to16BitBinary(String input) {
        // Parse string into integer
        int value = Integer.parseInt(input);

        // Convert to binary and pad to 16 bits
        String binary = String.format("%16s", Integer.toBinaryString(value)).replace(' ', '0');

        return binary;
    }
}
