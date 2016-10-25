package com.umnix.bookreader.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.umnix.bookreader.BookReaderApplication;
import com.umnix.bookreader.R;
import com.umnix.bookreader.db.Codec;
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
    protected WebView bookContent;

    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;

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

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        String defaultDescription = getActivity().getResources().getString(R.string.no_description);
        toolbar.setTitle(book.getCaption(defaultDescription));

        initReader();

        loadBook(book);

        return view;
    }

    private void initReader() {
        bookContent.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (progressBar == null || bookContent == null) {
                    return;
                }
                progressBar.setVisibility(View.GONE);
                bookContent.setVisibility(View.VISIBLE);
            }
        });
        WebSettings settings = bookContent.getSettings();
        settings.setUseWideViewPort(false);
        settings.setTextZoom(settings.getTextZoom() * 2);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setLoadWithOverviewMode(true);

        bookContent.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
    }

    private void loadBook(Book book) {
        progressBar.setVisibility(View.VISIBLE);
        bookContent.setVisibility(View.GONE);

        String text = Codec.decode(book.getText(), getActivity().getString(R.string.security_key));
        bookContent.loadData(text, "text/html; charset=UTF-8", null);
    }


    @Override
    public void onDestroyView() {
        unbinder.unbind();

        super.onDestroyView();
    }
}
