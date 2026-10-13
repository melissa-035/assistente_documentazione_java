```java
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Main class for the Music Playlist Manager application.
 * This class provides a GUI for creating, editing, saving, and managing music playlists.
 * It allows users to add/remove tracks, sort tracks, save and open playlists, and play audio.
 *
 * @author Your Name
 * @version 1.0
 */
public class MusicPlaylistManager extends JFrame implements ActionListener {

    /**
     * Panel for displaying album art.
     */
    private JPanel albumPanel;

    /**
     * Manages backup and default directory settings for playlists.
     */
    private BackupManager backupManager;

    /**
     * Object representing the current playlist.
     */
    private MyPlaylist playlistObject;

    /**
     * Table for displaying tracks in the current playlist.
     */
    private JTable songTracksTable;

    /**
     * Model for the song tracks table.
     */
    private DefaultTableModel trackTableModel;

    /**
     * Text field for entering playlist name.
     */
    private JTextField nameTextField;

    /**
     * Text field for entering playlist description.
     */
    private JTextField descriptionTextField;

    /**
     * Label for displaying playlist description.
     */
    private JLabel playlistDescLabelValue;

    /**
     * Label for displaying playlist track count.
     */
    private JLabel playlistCountLabelValue;

    /**
     * Label for displaying playlist total duration.
     */
    private JLabel playlistDurationLabelValue;

    /**
     * Label for displaying playlist name.
     */
    private JLabel playlistNameLabelValue;

    /**
     * Button for adding tracks to the playlist.
     */
    private JButton addTrackButton;

    /**
     * Button for removing selected tracks from the playlist.
     */
    private JButton removeTrackButton;

    /**
     * Button for creating a new playlist.
     */
    private JButton createPlayListButton;

    /**
     * Button for opening an existing playlist.
     */
    private JButton openExistingButton;

    /**
     * Button for saving the current playlist.
     */
    private JButton savePlayListButton;

    /**
     * Button for exiting the application.
     */
    private JButton exitAppButton;

    /**
     * Audio player component for playing tracks.
     */
    private AudioPlayer audioPlayer;

    /**
     * Button for sorting tracks.
     */
    private JButton sortButton;

    /**
     * Combo box for selecting sort criteria.
     */
    private JComboBox<String> sortByCombo;


    /**
     * Populates the left panel with playlist creation UI components.
     *
     * @param leftPanel The JPanel to populate with UI components.
     */
    public void populateLeftPanel(JPanel leftPanel) {
        JPanel newPlaylistPanel = new JPanel();
        newPlaylistPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Create New",
                TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        newPlaylistPanel.setBounds(30, 20, 250, 170);
        leftPanel.add(newPlaylistPanel);
        newPlaylistPanel.setLayout(null);

        JLabel playlistNameLbl = new JLabel("Playlist Name");
        playlistNameLbl.setBounds(10, 18, 100, 14);
        newPlaylistPanel.add(playlistNameLbl);
        nameTextField = new JTextField();
        nameTextField.setBounds(120, 14, 100, 30);
        newPlaylistPanel.add(nameTextField);
        nameTextField.setColumns(10);
        textFieldListener(nameTextField);

        JLabel descLabel = new JLabel("Description");
        descLabel.setBounds(10, 54, 100, 14);
        newPlaylistPanel.add(descLabel);
        descriptionTextField = new JTextField();
        descriptionTextField.setBounds(120, 54, 100, 30);
        newPlaylistPanel.add(descriptionTextField);
        descriptionTextField.setColumns(10);
        textFieldListener(descriptionTextField);

        createPlayListButton = new JButton("Create");
        createPlayListButton.setBounds(50, 96, 150, 30);
        createPlayListButton.addActionListener(this);
        newPlaylistPanel.add(createPlayListButton);

        openExistingButton = new JButton("Open Existing Playlists");
        openExistingButton.setBounds(20, 200, 200, 30);
        openExistingButton.addActionListener(this);
        leftPanel.add(openExistingButton);

        savePlayListButton = new JButton("Save Playlists");
        savePlayListButton.setBounds(20, 240, 200, 30);
        savePlayListButton.addActionListener(this);
        leftPanel.add(savePlayListButton);

        exitAppButton = new JButton("Exit Application");
        exitAppButton.setBounds(20, 280, 200, 30);
        exitAppButton.addActionListener(this);
        leftPanel.add(exitAppButton);
    }

    /**
     * Constructs the main MusicPlaylistManager UI.
     */
    public MusicPlaylistManager() {
        this.playlistObject = new MyPlaylist();
        this.backupManager = new BackupManager();
        setTitle("Music Playlist Manager");
        setResizable(false);

        try {
            String laf = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
            UIManager.put("Slider.focus", UIManager.get("Slider.background"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLocationRelativeTo(null);
        setBounds(100, 100, 800, 580);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                closeApp();
            }
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(mainPanel);
        mainPanel.setLayout(null);

        JPanel rightPanel = new JPanel();
        rightPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Song List",
                TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        rightPanel.setBounds(340, 90, 450, 260);
        mainPanel.add(rightPanel);
        rightPanel.setLayout(null);

        songTracksTable = new JTable();
        trackTableModel = new DefaultTableModel(new Object[][] {}, new String[] { "Id ", "Artist", "Title","Duration" }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        songTracksTable.setModel(trackTableModel);
        songTracksTable.getColumnModel().getColumn(0).setPreferredWidth(60);
        songTracksTable.getColumnModel().getColumn(0).setMaxWidth(60);
        songTracksTable.setColumnSelectionAllowed(false);
        songTracksTable.setFillsViewportHeight(true);
        songTracksTable.setRowSelectionAllowed(true);

        JScrollPane scrollPane1 = new JScrollPane(songTracksTable);
        scrollPane1.setBounds(10, 20, 425, 200);
        scrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        rightPanel.add(scrollPane1);

        removeTrackButton = new JButton("Remove");
        removeTrackButton.setBounds(350, 228, 89, 23);
        removeTrackButton.addActionListener(this);
        rightPanel.add(removeTrackButton);

        JPanel leftPanel = new JPanel();
        leftPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Playlists",
                TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        leftPanel.setBounds(10, 12, 320, 470);
        mainPanel.add(leftPanel);
        leftPanel.setLayout(null);

        addTrackButton = new JButton("Add");
        addTrackButton.setBounds(240, 228, 89, 23);
        addTrackButton.addActionListener(this);
        rightPanel.add(addTrackButton);

        sortButton = new JButton("Sort");
        sortButton.setBounds(130, 228, 89, 23);
        sortButton.addActionListener(this);
        rightPanel.add(sortButton);

        String filters[] = {"Sort by", "artist", "title", "duration"};
        sortByCombo = new JComboBox<String>(filters);
        sortByCombo.setBounds(20, 228, 89, 23);
        rightPanel.add(sortByCombo);

        sortByCombo.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent arg0) {
                //sortTable();
            }
        });


        populateLeftPanel(leftPanel);

        JPanel rightTopPanel = new JPanel();
        rightTopPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Playlist",
                TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        rightTopPanel.setBounds(340, 12, 450, 65);
        mainPanel.add(rightTopPanel);
        rightTopPanel.setLayout(null);

        JLabel pLabelName = new JLabel("Name : ");
        pLabelName.setBounds(10, 18, 80, 15);
        rightTopPanel.add(pLabelName);
        playlistNameLabelValue = new JLabel();
        playlistNameLabelValue.setBounds(120, 14, 150, 20);
        rightTopPanel.add(playlistNameLabelValue);

        JLabel descLabelText = new JLabel("Description : ");
        descLabelText.setBounds(10, 45, 120, 14);
        rightTopPanel.add(descLabelText);
        playlistDescLabelValue = new JLabel();
        playlistDescLabelValue.setBounds(140, 40, 140, 20);
        rightTopPanel.add(playlistDescLabelValue);

        JLabel countLabel = new JLabel("Count : ");
        countLabel.setBounds(280, 16, 80, 14);
        rightTopPanel.add(countLabel);
        playlistCountLabelValue = new JLabel();
        playlistCountLabelValue.setText("0");
        playlistCountLabelValue.setBounds(340, 12, 75, 20);
        rightTopPanel.add(playlistCountLabelValue);

        JLabel durLabel = new JLabel("Duration : ");
        durLabel.setBounds(280, 40, 80, 14);
        rightTopPanel.add(durLabel);
        playlistDurationLabelValue = new JLabel();
        playlistDurationLabelValue.setText("0s");
        playlistDurationLabelValue.setBounds(360, 36, 75, 20);
        rightTopPanel.add(playlistDurationLabelValue);

        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Music Player",
                TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panel.setBounds(340, 360, 360, 180);
        mainPanel.add(panel);
        panel.setLayout(null);

        albumPanel = new JPanel();
        albumPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING,
                TitledBorder.TOP, null, new Color(0, 0, 0)));
        albumPanel.setBounds(700, 365, 80, 80);
        mainPanel.add(albumPanel);
        albumPanel.setLayout(null);

        JLabel artLabel = new JLabel();
        artLabel.setBounds(4, 4, 70, 70);
        albumPanel.add(artLabel);

        audioPlayer = new AudioPlayer(this.playlistObject, artLabel);
        audioPlayer.setBounds(15, 15, 330, 150);
        panel.add(audioPlayer);
    }

    /**
     * Handles action events from UI buttons.
     *
     * @param e The ActionEvent object.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object currentSource = e.getSource();

        if (currentSource == addTrackButton) {
            addTracks();
        } else if (currentSource == removeTrackButton) {
            removeTracks();
        } else if ((currentSource == savePlayListButton) || (currentSource == createPlayListButton)) {
            savePlayList();
        } else if (currentSource == exitAppButton) {
            closeApp();
        } else if (currentSource == openExistingButton) {
            openPlayList();
        }
        else if (currentSource == sortButton) {
            sortTable();
        }
    }

    /**
     * Sorts the tracks in the playlist based on the selected criteria.
     */
    private void sortTable() {
        int rows = trackTableModel.getRowCount();
        for(int i = rows - 1; i >=0; i--)
        {
            trackTableModel.removeRow(i);
        }

        Track tracks[] = this.playlistObject.getTrackArray();
        String filter = sortByCombo.getSelectedItem().toString();
        if (filter.equals("artist")) {
            QuickSort qs = new QuickSort("artist");
            qs.sort(tracks, 0, tracks.length-1);
        }
        else if (filter.equals("title")) {
            QuickSort qs = new QuickSort("title");
            qs.sort(tracks, 0, tracks.length-1);
        }
        else if (filter.equals("duration")) {
            QuickSort qs = new QuickSort("duration");
            qs.sort(tracks, 0, tracks.length-1);
        }

        int id = 0;
        for (Track track : tracks) {
            trackTableModel.addRow(
                    new Object[] { id+ 1, track.getArtist(), track.getTitle(), track.getDuration() });
            id++;
        }

        MyPlaylist currentPlayList = audioPlayer.getPlayList();
        if (currentPlayList != null) {
            currentPlayList.updateTracks(tracks);
            audioPlayer.updateList(currentPlayList);
        }
    }

    /**
     * Opens an existing playlist file selected by the user.
     */
    public void openPlayList() {
        JFileChooser jFileChooser = new JFileChooser(new File(
                ((this.backupManager.getDefaultDirectory() != null) ? this.backupManager.getDefaultDirectory()
                        : "")));
        FileNameExtensionFilter feFilter = new FileNameExtensionFilter(null, "wpl");
        jFileChooser.setFileFilter(feFilter);
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (jFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            if (jFileChooser.getSelectedFile().getName().endsWith(".wpl")) {
                this.createPlaylist(jFileChooser.getSelectedFile());
                this.backupManager.setDefaultDirectory(jFileChooser.getSelectedFile().getParent());
            }
        }
    }

    /**
     * Creates a playlist from a file.
     *
     * @param playFile The file to load the playlist from.
     */
    public void createPlaylist(File playFile) {
        if (!this.playlistObject.checkSaved()) {
            int createDialog = JOptionPane.showConfirmDialog(this, "Would you like to save the current playlist?");
            if (createDialog == JOptionPane.YES_OPTION) {
                boolean result = this.savePlayList();
                if (!result) {
                    return;
                }
            }
            else if (createDialog == JOptionPane.CANCEL_OPTION || createDialog == JOptionPane.CLOSED_OPTION) {
                return;
            }
        }

        if (playFile != null) {
            try {
                this.playlistObject = new MyPlaylist(playFile);
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this, "The selected file does not exist" + "\n" + playFile.getAbsolutePath(), "File Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            this.playlistObject = new MyPlaylist();
        }
        this.refresh();
        this.audioPlayer.updateList(this.playlistObject);
    }

    /**
     * Refreshes the UI components to reflect the current state of the playlist.
     */
    public void refresh() {
        this.nameTextField.setText(this.playlistObject.getName());
        this.descriptionTextField.setText(this.playlistObject.getDescription());
        this.playlistCountLabelValue.setText(String.valueOf(this.playlistObject.getCount()));
        this.playlistDurationLabelValue.setText(this.playlistObject.getDuration());
        int numRows = this.songTracksTable.getModel().getRowCount();
        for (int i = 0; i < numRows; i++) {
            ((DefaultTableModel) this.songTracksTable.getModel()).removeRow(0);
        }

        int id = 0;
        for (Track track : this.playlistObject.getTracks()) {
            trackTableModel.addRow(
                    new Object[] {  id+ 1, track.getArtist(), track.getTitle(), track.getDuration() });
            id++;
        }
    }

    /**
     * Adds a track from a selected file to the playlist.
     *
     * @param selectedFile The file to add as a track.
     */
    public void addToList(File selectedFile) {

        if (selectedFile.getName().toLowerCase().endsWith(".mp3")) {
            try {
                Track track = new Track(selectedFile.getAbsolutePath());
                this.playlistObject.add(track);
                if (this.playlistObject.getCount() == 1) {
                    this.audioPlayer.setCurrentTrack(track);
                    this.audioPlayer.refreshGUI();
                }
                trackTableModel.addRow(new Object[] { this.playlistObject.getCount(), track.getArtist(), track.getTitle() });
                refresh();
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this, "The selected file does not exist" + "\n" + selectedFile.getAbsolutePath(), "File Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select an MP3 audio file.", "Unsupported File", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Opens a file chooser dialog to add multiple MP3 tracks to the playlist.
     */
    public void addTracks() {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setDialogTitle("Add MP3 files");
        fileChooser.setFileFilter(new FileNameExtensionFilter("MP3 audio", "mp3"));
        fileChooser.setMultiSelectionEnabled(true);
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            for (File selectedFile : fileChooser.getSelectedFiles()) {
                addToList(selectedFile);
            }
        }
    }

    /**
     * Updates the row numbers in the tracks table.
     */
    public void updateTrackNumbers() {
        for (int k = 0; k < this.songTracksTable.getModel().getRowCount(); k++) {
            this.songTracksTable.getModel().setValueAt((k + 1), k, 0);
        }
    }

    /**
     * Removes selected tracks from the playlist.
     */
    public void removeTracks() {
        int numRows = this.songTracksTable.getSelectedRowCount();
        for (int i = 0; i < numRows; i++) {
            int id = this.songTracksTable.getSelectedRows()[0];
            this.playlistObject.remove(id);
            ((DefaultTableModel) this.songTracksTable.getModel()).removeRow(id);
        }
        refresh();
    }

    /**
     * Saves the current playlist to a file.
     *
     * @return true if the playlist was saved successfully, false otherwise.
     */
    public boolean savePlayList() {
        if (playlistObject.getPlayListFile() == null) {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setSelectedFile(new File(playlistObject.getName() + ".wpl"));
            if (jFileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File sFile = jFileChooser.getSelectedFile();
                if (sFile.getAbsolutePath().endsWith(MyPlaylist.WPL_STR)) {
                    if (sFile.exists()) {
                        int confirmResult = JOptionPane.showConfirmDialog(this,
                                sFile.getName() + " exists already. Replace?", "Confirmation",
                                JOptionPane.YES_NO_OPTION);
                        if (!(confirmResult == JOptionPane.YES_OPTION)) {
                            return false;
                        }
                    }
                    this.playlistObject.setPlayListFile(jFileChooser.getSelectedFile().getAbsoluteFile());
                }
            } else {
                return false;
            }
        }
        this.playlistObject.savePlayList(MyPlaylist.WPL_STR);
        JOptionPane.showMessageDialog(this, "Playlist saved.");
        return true;
    }

    /**
     * Closes the application, prompting to save the current playlist if needed.
     */
    public void closeApp() {
        if (!this.playlistObject.checkSaved()) {
            int closeDialog = JOptionPane.showConfirmDialog(this,
                    "Do you want to save current playlist?");

            if (closeDialog == JOptionPane.YES_OPTION) {
                boolean result = this.savePlayList();
                if (!result) {
                    return;
                }
            }
            else if (closeDialog == JOptionPane.CANCEL_OPTION || closeDialog == JOptionPane.CLOSED_OPTION) {
                return;
            }
        }
        this.backupManager.saveDefaultsToFile();
        System.exit(0);
    }

    /**
     * Adds a DocumentListener to a text field to trigger an action when the text changes.
     *
     * @param tf The JTextField to add the listener to.
     */
    public void textFieldListener(JTextField tf) {
        DocumentListener listener = new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                onNewPlayList(tf);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                onNewPlayList(tf);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                onNewPlayList(tf);
            }
        };
        tf.getDocument().addDocumentListener(listener);
    }

    /**
     * Called when the playlist name or description text fields are modified.
     *
     * @param textField The text field that was modified.
     */
    private void onNewPlayList(JTextField textField) {
        if (textField == this.nameTextField) {
            this.playlistObject.setName(this.nameTextField.getText());
            playlistNameLabelValue.setText(this.nameTextField.getText());
        } else if (textField == this.descriptionTextField) {
            this.playlistObject.setDescription(this.descriptionTextField.getText());
            playlistDescLabelValue.setText(this.descriptionTextField.getText());
        }
    }

    /**
     * Main entry point for the application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MusicPlaylistManager manager = new MusicPlaylistManager();
                    manager.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}