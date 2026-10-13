# Music Playlist Manager

## Description
The Java Music Playlist Manager is a desktop application built with Swing that enables users to create, edit, manage, and play music playlists. It provides a rich set of features including audio playback with a GUI player, playlist backup and restoration, sorting and filtering of tracks, search functionality, looping, shuffling, and track splitting. The application leverages custom data structures such as hash tables, binary trees, and sets to efficiently manage collections of tracks.

## Features
- **Audio Player** – Play/pause, skip forward/backward, volume control, loop, shuffle.
- **Playlist Management** – Create, open, save, and delete playlists; add/remove tracks.
- **Backup & Restore** – Save default directories and application settings.
- **Search** – Search for tracks by title, artist, or album.
- **Sorting** – Sort playlists by various attributes (e.g., title, duration, artist).
- **Track Information** – Display track details (title, artist, album, duration, artwork).
- **Custom Data Structures** – Efficient storage and retrieval using `MyHashTable`, `BinaryTrackTree`, `MySet`, and `QuickSort`.

## Project Structure
```
src/
├── AudioPlayer.java          # Main audio player UI component
├── BackupManager.java        # Handles backup and default directory settings
├── BinaryTrackTree.java      # Binary tree for searching tracks
├── MusicPlaylistManager.java # Main application entry point and UI controller
├── MyHashTable.java          # Custom hash table implementation
├── MyPlaylist.java           # Playlist data model
├── MySet.java                # Custom set implementation
├── QuickSort.java            # Quick‑sort algorithm for sorting tracks
├── Track.java                # Model class representing a music track
└── test/
    └── java/
        └── DataStructureSmokeTest.java # Smoke tests for data structures
```

## Building and Running
The project uses **Maven** for dependency management and build automation.

1. **Clone the repository**
   ```bash
   git clone <repository‑url>
   cd <project‑directory>
   ```

2. **Build the project**
   ```bash
   mvn clean package
   ```
   This will compile all source files and produce a JAR artifact in `target/`.

3. **Run the application**
   ```bash
   java -jar target/<artifact‑name>.jar
   ```

   If you prefer to run from source directly, execute `mvn exec:java` (requires Java 8+).

## Dependencies
- **Java 8** or later
- **Maven** (for building; no external runtime dependencies)

All components are implemented natively within the codebase; no third‑party libraries are required.

## Usage
1. Launch the application. The main window appears with a playlist panel, player controls, and a menu bar.
2. **Create a new playlist** – Use the “New Playlist” button, give it a name and description.
3. **Add tracks** – Click “Add Track”, browse for audio files, and select the desired tracks.
4. **Play a track** – Select a track from the playlist and press the Play button. The player UI will show the current track’s details and a progress slider.
5. **Control playback** – Use the play/pause, skip forward/backward, loop, and shuffle buttons.
6. **Search** – Enter keywords in the search field to filter tracks.
7. **Sort** – Use the “Sort” dropdown to order the playlist.
8. **Backup** – Use the “Backup” menu to save default directories and settings.
9. **Save/Exit** – Save changes via the “Save” button or quit the application with “Exit”.

## Data Structures Overview
- **`MyHashTable`** – Provides O(1) average‑case insertion, removal, and lookup.
- **`MySet`** – Implements a set with duplicate‑free storage and iteration support.
- **`BinaryTrackTree`** – Allows efficient search of tracks by title.
- **`QuickSort`** – Used to sort arrays of `Track` objects based on various criteria.
- **`MyPlaylist`** – Stores tracks in an `ArrayList` and provides methods for persistence (`.wpl` parser), serialization, and manipulation.

## Testing
A simple smoke test for the custom data structures is included in `test/java/DataStructureSmokeTest.java`. To run the tests:

```bash
mvn test
```

The test validates basic operations of `MyHashTable`, `MySet`, `BinaryTrackTree`, and `QuickSort`.

## Contributing
We welcome contributions! Please follow these steps:
1. Fork the repository.
2. Create a feature branch (`git checkout -b feature/fooBar`).
3. Commit your changes (`git commit -a`).
4. Push to the branch (`git push origin feature/fooBar`).
5. Open a Pull Request.

## License
This project is licensed under the **MIT License**. See the `LICENSE` file for details.

## Acknowledgments
- Swing for the GUI framework.
- Apache Maven for build automation.
- Contributors who provided feedback and bug reports.