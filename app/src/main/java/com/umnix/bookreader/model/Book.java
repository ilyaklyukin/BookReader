package com.umnix.bookreader.model;


import com.j256.ormlite.field.DatabaseField;

public class Book {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField
    private String description;

    @DatabaseField
    private String text;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Author author;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Genre genre;

    public Book description(String description) {
        this.description = description;
        return this;
    }

    public Book text(String text) {
        this.text = text;
        return this;
    }

    public Book author(Author author) {
        this.author = author;
        return this;
    }

    public Book genre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getCaption(String defaultDescription) {
        if (author == null) {
            return description == null ? defaultDescription : description;
        } else if (description == null) {
            return author + ". " + defaultDescription;
        }

        return author.getName() + ". " + description;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", author=" + author +
                ", genre=" + genre +
                '}';
    }
}
