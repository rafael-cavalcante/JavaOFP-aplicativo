package com.example.javaofp.javaofp.serviceWS;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ServiceGenerator {

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder = new Retrofit.Builder()
            //.baseUrl("http://jijel.ifrn.edu.br/javaofpserver/webapi/")
            .baseUrl("http://192.168.100.180:8080/javaofpserver/webapi/")
            .addConverterFactory(JacksonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass){
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }
}
