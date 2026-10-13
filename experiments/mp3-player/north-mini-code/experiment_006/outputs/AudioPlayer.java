```java
/**
 * The AudioPlayer class provides a GUI component for playing audio tracks from a playlist.
 * It extends JPanel and implements ActionListener to handle user interactions such as play/pause,
 * skip forward/backward, shuffle, loop, and search. The player displays album art, track title,
 * a time slider for seeking, and controls for playback.
 *
 * @author (not specified)
 * @version 1.0
 * @since (not specified)
 */
@SuppressWarnings("serial")
public class AudioPlayer extends JPanel implements ActionListener
{
	// UI Icons for buttons and album art
	private final Image SHUFFLE_IMAGE = (new ImageIcon(getClass().getResource("/images/shuffle.png"))).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
	private final Image LOOP_IMAGE = (new ImageIcon(getClass().getResource("/images/loop.png"))).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
	private final Image PLAY_IMAGE = (new ImageIcon(getClass().getResource("/images/play_button.png"))).getImage().getScaledInstance(12, 12, Image.SCALE_SMOOTH);
	private final Image PAUSE_IMAGE = (new ImageIcon(getClass().getResource("/images/pause_button.png"))).getImage().getScaledInstance(12, 12, Image.SCALE_SMOOTH);
	private final Image DEFAULT_ALBUM_ART_ICON = (new ImageIcon(getClass().getResource("/images/album.png"))).getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);

	// Audio playback state
	private FileInputStream fileInpstream;
	private long totalLength;
	private volatile boolean isPausedFlag = false;
	private Player playerInstance;
	private Thread playerThread;
	private JSlider timeSlider;
	private Track currentTrack;
	private MyPlaylist myPlaylist;
	private JTextField fieldTrackTitle;
	private JButton skipBackTrack;
	private JButton skipForwardTrack;
	private JButton playPauseButton;
	private JLabel albumLabel;
	private JButton loopButton;
	private boolean isLooping;
	private JButton searchButton;
	private JTextField searchField;
	private JButton shuffleButton;

	/**
	 * Constructs an AudioPlayer with the given playlist and album label.
	 * Initializes UI components, sets up layout, and configures the player.
	 *
	 * @param myPlaylist the playlist containing tracks to be played
	 * @param albumLabel the label where album art is displayed
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
	 * Starts playback of the current track from a given byte offset.
	 *
	 * @param initialValue the byte offset from the start of the track to begin playback
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
	 * Starts playback of the current track from the current slider position.
	 */
	private void playTrack()
	{
		this.playTrack(this.timeSlider.getValue());
	}

	/**
	 * Toggles between playing and pausing the current track.
	 * If the player is paused, resumes playback; otherwise, pauses the current track.
	 * If no track is being played and the playlist has tracks, starts playing the first track.
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
	 * Resumes playback of the current track from the current slider position.
	 */
	private void resume()
	{
		playTrack(this.timeSlider.getValue());
	}

	/**
	 * Pauses the current track and updates the play/pause button icon.
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

	@Override
	/**
	 * Handles action events from UI components (buttons, etc.).
	 * Delegates to specific handler methods based on the source of the event.
	 *
	 * @param e the ActionEvent triggered by user interaction
	 */
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
	 * Shuffles the tracks in the current playlist.
	 * If the playlist is not null, it shuffles the array of tracks and updates the playlist.
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
	 * Shuffles an array of tracks.
	 *
	 * @param currentTracks the array of tracks to shuffle
	 * @return a new array with tracks in random order
	 */
	private Track[] shuffle(Track[] currentTracks) {
		Track[] shuffled = new Track[currentTracks.length];
		List<Track> tList = Arrays.asList(currentTracks);
		Collections.shuffle(tList);
		tList.toArray(shuffled);
		return shuffled;
	}


	/**
	 * Searches for a track by title in the playlist.
	 * Uses a BinaryTrackTree to efficiently find the track. If found, sets it as current track and starts playback.
	 * If the search field is empty, does nothing.
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
	 * Toggles the loop mode.
	 * When loop is enabled, playback will restart from the beginning after the last track.
	 * When disabled, playback stops at the last track.
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
	 * Skips to the next track in the playlist.
	 * If at the last track, either loops (if loop mode is enabled) or pauses playback.
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
	 * Skips to the previous track in the playlist.
	 * If at the first track, does nothing.
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
	 * Splits the current audio file and writes the specified segment to a new file.
	 *
	 * @param outputFile the file where the split segment will be saved
	 * @param isToSplit  if true, splits from start to current slider position; otherwise splits from current position to end
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
	 * Updates the internal playlist and refreshes the GUI.
	 *
	 * @param mplaylist the new playlist to set
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
	 * Refreshes the UI components to reflect the current track information.
	 * Updates album art label and track title text.
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
	 * Updates the time slider value based on the current playback progress.
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
	 * Returns the currently selected track.
	 *
	 * @return the current Track object
	 */
	public Track getCurrentTrack()
	{
		return this.currentTrack;
	}

	/**
	 * Sets a new current track, pauses playback, and resets the time slider.
	 *
	 * @param track the new track to set as current
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
	 * Returns the playlist used by this player.
	 *
	 * @return the MyPlaylist object
	 */
	public MyPlaylist getPlayList() {
		return myPlaylist;
	}

}
```