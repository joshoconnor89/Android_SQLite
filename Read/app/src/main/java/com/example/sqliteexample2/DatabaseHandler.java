package com.example.sqliteexample2;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;

//sql-lite imports
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


//sql-lite wrapper
public class DatabaseHandler extends SQLiteOpenHelper {

	// for our logs
	public static final String LOG_TAG = "DatabaseHandler.java";

	// database version
	private static final int DATABASE_VERSION = 2;

	// database name
	protected static final String DATABASE_NAME = "NinjaDatabase2";

	// table details
	public String tableName = "locations";

	public String fieldLocationId = "id";
	public String fieldLocationName = "name";
	public String fieldLocationDescription = "description";

	// constructor
	public DatabaseHandler(Context context) {

		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {

		String sql = "";

		sql += "CREATE TABLE " + tableName;
		sql += " ( ";
        //line below automatically assigns id integer number for database object
		sql += fieldLocationId + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
		sql += fieldLocationName + " TEXT, ";
		sql += fieldLocationDescription + " TEXT ";
		sql += " ) ";

		db.execSQL(sql);

	}

	/*
	 * When upgrading the database, it will drop the current table and recreate.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		String sql = "DROP TABLE IF EXISTS " + tableName;
		db.execSQL(sql);

		onCreate(db);
	}

	/*
	 * Create location record.
	 * 
	 * @param - location contains location details to be added as single row.
    */
	public boolean create(LocationObject location) {

		boolean createSuccessful = false;
		
		ContentValues values = new ContentValues();

		values.put(fieldLocationName, location.locationName);
		values.put(fieldLocationDescription, location.locationDescription);

		SQLiteDatabase db = this.getWritableDatabase();

		createSuccessful = db.insert(tableName, null, values) > 0;
		db.close();

		return createSuccessful;
	}

	/*
	 * Read all location record.
	 */
	public List<LocationObject> read() {

		List<LocationObject> recordsList = new ArrayList<LocationObject>();

		// select query
		String sql = "";
		sql += "SELECT * FROM " + tableName;
		sql += " ORDER BY " + fieldLocationId + " DESC";

		SQLiteDatabase db = this.getWritableDatabase();

		// execute the query
        //cursor keeps track of where we are at the database.
		Cursor cursor = db.rawQuery(sql, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
                //reference these variables and output them (ex:  fieldLocationId)
				int locationId = Integer.parseInt(cursor.getString(cursor
						.getColumnIndex(fieldLocationId)));

				String locationName = cursor.getString(cursor
						.getColumnIndex(fieldLocationName));

				String locationDescription = cursor.getString(cursor
						.getColumnIndex(fieldLocationDescription));

				LocationObject location = new LocationObject(locationId,
						locationName, locationDescription);

				// add to list
				recordsList.add(location);

			} while (cursor.moveToNext());
		}

		// close the database
		db.close();

		// return the list of records
		return recordsList;
	}

	/*
	 * Update location record.
	 * 
	 * @param id - will identify which record is to be updated.
	 * 
	 * @param name - the new location name to be saved.
	 * 
	 * @param description - the new location description to be saved.
	 */
	public boolean update(int id, String name, String description) {

		boolean updateSuccessful = false;

		ContentValues values = new ContentValues();

		values.put(fieldLocationName, name);
		values.put(fieldLocationDescription, description);

		// you can use AND if you have multiple conditions
		String where = fieldLocationId + " = ?";

		// you should use commas when you have multiple conditions
		String[] whereArgs = { Integer.toString(id) };

		SQLiteDatabase db = this.getWritableDatabase();

		// use the update command
		updateSuccessful = db.update(tableName, values, where, whereArgs) > 0;
		db.close();

		return updateSuccessful;
	}

	/*
	 * Delete location record.
	 * 
	 * @param id - to identify which location record is to be deleted.
	 */
	public boolean delete(int id) {
		boolean deleteSuccessful = false;

		SQLiteDatabase db = this.getWritableDatabase();
		deleteSuccessful = db.delete(tableName, "id ='" + id + "'", null) > 0;
		db.close();

		return deleteSuccessful;

	}

	/*
	 * For more methods like reading single record or counting records, see my
	 * full version of Android Sqlite tutorial, specifically,
	 * LocationTableController.java
	 */
}
