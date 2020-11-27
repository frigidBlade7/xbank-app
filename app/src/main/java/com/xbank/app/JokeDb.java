package com.xbank.app;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Joke.class}, version = 1)
public abstract class  JokeDb  extends RoomDatabase {

    public abstract JokeDAO jokeDAO();

}
