package br.com.servidor.brendonAlc;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
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
	private ServidorTarefas servidor;
	private ExecutorService threadPool;
	private BlockingQueue<String> filaComandos;
	
	public distribuirTarefas(ExecutorService threadPool, BlockingQueue<String> filaComandos, Socket socket, ServidorTarefas servidor) {
		this.threadPool = threadPool;
		this.filaComandos = filaComandos;
		this.socket = socket;
		this.servidor = servidor;
		
	}

	@Override
	public void run() {
		
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
					
					//criando os dois comandos
					ComandoA2ChamaWS a2WS = new ComandoA2ChamaWS(saidaCliente);
					ComandoA2AcessandoBanco c2Banco = new ComandoA2AcessandoBanco(saidaCliente);
					
					//passando os comando para o pool, resultado é um Future
					Future<String> futureWS = this.threadPool.submit(a2WS);
					Future<String> futureBanco = this.threadPool.submit(c2Banco);
					
					Callable<Void> juntaResultados = new JuntaResultadoFutureWSFutureBanco(futureWS, futureBanco, saidaCliente);
					this.threadPool.submit(juntaResultados);
					
					break;
					}
				case "a3": {
					this.filaComandos.put(comando);//Bloqueia
					saidaCliente.println("Comando a3 adicionado na fila");
					
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
			
			saidaCliente.close();
			entradaCliente.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
}
