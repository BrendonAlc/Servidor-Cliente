package br.com.servidorTarefas.brendonAlc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

import br.com.servidor.brendonAlc.distribuirTarefas;

public class ServidorTarefas extends Comandos {
	
	private ServerSocket servidor;
	private ExecutorService threadPool;
	private AtomicBoolean estaRodando;
	private BlockingQueue<String> filaComandos;

	public ServidorTarefas() throws IOException {
		System.out.println("Iniciando Servidor!");
		this.servidor = new ServerSocket(12345);
		
//		ThreadFactory defaultFactory = Executors.defaultThreadFactory();
//		this.threadPool =  Executors.newFixedThreadPool(4, new FabricaDeThreads()); //thread utilizada quando realizado teste com quantidade fixa
		this.threadPool = Executors.newCachedThreadPool(new FabricaDeThreads()); //Utilizando newCached para aumentar de acordo com o solicitado
		this.estaRodando = new AtomicBoolean(true);
		this.filaComandos = new ArrayBlockingQueue<>(2);
		iniciarConsumidores();
	}

	private void iniciarConsumidores() {
		int qtdConsumidores = 2;
		for (int i = 0; i < qtdConsumidores ; i++) {
			TarefaConsumir tarefa = new TarefaConsumir(filaComandos);
			this.threadPool.execute(tarefa);
		}
	}

	/*
	 * método só deve aceitar novos clientes enquanto estaRodando for true
	 */
	public void rodar() throws IOException{
		
		while (this.estaRodando.get()) {
			
			try {
				Socket socket = servidor.accept();
				System.out.println("Aceitando novo cliente na porta " + socket.getPort());
				
				//adicionar threadPool no contrutor para executar os comandos
				distribuirTarefas distribuirTarefas = new distribuirTarefas(threadPool, filaComandos, socket, this);
				
				this.threadPool.execute(distribuirTarefas); //ThreadDePoll executando a tarefa			
			} catch (SocketException e) {
				System.out.println("SocketException, está rodando?" + this.estaRodando);
			}
		}
	}	
	
	/*
	 * encerrando pool de threads e serversocket
	 * atribuindo valor false para o atributo estaRodando
	 */
	public void parar() throws IOException {
		this.estaRodando.set(false);;
		this.threadPool.shutdown();
		this.servidor.close();
		System.out.println("Parando servidor");
	}
	
	public static void main(String[] args) throws Exception {
		ServidorTarefas servidor = new ServidorTarefas();
		servidor.rodar();
	}

}
