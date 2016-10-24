package com.umnix.bookreader.model;


import com.j256.ormlite.field.DatabaseField;

public class Genre {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(canBeNull = false, unique = true)
    private String genreName;

    public Genre() {
    }

    public Genre(String genreName) {
        this.genreName = genreName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", genreName='" + genreName + '\'' +
                '}';
    }
}
