package com.example.javaofp.javaofp.dominio;
import java.util.ArrayList;
import java.util.List;

public class Ranking {
	private List <Estudante> lista;
	public Ranking () {
		lista = new ArrayList<Estudante>(); 
	}

	public List<Estudante> getLista() {
		return lista;
	}

	public void setLista(List<Estudante> lista) {
		this.lista = lista;
	}

}