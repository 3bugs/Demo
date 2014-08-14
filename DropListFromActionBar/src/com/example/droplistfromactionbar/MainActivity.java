package com.example.droplistfromactionbar;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;

public class MainActivity extends Activity implements OnMenuItemClickListener {

    ImageView image;
    
    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.actionbar_layout, null);
        image = (ImageView) layout.findViewById(R.id.image_view);
        
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu();
            }
        });

        actionBar.setCustomView(layout);
        actionBar.setDisplayShowCustomEnabled(true);
    }
    
    private void showPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, image);
        popupMenu.setOnMenuItemClickListener(MainActivity.this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.item_profile:
            Toast.makeText(MainActivity.this, "Profile selected", Toast.LENGTH_SHORT).show();
            return true;
        case R.id.item_logout:
            Toast.makeText(MainActivity.this, "Logout selected", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}
