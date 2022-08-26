package com.example.javaofp.javaofp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class CustomAdapterLink extends RecyclerView.Adapter<CustomAdapterLink.CustomViewHolder>{
    private Context context;
    private List<String> links;

    public CustomAdapterLink(Context context, List<String> links) {
        this.context = context;
        this.links = links;
    }

    @NonNull
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.item_link, parent, false));
    }

    public void onBindViewHolder(@NonNull final CustomViewHolder holder, final int position) {
        holder.itemTitle.setText("Vídio "+"0"+position);
        holder.itemImage.setImageResource(R.drawable.link);
        holder.itemRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(links.get(position));
                Intent navegador = new Intent(Intent.ACTION_VIEW,uri);
                context.startActivity(navegador);
            }
        });

    }

    public int getItemCount() {
        return links.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{

        private ImageView itemImage;
        private TextView itemTitle;
        private RelativeLayout itemRelativeLayout;

        public CustomViewHolder(View view) {
            super(view);
            itemImage = view.findViewById(R.id.ivIcone);
            itemTitle = view.findViewById(R.id.tvLink);
            itemRelativeLayout = view.findViewById(R.id.rlFundo);
        }
    }
}
