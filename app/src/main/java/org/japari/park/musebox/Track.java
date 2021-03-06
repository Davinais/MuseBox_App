package org.japari.park.musebox;

public class Track {
    private int trackID;
    private String title;
    private String owner;
    private String artwork_url;

    public Track(int id, String title, String owner, String art_url) {
        this.trackID = id;
        this.title = title;
        this.owner = owner;
        this.artwork_url = art_url;
    }

    public int getTrackID() {
        return trackID;
    }

    public String getTitle() {
        return title;
    }

    public String getOwner() {
        return owner;
    }

    public String getArtwork_url() {
        return artwork_url;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("{\"id\":").append(String.valueOf(trackID)).append(",\"own\":\"").append(owner).append("\",\"title\":\"").append(title).append("\",\"art\":\"").append(artwork_url).append("\"}").toString();
    }
}
