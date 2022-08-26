package com.example.javaofp.javaofp.dominio;

import java.io.Serializable;

public class Progresso implements Serializable{
	private int conteudoId;
	private int nStopQuestao;
	private int quantidadeQuestoes;
	private int nQuestoesCertas;
	private String nickname;
	private int questaoId;
	private int statusId;
	private int tentativas;
	private int tempo;
	
	public Progresso() {
		
	}
	
	public Progresso(String nickname, int questaoId, int statusId, int tentativas, int tempo) {
		super();
		this.nickname = nickname;
		this.questaoId = questaoId;
		this.statusId = statusId;
		this.tentativas = tentativas;
		this.tempo = tempo;
	}
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getQuestaoId() {
		return questaoId;
	}
	public void setQuestaoId(int questaoId) {
		this.questaoId = questaoId;
	}
	public int getQuantidadeQuestoes() {
		return quantidadeQuestoes;
	}
	public void setQuantidadeQuestoes(int quantidadeQuestoes) {
		this.quantidadeQuestoes = quantidadeQuestoes;
	}

	public int getConteudoId() {
		return conteudoId;
	}

	public void setConteudoId(int conteudoId) {
		this.conteudoId = conteudoId;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public int getTentativas() {
		return tentativas;
	}

	public void setTentativas(int tentativas) {
		this.tentativas = tentativas;
	}

	public int getTempo() {
		return tempo;
	}

	public void setTempo(int tempo) {
		this.tempo = tempo;
	}

	public int getnQuestoesCertas() {
		return nQuestoesCertas;
	}

	public void setnQuestoesCertas(int nQuestoesCertas) {
		this.nQuestoesCertas = nQuestoesCertas;
	}

	public int getnStopQuestao() {
		return nStopQuestao;
	}

	public void setnStopQuestao(int nStopQuestao) {
		this.nStopQuestao = nStopQuestao;
	} 
	
}
