```java
package com.envisionate.musicinfolders.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;

/**
 * This class defines the database schema for the MusicInfoFolders application.
 * It provides classes for managing the tables for folders and files.
 */
public class DBSchema {

    /**
     * Private constructor to prevent instantiation of this class.
     */
    private DBSchema() {}

    /**
     * Represents the structure of the 'folders' table.
     *
     * @author [Your Name]
     */
    private static class folders implements BaseColumns {
        /**
         * SQL statement to create the 'folders' table.
         */
        private static final String SQL_CREATE_FOLDERS = "CREATE TABLE folders (" +
                                                        folders._ID + " INTEGER PRIMARY KEY," +
                                                        "parentUri TEXT DEFAULT NULL," +
                                                        "folderUri TEXT NOT NULL," +
                                                        "folderName TEXT NOT NULL," +
                                                        "hash TEXT NOT NULL)";

        /**
         * SQL statement to drop the 'folders' table.
         */
        private static final String SQL_DELETE_FOLDERS = "DROP TABLE IF EXISTS folders";
    }

    /**
     * Represents the structure of the 'files' table.
     *
     * @author [Your Name]
     */
    private static class tracks implements BaseColumns {
        /**
         * SQL statement to create the 'files' table.
         */
        private static final String SQL_CREATE_TRACKS = "CREATE TABLE files (" +
                                                       tracks._ID + " INTEGER PRIMARY KEY," +
                                                       "folderId INTEGER NOT NULL," +
                                                       "fileUri TEXT NOT NULL," +
                                                       "fileName TEXT NOT NULL," +
                                                       "isFolder INTEGER NOT NULL," +
                                                       "FOREIGN KEY(folderId) REFERENCES folders(_id))";

        /**
         * SQL statement to drop the 'files' table.
         */
        private static final String SQL_DELETE_TRACKS = "DROP TABLE IF EXISTS tracks";
    }

    /**
     * Represents the helper class for managing the SQLite database.
     */
    public static class dbHelper extends SQLiteOpenHelper {

        /**
         * Version of the database schema.
         */
        public static final int DATABASE_VERSION = 1;

        /**
         * Name of the database file.
         */
        public static final String DATABASE_NAME = "database.db";

        /**
         * The SQLite database instance.
         */
        private static SQLiteDatabase theSQLiteDatabase = null;

        /**
         * Constructs a new dbHelper object.
         *
         * @param context The application context.
         */
        public dbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        /**
         * Executes a SQL command.
         *
         * @param sql The SQL command to execute.
         * @param parameters The parameters for the SQL command.
         */
        public void sqlCommand(String sql, Object[] parameters) {
            theSQLiteDatabase.execSQL(sql, parameters);
        }

        /**
         * Inserts a new row into a table.
         *
         * @param tableName The name of the table to insert into.
         * @param columnNames An array of column names.
         * @param values An array of values to insert.
         * @return The ID of the newly inserted row.
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
         * Inserts multiple rows into a table.
         *
         * @param tableName The name of the table to insert into.
         * @param columnNames An array of column names.
         * @param values A 2D array of values to insert.
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
         * Fetches multiple rows from a table.
         *
         * @param tableName The name of the table to fetch from.
         * @param columnNames An array of column names.
         * @param sqlClause An optional SQL clause (e.g., WHERE clause).
         * @param results A 2D array to store the results.
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
         * Executes a SQL query that returns a single integer.
         *
         * @param sql The SQL query to execute.
         * @param parameters The parameters for the SQL query.
         * @return The result of the query.
         */
        public int sqlQueryInt(String sql, String[] parameters) {
            Cursor theCursor = theSQLiteDatabase.rawQuery(sql, parameters);
            theCursor.moveToFirst();
            int result = theCursor.getInt(0);
            theCursor.close();
            return result;
        }

        /**
         * Executes a SQL query that returns a single string.
         *
         * @param sql The SQL query to execute.
         * @param parameteres The parameters for the SQL query.
         * @return The result of the query.
         */
        public static String sqlQueryString(String sql, String[] parameteres) {
            Cursor theCursor = theSQLiteDatabase.rawQuery(sql, parameteres);
            theCursor.moveToFirst();
            String result = theCursor.getString(0);
            theCursor.close();
            return result;
        }

        /**
         * Creates the database tables.
         *
         * @param db The SQLite database object.
         */
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(folders.SQL_CREATE_FOLDERS);
            db.execSQL(tracks.SQL_CREATE_TRACKS);
        }

        /**
         * Called when the database is opened.
         *
         * @param db The SQLite database object.
         */
        public void onOpen(SQLiteDatabase db) {
            theSQLiteDatabase = db;
        }

        /**
         * Called when the database is upgraded.
         *
         * @param db The SQLite database object.
         * @param oldVersion The old version of the database.
         * @param newVersion The new version of the database.
         */
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(folders.SQL_DELETE_FOLDERS);
            db.execSQL(tracks.SQL_DELETE_TRACKS);
            onCreate(db);
        }

        /**
         * Called when the database is downgraded.
         *
         * @param db The SQLite database object.
         * @param oldVersion The old version of the database.
         * @param newVersion The new version of the database.
         */
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }
}
```