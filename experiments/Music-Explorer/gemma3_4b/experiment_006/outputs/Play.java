```java
        
--------------------
FILE: app\src\main\java\com\envisionate\musicinfolders\Play.java
--------------------

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

    private ExoPlayer player = null;
    private PlayerView playerView = null;

    private DocumentFile thePlayingItem = null;

    private Runnable trackPositionQuery = null;
    private Boolean stopPositionQuery = false;
    private IMusicExplorerPlay trackMSListener = null;

    @OptIn(markerClass = ExperimentalCarApi.class)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Creates a new Play activity.
        super.onCreate(savedInstanceState);

        // Finishes the MusicPlayer if it exists, ensuring proper state management.
        if ( ! ( null == theMusicPlayer ) )
            theMusicPlayer.finish();

        // Sets the MusicPlayer to the current activity, establishing the context.
        theMusicPlayer = this;

        // Sets the layout for the activity, using the player layout.
        setContentView(R.layout.player);

        // Enables the home button navigation for easy return to the previous screen.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initializes a new ExoPlayer instance with a builder configuration.
        player = new ExoPlayer.Builder(this).build();

        // Finds the PlayerView widget by its ID, initializing it for media playback.
        playerView = findViewById(R.id.player_view);
        // Configures the PlayerView to display artwork in a fit mode.
        playerView.setArtworkDisplayMode(PlayerView.ARTWORK_DISPLAY_MODE_FIT);
        // Prevents the controller from hiding when the screen is touched.
        playerView.setControllerHideOnTouch(false);
        // Sets a timeout period (in milliseconds) before the controller disappears.
        playerView.setControllerShowTimeoutMs(86400000);
        // Associates the ExoPlayer with the PlayerView, enabling media playback.
        playerView.setPlayer(player);

        // Retrieves the current playing item from the properties object.
        thePlayingItem = properties.getCurrentFile();
        // Checks if the MusicExplorerInterface exists and adds the current player listener to it.
        if ( ! ( null == theMusicExplorerInterface.getCurrentPlayerListener() ) )
            player.addListener(theMusicExplorerInterface.getCurrentPlayerListener());

        // Updates the TextView associated with the folder view to display the current folder's name.
        ((TextView)findViewById(R.id.player_view_folder)).setText(properties.getCurrentFolder().getName());

        // Adds a listener to the player, enabling tracking of the current track position.
        player.addListener(this);

        // Handles the processing of the playing item, determining if it's a file or a directory.
        DocumentFile trackFolder  = null;

        if ( thePlayingItem.isDirectory() ) {
            // If the playing item is a directory, it retrieves the directory's contents.
            trackFolder = thePlayingItem;
            // Gets the list of files within the directory.
            DocumentFile[] theFiles = thePlayingItem.listFiles();
            // Iterates through the files, adding each one to the player's media items.
            for ( DocumentFile df : theFiles ) {
                // Skips any files that are directories, ensuring only audio files are processed.
                if ( df.isDirectory() )
                    continue;
                // Adds the current file to the player's media items.
                player.addMediaItem(MediaItem.fromUri(df.getUri()));
            }
        } else {
            // If the playing item is a file, it retrieves the file's URI.
            trackFolder = properties.getCurrentFolder();
            // Initializes an index to track the position of the current file within the folder.
            int myIndex = -1;
            // Initializes a counter to track the index of the file within the folder's list.
            int k = -1;
            // Iterates through the files in the folder, searching for the current file.
            for ( DocumentFile df : properties.getCurrentFolder().listFiles() ) {
                // Skips any files that are directories, ensuring only audio files are processed.
                if ( df.isDirectory() )
                    continue;
                // Increments the counter for each file.
                k++;
                // Adds the current file to the player's media items.
                player.addMediaItem(MediaItem.fromUri(df.getUri()));
                // Checks if the current file matches the name of the playing item.
                if ( df.getName().equals(thePlayingItem.getName()) )
                    // If it matches, sets the index to the current counter.
                    myIndex = k;
            }
            // Sets the player's position to the index of the current file within the folder.
            player.seekTo(myIndex,0);
        }

        // Updates the TextView associated with the folder view to display the name of the current folder.
        ((TextView)findViewById(R.id.player_view_folder)).setText(String.format("Folder: %s",trackFolder.getName