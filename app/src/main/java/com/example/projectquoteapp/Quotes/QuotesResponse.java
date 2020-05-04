package com.example.projectquoteapp.Quotes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuotesResponse {
    @SerializedName("qotd_date")
    @Expose
    private String qotdDate;
    @SerializedName("quote")
    @Expose
    private QuotesResult quote;

    public String getQotdDate() {
        return qotdDate;
    }

    public void setQotdDate(String qotdDate) {
        this.qotdDate = qotdDate;
    }

    public QuotesResult getQuote() {
        return quote;
    }

    public void setQuote(QuotesResult quote) {
        this.quote = quote;
    }
}