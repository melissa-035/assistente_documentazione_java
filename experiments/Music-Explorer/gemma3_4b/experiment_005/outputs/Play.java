```java
package com.envisionate.musicinfolders;

import static android.view.View.INVISIBLE;

import static androidx.media3.common.Player.EVENT_PLAYER_ERROR;
import static androidx.media3.common.Player.EVENT_TRACKS_CHANGED;

import static com.envisionate.carservice.CarService.PLAYER_TRACK_QUERY_DELAY;
import static com.envisionate.musicinfolders.Globals.properties;
import static com.envisionate.musicinfolders.Globals.theMusicExplorer;
import static com.envisionate.musicinfolders.Globals.theMusicExplorerInterface;
import static com.envisionate.musicinfolders.Globals.theMusicPlayer;

import android.media.AudioAttributes;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.car.app.annotations.ExperimentalCarApi;
import androidx.documentfile.provider.DocumentFile;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MediaMetadata;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import com.envisionate.carservice.CarService;
import com.envisionate.musicinfolders.interfaces.IMusicExplorerPlay;

public class Play extends AppCompatActivity implements Player.Listener {

    /**
     * The ExoPlayer instance used to play music.
     */
    private ExoPlayer player = null;

    /**
     * The PlayerView used to display the media player.
     */
    private PlayerView playerView = null;

    /**
     * The currently playing DocumentFile.
     */
    private DocumentFile thePlayingItem = null;

    /**
     * Runnable that queries the track position periodically.
     */
    private Runnable trackPositionQuery = null;

    /**
     * A boolean flag indicating whether the track position query is currently running.
     */
    private Boolean stopPositionQuery = false;

    /**
     * The interface for handling track position changes.
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

        // Finish the previous MusicPlayer activity if it exists.
        if ( ! ( null == theMusicPlayer ) )
            theMusicPlayer.finish();

        // Set the current activity as the MusicPlayer.
        theMusicPlayer = this;

        // Set the content view to the player layout.
        setContentView(R.layout.player);

        // Enable the back button to navigate up.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Create a new ExoPlayer instance with a builder configuration.
        player = new ExoPlayer.Builder(this).build();

        // Find the PlayerView by its id.
        playerView = findViewById(R.id.player_view);

        // Set the artwork display mode to FIT.
        playerView.setArtworkDisplayMode(PlayerView.ARTWORK_DISPLAY_MODE_FIT);

        // Prevent the controller from hiding on touch.
        playerView.setControllerHideOnTouch(false);

        // Set the controller show timeout to 86400000 milliseconds (1 day).
        playerView.setControllerShowTimeoutMs(86400000);

        // Set the PlayerView's player to the current ExoPlayer instance.
        playerView.setPlayer(player);

        // Get the current file from the properties.
        thePlayingItem = properties.getCurrentFile();

        // If a current player listener exists, add it to the player.
        if ( ! ( null == theMusicExplorerInterface.getCurrentPlayerListener() ) )
            player.addListener(theMusicExplorerInterface.getCurrentPlayerListener());

        // Set the text of the folder view to the name of the current folder.
        ((TextView)findViewById(R.id.player_view_folder)).setText(properties.getCurrentFolder().getName());

        // Add the current player listener to the player.
        player.addListener(this);

        // Handle loading the media items from the playing item.
        DocumentFile trackFolder  = null;

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

        // Set the text of the folder view to the name of the loaded folder.
        ((TextView)findViewById(R.id.player_view_folder)).setText(String.format("Folder: %s",trackFolder.getName()));

        // Create an AudioAttributes object for platform audio.
        androidx.media3.common.AudioAttributes theAttributes = androidx.media3.common.AudioAttributes.fromPlatformAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).build());

        // Set the audio attributes for the player.
        player.setAudioAttributes(theAttributes,true);

        // Create a Runnable for periodically querying the track position.
        trackPositionQuery = new Runnable() {
            @Override
            public void run() {
                // Get the current track position from the properties.
                properties.setCurrentTrackMS(player.getCurrentPosition());

                // If a track position listener exists, notify it of the change.
                if ( ! ( null == trackMSListener ) )
                    trackMSListener.onPlayTrackMSChanged(properties.getCurrentTrackMS());

                // If the track position query is not stopped, schedule it to run again.
                if ( ! ( null == theMusicPlayer ) && ! ( theMusicPlayer.stopPositionQuery ) )
                    new Handler(getMainLooper()).postDelayed(trackPositionQuery,PLAYER_TRACK_QUERY_DELAY);
            }
        };

        // Prepare the player and load the media items.
        player.prepare();

        // Seek to the current track position if it is not null.
        if ( -1 < properties.getCurrentTrackMS() )
            player.seekTo(properties.getCurrentTrackMS());

        // Play the music.
        player.play();

        // Handle started by auto notifications.
        if ( ! ( null == getIntent().getStringExtra("StartedByCar") ) ) {
            getIntent().removeExtra("StartedByCar");
            theMusicExplorer.setWasStartedByAuto(true);
            Util.broadcast(this,"com.envisionate.musicinfolders.STARTED_NOTIFY_WITH_PLAY");
        }

        // Handle started by auto notifications.
        if ( ! ( null == getIntent().getStringExtra("ClickIsFromAuto") ) ) {
            getIntent().removeExtra("ClickIsFromAuto");
            Util.broadcast(theMusicExplorer,"com.envisionate.musicinfolders.PLAY_NOTIFY");
        }

        // Clear