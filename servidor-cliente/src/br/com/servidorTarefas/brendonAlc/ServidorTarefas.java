package br.com.servidorTarefas.brendonAlc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import br.com.servidor.brendonAlc.distribuirTarefas;

public class ServidorTarefas {
	
	private ServerSocket servidor;
	private ExecutorService threadPoll;
	private AtomicBoolean estaRodando;

	public ServidorTarefas() throws IOException {
		System.out.println("Iniciando Servidor!");
		this.servidor = new ServerSocket(12345);
		this.threadPoll =  Executors.newFixedThreadPool(2);
		this.estaRodando = new AtomicBoolean(true);
	}

	/*
	 * método só deve aceitar novos clientes enquanto estaRodando for true
	 */
	public void rodar() throws IOException{
		
		while (this.estaRodando.get()) {
			
			try {
				Socket socket = servidor.accept();
				System.out.println("Aceitando novo cliente na porta " + socket.getPort());
				
				distribuirTarefas distribuirTarefas = new distribuirTarefas(socket, this);
				this.threadPoll.execute(distribuirTarefas); //ThreadDePoll executando a tarefa
			
			} catch (SocketException e) {
				System.out.println("SocketException, está rodando?" + this.estaRodando);
			}
		}//fim while
	}//fim rodar
	
	
	/*
	 * encerrando pool de threads e serversocket
	 * atribuindo valor false para o atributo estaRodando
	 */
	public void parar() throws IOException {
		System.out.println("Parando servidor");
		this.estaRodando.set(false);;
		this.threadPoll.shutdown();
		this.servidor.close();
	}
	
	public static void main(String[] args) throws Exception {
		ServidorTarefas servidor = new ServidorTarefas();
		servidor.rodar();
	}

}
