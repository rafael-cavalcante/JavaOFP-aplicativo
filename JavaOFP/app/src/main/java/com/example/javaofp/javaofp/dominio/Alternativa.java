package com.example.javaofp.javaofp.dominio;

import java.io.Serializable;

public class Alternativa implements Serializable {
		private int id;
		private String descricao;
		private int questaoId;
		private int alternativaCorreta;

		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}

		public String getDescricao() {
			return descricao;
		}
		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}
		
		public int getQuestaoId() {
			return questaoId;
		}
		public void setQuestaoId(int questao) {
			this.questaoId = questao;
		}

		public int getAlternativaCorreta() {
			return alternativaCorreta;
		}

		public void setAlternativaCorreta(int alternativaCorreta) {
			this.alternativaCorreta = alternativaCorreta;
		}
}



