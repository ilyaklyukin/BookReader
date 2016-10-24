package com.umnix.bookreader.db.dao;

import com.j256.ormlite.dao.Dao;
import com.umnix.bookreader.model.Book;

public class BookDaoImpl extends BaseDaoImpl<Book, Long> implements BookDao {

    private Dao<Book, Long> bookDao;

    public BookDaoImpl(Dao<Book, Long> bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    protected Dao getEntityDao() {
        return bookDao;
    }
}
