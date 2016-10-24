package com.umnix.bookreader.db;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.umnix.bookreader.BookReaderApplication;
import com.umnix.bookreader.db.dao.AuthorDao;
import com.umnix.bookreader.db.dao.AuthorDaoImpl;
import com.umnix.bookreader.db.dao.BookDao;
import com.umnix.bookreader.db.dao.BookDaoImpl;
import com.umnix.bookreader.db.dao.GenreDao;
import com.umnix.bookreader.db.dao.GenreDaoImpl;
import com.umnix.bookreader.model.Author;
import com.umnix.bookreader.model.Book;
import com.umnix.bookreader.model.Genre;

import java.sql.SQLException;

import javax.inject.Inject;

import timber.log.Timber;

public class DBContentProvider {

    @Inject
    protected Context context;

    private ORMLiteHelper dbHelper = null;

    private AuthorDao authorDao = null;

    private GenreDao genreDao = null;

    private BookDao bookDao = null;

    public DBContentProvider() {
        BookReaderApplication.getComponent().inject(this);

        try {
            authorDao = new AuthorDaoImpl(getHelper().getClassDao(Author.class));

            genreDao = new GenreDaoImpl(getHelper().getClassDao(Genre.class));

            bookDao = new BookDaoImpl(getHelper().getClassDao(Book.class));
        } catch (SQLException e) {
            Timber.e(e, "Error on getting dao");
        }
    }

    public AuthorDao getAuthorDao() {
        return authorDao;
    }

    public GenreDao getGenreDao() {
        return genreDao;
    }

    public BookDao getBookDao() {
        return bookDao;
    }

    private ORMLiteHelper getHelper() {
        if (dbHelper == null) {
            dbHelper = OpenHelperManager.getHelper(context, ORMLiteHelper.class);
        }
        return dbHelper;
    }

    public void onDestroy() {
        if (dbHelper != null) {
            OpenHelperManager.releaseHelper();
            dbHelper = null;
        }
    }
}
