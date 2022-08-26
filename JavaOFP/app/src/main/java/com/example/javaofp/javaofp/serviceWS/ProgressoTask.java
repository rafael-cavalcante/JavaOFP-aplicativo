package com.example.javaofp.javaofp.serviceWS;

import android.os.AsyncTask;
import android.util.Log;

import com.example.javaofp.javaofp.dominio.Progresso;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

public class ProgressoTask extends AsyncTask<String, Void, List<Progresso>>{
    @Override
    protected List<Progresso> doInBackground(String... strings) {
        ProgressoService progressoService = ServiceGenerator.createService(ProgressoService.class);

        Call<List<Progresso>> call = progressoService.buscarProgresso(strings[0]);

        List<Progresso> listProgressos = null;

        try{
            listProgressos = call.execute().body();
        }catch(IOException ex){
            Log.e("MainActivity", ex.getMessage());
        }

        return listProgressos;
    }
}
