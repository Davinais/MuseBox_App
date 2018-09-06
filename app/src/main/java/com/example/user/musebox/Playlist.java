package com.example.user.musebox;

import java.util.ArrayList;

public class Playlist {
    private String name;
    private ArrayList<Track> tracks;

    public Playlist(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
