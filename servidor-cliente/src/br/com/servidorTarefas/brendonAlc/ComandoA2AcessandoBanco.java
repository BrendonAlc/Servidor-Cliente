package br.com.servidorTarefas.brendonAlc;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;

public class ComandoA2AcessandoBanco implements Callable<String> {

	private PrintStream saida;

	public ComandoA2AcessandoBanco(PrintStream saida) {
		this.saida = saida;
	}

	@Override
	public String call() throws Exception {
		System.out.println("Servidor recebeu comando a2 - Banco");		
		saida.println("Processando comando a2 - Banco");
		
		Thread.sleep(15000);
		
		int numero = new Random().nextInt(100) + 1;		
		return Integer.toString(numero);
	}

}
