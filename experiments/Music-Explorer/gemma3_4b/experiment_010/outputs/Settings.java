```java
/**
 * This class represents the Settings activity for the Music Explorer application.
 * It allows users to configure various settings related to the application's behavior,
 * including the root folder, auto-display options, and Bluetooth disconnection settings.
 */
public class Settings extends AppCompatActivity {

    /**
     * A reference to the Settings object itself.
     */
    private Settings theSettings = null;

    /**
     * The number of times Bluetooth permission has been requested.
     */
    private int permissionDeniedCount = 0;

    /**
     * A thread used for database operations.
     */
    private Thread dbThread = null;

    /**
     * A boolean flag indicating whether the database thread has finished executing.
     */
    private Boolean dbThreadDone = false;

    /**
     * Called when the activity is first created.
     *
     * This method initializes the Settings activity and performs the following actions:
     * 1. Creates the database if it hasn't been created yet.
     * 2. Sets the 'isInitialSetup' property to false.
     * 3. Instantiates the Settings object.
     * 4. Sets the content view to the settings layout.
     * 5. Displays the app version and build date in the respective TextViews.
     * 6. Sets the initial root folder to the value retrieved from the properties object.
     * 7. Sets up listeners for the "Get root folder" button and auto-display column buttons.
     * 8. Sets up listeners for the auto-display folder icons and resume play switches.
     * 9. Initializes the exit on BT disconnect switch.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // If initial setup hasn't occurred, create the database and mark it as complete.
        if (properties.getIsInitialSetup()) {
            theDatabase.create();
            properties.setIsInitialSetup(false);
        }

        theSettings = this;

        setContentView(R.layout.settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Try to get the app version and build date from the package information.
        try {
            PackageManager manager = getApplicationContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getApplicationContext().getPackageName(), 0);
            String version = info.versionName;
            ((TextView) findViewById(R.id.version_info)).setText(String.format("Music Explorer: Version : %s-%s", version, BuildConfig.DEBUG ? "debug" : "release"));
            ((TextView) findViewById(R.id.build_date)).setText(String.format("Build date: %s", BuildConfig.BUILD_DATE));
        } catch (Exception ex) {
            // Handle any exceptions that occur during version or build date retrieval.
        }

        // Set the root folder from the properties object.
        String s = Uri.parse(properties.getRootFolder()).getPath();
        if (-1 < s.lastIndexOf(':')) {
            s = s.substring(s.lastIndexOf(':') + 1);
        }
        ((TextView) findViewById(R.id.root_folder)).setText(s);

        // Set listener for "Get root folder" button
        ((Button) findViewById(R.id.root_folder_get)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start an intent to open the document tree, providing the current root folder.
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, properties.getRootFolder());
                startActivityForResult(intent, 9999);
            }
        });

        // Set the auto-display columns value.
        ((TextView) findViewById(R.id.auto_display_columns_value)).setText(String.format("%d", properties.getAutoDisplayColumns()));

        // Set listener for increasing auto-display columns.
        ((TextView) findViewById(R.id.auto_display_columns_value_plus)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val = properties.getAutoDisplayColumns() + 1;
                properties.setAutoDisplayColumns(val);
                ((TextView) findViewById(R.id.auto_display_columns_value)).setText(String.format("%d", properties.getAutoDisplayColumns()));
            }
        });

        // Set listener for decreasing auto-display columns.
        ((TextView) findViewById(R.id.auto_display_columns_value_minus)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val = Math.max(1, properties.getAutoDisplayColumns() - 1);
                properties.setAutoDisplayColumns(val);
                ((TextView) findViewById(R.id.auto_display_columns_value)).setText(String.format("%d", properties.getAutoDisplayColumns()));
            }
        });

        // Set the auto-display folder icons switch.
        ((Switch) findViewById(R.id.auto_display_folder_icons_switch)).setChecked(properties.getAutoDisplayFolders());

        // Set listener for the auto-display folder icons switch.
        ((Switch) findViewById(R.id.auto_display_folder_icons_switch)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                properties.setAutoDisplayFolders(!properties.getAutoDisplayFolders());
            }
        });

        // Set the resume play on start switch.
        ((Switch) findViewById(R.id.resume_play_switch)).setChecked(properties.getResumePlayOnStart());

        // Set listener for the resume play on start switch.
        ((Switch) findViewById(R.id.resume_play_switch)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                properties.setResumePlayOnStart(((Switch) v).isChecked());
            }
        });

        // Set the exit on BT disconnect switch.
        ((Switch) findViewById(R.id.exit_on_bt_disconnect_switch)).setChecked(properties.getExitOnBTDisconnect());

        // Set listener for the exit on BT disconnect switch.
        ((Switch) findViewById(R.id.exit_on_bt_disconnect_switch)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                properties.setExitOnBTDisconnect(((Switch) v).isChecked());
                if (properties.getExitOnBTDisconnect()) {
                    if (2 == properties.getBtPermissionsRequestCount()) {
                        properties.setExitOnBTDisconnect(false);
                        ((Switch) findViewById(R.id.exit_on_bt_disconnect_switch)).setChecked(properties.getExitOnBTDisconnect());
                        new AlertDialog.Builder(theSettings)
                                .setTitle("Note")
                                .setMessage(R.string.permission_reset)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        theSettings.systemSettings();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                    } else {
                        properties.setBtPermissionsRequestCount(properties.getBtPermissionsRequestCount() + 1);
                        ActivityCompat.requestPermissions(theSettings, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 1);
                    }
                }
            }
        });

        // Set the visibility of the warning message for the root folder.
        ((TextView) findViewById(R.id.settings_set_root_warning)).setVisibility(INVISIBLE);
    }

    