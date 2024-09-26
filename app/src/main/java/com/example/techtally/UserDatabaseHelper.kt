package com.example.techtally

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// UserDatabaseHelper is responsible for managing user data within an SQLite database
class UserDatabaseHelper(private val context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Companion object holds constants for the database name, version, table name, and column names
    companion object {
        private const val DATABASE_NAME = "UserDatabase.db" // Name of the database file
        private const val DATABASE_VERSION = 15              // Version number of the database schema
        private const val TABLE_NAME = "data"                // Name of the table in the database
        private const val COLUMN_ID = "id"                   // Column for the user ID
        private const val COLUMN_USERNAME = "username"       // Column for the username
        private const val COLUMN_EMAIL = "email"             // Column for the email
        private const val COLUMN_PASSWORD = "password"       // Column for the password
        private const val COLUMN_ROLE = "role"               // Column for user role
    }

    // Called when the database is created for the first time
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = ("CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " + // ID auto-increments
                "$COLUMN_USERNAME TEXT, " +                        // Username column
                "$COLUMN_EMAIL TEXT, " +                           // Email column
                "$COLUMN_PASSWORD TEXT, " +                        // Password column
                "$COLUMN_ROLE TEXT)")                              // Role column
        db?.execSQL(createTableQuery)                         // Execute the SQL query to create the table

        // Insert a predefined admin account into the table during database creation
        val adminUsername = "admin"
        val adminEmail = "admin@example.com"
        val adminPassword = "admin123"
        val adminRole = "admin"

        // Insert the predefined admin credentials into the database
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, adminUsername)
            put(COLUMN_EMAIL, adminEmail)
            put(COLUMN_PASSWORD, adminPassword)
            put(COLUMN_ROLE, adminRole)
        }
        db?.insert(TABLE_NAME, null, values) // Insert admin data into the table
    }

    // Called when the database version is upgraded
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME" // Drop the existing table
        db?.execSQL(dropTableQuery)                             // Execute the SQL query to drop the table
        onCreate(db)                                            // Create a new table with the updated schema
    }

    // Inserts a new user's data into the database
    fun insertUser(username: String, email: String, password: String): Long {
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, username)          // Insert the username
            put(COLUMN_EMAIL, email)                // Insert the email
            put(COLUMN_PASSWORD, password)          // Insert the password
            put(COLUMN_ROLE, "user")                // Default role is 'user'
        }

        val db = writableDatabase
        return db.insert(TABLE_NAME, null, values) // Insert the user and return the new row ID
    }

    // Validates user credentials using either email or username and a password
    fun validateUser(input: String, password: String): Boolean {
        val db = this.readableDatabase  // Get a readable instance of the database
        // Query to check if the user exists by matching either email or username with the password
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM $TABLE_NAME WHERE ($COLUMN_EMAIL = ? OR $COLUMN_USERNAME = ?) AND $COLUMN_PASSWORD = ?",
            arrayOf(input, input, password) // Input will match either email or username
        )
        val userExists = cursor.moveToFirst()   // Check if any matching row exists
        cursor.close()                          // Close the cursor to free resources
        return userExists                       // Return true if user exists, otherwise false
    }

    // Retrieves the role of a user (either by username or email)
    fun getUserRole(input: String): String? {
        val db = this.readableDatabase  // Get a readable instance of the database
        // Query to get the role of the user by either username or email
        val cursor: Cursor = db.rawQuery(
            "SELECT $COLUMN_ROLE FROM $TABLE_NAME WHERE $COLUMN_EMAIL = ? OR $COLUMN_USERNAME = ?",
            arrayOf(input, input)   // Input will match either email or username
        )
        var userRole: String? = null    // Initialize variable to hold the user role
        if (cursor.moveToFirst()) {
            // If a match is found, get the role from the cursor
            userRole = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROLE))
        }
        cursor.close()
        return userRole // Return the role, or null if no user is found
    }

    // Checks if a username and password match a user in the database
    fun readUser(username: String, password: String): Boolean {
        val db = readableDatabase   // Get a readable instance of the database
        val selection = "$COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?"
        val selectionArgs = arrayOf(username, password) // Arguments to match the username and password
        val cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null)

        val userExists = cursor.count > 0   // Check if any rows match the query
        cursor.close()
        return userExists       // Return true if user exists, otherwise false
    }

    // Checks if the email and password match a user in the database
    fun readUserByEmail(email: String, password: String): Boolean {
        val db = readableDatabase   // Get a readable instance of the database
        // Query to check if the email and password match a user
        val selection = "$COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?" // Query condition
        val selectionArgs = arrayOf(email, password) // Arguments for the query
        val cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null) // Execute the query

        val userExists = cursor.count > 0 // Check if any rows match the query
        cursor.close() // Close the cursor to free up resources
        return userExists // Return true if user exists, otherwise false
    }
}