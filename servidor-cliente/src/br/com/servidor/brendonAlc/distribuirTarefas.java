package br.com.servidor.brendonAlc;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import javax.management.RuntimeErrorException;

import br.com.servidorTarefas.brendonAlc.ServidorTarefas;

public class distribuirTarefas implements Runnable{

	private Socket socket;
	private char[] comando;
	private ServidorTarefas servidor;
	
	public distribuirTarefas(Socket socket, ServidorTarefas servidor) {
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
					break;
					}
				case "a2": {
					saidaCliente.println("Confirmação do comando a2");
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
