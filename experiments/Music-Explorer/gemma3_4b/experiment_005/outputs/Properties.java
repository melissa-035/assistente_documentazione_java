```java
package com.envisionate.musicinfolders;

import static com.envisionate.carservice.CarService.ANDROID_AUTO_DISPLAY_FOLDERS;
import static com.envisionate.musicinfolders.Globals.FOLDER_NOMINAL_VIEW_HEIGHT;
import static com.envisionate.musicinfolders.Globals.FOLDER_NOMINAL_VIEW_WIDTH;
import static com.envisionate.musicinfolders.Globals.TRACK_NOMINAL_VIEW_WIDTH;
import static com.envisionate.musicinfolders.Globals.theMusicExplorer;

import android.content.SharedPreferences;
import android.net.Uri;

import androidx.documentfile.provider.DocumentFile;

import java.util.Stack;
import java.util.UUID;

/**
 * This class encapsulates the application's persistent settings and data.
 * It manages preferences, folder views, and other configurable options.
 */
public class Properties {

    /**
     * The number of columns to display files in the Android Auto display.
     */
    private static final int AUTO_DISPLAY_COLUMNS = 5;

    /**
     * A boolean indicating whether the application is in its initial setup state.
     * This is used to determine the default behavior and UI.
     */
    private Boolean isInitialSetup = true;

    /**
     * The root folder URI for the music library.
     * This is the starting point for browsing the music files.
     */
    private String rootFolder = null;

    /**
     * The name of the root folder.
     */
    private String rootFolderName = null;

    /**
     * A unique identifier (UUID) for this instance of the application.
     */
    private String uuid = null;

    /**
     * The width of the folder view.
     */
    private int folderViewWidth = FOLDER_NOMINAL_VIEW_WIDTH;

    /**
     * The height of the folder view.
     */
    private int folderViewHeight = FOLDER_NOMINAL_VIEW_HEIGHT;

    /**
     * The width of the file view.
     */
    private int fileViewWidth = TRACK_NOMINAL_VIEW_WIDTH;

    /**
     * The number of columns to display files in the auto display.
     */
    private int autoDisplayColumns = AUTO_DISPLAY_COLUMNS;

    /**
     * A boolean indicating whether the auto display folders should be enabled.
     */
    private Boolean autoDisplayFolders = ANDROID_AUTO_DISPLAY_FOLDERS;

    /**
     * The current DocumentFile object representing the currently viewed folder.
     */
    private DocumentFile currentFolder = null;

    /**
     * The current DocumentFile object representing the currently played file.
     */
    private DocumentFile currentFile = null;

    /**
     * The DocumentFile object representing the last played file.
     */
    private DocumentFile lastPlayedFile = null;

    /**
     * A boolean indicating whether to resume playback on app start.
     */
    private Boolean resumePlayOnStart = true;

    /**
     * A boolean indicating whether to exit the application on Bluetooth disconnect.
     */
    private Boolean exitOnBTDisconnect = false;

    /**
     * A counter for the number of Bluetooth permissions request attempts.
     */
    private int btPermissionsRequestCount = 0;

    /**
     * The milliseconds since the epoch representing the current track's playback position.
     */
    private long currentTrackMS = 0;

    /**
     * The SharedPreferences object used to store and retrieve application preferences.
     */
    private static SharedPreferences preferences = null;

    /**
     * Constructor for the Properties class.
     *
     * @param preferences The SharedPreferences object to use for storing and retrieving preferences.
     */
    Properties(SharedPreferences preferences) {
        this.preferences = preferences;
        isInitialSetup = preferences.getBoolean("isInitialSetup", true);
        rootFolder = preferences.getString("rootFolderName", "<click Browse>");
        rootFolderName = theMusicExplorer.uriToName(Uri.parse(rootFolder));
        folderViewWidth = preferences.getInt("folderViewWidth", FOLDER_NOMINAL_VIEW_WIDTH);
        folderViewHeight = preferences.getInt("folderViewHeight", FOLDER_NOMINAL_VIEW_HEIGHT);
        fileViewWidth = preferences.getInt("fileViewWidth", TRACK_NOMINAL_VIEW_WIDTH);

        try {
            currentFolder = DocumentFile.fromTreeUri(theMusicExplorer, Uri.parse(preferences.getString("currentFolder", null)));
        } catch (Exception ex) {
            currentFolder = null;
        }

        if (currentFolder == null) {
            currentFolder = DocumentFile.fromTreeUri(theMusicExplorer, Uri.parse(rootFolder));
        }

        try {
            currentFile = DocumentFile.fromTreeUri(theMusicExplorer, Uri.parse(preferences.getString("currentFile", null)));
        } catch (Exception ex1) {
            currentFile = null;
        }

        try {
            lastPlayedFile = DocumentFile.fromTreeUri(theMusicExplorer, Uri.parse(preferences.getString("lastPlayedFile", null)));
        } catch (Exception ex1) {
            lastPlayedFile = null;
        }

        try {
            currentTrackMS = preferences.getLong("currentTrackMS", -1);
        } catch (Exception ex) {
        }

        uuid = preferences.getString("uuid", UUID.randomUUID().toString());

    }

    /**
     * Resets all preferences to their default values.
     */
    public void resetPreferences() {
        isInitialSetup = true;
        rootFolder = "<click Browse>";
        rootFolderName = theMusicExplorer.uriToName(Uri.parse(rootFolder));
        folderViewWidth = FOLDER_NOMINAL_VIEW_WIDTH;
        folderViewHeight = FOLDER_NOMINAL_VIEW_HEIGHT;
        fileViewWidth = TRACK_NOMINAL_VIEW_WIDTH;
        autoDisplayColumns = AUTO_DISPLAY_COLUMNS;
        autoDisplayFolders = ANDROID_AUTO_DISPLAY_FOLDERS;
        currentFolder = null;
        currentFile = null;
        lastPlayedFile = null;
        resumePlayOnStart = true;
        exitOnBTDisconnect = false;
        /*
         This property isn't reset
         btPermissionsRequestCount = 0;
         */
        currentTrackMS = -1;
        uuid = UUID.randomUUID().toString();
        savePreferences();
    }

    /**
     * Saves the current preferences to the SharedPreferences object.
     */
    public void savePreferences() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isInitialSetup", isInitialSetup);
        editor.putString("rootFolderName", rootFolder);
        editor.putInt("folderViewWidth", folderViewWidth);
        editor.putInt("folderViewHeight", folderViewHeight);
        editor.putInt("fileViewWidth", fileViewWidth);
        editor.putInt("autoDisplayColumns", autoDisplayColumns);
        editor.putBoolean("autoDisplayFolders", autoDisplayFolders);
        editor.putString("currentFolder", null == currentFolder ? null : currentFolder.getUri().toString());
        editor.putString("currentFile", null == currentFile ? null : currentFile.getUri().toString());
        editor.putString("lastPlayedFile", null == lastPlayedFile ? null : lastPlayedFile.getUri().toString());
        editor.putBoolean("resumePlayOnStart", resumePlayOnStart);
        editor.putBoolean("exitOnBTDisconnect", exitOnBTDisconnect);
        editor.putInt("btPermissionsRequestCount", btPermissionsRequestCount);
        editor.putLong("currentTrackMS", currentTrackMS);

        editor.putString("uuid", uuid);

        editor.commit();
    }

    /**
     * Sets the value of the isInitialSetup property.
     *
     * @param v The new value for isInitialSetup.
     */
    public void setIsInitialSetup(Boolean v) {
        isInitialSetup = v;
    }

    /**
     * Gets the value of the isInitialSetup property.
     *
     * @return The value of isInitialSetup.
     */
