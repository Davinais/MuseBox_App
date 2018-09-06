package com.example.user.musebox;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EditTracks extends AppCompatActivity
                        implements AddTrackDialogFragment.AddTrackDialogListener {

    private RequestQueue mQueue;
    private static String sc_api_url = "https://api.soundcloud.com";
    private static String sc_client_id = "rZY6FYrMpGVhVDfaKEHdCaY8ALekxd8P";
    private ArrayList<Track> tracks;
    private TrackAdapter trackAdapter;
    private RecyclerView trackRecyclerView;
    private TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editlist);
        Toolbar toolbar = findViewById(R.id.edit_toolbar);
        setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        String plName = getIntent().getStringExtra("PLAYLIST_NAME");
        setTitle(plName);

        RequestManager glide = Glide.with(this);
        tracks = new ArrayList<>();
        trackAdapter = new TrackAdapter(glide, tracks);
        trackRecyclerView = findViewById(R.id.playlist_view);
        emptyView = findViewById(R.id.emptyList_View);
        emptyView.setText(R.string.no_track);
        LinearLayoutManager trackLM = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        trackRecyclerView.setLayoutManager(trackLM);
        trackRecyclerView.setAdapter(trackAdapter);

        updateEmptyView();

        mQueue = Volley.newRequestQueue(this);
    }

    public void updateEmptyView() {
        if (tracks.isEmpty()) {
            trackRecyclerView.setVisibility(View.INVISIBLE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            trackRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.INVISIBLE);
        }
    }

    public void addTrack(MenuItem item) {
        AddTrackDialogFragment dialogFragment = new AddTrackDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "Add Track");
    }

    @Override
    public void onPositiveClick(DialogFragment dialog) {
        EditText trackUrl = dialog.getDialog().findViewById(R.id.trackurl_enter);
        resolveTrack(trackUrl.getText().toString());
    }

    public void resolveTrack(String trackUrl) {
        String req_url = sc_api_url + "/resolve?url=" + trackUrl + "&client_id=" + sc_client_id;

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, req_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int track_id = response.getInt("id");
                            String track_owner = response.getJSONObject("user").getString("username");
                            String track_title = response.getString("title");
                            String art_url = response.getString("artwork_url");
                            tracks.add(new Track(track_id, track_title, track_owner, art_url));
                            updateEmptyView();
                            trackAdapter.notifyItemInserted(tracks.size() - 1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                Toast.makeText(getBaseContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        mQueue.add(req);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edittrack_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
