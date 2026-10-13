# Music Playlist Manager

A desktop application built with Java Swing that provides a full-featured music player and playlist management system. It leverages several custom data structures (hash tables, trees, sets, quicksort) to efficiently store, search, sort, and organize music tracks. The application also includes backup functionality for settings and supports audio playback with a GUI.

---

## Features

- **Playlist Creation & Management** – Create new playlists, add/remove tracks, view track details (title, artist, album, duration, artwork).
- **Audio Playback** – Play, pause, skip tracks, loop, shuffle, and view real‑time playback progress.
- **Search & Sort** – Search tracks by title, artist, or album; sort playlists by various criteria (title, artist, album, duration).
- **Backup & Restore** – Save/restore user preferences and default directories.
- **Data‑Structure Exercises** – Demonstrates the use of custom `MyHashTable`, `BinaryTrackTree`, `MySet`, and `QuickSort` implementations in a real‑world context.
- **Unit Tests** – Smoke tests for the core data structures.

---

## Prerequisites

- **Java Development Kit (JDK) 8 or higher** (JRE for running the application).
- No external libraries are required beyond the standard JDK.

---

## Building & Running

### 1. Compile the Source Code

```bash
# From the project root
javac -d out src/**/*.java
```

*(If you prefer Maven/Gradle, a simple `pom.xml` or `build.gradle` can be added later.)*

### 2. Run the Application

```bash
java -cp out com.example.musicplaylistmanager.Main
```

*The main class is `MusicPlaylistManager` (in `src/MusicPlaylistManager.java`). Adjust the package name if needed.*

### 3. Run the Data‑Structure Smoke Tests

```bash
java -cp out test.java.DataStructureSmokeTest
```

---

## Project Structure

```
src/
├─ AudioPlayer/               # GUI audio player component
│   └─ AudioPlayer.java
├─ BackupManager.java         # Handles backup of user defaults
├─ BinaryTrackTree.java       # Binary tree for searching tracks
├─ MusicPlaylistManager.java  # Main UI & controller
├─ MyHashTable.java           # Custom hash‑table implementation
├─ MyPlaylist.java            # Playlist model (list of tracks)
├─ MySet.java                 # Custom set implementation
├─ QuickSort.java             # Quick‑sort algorithm for tracks
├─ Track.java                 # Model for a single music track
└─ test/
    └─ java/
        └─ DataStructureSmokeTest.java   # Smoke tests for data structures
```

### Key Classes (brief overview)

| Class | Responsibility |
|-------|----------------|
| **AudioPlayer** | Provides playback controls, progress slider, and audio visualization. |
| **BackupManager** | Persists user preferences (default directories) to a file. |
| **BinaryTrackTree** | Efficiently searches a collection of tracks by title. |
| **MusicPlaylistManager** | Main window – creates/opens/saves playlists, invokes the `AudioPlayer`, and coordinates UI events. |
| **MyHashTable** | Hash‑based storage for tracks (used for quick look‑ups). |
| **MyPlaylist** | Holds a list of `Track` objects, supports CRUD operations, and can serialize/deserialize playlists. |
| **MySet** | Unordered collection of unique tracks (used internally by the playlist). |
| **QuickSort** | Sorts an array of `Track` objects (used for sorting in the UI). |
| **Track** | Represents a single audio file (metadata, artwork, file reference). |
| **DataStructureSmokeTest** | Simple unit tests that verify the basic behavior of the custom data structures. |

---

## Using the Application

### 1. Creating a New Playlist

1. Click **“Create Playlist”** button.
2. Enter a name and optional description.
3. Click **“Create”** – a new empty playlist appears in the left‑hand panel.

### 2. Adding Tracks

1. Select a playlist.
2. Click **“Add Tracks”** → browse for audio files (`.mp3`, `.wav`, etc.).
3. Selected tracks are added to the playlist table with their metadata.

### 3. Managing a Playlist

- **Remove Track** – select one or more rows and click **“Remove Track”**.
- **Sort** – use the **Sort** button and **Sort By** combo box to order tracks.
- **Search** – type in the search field to filter the playlist table in real time.

### 4. Playing Music

1. Select a track in any playlist.
2. Click **Play** (or press **Space**) to start playback.
3. Use the playback controls (Play/Pause, Skip ◀ / ►, Loop, Shuffle) provided by the `AudioPlayer` panel.

### 5. Saving & Loading Playlists

- **Save Playlist** – choose a location to store the playlist (`.wpl` format).
- **Open Playlist** – load an existing playlist file and populate the UI.

### 6. Backup & Defaults

- The **Backup** menu (under `File`) lets you save/restore user preferences (default directories for opening/saving files).

### 7. Exiting

- Click **“Exit”** button or press **Ctrl+Q** to close the application cleanly (the app will prompt to save any unsaved changes).

---

## Testing

The project includes a simple smoke‑test suite (`DataStructureSmokeTest.java`) that validates the basic contract of the custom data structures (`MyHashTable`, `BinaryTrackTree`, `MySet`, `QuickSort`). Run the tests with:

```bash
java -cp out test.java.DataStructureSmokeTest
```

All tests should pass. For a more comprehensive test coverage you can later integrate JUnit or TestNG.

---

## Contributing

If you’d like to contribute, please:

1. Fork the repository.
2. Create a feature branch (`git checkout -b feature/awesome-feature`).
3. Commit your changes (`git commit -a`).
4. Push to the branch (`git push origin feature/awesome-feature`).
5. Open a Pull Request.

Please ensure your code follows the existing Java conventions and includes minimal, focused commits.

---

## License

This project is open‑source software licensed under the **MIT License**. See the `LICENSE` file for details.

---

### Contact

For questions or issues, please open an issue in the repository or contact the project maintainer at **maintainer@example.com**.