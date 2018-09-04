package com.example.user.musebox;

import android.app.ActionBar;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

public class Editlist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editlist);
        Toolbar toolbar = findViewById(R.id.edit_toolbar);
        setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editpl_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
