package com.example.javaofp.javaofp.serviceWS;

import android.os.AsyncTask;
import android.util.Log;

import com.example.javaofp.javaofp.dominio.Conteudo;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

public class ConteudoTask extends AsyncTask<Void, Void, List<Conteudo>>{

    @Override
    protected List<Conteudo> doInBackground(Void... voids) {
        ConteudoService conteudoService = ServiceGenerator.createService(ConteudoService.class);

        Call<List<Conteudo>> call = conteudoService.buscarConteudos();

        List<Conteudo> listConteudos = null;

        try{
            listConteudos = call.execute().body();
        }catch(IOException ex){
            Log.e("MainActivity", ex.getMessage());
        }

        return listConteudos;
    }
}
