package com.umnix.bookreader.db.dao;

import com.umnix.bookreader.model.Author;

import java.sql.SQLException;

public interface AuthorDao extends BaseDao<Author, Long> {
    Author getByName(String name) throws SQLException;
}
