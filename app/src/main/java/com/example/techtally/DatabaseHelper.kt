package com.example.techtally

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper(private val context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "UserDatabase.db" // Name of the database file
        private const val DATABASE_VERSION = 15             // Version number of the database schema
        private const val TABLE_NAME = "data"               // Name of the table in the database
        private const val COLUMN_ID = "id"                  // Column for the user ID
        private const val COLUMN_USERNAME = "username"      // Column for the username
        private const val COLUMN_EMAIL = "email"            // Column for the email
        private const val COLUMN_PASSWORD = "password"      // Column for the password
    }

    // Called when the database is created for the first time
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = ("CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +      // ID auto-increments
                "$COLUMN_USERNAME TEXT, " +            // Username column
                "$COLUMN_EMAIL TEXT, " +               // Email column
                "$COLUMN_PASSWORD TEXT)")              // Password column
        db?.execSQL(createTableQuery)                 // Execute the SQL query to create the table
    }

    // Called when the database version is upgraded
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME" // Drop the existing table
        db?.execSQL(dropTableQuery)                             // Execute the SQL query to drop the table
        onCreate(db)                                            // Create a new table with the updated schema
    }

    // Adds a new user's information to the database
    fun insertUser(username: String, email: String, password: String): Long {
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, username)          // Insert the username
            put(COLUMN_EMAIL, email)                // Insert the email
            put(COLUMN_PASSWORD, password)          // Insert the password
        }

        val db = writableDatabase                                // Get a writable database instance
        return db.insert(TABLE_NAME, null, values) // Insert the and return the row ID
    }

    // Checks if a user input username and password exists
    fun readUser(username: String, password: String): Boolean {
        val db = readableDatabase                                           // Get a readable database instance
        val selection = "$COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?"     // Query condition
        val selectionArgs = arrayOf(username, password)                     // Arguments for the query
        val cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null) // Execute the query

        val userExists = cursor.count > 0   // Check if any rows match the query
        cursor.close()                      // Close the cursor to free up resources
        return userExists                   // Return true if user exists, otherwise false
    }

    // Checks if user input email and password exists
    fun readUserByEmail(email: String, password: String): Boolean {
        val db = readableDatabase                                       // Get a readable database instance
        val selection = "$COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?"    // Query condition
        val selectionArgs = arrayOf(email, password)                    // Arguments for the query
        val cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null) // Execute the query

        val userExists = cursor.count > 0   // Check if any rows match the query
        cursor.close()                      // Close the cursor to free up resources
        return userExists                   // Return true if user exists, otherwise false
    }
}


