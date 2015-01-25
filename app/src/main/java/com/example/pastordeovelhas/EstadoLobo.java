package com.example.pastordeovelhas;

public enum EstadoLobo {
	
		Livre(1),DigerindoOvelha(0),DestruindoCerca(2);		
		private int estadoLobo;
		EstadoLobo(int valor){
			this.estadoLobo = valor;
		}
		
		
		
	
}
