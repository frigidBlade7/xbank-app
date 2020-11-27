package com.xbank.app;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
interface JokeDAO {

    @Insert(onConflict = REPLACE)
    void insert(Joke joke);

    @Query("SELECT * FROM joke ORDER BY id")
    LiveData<List<Joke>> getAllJokes();
}
