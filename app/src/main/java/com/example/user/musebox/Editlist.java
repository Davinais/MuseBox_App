package com.example.user.musebox;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Editlist extends AppCompatActivity
                      implements AddPlaylistDialogFragment.AddPlaylistDialogListener {

    private RequestQueue mQueue;
    private static String sc_api_url = "https://api.soundcloud.com";
    private static String sc_client_id = "rZY6FYrMpGVhVDfaKEHdCaY8ALekxd8P";
    private ArrayList<PlayList> playLists;
    private PlayListAdapter plAdapter;
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

        playLists = new ArrayList<>();
        plAdapter = new PlayListAdapter(playLists);
        plRecyclerView = findViewById(R.id.playlist_view);
        emptyView = findViewById(R.id.emptyPlayList_View);
        LinearLayoutManager plLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        plRecyclerView.setLayoutManager(plLayoutManager);
        plRecyclerView.setAdapter(plAdapter);

        updateEmptyView();

        mQueue = Volley.newRequestQueue(this);
    }

    public void updateEmptyView() {
        if (playLists.isEmpty()) {
            plRecyclerView.setVisibility(View.INVISIBLE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            plRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.INVISIBLE);
        }
    }

    public boolean addPlaylist(MenuItem item) {
        AddPlaylistDialogFragment dialogFragment = new AddPlaylistDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "Add Playlist");
        return true;
    }

    @Override
    public void onCreateButtonClick(DialogFragment dialog) {
        EditText plName = dialog.getDialog().findViewById(R.id.playlistname_enter);
        playLists.add(new PlayList(plName.getText().toString()));
        updateEmptyView();
        plAdapter.notifyDataSetChanged();
    }

    public void resolveTrack(View view) {
        String req_url = sc_api_url + "/resolve?url=" + "&client_id=" + sc_client_id;

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, req_url, null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        int track_id = response.getInt("id");
                        String track_owner = response.getJSONObject("user").getString("username");
                        String track_title = response.getString("title");
                        String art_url = response.getString("artwork_url");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

        mQueue.add(req);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editpl_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
