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

        // Initialize the database if it hasn't been created yet
        if (properties.getIsInitialSetup()) {
            theDatabase.create();
            properties.setIsInitialSetup(false);
        }

        theSettings = this; // Store a reference to the current Settings activity

        // Set the content view (layout)
        setContentView(R.layout.settings);

        // Enable back button navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Retrieve application version information
        try {
            PackageManager manager = getApplicationContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getApplicationContext().getPackageName(), 0);
            String version = info.versionName;
            ((TextView) findViewById(R.id.version_info)).setText(String.format("Music Explorer: Version : %s-%s", version, BuildConfig.DEBUG ? "debug" : "release"));
            ((TextView) findViewById(R.id.build_date)).setText(String.format("Build date: %s", BuildConfig.BUILD_DATE));
        } catch (Exception ex) {
            // Handle any exceptions that might occur during version retrieval
        }

        // Initialize the root folder path from properties
        String s = Uri.parse(properties.getRootFolder()).getPath();
        if (-1 < s.lastIndexOf(':')) {
            s = s.substring(s.lastIndexOf(':') + 1); // Extract the path after the colon
        }
        ((TextView) findViewById(R.id.root_folder)).setText(s);

        // Set an onClickListener for the "Get Root Folder" button
        ((Button) findViewById(R.id.root_folder_get)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch an Intent to allow the user to select a document
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, properties.getRootFolder());
                startActivityForResult(intent, 9999);
            }
        });

        // Set the autoDisplayColumns value to the value in properties
        ((TextView) findViewById(R.id.auto_display_columns_value)).setText(String.format("%d", properties.getAutoDisplayColumns()));

        // Set onClickListeners for the "+" and "-" buttons for autoDisplayColumns
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

        // Set the autoDisplayFolderIconsSwitch to the value in properties
        ((Switch) findViewById(R.id.auto_display_folder_icons_switch)).setChecked(properties.getAutoDisplayFolders());

        // Set an onClickListener for the autoDisplayFolderIconsSwitch
        ((Switch) findViewById(R.id.auto_display_folder_icons_switch)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                properties.setAutoDisplayFolders(!properties.getAutoDisplayFolders());
            }
        });

        // Set the resumePlayOnStartSwitch to the value in properties
        ((Switch) findViewById(R.id.resume_play_switch)).setChecked(properties.getResumePlayOnStart());

        // Set an onClickListener for the resumePlayOnStartSwitch
        ((Switch) findViewById(R.id.resume_play_switch)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                properties.setResumePlayOnStart(((Switch) v).isChecked());
            }
        });

        // Set the exitOnBTDisconnectSwitch to the value in properties
        ((Switch) findViewById(R.id.exit_on_bt_disconnect_switch)).setChecked(properties.getExitOnBTDisconnect());

        // Set an onClickListener for the exitOnBTDisconnectSwitch
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

        // Set visibility for warning message
        ((TextView) findViewById(R.id.settings_set_root_warning)).setVisibility(INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 9999:
                // Handle the result