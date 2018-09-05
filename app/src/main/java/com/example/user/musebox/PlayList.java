package com.example.user.musebox;

import java.util.ArrayList;

public class PlayList {
    private String name;
    private ArrayList<Track> tracks;

    public PlayList(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
