package com.umnix.bookreader.db;


import android.content.Context;

import com.umnix.bookreader.R;
import com.umnix.bookreader.db.dao.AuthorDao;
import com.umnix.bookreader.db.dao.AuthorDaoImpl;
import com.umnix.bookreader.db.dao.BookDao;
import com.umnix.bookreader.db.dao.BookDaoImpl;
import com.umnix.bookreader.db.dao.GenreDao;
import com.umnix.bookreader.db.dao.GenreDaoImpl;
import com.umnix.bookreader.model.Author;
import com.umnix.bookreader.model.Book;
import com.umnix.bookreader.model.Genre;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import timber.log.Timber;

public class LibraryInitializer {

    private AuthorDao authorDao;
    private GenreDao genreDao;
    private BookDao bookDao;
    private Context context;

    public LibraryInitializer(Context context, ORMLiteHelper dbHelper) throws SQLException {
        this.context = context;
        this.authorDao = new AuthorDaoImpl(dbHelper.getClassDao(Author.class));
        this.genreDao = new GenreDaoImpl(dbHelper.getClassDao(Genre.class));
        this.bookDao = new BookDaoImpl(dbHelper.getClassDao(Book.class));
    }

    public void initLibrary() throws SQLException {

        fillAuthors();
        fillGenres();
        fillBooks();
    }

    private void fillAuthors() throws SQLException {
        authorDao.deleteAll();

        Author author = new Author("Рэй Бредбери");
        authorDao.save(author);

        author = new Author("Айзек Азимов");
        authorDao.save(author);

        author = new Author("Жюстин");
        authorDao.save(author);

        author = new Author("Агния Барто");
        authorDao.save(author);
    }

    private void fillGenres() throws SQLException {
        genreDao.deleteAll();

        Genre genre = new Genre("фантастика");
        genreDao.save(genre);

        genre = new Genre("стихи");
        genreDao.save(genre);

        genre = new Genre("биография");
        genreDao.save(genre);

        genre = new Genre("сказка");
        genreDao.save(genre);
    }

    private void fillBooks() throws SQLException {
        bookDao.deleteAll();

        String securityKey = context.getString(R.string.security_key);

        Book book = new Book()
                .description("Основатели (eng.)")
                .text(Codec.encode(getAssetContent("osnovateli.txt"), securityKey))
                .author(authorDao.getByName("Айзек Азимов"))
                .genre(genreDao.getByName("фантастика"));
        bookDao.save(book);

        book = new Book()
                .description("Этим утром я решила перестать есть")
                .text(Codec.encode(getAssetContent("perestala_est.txt"), securityKey))
                .author(authorDao.getByName("Жюстин"))
                .genre(genreDao.getByName("биография"));
        bookDao.save(book);

        book = new Book()
                .description("Жилец из верхней квартиры")
                .text(Codec.encode(getAssetContent("kvartira.txt"), securityKey))
                .author(authorDao.getByName("Рэй Бредбери"))
                .genre(genreDao.getByName("фантастика"));
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
