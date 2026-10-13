```markdown
# ASCII Art Converter

A simple Java console application that converts images to ASCII art and prints the result to the terminal. The project follows a clean, layered architecture and includes validation for input parameters.

## Features

- Convert a given image file to ASCII art using intensity‑based character mapping.
- Validate input: file existence, supported file extensions, and correct number of arguments.
- Print ASCII art directly to the console.
- Built‑in utilities for screen size detection and image manipulation.

## Architecture Overview

- **Controller** (`AsciiArtController`) – Handles command‑line arguments and orchestrates the conversion flow.
- **Service** (`AsciiArtService`) – Coordinates image conversion and printing.
- **Converter** (`Converter`) – Maps each pixel’s intensity to an appropriate ASCII character.
- **Printer** (`AsciiArtPrinter`) – Builds and outputs the ASCII art row by row.
- **Validator** (`AsciiArtValidationChain`, `ValidationRule`, and concrete rules) – Enforces input validation rules.
- **Utilities** (`ScreenSizeProvider`, `ImageUtils`) – Provide platform‑specific and generic helper functions.

## How to Build

The project uses **Maven** as the build tool. Ensure you have Java 8+ and Maven installed.

```bash
# Clone the repository (if applicable)
git clone <repository-url>
cd asciiart-converter

# Build the project and resolve dependencies
mvn clean install
```

After a successful build, the executable JAR will be located at:
`target/asciiart-converter-<version>-jar-with-dependencies.jar` (or simply `target/asciiart-converter.jar` if you configure a different final name).

If you prefer Gradle, you can use the equivalent commands:

```bash
./gradlew clean build
```

and run the JAR from `build/libs/`.

## How to Run

Run the application with a single command‑line argument: the path to the image file you want to convert.

```bash
# Using the Maven‑built JAR
java -jar target/asciiart-converter-<version>-jar-with-dependencies.jar /path/to/your/image.png

# Or, if you have installed the application via `mvn install`, you can run the main class directly
mvn exec:java -Dexec.mainClass="pl.mgodyn.asciiart.application.AsciiArtApp" -Dexec.args="/path/to/your/image.png"
```

### Supported Image Formats

The application currently supports:
- **PNG** (`.png`)
- **JPEG** (`.jpg`, `.jpeg`)

> **Tip**: Provide an absolute path or a path relative to the current working directory to avoid file‑not‑found errors.

## Example

Given an image file `sunset.jpg`:

```
$ java -jar target/asciiart-converter-1.0.jar ./sunset.jpg
 ______
|      \
|   _   \
|  (O)  |
|   T   |
|  /|\  |
|  / \  |
|_______|
```

(The exact output depends on the image dimensions and intensity mapping.)

## Validation Rules

The application validates the input according to the following rules:

1. **FileExistenceValidationRule** – Ensures the provided file exists.
2. **FileExtensionValidationRule** – Checks that the file extension is one of the supported formats.
3. **LengthValidationRule** – Verifies that exactly one argument (the image path) is supplied.

If any rule fails, a clear error message is printed to the console and the program exits.

## Testing

The project includes unit tests for the validation chain, converter, and printer components. Run the tests with:

```bash
mvn test
```

or with Gradle:

```bash
./gradlew test
```

## Project Structure

```
src/
  main/
    java/
      pl/
        mgodyn/
          asciiart/
            application/               # Controller & main entry point
            converter/                 # Pixel‑to‑ASCII conversion
            io/                        # ASCII art printing utilities
            service/                   # Core conversion & printing service
            utils/                     # Helper utilities
            application/
              validator/               # Validation framework
                ValidationRule.java
                AsciiArtValidationChain.java
                rules/                 # Concrete validation rules
                  FileExistenceValidationRule.java
                  FileExtensionValidationRule.java
                  LengthValidationRule.java
  target/                            # Build output (generated)
```

## Contributing

Feel free to submit issues or pull requests. Please follow the existing code style and ensure that all tests pass before merging.

## License

This project is licensed under the **Apache License, Version 2.0**. See the `LICENSE` file for details.

---

*If you have any questions or need further assistance, please open an issue in the repository.*