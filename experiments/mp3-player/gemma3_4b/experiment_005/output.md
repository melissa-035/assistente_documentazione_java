```markdown
# MP3 Player - Java Project

**A simple MP3 player application with playlist management and backup features.**

## Description

This project implements a basic MP3 player that allows users to play audio files, manage playlists, create backups, and search for tracks.  It uses various data structures like `ArrayList`, `HashMap` (represented by `MyHashTable`), and binary trees to store and manipulate track information. The application provides a graphical user interface (GUI) built with Swing for interaction.

## Features

*   **Audio Playback:** Play MP3 files directly from the file system.
*   **Playlist Management:** Create, edit, and save playlists of MP3 tracks.
*   **Search Functionality:** Search for tracks by title or artist within a playlist or file system.
*   **Backup/Restore:**  Save and restore playlist data to files.
*   **Track Information Display:** Displays track details like title, artist, album art, and duration.
*   **Sorting:** Sort playlists by various criteria (e.g., alphabetically by title).
*   **User Interface:** A Swing-based GUI for user interaction.

## Technologies Used

*   Java SE
*   Swing (GUI framework)
*   File I/O operations
*   Data Structures: `ArrayList`, `HashMap` (MyHashTable), Binary Trees, `Track` objects
*   Sorting Algorithms: QuickSort

## Project Structure

The project is organized into several Java files, each responsible for a specific aspect of the application:

*   **AudioPlayer.java:** Handles audio playback controls and manages the player instance.
*   **BackupManager.java:**  Manages backup operations, saving and restoring playlist data to/from files.
*   **BinaryTrackTree.java:** (Not Currently Used) Likely intended for track indexing but currently not implemented.
*   **MusicPlaylistManager.java:** Contains the main GUI logic and handles playlist creation, management, and interaction with the AudioPlayer.
*   **MyHashTable.java:**  (Hash Table implementation - potentially simplified version). Could be used to efficiently store and retrieve track data based on its ID or other attributes.
*   **MyPlaylist.java:** Represents a playlist containing a collection of Track objects.
*   **MySet.java:** (Unimplemented Set Implementation). Potentially used for managing unique tracks in a playlist, but currently empty.
*   **QuickSort.java:**  Implements the QuickSort algorithm for sorting track lists.
*   **Track.java:** Represents an individual MP3 track with its metadata (title, artist, album art, duration, file).
*   **DataStructureSmokeTest.java:** Unit tests to ensure basic functionality of data structures.

## Getting Started

1.  **Dependencies:** Ensure you have a Java Development Kit (JDK) installed.
2.  **Compilation:** Compile the source code using a Java compiler (e.g., `javac`).
3.  **Execution:** Run the compiled application from your IDE or command line: `java MainClass` (assuming "MainClass" is the name of the class containing the `main()` method in MusicPlaylistManager).

## Future Enhancements

*   **GUI Improvements:** Enhance the GUI with a more user-friendly interface, better visual design, and potentially more advanced controls.
*   **Support for More Audio Formats:** Extend support beyond MP3 files to include other audio formats (e.g., WAV, FLAC).
*   **Error Handling:** Implement robust error handling mechanisms to gracefully handle file errors, invalid data, or unexpected situations.
*   **Advanced Playlist Features:**  Add features like track shuffling, random play, and queue management.
* **Implement BinaryTrackTree:** Actual implementation of binary tree for faster access if needed.
*   **More Extensive Testing:** Add more comprehensive unit tests to cover a wider range of scenarios and ensure code quality.


## License

[MIT License](https://opensource.org/licenses/MIT) -  (Add license details here or reference an existing one.)

---
```