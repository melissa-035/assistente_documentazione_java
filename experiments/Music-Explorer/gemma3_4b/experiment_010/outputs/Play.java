```java
/**
 * Play Activity for music playback.
 *
 * This activity provides a user interface for playing music files stored locally.
 * It uses ExoPlayer for media playback and handles user interactions such as
 * navigation, playback controls, and track information display.
 * It also integrates with the CarService for features related to the car environment.
 */
public class Play extends AppCompatActivity implements Player.Listener {

    /**
     * The ExoPlayer instance used for media playback.
     */
    private ExoPlayer player = null;

    /**
     * The PlayerView for displaying the media player controls and the media itself.
     */
    private PlayerView playerView = null;

    /**
     * The DocumentFile representing the currently playing item.
     */
    private DocumentFile thePlayingItem = null;

    /**
     * A Runnable that periodically queries the track position and updates the
     * UI accordingly.
     */
    private Runnable trackPositionQuery = null;

    /**
     * A boolean flag indicating whether the track position query should continue.
     */
    private Boolean stopPositionQuery = false;

    /**
     * An interface for listening to track position changes.
     */
    private IMusicExplorerPlay trackMSListener = null;


    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ensure the MusicPlayer is finished if it exists.
        if ( ! ( null == theMusicPlayer ) )
            theMusicPlayer.finish();

        theMusicPlayer = this; // Set the current activity as the MusicPlayer

        setContentView(R.layout.player);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Create a new ExoPlayer instance.
        player = new ExoPlayer.Builder(this).build();

        // Find the PlayerView by ID.
        playerView = findViewById(R.id.player_view);
        playerView.setArtworkDisplayMode(PlayerView.ARTWORK_DISPLAY_MODE_FIT);
        playerView.setControllerHideOnTouch(false);
        playerView.setControllerShowTimeoutMs(86400000);
        playerView.setPlayer(player);

        // Get the current file from the properties.
        thePlayingItem = properties.getCurrentFile();

        // Add the current player listener to the player.
        if ( ! ( null == theMusicExplorerInterface.getCurrentPlayerListener() ) )
            player.addListener(theMusicExplorerInterface.getCurrentPlayerListener());

        // Set the folder name in the TextView.
        ((TextView)findViewById(R.id.player_view_folder)).setText(properties.getCurrentFolder().getName());

        // Add the player listener to the player.
        player.addListener(this);

        DocumentFile trackFolder  = null;

        // Check if the playing item is a directory.
        if ( thePlayingItem.isDirectory() ) {
            trackFolder = thePlayingItem;
            DocumentFile[] theFiles = thePlayingItem.listFiles();
            for ( DocumentFile df : theFiles ) {
                if ( df.isDirectory() )
                    continue;
                player.addMediaItem(MediaItem.fromUri(df.getUri()));
            }
        } else {
            trackFolder = properties.getCurrentFolder();
            int myIndex = -1;
            int k = -1;
            for ( DocumentFile df : properties.getCurrentFolder().listFiles() ) {
                if ( df.isDirectory() )
                    continue;
                k++;
                player.addMediaItem(MediaItem.fromUri(df.getUri()));
                if ( df.getName().equals(thePlayingItem.getName()) )
                    myIndex = k;
            }
            player.seekTo(myIndex,0);
        }

        ((TextView)findViewById(R.id.player_view_folder)).setText(String.format("Folder: %s",trackFolder.getName()));

        // Set the audio attributes.
        androidx.media3.common.AudioAttributes theAttributes = androidx.media3.common.AudioAttributes.fromPlatformAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).build());
        player.setAudioAttributes(theAttributes,true);

        // Create the track position query runnable.
        trackPositionQuery = new Runnable() {
            @Override
            public void run() {
                // Update the track position and notify the listener.
                properties.setCurrentTrackMS(player.getCurrentPosition());
                if ( ! ( null == trackMSListener ) )
                    trackMSListener.onPlayTrackMSChanged(properties.getCurrentTrackMS());
                // Schedule the next query if the query is not stopped.
                if ( ! ( null == theMusicPlayer ) && ! ( theMusicPlayer.stopPositionQuery ) )
                    new Handler(getMainLooper()).postDelayed(trackPositionQuery,PLAYER_TRACK_QUERY_DELAY);
            }
        };

        // Prepare the player.
        player.prepare();

        // Seek to the current track position if it exists.
        if ( -1 < properties.getCurrentTrackMS() )
            player.seekTo(properties.getCurrentTrackMS());

        // Play the music.
        player.play();

        // Handle started by auto events.
        if ( ! ( null == theMusicExplorer.getIntent().getStringExtra("StartedByCar") ) ) {
            theMusicExplorer.getIntent().removeExtra("StartedByCar");
            theMusicExplorer.setWasStartedByAuto(true);
            Util.broadcast(this,"com.envisionate.musicinfolders.STARTED_NOTIFY_WITH_PLAY");
        }

        // Handle click is from auto events.
        if ( ! ( null == getIntent().getStringExtra("ClickIsFromAuto") ) ) {
            getIntent().removeExtra("ClickIsFromAuto");
            Util.broadcast(theMusicExplorer,"com.envisionate.musicinfolders.PLAY_NOTIFY");
        }

        theMusicExplorer.clearResumedPlay();

        properties.setLastPlayedFile(properties.getCurrentFile());
    }

    /**
     * Called when the activity resumes.
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Called when the activity creates its options menu.
     *
     * @param menu The menu to be shown.
     * @return True if the menu has been created or updated, false if no menu
     *         needs to be shown.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.exit_only, menu);
        return true;
    }

    /**
     * Called when the activity is given a menu item to respond to.
     *
     * @param item The menu item that was clicked.
     * @return True if the menu item was handled, false otherwise.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if ( item.getItemId() == android.R.id.home ) {
            theMusicExplorerInterface.gotoParent();
            return true;
        }
        if ( item.getItemId() == R.id.exit ) {
            Util.broadcast(this,"com.envisionate.musicinfolders.STOP_REQUESTED");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Pauses the current playback.
     */
    public void pause() {
        player.pause();
    }

    /**
     * Moves to the previous track.
     */
    public void previous() {
        player.seekToPrevious();
        Util.broadcast