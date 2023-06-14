package br.com.teste.brendonAlc;

import br.com.teste.brendonAlc.experimento;
import br.com.teste.brendonAlc.ServidorDeTeste;


public class TarefaPararServidor implements Runnable {

	private ServidorDeTeste servidor;
	
	//recebendo o servidor como parametro
	public TarefaPararServidor(ServidorDeTeste servidor) {
		this.servidor = servidor;
	}
	
	public void run() {
		//chamando o método estaRodando()
		System.out.println("Servidor começando, estaRodando = " + this.servidor.estaRodando());
		
		while (!this.servidor.estaRodando()) {
		}
		
		System.out.println("Servidor rodando, estaRodando = " + this.servidor.estaRodando());
		
		while (!this.servidor.estaRodando()) {
		}
		
		System.out.println("Servidor terminando , estaRodando = " + this.servidor.estaRodando());
	}
	
}
