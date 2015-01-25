package com.example.pastordeovelhas;

/**
 * Classe responsavel pela personagem Ovelha.
 * 
 * Nessa classe estao todos os estados possiveis para a ovelha, as demais acoes
 * ficam na classe dinamica.
 * 
 * @author wolvery
 * 
 */

public class Ovelha extends Personagem {
	// Estado da Ovelha em relacao a refeicao.
	private EstadoOvelha estado;

	private enum Vida {
		Morto(2), Machucado(1), Vivo(0);

		private int valorVida;

		Vida(int valor) {
			valorVida = valor;
		}

		public int getValorVida() {
			return valorVida;
		}
	}

	private Vida situacaoVida;

	public Ovelha(Ocupante nomeEspaco, Casa casaPersonagem) {
		super(nomeEspaco, casaPersonagem);
		// TODO Auto-generated constructor stub
		estado = EstadoOvelha.Satisfeito;
		situacaoVida = Vida.Vivo;
	}

	public int getNomeEstado() {
		return estado.getValorFome();
	}

	/**
	 * @param nomeEstado
	 *            the nomeEstado to set
	 */
	public void setNomeEstado(EstadoOvelha nomeEstado) {
		this.estado = nomeEstado;
	}

	public int getEstadoVida() {
		return situacaoVida.valorVida;
	}

	public void setEstadoVida(Vida situacaoVida) {
		this.situacaoVida = situacaoVida;
	}

	// Classe vai para o controle do JOGO
	// Estado da ovelha, se ela esta' mais que faminta, morre e e' retirada.
	public void vidaAtualizaFome() {

		if (estado == EstadoOvelha.Faminto) {
			this.setNomeEspaco(Ocupante.Vazio);
		} else {
			if (estado == EstadoOvelha.PoucaFome) {
				setNomeEstado(estado.Faminto);
			} else {
				setNomeEstado(estado.PoucaFome);
			}
		}

	}

	// Estado quando o pastor alimenta a ovelha ela tera seu estado alterado
	public void alimentarOvelha() {
		if (estado == EstadoOvelha.Faminto) {
			estado = EstadoOvelha.PoucaFome;
		} else {
			if (estado == EstadoOvelha.PoucaFome) {
				estado = EstadoOvelha.Satisfeito;
			}
		}
	}

	// Estado da ovelha, quando ela e' atacada.
	/**
	 * Duas chamadas mata a ovelha.
	 */
	public void vidaOvelhaAtaque() {
		if (situacaoVida == Vida.Machucado) {
			situacaoVida = Vida.Morto;
		}
	}

	/**
	 * Ovelha foi atacada.
	 */
	public void atacada() {
		if (situacaoVida.equals(Vida.Vivo)) {
			situacaoVida = Vida.Machucado;
		}
	}

}
