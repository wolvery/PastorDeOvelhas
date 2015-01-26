package com.example.pastordeovelhas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
	private int startLocation;
	private Dinamica dinamicaJogo;
	private Boolean jogadaValida;
	private Boolean gameOn;

	private int quantidadeJogadores;	
	private ArrayList<Personagem> elementosJogador;
	private Button[][] buttons;
    private Jogador meuJogador;
    private Jogador[] adversarios;
    private int setDeCores[] = {Color.RED, Color.BLACK, Color.BLUE, Color.GRAY,Color.GREEN};
    private int ids[][] = {{R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5,
            R.id.button6},{R.id.button7,R.id.button8,R.id.button9,R.id.button10,R.id.button11,
            R.id.button12},{R.id.button13,R.id.button14,R.id.button15,R.id.button16,R.id.button17,
            R.id.button18},{R.id.button19,R.id.button20,R.id.button21,R.id.button22,R.id.button23,
            R.id.button24},{R.id.button25,R.id.button26,R.id.button27,R.id.button28,R.id.button29,
            R.id.button30},{R.id.button31,R.id.button32,R.id.button33,R.id.button34,R.id.button35,
            R.id.button36}};
    private TextView texto;
    private Button buttonMover,buttonOvelha,buttonCerca;
	private ArrayList<Personagem> elementosAdversarios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dinamicaJogo = new Dinamica(36);
        texto = (TextView) findViewById(R.id.text);
        buttons = new Button[6][6];
        generatorButton();

    }

    private void generatorButton(){
        int i=0;
        int j=0;
        for (i=0;i<6;i++){
            for (j=0;j<6;j++){
                buttons[i][j]=(Button) findViewById(ids[i][j]);
                buttons[i][j].setBackgroundResource(R.drawable.vazio);
                buttons[i][j].setOnClickListener(new ButtonClickListener(i,j));
            }
        }
        buttonMover = (Button) findViewById(R.id.button37);
        buttonMover.setVisibility(View.GONE);
        buttonMover.setOnClickListener(new ButtonClickListener(-1,0));
        buttonOvelha = (Button) findViewById(R.id.button38);
        buttonOvelha.setVisibility(View.GONE);
        buttonOvelha.setOnClickListener(new ButtonClickListener(-1,1));
        buttonCerca = (Button) findViewById(R.id.button39);
        buttonCerca.setVisibility(View.GONE);
        buttonCerca.setOnClickListener(new ButtonClickListener(-1,2));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (item.getTitle()=="New Game"){

        }
        else if (item.getTitle()=="Exit"){
               MainActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * Avalia se o espaco eh do jogador.
     * @param casaAVerificar
     * @return
     */
    public Boolean casaPertenceAoJogador(Casa casaAVerificar) {
        Iterator<Personagem> iter = meuJogador.getPersonagens().iterator();
        while (iter.hasNext()) {
            Personagem elemento = iter.next();
            if (elemento.getCasaPersonagem() == casaAVerificar) {
                return true;
            }
        }

        return false;
    }
    /**
     * Avalia se o espaco eh de algum adversario.
     * @param casaAVerificar
     * @return
     */
    public Boolean casaPertenceAoAdversario(Casa casaAVerificar) {
        for (int i = 0; i<quantidadeJogadores;i++) {
            Iterator<Personagem> iter = adversarios[i].getPersonagens().iterator();
            while (iter.hasNext()) {
                Personagem elemento = iter.next();
                if (elemento.getCasaPersonagem() == casaAVerificar) {
                    return true;
                }
            }


        }
        return false;
    }
    private Boolean atualizarCasaPorOcupante(Casa casa, Ocupante ocupante){
        switch (ocupante){
            case Ovelha:
                buttons[casa.getPosicaoX()][casa.getPosicaoY()].setBackgroundResource(R.drawable.sheep);
                return dinamicaJogo.ocuparCasa(casa,ocupante);
            case Pastor:
                buttons[casa.getPosicaoX()][casa.getPosicaoY()].setBackgroundResource(R.drawable.pastor);
                return dinamicaJogo.ocuparCasa(casa,ocupante);
            case Lobo:
                buttons[casa.getPosicaoX()][casa.getPosicaoY()].setBackgroundResource(R.drawable.wall);
                return dinamicaJogo.ocuparCasa(casa,ocupante);
            case Cerca:
                buttons[casa.getPosicaoX()][casa.getPosicaoY()].setBackgroundResource(R.drawable.sheep);
                return dinamicaJogo.ocuparCasa(casa,ocupante);
            case Vazio:
            default:
                buttons[casa.getPosicaoX()][casa.getPosicaoY()].setBackgroundResource(R.drawable.vazio);
                dinamicaJogo.esvaziarCasa(casa);
                return true;
        }
    }
    private void carregarPersonagemDoAdversario(Personagem personagem, int cor){
        int x,y;
        x = personagem.getCasaPersonagem().getPosicaoX();
        y = personagem.getCasaPersonagem().getPosicaoY();
        buttons[x][y].setBackgroundColor(setDeCores[cor]);
        atualizarCasaPorOcupante(new Casa(x,y),personagem.getNomeEspaco());

    }

    private void habilitarMovimentoPersonagem(){

    }

    //Atualiza o jogo.
    private void loadGame(){
        //Quantidade de jogadores no jogo deve ser fornecida pelo servidor
        quantidadeJogadores = 1;
        // Disposicao de cada jogador na tela deve ser fornecida pelo servidor
        adversarios = new Jogador[quantidadeJogadores];
        for(int i = 0; i< quantidadeJogadores; i++){
            ArrayList<Personagem> personagensDoJogadorI = adversarios[i].getPersonagens();
            Iterator<Personagem> iterator = personagensDoJogadorI.iterator();
            while(iterator.hasNext()){
                Personagem personagem = iterator.next();
                carregarPersonagemDoAdversario(personagem, i);
            }
        }
    }



    private void startNewGame(){
        gameOn = true;
        //->OBTER POSICAO DAS PECAS ALOCAR SEMPRE A ESQUERDA DA CASA
        Casa casaAlocarEsquerda = new Casa(0,0);
        Pastor meuPastor = new Pastor(Ocupante.Pastor,casaAlocarEsquerda);
        casaAlocarEsquerda.setPosicaoY(casaAlocarEsquerda.getPosicaoY()+1);
        Lobo meuLobo = new Lobo(Ocupante.Lobo,casaAlocarEsquerda);
        meuJogador = new Jogador(new ArrayList<Personagem>());
        meuJogador.addPersonagem(meuPastor);
        meuJogador.addPersonagem(meuLobo);
        // Carregar posicao dos elementos no jogo
        atualizarCasaPorOcupante(meuPastor.getCasaPersonagem(),meuPastor.getNomeEspaco());
        atualizarCasaPorOcupante(meuLobo.getCasaPersonagem(),meuLobo.getNomeEspaco());

    }

    private class ButtonClickListenerAcao implements View.OnClickListener {

        @Override
        public void onClick(View view) {

        }
    }
    private class ButtonClickListener implements View.OnClickListener{
        private int x,y;
        private Casa casa;
        public ButtonClickListener(int x, int y){
            if (x>=0) {
                this.x = x;
                this.y = y;
                casa = new Casa(x, y);
            }else{
             this.x = x;
             this.y = y;
            }

        }

        @Override
        public void onClick(View view) {
            if (gameOn){
                if (casaPertenceAoJogador(casa) && x>0 ){
                    //Precisamos saber qual personagem eh
                    switch (dinamicaJogo.casaOcupadaPor(casa)) {
                        case Pastor:
                            texto.setText("ACAO:");
                            buttonMover.setVisibility(View.VISIBLE);
                            buttonMover.setText("Mover");
                            buttonOvelha.setVisibility(View.VISIBLE);
                            buttonOvelha.setText("Criar ovelha");
                            buttonCerca.setVisibility(View.VISIBLE);
                            buttonCerca.setText("Cercar");
                            break;
                        case Lobo:
                            texto.setText("ACAO:");
                            buttonMover.setVisibility(View.VISIBLE);
                            buttonMover.setText("Mover");
                            buttonOvelha.setVisibility(View.VISIBLE);
                            buttonOvelha.setText("Comer ovelha");
                            buttonCerca.setVisibility(View.VISIBLE);
                            buttonCerca.setText("Destruir");
                            break;
                    }
                }
                else if (casaPertenceAoJogador(casa) && x<0 ){
                    switch(y){
                        case 0:
                            habilitarMovimentoPersonagem();

                            break;
                        case -1:

                            break;
                        case -2:

                            break;
                    }
                }
            }
        }
    }
}
