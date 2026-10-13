```markdown
# AsciiArtGenerator

This project provides a basic implementation for converting images into ASCII art. It includes functionality for validating input arguments, converting pixel data to ASCII characters, and printing the resulting ASCII art to the console.

## Features

*   **Image Conversion:** Converts an input image into ASCII art.
*   **Input Validation:**  Validates the input arguments to ensure they are suitable for the conversion process.
*   **Console Output:** Prints the generated ASCII art to the console.
*   **Unit Tests:**  Includes comprehensive unit tests for core components.

## Project Structure

The project is structured into the following modules:

*   **Application Layer:** Contains the main application logic (`AsciiArtApp`, `AsciiArtController`).
*   **Validation Layer:** Implements a chain of validation rules to ensure valid input (`AsciiArtValidationChain`, `ValidationRule`, `FileExistenceValidationRule`, `FileExtensionValidationRule`, `LengthValidationRule`).
*   **Converter Layer:** Handles the conversion of pixel data to ASCII characters (`Converter`).
*   **IO Layer:** Provides functionality for printing the ASCII art (`AsciiArtPrinter`).
*   **Service Layer:** Contains the core conversion logic (`AsciiArtService`).
*   **Utility Layer:** Provides helper classes for screen size determination and image manipulation (`DefaultScreenSizeProvider`, `ImageUtils`, `ScreenSizeProvider`).
*   **Test Layer:** Contains unit tests for various components (`AsciiArtValidationChainTest`, `ValidationRuleTest`, `ConverterTest`, `AsciiArtPrinterTest`, `ImageUtilsTest`).


## Dependencies

*   (Add any external dependencies here, e.g., JUnit, Jackson, etc.)


## Getting Started

1.  **Clone the repository:**
    ```bash
    git clone <repository_url>
    cd AsciiArtGenerator
    ```
2.  **Build the project:**
    ```bash
    mvn clean install
    ```
3.  **Run the application:**
    The application is designed to be run from the command line.  Instructions for running the application will be provided by the `AsciiArtController`. Example:
   ```bash
   java pl.mgodyn.asciiart.application.AsciiArtApp image.jpg
   ```


## Usage

The application takes the path to an image as a command-line argument.  The image will be converted to ASCII art and printed to the console.  The `AsciiArtController` class handles the overall control flow and manages the validation and conversion process.

## Contributing

Contributions are welcome! Please follow these guidelines:

1.  Fork the repository.
2.  Create a new branch for your feature or bug fix.
3.  Write your code and add tests.
4.  Submit a pull request.

## License

(Specify the license for your project, e.g., MIT License)
```

**Note:** This README.md provides a basic structure.  You'll need to fill in the specific details, such as:

*   The actual repository URL.
*   Instructions for building and running the project.
*   Details on any dependencies.
*   The license information.
*   Specific command line arguments and their descriptions.
*   The steps for running the unit tests.
