package org.japari.park.musebox;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
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
        vh.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditTracks.class);
            intent.putExtra("PLAYLIST_NAME", playlists.get(vh.getAdapterPosition()).getName());
            context.startActivity(intent);
        });
        vh.itemView.setOnLongClickListener(v -> {
            int adPos = vh.getAdapterPosition();
            File plFile = new File(context.getFilesDir(), playlists.get(adPos).getName());
            playlists.remove(adPos);
            if(plFile.exists())
            {
                plFile.delete();
            }
            notifyItemRemoved(adPos);
            return true;
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
