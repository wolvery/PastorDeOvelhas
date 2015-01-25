package com.example.pastordeovelhas;

/**
 * Classe para a personagem do Lobo. Nessa classe estao todos os estados
 * possiveis para o lobo, as demais acoes ficam na classe dinamica.
 * 
 * @author wolvery
 * 
 */

public class Lobo extends Personagem {
	// Estado do Lobo: Se ele esta comendo uma ovelha ou nao.
	private EstadoLobo estado;
	// Status de espera ocupado ovelha
	private int ocupado;

	/**
	 * Cria o lobo, seta o espaco como Lobo e na casa gerada, com seu estado
	 * Livre, pronto para alimentar.
	 * 
	 * @param espaco
	 * @param casaGerada
	 */
	public Lobo(Ocupante espaco, Casa casaGerada) {
		super(espaco, casaGerada);
		estado = EstadoLobo.Livre;
		ocupado = 0;
	}

	/**
	 * Obtem o estado do Lobo.
	 * 
	 * @return
	 */

	public EstadoLobo getEstado() {
		return estado;
	}

	/**
	 * Altera o estado lobo caso comece a comer a ovelha ou esteja livre.
	 * 
	 * @param estado
	 */
	public void setEstado(EstadoLobo estado) {
		this.estado = estado;
	}

	/**
	 * Altera estado do lobo para comendo Ovelha.
	 * 
	 * @param nomeEstado
	 * @param nomeOvelha
	 */
	public void comerOvelha() {
		if (estado.equals(EstadoLobo.Livre)) {
			this.setEstado(EstadoLobo.DigerindoOvelha);
			ocupado = 1;
		}
	}

	/**
	 * Altera o estado do Lobo para destruindo cerca.
	 */
	public void destruirCerca() {
		if (estado.equals(EstadoLobo.Livre)) {
			this.setEstado(EstadoLobo.DestruindoCerca);
			ocupado = 2;
		}
	}

	/**
	 * Atualiza estado do lobo.
	 */
	public void liberarLobo() {
		/**
		 * Delay de acordo com o ocupado.
		 */
		switch (this.estado) {
		/**
		 * Caso Lobo esta comendo ovelha 1 delay.
		 */
		case DigerindoOvelha:
			if (ocupado == 1) {
				ocupado = 0;
			} else
				estado = EstadoLobo.Livre;
			break;
			/**
			 * Caso Lobo esta destruindo a cerca 2 delays.
			 */
		case DestruindoCerca:
			if (ocupado == 2) {
				ocupado = 1;
			} else if (ocupado == 1) {
				ocupado = 0;
			} else
				estado = EstadoLobo.Livre;
			break;
		default:
			break;
		}
	}

}
