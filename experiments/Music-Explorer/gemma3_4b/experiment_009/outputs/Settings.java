```java
package com.envisionate.musicinfolders;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.envisionate.musicinfolders.Globals.properties;
import static com.envisionate.musicinfolders.Globals.theDatabase;
import static com.envisionate.musicinfolders.Globals.theMusicExplorer;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.car.app.annotations.ExperimentalCarApi;
import androidx.core.app.ActivityCompat;

public class Settings extends AppCompatActivity {

    private Settings theSettings = null;
    private int permissionDeniedCount = 0;
    private Thread dbThread = null;
    private Boolean dbThreadDone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Create the database if it hasn't been created yet. This ensures the database is initialized.
        if (properties.getIsInitialSetup()) {
            theDatabase.create();
            properties.setIsInitialSetup(false);
        }

        theSettings = this;

        // Set the content view to the settings layout. This loads the UI elements.
        setContentView(R.layout.settings);

        // Enable the back button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Retrieve version and build date information from BuildConfig.
        try {
            PackageManager manager = getApplicationContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getApplicationContext().getPackageName(), 0);
            String version = info.versionName;
            ((TextView) findViewById(R.id.version_info)).setText(String.format("Music Explorer: Version : %s-%s", version, BuildConfig.DEBUG ? "debug" : "release"));
            ((TextView) findViewById(R.id.build_date)).setText(String.format("Build date: %s", BuildConfig.BUILD_DATE));
        } catch (Exception ex) {
            // Handle potential exceptions during version/build date retrieval.
        }

        // Get the root folder URI from properties and display it in the TextView.
        String s = Uri.parse(properties.getRootFolder()).getPath();
        if (-1 < s.lastIndexOf(':')) {
            s = s.substring(s.lastIndexOf(':') + 1);
        }
        ((TextView) findViewById(R.id.root_folder)).setText(s);

        // Set an OnClickListener for the button to open the root folder selection dialog.
        ((Button) findViewById(R.id.root_folder_get)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to open the document tree.
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                // Set the initial URI for the intent.
                intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, properties.getRootFolder());
                // Start the activity to select the root folder.
                startActivityForResult(intent, 9999);
            }
        });

        // Set the value of the TextView for auto-display columns.
        ((TextView) findViewById(R.id.auto_display_columns_value)).setText(String.format("%d", properties.getAutoDisplayColumns()));

        // Set OnClickListeners for the buttons to increment and decrement auto-display columns.
        ((TextView) findViewById(R.id.auto_display_columns_value_plus)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val = properties.getAutoDisplayColumns() + 1;
                properties.setAutoDisplayColumns(val);
                ((TextView) findViewById(R.id.auto_display_columns_value)).setText(String.format("%d", properties.getAutoDisplayColumns()));
            }
        });

        ((TextView) findViewById(R.id.auto_display_columns_value_minus)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val = Math.max(1, properties.getAutoDisplayColumns() - 1);
                properties.setAutoDisplayColumns(val);
                ((TextView) findViewById(R.id.auto_display_columns_value)).setText(String.format("%d", properties.getAutoDisplayColumns()));
            }
        });

        // Set the state of the Switch for auto-display folder icons.
        ((Switch) findViewById(R.id.auto_display_folder_icons_switch)).setChecked(properties.getAutoDisplayFolders());

        // Set an OnClickListener for the Switch to toggle the auto-display folder icons.
        ((Switch) findViewById(R.id.auto_display_folder_icons_switch)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                properties.setAutoDisplayFolders(!properties.getAutoDisplayFolders());
            }
        });

        // Set the state of the Switch for resume play on start.
        ((Switch) findViewById(R.id.resume_play_switch)).setChecked(properties.getResumePlayOnStart());

        // Set an OnClickListener for the Switch to toggle resume play on start.
        ((Switch) findViewById(R.id.resume_play_switch)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                properties.setResumePlayOnStart(((Switch) v).isChecked());
            }
        });

        // Set the state of the Switch for exit on BT disconnect.
        ((Switch) findViewById(R.id.exit_on_bt_disconnect_switch)).setChecked(properties.getExitOnBTDisconnect());

        // Set an OnClickListener for the Switch to toggle exit on BT disconnect.
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

        // Set the visibility of the TextView to indicate the root folder warning.
        ((TextView) findViewById(R.id.settings_set_root_warning)).setVisibility(INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Clean up