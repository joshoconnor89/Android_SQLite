package com.example.sqliteexample2;

import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.app.Activity;

public class MainActivity extends Activity {

	// for our logs
	public static final String LOG_TAG = "MainActivity.java"    ;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        //DatabaseHandler is a custom class that does not need to be imported.
		DatabaseHandler databaseH = new DatabaseHandler(MainActivity.this);



//CRUD (CREATE, READ, UPDATE, DELETE)

		// CREATE
		LocationObject LocationObj = new LocationObject("Quezon City",
				"The place where I work.");
        //call the create method in the DatabaseHandler.java
		if (databaseH.create(LocationObj)) {
			Log.v(LOG_TAG, "Record successfully created.");
		}

		// READ
            //look for tag in logcat in ADM
            List<LocationObject> locations = databaseH.read();
        for (LocationObject record : locations) {
			Log.v(LOG_TAG, "ID: " + record.locationId);
			Log.v(LOG_TAG, "Name: " + record.locationName);
			Log.v(LOG_TAG, "Description: " + record.locationDescription);
		}

		// UPDATE
		if (databaseH.update(1, "Quezon City, PH",
				"The place where I work AND CODE.")) {
			Log.v(LOG_TAG, "Record successfully updated.");
		}

		// DELETE
		if (databaseH.delete(1)) {
			Log.v(LOG_TAG, "Record successfully deleted.");
		}
	}

}

/*
 * Represents location details or fields.
 */
class LocationObject {

	public int locationId;
	public String locationName;
	public String locationDescription;

	// constructor for adding
	public LocationObject(String locationName, String locationDescription) {
		this.locationName = locationName;
		this.locationDescription = locationDescription;
	}

	// constructor for updating
	public LocationObject(int locationId, String locationName,
			String locationDescription) {
		this.locationId = locationId;
		this.locationName = locationName;
		this.locationDescription = locationDescription;
	}

}
