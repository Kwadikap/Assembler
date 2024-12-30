# Hack Assembler

The Hack Assembler is a tool that reads Hack assembly language files (`.asm`) and generates corresponding machine code files (`.hack`) that can be executed by the Hack CPU. This project simulates the process of translating assembly code into machine code for a theoretical computer architecture, as outlined in *The Elements of Computing Systems* by Nisan and Schocken.

## Features

- **Input**: Reads Hack assembly language instructions from `.asm` files.
- **Symbol Table**: Handles labels and variables by maintaining a symbol table that maps them to memory addresses.
- **Translation**: Translates Hack assembly instructions into binary machine code.
- **Output**: Generates `.hack` files with binary machine code that can be executed on the Hack CPU.

## Project Structure

The project consists of the following main components:

- **Assembler.java**: The core class that orchestrates the translation of Hack assembly code to machine code.
- **Parser.java**: A helper class responsible for parsing Hack assembly commands.
- **Translator.java**: Contains methods to translate the Hack assembly mnemonics (e.g., `A_COMMAND`, `C_COMMAND`, `JUMP`) into binary code.
- **SymbolTable.java**: Manages the symbol table, which maps symbols (e.g., variables and labels) to memory addresses.

## Usage

To use the assembler, provide the `.asm` file you want to translate as a command-line argument. The assembler will process the assembly code and produce a `.hack` file with the machine code output.

### Example

1. Clone the repository to your local machine:

   ```bash
   git clone https://github.com/your-username/hack-assembler.git
   cd hack-assembler
   ```

2. Compile the `Assembler.java` file:

   ```bash
   javac Assembler.java
   ```

3. Run the assembler with a Hack assembly file (`.asm`):

   ```bash
   java Assembler MyProgram.asm
   ```

4. This will generate a file named `MyProgram.hack` in the same directory.

### Sample Input (`MyProgram.asm`):

```asm
@2
D=A
@3
D=D+A
0;JMP
```

### Sample Output (`MyProgram.hack`):

```txt
0000000000000010
1110110000010000
0000000000000011
1110000010010000
1110101010000111
```

## Error Handling

- Invalid instructions or syntax errors will result in an error message and the termination of the program.
- Missing or incorrect file extensions will be flagged appropriately.

## Assumptions

- The input assembly file follows the Hack assembly language specification.
- Labels and variables are defined according to the standard Hack conventions.

## Dependencies

This project depends on the following helper classes:

- `Parser`: For parsing assembly instructions.
- `SymbolTable`: For managing symbols and their associated memory addresses.
- `Translator`: For converting Hack assembly mnemonics to binary machine code.

## Contributing

Contributions are welcome! If you would like to improve the tool or fix bugs, feel free to submit a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Author

**Kwadwo Adusei Poku**\
*Date*: 2024-12-30\
*Email*: kwadikap@gmail.com\
*Github*: https://github.com/Kwadikap

