package com.example.javaofp.javaofp.serviceWS;

import android.os.AsyncTask;
import android.util.Log;

import com.example.javaofp.javaofp.dominio.Estudante;

import java.io.IOException;

import retrofit2.Call;

public class EstudanteTask extends AsyncTask<String, Void, Estudante> {
    @Override
    protected Estudante doInBackground(String... strings) {
        EstudanteService estudanteService = ServiceGenerator.createService(EstudanteService.class);

        Call<Estudante> call = estudanteService.solicitarLogin(strings[0], strings[1]);

        Estudante estudante = null;

        try{
            estudante = call.execute().body();

        }catch(IOException ex){
            Log.e("MainActivity", ex.getMessage());

        }

        return estudante;
    }
}
