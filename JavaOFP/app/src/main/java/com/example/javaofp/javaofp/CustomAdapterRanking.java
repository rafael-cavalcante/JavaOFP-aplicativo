package com.example.javaofp.javaofp;

/**
 * Created by javaofp on 25/06/19.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.javaofp.javaofp.dominio.Perfil;
import com.example.javaofp.javaofp.dominio.Ranking;

public class CustomAdapterRanking extends RecyclerView.Adapter<CustomAdapterRanking.CustomViewHolder> {

    private Context context;
    private Ranking items;

    public CustomAdapterRanking(Context context, Ranking items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ranking, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, final int position) {
        holder.itemTitle.setText(items.getLista().get(position).getNickname());
        holder.itemSubTitle.setText(String.valueOf(Math.ceil(items.getLista().get(position).getPontuacao())));
        holder.itemImage.setImageResource(Perfil.getPerfil(items.getLista().get(position).getFoto()));

    }

    @Override
    public int getItemCount() {
        return items.getLista().size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{

        private ImageView itemImage;
        private TextView itemTitle;
        private TextView itemSubTitle;

        public CustomViewHolder(View view) {
            super(view);
            itemImage = view.findViewById(R.id.ivIcone);
            itemTitle = view.findViewById(R.id.tvTitulo);
            itemSubTitle = view.findViewById(R.id.tvSubTitulo);
        }
    }

}
