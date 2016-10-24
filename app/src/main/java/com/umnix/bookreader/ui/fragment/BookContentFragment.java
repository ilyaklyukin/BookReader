package com.umnix.bookreader.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.umnix.bookreader.BookReaderApplication;
import com.umnix.bookreader.R;
import com.umnix.bookreader.db.DBContentProvider;
import com.umnix.bookreader.model.Book;

import java.sql.SQLException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

public class BookContentFragment extends Fragment {

    private static final String EXTRA_BOOK_ID = "extra.book_id";

    @Inject
    protected DBContentProvider dbContentProvider;

    @BindView(R.id.book_content)
    protected TextView bookContent;

    private Unbinder unbinder;

    private Book book;

    public static BookContentFragment newInstance(Book book) {
        BookContentFragment fragment = new BookContentFragment();

        Bundle args = new Bundle();
        args.putLong(EXTRA_BOOK_ID, book.getId());

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BookReaderApplication.getComponent().inject(this);

        long bookId = getArguments().getLong(EXTRA_BOOK_ID, -1);

        try {
            book = dbContentProvider.getBookDao().getById(bookId);
        } catch (SQLException e) {
            Timber.e(e, "Can't get fragment_book #%d: %s", bookId, e.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);

        BookReaderApplication.getComponent().inject(this);
        unbinder = ButterKnife.bind(this, view);

        bookContent.setMovementMethod(new ScrollingMovementMethod());
        bookContent.setText(book.getText());

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(book.getCaption());

        return view;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();

        super.onDestroyView();
    }
}
