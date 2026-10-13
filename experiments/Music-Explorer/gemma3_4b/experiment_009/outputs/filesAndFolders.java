```java
package com.envisionate.musicinfolders;

import androidx.documentfile.provider.DocumentFile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

/**
 * This class manages a collection of music files and folders, 
 * providing methods for sorting and accessing their names and URIs.
 */
public class filesAndFolders {

    /**
     * An ArrayList to store the names of the music folders.
     */
    private ArrayList<String> theFolderNames;

    /**
     * An ArrayList to store the URIs of the music folders.
     */
    private ArrayList<String> theFolderURis;

    /**
     * A TreeMap to store the mapping between folder names and their URIs.
     * The TreeMap maintains the keys in sorted order.
     */
    private TreeMap<String, String> folderNameToUri = new TreeMap<String, String>();

    /**
     * An ArrayList to store the names of the music files.
     */
    private ArrayList<String> theFileNames;

    /**
     * An ArrayList to store the URIs of the music files.
     */
    private ArrayList<String> theFileURis;

    /**
     * A TreeMap to store the mapping between file names and their URIs.
     * The TreeMap maintains the keys in sorted order.
     */
    private TreeMap<String, String> fileNameToUri = new TreeMap<String, String>();

    /**
     * Constructs a new filesAndFolders object.
     *
     * @param folders        An ArrayList of folder names.
     * @param files          An ArrayList of file names.
     * @param folderURis     An ArrayList of folder URIs.
     * @param fileURis       An ArrayList of file URIs.
     */
    public filesAndFolders(ArrayList<String> folders, ArrayList<String> files, ArrayList<String> folderURis, ArrayList<String> fileURis) {
        theFolderNames = folders;
        theFileNames = files;
        theFolderURis = folderURis;
        theFileURis = fileURis;
    }

    /**
     * Sorts the folder names and file names alphabetically (case-insensitive).
     * Also updates the corresponding URIs in the ArrayLists and the TreeMap.
     */
    public void sort() {

        theFolderNames.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareToIgnoreCase(o2);
            }
        });

        theFolderURis.clear();

        for (String name : theFolderNames)
            theFolderURis.add(folderNameToUri.get(name));

        theFileNames.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareToIgnoreCase(o2);
            }
        });

        theFileURis.clear();

        for (String name : theFileNames)
            theFileURis.add(fileNameToUri.get(name));
    }

    /**
     * Returns the total number of files and folders in the collection.
     *
     * @return The total number of files and folders.
     */
    public int size() {
        return theFolderNames.size() + theFileNames.size();
    }

    /**
     * Adds a DocumentFile to the collection.
     *
     * @param df The DocumentFile object to add.
     */
    public void addEntity(DocumentFile df) {
        if (df.isDirectory()) {
            theFolderNames.add(df.getName());
            theFolderURis.add(df.getUri().toString());
            folderNameToUri.put(df.getName(), df.getUri().toString());
        } else {
            theFileNames.add(df.getName());
            theFileURis.add(df.getUri().toString());
            fileNameToUri.put(df.getName(), df.getUri().toString());
        }
    }

    /**
     * Returns the ArrayList of folder names.
     *
     * @return The ArrayList of folder names.
     */
    public ArrayList<String> getFolderNames() {
        return theFolderNames;
    }

    /**
     * Returns the ArrayList of folder URIs.
     *
     * @return The ArrayList of folder URIs.
     */
    public ArrayList<String> getFolderURis() {
        return theFolderURis;
    }

    /**
     * Returns the ArrayList of file names.
     *
     * @return The ArrayList of file names.
     */
    public ArrayList<String> getFileNames() {
        return theFileNames;
    }

    /**
     * Returns the ArrayList of file URIs.
     *
     * @return The ArrayList of file URIs.
     */
    public ArrayList<String> getFileURis() {
        return theFileURis;
    }
}
```