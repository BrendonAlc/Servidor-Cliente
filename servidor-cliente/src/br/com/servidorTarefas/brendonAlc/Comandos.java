package br.com.servidorTarefas.brendonAlc;

import java.io.PrintStream;

public class Comandos {

	public class ComandoA1 implements Runnable {

		private PrintStream saida;
		
		public ComandoA1(PrintStream saida) {
			this.saida = saida;
		}
		
		@Override
		public void run() {
			System.out.println("Executando comando a1");
			
			try {
				Thread.sleep(20000);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			
			//mensagem enviada para o cliente
			saida.println("Comando a1 executado com sucesso!");
		}
	}
	
	public class ComandoA2 implements Runnable {
		
		private PrintStream saida;
		
		public ComandoA2(PrintStream saida) {
			this.saida = saida;
		}

		@Override
		public void run() {
			System.out.println("Executando comando a2");
			
			try {
				Thread.sleep(20000);
			} catch (Exception e) {
				throw new RuntimeException("Exceção no comando a2");
			}
			
			//mensagem enviada para o cliente
			saida.println("Comando a2 executado com sucesso!");
		}
		
	}
	
	
}
