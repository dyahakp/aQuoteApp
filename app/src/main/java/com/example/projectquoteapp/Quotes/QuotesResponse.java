package com.example.projectquoteapp.Quotes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuotesResponse {
    @SerializedName("qotd_date")
    @Expose
    private String qotdDate;
    @SerializedName("quote")
    @Expose
    private QuotesModel quote;

    public String getQotdDate() {
        return qotdDate;
    }

    public void setQotdDate(String qotdDate) {
        this.qotdDate = qotdDate;
    }

    public QuotesModel getQuote() {
        return quote;
    }

    public void setQuote(QuotesModel quote) {
        this.quote = quote;
    }
}