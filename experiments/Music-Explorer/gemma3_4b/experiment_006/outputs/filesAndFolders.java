```java
package com.envisionate.musicinfolders;

import androidx.documentfile.provider.DocumentFile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

/**
 * This class manages lists of folder and file names along with their URIs,
 * facilitating organization and retrieval of music files within a directory structure.
 * It provides methods for sorting, adding new entities, and accessing the collected data.
 */
public class filesAndFolders {

    private ArrayList<String> theFolderNames;
    private ArrayList<String> theFolderURis;
    private TreeMap<String,String> folderNameToUri = new TreeMap<String,String>();

    private ArrayList<String> theFileNames;
    private ArrayList<String> theFileURis;
    private TreeMap<String,String> fileNameToUri = new TreeMap<String,String>();

    /**
     * Constructs a `filesAndFolders` object, initializing the lists of folder and file names
     * along with their respective URIs.
     *
     * @param folders         A list of folder names.
     * @param files           A list of file names.
     * @param folderURis      A list of folder URIs.
     * @param fileURis        A list of file URIs.
     */
    public filesAndFolders(ArrayList<String> folders, ArrayList<String> files, ArrayList<String> folderURis, ArrayList<String> fileURis) {
        theFolderNames = folders;
        theFileNames = files;
        theFolderURis = folderURis;
        theFileURis = fileURis;
    }

    /**
     * Sorts the folder names and file names alphabetically (case-insensitive)
     * and updates the corresponding URIs in the ArrayLists.
     */
    public void sort() {

        theFolderNames.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareToIgnoreCase(o2);
            }
        });

        theFolderURis.clear();

        for ( String name : theFolderNames )
            theFolderURis.add(folderNameToUri.get(name));

        theFileNames.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareToIgnoreCase(o2);
            }
        });

        theFileURis.clear();

        for ( String name : theFileNames )
            theFileURis.add(fileNameToUri.get(name));
    }

    /**
     * Returns the total number of folders and files managed by this class.
     *
     * @return The total number of folders and files.
     */
    public int size() {
        return theFolderNames.size() + theFileNames.size();
    }

    /**
     * Adds a `DocumentFile` to the collection, categorizing it as either a folder
     * or a file and updating the associated lists and maps with the file's name
     * and URI.
     *
     * @param df The `DocumentFile` to add.
     */
    public void addEntity(DocumentFile df) {
        if ( df.isDirectory() ) {
            theFolderNames.add(df.getName());
            theFolderURis.add(df.getUri().toString());
            folderNameToUri.put(df.getName(),df.getUri().toString());
        } else {
            theFileNames.add(df.getName());
            theFileURis.add(df.getUri().toString());
            fileNameToUri.put(df.getName(),df.getUri().toString());
        }
    }

    /**
     * Returns the list of folder names.
     *
     * @return The list of folder names.
     */
    public ArrayList<String> getFolderNames() {
        return theFolderNames;
    }

    /**
     * Returns the list of folder URIs.
     *
     * @return The list of folder URIs.
     */
    public ArrayList<String> getFolderURis() {
        return theFolderURis;
    }

    /**
     * Returns the list of file names.
     *
     * @return The list of file names.
     */
    public ArrayList<String> getFileNames() {
        return theFileNames;
    }

    /**
     * Returns the list of file URIs.
     *
     * @return The list of file URIs.
     */
    public ArrayList<String> getFileURis() {
        return theFileURis;
    }
}
```