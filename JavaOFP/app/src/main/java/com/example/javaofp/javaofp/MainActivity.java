package com.example.javaofp.javaofp;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.javaofp.javaofp.dominio.Estudante;
import com.example.javaofp.javaofp.dominio.Perfil;
import com.example.javaofp.javaofp.dominio.Progresso;
import com.example.javaofp.javaofp.dominio.Ranking;
import com.example.javaofp.javaofp.serviceWS.ConteudoTask;
import com.example.javaofp.javaofp.serviceWS.EstudanteTask;
import com.example.javaofp.javaofp.serviceWS.ProgressoTask;
import com.example.javaofp.javaofp.dominio.Conteudo;
import com.example.javaofp.javaofp.serviceWS.RetrofitConfig;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Estudante estudante = new Estudante();
    private TextView tvPontos;
    private ImageView ivPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvNickname = findViewById(R.id.tvNickname);
        tvPontos = findViewById(R.id.tvPontos);
        ivPerfil = findViewById(R.id.ivPerfilEstudante);

        Intent intent = getIntent();

        if(intent.hasExtra("estudante")) {
            estudante = (Estudante) intent.getSerializableExtra("estudante");
            tvNickname.setText(estudante.getNickname());
            tvPontos.setText(estudante.getPontuacao()+"p");
            ivPerfil.setImageResource(Perfil.getPerfil(estudante.getFoto()));

        }

        ivPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialogoPerfil();
            }
        });

        updateProgressoConteudo();
    }

    public void onclick(View view){
        Call <Ranking> call = new RetrofitConfig().getRankingService().buscarRanking();

        call.enqueue(new Callback<Ranking>() {

            @Override
            public void onResponse(Call <Ranking> call, Response<Ranking> response) {
                customDialogoRanking(response.body());

            }

            @Override
            public void onFailure(Call<Ranking> call, Throwable t) {

            }
        });
    }

    public void updateProgressoConteudo(){
        ProgressoTask progressoTask = new ProgressoTask();

        List<Progresso> progressos = new ArrayList<Progresso>();

        try{
            progressos = progressoTask.execute(estudante.getNickname()).get();

        }catch(Exception ex){
            Log.e("MainActivity", ex.getMessage());

        }

        ConteudoTask conteudoTask = new ConteudoTask();

        try{

            List<Conteudo> conteudos = conteudoTask.execute().get();

            conteudos.get(0).setImageIcone(R.drawable.conteudo_desbloqueado);

            for(Progresso p : progressos){
                if((p.getnQuestoesCertas() > p.getQuantidadeQuestoes()/2) && p.getnStopQuestao() <= p.getQuantidadeQuestoes()){
                    conteudos.get(p.getConteudoId()).setImageIcone(R.drawable.conteudo_desbloqueado);
                }
            }

            CustomAdapterConteudo adapter = new CustomAdapterConteudo(MainActivity.this, conteudos, progressos, estudante);

            RecyclerView recyclerView = findViewById(R.id.rvConteudos);

            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));

            recyclerView.setAdapter(adapter);

        }catch(Exception ex) {
            Log.e("MainActivity", ex.getMessage());
        }
    }

    public void updateEstudante(){
        EstudanteTask estudanteTask = new EstudanteTask();

        try{
            estudante = estudanteTask.execute(estudante.getNickname(), estudante.getSenha()).get();

            tvPontos.setText(estudante.getPontuacao()+"p");
            ivPerfil.setImageResource(Perfil.getPerfil(estudante.getFoto()));

        }catch(Exception ex){
            Log.e("MainActivity", ex.getMessage());
        }
    }

    public void customDialogoRanking(Ranking ranking){
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.popup_ranking);
        dialog.setCancelable(false);

        RecyclerView recyclerView = dialog.findViewById(R.id.rvRanking);
        CardView cvVoltarMenu = dialog.findViewById(R.id.cvVoltar);

        CustomAdapterRanking adapter = new CustomAdapterRanking(MainActivity.this,ranking);

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));

        recyclerView.setAdapter(adapter);

        cvVoltarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

    public void customDialogoPerfil(){
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.popup_perfil);

        RecyclerView recyclerView = dialog.findViewById(R.id.rvPerfil);

        CustomAdapterPerfil adapter = new CustomAdapterPerfil(MainActivity.this, estudante);

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));

        recyclerView.setAdapter(adapter);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        updateEstudante();
        updateProgressoConteudo();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        SharedPreferences save = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor saveEdit = save.edit();
        saveEdit.clear();
        saveEdit.commit();
    }

}