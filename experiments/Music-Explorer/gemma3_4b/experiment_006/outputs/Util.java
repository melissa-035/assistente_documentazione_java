```java
import static android.os.Looper.getMainLooper;
import static com.envisionate.carservice.CarService.ANDROID_AUTO_ICON_SIDE;
import static com.envisionate.musicinfolders.Globals.properties;
import static com.envisionate.musicinfolders.Globals.theDatabase;
import static com.envisionate.musicinfolders.Globals.theMusicExplorer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.text.LineBreakConfig;
import android.graphics.text.LineBreaker;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.documentfile.provider.DocumentFile;

import java.util.ArrayList;
import java.util.List;

/**
 * A utility class containing various helper methods for the music folders application.
 * This class provides functionality for handling file and folder operations,
 * generating folder images, and broadcasting intents.
 */
public class Util {

    /**
     * Constructs a new Util instance.
     */
    public Util() {}

    /**
     * Retrieves a list of files and folders from a DocumentFile object.
     *
     * This method prioritizes retrieving data from the database if available.
     * If the database is not available, it attempts to retrieve the data
     * from the DocumentFile tree.
     *
     * @param overrideCurrent An optional DocumentFile object to override the current folder.
     * @return A filesAndFolders object containing the list of files and folders.
     */
    public filesAndFolders getCurrentFilesAndFolders(DocumentFile overrideCurrent) {

        DocumentFile theRoot = null;

        DocumentFile theParent = properties.getCurrentFolder();

        if ( ! ( null == overrideCurrent ) )
            properties.setCurrentFolder(overrideCurrent);

        DocumentFile tryDbDocument = properties.getCurrentFolder();
        Boolean tryDb = true;
        Boolean useDb = true;

        if ( null == tryDbDocument ) {
            try {
                tryDbDocument = DocumentFile.fromTreeUri(theMusicExplorer, Uri.parse(properties.getRootFolder()));
            } catch ( Exception ex ) {
                tryDb = false;
                useDb = false;
            }
        }

        if ( tryDb )
            useDb = theDatabase.hasFoldersAndFiles(tryDbDocument.getUri());

        Boolean retainParentUri = false;

        if ( ! useDb ) {
            if ( null == properties.getCurrentFolder() ) {
                try {
                    theRoot = DocumentFile.fromTreeUri(theMusicExplorer, Uri.parse(properties.getRootFolder()));
                } catch ( Exception ex ) {
                    return null;
                }
            }
        } else {
            filesAndFolders returnVal = new filesAndFolders(new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>());
            if ( theDatabase.getFoldersAndFiles(tryDbDocument.getUri(),returnVal.getFolderNames(),returnVal.getFolderURis(),returnVal.getFileNames(),returnVal.getFileURis()) )
                return returnVal;
            retainParentUri = true;
        }

        if ( null == properties.getCurrentFolder() )
            properties.setCurrentFolder(theRoot);

        DocumentFile [] entityArray = properties.getCurrentFolder().listFiles();

        filesAndFolders theFilesAndFolders = new filesAndFolders(new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>());

        if ( 0 == entityArray.length )
            return theFilesAndFolders;

        for ( DocumentFile df : entityArray ) {
            if ( ! df.getName().startsWith(".") )
                theFilesAndFolders.addEntity(df);
        }

        theFilesAndFolders.sort();

        theDatabase.addFolder(theParent,null == properties.getCurrentFolder() ? theRoot : properties.getCurrentFolder(),theFilesAndFolders,true,retainParentUri);

        return theFilesAndFolders;
    }

    /**
     * Generates an image representation of a folder.
     *
     * This method creates a ConstraintLayout containing a folder icon and the folder name.
     * It dynamically adjusts the text size to fit within the image bounds,
     * ensuring text is readable.
     *
     * @param theActivity The Activity in which to create the image.
     * @param folderName  The name of the folder to display in the image.
     * @return A Bitmap representing the folder image.
     */
    public static Bitmap getFolderImage(AppCompatActivity theActivity, String folderName) {

        ConstraintLayout layout = new ConstraintLayout(theActivity);

        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(224,224);

        layout.setId(R.id.folder_layout);
        layout.setLayoutParams(layoutParams);

        ImageView imageView = null;

        if ( properties.getAutoDisplayFolders() ) {
            imageView = new ImageView(theActivity);
            imageView.setId(R.id.folder_image_view);
            ConstraintLayout.LayoutParams imageViewParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setImageResource(R.drawable.