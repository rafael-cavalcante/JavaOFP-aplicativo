package com.example.javaofp.javaofp.dominio;

import com.example.javaofp.javaofp.R;

import java.util.ArrayList;

public class Perfil {
    private static ArrayList<Integer> perfis = new ArrayList<Integer>(){
        {
            add(R.drawable.perfil1);
            add(R.drawable.perfil2);
            add(R.drawable.perfil3);
            add(R.drawable.perfil4);
            add(R.drawable.perfil5);
            add(R.drawable.perfil6);
            add(R.drawable.perfil7);
            add(R.drawable.perfil8);
            add(R.drawable.perfil9);
            add(R.drawable.perfil10);
            add(R.drawable.perfil11);
            add(R.drawable.perfil12);
            add(R.drawable.perfil13);
            add(R.drawable.perfil14);
        }

    };

    public static Integer getPerfil(int indentificador){
        return perfis.get(indentificador);
    }

    public static int getQuantidadePerfil(){
        return perfis.size();
    }

}
