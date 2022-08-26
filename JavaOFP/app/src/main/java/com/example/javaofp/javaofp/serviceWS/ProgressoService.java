package com.example.javaofp.javaofp.serviceWS;

import com.example.javaofp.javaofp.dominio.Progresso;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by javaofp on 10/10/19.
 */

public interface ProgressoService {
    @POST ("progressos")
    Call <Boolean> addProgresso(@Body Progresso progresso);

    @GET("progressos/{nickname}")
    Call <List<Progresso>> buscarProgresso(@Path("nickname") String nickname);

    @GET("progressos/{nickname},{idConteudo}")
    Call <Double> calcularPontuacao(@Path("nickname") String nickname, @Path("idConteudo") int idConteudo);

    @DELETE("progressos/{nickname},{idConteudo}")
    Call <Void> deletarProgresso(@Path("nickname") String nickname, @Path("idConteudo") int idConteudo);

}
