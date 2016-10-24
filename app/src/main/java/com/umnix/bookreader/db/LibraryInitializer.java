package com.umnix.bookreader.db;


import android.content.Context;

import com.umnix.bookreader.BookReaderApplication;
import com.umnix.bookreader.db.dao.AuthorDao;
import com.umnix.bookreader.db.dao.BookDao;
import com.umnix.bookreader.db.dao.GenreDao;
import com.umnix.bookreader.model.Author;
import com.umnix.bookreader.model.Book;
import com.umnix.bookreader.model.Genre;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import javax.inject.Inject;

import timber.log.Timber;

public class LibraryInitializer {
    @Inject
    protected DBContentProvider dbContentProvider;

    @Inject
    Context context;

    private AuthorDao authorDao;
    private GenreDao genreDao;
    private BookDao bookDao;

    public LibraryInitializer() {
        BookReaderApplication.getComponent().inject(this);

        authorDao = dbContentProvider.getAuthorDao();
        genreDao = dbContentProvider.getGenreDao();
        bookDao = dbContentProvider.getBookDao();
    }

    public void initLibrary() throws SQLException {

        fillAuthors();
        fillGenres();
        fillBooks();
    }

    private void fillAuthors() throws SQLException {
        authorDao.deleteAll();

        Author author = new Author("R.Bradbury");
        authorDao.save(author);

        author = new Author("I.Asimov");
        authorDao.save(author);

        author = new Author("A.Barto");
        authorDao.save(author);
    }

    private void fillGenres() throws SQLException {
        genreDao.deleteAll();

        Genre genre = new Genre("novel");
        genreDao.save(genre);

        genre = new Genre("poems");
        genreDao.save(genre);

        genre = new Genre("fairy tale");
        genreDao.save(genre);
    }

    private void fillBooks() throws SQLException {
        bookDao.deleteAll();

        Book book = new Book()
                .description("Основатели")
                .text(getAssetContent("osnovateli.txt"))
                .author(authorDao.getByName("I.Asimov"))
                .genre(genreDao.getByName("novel"));
        bookDao.save(book);

        book = new Book()
                .description("Космический рейнджер")
                .text(getAssetContent("cosmo_ranger.txt"))
                .author(authorDao.getByName("I.Asimov"))
                .genre(genreDao.getByName("novel"));
        bookDao.save(book);

        book = new Book()
                .description("451 градус по Фаренгейту")
                .text(getAssetContent("fahrenheit.txt"))
                .author(authorDao.getByName("R.Bradbury"))
                .genre(genreDao.getByName("novel"));
        bookDao.save(book);
    }

    private String getAssetContent(String fileName) {

        String result = "";

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName), "UTF-8"));

            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = reader.readLine();
            }

            reader.close();
            result = sb.toString();
        } catch (IOException e) {
            Timber.e(e, "Error in reading file from assets");
        }

        return result;
    }
}
