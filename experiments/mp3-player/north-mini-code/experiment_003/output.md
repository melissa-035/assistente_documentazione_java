# Java Music Player Application

A comprehensive desktop application featuring a GUI-based audio player, playlist management, and custom data structure implementations.

## Overview

This project is a Swing-based Java application (`MusicPlaylistManager`) that allows users to play audio files, create and manage playlists, and utilizes custom data structures (Hash Table, Binary Tree, Set, QuickSort) for internal operations.

### Key Features
*   **Audio Playback**: Play, pause, skip tracks, looping, and shuffling support.
*   **Playlist Management**: Create new playlists, open existing ones, and save playlists to files (`.wpl`).
*   **Data Structures**: Implements custom `MyHashTable`, `MySet`, `BinaryTrackTree`, and `QuickSort` algorithms instead of using standard Java collections for specific logic demonstrations.
*   **GUI**: Integrated Swing interface with controls for playback, searching, and sorting.

## Installation & Usage

### Prerequisites
*   **Java Development Kit (JDK)**: Version 8 or higher.
*   **IDE**: NetBeans, IntelliJ IDEA, or VS Code is recommended, but the project can also be built using the command line.

### Building from Source (Command Line)

Since this project does not use Maven or Gradle, you can compile and run it directly using `javac`.

1.  **Compile the source files**:
    Open your terminal or command prompt in the project root directory and run:
    ```bash
    javac src/*.java
    ```
    *Note: This assumes all `.java` files are located in the `src` directory as shown in the repository structure.*

2.  **Run the Application**:
    After successful compilation, execute the main class:
    ```bash
    java MusicPlaylistManager
    ```

### Running in an IDE
1.  Import the project as a Maven/Facet project or simply import the `src` folder as a source folder.
2.  Ensure all dependencies (if any, though this looks mostly self-contained) are satisfied.
3.  Run `MusicPlaylistManager` as a Java Application.

## Project Structure

The source code is organized as follows:

*   **`src/`**: The main source directory containing the application logic and UI.
    *   `AudioPlayer.java`: Handles audio stream processing, playback threads, and the player UI controls.
    *   `BackupManager.java`: Manages default directories for file operations.
    *   `BinaryTrackTree.java`: A Binary Search Tree implementation used for searching tracks by title.
    *   `MusicPlaylistManager.java`: The primary entry point and controller class, managing the main GUI, playlist interactions, and event handling.
    *   `MyHashTable.java`: A generic hash table implementation.
    *   `MyPlaylist.java`: Represents a collection of tracks, handling serialization and deserialization of playlist data.
    *   `MySet.java`: A custom set implementation.
    *   `QuickSort.java`: Provides an implementation of the QuickSort algorithm to sort Track arrays.
    *   `Track.java`: The data model class representing a single music track (Title, Artist, Album, Duration, File).
*   **`src/test/java/`**: Contains unit tests for the custom data structures.
    *   `DataStructureSmokeTest.java`: Verifies the functionality of `MyHashTable`, `MySet`, `BinaryTrackTree`, and `QuickSort`.

## How It Works

### Data Structures
This repository is particularly notable for implementing its own algorithms rather than relying on Java's built-in Collections framework for specific components:
*   **`MyHashTable`**: Provides storage and retrieval of key-value pairs (e.g., mapping Track IDs to Track objects).
*   **`MySet`**: Handles unique collections of objects.
*   **`BinaryTrackTree`**: Efficiently searches for tracks based on title using a tree structure.
*   **`QuickSort`**: Sorts lists of tracks (e.g., by name or duration).

### Audio Player
*   **Threading**: The `AudioPlayer` uses a dedicated `Thread` (`playerThread`) to handle audio decoding and playback, ensuring that the GUI remains responsive while music is playing.
*   **Controls**: Features standard playback controls (Play/Pause, Skip Back/Forward), a progress slider (`timeSlider`), and looping options.

### Playlist Management
*   **Persistence**: The `MyPlaylist` class handles file I/O operations to save and load playlists (using a custom `.wpl` format).
*   **Manager**: `MusicPlaylistManager` connects the UI with the `AudioPlayer` and `MyPlaylist`, coordinating the flow of tracks between the data model and the user interface.