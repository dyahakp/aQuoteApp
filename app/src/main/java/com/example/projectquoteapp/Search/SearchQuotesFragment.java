package com.example.projectquoteapp.Search;

import android.app.Application;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectquoteapp.Adapter.SearchQuotesAdapter;
import com.example.projectquoteapp.Favorite.FavQuote;
import com.example.projectquoteapp.Favorite.FavQuoteDatabase;
import com.example.projectquoteapp.Quotes.QuotesModel;
import com.example.projectquoteapp.R;
import com.example.projectquoteapp.ViewModel.SearchViewModel;

import co.ceryle.radiorealbutton.RadioRealButton;
import co.ceryle.radiorealbutton.RadioRealButtonGroup;

public class SearchQuotesFragment extends Fragment {
    SearchViewModel searchViewModel;
    FavQuoteDatabase db;
    String type;
    private SearchQuotesAdapter searchQuotesAdapter;
    private RecyclerView rvQuotes;
    private LinearLayoutManager layoutManager;
    private EditText etSearch;
    private Button btnSearch;
    private RadioRealButton radioWord;
    private RadioRealButton radioAuthor;
    private RadioRealButtonGroup radioGroup;
    private ProgressBar progressBarSearch;
    private Observer<SearchQuotesResponse> getSearchQuote = new Observer<SearchQuotesResponse>() {
        @Override
        public void onChanged(SearchQuotesResponse searchResponse) {
            if (searchResponse != null) {
                searchQuotesAdapter.setData(searchResponse.getQuotes());
                progressBarSearch.setVisibility(View.GONE);
                rvQuotes.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialization();
        getListQuotes();
        handleClick();
        radioHandler();
    }

    private void initialization() {
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        rvQuotes = (RecyclerView) getView().findViewById(R.id.fragment_rv);
        etSearch = (EditText) getView().findViewById(R.id.etSearch);
        btnSearch = (Button) getView().findViewById(R.id.btnSearch);
        radioWord = (RadioRealButton) getView().findViewById(R.id.radioWord);
        radioAuthor = (RadioRealButton) getView().findViewById(R.id.radioAuthor);
        radioGroup = (RadioRealButtonGroup) getView().findViewById(R.id.radioGroup);
        progressBarSearch = (ProgressBar) getView().findViewById(R.id.progressBarSearch);
        layoutManager = new LinearLayoutManager(getContext());
        rvQuotes.setLayoutManager(layoutManager);
        rvQuotes.setHasFixedSize(true);
        searchQuotesAdapter = new SearchQuotesAdapter(getContext());
        rvQuotes.setAdapter(searchQuotesAdapter);
    }

    private void getListQuotes() {
        searchViewModel.setSearchResponseLiveData();
        searchViewModel.getSearchResponseLiveData().observe(getViewLifecycleOwner(), getSearchQuote);
        handleFilter();
    }

    private void handleFilter() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etSearch.getText().toString().isEmpty()) {
                    searchViewModel.setSearchResponseLiveData();
                    rvQuotes.setVisibility(View.GONE);
                    progressBarSearch.setVisibility(View.VISIBLE);
                } else {
                    searchViewModel.setSearchResponseLiveDataFilter(etSearch.getText().toString(), type);
                    rvQuotes.setVisibility(View.GONE);
                    progressBarSearch.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void radioHandler() {
        radioGroup.setOnClickedButtonListener(new RadioRealButtonGroup.OnClickedButtonListener() {
            @Override
            public void onClickedButton(RadioRealButton button, int position) {
                if (button.getText().equalsIgnoreCase("word")) {
                    type = "";
                } else {
                    type = "author";
                }
            }
        });
    }

    private void handleClick() {
        searchQuotesAdapter.setOnItemClickListener(new SearchQuotesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, QuotesModel item, int position) {

            }

            @Override
            public void onFavClick(View view, QuotesModel item, int position) {
                insertData(getActivity().getApplication(), new FavQuote(
                        item.getId(),
                        item.getBody(),
                        item.getAuthor()
                ));
                Toast.makeText(getContext(), "Quote Save to Favorite", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCopyClick(View view, QuotesModel item, int position) {
                String textQuote = item.getBody() + " ~" + item.getAuthor();
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("quote", textQuote);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getContext(), "Quote Copied to Clipboard", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onShareClick(View view, QuotesModel item, int position) {
                String textQuote = item.getBody() + " ~" + item.getAuthor();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, textQuote);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
    }

    private void insertData(Application application, FavQuote quote) {
        searchViewModel.insertFavQuote(application, quote);
    }
}
