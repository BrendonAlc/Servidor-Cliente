package br.com.servidorTarefas.brendonAlc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.servidor.brendonAlc.distribuirTarefas;

public class servidorTarefas {

	public static void main(String[] args) throws IOException {
		System.out.println("Iniciando Servidor!");
		ServerSocket socket = new ServerSocket(12345);
		
		ExecutorService threadDePoll =  Executors.newFixedThreadPool(2);
		
		while (true) {
			ServerSocket servidor = socket;
			Socket socket1 = servidor.accept();
			System.out.println("Aceitando novo cliente na porta " + socket1.getPort());
			
			distribuirTarefas distribuirTarefas = new distribuirTarefas(socket1);
			threadDePoll.execute(distribuirTarefas); //ThreadDePoll executando a tarefa
		}
	}

}
