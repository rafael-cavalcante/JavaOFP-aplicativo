package com.example.javaofp.javaofp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.javaofp.javaofp.dominio.Estudante;
import com.example.javaofp.javaofp.dominio.Perfil;
import com.example.javaofp.javaofp.serviceWS.RetrofitConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomAdapterPerfil extends RecyclerView.Adapter<CustomAdapterPerfil.CustomViewHolder> {
    private Context context;
    private Estudante estudante;

    public CustomAdapterPerfil(Context context, Estudante estudante){
        this.context = context;
        this.estudante = estudante;
    }

    @NonNull
    @Override
    public CustomAdapterPerfil.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.item_perfil, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomAdapterPerfil.CustomViewHolder holder, final int position) {
        holder.itemImage.setImageResource(Perfil.getPerfil(position));
        holder.relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<Boolean> call = new RetrofitConfig().getEstudanteService().updatePerfil(estudante.getNickname(), position);

                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if(response.body()){
                            Toast.makeText(context, "Perfil alterado, realize login novamente", Toast.LENGTH_LONG).show();

                        }else{
                            Toast.makeText(context, "Não foi possíel alterar perfil" + position, Toast.LENGTH_LONG).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {

                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return Perfil.getQuantidadePerfil();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{

        private ImageView itemImage;
        private RelativeLayout relative;

        public CustomViewHolder(View view) {
            super(view);
            itemImage = view.findViewById(R.id.ivPerfil);
            relative = view.findViewById(R.id.rlPerfil);
        }
    }
}
