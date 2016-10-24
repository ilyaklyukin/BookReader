package com.umnix.bookreader.model;


import com.j256.ormlite.field.DatabaseField;

public class Author {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(canBeNull = false, unique = true)
    private String name;

    public Author() {
    }

    public Author(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
