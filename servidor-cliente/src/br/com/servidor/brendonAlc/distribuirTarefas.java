package br.com.servidor.brendonAlc;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.management.RuntimeErrorException;

import br.com.servidorTarefas.brendonAlc.ComandoA2AcessandoBanco;
import br.com.servidorTarefas.brendonAlc.ComandoA2ChamaWS;
import br.com.servidorTarefas.brendonAlc.Comandos;
import br.com.servidorTarefas.brendonAlc.Comandos.ComandoA1;
import br.com.servidorTarefas.brendonAlc.ServidorTarefas;

public class distribuirTarefas extends Comandos implements Runnable{

	private Socket socket;
	private char[] comando;
	private ServidorTarefas servidor;
	private ExecutorService threadPool;
	
	public distribuirTarefas(ExecutorService threadPool, Socket socket, ServidorTarefas servidor) {
		this.socket = socket;
		this.servidor = servidor;
		this.threadPool = threadPool;
		
	}

	@Override
	public void run(Runnable c2WS) {
		
		try {
			System.out.println("Distribuindo as tarefas para o cliente " + socket);
			
			Scanner entradaCliente = new Scanner(socket.getInputStream());
			PrintStream saidaCliente = new PrintStream(socket.getOutputStream());
			
			while(entradaCliente.hasNextLine()) {
				String comando = entradaCliente.nextLine();
				System.out.println("Comando recebido " + comando);
				
				switch (comando) {
				case "a1": {
					saidaCliente.println("Confirmação do comando a1");
					ComandoA1 a1 = new ComandoA1(saidaCliente);
					this.threadPool.execute(a1);
					break;
					}
				case "a2": {
					saidaCliente.println("Confirmação do comando a2");
					ComandoA2ChamaWS a2WS = new ComandoA2ChamaWS(saidaCliente);
					ComandoA2AcessandoBanco c2Banco = new ComandoA2AcessandoBanco(saidaCliente);
					Future<?> FutureWS = this.threadPool.submit(c2WS);
					Future<?> Futurebanco = this.threadPool.submit(c2Banco);
					
					this.threadPool.submit(new JuntaResultadoFuture);
					
					break;
					}
				case "fim": {
					saidaCliente.println("Desligando servidor");
					servidor.parar();
					return;
				}
				default:{
					saidaCliente.println("Comando não encontrado");
					}
				}
			}
			
			System.out.println(comando);
			
			saidaCliente.close();
			entradaCliente.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}	

}
