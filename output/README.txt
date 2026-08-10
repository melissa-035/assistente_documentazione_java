```markdown
# MP3 Player

## Project Description

This project is a basic MP3 player application that allows users to manage and play music tracks from local files. It provides features for creating playlists, searching tracks, and saving/loading playlists.

## Repository Structure

The repository contains the source code for the MP3 player application, organized into multiple Java classes.  Key directories include:

*   `src`: Contains the main source code files.
*   `test`: Contains unit tests.

## Main Classes

*   **AudioPlayer:** The core class responsible for playing audio tracks and managing playback controls (play/pause, skip forward/backward, looping).
*   **BackupManager:** Handles saving and loading of application defaults and settings to files.
*   **BinaryTrackTree:**  A data structure used internally for track management. Its functionality is not fully described in the provided source code.
*   **MusicPlaylistManager:**  Manages the creation, editing, and display of music playlists within a GUI window.
*   **MyHashTable:**  An internal hash table implementation used by the MusicPlaylistManager.
*   **MySet:** An internal set implementation used by the MusicPlaylistManager.
*   **QuickSort:**  A sorting algorithm implemented for use in the MusicPlaylistManager.
*   **Track:** Represents an individual music track with properties such as title, artist, album, duration and associated image.

## Implemented Features

*   Play and pause audio tracks.
*   Skip forward and backward within a track.
*   Loop playback of a single track or the entire playlist.
*   Search for tracks by name.
*   Create and manage music playlists.
*   Save and load playlists to files.
*   Sorting of playlists based on various criteria (not fully documented).

## Dependencies

The source code does not explicitly define any external dependencies. However, the following internal data structures are used within the application:
* ArrayList
* HashMap
* HashSet
* JTable
* JSlider
* JTextField
* JPanel
* JButton
* JLabel
* DefaultTableModel
* BufferedImage