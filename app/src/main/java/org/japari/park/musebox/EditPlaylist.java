package org.japari.park.musebox;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;


public class EditPlaylist extends AppCompatActivity
                          implements AddPlaylistDialogFragment.AddPlaylistDialogListener {

    private static String plFileName = "Playlists.json";

    private ArrayList<Playlist> playlists;
    private PlaylistAdapter plAdapter;
    private RecyclerView plRecyclerView;
    private TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editlist);
        Toolbar toolbar = findViewById(R.id.edit_toolbar);
        setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        playlists = new ArrayList<>();
        plAdapter = new PlaylistAdapter(getBaseContext(), playlists);
        plRecyclerView = findViewById(R.id.playlist_view);
        emptyView = findViewById(R.id.emptyList_View);
        emptyView.setText(R.string.no_playlist);
        LinearLayoutManager plLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        plRecyclerView.addItemDecoration(decoration);
        plRecyclerView.setLayoutManager(plLayoutManager);
        plRecyclerView.setAdapter(plAdapter);
        if((new File(getFilesDir(), plFileName)).exists()) {
            try {
                FileInputStream inputStream = openFileInput(plFileName);
                byte[] buf = new byte[inputStream.available()];
                inputStream.read(buf);
                inputStream.close();
                JSONArray plarray = new JSONArray(new String(buf));
                for (int i = 0; i < plarray.length(); i++)
                    playlists.add(new Playlist(plarray.getString(i)));
                plAdapter.notifyDataSetChanged();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }

        updateEmptyView();
    }

    public void updateEmptyView() {
        if (playlists.isEmpty()) {
            plRecyclerView.setVisibility(View.INVISIBLE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            plRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.INVISIBLE);
        }
    }

    public void addPlaylist(MenuItem item) {
        AddPlaylistDialogFragment dialogFragment = new AddPlaylistDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "Add Playlist");
    }

    @Override
    public void onPositiveClick(DialogFragment dialog) {
        EditText plName = dialog.getDialog().findViewById(R.id.playlistname_enter);
        playlists.add(new Playlist(plName.getText().toString()));
        updateEmptyView();
        plAdapter.notifyItemInserted(playlists.size() - 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editpl_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStop() {
        try {
            FileOutputStream fo = openFileOutput(plFileName, MODE_PRIVATE);
            PrintStream p = new PrintStream(fo);
            p.print(playlists.toString());
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onStop();
    }
}
