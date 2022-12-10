package com.utec.pft.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectionRest {

    private static Retrofit retrofit = null;
    public static String ip="167.57.93.253";
    private static final String RUTA_API = "http://186.54.18.231:8080/PFT/tambo/";


    public static Retrofit getConnection() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        Gson gson = new GsonBuilder()
                .setDateFormat("dd/MM/yyyy")
                .create();
        if (retrofit == null) {
            String ipFinal= "http://"+ip+":8080/PFT/tambo/";
            retrofit = new Retrofit.Builder()
                    .baseUrl(ipFinal)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
