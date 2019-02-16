package com.example.retrofitdemo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPIs {
//api.openweathermap.org/data/2.5/weather?q=London
//
//api.openweathermap.org/data/2.5/weather?q=London,uk
    @GET("/data/2.5/weatherc")
    Call<WResponse> getWeatherByCity(@Query("q") String city, @Query("appid") String apiKey);
}
