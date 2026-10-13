```java
package com.envisionate.musicinfolders;

import static com.envisionate.musicinfolders.Globals.properties;
import static com.envisionate.musicinfolders.Globals.theDatabase;
import static com.envisionate.musicinfolders.Globals.theMusicExplorer;
import static com.envisionate.musicinfolders.Globals.theMusicExplorerInterface;
import static com.envisionate.musicinfolders.Globals.theMusicPlayer;
import static com.envisionate.musicinfolders.Globals.theSQLiteDatabase;
import static com.envisionate.musicinfolders.Globals.theUtilities;
import static com.envisionate.musicinfolders.Globals.trackLaunchedActivities;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowMetrics;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;
import androidx.media3.common.MediaMetadata;
import androidx.media3.common.Player;

import com.envisionate.musicinfolders.database.DBSchema;
import com.envisionate.musicinfolders.database.Database;
import com.envisionate.musicinfolders.database.DBSchema.dbHelper;

import com.envisionate.musicinfolders.interfaces.IMusicExplorer;

import java.util.ArrayList;

public class MusicExplorer extends AppCompatActivity implements Player.Listener {

    /**