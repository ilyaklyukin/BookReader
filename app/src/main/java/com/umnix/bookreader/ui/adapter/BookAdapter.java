package com.umnix.bookreader.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.umnix.bookreader.model.Book;
import com.umnix.bookreader.ui.OnItemClickListener;
import com.umnix.bookreader.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private List<Book> itemList;
    private OnItemClickListener<Book> listener;

    public BookAdapter(List<Book> itemList, OnItemClickListener<Book> listener) {
        this.itemList = itemList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book book = itemList.get(position);

        holder.description.setText(book.getDescription());
        holder.author.setText(book.getAuthor() == null ? "-" : book.getAuthor().getName());

        holder.view.setOnClickListener(v -> listener.onItemClick(book));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private View view;

        @BindView(R.id.book_description)
        protected TextView description;

        @BindView(R.id.author)
        protected TextView author;

        public ViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
            this.view = view;
        }
    }
}
