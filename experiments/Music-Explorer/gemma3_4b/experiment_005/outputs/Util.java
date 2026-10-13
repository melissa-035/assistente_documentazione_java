```java
package com.envisionate.musicinfolders;

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

public class Util {

    /**
     * Default constructor for the Util class.
     */
    public Util() {}

    /**
     * Retrieves the current files and folders from various sources, prioritizing the database.
     *
     * @param overrideCurrent An optional DocumentFile to override the current folder. If provided, the
     *                         current folder will be set to this DocumentFile.
     * @return A filesAndFolders object containing the current files and folders.
     */
    public filesAndFolders getCurrentFilesAndFolders(DocumentFile overrideCurrent) {

        DocumentFile theRoot = null;

        DocumentFile theParent = properties.getCurrentFolder();

        // If an overrideDocumentFile is provided, set the current folder to it.
        if ( ! ( null == overrideCurrent ) )
            properties.setCurrentFolder(overrideCurrent);

        DocumentFile tryDbDocument = properties.getCurrentFolder();
        Boolean tryDb = true;
        Boolean useDb = true;

        // Check if the database folder exists and is valid.
        if ( null == tryDbDocument ) {
            try {
                tryDbDocument = DocumentFile.fromTreeUri(theMusicExplorer, Uri.parse(properties.getRootFolder()));
            } catch ( Exception ex ) {
                tryDb = false;
                useDb = false;
            }
        }

        // If the database is available and valid, use it.
        if ( tryDb )
            useDb = theDatabase.hasFoldersAndFiles(tryDbDocument.getUri());

        Boolean retainParentUri = false;

        // If the database is not available, fallback to the root document file.
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

        // If the current folder is null, set it to the root document file.
        if ( null == properties.getCurrentFolder() )
            properties.setCurrentFolder(theRoot);

        // Get the list of files and folders from the current folder.
        DocumentFile [] entityArray = properties.getCurrentFolder().listFiles();

        filesAndFolders theFilesAndFolders = new filesAndFolders(new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>());

        // Iterate over the files and folders and add them to the list.
        if ( 0 == entityArray.length )
            return theFilesAndFolders;

        for ( DocumentFile df : entityArray ) {
            // Only add files/folders that don't start with a dot (hidden files/folders).
            if ( ! df.getName().startsWith(".") )
                theFilesAndFolders.addEntity(df);
        }

        // Sort the files and folders alphabetically.
        theFilesAndFolders.sort();

        // Add the current folder and its contents to the database.
        theDatabase.addFolder(theParent,null == properties.getCurrentFolder() ? theRoot : properties.getCurrentFolder(),theFilesAndFolders,true,retainParentUri);

        // Return the list of files and folders.
        return theFilesAndFolders;
    }

    /**
     * Creates a visual representation of a folder with its name.
     *
     * @param theActivity The Activity where the image is being displayed.
     * @param folderName The name of the folder.
     * @return A Bitmap representing the folder image, or null if an error occurs.
     */
    public static Bitmap getFolderImage(AppCompatActivity theActivity, String folderName) {

        ConstraintLayout layout = new ConstraintLayout(theActivity);

        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(224,224);

        layout.setId(R.id.folder_layout);
        layout.setLayoutParams(layoutParams);

        ImageView imageView = null;

        // If auto display folders are enabled, display a default folder icon.
        if ( properties.getAutoDisplayFolders() ) {
            imageView = new ImageView(theActivity);
            imageView.setId(R.id.folder_image_view);
            ConstraintLayout.LayoutParams imageViewParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setImageResource(R.drawable.folder);
            imageViewParams.bottomToBottom = R.id.folder_layout;
            imageViewParams.leftToLeft = R.id.folder_layout;
            imageViewParams.topToTop = R.id.folder_layout;
            imageViewParams.verticalBias = 0.0f;
            imageView.setLayoutParams(imageViewParams);
        }

        float textSize = 0.0f;

        TextView textView = new TextView(theActivity);

        textView.setId(R.id.folder_text_view);

        ConstraintLayout.LayoutParams textViewParams = new ConstraintLayout.LayoutParams(0,224);

        textViewParams.width = 224;
        textViewParams.height = 224;

        if ( ! ( null == imageView ) ) {
            textViewParams.leftMargin = 16;
