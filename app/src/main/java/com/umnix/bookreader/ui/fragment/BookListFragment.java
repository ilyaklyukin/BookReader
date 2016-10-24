package com.umnix.bookreader.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umnix.bookreader.BookReaderApplication;
import com.umnix.bookreader.R;
import com.umnix.bookreader.db.DBContentProvider;
import com.umnix.bookreader.model.Book;
import com.umnix.bookreader.ui.BookListActivity;
import com.umnix.bookreader.ui.OnItemClickListener;
import com.umnix.bookreader.ui.adapter.BookAdapter;
import com.umnix.bookreader.ui.adapter.VerticalSpaceItemDecoration;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

public class BookListFragment extends Fragment implements OnItemClickListener<Book> {

    private final int VERTICAL_LIST_DIVIDER_SPACE = 1;
    @Inject
    protected DBContentProvider dbContentProvider;

    @BindView(R.id.book_list_view)
    protected RecyclerView bookListView;

    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        BookReaderApplication.getComponent().inject(this);
        unbinder = ButterKnife.bind(this, view);

        List<Book> bookList = null;
        try {
            //TODO : do not read text!
            bookList = dbContentProvider.getBookDao().getAll();
        } catch (SQLException e) {
            Timber.e(e, "Error in reading books");
        }

        bookListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bookListView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_LIST_DIVIDER_SPACE));

        bookListView.setAdapter(new BookAdapter(bookList, this));

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(getActivity().getResources().getString(R.string.app_name));

        return view;
    }

    @Override
    public void onItemClick(Book book) {
        Timber.d("Selected fragment_book: %s", book);

        ((BookListActivity) getActivity()).onSelectBook(book);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();

        super.onDestroyView();
    }
}
