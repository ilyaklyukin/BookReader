package com.umnix.bookreader.db.dao;

import java.sql.SQLException;
import java.util.List;

public interface BaseDao<T, ID> {
    <T> T save(T entity) throws SQLException;

    <T> int delete(T entity) throws SQLException;

    <T, ID> int deleteAll() throws SQLException;

    <T> List<T> getAll() throws SQLException;

    <T, ID> T getById(ID id) throws SQLException;
}
