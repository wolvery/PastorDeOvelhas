package com.example.pastordeovelhas;


public enum EstadoOvelha{
	Faminto(3),PoucaFome(2),Bem(1),Satisfeito(0);
	
	private int valorFome;
	EstadoOvelha(int valor){
		valorFome = valor;
	}
	public int getValorFome() {
		return valorFome;
	}
	public void setValorFome(int valorFome) {
		this.valorFome = valorFome;
	}
	
}
