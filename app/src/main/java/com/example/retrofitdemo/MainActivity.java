package com.example.retrofitdemo;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private EditText editText;

    private Button button;

    private TextView responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        editText = findViewById(R.id.city_name);
        button = findViewById(R.id.city_click);
        responseText = findViewById(R.id.response_text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchWeatherDetails();
            }
        });
    }

    private void fetchWeatherDetails() {

        //Obtain an instance of Retrofit by calling the static method.
        Retrofit retrofit = NetworkClient.getRetrofitClient();

        /*
        The main purpose of Retrofit is to create HTTP calls from the Java interface based on the annotation associated with each method. This is achieved by just passing the interface class as parameter to the create method
        */
        WeatherAPIs weatherAPIs = retrofit.create(WeatherAPIs.class);

        /*
        Invoke the method corresponding to the HTTP request which will return a Call object. This Call object will used to send the actual network request with the specified parameters
        */
        Call call = weatherAPIs.getWeatherByCity(editText.getText().toString(), "235bef5a99d6bc6193525182c409602c");

        /*
        This is the line which actually sends a network request. Calling enqueue() executes a call asynchronously. It has two callback listeners which will invoked on the main thread
        */
        call.enqueue(new Callback() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call call, Response response) {
                /*This is the success callback. Though the response type is JSON, with Retrofit we get the response in the form of WResponse POJO class
                 */
                if (response.body() != null) {
                    WResponse wResponse = (WResponse) response.body();

                    responseText.setText("Temp: " + wResponse.getMain().getTemp() + "\n " +
                            "Humidity: " + wResponse.getMain().getHumidity() + "\n" +
                            "Pressure: " + wResponse.getMain().getPressure());
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                /*
                Error callback
                */
            }
        });

    }
}