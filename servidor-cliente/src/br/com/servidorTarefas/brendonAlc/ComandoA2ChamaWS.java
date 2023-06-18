package br.com.servidorTarefas.brendonAlc;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;

public class ComandoA2ChamaWS implements Callable<String>{

	private PrintStream saida;

	public ComandoA2ChamaWS(PrintStream saida) {
		this.saida = saida;
	}

	@Override
	public String call() throws Exception {
		System.out.println("Servidor recebeu comando a2 - WS");
		saida.println("processando comando a2 - WS");
		
		Thread.sleep(15000);
		
		int numero = new Random().nextInt(100) + 1;
		System.out.println("Servidor finalizou comando a2 - WS");
		return Integer.toString(numero);
	}

}
