package com.example.javaofp.javaofp.serviceWS;

import com.example.javaofp.javaofp.dominio.Ranking;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by javaofp on 27/08/19.
 */

public interface RankingService {
    @GET("ranking")
    Call <Ranking> buscarRanking();
}
