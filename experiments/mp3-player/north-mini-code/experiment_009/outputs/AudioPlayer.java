```java
/**
 * <p>A JPanel component that manages and plays audio tracks from a playlist.
 * It provides a graphical user interface for playback control, including features
 * such as shuffle, loop, search, track navigation, and splitting.</p>
 *
 * <p>This class utilizes the JavaZoom library for MP3 playback and implements
 * the {@link ActionListener} interface to handle user interactions from buttons
 * and sliders.</p>
 *
 * @author Developer
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("serial")
public class AudioPlayer extends JPanel implements ActionListener
{
	/**
	 * Icon image for the shuffle button.
	 */
	private final Image SHUFFLE_IMAGE = (new ImageIcon(getClass().getResource("/images/shuffle.png"))).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
	/**
	 * Icon image for the loop button.
	 */
	private final Image LOOP_IMAGE = (new ImageIcon(getClass().getResource("/images/loop.png"))).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
	/**
	 * Icon image for the play button.
	 */
	private final Image PLAY_IMAGE = (new ImageIcon(getClass().getResource("/images/play_button.png"))).getImage().getScaledInstance(12, 12, Image.SCALE_SMOOTH);
	/**
	 * Icon image for the pause button.
	 */
	private final Image PAUSE_IMAGE = (new ImageIcon(getClass().getResource("/images/pause_button.png"))).getImage().getScaledInstance(12, 12, Image.SCALE_SMOOTH);
	/**
	 * Default icon displayed when no album art is available.
	 */
	private final Image DEFAULT_ALBUM_ART_ICON = (new ImageIcon(getClass().getResource("/images/album.png"))).getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);

	/**
	 * Input stream for the currently loaded audio file.
	 */
	private FileInputStream fileInpstream;
	/**
	 * Total length (size in bytes) of the currently loaded track.
	 */
	private long totalLength;
	/**
	 * Flag indicating whether the track is currently paused.
	 */
	private volatile boolean isPausedFlag = false;
	/**
	 * The JavaZoom Player instance responsible for decoding and playing audio.
	 */
	private Player playerInstance;
	/**
	 * Thread dedicated to running the audio playback.
	 */
	private Thread playerThread;
	/**
	 * Slider UI component for seeking through the audio track.
	 */
	private JSlider timeSlider;
	/**
	 * The Track object currently being played.
	 */
	private Track currentTrack;
	/**
	 * The playlist containing available tracks.
	 */
	private MyPlaylist myPlaylist;
	/**
	 * Text field displaying the title of the current track.
	 */
	private JTextField fieldTrackTitle;
	/**
	 * Button to skip backward to the previous track.
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
	 * Label displaying the album cover art.
	 */
	private JLabel albumLabel;
	/**
	 * Button to toggle repeat/loop mode.
	 */
	private JButton loopButton;
	/**
	 * Flag indicating whether looping is enabled.
	 */
	private boolean isLooping;
	/**
	 * Button to initiate track searching.
	 */
	private JButton searchButton;
	/**
	 * Text field for entering search queries.
	 */
	private JTextField searchField;
	/**
	 * Button to shuffle the playlist order.
	 */
	private JButton shuffleButton;

	/**
	 * Constructs the AudioPlayer UI.
	 * Initializes components, sets the layout, assigns action listeners,
	 * and configures the initial state with the provided playlist and album label.
	 *
	 * @param myPlaylist The {@link MyPlaylist} instance containing tracks to manage.
	 * @param albumLabel The {@link JLabel} where album art will be displayed.
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
	 * Starts playback of the current track from a specific position.
	 * Sets up the file input stream, creates player instances, and initiates threads
	 * for playback and progress tracking.
	 *
	 * @param initialValue The starting byte offset within the audio file.
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
	 * Handles action events triggered by UI components (buttons, etc.).
	 * Delegates execution to specific private methods based on which button was pressed.
	 *
	 * @param e The {@link ActionEvent} representing the user's action.
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
	 * Shuffles the tracks in the current playlist and updates the internal list.
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
	 * Shuffles an array of tracks randomly.
	 *
	 * @param currentTracks The array of tracks to shuffle.
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
	 * Searches for a track in the playlist based on the text in the search field.
	 * If found, sets it as the current track and starts playback.
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
	 * Toggles the loop mode state between enabled and disabled.
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
	 * Toggles between playing and pausing the current track.
	 * If a track is playing, it pauses. If paused, it resumes. If no track is loaded,
	 * it loads the first track from the playlist.
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
	 * Plays the track starting from the current value of the time slider.
	 */
	private void playTrack()
	{
		this.playTrack(this.timeSlider.getValue());
	}

	/**
	 * Resumes playback from the current position indicated by the time slider.
	 */
	private void resume()
	{
		playTrack(this.timeSlider.getValue());
	}


	/**
	 * Pauses the currently playing track.
	 * Updates the UI button icon and stops the player instance.
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
	 * Skips to the next track in the playlist.
	 * If the current track is the last one, behavior depends on loop mode.
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
	 * Skips backward to the previous track in the playlist.
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
	 * Splits the current audio track and writes it to a specified file.
	 * Depending on the boolean flag, it splits from start to cursor or cursor to end.
	 *
	 * @param outputFile The File to write the split audio content to.
	 * @param isToSplit  If true, splits from start to cursor. If false, splits from cursor to end.
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
	 * Updates the internal playlist and refreshes the UI components based on the new playlist data.
	 *
	 * @param mplaylist The new {@link MyPlaylist} instance.
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
	 * Refreshes the graphical user interface elements.
	 * Updates the album art label and the track title field based on the current track.
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
	 * Updates the time slider position based on the playback progress of the current track.
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
	 * Gets the currently loaded audio track.
	 *
	 * @return The {@link Track} object representing the current track, or null if none is loaded.
	 */
	public Track getCurrentTrack()
	{
		return this.currentTrack;
	}

	/**
	 * Sets the current track to play. Pauses any currently playing track and resets the slider.
	 *
	 * @param track The {@link Track} to set as the current track.
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
	 * Gets the current playlist.
	 *
	 * @return The {@link MyPlaylist} object.
	 */
	public MyPlaylist getPlayList() {
		return myPlaylist;
	}

}