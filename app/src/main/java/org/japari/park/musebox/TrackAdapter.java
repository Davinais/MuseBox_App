package org.japari.park.musebox;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;

import java.util.ArrayList;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.TrackViewHolder> {

    private final RequestManager glide;
    private ArrayList<Track> tracks;

    public TrackAdapter(RequestManager glide, ArrayList<Track> tracks) {
        this.glide = glide;
        this.tracks = tracks;
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_item, parent, false);
        TrackViewHolder vh = new TrackViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final TrackViewHolder vh, int pos) {
        Track track = tracks.get(pos);
        glide.load(track.getArtwork_url()).into(vh.artwork_view);
        vh.owner_view.setText(track.getOwner());
        vh.title_view.setText(track.getTitle());
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vh.itemView.requestFocus();
            }
        });
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public static class TrackViewHolder extends RecyclerView.ViewHolder {
        ImageView artwork_view;
        TextView owner_view;
        TextView title_view;

        TrackViewHolder(View view) {
            super(view);
            artwork_view = view.findViewById(R.id.artwork_view);
            owner_view = view.findViewById(R.id.track_owner_view);
            title_view = view.findViewById(R.id.track_title_view);
        }
    }
}
