package com.example.projectquoteapp.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projectquoteapp.Favorite.FavQuote;
import com.example.projectquoteapp.Favorite.FavQuoteRepository;
import com.example.projectquoteapp.Search.SearchQuotesResponse;
import com.example.projectquoteapp.Service.ApiInterface;
import com.example.projectquoteapp.Service.ApiMain;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchViewModel extends ViewModel {
    private static final String TAG = SearchViewModel.class.getSimpleName();
    private MutableLiveData<SearchQuotesResponse> searchResponseLiveData = new MutableLiveData<>();
    private ApiInterface apiInterface;
    private FavQuoteRepository favQuoteRepository;

    public void setSearchResponseLiveData() {
        apiInterface = ApiMain.getClient().create(ApiInterface.class);
        apiInterface.getQuotes(ApiMain.API_KEY).enqueue(new Callback<SearchQuotesResponse>() {
            @Override
            public void onResponse(Call<SearchQuotesResponse> call, Response<SearchQuotesResponse> response) {
                if (response.code() == 200) {
                    searchResponseLiveData.setValue(response.body());
                } else {
                    searchResponseLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<SearchQuotesResponse> call, Throwable t) {
                Log.d(TAG, t.toString());
                searchResponseLiveData.setValue(null);
            }
        });
    }

    public void setSearchResponseLiveDataFilter(String query, String type) {
        apiInterface = ApiMain.getClient().create(ApiInterface.class);
        apiInterface.getQuotesFilter(ApiMain.API_KEY, query, type).enqueue(new Callback<SearchQuotesResponse>() {
            @Override
            public void onResponse(Call<SearchQuotesResponse> call, Response<SearchQuotesResponse> response) {
                if (response.code() == 200) {
                    Log.d(TAG, "onResponse response:: " + response);
                    searchResponseLiveData.setValue(response.body());
                } else {
                    searchResponseLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<SearchQuotesResponse> call, Throwable t) {
                Log.d(TAG, t.toString());
                searchResponseLiveData.setValue(null);
            }
        });
    }

    public LiveData<SearchQuotesResponse> getSearchResponseLiveData() {
        return searchResponseLiveData;
    }

    public void insertFavQuote(Application application, FavQuote quote) {
        favQuoteRepository = new FavQuoteRepository(application);
        favQuoteRepository.insert(quote);
    }

}
