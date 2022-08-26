package com.example.javaofp.javaofp.serviceWS;

import com.example.javaofp.javaofp.dominio.Questao;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface QuestaoService {

    @GET("questoes/{conteudoId}")
    Call <List<Questao>> buscarQuestoes(@Path("conteudoId") int id);
}
