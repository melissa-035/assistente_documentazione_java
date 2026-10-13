```java
/**
 * A class to hold application properties.
 *
 */
public class Properties {

    /**
     * The number of columns to display in the auto display.
     */
    private static final int AUTO_DISPLAY_COLUMNS = 5;

    /**
     * Whether the initial setup is complete.
     */
    private Boolean isInitialSetup = true;

    /**
     * The root folder URI.
     */
    private String rootFolder = null;

    /**
     * The root folder name.
     */
    private String rootFolderName = null;

    /**
     * A unique identifier.
     */
    private String uuid = null;

    /**
     * The folder view width.
     */
    private int folderViewWidth = FOLDER_NOMINAL_VIEW_WIDTH;

    /**
     * The folder view height.
     */
    private int folderViewHeight = FOLDER_NOMINAL_VIEW_HEIGHT;

    /**
     * The file view width.
     */
    private int fileViewWidth = TRACK_NOMINAL_VIEW_WIDTH;

    /**
     * The number of auto display columns.
     */
    private int autoDisplayColumns = AUTO_DISPLAY_COLUMNS;

    /**
     * Whether the auto display folders are enabled.
     */
    private Boolean autoDisplayFolders = ANDROID_AUTO_DISPLAY_FOLDERS;

    /**
     * The current folder document file.
     */
    private DocumentFile currentFolder = null;

    /**
     * The current file document file.
     */
    private DocumentFile currentFile = null;

    /**
     * The last played file document file.
     */
    private DocumentFile lastPlayedFile = null;

    /**
     * Whether to resume play on start.
     */
    private Boolean resumePlayOnStart = true;

    /**
     * Whether to exit on Bluetooth disconnect.
     */
    private Boolean exitOnBTDisconnect = false;

    /**
     * The Bluetooth permissions request count.
     */
    private int btPermissionsRequestCount = 0;

    /**
     * The current track milliseconds.
     */
    private long currentTrackMS = 0;

    /**
     * The shared preferences.
     */
    private static SharedPreferences preferences = null;

    /**
     * Constructs a new Properties object.
     *
     * @param preferences the shared preferences.
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
            if (null == currentFolder) {
                currentFolder = DocumentFile.fromTreeUri(theMusicExplorer, Uri.parse(rootFolder));
            }
            currentFile = DocumentFile.fromTreeUri(theMusicExplorer, Uri.parse(preferences.getString("currentFile", null)));
            lastPlayedFile = DocumentFile.fromTreeUri(theMusicExplorer, Uri.parse(preferences.getString("lastPlayedFile", null)));
        } catch (Exception ex) {
        }

        autoDisplayColumns = preferences.getInt("autoDisplayColumns", AUTO_DISPLAY_COLUMNS);
        autoDisplayFolders = preferences.getBoolean("autoDisplayFolders", ANDROID_AUTO_DISPLAY_FOLDERS);
        resumePlayOnStart = preferences.getBoolean("resumePlayOnStart", resumePlayOnStart);
        exitOnBTDisconnect = preferences.getBoolean("exitOnBTDisconnect", exitOnBTDisconnect);
        btPermissionsRequestCount = preferences.getInt("btPermissionsRequestCount", btPermissionsRequestCount);
        currentTrackMS = preferences.getLong("currentTrackMS", -1);

        uuid = preferences.getString("uuid", UUID.randomUUID().toString());
    }

    /**
     * Resets the properties to their default values.
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
     * Saves the properties to shared preferences.
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
     * Sets whether the initial setup is complete.
     *
     * @param v the value to set
     */
    public void setIsInitialSetup(Boolean v) {
        isInitialSetup = v;
    }

    /**
     * Gets whether the initial setup is complete.
     *
     * @return whether the initial setup is complete
     */
    public Boolean getIsInitialSetup() {
        return