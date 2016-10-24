package com.umnix.bookreader.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.umnix.bookreader.BuildConfig;
import com.umnix.bookreader.R;
import com.umnix.bookreader.model.Author;
import com.umnix.bookreader.model.Book;
import com.umnix.bookreader.model.Genre;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

public class ORMLiteHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "umnix";
    private static final Class<?>[] objects = {
            Author.class,
            Genre.class,
            Book.class
    };

    private Map<String, Object> daoCache = new HashMap<>();

    public ORMLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, BuildConfig.DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.setForeignKeyConstraintsEnabled(true);
        }
    }

    /**
     * This is called when the database is first created.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Timber.i("onCreate database");

            for (Class<?> clazz : objects) {
                TableUtils.createTable(connectionSource, clazz);
            }

        } catch (SQLException e) {
            Timber.e(e, "Can't create database");
            throw new RuntimeException(e);
        }
    }

    /**
     * This is called when application is upgraded and it has a higher version number.
     * This allows to adjust the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            for (Class<?> clazz : objects) {
                TableUtils.dropTable(connectionSource, clazz, true);
            }

            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public <T, ID> Dao<T, ID> getClassDao(Class<T> clazz) throws SQLException {
        Dao<T, ID> classDao = (Dao) daoCache.get(clazz.getName());
        if (classDao == null) {
            classDao = getDao(clazz);
            daoCache.put(clazz.getName(), classDao);
        }

        return classDao;
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        if (daoCache != null) {
            daoCache.clear();
        }
    }
}
