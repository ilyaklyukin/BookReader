package com.umnix.bookreader.db.dao;

import com.umnix.bookreader.model.Genre;

import java.sql.SQLException;

public interface GenreDao extends BaseDao<Genre, Long> {
    Genre getByName(String name) throws SQLException;
}
