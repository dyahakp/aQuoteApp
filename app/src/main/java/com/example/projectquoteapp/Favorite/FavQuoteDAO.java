package com.example.projectquoteapp.Favorite;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FavQuoteDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertQuote(FavQuote favQuote);

    @Update()
    void updateQuote(FavQuote favQuote);

    @Delete()
    void deleteQuote(FavQuote favQuote);

    @Query("SELECT * FROM favquote")
    LiveData<List<FavQuote>> selectAllFavQuotes();
}
