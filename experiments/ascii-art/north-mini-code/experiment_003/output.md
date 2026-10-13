```markdown
# ASCII Art Generator

**ASCII Art Generator** is a command-line application written in Java that converts images (e.g., JPG, PNG) into ASCII art and prints the result directly to the console.

## Table of Contents
- [Installation](#installation)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Technologies](#technologies)

---

## Installation

### Prerequisites
*   **Java 8** or higher
*   **Maven** (for building)

### Steps
1.  Clone the repository:
    ```bash
    git clone <repository-url>
    cd <project-folder>
    ```

2.  Build the project using Maven. This will download dependencies and compile the source code:
    ```bash
    mvn clean install
    ```

    The build process produces a JAR file (e.g., `target/ascii-art-generator.jar`) in the `target` directory.

---

## Usage

After building, you can run the application using the generated JAR file.

### Command Line
The application relies on a validation chain to check command-line arguments. It expects an input image file.

```bash
java -jar target/ascii-art-generator.jar <path_to_image_file>
```

### Arguments Validation
The application validates the input arguments using a chain of responsibility:
*   **File Existence**: Ensures the specified file exists on the system.
*   **File Extension**: Ensures the file has a supported image extension (e.g., .png, .jpg).
*   **Length Validation**: Ensures the correct number of arguments is provided.

---

## Project Structure

The project follows a standard layered architecture. Below is an overview of the key components based on the provided source files:

*   **`src/main/java/pl/mgodyn/asciiart/application/`**
    *   `AsciiArtApp.java`: The main entry point of the application. Contains the `main` method to start the program.
    *   `AsciiArtController.java`: Orchestrates the flow of data between the user input, validation, service logic, and output printing.

*   **`src/main/java/pl/mgodyn/asciiart/application/validator/`**
    *   `AsciiArtValidationChain.java`: Manages a list of `ValidationRule` objects to execute a series of validation checks.
    *   `ValidationRule.java`: The base interface/class for defining validation logic.
    *   **`rules/`** (Sub-package)
        *   `FileExistenceValidationRule.java`: Validates that the provided file path points to an existing file.
        *   `FileExtensionValidationRule.java`: Validates that the file has an acceptable extension (e.g., .png, .jpg, .jpeg).
        *   `LengthValidationRule.java`: Validates the length of the arguments array.

*   **`src/main/java/pl/mgodyn/asciiart/service/`**
    *   `AsciiArtService.java`: Contains the core business logic. Specifically, it provides methods to `convert` a `BufferedImage` into an ASCII representation and to `print` the result.

*   **`src/main/java/pl/mgodyn/asciiart/io/`**
    *   `AsciiArtPrinter.java`: Handles the output mechanism. It manages a character buffer (row-by-row), allows adding characters, creating new rows, and finally printing the complete ASCII art to the console.

*   **`src/main/java/pl/mgodyn/asciiart/converter/`**
    *   `Converter.java`: Provides the mapping logic from pixel intensity (represented as an `int` from a `BufferedImage`) to specific ASCII characters (e.g., `@`, `#`, `:`, `.`).

*   **`src/main/java/pl/mgodyn/asciiart/utils/`**
    *   `DefaultScreenSizeProvider.java`: Provides the default screen dimensions to calculate the width of the ASCII art output.
    *   `ImageUtils.java`: Utility class for handling image-related operations (e.g., reading images).
    *   `ScreenSizeProvider.java`: Interface defining the contract for retrieving screen size information.

---

## Technologies

*   **Java 8+**
*   **Maven** (POM-based build system)
*   **Standard Java Libraries** (AWT/Swing for image handling, java.io for file I/O)

---

*Generated based on the repository structure provided.*
```