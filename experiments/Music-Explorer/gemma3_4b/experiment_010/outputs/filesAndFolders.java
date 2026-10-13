```java
package com.envisionate.musicinfolders;

import androidx.documentfile.provider.DocumentFile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

/**
 * This class manages a collection of music files and folders, storing their names and URIs.
 * It provides methods for sorting, calculating size, and adding new files and folders.
 */
public class filesAndFolders {

    private ArrayList<String> theFolderNames;
    private ArrayList<String> theFolderURis;
    private TreeMap<String,String> folderNameToUri = new TreeMap<String,String>();

    private ArrayList<String> theFileNames;
    private ArrayList<String> theFileURis;
    private TreeMap<String,String> fileNameToUri = new TreeMap<String,String>();

    /**
     * Constructs a new `filesAndFolders` object with initial lists for folder names, file names,
     * and their corresponding URIs.
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
     * Sorts the folder names and file names alphabetically (case-insensitive) and updates the
     * corresponding URIs in the ArrayLists.
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
     * Calculates the total number of files and folders in the collection.
     *
     * @return The total number of files and folders.
     */
    public int size() {
        return theFolderNames.size() + theFileNames.size();
    }

    /**
     * Adds a new `DocumentFile` to the collection, determining whether it's a folder or a file
     * and adding it to the appropriate ArrayList and TreeMap.
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
     * Returns a copy of the ArrayList containing all folder names.
     *
     * @return The ArrayList of folder names.
     */
    public ArrayList<String> getFolderNames() {
        return theFolderNames;
    }

    /**
     * Returns a copy of the ArrayList containing all folder URIs.
     *
     * @return The ArrayList of folder URIs.
     */
    public ArrayList<String> getFolderURis() {
        return theFolderURis;
    }

    /**
     * Returns a copy of the ArrayList containing all file names.
     *
     * @return The ArrayList of file names.
     */
    public ArrayList<String> getFileNames() {
        return theFileNames;
    }

    /**
     * Returns a copy of the ArrayList containing all file URIs.
     *
     * @return The ArrayList of file URIs.
     */
    public ArrayList<String> getFileURis() {
        return theFileURis;
    }
}
```