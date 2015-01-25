package com.example.pastordeovelhas;



/**
 * Atributos comuns a personagens animaveis.
 * 
 * @author wolvery
 *
 */

public abstract class Personagem {

	private Ocupante nomeEspaco;
	private Casa casaPersonagem;
	
	public Personagem(Ocupante nomeEspaco, Casa casaPersonagem) {
		super();
		this.nomeEspaco = nomeEspaco;
		this.casaPersonagem = casaPersonagem;
	}
	/**
	 * @return the nomeEspaco
	 */
	public Ocupante getNomeEspaco() {
		return nomeEspaco;
	}
	/**
	 * @param nomeEspaco the nomeEspaco to set
	 */
	public void setNomeEspaco(Ocupante nomeEspaco) {
		this.nomeEspaco = nomeEspaco;
	}
	/**
	 * @return the casaPersonagem
	 */
	public Casa getCasaPersonagem() {
		return casaPersonagem;
	}
	/**
	 * @param casaPersonagem the casaPersonagem to set
	 */
	public void setCasaPersonagem(Casa casaPersonagem) {
		this.casaPersonagem = casaPersonagem;
	}
	
	
}
