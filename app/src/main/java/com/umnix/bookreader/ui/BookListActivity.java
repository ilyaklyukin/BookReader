package com.umnix.bookreader.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.umnix.bookreader.BookReaderApplication;
import com.umnix.bookreader.R;
import com.umnix.bookreader.db.LibraryInitializer;
import com.umnix.bookreader.model.Book;
import com.umnix.bookreader.ui.fragment.BookContentFragment;
import com.umnix.bookreader.ui.fragment.BookListFragment;

import java.sql.SQLException;

import javax.inject.Inject;

import butterknife.ButterKnife;
import timber.log.Timber;

public class BookListActivity extends AppCompatActivity {

    @Inject
    protected LibraryInitializer libraryInitializer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        BookReaderApplication.getComponent().inject(this);

        ButterKnife.bind(this);

        try {
            libraryInitializer.initLibrary();
        } catch (SQLException e) {
            Timber.e(e, "Error in initializing database");
        }

        attachFragment();
    }

    private void attachFragment() {
        BookListFragment fragment = new BookListFragment();
        attachFragment(fragment, false);
    }


    public void onSelectBook(Book book) {
        BookContentFragment fragment = BookContentFragment.newInstance(book);
        attachFragment(fragment, true);
    }

    private void attachFragment(Fragment fragment, boolean isInStack) {
        String tag = fragment.getClass().getName();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (isInStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(R.id.container, fragment, tag);
        ft.commit();
    }
}
