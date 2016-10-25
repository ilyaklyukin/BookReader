package com.umnix.bookreader.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.umnix.bookreader.BookReaderApplication;
import com.umnix.bookreader.R;
import com.umnix.bookreader.model.Book;
import com.umnix.bookreader.ui.fragment.BookContentFragment;
import com.umnix.bookreader.ui.fragment.BookListFragment;

import butterknife.ButterKnife;

public class BookListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        BookReaderApplication.getComponent().inject(this);

        ButterKnife.bind(this);

        attachBookListFragment();
    }

    private void attachBookListFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(
                BookListFragment.class.getName());
        if (fragment != null) {
            return;
        }
        fragment = new BookListFragment();
        attachFragment(fragment, false);
    }


    public void onSelectBook(Book book) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(
                BookContentFragment.class.getName());
        if (fragment != null) {
            return;
        }

        fragment = BookContentFragment.newInstance(book);
        attachFragment(fragment, true);
    }

    private void attachFragment(Fragment fragment, boolean isInStack) {
        String tag = fragment.getClass().getName();
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (isInStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(R.id.container, fragment, tag);
        ft.commit();
    }
}
