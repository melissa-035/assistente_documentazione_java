```java
/**
 * This class represents the Settings activity for the Music Explorer application.
 * It allows users to configure various settings such as auto-display columns,
 * root folder selection, Bluetooth disconnection behavior, and more.
 */
public class Settings extends AppCompatActivity {

    private Settings theSettings = null;
    private int permissionDeniedCount = 0;
    private Thread dbThread = null;
    private Boolean dbThreadDone = false;

    /**
     * Called when the activity is first created.
     * This method initializes the Settings activity, creates the database if it doesn't exist,
     * and sets up the UI elements. It also performs version and build date information retrieval.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (properties.getIsInitialSetup()) {
            theDatabase.create();
            properties.setIsInitialSetup(false);
        }

        theSettings = this;

        setContentView(R.layout.settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try {
            PackageManager manager = getApplicationContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getApplicationContext().getPackageName(), 0);
            String version = info.versionName;
            ((TextView)findViewById(R.id.version_info)).setText(String.format("Music Explorer: Version : %s-%s", version, BuildConfig.DEBUG ? "debug" : "release"));
            ((TextView)findViewById(R.id.build_date)).setText(String.format("Build date: %s", BuildConfig.BUILD_DATE));
        } catch (Exception ex) {
        }

        String s = Uri.parse(properties.getRootFolder()).getPath();
        if (-1 < s.lastIndexOf(':'))
            s = s.substring(s.lastIndexOf(':') + 1);

        ((TextView)findViewById(R.id.root_folder)).setText(s);

        ((Button)findViewById(R.id.root_folder_get)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, properties.getRootFolder());
                startActivityForResult(intent, 9999);
            }
        });

        ((TextView)findViewById(R.id.auto_display_columns_value)).setText(String.format("%d", properties.getAutoDisplayColumns()));

        ((TextView)findViewById(R.id.auto_display_columns_value_plus)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int val = properties.getAutoDisplayColumns() + 1;
                properties.setAutoDisplayColumns(val);
                ((TextView)findViewById(R.id.auto_display_columns_value)).setText(String.format("%d", properties.getAutoDisplayColumns()));
            }
        });

        ((TextView)findViewById(R.id.auto_display_columns_value_minus)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int val = Math.max(1, properties.getAutoDisplayColumns() - 1);
                properties.setAutoDisplayColumns(val);
                ((TextView)findViewById(R.id.auto_display_columns_value)).setText(String.format("%d", properties.getAutoDisplayColumns()));
            }
        });

        ((Switch)findViewById(R.id.auto_display_folder_icons_switch)).setChecked(properties.getAutoDisplayFolders());

        ((Switch)findViewById(R.id.auto_display_folder_icons_switch)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                properties.setAutoDisplayFolders(!properties.getAutoDisplayFolders());
            }
        });

        ((Switch)findViewById(R.id.resume_play_switch)).setChecked(properties.getResumePlayOnStart());

        ((Switch)findViewById(R.id.resume_play_switch)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                properties.setResumePlayOnStart(((Switch)v).isChecked());
            }
        });

        ((Switch)findViewById(R.id.exit_on_bt_disconnect_switch)).setChecked(properties.getExitOnBTDisconnect());

        ((Switch)findViewById(R.id.exit_on_bt_disconnect_switch)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                properties.setExitOnBTDisconnect(((Switch)v).isChecked());
                if (properties.getExitOnBTDisconnect()) {
                    if (2 == properties.getBtPermissionsRequestCount()) {
                        properties.setExitOnBTDisconnect(false);
                        ((Switch)findViewById(R.id.exit_on_bt_disconnect_switch)).setChecked(properties.getExitOnBTDisconnect());
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

        ((TextView)findViewById(R.id.settings_set_root_warning)).setVisibility(INVISIBLE);
    }

    /**
     * Called when the activity is destroyed.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Called when an activity loses focus and is about to close.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 9999:
                Uri uri = data.getData();
                properties.setRootFolder(uri);
                getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                String s = Uri.parse(properties.getRootFolder()).getPath();
                if (-1 < s.lastIndexOf(':'))
                    s = s.substring(s.lastIndexOf(':') + 1);
                ((TextView)findViewById(R.