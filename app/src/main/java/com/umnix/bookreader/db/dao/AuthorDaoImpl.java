package com.umnix.bookreader.db.dao;

import com.j256.ormlite.dao.Dao;
import com.umnix.bookreader.model.Author;

import java.sql.SQLException;


public class AuthorDaoImpl extends BaseDaoImpl<Author, Long> implements AuthorDao {

    private static final String NAME_COLUMN = "name";

    private Dao<Author, Long> authorDao;

    public AuthorDaoImpl(Dao<Author, Long> authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    protected Dao getEntityDao() {
        return authorDao;
    }


    @Override
    public Author getByName(String name) throws SQLException {
        return authorDao.queryForFirst(authorDao.queryBuilder().where().eq(NAME_COLUMN, name).prepare());
    }
}
