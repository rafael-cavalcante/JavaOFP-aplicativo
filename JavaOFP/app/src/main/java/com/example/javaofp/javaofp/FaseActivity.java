package com.example.javaofp.javaofp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ProgressBar;

import com.example.javaofp.javaofp.dominio.Estudante;
import com.example.javaofp.javaofp.dominio.Progresso;
import com.example.javaofp.javaofp.serviceWS.RetrofitConfig;
import com.example.javaofp.javaofp.dominio.Conteudo;
import com.example.javaofp.javaofp.dominio.Alternativa;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FaseActivity extends AppCompatActivity {
    private Estudante estudante;
    private Progresso progresso;
    private Conteudo conteudo;
    private CountDownTimer tempo;
    private AlertDialog alerta;
    private RadioButton a1, a2, a3, a4;
    private RadioGroup rgAlternativas;
    private ProgressBar progressoBar;
    private TextView tvEnunciado,tvTempo;
    private int nQuestao = 0, nQuestaoCertas = 0,status, tentativas = 0;
    private long timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fase);

        tvEnunciado = findViewById(R.id.tvEnunciado);
        tvTempo = findViewById(R.id.tvTempo);
        a1 = findViewById(R.id.radioButton);
        a2 = findViewById(R.id.radioButton2);
        a3 = findViewById(R.id.radioButton3);
        a4 = findViewById(R.id.radioButton4);
        rgAlternativas = findViewById(R.id.rgAlternativas);
        progressoBar = findViewById(R.id.progressBar);

        Intent intent = getIntent();

        if(intent.hasExtra("estudante")){

            estudante = (Estudante) intent.getSerializableExtra("estudante");
        }
        if(intent.hasExtra("progresso")){

            progresso = (Progresso) intent.getSerializableExtra("progresso");
            nQuestao = progresso.getnStopQuestao();
            nQuestaoCertas = progresso.getnQuestoesCertas();
        }
        if(intent.hasExtra("conteudo")) {

            conteudo = (Conteudo) intent.getSerializableExtra("conteudo");
            tvEnunciado.setText(conteudo.getQuestoes().get(nQuestao).getEnunciado());
            buscarAlternativas(conteudo.getQuestoes().get(nQuestao).getId());
        }


        progressoBar.setMax(conteudo.getQuestoes().size() * 10);
        progressoBar.setProgress((nQuestao + 1)* 10);

    }

    public void responderQuestao(View view){
        int idResposta = -1;

        if(a1.isChecked()){
            idResposta = 0;
        }else if(a2.isChecked()){
            idResposta = 1;
        }else if(a3.isChecked()){
            idResposta = 2;
        }else if(a4.isChecked()){
            idResposta = 3;
        }else{
            Toast.makeText(getApplicationContext(), "Selecione uma Alternativa",Toast.LENGTH_LONG).show();
        }

        if(idResposta != -1){
            if(conteudo.getQuestoes().get(nQuestao).getAlternativas().get(idResposta).getAlternativaCorreta() == 1){
                this.status = 1;
                mensagem("Resposta Correta","Parabéns você acertou",R.drawable.feliz, 1, "Próxima Questão");
                nQuestaoCertas++;
                tentativas--;
            }else if (tentativas < 2){
                mensagem("Resposta Errada","Não foi dessa vez mas não desista",R.drawable.triste,0, "Tentar novamente");

            }else{
                mensagem("Resposta Errada","Suas tentativas acabaram",R.drawable.triste,0, "Próxima Questão");

            }

            rgAlternativas.clearCheck();

            tempo.cancel();

        }

        tentativas++;

    }

    public void buscarAlternativas(int id){
        Call<List<Alternativa>> call = new RetrofitConfig().getAlternativaService().buscarAlternativas(id);

        call.enqueue(new Callback<List<Alternativa>>() {

            @Override
            public void onResponse(Call <List<Alternativa>> call, Response<List<Alternativa>> response) {

                List<Alternativa> listAlternativas = response.body();

                if(!listAlternativas.isEmpty()){
                    a1.setText(listAlternativas.get(0).getDescricao());
                    a2.setText(listAlternativas.get(1).getDescricao());
                    a3.setText(listAlternativas.get(2).getDescricao());
                    a4.setText(listAlternativas.get(3).getDescricao());
                }

                rgAlternativas.setVisibility(View.INVISIBLE);

                contagemRegressiva(36000);

                conteudo.getQuestoes().get(nQuestao).setAlternativas(listAlternativas);
            }

            @Override
            public void onFailure(Call<List<Alternativa>> call, Throwable t) {

            }
        });
    }

    public void proximaQuestao(){

        if(nQuestao < conteudo.getQuestoes().size()){

            progressoBar.setProgress((nQuestao + 1)* 10);

            tvEnunciado.setText(conteudo.getQuestoes().get(nQuestao).getEnunciado());
            buscarAlternativas(conteudo.getQuestoes().get(nQuestao).getId());

        }else{
            if(nQuestaoCertas >= (double) nQuestao/2){
                aplicarPontos(estudante.getNickname(), conteudo.getId());

            }else{
                deletarProgresso(estudante.getNickname(), conteudo.getId());

            }
        }
        tentativas = 0;
        status = 0;
    }

    public void contagemRegressiva(long tempoTotal){
        tempo = new CountDownTimer(tempoTotal,1000) {
            @Override
            public void onTick(long l) {
                timer = l;
                tvTempo.setText((l/1000)+"s");
                if(l <= 30000){
                    rgAlternativas.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFinish() {
                mensagem("Tempo Esgotado","Não foi possivel registrar sua resposta",R.drawable.tempo, 2, "Próxima Questão");
            }
        }.start();
    }

    public void mensagem(String titulo, String mensagem, int img, final int status, String enunciado){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titulo);
        builder.setMessage(mensagem);
        builder.setIcon(img);
        builder.setCancelable(false);
        builder.setPositiveButton(enunciado, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                if(status == 1 || status == 2 || tentativas == 3){
                    salvarProgresso(true);
                }else{
                    contagemRegressiva(timer);
                }
            }
        });
        builder.setNegativeButton("sair", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                salvarProgresso(false);
                finish();
            }
        });
        alerta = builder.create();
        alerta.show();
    }

    public void salvarProgresso(final boolean continuar){
        Progresso progresso = new Progresso(estudante.getNickname(),conteudo.getQuestoes().get(nQuestao).getId(),status,tentativas,30 - ((int) timer / 1000));

        Call<Boolean> call = new RetrofitConfig().getProgressoService().addProgresso(progresso);

        call.enqueue(new Callback<Boolean>() {

            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.body()){
                    nQuestao++;
                    if(continuar){
                        proximaQuestao();
                    }

                }else{
                    Toast.makeText(FaseActivity.this, "ERRO não foi possível Salvar Progresso",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

    public void aplicarPontos(String nickname, int idconteudo) {
        Call<Double> call = new RetrofitConfig().getProgressoService().calcularPontuacao(nickname, idconteudo);

        call.enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                estudante.setPontuacao(estudante.getPontuacao() + response.body());
                customDialogo(response.body(), 2);

            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {

            }
        });
    }

    public void deletarProgresso(String nickname, int idconteudo){
        Call<Void> call = new RetrofitConfig().getProgressoService().deletarProgresso(nickname, idconteudo);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                customDialogo(0, 1);

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }

    public void customDialogo(double pontos, int tipo){
        final Dialog dialog = new Dialog(FaseActivity.this);
        dialog.setContentView(R.layout.popup_conteudofinalizado);
        Button btClose = dialog.findViewById(R.id.btFinalizar);
        TextView tvNickname = dialog.findViewById(R.id.tvNickname);
        TextView tvPontos = dialog.findViewById(R.id.tvPontos);
        TextView tvMensagem = dialog.findViewById(R.id.tvMensagem);
        ImageView ivMedalha = dialog.findViewById(R.id.ivMedalha);
        dialog.setCancelable(false);

        if(tipo == 1){
            tvMensagem.setText("você errou mais de 50% que pena, não desista");
        }else if(tipo == 2){
            if(nQuestaoCertas*100/nQuestao > 80){
                tvMensagem.setText("Parabéns Conteúdo Finalizado");
                ivMedalha.setImageResource(R.drawable.medalha_ouro);
            }else if(nQuestaoCertas*100/nQuestao > 70){
                tvMensagem.setText("Parabéns Conteúdo Finalizado");
                ivMedalha.setImageResource(R.drawable.medalha_prata);
            }else{
                tvMensagem.setText("Parabéns Conteúdo Finalizado");
                ivMedalha.setImageResource(R.drawable.medalha_bronze);
            }

        }

        tvNickname.setText(estudante.getNickname());
        tvPontos.setText("Pontos :"+pontos);
        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();

            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();
    }

    @Override
    public void onBackPressed(){
        tempo.cancel();
        finish();

    }

}