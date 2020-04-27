package com.example.projectquoteapp.Favorite;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectquoteapp.Adapter.FavQuotesAdapter;
import com.example.projectquoteapp.R;
import com.example.projectquoteapp.ViewModel.FavoriteViewModel;

import java.util.List;

public class FavQuoteFragment extends Fragment {
    FavoriteViewModel favoriteViewModel;
    FavQuoteDatabase db;
    private FavQuotesAdapter favQuotesAdapter;
    private RecyclerView rvQuotes;
    private LinearLayoutManager layoutManager;
    private EditText etSearch;
    private Button btnSearch;
    private Observer<List<FavQuote>> getQuote = new Observer<List<FavQuote>>() {
        @Override
        public void onChanged(List<FavQuote> quote) {
            favQuotesAdapter.setData(quote);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialization();
        getListQuotes();
        handleClick();
    }

    private void initialization() {
        favoriteViewModel = new FavoriteViewModel(getActivity().getApplication());
        rvQuotes = (RecyclerView) getView().findViewById(R.id.fragment_rv);
        layoutManager = new LinearLayoutManager(getContext());
        rvQuotes.setLayoutManager(layoutManager);
        rvQuotes.setHasFixedSize(true);
        favQuotesAdapter = new FavQuotesAdapter(getContext());
        rvQuotes.setAdapter(favQuotesAdapter);
    }

    private void getListQuotes() {
        favoriteViewModel.getAllFavQuotes().observe(getViewLifecycleOwner(), getQuote);
    }

    private void handleClick() {
        favQuotesAdapter.setOnItemClickListener(new FavQuotesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, FavQuote item, int position) {

            }

            @Override
            public void onFavClick(View view, FavQuote item, int position) {
                deleteData(new FavQuote(
                        item.getId(),
                        item.getQuote(),
                        item.getAuthor()
                ));
                Toast.makeText(getContext(), "Quote Delete from Favorite", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCopyClick(View view, FavQuote item, int position) {
                String textQuote = item.getQuote() + " ~" + item.getAuthor();
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("quote", textQuote);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getContext(), "Quote Copied to Clipboard", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onShareClick(View view, FavQuote item, int position) {
                String textQuote = item.getQuote() + " ~" + item.getAuthor();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, textQuote);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
    }

    private void deleteData(FavQuote quote) {
        favoriteViewModel.deleteFavQuote(quote);
    }

}
