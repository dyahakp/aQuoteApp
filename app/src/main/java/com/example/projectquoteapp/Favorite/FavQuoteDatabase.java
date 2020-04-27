package com.example.projectquoteapp.Favorite;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FavQuote.class}, version = 1)
public abstract class FavQuoteDatabase extends RoomDatabase {
    public static volatile FavQuoteDatabase INSTANCE;

    public static FavQuoteDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FavQuoteDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FavQuoteDatabase.class, "fav_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract FavQuoteDAO favQuoteDAO();
}
