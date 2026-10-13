# Music Playlist Manager

## Project Overview

This repository contains a Java-based Music Playlist Manager application that allows users to create, manage, and play music playlists. The application features audio playback functionality, playlist management, sorting, searching, and backup capabilities.

## Project Structure

### Core Classes

#### 1. **AudioPlayer**
The main audio playback component responsible for:
- Playing/pausing audio tracks
- Managing playlist navigation (skip forward/backward)
- Updating GUI elements (timer, progress sliders)
- Supporting audio looping and shuffling
- Real-time track information display

**Key Features:**
- Thread-based audio playback
- GUI integration with Java Swing components
- Track selection and playlist management
- Audio control buttons and progress tracking

#### 2. **MusicPlaylistManager**
The primary application controller and GUI manager that:
- Manages the main user interface layout
- Handles playlist creation, saving, and loading
- Provides track management operations
- Implements sorting functionality
- Manages application lifecycle (open, save, exit)
- Coordinates between different components

**Key Features:**
- Comprehensive playlist management interface
- Track table with sorting capabilities
- Playlist metadata management
- Integration with backup and audio components

#### 3. **MyPlaylist**
Represents a music playlist with:
- Track storage and management
- Playlist metadata (name, description)
- Persistence operations (save/load)
- Playlist analysis (count, duration)
- Track manipulation (add, remove, swap)

**Key Features:**
- Dynamic track array management
- File-based persistence (WPL format)
- Playlist statistics tracking
- Track indexing and retrieval

#### 4. **Track**
Represents an individual music track with:
- Metadata (title, artist, album, duration)
- Audio file references
- Album artwork support
- Hash-based data structures integration

**Key Features:**
- Complete metadata storage
- Audio file handling
- Hash and equality implementations
- Integration with search and sorting features

### Supporting Classes

#### Data Structures and Algorithms

- **BinaryTrackTree**: Binary search tree implementation for track searching
- **MyHashTable**: Custom hash table implementation for track storage and retrieval
- **MySet**: Set implementation for track collections
- **QuickSort**: Sorting implementation for playlist tracks

#### Utility Classes

- **BackupManager**: Handles application settings backup and restoration
- **Track**: Maintains track metadata and audio file information

### Test Files

- **DataStructureSmokeTest**: Basic functionality tests for data structures

## Key Features

### Audio Playback
- Real-time audio playback with play/pause controls
- Skip forward/backward navigation
- Progress tracking with visual sliders
- Loop and shuffle modes
- Thread-safe audio operations

### Playlist Management
- Create new playlists with customizable names and descriptions
- Import existing playlists (WPL format)
- Add/remove tracks from playlists
- Save playlists to files
- View playlist statistics (track count, total duration)

### Search and Navigation
- Search functionality with real-time filtering
- Track title and metadata search
- Playlist browsing with file selection
- Jump to specific tracks

### Organization and Sorting
- Multiple sorting options (title, artist, album, duration)
- Track reordering capabilities
- Index-based track management
- Efficient track retrieval by ID

### Persistence
- Automatic playlist saving
- Settings backup management
- File-based playlist storage
- Cross-session playlist recovery

## Technology Stack

- **Java Swing**: GUI framework for user interface
- **Java AWT**: Additional GUI components and event handling
- **Custom Data Structures**: Hash tables, sets, trees, and sorting algorithms
- **File I/O**: Playlist persistence and audio file management
- **Threading**: Audio playback and UI responsiveness

## Installation and Usage

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Java Swing supported environment

### Building the Project
1. Clone this repository
2. Navigate to the project directory
3. Compile using: `javac src/*.java`
4. Run the main application class

### Running the Application
1. Execute the MusicPlaylistManager class
2. Use the main interface to:
   - Create new playlists
   - Load existing playlists
   - Manage tracks in the playlist
   - Play audio tracks
   - Search and filter tracks

### Key Controls
- **Playlist Panel**: Left side for playlist management
- **Track Table**: Center for viewing and managing tracks
- **Audio Player**: Bottom for playback controls
- **Toolbars**: For quick access to common operations

## Design Patterns and Architecture

### Observer Pattern
- AudioPlayer notifies GUI components of playback state changes
- Playlist updates propagate to interested components

### Model-View-Controller (MVC)
- **Model**: MyPlaylist, Track classes
- **View**: Swing components (JTable, JPanel, etc.)
- **Controller**: MusicPlaylistManager

### Command Pattern
- Audio playback and navigation operations encapsulated as commands
- GUI actions mapped to specific methods

### Composite Pattern
- Playlist management through composite track structures

## Customization and Extension

### Adding New Features
1. Extend existing classes (e.g., AudioPlayer for new audio formats)
2. Implement new sorting algorithms
3. Add new data structure implementations
4. Extend the GUI with new components

### Modifying Existing Features
- Change audio playback behavior in AudioPlayer
- Modify playlist format in MyPlaylist
- Adjust sorting algorithms in QuickSort
- Customize data structures in MyHashTable or BinaryTrackTree

## Testing and Validation

### Testing Strategy
- **DataStructureSmokeTest**: Basic functionality verification
- Unit tests for individual classes
- Integration tests for component interactions
- Manual testing for GUI functionality

### Common Test Scenarios
- Track addition and removal
- Playlist save/load operations
- Audio playback controls
- Sorting functionality
- Search and filtering

## Troubleshooting

### Common Issues
1. **Audio playback problems**: Check file formats and dependencies
2. **GUI not displaying**: Verify Java version and Swing compatibility
3. **Playlist loading errors**: Check file format and path handling
4. **Memory issues**: Monitor large playlist sizes and track counts

### Error Handling
- Graceful handling of missing audio files
- Validation of playlist formats
- Thread safety for audio operations
- Exception handling for file operations

## Contributing

### Code Quality Guidelines
- Follow existing coding standards
- Maintain thread safety for shared resources
- Implement proper error handling
- Add comprehensive documentation

### Adding New Classes
- Place new classes in the `src` directory
- Follow existing naming conventions
- Update MusicPlaylistManager if needed for integration
- Ensure proper error handling and documentation

This project provides a comprehensive foundation for music playlist management with extensible architecture for future enhancements and customizations.