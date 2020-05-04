package com.example.projectquoteapp.Search;

import com.example.projectquoteapp.Quotes.QuotesResult;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchQuotesResponse {
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("last_page")
    @Expose
    private Boolean lastPage;
    @SerializedName("quotes")
    @Expose
    private ArrayList<QuotesResult> quotes = null;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Boolean getLastPage() {
        return lastPage;
    }

    public void setLastPage(Boolean lastPage) {
        this.lastPage = lastPage;
    }

    public ArrayList<QuotesResult> getQuotes() {
        return quotes;
    }

    public void setQuotes(ArrayList<QuotesResult> quotes) {
        this.quotes = quotes;
    }
}
