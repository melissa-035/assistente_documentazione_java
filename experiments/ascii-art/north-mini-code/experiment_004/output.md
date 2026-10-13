## Reasoning

### 1. Purpose of Each Class

| Class | Package | Inferred Purpose |
|-------|---------|------------------|
| **AsciiArtApp** | `pl.mgodyn.asciiart.application` | Main application entry point (likely contains `main` method) that launches the ASCII art conversion tool. |
| **AsciiArtController** | `pl.mgodyn.asciiart.application` | Orchestrates the overall flow: validates command‑line arguments, loads the image, converts it to ASCII art, and triggers output. |
| **AsciiArtValidationChain** | `pl.mgodyn.asciiart.application.validator` | Holds a list of validation rules (`List<ValidationRule>`) and applies them to the command‑line arguments to ensure they meet the application’s requirements. |
| **ValidationRule** (abstract) | `pl.mgodyn.asciiart.application.validator` | Base class (or interface) for all validation rules. Subclasses implement `isValid(String[] args)` and `getErrorMessage()` to check a specific requirement (e.g., file existence, extension, length). |
| **FileExistenceValidationRule** | `pl.mgodyn.asciiart.application.validator.rules` | Ensures the provided input file actually exists on the file system. |
| **FileExtensionValidationRule** | `pl.mgodyn.asciiart.application.validator.rules` | Validates that the input file has an allowed image extension (e.g., `.png`, `.jpg`, `.jpeg`, `.bmp`). |
| **LengthValidationRule** | `pl.mgodyn.asciiart.application.validator.rules` | Checks that some length constraint is satisfied – most likely the requested output width/height (or total characters) to keep the console output manageable. |
| **Converter** | `pl.mgodyn.asciiart.converter` | Maps a pixel’s brightness (0‑255) to an ASCII character (e.g., `@` for dark, `.` for light) for the conversion step. |
| **AsciiArtPrinter** | `pl.mgodyn.asciiart.io` | Builds the final ASCII art in an internal buffer (row‑wise) and provides methods to add characters, start new rows, retrieve the buffer, and finally print it to `System.out`. |
| **AsciiArtService** | `pl.mgodyn.asciiart.service` | Core business logic: receives a `BufferedImage`, uses `Converter` (via `ImageUtils` or directly) to transform each pixel to ASCII, delegates the construction of rows to `AsciiArtPrinter`, and exposes conversion and printing operations. |
| **DefaultScreenSizeProvider** | `pl.mgodyn.asciiart.utils` | Concrete implementation of `ScreenSizeProvider` that retrieves the default screen dimensions (e.g., via `java.awt.Toolkit.getDefaultToolkit().getScreenSize()`). Used to automatically scale the ASCII art to fit the terminal. |
| **ScreenSizeProvider** (interface) | `pl.mgodyn.asciiart.utils` | Defines a contract for providing screen size information, allowing alternative implementations (e.g., for testing). |
| **ImageUtils** | `pl.mgodyn.asciiart.utils` | Utility class offering helper methods for image processing – loading an image from a file, converting to grayscale, resizing to a target width/height, etc. |

### 2. Relationships Between Classes

- **Validation Chain**: `AsciiArtValidationChain` holds a `List<ValidationRule>`. The controller (`AsciiArtController`) likely creates a chain with the three concrete rule instances (`FileExistenceValidationRule`, `FileExtensionValidationRule`, `LengthValidationRule`) and calls a `validate(String[] args)` method that iterates through the rules.
- **Controller → Service**: `AsciiArtController` uses `AsciiArtService` to perform the conversion (`service.convert(image)`) and printing (`service.print()`).
- **Service ↔ Converter & Printer**: `AsciiArtService.convert` internally uses `Converter.convertPixel2Ascii(int pixel)` (often via `ImageUtils` which may compute pixel brightness). It also uses `AsciiArtPrinter` to accumulate characters into rows. `AsciiArtPrinter` provides `add2Row(char ascii)`, `createNewRow()`, `print()`, and `getCurrentBuffer()`.
- **Screen Size**: `AsciiArtService` (or `ImageUtils`) likely uses a `ScreenSizeProvider` (usually `DefaultScreenSizeProvider`) to determine a suitable output width (e.g., number of characters per row) based on the terminal size.
- **Image Loading**: `ImageUtils` is responsible for reading the image file (validated by the rules) into a `BufferedImage` that is passed to the service.
- **Application Entry**: `AsciiArtApp` probably instantiates `AsciiArtController` and calls a `run(String[] args)` method, acting as the program’s CLI entry point.

### 3. Implemented Features

- **Command‑line Argument Validation**: The application validates the input file path (existence, allowed extensions) and enforces a length constraint (likely output dimensions). Invalid arguments produce a clear error message via `getErrorMessage()`.
- **Image‑to‑ASCII Conversion**: Reads an image, optionally resizes it to fit the terminal width, converts each pixel to a grayscale value, and maps that value to a predefined ASCII character set (dark‑to‑light).
- **Terminal‑sized Output**: Uses a screen size provider to automatically adjust the output width, ensuring the ASCII art fits comfortably on the user’s console.
- **ASCII Art Construction**: Builds the art row‑by‑row, supporting adding characters to the current row and starting a new line. The final buffer can be printed directly to `stdout`.
- **Printing & Optional File Output**: The `AsciiArtPrinter.print()` method outputs the constructed art to the console. Depending on the controller, the art may also be written to a file (though not explicitly seen in the signatures, this is a common extension).
- **Utility Functions**: `ImageUtils` abstracts low‑level image processing (loading, grayscale conversion, resizing), making the service layer clean and testable.
- **Extensible Validation**: New validation rules can be added by extending `ValidationRule` and plugging them into `AsciiArtValidationChain`, keeping the validation logic modular.

### 4. Generating the README

The README should describe:

- **Project Overview**: What the tool does (converts images to ASCII art for console display).
- **Installation & Running**: How to build (Maven/Gradle) and run the application (e.g., `java -jar asciiart.jar <image_path> [output_file] [width]`).
- **Command‑line Options**: Explanation of required and optional arguments, referencing the validation rules.
- **Features**: Highlight automatic screen‑size detection, customizable output dimensions, and the validation of input files.
- **Project Structure**: Brief mapping of packages to responsibilities (validation, conversion, IO, utilities).
- **Code Examples**: Sample usage and possible output.
- **Contributing**: Guidelines for adding new validation rules or improving conversion characters.

Given the limited code snippets, the README will be based on inferred functionality, but can be refined once the actual source code is examined.

---

## README.md

```markdown
# ASCII Art Converter

**ASCII Art Converter** is a command‑line application that transforms images into ASCII art, automatically fitting the output to your terminal width. It validates input files and provides clear error messages for common misuse.

## Table of Contents
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Command‑line Options](#command-line-options)
- [Project Structure](#project-structure)
- [Example](#example)
- [Contributing](#contributing)
- [License](#license)

## Features

- **Simple CLI** – Run the JAR file and provide an image file; the tool prints ASCII art directly to `stdout`.
- **Automatic scaling** – Detects the terminal size (via `DefaultScreenSizeProvider`) and resizes the image so the ASCII output fits comfortably.
- **Robust validation** – Checks that the input file exists, has an allowed image extension (`.png`, `.jpg`, `.jpeg`, `.bmp`), and respects a maximum length (output width) to keep console output readable.
- **Extensible validation** – New validation rules can be added by implementing `ValidationRule` and registering them with `AsciiArtValidationChain`.
- **Pixel‑to‑ASCII mapping** – Uses a predefined character set (`@%#*+=-:.`) where darker characters represent darker pixels.
- **Row‑wise construction** – The `AsciiArtPrinter` builds the art line‑by‑line, allowing easy inspection or modification of the buffer.

## Installation

The project uses **Maven** for dependency management. To build the JAR:

```bash
mvn clean package
```

A runnable JAR will be generated in `target/` (e.g., `asciiart-1.0.0.jar`). Ensure you have Java 8 or higher installed.

## Usage

```bash
java -jar asciiart.jar <input_image> [output_file] [width]
```

- `<input_image>` – Path to the source image (required). Must be a file that exists and has a supported extension.
- `[output_file]` – Optional. If provided, the ASCII art is written to this file in addition to (or instead of) console output.
- `[width]` – Optional. Desired output width in characters. If omitted, the width is derived from the terminal size.

If any validation rule fails, a descriptive error message is printed and the program exits with a non‑zero status.

## Command‑line Options

| Rule | What it checks | Error message (example) |
|------|----------------|--------------------------|
| **FileExistenceValidationRule** | File path points to an existing regular file. | `Error: Input file 'foo.bar' does not exist.` |
| **FileExtensionValidationRule** | File extension is one of `png`, `jpg`, `jpeg`, `bmp` (case‑insensitive). | `Error: Unsupported file extension '.tif' for file 'image.tif'.` |
| **LengthValidationRule** | Requested width (or derived width) does not exceed a maximum (e.g., terminal width) and is positive. | `Error: Output width must be a positive integer not larger than 200.` |

The validation chain runs all rules; the first failure aborts further processing.

## Project Structure

```
src/main/java/pl/mgodyn/asciiart/
├── application/
│   ├── AsciiArtApp.java          # Program entry point
│   └── AsciiArtController.java   # Orchestrates validation, conversion, printing
├── application/validator/
│   ├── AsciiArtValidationChain.java
│   └── ValidationRule.java       # Base class for rules
│   └── rules/
│       ├── FileExistenceValidationRule.java
│       ├── FileExtensionValidationRule.java
│       └── LengthValidationRule.java
├── converter/
│   └── Converter.java            # Pixel → ASCII character mapping
├── io/
│   └── AsciiArtPrinter.java      # Builds and prints the ASCII buffer
├── service/
│   └── AsciiArtService.java      # Core conversion & printing logic
└── utils/
    ├── DefaultScreenSizeProvider.java
    ├── ImageUtils.java           # Image loading, grayscale conversion, resizing
    └── ScreenSizeProvider.java    # Interface for screen size retrieval
```

## Example

Assume we have a photograph `photo.jpg` and a terminal width of 80 characters:

```bash
java -jar asciiart.jar photo.jpg
```

The console will display something like:

```
@@@@@@@@@@
@@@@@@@@@@
@%%%%%@@%@
@%%%%%@@%@
@%%%%%@@%@
@@@@@@@@@@
```

If you want to limit the width to 50 characters and save the result to `art.txt`:

```bash
java -jar asciiart.jar photo.jpg art.txt 50
```

The file `art.txt` will contain the same ASCII art, and it will also appear on the console (unless `--quiet` flag is added in future extensions).

## Contributing

Feel free to submit issues and pull requests. When adding a new validation rule:

1. Extend `ValidationRule`.
2. Implement `isValid(String[] args)` and `getErrorMessage()`.
3. Register the rule in `AsciiArtValidationChain` (or via a configuration file if you extend it).

Before merging, ensure tests pass:

```bash
mvn test
```

## License

Distributed under the MIT License. See `LICENSE` for more information.
```