package com.example.pastordeovelhas;

import java.util.ArrayList;
import java.util.Iterator;
/**
 * Classe Jogador. 
 * Todos os personagens do Elemento se encontraram aqui.
 * @author wolvery
 *
 */
public class Dinamica {
	ArrayList<Personagem> figurantes = new ArrayList<Personagem>();
	/**
	 * Gera elementos em um tabuleiro.
	 * 
	 * @param qtdeElementos
	 */
	public Dinamica(int qtdeElementos) {
		int j = (int) Math.sqrt(qtdeElementos);

		for (int i = 0; i < j; i++) {
			for (; j >= 0; j--) {
				figurantes.add(new Figurante(Ocupante.Vazio, new Casa(i, j)));
			}
		}
	}
	/**
	 * Avalia se o espaco esta vazio.
	 * @param casaAVerificar
	 * @return
	 */
	public Boolean espacoDisponivel(Casa casaAVerificar) {
		Iterator<Personagem> iter = figurantes.iterator();
		while (iter.hasNext()) {
			Personagem elemento = iter.next();
			if (elemento.getCasaPersonagem() == casaAVerificar
					&& elemento.getNomeEspaco() == Ocupante.Vazio) {
				return true;
			}
		}

		return false;
	}
    /**
     * Avalia se o espaco esta vazio e o ocupa por personagem.
     * @param casaAVerificar
     * @return
     */
    public Boolean ocuparCasa(Casa casaAVerificar, Ocupante ocupante) {
        Iterator<Personagem> iter = figurantes.iterator();
        while (iter.hasNext()) {
            Personagem elemento = iter.next();

            if (elemento.getCasaPersonagem() == casaAVerificar
                    && elemento.getNomeEspaco() == Ocupante.Vazio) {
                figurantes.remove(elemento);
                elemento.setNomeEspaco(ocupante);
                figurantes.add(elemento);
                return true;
            }
        }

        return false;
    }

	/**
	 * Espaco contido na casa dentro do Array.
	 * @param casaAVerificar
	 * @return Espaco ocupado podendo ser Lobo, ovelha, pastor, vazio
	 */
	public Ocupante casaOcupadaPor(Casa casaAVerificar) {
		Iterator<Personagem> iter = figurantes.iterator();
		while (iter.hasNext()) {
			Personagem elemento = iter.next();
			if (elemento.getCasaPersonagem() == casaAVerificar) {
				return elemento.getNomeEspaco();
			}
		}
		return null;
	}
	
	/**
	 * Casa ocupada por Personagem
	 * @param casaAVerificar
	 * @return Personagem contido no Array.
	 */
	public Personagem casaOcupadaPorElemento(Casa casaAVerificar) {
		Iterator<Personagem> iter = figurantes.iterator();
		while (iter.hasNext()) {
			Personagem elemento = iter.next();
			if (elemento.getCasaPersonagem() == casaAVerificar) {
				return elemento;
			}
		}
		return null;
	}
	/**
	 * Remover elemento da casa.
	 * @param casaAVerificar
	 */
	public void removerCasa(Casa casaAVerificar) {
		Iterator<Personagem> iter = figurantes.iterator();
		while (iter.hasNext()) {
			Personagem elemento = iter.next();
			if (elemento.getCasaPersonagem() == casaAVerificar) {
				figurantes.remove(elemento);
			}
		}
	}
	/**
	 * Altera o estado da casa dentro do Array para ocupante vazio.
	 * @param casaAVerificar
	 */
	
	public void esvaziarCasa(Casa casaAVerificar) {
		Iterator<Personagem> iter = figurantes.iterator();
		while (iter.hasNext()) {
			Personagem elemento = iter.next();
			if (elemento.getCasaPersonagem() == casaAVerificar) {
				figurantes.remove(elemento);
				figurantes.add(new Figurante(Ocupante.Vazio,casaAVerificar));
			}
		}
	}
	
	public Boolean casasIguais(Casa origem, Casa destino){
		if (origem == destino){
			return true;
		}
		return false;
	}
	/**
	 * Casas proximas na distancia de 1 quadrado em x ou y.
	 * @param origem
	 * @param destino
	 * @return
	 */
	public Boolean diferencaEntreCasas(Casa origem, Casa destino){
		// origem e destino sao iguais
		if (origem.getPosicaoX() != destino.getPosicaoX() && origem.getPosicaoY() != origem.getPosicaoY()){
			return false;
		}
		
		// Se a origem tiver uma diferenca de valor 1 em x no destino, destino deve possuir o mesmo 
		// valor em y para ser permitido mover
		if (( origem.getPosicaoX() > destino.getPosicaoX() && 
				Math.abs(origem.getPosicaoX()-1) == destino.getPosicaoX() ) ||
				(destino.getPosicaoX() > origem.getPosicaoX() && 
						Math.abs(destino.getPosicaoX()-1) == origem.getPosicaoX() ))
		{			
			if ( origem.getPosicaoY() == destino.getPosicaoY()){
					return true;
			}
		}
		// Se a origem tiver uma diferenca de valor 1 em y no destino, destino deve possuir o mesmo 
		// valor em x para ser permitido mover
				
		else if(( origem.getPosicaoY() > destino.getPosicaoY() && 
				Math.abs(origem.getPosicaoY()-1) == destino.getPosicaoY() ) ||
				(destino.getPosicaoY() > origem.getPosicaoY() && 
						Math.abs(destino.getPosicaoY()-1) == origem.getPosicaoY() )){
			if(origem.getPosicaoX() == destino.getPosicaoY()){
				return true;
			}
		}
		return false;
	}
	/**
	 * Obtem o elemento dentro do Array.
	 * @param personagem
	 * @return Se nao encontrado volta null.
	 */
	public Personagem getElemento(Personagem personagem){
		Iterator<Personagem> iter = figurantes.iterator();
		while (iter.hasNext()) {
			Personagem elemento = iter.next();
			if (elemento == personagem) {
				return elemento;
			}
		}
		return null;
	}
	
/**
 * Move Personagem para casa desocupada.
 * @param personagem
 * @param casaDestino
 */
	public Boolean moverParaCasaDesocupada(Personagem personagem, Casa casaDestino) {
		Ocupante espacoDestino = casaOcupadaPor(casaDestino);
		if (espacoDestino.equals(Ocupante.Vazio)){
			Casa origem = personagem.getCasaPersonagem();
			esvaziarCasa(origem);			
			personagem.setCasaPersonagem(casaDestino);
			return true;
		}
        return false;
	}
	/**
	 * Acao do lobo devorar a ovelha do adversario.
	 * @param lobo
	 * @param ovelha
	 * @return Se o lobo conseguiu atacar a ovelha.
	 */
	public Boolean devorarOvelha(Lobo lobo, Ovelha ovelha){
		if (lobo.getNomeEspaco().equals(Ocupante.Lobo) && 
				ovelha.getNomeEspaco().equals(Ocupante.Ovelha)){
			//verificar se lobo esta proximo de ovelha
			if (diferencaEntreCasas(lobo.getCasaPersonagem(), ovelha.getCasaPersonagem())){
				Casa antigacasaDoLobo = lobo.getCasaPersonagem();
				lobo.comerOvelha();
				ovelha.sofrerAtaque();
				lobo.setCasaPersonagem(ovelha.getCasaPersonagem());				
				figurantes.remove(ovelha);
				figurantes.add(new Figurante(Ocupante.Vazio,antigacasaDoLobo));
				return true;
			}			
		}
		return false;
	}
	/**
	 * Retorna verdadeiro se Lobo conseguiu atacar cerca. Nao finalizou.
	 * E' necessario atualizar o estado do Lobo depois dessa funcao para retornar ao Estado Livre.
	 * @param lobo
	 * @param cerca
	 * @return
	 */
	
	public Boolean destruirCerca(Lobo lobo, Figurante cerca){
		if (lobo.getNomeEspaco().equals(Ocupante.Lobo) && 
				cerca.getNomeEspaco().equals(Ocupante.Cerca)){
			//verificar se lobo esta proximo da cerca
			if (diferencaEntreCasas(lobo.getCasaPersonagem(), cerca.getCasaPersonagem())){
				Casa antigacasaDoLobo = lobo.getCasaPersonagem();				
				lobo.destruirCerca();
				lobo.setCasaPersonagem(cerca.getCasaPersonagem());
				figurantes.remove(cerca);
				figurantes.add(new Figurante(Ocupante.Vazio,antigacasaDoLobo));
				return true;
			}			
		}
		return false;
	}
	/**
	 * Se o pastor estiver proximo a ovelha podera a alimentar.
	 * @param pastor
	 * @param ovelha
	 * @return
	 */
	public Boolean alimentarOvelha(Pastor pastor,Ovelha ovelha){
		if (pastor.getNomeEspaco().equals(Ocupante.Pastor) && 
				ovelha.getNomeEspaco().equals(Ocupante.Ovelha)){
			//verificar se lobo esta proximo da cerca
			if (diferencaEntreCasas(pastor.getCasaPersonagem(), ovelha.getCasaPersonagem())){								
				ovelha.alimentarOvelha();				
				return true;
			}			
		}
		return false;
	}
	/**
	 * Constroe uma cerca em um espaco proximo ao pastor.
	 * Atualiza no array de figurantes.
	 * @param pastor
	 * @param figurante
	 * @return
	 */
	public Boolean construirCerca(Pastor pastor, Figurante figurante){
		if (pastor.getNomeEspaco().equals(Ocupante.Pastor) && 
				figurante.getNomeEspaco().equals(Ocupante.Vazio)){
			//verificar se lobo esta proximo da cerca
			if (diferencaEntreCasas(pastor.getCasaPersonagem(), figurante.getCasaPersonagem())){
				figurantes.remove(figurante);
				figurante.setNomeEspaco(Ocupante.Cerca);
				figurantes.add(figurante);
				return true;
			}			
		}
		return false;
	}
	
	
	
	
	
	
	
	
	
}

