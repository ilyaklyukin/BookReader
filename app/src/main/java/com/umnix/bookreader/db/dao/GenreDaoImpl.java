package com.umnix.bookreader.db.dao;

import com.j256.ormlite.dao.Dao;
import com.umnix.bookreader.model.Genre;

import java.sql.SQLException;

public class GenreDaoImpl extends BaseDaoImpl<Genre, Long> implements GenreDao {

    private static final String NAME_COLUMN = "genreName";

    private Dao<Genre, Long> genreDao;

    public GenreDaoImpl(Dao<Genre, Long> genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    protected Dao getEntityDao() {
        return genreDao;
    }

    @Override
    public Genre getByName(String name) throws SQLException {
        return genreDao.queryForFirst(genreDao.queryBuilder().where().eq(NAME_COLUMN, name).prepare());
    }
}
