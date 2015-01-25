package com.example.pastordeovelhas;

import java.util.ArrayList;

/**
 * Classe responsavel para cada jogador adversario.
 */
public class Jogador {
    private ArrayList<Personagem> personagens;

    public Jogador(ArrayList<Personagem> personagens){
        this.personagens = personagens;
    }

    public ArrayList<Personagem> getPersonagens(){
        return personagens;
    }
    public void addPersonagem(Personagem personagem){
        personagens.add(personagem);
    }
    public void removePersonagem(Personagem personagem){
        personagens.remove(personagem);
    }
}
