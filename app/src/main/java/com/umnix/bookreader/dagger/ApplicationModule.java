package com.umnix.bookreader.dagger;

import android.content.Context;

import com.umnix.bookreader.BookReaderApplication;
import com.umnix.bookreader.db.DBContentProvider;
import com.umnix.bookreader.db.LibraryInitializer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

@Module
public class ApplicationModule {

    private final BookReaderApplication bookReaderApplication;

    public ApplicationModule(BookReaderApplication bookReaderApplication) {
        this.bookReaderApplication = bookReaderApplication;
    }

    public void bootstrap() {
        Timber.plant(new Timber.DebugTree());
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.bookReaderApplication;
    }

    @Provides
    @Singleton
    DBContentProvider provideDBContentProvider() {
        return new DBContentProvider();
    }

    @Provides
    @Singleton
    LibraryInitializer provideLibraryInitializer() {
        return new LibraryInitializer();
    }
}
