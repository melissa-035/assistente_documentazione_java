```java
package com.envisionate.musicinfolders.database;

import static com.envisionate.musicinfolders.Globals.theMusicExplorer;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.documentfile.provider.DocumentFile;

import com.envisionate.musicinfolders.Util;
import com.envisionate.musicinfolders.filesAndFolders;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * A class to handle database operations for managing music folders.
 * This class provides methods to create, query, and update data related to music folders and files in an SQLite database.
 */
public class Database {

    private static Context theContext;
    private static Thread dbThread = null;
    private static DBSchema.dbHelper dbHelper = null;

    /**
     * Constructs a new Database object, initializing the database helper.
     *
     * @param ctx The context to use for the database operations.
     */
    public Database(Context ctx) {
        theContext = ctx;
        dbHelper = new DBSchema.dbHelper(theContext);
    }

    /**
     * Opens a database connection for writing.
     *
     * @return A SQLiteDatabase object for writing to the database.
     */
    public SQLiteDatabase open() {
        return dbHelper.getWritableDatabase();
    }

    /**
     * Creates a new database instance.
     */
    public void create() {
        dbHelper.getWritableDatabase();
    }

    /**
     * Checks if a folder with the given URI exists in the database.
     *
     * @param folderUri The URI of the folder to check.
     * @return True if the folder exists in the database, false otherwise.
     */
    public Boolean hasFoldersAndFiles(Uri folderUri) {
        return 0 < dbHelper.sqlQueryInt("SELECT COUNT(*) FROM folders WHERE folderUri = ?", new String[]{folderUri.toString()});
    }

    /**
     * Retrieves the names, URIs, and file names/URIs associated with a folder from the database.
     * This method fetches both folder and file information based on a given folder URI.
     *
     * @param folderUri          The URI of the folder.
     * @param folderNames        A list to store the folder names.
     * @param folderURis         A list to store the folder URIs.
     * @param fileNames          A list to store the file names.
     * @param fileURis           A list to store the file URIs.
     * @return True if the folder and its associated files exist in the database, false otherwise.
     */
    public Boolean getFoldersAndFiles(Uri folderUri, ArrayList<String> folderNames, ArrayList<String> folderURis,
                                        ArrayList<String> fileNames, ArrayList<String> fileURis) {

        String strFolderUri = folderUri.toString();

        long folderId = dbHelper.sqlQueryInt("SELECT _id FROM folders WHERE folderUri = ?", new String[]{strFolderUri});
        String oldHashString = dbHelper.sqlQueryString("SELECT hash FROM folders WHERE folderUri = ?", new String[]{strFolderUri});

        dbHelper.fetchMultiple("files", new String[]{"fileName", "fileUri"},
                " WHERE folderId = " + Long.toString(folderId) + " AND isFolder = 1 ORDER BY fileName COLLATE NOCASE ASC", new ArrayList[]{folderNames, folderURis});

        dbHelper.fetchMultiple("files", new String[]{"fileName", "fileUri"},
                " WHERE folderId = " + Long.toString(folderId) + " AND isFolder = 0 ORDER BY fileName COLLATE NOCASE ASC", new ArrayList[]{fileNames, fileURis});

        List<String> onDiskFileList = Util.getFileNamesFromTreeUri(theMusicExplorer.getApplicationContext(), folderUri);

        onDiskFileList.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareToIgnoreCase(o2);
            }
        });

        String onDiskHash = String.join("", onDiskFileList);

        if (!onDiskHash.equals(oldHashString))
            return false;

        List<String> inDBFileList = new ArrayList<>();

        for (String fn : folderNames)
            inDBFileList.add(fn);

        for (String fn : fileNames)
            inDBFileList.add(fn);

        inDBFileList.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareToIgnoreCase(o2);
            }
        });

        String inDBHash = String.join("", inDBFileList);

        if (!inDBHash.equals(oldHashString))
            return false;

        return true;
    }

    /**
     * Adds a folder and its associated files to the database.
     *
     * @param parentFolder     The document file representing the parent folder.
     * @param thisFolder       The document file representing the folder being added.
     * @param fAndF             The files and folders object containing folder and file names/URIs.
     * @param doReplace        Whether to replace an existing folder with the same URI.
     * @param retainParentUri Whether to retain the parent URI during the operation.
     */
    public void addFolder(DocumentFile parentFolder, DocumentFile thisFolder, filesAndFolders fAndF, Boolean doReplace, Boolean retainParentUri) {
        addFolder(parentFolder, thisFolder, fAndF.getFolderNames(), fAndF.getFolderURis(), fAndF.getFileNames(), fAndF.getFileURis(), doReplace, retainParentUri);
    }

    /**
     * Adds a folder and its associated files to the database, allowing for replacing existing folders and retaining parent URIs.
     *
     * @param parentFolder     The document file representing the parent folder.
     * @param thisFolder       The document file representing the folder being added.
     * @param folderNames       A list of folder names.
     * @param folderURis        A list of folder URIs.
     * @param fileNames         A list of file names.
     * @param fileURis          A list of file URIs.
     * @param doReplace         Whether to replace an existing folder with the same URI.
     * @param retainParentUri   Whether to retain the parent URI during the operation.
     */
    public void addFolder(DocumentFile parentFolder, DocumentFile thisFolder, ArrayList<String> folderNames,
                            ArrayList<String> folderURis, ArrayList<String> fileNames, ArrayList<String> fileURis,
                            Boolean doReplace, Boolean retainParentUri) {

        String folderUri = thisFolder.getUri().toString();
        int countExisting = dbHelper.sqlQueryInt("SELECT COUNT(*) FROM folders WHERE folderUri = ?", new String[]{folderUri});

        if (0 < countExisting && !doReplace)
            return;

        if (retainParentUri) {
            String parentUri = dbHelper.sqlQueryString("SELECT parentUri FROM folders WHERE folderUri = ?", new String[]{folderUri});
            parentFolder = DocumentFile.fromTreeUri(theMusicExplorer, Uri.parse(parentUri));
        }

        if (0 < countExisting && doReplace) {
            long folderId = dbHelper.sqlQueryInt("SELECT _id FROM folders WHERE folderUri = ?", new String[]{folderUri});
            dbHelper.sqlCommand("DELETE FROM files WHERE folderId = ?", new Object[]{folderId});
            dbHelper.sqlCommand("DELETE FROM folders WHERE _id = ?", new Object[]{folderId});
        }

        String thisFolderName = DatabaseUtils.sqlEscapeString(thisFolder.getName());

        int countRows = folderNames.size() + fileNames.size();

        String [][] theValues = new String[countRows][4];

        List<String> allNames = new ArrayList<>();

        int folderCount = 0;
        for (String folderName : folderNames) {
            theValues[folderCount][0] = "";
            theValues[folderCount][1] = folderURis.get(folderCount);
            theValues[folderCount][2] = folderName;
            theValues[folderCount][3] = "1";
            allNames.add(folderName);
            folderCount++;
        }

        int fileCount = 0;
        for (String fileName : fileNames) {
            theValues[folderCount + fileCount][0] = "";
            theValues[folderCount + fileCount][1] = fileURis.get(fileCount);
            theValues[folderCount + fileCount][2] = fileName;
            theValues[folderCount + fileCount][3] = "0";
            allNames.add(fileName);
            fileCount++;
        }

        allNames.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareToIgnoreCase(o2);
            }
        });

        String hashString = String.join("", allNames);

        long folderId = dbHelper.insertOne("folders", new String[]{"parentUri", "folderUri", "folderName", "hash"},
                new String[]{null == parentFolder ? null : parentFolder.getUri().toString(), folderUri, thisFolderName, hashString.toString()});

        for (int k = 0; k < folderCount; k++)
            theValues[k][0] = Long.toString(folderId);

        for (int k = 0; k < fileCount; k++)
            theValues[folderCount + k][0] = Long.toString(folderId);

        try {
            dbHelper.insertMultiple("files", new String[]{"folderId", "fileUri", "fileName", "isFolder"}, theValues);
        } catch (Exception ex) {
            Log.d("MusicExplorer", ex.getMessage