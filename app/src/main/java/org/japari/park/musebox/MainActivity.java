package org.japari.park.musebox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.title_toolbar);
        setSupportActionBar(toolbar);
    }

    public void enterEdit(View view) {
        Intent intent = new Intent(this, EditPlaylist.class);
        startActivity(intent);
    }
}
