package com.xbank.app;

import retrofit2.Call;
import retrofit2.http.GET;

interface JokeApiInterface {
    @GET("random_joke")
    Call<Joke> getAJoke();


}

