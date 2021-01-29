package com.example.zomato.poc.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RestApiServiceBuilder {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://developers.zomato.com/";

    private static Retrofit getRetrofit() {
        if (retrofit == null) {
            //HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            //logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    //.addInterceptor(logging)
                    .build();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static <T> T buildService(Class<T> serviceType) {
        return getRetrofit().create(serviceType);
    }
}
