package com.example.javaofp.javaofp.serviceWS;

/**
 * Created by javaofp on 08/08/19.
 */

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitConfig {

    private final Retrofit retrofit;

    public RetrofitConfig() {
        this.retrofit = new Retrofit.Builder()

                //.baseUrl("http://jijel.ifrn.edu.br/javaofpserver/webapi/")
                .baseUrl("http://192.168.100.180:8080/javaofpserver/webapi/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public QuestaoService getQuestaoService() {
        return this.retrofit.create(QuestaoService.class);
    }

    public AlternativaService getAlternativaService() { return this.retrofit.create(AlternativaService.class); }

    public RankingService getRankingService() {
        return this.retrofit.create(RankingService.class);
    }

    public ProgressoService getProgressoService(){ return this.retrofit.create(ProgressoService.class); }

    public EstudanteService getEstudanteService(){ return this.retrofit.create(EstudanteService.class); }

}
