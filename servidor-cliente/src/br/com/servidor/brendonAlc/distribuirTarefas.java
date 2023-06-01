package br.com.servidor.brendonAlc;

import java.net.Socket;
import java.util.Scanner;

import javax.management.RuntimeErrorException;

public class distribuirTarefas implements Runnable{

	private Socket socket;
	
	public distribuirTarefas(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		
		try {
			System.out.println("Distribuindo as tarefas para o cliente " + socket);
			Scanner entradaCliente = new Scanner(socket.getInputStream());
			
			while(entradaCliente.hasNextLine()) {
				String comando = entradaCliente.nextLine();
				System.out.println(comando);
			}
			
			entradaCliente.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}	

}
