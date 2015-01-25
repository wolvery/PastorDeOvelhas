package com.example.pastordeovelhas;

public class Casa {
	/**
	 * Posicao atual da casa.
	 */
	private int posicaoX, posicaoY;
	
	/**
	 * Construtor da posicao da casa do Elemento.
	 * 
	 * @param posicaoX
	 * @param posicaoY
	 */
	public Casa(int posicaoX, int posicaoY) {
		super();
		this.posicaoX = posicaoX;
		this.posicaoY = posicaoY;
	}

	/**
	 * @return the posicaoX
	 */
	public int getPosicaoX() {
		return posicaoX;
	}

	/**
	 * @param posicaoX the posicaoX to set
	 */
	public void setPosicaoX(int posicaoX) {
		this.posicaoX = posicaoX;
	}

	/**
	 * @return the posicaoY
	 */
	public int getPosicaoY() {
		return posicaoY;
	}

	/**
	 * @param posicaoY the posicaoY to set
	 */
	public void setPosicaoY(int posicaoY) {
		this.posicaoY = posicaoY;
	}
}