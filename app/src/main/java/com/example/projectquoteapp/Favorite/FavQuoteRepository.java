package com.example.projectquoteapp.Favorite;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavQuoteRepository {
    private FavQuoteDAO favQuoteDAO;
    private ExecutorService executorService;

    public FavQuoteRepository(Application application) {
        executorService = Executors.newSingleThreadExecutor();
        FavQuoteDatabase db = FavQuoteDatabase.getDatabase(application);
        favQuoteDAO = db.favQuoteDAO();
    }

    public LiveData<List<FavQuote>> getAllFavQuotes() {
        return favQuoteDAO.selectAllFavQuotes();
    }

    public void insert(final FavQuote quote) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                favQuoteDAO.insertQuote(quote);
            }
        });
    }

    public void update(final FavQuote quote) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                favQuoteDAO.updateQuote(quote);
            }
        });
    }

    public void delete(final FavQuote quote) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                favQuoteDAO.deleteQuote(quote);
            }
        });
    }
}
