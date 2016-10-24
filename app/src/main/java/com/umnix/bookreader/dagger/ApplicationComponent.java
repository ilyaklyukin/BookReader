package com.umnix.bookreader.dagger;

import com.umnix.bookreader.BookReaderApplication;
import com.umnix.bookreader.db.DBContentProvider;
import com.umnix.bookreader.db.LibraryInitializer;
import com.umnix.bookreader.ui.BookListActivity;
import com.umnix.bookreader.ui.fragment.BookContentFragment;
import com.umnix.bookreader.ui.fragment.BookListFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(BookReaderApplication application);

    void inject(DBContentProvider dbContentProvider);

    void inject(LibraryInitializer libraryInitializer);

    void inject(BookListActivity bookListActivity);

    void inject(BookListFragment bookListFragment);

    void inject(BookContentFragment bookContentFragment);
}