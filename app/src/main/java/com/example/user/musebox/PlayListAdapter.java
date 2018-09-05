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

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.PlayListViewHolder> {

    private ArrayList<PlayList> playLists;
    private Context context;

    public PlayListAdapter(ArrayList<PlayList> playLists) {
        this.playLists = playLists;
    }

    @NonNull
    @Override
    public PlayListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_item, parent, false);
        PlayListViewHolder vh = new PlayListViewHolder(v);
        context = parent.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PlayListViewHolder vh, final int pos) {
        vh.plName.setText(playLists.get(pos).getName());
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Item " + pos + " is clicked", Toast.LENGTH_SHORT).show();
            }
        });
        vh.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(context, "Item " + pos + " is long clicked", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return playLists == null ? 0 : playLists.size();
    }

    public static class PlayListViewHolder extends RecyclerView.ViewHolder {

        TextView plName;

        PlayListViewHolder(View view) {
            super(view);
            plName = view.findViewById(R.id.playlist_name_display);
        }
    }
}
