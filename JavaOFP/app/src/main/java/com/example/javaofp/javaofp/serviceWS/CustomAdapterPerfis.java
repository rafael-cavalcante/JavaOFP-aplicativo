package com.example.javaofp.javaofp.serviceWS;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.javaofp.javaofp.R;

import java.util.ArrayList;

public class CustomAdapterPerfis extends RecyclerView.Adapter<CustomAdapterPerfis.CustomViewHolder> {

    private Context context;
    private ArrayList<Integer> perfis = new ArrayList<>();

    @NonNull
    @Override
    public CustomAdapterPerfis.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterPerfis.CustomViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{

        private ImageView itemImage;
        private RelativeLayout relative;

        public CustomViewHolder(View view) {
            super(view);
            itemImage = view.findViewById(R.id.ivIcone);
            relative = view.findViewById(R.id.rlItem);
        }
    }
}
