```java
package com.envisionate.carservice.screen;

import static android.os.Looper.getMainLooper;
import static androidx.media3.common.Player.EVENT_PLAYER_ERROR;
import static com.envisionate.musicinfolders.Globals.properties;
import static com.envisionate.musicinfolders.Globals.theMusicExplorer;
import static com.envisionate.musicinfolders.Globals.theMusicExplorerInterface;
import static com.envisionate.musicinfolders.Globals.theMusicPlayer;

import android.os.Handler;
import android.util.Log;

import androidx.activity.OnBackPressedCallback;
import androidx.car.app.CarContext;
import androidx.car.app.Screen;
import androidx.car.app.annotations.ExperimentalCarApi;
import androidx.car.app.model.Action;
import androidx.car.app.model.CarIcon;
import androidx.car.app.model.GridItem;
import androidx.car.app.model.GridSection;
import androidx.car.app.model.Header;
import androidx.car.app.model.OnClickListener;
import androidx.car.app.model.Row;
import androidx.car.app.model.RowSection;
import androidx.car.app.model.Section;
import androidx.car.app.model.SectionedItemTemplate;
import androidx.car.app.model.Template;
import androidx.core.graphics.drawable.IconCompat;
import androidx.documentfile.provider.DocumentFile;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MediaMetadata;
import androidx.media3.common.Player;

import com.envisionate.musicinfolders.R;
import com.envisionate.musicinfolders.Util;
import com.envisionate.musicinfolders.interfaces.IMusicExplorerPlay;

import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A screen for playing music in a car context.
 * 
 * This class implements the CarAppScreen interface, providing a UI for controlling music playback.
 * It handles displaying track information, playback controls, and other relevant elements.
 */
@ExperimentalCarApi
public class CarPlayerScreen extends Screen implements Player.Listener, IMusicExplorerPlay {

    public static CarPlayerScreen Instance = null;

    private List<Section<?>> sections = new ArrayList<>();
    private RowSection.Builder rowSectionBuilder = new RowSection.Builder();
    //private RowSection.Builder progressBarRowSectionBuilder = new RowSection.Builder();
    private GridSection.Builder gridSectionBuilder = new GridSection.Builder();

    private SectionedItemTemplate.Builder theSectionedItemTemplateBuilder = new SectionedItemTemplate.Builder();

    private CarIcon playPrevious = new CarIcon.Builder(IconCompat.createWithResource(getCarContext(), R.drawable.play_previous)).build();
    private CarIcon playPause = new CarIcon.Builder(IconCompat.createWithResource(getCarContext(), R.drawable.play_pause)).build();
    private CarIcon playNext = new CarIcon.Builder(IconCompat.createWithResource(getCarContext(), R.drawable.play_next)).build();
    private CarIcon playContinue = new CarIcon.Builder(IconCompat.createWithResource(getCarContext(), R.drawable.play)).build();
    private CarIcon exitCarIcon = new CarIcon.Builder(IconCompat.createWithResource(getCarContext(), R.drawable.exit)).build();

    private String albumInformation = null;
    private String trackInformation = null;
    private String errorInformation = null;
    private long trackDuration = 0;
    private long currentTrackMS = 0;
    private Boolean inhibitInvalidate = false;

    /**
     * Constructs a new CarPlayerScreen.
     *
     * @param carContext The CarContext providing the necessary resources and APIs for the CarAppScreen.
      */
    public CarPlayerScreen(@NonNull CarContext carContext) {
        super(carContext);
    }

    /**
     * Creates the template for the screen.
     * 
     * This method initializes the various components of the screen and builds the template
     * that will be displayed to the user. It sets up the row and grid sections, creates the
     * icons, and configures the header.
     *
     * @return The created SectionedItemTemplate.
     */
    @Override
    public @NonNull Template onGetTemplate() {

        inhibitInvalidate = false;

        Log.d("MusicExplorer", "onGetTemplate called for CarPlayerScreen");

        rowSectionBuilder.clearItems();
        gridSectionBuilder.clearItems();

        theSectionedItemTemplateBuilder.clearSections();

        rowSectionBuilder.addItem(new Row.Builder()
                .setTitle(String.format("Folder: %s", properties.getCurrentFolder().getName()))
                .build());

        rowSectionBuilder.addItem(new Row.Builder()
                .setTitle(null == albumInformation ? " " : albumInformation)
                .build());

        rowSectionBuilder.addItem(new Row.Builder()
                .setTitle(null == trackInformation ? " " : trackInformation)
                .build());

        /*
         I wish to fuck I could get some sort of status bar indicater
         to work!!!

         This particular method causes the head unit display to flash on update

         rowSectionBuilder.addItem(new Row.Builder()
                     .setTitle(" ")
                     .setImage(statusCircle,Row.IMAGE_TYPE_SMALL)
                     .build());
         */

        if ( ! ( null == errorInformation ) ) {
            rowSectionBuilder.addItem(new Row.Builder()
                    .setTitle(errorInformation)
                    .build());
        }

        sections.add(rowSectionBuilder.build());

        if ( null == errorInformation ) {

            gridSectionBuilder.addItem(new GridItem.Builder()
                    .setTitle(" ")
                    .setImage(playPrevious, GridItem.IMAGE_TYPE_LARGE)
                    .setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick() {
                            theMusicExplorerInterface.previous();
                        }
                    })
                    .build());

            gridSectionBuilder.addItem(new GridItem.Builder()
                    .setTitle(" ")
                    .setImage(theMusicPlayer.getPlayer().isPlaying() ? playPause : playContinue, GridItem.IMAGE_TYPE_LARGE)
                    .setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick() {
                            if ( theMusicPlayer.getPlayer().isPlaying() )
                                theMusicExplorerInterface.pause();
                            else
                                theMusicExplorerInterface.play();
                        }
                    })
                    .build());

            gridSectionBuilder.addItem(new GridItem.Builder()
                    .setTitle(" ")
                    .setImage(playNext, GridItem.IMAGE_TYPE_LARGE)
                    .setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick() {
                            theMusicExplorerInterface.next();
                        }
                    })
                    .build());

            /*
            gridSectionBuilder.addItem(new GridItem.Builder()
                    .setTitle(" ")
                    .setImage(statusCircle,GridItem.IMAGE_TYPE_ICON)
                    .build());
            */

            sections.add(gridSectionBuilder.build());

        }

        Action extraAction = new Action.Builder()
                .setIcon(