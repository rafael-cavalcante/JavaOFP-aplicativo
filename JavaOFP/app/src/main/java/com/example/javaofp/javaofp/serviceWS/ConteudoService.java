package com.example.javaofp.javaofp.serviceWS;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import com.example.javaofp.javaofp.dominio.Conteudo;

/**
 * Created by javaofp on 08/08/19.
 */

public interface ConteudoService {

    @GET("conteudos")
    Call <List<Conteudo>> buscarConteudos();

}
