package com.example.user.musebox;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {

    private ArrayList<Playlist> playlists;
    private Context context;

    public PlaylistAdapter(Context context, ArrayList<Playlist> playlists) {
        this.context = context;
        this.playlists = playlists;
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_item, parent, false);
        PlaylistViewHolder vh = new PlaylistViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final PlaylistViewHolder vh, final int pos) {
        vh.plName.setText(playlists.get(pos).getName());
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Item " + pos + " is clicked", Toast.LENGTH_SHORT).show();
            }
        });
        vh.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int adPos = vh.getAdapterPosition();
                playlists.remove(adPos);
                notifyItemRemoved(adPos);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return playlists == null ? 0 : playlists.size();
    }

    public static class PlaylistViewHolder extends RecyclerView.ViewHolder {

        TextView plName;

        PlaylistViewHolder(View view) {
            super(view);
            plName = view.findViewById(R.id.playlist_name_display);
        }
    }
}
