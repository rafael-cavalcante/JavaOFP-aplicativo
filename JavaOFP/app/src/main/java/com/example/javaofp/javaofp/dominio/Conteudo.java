package com.example.javaofp.javaofp.dominio;

import com.example.javaofp.javaofp.R;

import java.io.Serializable;
import java.util.List;

/**
 * Created by javaofp on 25/06/19.
 */

public class Conteudo implements Serializable {
    private int id;
    private String nome;
    private String descricao;
    private List<String> links;
    private Tipo tipo;
    private Status status;
    private List<Questao> questoes;
    private int imageIcone = R.drawable.conteudo_bloqueado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Questao> getQuestoes() {
        return questoes;
    }

    public void setQuestoes(List<Questao> questoes) {
        this.questoes = questoes;
    }

    public int getImageIcone() {
        return imageIcone;
    }

    public void setImageIcone(int imageIcone) {
        this.imageIcone = imageIcone;
    }
}
