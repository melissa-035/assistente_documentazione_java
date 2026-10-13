```java
/**
 * The AudioPlayer class is a JPanel-based graphical user interface component 
 * responsible for playing audio files, managing playback controls, and handling 
 * playlist operations such as shuffling, looping, and searching. It integrates 
 * with the Javazoom library for MP3 playback and allows users to split audio tracks.
 * 
 * @author AI Generated
 * @version 1.0
 * @since 2023
 */
@SuppressWarnings("serial")
public class AudioPlayer extends JPanel implements ActionListener
{
    /**
     * Image icon for the shuffle button, scaled to 20x20 pixels.
     */
    private final Image SHUFFLE_IMAGE = (new ImageIcon(getClass().getResource("/images/shuffle.png"))).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);

    /**
     * Image icon for the loop button, scaled to 20x20 pixels.
     */
    private final Image LOOP_IMAGE = (new ImageIcon(getClass().getResource("/images/loop.png"))).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);

    /**
     * Image icon for the play button, scaled to 12x12 pixels.
     */
    private final Image PLAY_IMAGE = (new ImageIcon(getClass().getResource("/images/play_button.png"))).getImage().getScaledInstance(12, 12, Image.SCALE_SMOOTH);

    /**
     * Image icon for the pause button, scaled to 12x12 pixels.
     */
    private final Image PAUSE_IMAGE = (new ImageIcon(getClass().getResource("/images/pause_button.png"))).getImage().getScaledInstance(12, 12, Image.SCALE_SMOOTH);

    /**
     * Default album art icon displayed when no track is playing or available.
     */
    private final Image DEFAULT_ALBUM_ART_ICON = (new ImageIcon(getClass().getResource("/images/album.png"))).getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);

    /**
     * Input stream for reading the current audio file.
     */
    private FileInputStream fileInpstream;

    /**
     * Total length (in bytes) of the currently loaded audio file.
     */
    private long totalLength;

    /**
     * Flag indicating whether the audio is currently paused.
     */
    private volatile boolean isPausedFlag = false;

    /**
     * The Javazoom Player instance responsible for decoding and playing audio.
     */
    private Player playerInstance;

    /**
     * Thread dedicated to running the audio player.
     */
    private Thread playerThread;

    /**
     * Slider component used to seek to different positions within the audio track.
     */
    private JSlider timeSlider;

    /**
     * The Track object currently being played or loaded.
     */
    private Track currentTrack;

    /**
     * The playlist containing all available tracks.
     */
    private MyPlaylist myPlaylist;

    /**
     * Text field displaying the title and artist of the current track.
     */
    private JTextField fieldTrackTitle;

    /**
     * Button to skip back to the previous track.
     */
    private JButton skipBackTrack;

    /**
     * Button to skip forward to the next track.
     */
    private JButton skipForwardTrack;

    /**
     * Button to toggle between play and pause states.
     */
    private JButton playPauseButton;

    /**
     * Label used to display the album art of the current track.
     */
    private JLabel albumLabel;

    /**
     * Button to toggle looping mode on or off.
     */
    private JButton loopButton;

    /**
     * Flag indicating whether looping is currently enabled.
     */
    private boolean isLooping;

    /**
     * Text field for entering search queries.
     */
    private JButton searchButton;

    /**
     * Text field for entering search queries.
     */
    private JTextField searchField;

    /**
     * Button to trigger shuffling the playlist.
     */
    private JButton shuffleButton;

    /**
     * Constructs the AudioPlayer panel, initializing UI components, layout, 
     * and event listeners. It sets up the skip back, play/pause, shuffle, loop, 
     * search, and skip forward buttons, as well as the time slider with custom 
     * mouse handling for seeking.
     * 
     * @param myPlaylist The MyPlaylist object containing the tracks to be played.
     * @param albumLabel The JLabel reference where album art will be displayed.
     */
    public AudioPlayer(MyPlaylist myPlaylist, JLabel albumLabel)
    {
        super();
        this.myPlaylist = myPlaylist;
        this.albumLabel = albumLabel;
        this.setLayout(null);
        this.albumLabel.setIcon(new ImageIcon(DEFAULT_ALBUM_ART_ICON));
        Image icon1 = (new ImageIcon(getClass().getResource("/images/back.png"))).getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        skipBackTrack = new JButton(new ImageIcon(icon1));
        skipBackTrack.addActionListener(this);
        skipBackTrack.setBounds(10, 50, 80, 25);
        add(skipBackTrack);

        playPauseButton = new JButton(new ImageIcon(PLAY_IMAGE));
        playPauseButton.addActionListener(this);
        playPauseButton.setBounds(135, 50, 60, 25);
        add(playPauseButton);

        shuffleButton = new JButton(new ImageIcon(SHUFFLE_IMAGE));
        shuffleButton.addActionListener(this);
        shuffleButton.setBounds(10, 125, 60, 25);
        add(shuffleButton);

        loopButton = new JButton(new ImageIcon(LOOP_IMAGE));
        loopButton.addActionListener(this);
        loopButton.setBounds(80, 125, 60, 25);
        add(loopButton);

        searchField = new JTextField();

        searchField.setBounds(150, 125, 80, 25);
        add(searchField);

        searchButton = new JButton("Search");
        searchButton.addActionListener(this);
        searchButton.setBounds(240, 125, 90, 25);
        add(searchButton);

        Image icon2 = (new ImageIcon(getClass().getResource("/images/forward.png"))).getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        skipForwardTrack = new JButton(new ImageIcon(icon2));
        skipForwardTrack.addActionListener(this);
        skipForwardTrack.setBounds(240, 50, 80, 25);
        add(skipForwardTrack);

        fieldTrackTitle = new JTextField();
        fieldTrackTitle.setBorder(BorderFactory.createEmptyBorder());
        fieldTrackTitle.setHorizontalAlignment(SwingConstants.CENTER);
        fieldTrackTitle.setEditable(false);
        fieldTrackTitle.setBackground(SystemColor.menu);
        fieldTrackTitle.setBounds(10, 15, 310, 20);
        add(fieldTrackTitle);

        timeSlider = new JSlider() {
            {
                MouseListener[] mouseListeners = getMouseListeners();
                for (MouseListener l : mouseListeners)
                    removeMouseListener(l);
                final BasicSliderUI baseUI = (BasicSliderUI) getUI();
                BasicSliderUI.TrackListener tlistener = baseUI.new TrackListener()
                {
                    @Override
                    public void mouseReleased(MouseEvent e)
                    {
                        Point point = e.getPoint();
                        int val = baseUI.valueForXPosition(point.x);
                        setValue(val);
                        if (!isPausedFlag)
                        {
                            pauseTrack();
                            setValue(val);
                            resume();
                        }
                    }

                    @Override public boolean shouldScroll(int dir) {
                        return false;
                    }
                };
                addMouseListener(tlistener);
            }
        };

        timeSlider.setValue(0);
        timeSlider.setBounds(10, 95, 310, 25);
        add(timeSlider);
    }

    /**
     * Starts or resumes playback of the current track from a specific byte offset.
     * This method initializes the audio stream, creates a new Player instance, 
     * and starts a background thread for playback. It also handles the logic 
     * for updating the GUI and tracking progress. Note: If the user seeks (drags) 
     * the slider during playback, the player pauses, seeks to the new position, 
     * and resumes automatically.
     * 
     * @param initialValue The byte offset from the start of the track to begin playback from.
     */
    private void playTrack(long initialValue)
    {
        if (this.currentTrack != null)
        {
            this.isPausedFlag = false;
            if (playerInstance != null)
            {
                playerInstance.close();
                playerInstance = null;
            }
            try
            {
                this.fileInpstream = new FileInputStream(this.currentTrack.getTrackFile());
                this.totalLength = this.fileInpstream.available();
                this.timeSlider.setMaximum((int) this.totalLength);
                this.fileInpstream.skip(initialValue);
                playerInstance = new Player(this.fileInpstream);
                this.playerThread = new Thread("Audio Player")
                {
                    public void run()
                    {
                        try
                        {
                            refreshGUI();
                            playPauseButton.setIcon(new ImageIcon(PAUSE_IMAGE));
                            playerInstance.play();
                            if (playerInstance != null && playerInstance.isComplete())
                            {
                                skipForwardTrack();
                            }
                        }
                        catch (JavaLayerException e)
                        {
                            e.printStackTrace();
                        }
                    }
                };

                this.playerThread.start();
                Set<Thread> threads = Thread.getAllStackTraces().keySet();
                for (Thread th : threads)
                {
                    if (th.getName().equals("Track Timer Thread"))
                    {
                        return;
                    }
                }

                Thread progressThread = new Thread("Track Timer Thread") {
                    public void run()
                    {
                        while (!this.isInterrupted())
                        {
                            try
                            {
                                if (playerInstance.isComplete() || isPausedFlag)
                                {
                                    break;
                                }
                                updateTimerProgress();
                                Thread.sleep(100);
                            }
                            catch (NullPointerException | InterruptedException e)
                            {
                                break;
                            }
                        }
                    }
                };
                progressThread.start();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Implements the ActionListener interface to respond to user interactions with buttons.
     * Delegates specific actions (skip back, play/pause, skip forward, loop, search, shuffle)
     * to their respective handler methods based on the source of the action event.
     * 
     * @param e The ActionEvent object representing the user action.
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object currentSource = e.getSource();

        if (currentSource == skipBackTrack)
        {
            skipBackTrack();
        }
        else if (currentSource == playPauseButton)
        {
            playOrPause();
        }
        else if (currentSource == skipForwardTrack)
        {
            skipForwardTrack();
        }
        else if (currentSource == loopButton)
        {
            loopTrack();
        }
        else if (currentSource == searchButton)
        {
            searchTracks();
        }

        else if (currentSource == shuffleButton)
        {
            shuffleTracks();
        }
    }

    /**
     * Shuffles the tracks in the current playlist. It retrieves the track array from 
     * the playlist, applies a shuffle algorithm, updates the playlist with the shuffled 
     * tracks, and refreshes the playlist view.
     */
    private void shuffleTracks() {
        MyPlaylist currentPlayList = getPlayList();
        if (currentPlayList != null) {
            Track[] currentTracks = currentPlayList.getTrackArray();
            Track[] shuffled = shuffle(currentTracks);
            currentPlayList.updateTracks(shuffled);
            updateList(currentPlayList);
        }
    }

    /**
     * Utility method to shuffle an array of Track objects. It converts the array 
     * to a List, uses Collections.shuffle to randomize the order, and returns a new 
     * array containing the shuffled tracks.
     * 
     * @param currentTracks The array of Track objects to be shuffled.
     * @return A new array containing the tracks in random order.
     */
    private Track[] shuffle(Track[] currentTracks) {
        Track[] shuffled = new Track[currentTracks.length];
        List<Track> tList = Arrays.asList(currentTracks);
        Collections.shuffle(tList);
        tList.toArray(shuffled);
        return shuffled;
    }

    /**
     * Searches for a track within the current playlist based on the text entered 
     * in the search field. Uses a BinaryTrackTree for efficient searching. 
     * If a matching track is found, it becomes the current track and playback starts.
     */
    private void searchTracks() {
        String title = searchField.getText();
        if (title.isEmpty() || title.isBlank()) {
            return;
        }

        BinaryTrackTree bTree = new BinaryTrackTree();
        Track tracks[] = myPlaylist.getTrackArray();
        for (int i=0; i<tracks.length;i++) {
            bTree.insert(tracks[i]);
        }

        TreeNode node = bTree.find(bTree.root, title);
        if (node !=null) {
            this.currentTrack = node.key;
            this.playTrack(0);
        }
    }

    /**
     * Toggles the looping state. If looping is off, it turns it on; if looping is on, 
     * it turns it off.
     */
    private void loopTrack() {
        if (isLooping) {
            isLooping = false;
        }
        else {
            isLooping = true;
        }
    }

    /**
     * Toggles between playing and pausing the current track. If no track is currently 
     * playing (playerInstance is null), it loads the first track from the playlist 
     * and starts playback.
     */
    public void playOrPause()
    {
        if (this.playerInstance != null)
        {
            if (this.isPausedFlag)
            {
                this.resume();
            }
            else
            {
                this.pauseTrack();
            }
        }
        else
        {
            if (this.myPlaylist.getTrackById(0) != null)
            {
                this.currentTrack = this.myPlaylist.getById(0);
                this.playTrack(0);
            }
        }
    }

    /**
     * Starts playback of the current track from the position indicated by the time slider.
     * This is an overload of {@link #playTrack(long)}.
     */
    private void playTrack()
    {
        this.playTrack(this.timeSlider.getValue());
    }

    /**
     * Resumes playback of the current track from the position indicated by the time slider.
     */
    private void resume()
    {
        playTrack(this.timeSlider.getValue());
    }

    /**
     * Pauses the current track. It closes the player instance, sets the play/pause 
     * button icon back to the play state, and flags the player as paused.
     */
    private void pauseTrack()
    {
        this.playPauseButton.setIcon(new ImageIcon(PLAY_IMAGE));
        if (playerInstance != null)
        {
            this.playerInstance.close();
        }
        this.isPausedFlag = true;
    }

    /**
     * Advances to the next track in the playlist. If the current track is the last 
     * track, it handles the end-of-playlist logic: if looping is enabled, it restarts 
     * from the first track; otherwise, it pauses the track and resets the slider to 0.
     */
    public void skipForwardTrack()
    {
        if (this.myPlaylist.getLast() != null)
        {
            if (this.currentTrack != this.myPlaylist.getLast())
            {
                this.currentTrack = this.myPlaylist.getTrackById(this.myPlaylist.getIndexOf(this.currentTrack) + 1);
                this.playTrack(0);
            }
            else
            {
                if (isLooping) {
                    Track first = this.myPlaylist.getById(0);
                    this.currentTrack = first;
                    this.playTrack(0);
                }
                else {
                    this.pauseTrack();
                    this.timeSlider.setValue(0);
                }
            }
        }
    }

    /**
     * Moves backward to the previous track in the playlist. If the current track 
     * is the first track, no action is performed.
     */
    private void skipBackTrack()
    {
        if (this.currentTrack != this.myPlaylist.getTrackById(0) && this.myPlaylist.getCount() > 0)
        {
            this.currentTrack = this.myPlaylist.getTrackById(this.myPlaylist.getIndexOf(this.currentTrack) - 1);
        }
        this.playTrack(0);
    }

    /**
     * Splits the currently playing audio track based on the current slider position 
     * and writes the resulting segment to the specified output file. It supports 
     * splitting from the start to the current position or from the current position 
     * to the end of the file.
     * 
     * @param outputFile The File object representing the destination for the split audio.
     * @param isToSplit If true, splits from the start of the track to the slider position. 
     *                  If false, splits from the slider position to the end of the track.
     * @throws IOException If an error occurs during file reading or writing.
     */
    public void splitCurrentTrackAndWriteToFile(File outputFile, boolean isToSplit)
    {
        if (this.currentTrack != null)
        {
            try
            {
                FileInputStream currentInpStream = new FileInputStream(this.currentTrack.getTrackFile());
                int point1, point2;
                if (isToSplit)
                {
                    point1 = 0;
                    point2 = this.timeSlider.getValue();
                }
                else
                {
                    point1 = this.timeSlider.getValue();
                    point2 = (int) this.totalLength;
                }
                currentInpStream.skip((long) point1);
                FileOutputStream fops = new FileOutputStream(outputFile);
                int remaining = point2 - point1;
                byte[] dataBuffer = new byte[remaining];
                currentInpStream.read(dataBuffer);
                fops.write(dataBuffer);
                currentInpStream.close();
                fops.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return;
            }
        }
    }

    /**
     * Updates the internal playlist with a new MyPlaylist object. If the new playlist 
     * contains tracks, the first track is set as the current track and the GUI 
     * is refreshed.
     * 
     * @param mplaylist The new MyPlaylist object to set.
     */
    public void updateList(MyPlaylist mplaylist)
    {
        this.myPlaylist = mplaylist;
        if (mplaylist.getCount() > 0)
        {
            Track firstTrack = mplaylist.getById(0);
            this.currentTrack = firstTrack;
        }
        this.refreshGUI();
    }

    /**
     * Refreshes the graphical user interface elements based on the state of the 
     * current track. Updates the album art label and the track title text field.
     * If the current track is null, a default album art icon is displayed.
     */
    public void refreshGUI()
    {
        try
        {
            this.albumLabel.setIcon(new ImageIcon(new ImageIcon(this.currentTrack.getAlbumArt()).getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH)));
        }
        catch (NullPointerException e)
        {
            this.albumLabel.setIcon(new ImageIcon(DEFAULT_ALBUM_ART_ICON));
        }

        if (this.currentTrack != null)
            this.fieldTrackTitle.setText(this.currentTrack.getTitle() + " - " + this.currentTrack.getArtist());
    }

    /**
     * Updates the timer progress by calculating the current playback position 
     * and setting the value of the time slider. The calculation is based on the 
     * total length of the file minus the remaining available bytes in the input stream.
     */
    public void updateTimerProgress()
    {
        int result;
        try
        {
            result = (int) (this.totalLength - this.fileInpstream.available());
        }
        catch (IOException e)
        {
            return;
        }
        this.timeSlider.setValue(result);
    }

    /**
     * Retrieves the currently selected or playing track.
     * 
     * @return The Track object currently active.
     */
    public Track getCurrentTrack()
    {
        return this.currentTrack;
    }

    /**
     * Sets the current track. This action pauses any currently playing audio, 
     * resets the time slider, and updates the slider's maximum value based on 
     * the new track's file size.
     * 
     * @param track The Track object to set as the current track.
     * @throws IOException If the track file cannot be read to determine its length.
     */
    public void setCurrentTrack(Track track)
    {
        this.currentTrack = track;
        this.pauseTrack();
        this.timeSlider.setValue(0);
        try
        {
            FileInputStream fips = new FileInputStream(this.currentTrack.getTrackFile());
            this.timeSlider.setMaximum((int) fips.available());
            fips.close();
        }
        catch (IOException e)
        {
            return;
        }
    }

    /**
     * Retrieves the current playlist associated with this player.
     * 
     * @return The MyPlaylist object.
     */
    public MyPlaylist getPlayList() {
        return myPlaylist;
    }
}
```