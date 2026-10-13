```markdown
# ASCII Art Converter

A Java application that converts images into ASCII art, displayed in the console.

## Overview
The project provides a command-line tool that accepts an image file and optional dimensions, validates the input, converts the image to ASCII characters based on pixel intensity, and prints the result to the terminal.

## Classes

### AsciiArtApp
- **Description**: The main entry point for the application. Launches the ASCII art conversion process.
- **Fields**: None
- **Methods**: None

### AsciiArtController
- **Description**: Coordinates validation, conversion, and printing steps.
- **Fields**: None
- **Methods**: None

### AsciiArtValidationChain
- **Description**: Holds a list of validation rules and applies them to input arguments.
- **Fields**:
  - `rules`: List<ValidationRule>
- **Methods**: None

### ValidationRule
- **Description**: Abstract base class for validation rules.
- **Fields**: None
- **Methods**: None

### FileExistenceValidationRule
- **Description**: Validates that the specified image file exists.
- **Fields**: None
- **Methods**:
  - `isValid(String[] args)`: Checks if the file exists.
  - `getErrorMessage()`: Returns error message if validation fails.

### FileExtensionValidationRule
- **Description**: Validates the file extension of the image file.
- **Fields**: None
- **Methods**:
  - `isValid(String[] args)`: Checks if the extension is allowed.
  - `getErrorMessage()`: Returns error message if validation fails.

### LengthValidationRule
- **Description**: Validates the dimensions (width/height) arguments.
- **Fields**: None
- **Methods**:
  - `isValid(String[] args)`: Checks if dimensions are within constraints.
  - `getErrorMessage()`: Returns error message if validation fails.

### Converter
- **Description**: Converts pixel intensity values to corresponding ASCII characters.
- **Fields**: None
- **Methods**:
  - `convertPixel2Ascii(int pixel)`: Returns ASCII character for given pixel intensity.

### AsciiArtPrinter
- **Description**: Manages the internal buffer and prints the ASCII art to the console.
- **Fields**: None
- **Methods**:
  - `add2Row(char ascii)`: Adds a character to the current row buffer.
  - `createNewRow()`: Starts a new row in the buffer.
  - `print()`: Outputs the buffered ASCII art.
  - `getCurrentBuffer()`: Returns the current buffer (for testing/inspection).

### AsciiArtService
- **Description**: Handles the conversion of a BufferedImage to ASCII art and printing.
- **Fields**: None
- **Methods**:
  - `convert(BufferedImage image)`: Performs the conversion and stores result in printer.
  - `print()`: Prints the converted ASCII art.

### DefaultScreenSizeProvider
- **Description**: Provides default screen dimensions for resizing the image.
- **Fields**: None
- **Methods**:
  - `getScreenSize()`: Returns a Dimension object representing screen size.

### ImageUtils
- **Description**: Utility methods for image processing.
- **Fields**: None
- **Methods**: None

### ScreenSizeProvider
- **Description**: Interface for obtaining screen size.
- **Fields**: None
- **Methods**: None

## How to Use

### Prerequisites
- Java 8 or higher

### Running the Application
Run the main class `AsciiArtApp`. It expects command-line arguments:
```
<imagePath> [width] [height]
```
- `imagePath`: Path to the image file (must exist and have a supported extension).
- `width` (optional): Desired width of the ASCII art.
- `height` (optional): Desired height of the ASCII art.

Example:
```bash
java -jar asciiart.jar image.png 80 40
```

### Validation Rules
- **FileExistenceValidationRule**: Ensures the file exists.
- **FileExtensionValidationRule**: Ensures the file extension is one of `.png`, `.jpg`, `.jpeg`.
- **LengthValidationRule**: Ensures width and height are positive integers within screen limits (if provided).

### Build
The project uses Maven. To compile and package:
```bash
mvn clean package
```
The resulting JAR will be located in `target/`.

For questions or issues, please refer to the source code or contact the maintainer.
```