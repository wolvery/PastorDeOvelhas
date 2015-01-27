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
    private Casa ultimaCasaPressionada,casaAcao;

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
    public void alteraButao(int x,int y){
        buttons[x][y].setBackgroundColor(Color.DKGRAY);
        buttons[x][y].setOnClickListener(new ButtonMoveListener(x,y));
    }

    public void desalteraButao(int x,int y){
        buttons[x][y].setBackgroundColor(Color.TRANSPARENT);
        buttons[x][y].setOnClickListener(new ButtonClickListener(x,y));
    }
    public void alteraButaoOvelha(int x,int y){
        buttons[x][y].setBackgroundColor(Color.DKGRAY);
        buttons[x][y].setOnClickListener(new ButtonOvelhaCercaListener(x,y));
    }

    public void desalteraButaoOvelha(int x,int y){
        buttons[x][y].setBackgroundColor(Color.TRANSPARENT);
        buttons[x][y].setOnClickListener(new ButtonClickListener(x,y));
    }
    public void alteraButaoCerca(int x,int y){
        buttons[x][y].setBackgroundColor(Color.DKGRAY);
        buttons[x][y].setOnClickListener(new ButtonOvelhaCercaListener(x,y));
    }

    public void desalteraButaoDaCerca(int x,int y){
        buttons[x][y].setBackgroundColor(Color.TRANSPARENT);
        buttons[x][y].setOnClickListener(new ButtonClickListener(x,y));
    }

    public void avaliaCasaAoRedor(Casa casa){
        int x = casa.getPosicaoX();
        int y = casa.getPosicaoY();
        //Casa de cima vazia
        if (x+1 <6){
            if (dinamicaJogo.espacoDisponivel(new Casa(x+1,y))){
                alteraButao(x+1,y);
            }
        }
        if (x-1 >=0){
            if (dinamicaJogo.espacoDisponivel(new Casa(x-1,y))){
                alteraButao(x-1,y);
            }
        }
        if (y+1 <6){
            if (dinamicaJogo.espacoDisponivel(new Casa(x,y+1))){
                alteraButao(x,y+1);
            }
        }
        if (y-1 >= 0){
            if (dinamicaJogo.espacoDisponivel(new Casa(x,y-1))){
                alteraButao(x,y-1);
            }
        }
    }
    public void avaliaOvelhaAoRedor(Casa casa){
        int x = casa.getPosicaoX();
        int y = casa.getPosicaoY();
        //Casa de cima vazia
        if (x+1 < 6){
            if (dinamicaJogo.casaOcupadaPor(new Casa(x+1,y)).equals(Ocupante.Ovelha)){
                alteraButaoOvelha(x + 1, y);
            }
        }
        if (x-1 >= 0){
            if (dinamicaJogo.casaOcupadaPor(new Casa(x-1,y)).equals(Ocupante.Ovelha)){
                alteraButaoOvelha(x - 1, y);
            }
        }
        if (y+1 < 6){
            if (dinamicaJogo.casaOcupadaPor(new Casa(x,y+1)).equals(Ocupante.Ovelha)){
                alteraButaoOvelha(x, y + 1);
            }
        }
        if (y-1 >= 0){
            if (dinamicaJogo.casaOcupadaPor(new Casa(x,y-1)).equals(Ocupante.Ovelha)){
                alteraButaoOvelha(x, y - 1);
            }
        }
    }
    public void avaliaCercaAoRedor(Casa casa){
        int x = casa.getPosicaoX();
        int y = casa.getPosicaoY();
        //Casa de cima vazia
        if (x+1 <6){
            if (dinamicaJogo.casaOcupadaPor(new Casa(x+1,y)).equals(Ocupante.Cerca)){
                alteraButaoCerca(x + 1, y);
            }
        }
        if (x-1 >=0){
            if (dinamicaJogo.casaOcupadaPor(new Casa(x-1,y)).equals(Ocupante.Cerca)){
                alteraButaoCerca(x - 1, y);
            }
        }
        if (y+1 <6){
            if (dinamicaJogo.casaOcupadaPor(new Casa(x,y+1)).equals(Ocupante.Cerca)){
                alteraButaoCerca(x, y + 1);
            }
        }
        if (y-1 >= 0){
            if (dinamicaJogo.casaOcupadaPor(new Casa(x,y-1)).equals(Ocupante.Cerca)){
                alteraButaoCerca(x, y - 1);
            }
        }
    }
    public void desativaOvelhaAoRedor(Casa casa){
        int x = casa.getPosicaoX();
        int y = casa.getPosicaoY();
        //Casa de cima vazia
        if (x+1 < 6){
            if (dinamicaJogo.casaOcupadaPor(new Casa(x+1,y)).equals(Ocupante.Ovelha)){
                desalteraButaoOvelha(x + 1, y);
            }
        }
        if (x-1 >= 0){
            if (dinamicaJogo.casaOcupadaPor(new Casa(x-1,y)).equals(Ocupante.Ovelha)){
                desalteraButaoOvelha(x - 1, y);
            }
        }
        if (y+1 < 6){
            if (dinamicaJogo.casaOcupadaPor(new Casa(x,y+1)).equals(Ocupante.Ovelha)){
                desalteraButaoOvelha(x, y + 1);
            }
        }
        if (y-1 >= 0){
            if (dinamicaJogo.casaOcupadaPor(new Casa(x,y-1)).equals(Ocupante.Ovelha)){
                desalteraButaoOvelha(x, y - 1);
            }
        }
    }
    public void desativaCercaAoRedor(Casa casa){
        int x = casa.getPosicaoX();
        int y = casa.getPosicaoY();
        //Casa de cima vazia
        if (x+1 <6){
            if (dinamicaJogo.casaOcupadaPor(new Casa(x+1,y)).equals(Ocupante.Cerca)){
                desalteraButaoDaCerca(x + 1, y);
            }
        }
        if (x-1 >=0){
            if (dinamicaJogo.casaOcupadaPor(new Casa(x-1,y)).equals(Ocupante.Cerca)){
                desalteraButaoDaCerca(x - 1, y);
            }
        }
        if (y+1 <6){
            if (dinamicaJogo.casaOcupadaPor(new Casa(x,y+1)).equals(Ocupante.Cerca)){
                desalteraButaoDaCerca(x, y + 1);
            }
        }
        if (y-1 >= 0){
            if (dinamicaJogo.casaOcupadaPor(new Casa(x,y-1)).equals(Ocupante.Cerca)){
                desalteraButaoDaCerca(x, y - 1);
            }
        }
    }

    public void desativarCasasDisponiveis(Casa casa){
        int x = casa.getPosicaoX();
        int y = casa.getPosicaoY();
        //Casa de cima vazia
        if (x+1 <6){
            if (dinamicaJogo.espacoDisponivel(new Casa(x+1,y))){
                desalteraButao(x + 1, y);
            }
        }
        if (x-1 >=0){
            if (dinamicaJogo.espacoDisponivel(new Casa(x-1,y))){
                desalteraButao(x - 1, y);
            }
        }
        if (y+1 <6){
            if (dinamicaJogo.espacoDisponivel(new Casa(x,y+1))){
                desalteraButao(x, y + 1);
            }
        }
        if (y-1 >= 0){
            if (dinamicaJogo.espacoDisponivel(new Casa(x,y-1))){
                desalteraButao(x, y - 1);
            }
        }
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


    /**
     * Servidor deve avaliar acao.
     */
    private void habilitarMovimentoPersonagem(){
        Personagem personagem = dinamicaJogo.casaOcupadaPorElemento(ultimaCasaPressionada);
        if (dinamicaJogo.moverParaCasaDesocupada(personagem,casaAcao)){
            meuJogador.removePersonagem(personagem);
            personagem.setCasaPersonagem(casaAcao);
            meuJogador.addPersonagem(personagem);
        }
    }

    private void criarPersonagemNaCasaAcao(Personagem personagem){
        if (atualizarCasaPorOcupante(casaAcao,personagem.getNomeEspaco()))
            meuJogador.addPersonagem(personagem);
    }

    private void criarOvelha(){
        criarPersonagemNaCasaAcao(new Ovelha(Ocupante.Ovelha,casaAcao));
    }
    private void criarCerca(){
        criarPersonagemNaCasaAcao(new Figurante(Ocupante.Cerca,casaAcao));
    }

    private void atualizaEstadoOvelha(Casa casa){
        if (dinamicaJogo.casaOcupadaPor(casa).equals(Ocupante.Ovelha)) {
            Ovelha ovelha = (Ovelha) dinamicaJogo.casaOcupadaPorElemento(casaAcao);
            meuJogador.atualizaEstadoOvelha(ovelha);
        }

    }

    private void alimentarOvelha(){
        Ovelha ovelha = (Ovelha) dinamicaJogo.casaOcupadaPorElemento(casaAcao);
        ovelha.alimentarOvelha();
        meuJogador.alimentaOvelha(ovelha);
    }

    private void comerOvelha(){
        Ovelha ovelha = (Ovelha) dinamicaJogo.casaOcupadaPorElemento(casaAcao);
        Lobo loboo =  meuJogador.getMeuLobo();
        if (dinamicaJogo.devorarOvelha(loboo,ovelha)) {
            loboo.setCasaPersonagem(casaAcao);
            loboo.comerOvelha();
            meuJogador.setMeuLobo(loboo);
        }
        // servidor tem que segurar o lobo
    }

    private void destruirCerca(){
        Figurante cerca = (Figurante) dinamicaJogo.casaOcupadaPorElemento(casaAcao);
        Lobo lobo = (Lobo) dinamicaJogo.casaOcupadaPorElemento(ultimaCasaPressionada);
        if (dinamicaJogo.destruirCerca(lobo,cerca)){
            lobo.destruirCerca();
            lobo.setCasaPersonagem(casaAcao);
            meuJogador.setMeuLobo(lobo);
        }
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
    private class ButtonOvelhaCercaListener implements View.OnClickListener{
        private int x,y;
        private Casa casa;
        public ButtonOvelhaCercaListener(int x, int y){
            casa = ultimaCasaPressionada;
            if (x>=0) {
                this.x = x;
                this.y = y;
                casaAcao = new Casa(x,y);
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
                            if (dinamicaJogo.casaOcupadaPor(casaAcao).equals(Ocupante.Ovelha)) {
                                texto.setText("ACAO:");
                                buttonMover.setVisibility(View.VISIBLE);
                                buttonMover.setText("Alimentar Ovelha");
                                buttonMover.setOnClickListener(new ButtonOvelhaCercaListener(-1, 0));
                            }
                            break;
                        case Lobo:
                            texto.setText("ACAO:");
                            if (dinamicaJogo.casaOcupadaPor(casaAcao).equals(Ocupante.Ovelha)) {
                                buttonMover.setVisibility(View.VISIBLE);
                                buttonMover.setText("Comer Ovelha");
                                buttonMover.setOnClickListener(new ButtonOvelhaCercaListener(-1, 1));
                            }else if(dinamicaJogo.casaOcupadaPor(casaAcao).equals(Ocupante.Cerca)) {
                                buttonMover.setVisibility(View.VISIBLE);
                                buttonMover.setText("Destruir");
                                buttonMover.setOnClickListener(new ButtonOvelhaCercaListener(-1, 2));
                            }
                            break;
                    }
                }
                else if (casaPertenceAoJogador(casa) && x<0 ){
                    desativarCasasDisponiveis(ultimaCasaPressionada);
                    desativaCercaAoRedor(ultimaCasaPressionada);
                    desativaOvelhaAoRedor(ultimaCasaPressionada);
                    switch(y){
                        case 0:
                            alimentarOvelha();
                            break;
                        case 1:
                            if (dinamicaJogo.casaOcupadaPor(ultimaCasaPressionada).equals(Ocupante.Lobo)){
                                comerOvelha();
                            }
                            break;
                        case 2:
                            if (dinamicaJogo.casaOcupadaPor(ultimaCasaPressionada).equals(Ocupante.Lobo)){
                                destruirCerca();
                            }
                            break;
                    }
                }
            }
        }
    }
    private class ButtonMoveListener implements View.OnClickListener{
        private int x,y;
        private Casa casa;
        public ButtonMoveListener(int x, int y){
            casa = ultimaCasaPressionada;
            if (x > 0) {
                this.x = x;
                this.y = y;
                casaAcao = new Casa(x,y);
            }else if (x < 0){
                this.x = x;
                this.y = y;
            }
        }

        @Override
        public void onClick(View view) {
            if (gameOn){
                if (casaPertenceAoJogador(casa) && x > 0 ){
                    //Precisamos saber qual personagem eh
                    switch (dinamicaJogo.casaOcupadaPor(casa)) {
                        case Pastor:
                            texto.setText("ACAO:");
                            buttonMover.setVisibility(View.VISIBLE);
                            buttonMover.setOnClickListener(new ButtonMoveListener(-1, 0));
                            buttonMover.setText("Mover");
                            buttonOvelha.setVisibility(View.VISIBLE);
                            buttonOvelha.setOnClickListener(new ButtonMoveListener(-1, 1));
                            buttonOvelha.setText("Criar ovelha");
                            buttonCerca.setVisibility(View.VISIBLE);
                            buttonCerca.setOnClickListener(new ButtonMoveListener(-1, 2));
                            buttonCerca.setText("Cercar");
                            break;
                        case Lobo:
                            ultimaCasaPressionada = this.casa;
                            texto.setText("ACAO:");
                            buttonMover.setVisibility(View.VISIBLE);
                            buttonMover.setText("Mover");
                            buttonMover.setOnClickListener(new ButtonMoveListener(-1,0));
                            /*
                            buttonOvelha.setVisibility(View.VISIBLE);
                            buttonOvelha.setText("Comer ovelha");
                            buttonCerca.setVisibility(View.VISIBLE);
                            buttonCerca.setText("Destruir");
                            */
                            break;
                    }
                }
                else if (casaPertenceAoJogador(casa) && x < 0 ){
                    desativarCasasDisponiveis(ultimaCasaPressionada);
                    desativaCercaAoRedor(ultimaCasaPressionada);
                    desativaOvelhaAoRedor(ultimaCasaPressionada);
                    switch(y){
                        case 0:
                            habilitarMovimentoPersonagem();
                            break;
                        case 1:
                            if (dinamicaJogo.casaOcupadaPor(ultimaCasaPressionada).equals(Ocupante.Pastor)){
                                criarOvelha();
                            }
                            break;
                        case 2:
                            if (dinamicaJogo.casaOcupadaPor(ultimaCasaPressionada).equals(Ocupante.Pastor)){
                                criarCerca();
                            }
                            break;
                    }
                }
            }
        }
    }
    private class ButtonClickListener implements View.OnClickListener{
        private int x,y;
        private Casa casa;
        public ButtonClickListener(int x, int y){
            this.x = x;
            this.y = y;
            casa = new Casa(x, y);
        }

        @Override
        public void onClick(View view) {
            if (gameOn){
                if (casaPertenceAoJogador(casa) ){
                    //Precisamos saber qual personagem eh
                    switch (dinamicaJogo.casaOcupadaPor(casa)) {
                        case Pastor:
                        case Lobo:
                            ultimaCasaPressionada = this.casa;
                            avaliaCasaAoRedor(this.casa);
                            avaliaOvelhaAoRedor(this.casa);
                            avaliaCercaAoRedor(this.casa);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }
}
