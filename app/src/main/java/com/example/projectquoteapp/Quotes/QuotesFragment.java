package com.example.projectquoteapp.Quotes;

import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.projectquoteapp.Favorite.FavQuote;
import com.example.projectquoteapp.R;
import com.example.projectquoteapp.ViewModel.QuoteViewModel;

public class QuotesFragment extends Fragment {
    QuoteViewModel quoteViewModel;
    private TextView author, body;
    private ImageButton btnFav, btnCopy, btnShare;
    private ProgressBar progressBarQotd;
    private CardView cardQotd;
    private int id;
    private ImageView refresh;
    private Observer<QuotesResponse> getQotd = new Observer<QuotesResponse>() {
        @Override
        public void onChanged(QuotesResponse quotesResponse) {
            if (quotesResponse != null) {
                id = quotesResponse.getQuote().getId();
                author.setText(quotesResponse.getQuote().getAuthor());
                body.setText(quotesResponse.getQuote().getBody());
            }
            progressBarQotd.setVisibility(View.GONE);
            cardQotd.setVisibility(View.VISIBLE);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quotes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialization();
        getQuotes();
        handleButton();
    }

    private void initialization() {
        quoteViewModel = new ViewModelProvider(this).get(QuoteViewModel.class);
        author = getView().findViewById(R.id.author);
        body = getView().findViewById(R.id.body);
        btnFav = getView().findViewById(R.id.btnLike);
        btnCopy = getView().findViewById(R.id.btnCopy);
        btnShare = getView().findViewById(R.id.btnShare);
        cardQotd = getView().findViewById(R.id.cardQotd);
        refresh = getView().findViewById(R.id.btnRefresh);
        progressBarQotd = getView().findViewById(R.id.progressBarQotd);
    }

    private void getQuotes() {
        quoteViewModel.setQuotesResponseLiveData();
        quoteViewModel.getQuotesResponseLiveData().observe(getViewLifecycleOwner(), getQotd);
    }

    private void handleButton() {
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textQuote = body.getText().toString() + " ~" + author.getText().toString();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, textQuote);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textQuote = body.getText().toString() + " ~" + author.getText().toString();
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("quote", textQuote);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getContext(), "Quote Copied to Clipboard", Toast.LENGTH_LONG).show();
            }
        });

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData(getActivity().getApplication(), new FavQuote(
                        id,
                        body.getText().toString(),
                        author.getText().toString()
                ));
                Toast.makeText(getContext(), "Quote Save to Favorite", Toast.LENGTH_LONG).show();
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQuotes();
            }
        });
    }
    private void insertData(Application application, FavQuote quote) {
        quoteViewModel.insertFavQuote(application, quote);
    }
}