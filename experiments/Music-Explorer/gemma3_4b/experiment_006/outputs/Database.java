```java
/**
 * Provides a database interface for managing music folder structures.
 * This class uses an SQLite database to store information about music files and folders,
 * allowing for efficient retrieval and manipulation of music library data.
 */
public class Database {

    /**
     * The context associated with the application.
     */
    private static Context theContext;

    /**
     * The thread used for database operations.
     */
    private static Thread dbThread = null;

    /**
     * The SQLite database helper object.
     */
    private static DBSchema.dbHelper dbHelper = null;

    /**
     * Constructs a new Database object, initializing the context and database helper.
     *
     * @param ctx The application context.
     */
    public Database(Context ctx) {
        theContext = ctx;
        dbHelper = new DBSchema.dbHelper(theContext);
    }

    /**
     * Opens a database connection for writing.
     *
     * @return A SQLiteDatabase object representing the database connection.
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
     * Checks if there are any folders and files associated with the given URI.
     *
     * @param folderUri The URI of the folder to check.
     * @return True if the folder exists in the database, false otherwise.
     */
    public Boolean hasFoldersAndFiles(Uri folderUri) {
        return 0 < dbHelper.sqlQueryInt("SELECT COUNT(*) FROM folders WHERE folderUri = ?",new String [] {folderUri.toString()});
    }

    /**
     * Retrieves the names, URIs, and file information for folders and files
     * associated with the given URI.
     *
     * @param folderUri       The URI of the folder to query.
     * @param folderNames     A list to store folder names.
     * @param folderURis      A list to store folder URIs.
     * @param fileNames       A list to store file names.
     * @param fileURis        A list to store file URIs.
     * @return True if the folder exists in the database and has associated files,
     *         false otherwise.
     */
    public Boolean getFoldersAndFiles(Uri folderUri, ArrayList<String> folderNames,ArrayList<String> folderURis,ArrayList<String> fileNames,ArrayList<String> fileURis) {

        String strFolderUri = folderUri.toString();

        long folderId = dbHelper.sqlQueryInt("SELECT _id FROM folders WHERE folderUri = ?",new String [] {strFolderUri} );
        String oldHashString = dbHelper.sqlQueryString("SELECT hash FROM folders WHERE folderUri = ?",new String [] {strFolderUri} );

        dbHelper.fetchMultiple("files",new String [] {"fileName","fileUri"},
                    " WHERE folderId = " + Long.toString(folderId) + " AND isFolder = 1 ORDER BY fileName COLLATE NOCASE ASC",new ArrayList [] {folderNames,folderURis});

        dbHelper.fetchMultiple("files",new String [] {"fileName","fileUri"},
                    " WHERE folderId = " + Long.toString(folderId) + " AND isFolder = 0 ORDER BY fileName COLLATE NOCASE ASC",new ArrayList [] {fileNames,fileURis});

        List<String> onDiskFileList = Util.getFileNamesFromTreeUri(theMusicExplorer.getApplicationContext(),folderUri);

        onDiskFileList.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareToIgnoreCase(o2);
            }
        });

        String onDiskHash = String.join("",onDiskFileList);

        if ( ! onDiskHash.equals(oldHashString) )
            return false;

        List<String> inDBFileList = new ArrayList<String>();

        for ( String fn : folderNames )
            inDBFileList.add(fn);

        for ( String fn : fileNames )
            inDBFileList.add(fn);

        inDBFileList.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareToIgnoreCase(o2);
            }
        });

        String inDBHash = String.join("",inDBFileList);

        if ( ! inDBHash.equals(oldHashString) )
            return false;

        return true;
    }

    /**
     * Adds a folder and its associated files to the database.
     *
     * @param parentFolder      The parent DocumentFile object.
     * @param thisFolder        The current DocumentFile object being added.
     * @param folderNames       A list of folder names.
     * @param folderURis        A list of folder URIs.
     * @param fileNames         A list of file names.
     * @param fileURis          A list of file URIs.
     * @param doReplace         Whether to replace an existing folder if it exists.
     * @param retainParentUri Whether to retain the parent URI.
     */
    public void addFolder(DocumentFile parentFolder,DocumentFile thisFolder, ArrayList<String> folderNames,
                            ArrayList<String> folderURis,ArrayList<String> fileNames,ArrayList<String> fileURis,Boolean doReplace,Boolean retainParentUri) {

        String folderUri = thisFolder.getUri().toString();
        int countExisting = dbHelper.sqlQueryInt("SELECT COUNT(*) FROM folders WHERE folderUri = ?",new String [] {folderUri});

        if ( 0 < countExisting && ! doReplace )
            return;

        if ( retainParentUri ) {
            String parentUri = dbHelper.sqlQueryString("SELECT parentUri FROM folders WHERE folderUri = ?",new String [] {folderUri});
            parentFolder = DocumentFile.fromTreeUri(theMusicExplorer,Uri.parse(parentUri));
        }

        if ( 0 < countExisting && doReplace ) {
            long folderId = dbHelper.sqlQueryInt("SELECT _id FROM folders WHERE folderUri = ?",new String [] {folderUri});
            dbHelper.sqlCommand("DELETE FROM files WHERE folderId = ?",new Object [] {folderId});
            dbHelper.sqlCommand("DELETE FROM folders WHERE _id = ?", new Object [] {folderId});
        }

        String thisFolderName = DatabaseUtils.sqlEscapeString(thisFolder.getName());

        int countRows = folderNames.size() + fileNames.size();

        String [][] theValues = new String[countRows][4];

        List<String> allNames = new ArrayList<String>();

        int folderCount = 0;
        for ( String folderName : folderNames ) {
            theValues[folderCount][0] = "";
            theValues[folderCount][1] = folderURis.get(folderCount);
            theValues[folderCount][2] = folderName;
            theValues[folderCount][3] = "1";
            allNames.add(folderName);
            folderCount++;
        }

        int fileCount = 0;
        for ( String fileName : fileNames ) {
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

        String hashString = String.join("",allNames);

        long folderId = dbHelper.insertOne("folders",new String [] {"parentUri","folderUri","folderName","hash"},
                                            new String [] {null == parentFolder ? null : parentFolder.getUri().toString(),folderUri,thisFolderName,hashString.toString()});

        for ( int k = 0; k < folderCount; k++ )
            theValues[k][0] = Long.toString(folderId);

        for ( int k = 0; k < fileCount; k++ )
            theValues[folderCount + k][0] = Long.toString(folderId);

        try {
            dbHelper.insertMultiple("files",new String [] {"folderId","fileUri","fileName","isFolder"},theValues);
        } catch ( Exception ex ) {
            Log.d("MusicExplorer",ex.getMessage());
        }

    }

}
```