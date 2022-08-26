package com.example.javaofp.javaofp.serviceWS;

import com.example.javaofp.javaofp.dominio.Alternativa;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AlternativaService {

    @GET("alternativas/{questaoId}")
    Call<List<Alternativa>> buscarAlternativas(@Path("questaoId") int id);
}
