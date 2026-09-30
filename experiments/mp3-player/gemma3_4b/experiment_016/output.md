```markdown
# Audio Player and Playlist Manager

## Description
This Java project implements an audio player with playlist management capabilities. It allows users to browse a music library, create playlists, play and pause tracks, skip forward and backward, and manage the playback settings. The project utilizes various data structures like hashtables and trees for efficient track management and includes features for backups and playlist saving.

---

## Classes

### AudioPlayer

The central class controlling the audio playback functionality, managing tracks, playlists, and user interface interactions.

*   **Fields:**
    *   `fileInpstream: FileInputStream`
    *   `totalLength: long`
    *   `playerInstance: Player`
    *   `playerThread: Thread`
    *   `timeSlider: JSlider`
    *   `currentTrack: Track`
    *   `myPlaylist: MyPlaylist`
    *   `fieldTrackTitle: JTextField`
    *   `skipBackTrack: JButton`
    *   `skipForwardTrack: JButton`
    *   `playPauseButton: JButton`
    *   `albumLabel: JLabel`
    *   `loopButton: JButton`
    *   `isLooping: boolean`
    *   `searchButton: JButton`
    *   `searchField: JTextField`
    *   `shuffleButton: JButton`

*   **Methods:**
    *   `mouseReleased(MouseEvent e)`
    *   `shouldScroll(int dir)`
    *   `run()`
    *   `run()`
    *   `actionPerformed(ActionEvent e)`
    *   `playOrPause()`
    *   `skipForwardTrack()`
    *   `splitCurrentTrackAndWriteToFile(File outputFile, boolean isToSplit)`
    *   `updateList(MyPlaylist mplaylist)`
    *   `refreshGUI()`
    *   `updateTimerProgress()`
    *   `getCurrentTrack()`
    *   `setCurrentTrack(Track track)`
    *   `getPlayList()`

### BackupManager

Handles the backup functionality, saving and restoring default directory settings.

*   **Fields:**
    *   `currentFile: File`
    *   `defaultBrowDirectory: String`
    *   `defaultDirectory: String`

*   **Methods:**
    *   `saveDefaultsToFile()`
    *   `getDefaultDirectory()`
    *   `setDefaultDirectory(String directory)`
    *   `setDefaultBrowserDir(String path)`
    *   `getDefaultBrowseDirectory()`

### BinaryTrackTree

A tree-based data structure to store and manage tracks, potentially optimized for searching or retrieval. (Details of the implementation not provided, but its core functionality is to organize tracks efficiently).

*   **Fields:**
    *   (None explicitly listed)

*   **Methods:**
    *   `find(TreeNode node, String title)`

### MusicPlaylistManager

Manages the playlist creation and editing interface, including track management and display.

*   **Fields:**
    *   `albumPanel: JPanel`
    *   `backupManager: BackupManager`
    *   `playlistObject: MyPlaylist`
    *   `songTracksTable: JTable`
    *   `trackTableModel: DefaultTableModel`
    *   `nameTextField: JTextField`
    *   `descriptionTextField: JTextField`
    *   `playlistDescLabelValue: JLabel`
    *   `playlistCountLabelValue: JLabel`
    *   `playlistDurationLabelValue: JLabel`
    *   `playlistNameLabelValue: JLabel`
    *   `addTrackButton: JButton`
    *   `removeTrackButton: JButton`
    *   `createPlayListButton: JButton`
    *   `openExistingButton: JButton`
    *   `savePlayListButton: JButton`
    *   `exitAppButton: JButton`
    *   `audioPlayer: AudioPlayer`
    *   `sortButton: JButton`
    *   `sortByCombo: JComboBox<String>`

*   **Methods:**
    *   `populateLeftPanel(JPanel leftPanel)`
    *   `windowClosing(WindowEvent evt)`
    *   `isCellEditable(int row, int column)`
    *   `itemStateChanged(ItemEvent arg0)`
    *   `actionPerformed(ActionEvent e)`
    *   `openPlayList()`
    *   `createPlaylist(File playFile)`
    *   `refresh()`
    *   `addToList(File selectedFile)`
    *   `addTracks()`
    *   `updateTrackNumbers()`
    *   `removeTracks()`
    *   `savePlayList()`
    *   `closeApp()`
    *   `textFieldListener(JTextField tf)`
    *   `removeUpdate(DocumentEvent e)`
    *   `insertUpdate(DocumentEvent e)`
    *   `changedUpdate(DocumentEvent e)`
    *   `run()`

### MyHashTable

A hash table implementation for storing and retrieving tracks based on their IDs or other keys.

*   **Fields:**
    *   `size: int`
    *   `bucketCount: int`

*   **Methods:**
    *   `size()`
    *   `isEmpty()`
    *   `remove(K key)`
    *   `get(K key)`
    *   `add(K key, V value)`

### MyPlaylist

Represents a playlist, storing a collection of tracks and providing functionality for managing the playlist.

*   **Fields:**
    *   `description: String`
    *   `name: String`
    *   `playlistFile: File`
    *   `tracks: ArrayList<Track>`

*   **Methods:**
    *   `savePlayList(String file)`
    *   `wplParser()`
    *   `checkSaved()`
    *   `getName()`
    *   `getDescription()`
    *   `setName(String name)`
    *   `setDescription(String description)`
    *   `getTracks()`
    *   `getIndexOf(Track track)`
    *   `getTrackById(int id)`
    *   `getById(int index)`
    *   `getLast()`
    *   `getPlayListFile()`
    *   `setPlayListFile(File file)`
    *   `getCount()`
    *   `getDuration()`
    *   `add(Track track)`
    *   `remove(int id)`
    *   `getTrackArray()`
    *   `updateTracks(Track[] ts)`
    *   `swap(int index1, int index2)`

### MySet

A set implementation to store unique tracks, likely utilizing a hash table. (Details of the implementation are not provided).

*   **Fields:**
    *   (None explicitly listed)

*   **Methods:**
    *   `add(Object object)`
    *   `iterator()`
    *   `size()`

### QuickSort

A quicksort algorithm for sorting tracks based on their properties (e.g., title, artist). (Details of implementation not provided).

*   **Fields:**
    *   (None explicitly listed)

*   **Methods:**
    *   `sort(Track array[], int start, int end)`

### Track

Represents a single music track, containing details such as title, artist, album, and duration.

*   **Fields:**
    *   `albumImage: BufferedImage`
    *   `trackFile: File`
    *   `titleStr: String`
    *   `artistStr: String`
    *   `albumStr: String`
    *   `duration: long`

*   **Methods:**
    *   `getArtist()`
    *   `getTitle()`
    *   `getDuration()`
    *   `getAlbumArt()`
    *   `getAlbum()`
    *   `getTrackFile()`
    *   `hashCode()`
    *   `equals(Object obj)`

### DataStructureSmokeTest

A test class to perform basic smoke tests on the data structures used in the project. (No details on tests provided).

*   **Fields:**
    *   (None explicitly listed)

*   **Methods:**
    *   (None explicitly listed)
```