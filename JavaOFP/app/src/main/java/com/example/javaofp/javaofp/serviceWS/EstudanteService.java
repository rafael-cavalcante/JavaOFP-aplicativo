package com.example.javaofp.javaofp.serviceWS;

import com.example.javaofp.javaofp.dominio.Estudante;

import java.sql.Date;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EstudanteService {
    @POST("estudantes")
    Call <Boolean> solicitarRegistro(@Body Estudante estudante);

    @GET("estudantes/{nickname},{senha}")
    Call <Estudante> solicitarLogin(@Path("nickname") String nickname, @Path("senha") String senha);

    @PUT("estudantes/{nickname},{perfil}")
    Call <Boolean> updatePerfil(@Path("nickname") String nickname, @Path("perfil") int perfil);
}
