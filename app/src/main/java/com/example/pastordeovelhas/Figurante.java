package com.example.pastordeovelhas;

/**
 * Elemento terceiro ao jogo pode ser: Cerca ou vazio.
 * 
 * Nessa classe estao todos os estados possiveis,
 * as demais acoes ficam na classe dinamica.
 * @author wolvery
 *
 */
public class Figurante extends Personagem{
	public Figurante(Ocupante espaco,Casa casaGerada){
		super(espaco, casaGerada);		
	}	
}
