```java
/**
 * This class provides a database schema and helper for managing music folder and file information.
 * It uses SQLite for local storage and offers functionalities for creating tables, inserting data,
 * querying data, and handling database upgrades.
 */
public class DBSchema {

    /**
     * Private constructor to prevent instantiation of this class.
     */
    private DBSchema() {}

    /**
     * Represents the structure for folders in the music library.
     */
    private static class folders {
        /**
         * SQL statement to create the 'folders' table.
         *
         * @param SQL_CREATE_FOLDERS The SQL statement for creating the table.
         */
        private static final String SQL_CREATE_FOLDERS = "CREATE TABLE folders (" + folders._ID + " INTEGER PRIMARY KEY," +
                                                                "parentUri TEXT DEFAULT NULL," +
                                                                "folderUri TEXT NOT NULL," +
                                                                "folderName TEXT NOT NULL," +
                                                                "hash TEXT NOT NULL)";

        /**
         * SQL statement to drop the 'folders' table.
         *
         * @param SQL_DELETE_FOLDERS The SQL statement for dropping the table.
         */
        private static final String SQL_DELETE_FOLDERS = "DROP TABLE IF EXISTS folders";
    }

    /**
     * Represents the structure for files (tracks) within the music library.
     */
    private static class tracks {
        /**
         * SQL statement to create the 'tracks' (files) table.
         *
         * @param SQL_CREATE_TRACKS The SQL statement for creating the table.
         */
        private static final String SQL_CREATE_TRACKS = "CREATE TABLE files (" + tracks._ID + " INTEGER PRIMARY KEY," +
                                                                "folderId INTEGER NOT NULL," +
                                                                "fileUri TEXT NOT NULL," +
                                                                "fileName TEXT NOT NULL," +
                                                                "isFolder INTEGER NOT NULL," +
                                                                "FOREIGN KEY(folderId) REFERENCES folders(_id))";

        /**
         * SQL statement to drop the 'tracks' table.
         *
         * @param SQL_DELETE_TRACKS The SQL statement for dropping the table.
         */
        private static final String SQL_DELETE_TRACKS = "DROP TABLE IF EXISTS tracks";
    }

    /**
     * This class manages the SQLite database. It extends SQLiteOpenHelper to handle database creation,
     * upgrades, and provides methods for interacting with the database.
     */
    public static class dbHelper extends SQLiteOpenHelper {

        /**
         * Database version number.
         */
        public static final int DATABASE_VERSION = 1;

        /**
         * Database name.
         */
        public static final String DATABASE_NAME = "database.db";

        /**
         * The SQLite database instance. This is a static variable to maintain a single database connection.
         */
        private static SQLiteDatabase theSQLiteDatabase = null;

        /**
         * Constructs a dbHelper object.
         *
         * @param context The application context.
         */
        public dbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        /**
         * Executes a SQL command with parameters.
         *
         * @param sql       The SQL command to execute.
         * @param parameters An array of parameters to pass to the SQL command.
         */
        public void sqlCommand(String sql, Object[] parameters) {
            theSQLiteDatabase.execSQL(sql, parameters);
        }

        /**
         * Inserts a row into the specified table.
         *
         * @param tableName    The name of the table to insert into.
         * @param columnNames  An array of column names.
         * @param values       An array of values to insert.
         * @return The ID of the newly inserted row, or -1 if the insertion fails.
         */
        public long insertOne(String tableName, String[] columnNames, String[] values) {
            ContentValues v = new ContentValues();
            int k = 0;
            for (String s : columnNames) {
                v.put(s, values[k++]);
            }
            return theSQLiteDatabase.insert(tableName, null, v);
        }

        /**
         * Inserts multiple rows into the specified table.
         *
         * @param tableName    The name of the table to insert into.
         * @param columnNames  An array of column names.
         * @param values       A two-dimensional array of values to insert. Each inner array represents a row.
         */
        public void insertMultiple(String tableName, String[] columnNames, String[][] values) {

            String sql = "";
            sql += "INSERT INTO " + tableName + "(";
            for (String s : columnNames) {
                sql += s + ",";
            }
            sql = sql.substring(0, sql.length() - 1) + ") ";

            int valuesLength = 0;

            sql += " VALUES ";
            for (String[] s2 : values) {
                sql += "(";
                for (String s3 : s2) {
                    sql += "?,";
                    valuesLength++;
                }
                sql = sql.substring(0, sql.length() - 1) + "),";
            }
            sql = sql.substring(0, sql.length() - 1);

            String[] parameters = new String[valuesLength];

            int k = 0;
            for (String[] s2 : values) {
                for (String s3 : s2) {
                    parameters[k++] = s3;
                }
            }

            try {
                theSQLiteDatabase.execSQL(sql, parameters);
            } catch (Exception ex) {
                Log.d("MusicExplorer", ex.getMessage());
            }
        }

        /**
         * Fetches multiple rows from the specified table based on the provided query clause.
         *
         * @param tableName    The name of the table to query.
         * @param columnNames  An array of column names to retrieve.
         * @param sqlClause     An optional SQL clause to filter the results. If null, all rows are returned.
         * @param results       An array of ArrayLists to store the results.
         */
        public void fetchMultiple(String tableName, String[] columnNames, String sqlClause, ArrayList<String>[] results) {

            String sql = "SELECT ";
            for (String s : columnNames) {
                sql += s + ",";
            }
            sql = sql.substring(0, sql.length() - 1);

            sql += " FROM " + tableName + (null == sqlClause ? "" : sqlClause);

            Cursor theCursor = theSQLiteDatabase.rawQuery(sql, null);
            while (theCursor.moveToNext()) {
                for (int k = 0; k < results.length; k++) {
                    results[k].add(theCursor.getString(k));
                }
            }
            theCursor.close();
        }

        /**
         * Queries an integer value from the database.
         *
         * @param sql       The SQL query to execute.
         * @param parameters An array of parameters to pass to the query.
         * @return The integer value returned by the query, or 0 if the query returns no results.
         */
        public int sqlQueryInt(String sql, String[] parameters) {
            Cursor theCursor = theSQLiteDatabase.rawQuery(sql, parameters);
            theCursor.moveToFirst();
            int result = theCursor.getInt(0);
            theCursor.close();
            return result;
        }

        /**
         * Queries a string value from the database.
         *
         * @param sql       The SQL query to execute.
         * @param parameteres An array of parameters to pass to the query.
         * @return The string value returned by the query, or null if the query returns no results.
         */
        public static String sqlQueryString(String sql, String[] parameteres) {
            Cursor theCursor = theSQLiteDatabase.rawQuery(sql, parameteres);
            theCursor.moveToFirst();
            String result = theCursor.getString(0);
            theCursor.close();
            return result;
        }

        /**
         * Creates the database tables. This method is called when the database is created or when the database schema is changed.
         *
         * @param db The database to be created or opened.
         */
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(folders.SQL_CREATE_FOLDERS);
            db.execSQL(tracks.SQL_CREATE_TRACKS);
        }

        /**
         * Called when the database acquires a lock or gains exclusive access to the database.
         *
         * @param db The database that is being opened.
         */
        public void onOpen(SQLiteDatabase db) {
            theSQLiteDatabase = db;
        }

        /**
         * Called when the database is upgraded to a new version.
         *
         * @param db  The database being upgraded.
         * @param oldVersion The old version number.
         * @param newVersion The new version number.
         */
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(folders.SQL_DELETE_FOLDERS);
            db.execSQL(tracks.SQL_DELETE_TRACKS);
            onCreate(db);
        }

        /**
         * Called when the database is downgraded to an older version.
         *
         * @param db  The database being downgraded.
         * @param oldVersion The old version number.
         * @param newVersion The new version number.
         */
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }
}
```