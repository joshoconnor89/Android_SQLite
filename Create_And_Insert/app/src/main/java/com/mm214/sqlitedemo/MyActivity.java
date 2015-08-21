package com.mm214.sqlitedemo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.os.Bundle;import android.widget.Toast;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;


public class MyActivity extends Activity {

    private static final String SAMPLE_DB_NAME = "TrekBook";
    private static final String SAMPLE_TABLE_NAME = "Info";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        SQLiteDatabase sampleDB =  this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
        sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " +
                SAMPLE_TABLE_NAME +
                " (LastName VARCHAR, FirstName VARCHAR," +
                " Rank VARCHAR);");
        sampleDB.execSQL("INSERT INTO " +
                SAMPLE_TABLE_NAME +
                " Values ('Kirk','James, T','Captain');");
        sampleDB.close();
        Toast.makeText(this, "DB Created!", Toast.LENGTH_LONG).show();
    }


}



