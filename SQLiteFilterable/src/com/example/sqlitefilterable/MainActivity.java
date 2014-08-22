package com.example.sqlitefilterable;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;

public class MainActivity extends Activity {

    ListView list;
    EditText filterBox;

    private MyDbHelper dbHelper;
    private SQLiteDatabase db;
    Cursor cursor;
    SimpleCursorAdapter adapter;

    String[] queryColumns = new String[] { 
            MyDbHelper.COL_ID,
            MyDbHelper.COL_PLANET_NAME, 
            MyDbHelper.COL_PLANET_DIAMETER 
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MyDbHelper(this);

        list = (ListView) findViewById(R.id.list);
        filterBox = (EditText) findViewById(R.id.filter_text);

        filterBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onResume() {
        super.onResume();

        db = dbHelper.getReadableDatabase();

        cursor = db.query(MyDbHelper.TABLE_NAME, queryColumns, null, null,
                null, null, null);

        String[] showColumns = new String[] { MyDbHelper.COL_PLANET_NAME,
                MyDbHelper.COL_PLANET_DIAMETER };
        int[] views = new int[] { android.R.id.text1, android.R.id.text2 };

        adapter = new SimpleCursorAdapter(this,
                android.R.layout.two_line_list_item, cursor, showColumns, views);

        adapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                return fetchPlanetsByName(constraint.toString());
            }
        });

        list.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        cursor.close();
        db.close();
    }

    public Cursor fetchPlanetsByName(String searchText) throws SQLException {
        Cursor cursor = null;
        
        if (searchText == null || searchText.length() == 0) {
            cursor = db.query(MyDbHelper.TABLE_NAME, queryColumns, null, null,
                    null, null, null);

        } else {
            cursor = db.query(true, MyDbHelper.TABLE_NAME, queryColumns,
                    MyDbHelper.COL_PLANET_NAME + " LIKE '%"
                    + searchText + "%'", null, null, null,
                    null, null);
        }
        
        if (cursor != null) {
            cursor.moveToFirst();
        }
        
        return cursor;
    }
}
