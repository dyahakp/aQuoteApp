package com.example.projectquoteapp.ViewModel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.projectquoteapp.Favorite.FavQuote;
import com.example.projectquoteapp.Favorite.FavQuoteRepository;

import java.util.List;

public class FavoriteViewModel extends ViewModelProvider.NewInstanceFactory {
    private static final String TAG = FavoriteViewModel.class.getSimpleName();
    private static volatile FavoriteViewModel INSTANCE;
    private FavQuoteRepository favQuoteRepository;

    public FavoriteViewModel(Application application) {
        favQuoteRepository = new FavQuoteRepository(application);
    }

    public static FavoriteViewModel getInstance(Application application) {

        if (INSTANCE == null) {
            synchronized (FavoriteViewModel.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoriteViewModel(application);
                }
            }
        }
        return INSTANCE;
    }

    public LiveData<List<FavQuote>> getAllFavQuotes() {
        return favQuoteRepository.getAllFavQuotes();
    }

    public void deleteFavQuote(FavQuote quote) {
        favQuoteRepository.delete(quote);
    }
}
