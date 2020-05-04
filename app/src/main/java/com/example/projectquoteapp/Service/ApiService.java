package com.example.projectquoteapp.Service;
import com.example.projectquoteapp.Quotes.QuotesResponse;
import com.example.projectquoteapp.Search.SearchQuotesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/api/qotd")
    Call<QuotesResponse> getQotd();

    @GET("/api/quotes")
    Call<SearchQuotesResponse> getQuotes(
            @Header("Authorization") String token
    );
    
    @GET("/api/quotes")
    Call<SearchQuotesResponse> getQuotesFilter(
            @Header("Authorization") String token,
            @Query("filter") String filter,
            @Query("type") String type
    );
}
