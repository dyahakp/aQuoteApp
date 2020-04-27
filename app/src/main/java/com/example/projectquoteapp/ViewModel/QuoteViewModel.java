package com.example.projectquoteapp.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projectquoteapp.Favorite.FavQuote;
import com.example.projectquoteapp.Favorite.FavQuoteRepository;
import com.example.projectquoteapp.Quotes.QuotesResponse;
import com.example.projectquoteapp.Service.ApiInterface;
import com.example.projectquoteapp.Service.ApiMain;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuoteViewModel extends ViewModel {
    private static final String TAG = QuoteViewModel.class.getSimpleName();
    private ApiInterface apiInterface;
    private MutableLiveData<QuotesResponse> quotesResponseLiveData = new MutableLiveData<>();
    private FavQuoteRepository favQuoteRepository;

    public void setQuotesResponseLiveData() {
        apiInterface = ApiMain.getClient().create(ApiInterface.class);
        apiInterface.getQotd().enqueue(new Callback<QuotesResponse>() {
            @Override
            public void onResponse(Call<QuotesResponse> call, Response<QuotesResponse> response) {
                if (response.code() == 200) {
                    Log.d(TAG, "onResponse response:: " + response);
                    quotesResponseLiveData.setValue(response.body());
                } else {
                    quotesResponseLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<QuotesResponse> call, Throwable t) {
                Log.d(TAG, t.toString());
                quotesResponseLiveData.setValue(null);
            }
        });

    }

    public LiveData<QuotesResponse> getQuotesResponseLiveData() {
        return quotesResponseLiveData;
    }

    public void insertFavQuote(Application application, FavQuote quote) {
        favQuoteRepository = new FavQuoteRepository(application);
        favQuoteRepository.insert(quote);
    }
}
