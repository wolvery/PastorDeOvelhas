package com.example.pastordeovelhas;

import java.util.ArrayList;

/**
 * Classe responsavel para cada jogador adversario.
 */
public class Jogador {
    private ArrayList<Personagem> personagens;
    private Lobo meuLobo;
    private ArrayList<Ovelha> grupoDeOvelhas;

    public Jogador(ArrayList<Personagem> personagens){
        this.personagens = personagens;
    }

    public ArrayList<Personagem> getPersonagens(){
        return personagens;
    }
    public void addPersonagem(Personagem personagem){
        if (personagem.getNomeEspaco().equals(Ocupante.Ovelha)){
            grupoDeOvelhas.add((Ovelha) personagem);
        }
        else if (personagem.getNomeEspaco().equals(Ocupante.Lobo)){
            meuLobo = (Lobo) personagem;
        }
        else {
            personagens.add(personagem);
        }
    }
    public void removePersonagem(Personagem personagem){
        if (grupoDeOvelhas.contains((Ovelha) personagem))
            grupoDeOvelhas.remove((Ovelha) personagem);
        else
            personagens.remove(personagem);
    }
    public ArrayList<Ovelha> getOvelhass(){
        return grupoDeOvelhas
                ;
    }
    public void addOvelha(Ovelha ovelha){
        grupoDeOvelhas.add(ovelha);
    }
    public void removeOvelha(Ovelha ovelha){
        grupoDeOvelhas.remove(ovelha);
    }

    /**
     * Remove ovelha caso esteja morta.
     * @param ovelha
     * @return
     */
    public Boolean atualizaEstadoOvelha(Ovelha ovelha){
        grupoDeOvelhas.remove(ovelha);
        ovelha.vidaAtualizaFome();
        ovelha.vidaOvelhaAtaque();
        if (ovelha.ovelhaViva()) {
            grupoDeOvelhas.add(ovelha);
            return true;
        }
        return false;
    }
    /**
     * Alimenta ovelha.
     * @param ovelha
     * @return
     */
    public Boolean alimentaOvelha(Ovelha ovelha){
        grupoDeOvelhas.remove(ovelha);
        ovelha.alimentarOvelha();
        if (ovelha.ovelhaViva()) {
            grupoDeOvelhas.add(ovelha);
            return true;
        }
        return false;
    }

    public Lobo getMeuLobo(){
        return meuLobo;
    }
    public void setMeuLobo(Lobo novo){
        this.meuLobo = novo;
    }


}
