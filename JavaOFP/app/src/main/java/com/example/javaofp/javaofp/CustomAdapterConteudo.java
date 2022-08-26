package com.example.javaofp.javaofp;

/**
 * Created by javaofp on 25/06/19.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.javaofp.javaofp.dominio.Conteudo;
import com.example.javaofp.javaofp.dominio.Estudante;
import com.example.javaofp.javaofp.dominio.Progresso;
import com.example.javaofp.javaofp.dominio.Questao;
import com.example.javaofp.javaofp.serviceWS.RetrofitConfig;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomAdapterConteudo extends RecyclerView.Adapter<CustomAdapterConteudo.CustomViewHolder> {

    private Context context;
    private List<Conteudo> conteudos;
    private List<Progresso> progressos;
    private Estudante estudante;

    public CustomAdapterConteudo(Context context, List<Conteudo> conteudos, List<Progresso> progressos, Estudante estudante) {
        this.context = context;
        this.conteudos = conteudos;
        this.progressos = progressos;
        this.estudante = estudante;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.item_conteudo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, final int position) {
        holder.itemTitle.setText(conteudos.get(position).getNome());
        holder.itemImage.setImageResource(conteudos.get(position).getImageIcone());
        holder.relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(conteudos.get(position).getImageIcone() != R.drawable.conteudo_bloqueado){
                    customDialogoMain2(position);

                }else{
                    Toast.makeText(context.getApplicationContext(), "Conteúdo bloqueado",Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return conteudos.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{

        private ImageView itemImage;
        private TextView itemTitle;
        private RelativeLayout relative;

        public CustomViewHolder(View view) {
            super(view);
            itemImage = view.findViewById(R.id.ivIcone);
            itemTitle = view.findViewById(R.id.tvTitulo);
            relative = view.findViewById(R.id.rlItem);
        }
    }

    public void customDialogoMain2(int position){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_conteudo);
        dialog.setCancelable(true);

        final Conteudo conteudo = conteudos.get(position);
        final Button btIniciar = dialog.findViewById(R.id.btIniciar);
        TextView tvTitulo = dialog.findViewById(R.id.tvTitulo);
        TextView tvDescricao = dialog.findViewById(R.id.tvDescricao);
        RecyclerView recyclerView = dialog.findViewById(R.id.rvLinks);
        TextView tvQuestoesCertas = dialog.findViewById(R.id.tvQuestoesCertas);
        TextView tvTotalQuestoes = dialog.findViewById(R.id.tvTotalQuestoes);


        if(progressos.size() >= conteudo.getId()){
            final Progresso progresso = progressos.get(position);

            tvQuestoesCertas.setText(Integer.toString(progresso.getnQuestoesCertas()));
            tvTotalQuestoes.setText(Integer.toString(progresso.getQuantidadeQuestoes()));

            if (!(progresso.getQuantidadeQuestoes() == progresso.getnStopQuestao())){
                Call<List<Questao>> call = new RetrofitConfig().getQuestaoService().buscarQuestoes(conteudo.getId());

                call.enqueue(new Callback<List<Questao>>() {

                    @Override
                    public void onResponse(Call <List<Questao>> call, Response<List<Questao>> response) {
                        conteudo.setQuestoes(response.body());

                    }

                    @Override
                    public void onFailure(Call<List<Questao>> call, Throwable t) {

                    }
                });
            }


            btIniciar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (progresso.getQuantidadeQuestoes() == progresso.getnStopQuestao()){
                        Toast.makeText(context.getApplicationContext(), "Parabéns Conteudo já Finalizado",Toast.LENGTH_LONG).show();

                    }else{
                        Intent intent = new Intent(context, FaseActivity.class);
                        intent.putExtra("conteudo", conteudo);
                        intent.putExtra("estudante", estudante);
                        intent.putExtra("progresso", progresso);

                        context.startActivity(intent);
                        dialog.dismiss();

                    }

                }
            });
        } else {
            btIniciar.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Toast.makeText(context.getApplicationContext(), "Questões indisponíveis",Toast.LENGTH_LONG).show();
                }
            });
        }

        btIniciar.setEnabled(true);

        tvTitulo.setText("Nº "+ conteudo.getId()+" "+ conteudo.getNome());
        tvDescricao.setText(conteudo.getDescricao());

        CustomAdapterLink adapter = new CustomAdapterLink(context, conteudo.getLinks());

        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        recyclerView.setAdapter(adapter);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

}
