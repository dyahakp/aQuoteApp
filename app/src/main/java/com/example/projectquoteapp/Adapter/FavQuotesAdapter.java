package com.example.projectquoteapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projectquoteapp.Favorite.FavQuote;
import com.example.projectquoteapp.R;

import java.util.ArrayList;
import java.util.List;

public class FavQuotesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<FavQuote> quotesList = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;


    public FavQuotesAdapter(Context context) {
        this.context = context;
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public void setData(List<FavQuote> items) {
        quotesList.clear();
        quotesList.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_fav_quote, parent, false);
        vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            final ViewHolder view = (ViewHolder) holder;
            FavQuote quotes = quotesList.get(position);
            view.ivBody.setText(quotes.getQuote());
            view.ivAuthor.setText(quotes.getAuthor());
            view.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, quotesList.get(position), position);
                    }
                }
            });
            view.btnCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onCopyClick(v, quotesList.get(position), position);
                    }
                }
            });
            view.btnFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onFavClick(v, quotesList.get(position), position);
                    }
                }
            });
            view.btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onShareClick(v, quotesList.get(position), position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return quotesList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, FavQuote item, int position);

        void onFavClick(View view, FavQuote item, int position);

        void onCopyClick(View view, FavQuote item, int position);

        void onShareClick(View view, FavQuote item, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ivBody, ivAuthor;
        ImageButton btnFav, btnShare, btnCopy;
        LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ivBody = itemView.findViewById(R.id.body);
            ivAuthor = itemView.findViewById(R.id.author);
            linearLayout = itemView.findViewById(R.id.lyt_item);
            btnFav = (ImageButton) itemView.findViewById(R.id.btnLike);
            btnCopy = (ImageButton) itemView.findViewById(R.id.btnCopy);
            btnShare = (ImageButton) itemView.findViewById(R.id.btnShare);
        }
    }
}
