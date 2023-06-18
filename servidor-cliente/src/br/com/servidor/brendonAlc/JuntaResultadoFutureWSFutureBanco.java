package br.com.servidor.brendonAlc;

import java.io.PrintStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class JuntaResultadoFutureWSFutureBanco implements Callable<Void> {

	private Future<?> futureWS;
	private Future<?> futureBanco;
	private PrintStream saidaCliente;

	public JuntaResultadoFutureWSFutureBanco(Future<?> futureWS2, Future<?> futureBanco, PrintStream saidaCliente) {
		this.futureWS = futureWS2;
		this.futureBanco = futureBanco;
		this.saidaCliente = saidaCliente;
	}

	@Override
	public Void call() {
		
		System.out.println("Aguardando resultados do future WS e Banco.");
		
		try {
			Object numeroMagico = this.futureWS.get(20, TimeUnit.SECONDS);
			Object numeroMagico2 = this.futureBanco.get(20, TimeUnit.SECONDS);
			
			this.saidaCliente.println("Resultado comando A2: " + numeroMagico + ", " + numeroMagico2);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {

			System.out.println("Timeout na execucao do comando A2");
			
			this.saidaCliente.println("Timeout na execucao do comando A2");
			this.futureWS.cancel(true);
			this.futureBanco.cancel(true);
		}
		
		System.out.println("Finalizou JuntaResultadoFutureWSFutureBanco");
		
		return null;
	}

}
