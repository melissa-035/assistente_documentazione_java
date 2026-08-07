```markdown
# mp3-player

## Project Description

This project is an MP3 player application that allows users to manage and play music playlists.

## Repository Structure

The repository contains the following source files:

*   `AudioPlayer.java`: The main class for handling audio playback functionality.
*   `BackupManager.java`:  Manages backup operations, saving default settings.
*   `BinaryTrackTree.java`: (Not implemented - likely a data structure related to track management)
*   `MusicPlaylistManager.java`: Handles playlist creation, editing, and display within the user interface.
*   `MyHashTable.java`:  (Not implemented - likely a custom hash table implementation).
*   `MyPlaylist.java`: Represents a music playlist with tracks and metadata.
*   `MySet.java`: (Not implemented - likely a custom set implementation)
*   `QuickSort.java`: (Not implemented - likely a quick sort algorithm implementation).
*   `Track.java`: Represents an individual music track with properties such as title, artist, album, and duration.
*   `DataStructureSmokeTest.java`: Unit tests for the data structures (not implemented).

## Main Java Classes

### AudioPlayer

The `AudioPlayer` class is the core of the MP3 player application. It handles audio playback, user interface interactions, and playlist management.  It uses a `Player` instance to actually play the music files. Key functionalities include:

*   Playing and pausing tracks
*   Skipping forward and backward
*   Managing the current track and playlist
*   Handling slider controls for seeking within tracks
*   Implementing search functionality

### BackupManager

The `BackupManager` class is responsible for saving and restoring default application settings (such as directory paths). It provides methods to save these defaults to a file and retrieve them.

### MusicPlaylistManager

The `MusicPlaylistManager` manages the creation, editing, and display of playlists. This includes handling user interface elements such as tables, text fields, and buttons for adding, removing, and sorting tracks within a playlist. The class interacts with the `AudioPlayer` to control playback. 

### Track

The `Track` class represents individual music tracks in the application.  It stores metadata about the track, including its title, artist, album, duration, and associated file information. It provides methods for accessing this metadata.  Crucially it includes a `hashCode()` and `equals()` method implemented to allow comparison of Track objects.

## Implemented Features

*   Playlist creation and management
*   Music playback control (play/pause, skip forward/backward)
*   Track searching
*   Shuffle functionality
*   GUI controls for playlist manipulation
*   Backup and restore default settings
