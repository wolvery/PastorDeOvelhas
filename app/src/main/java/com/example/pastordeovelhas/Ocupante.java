package com.example.pastordeovelhas;

/**
 * Valores para a ocupacao de alguma casa.
 * 
 * @author wolvery
 *
 */

public enum Ocupante {
	Vazio(0),Pastor(1),Ovelha(2),Lobo(3),Cerca(4);
	private int valorOcupado;
	//Construtor
	private Ocupante(int valor) {
		valorOcupado = valor;
	}
	//Valor do enum de espaco ocupado.
	// Vazia ->0, Pastor>-1...
	public int getValorEspaco() {
		return valorOcupado;
	}
	
}
