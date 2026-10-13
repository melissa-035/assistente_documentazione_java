```markdown
# AsciiArt

This repository contains a Java application for converting images to ASCII art.

## Description

The AsciiArt application allows users to convert images to ASCII art using a configurable set of rules and a conversion process.  The application provides a basic interface for image processing and output.

---

## Classes

### AsciiArtApp

The entry point for the application. This class orchestrates the conversion process.

### AsciiArtController

Handles user input and calls the appropriate services for image conversion and validation.

### AsciiArtValidationChain

A chain of validation rules used to ensure the input image file is valid.

### ValidationRule

An interface defining a validation rule.

### FileExistenceValidationRule

A validation rule that checks if the input file exists.

### FileExtensionValidationRule

A validation rule that checks if the input file has a supported extension.

### LengthValidationRule

A validation rule that checks the length of the input file path.

### Converter

Responsible for the pixel-to-ASCII character conversion.

### AsciiArtPrinter

Handles the printing of the resulting ASCII art to the console.

### DefaultScreenSizeProvider

Provides a default screen size for the ASCII art output.

### ImageUtils

Contains utility methods for image manipulation.

### ScreenSizeProvider

An interface for providing screen sizes.

---

## Tests

### AsciiArtValidationChainTest

Tests the AsciiArtValidationChain.

### ValidationRuleTest

Tests the base ValidationRule interface.

### ConverterTest

Tests the Converter class.

### AsciiArtPrinterTest

Tests the AsciiArtPrinter class.

### ImageUtilsTest

Tests the ImageUtils class.

---

## Contributing

Contributions to this project are welcome! Please see the [CONTRIBUTING.md](CONTRIBUTING.md) file for more information.
```
