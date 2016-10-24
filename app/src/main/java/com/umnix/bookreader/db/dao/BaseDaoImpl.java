package com.umnix.bookreader.db.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.List;

public abstract class BaseDaoImpl<T, ID> implements BaseDao<T, ID> {
    abstract protected Dao getEntityDao();

    @Override
    public <T> T save(T entity) throws SQLException {
        getEntityDao().createOrUpdate(entity);
        return entity;
    }

    @Override
    public <T> int delete(T entity) throws SQLException {
        return getEntityDao().delete(entity);
    }

    @Override
    public <T, ID> int deleteAll() throws SQLException {
        DeleteBuilder<T, ID> deleteBuilder = getEntityDao().deleteBuilder();
        return deleteBuilder.delete();
    }

    @Override
    public <T> List<T> getAll() throws SQLException {
        return getEntityDao().queryForAll();
    }

    @Override
    public <T, ID> T getById(ID id) throws SQLException {
        return (T) getEntityDao().queryForId(id);
    }
}
