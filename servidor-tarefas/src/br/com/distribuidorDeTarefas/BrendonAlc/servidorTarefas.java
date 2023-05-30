package br.com.distribuidorDeTarefas.BrendonAlc;

import java.net.ServerSocket;
import java.net.Socket;

public class servidorTarefas {

	public static void main(String[] args) throws Exception {

		System.out.println("Iniciando Servidor!");
		ServerSocket socket = new ServerSocket(12345);
		
		while (true) {
			Socket socket = servidor.accept();
			System.out.println("Aceitando novo cliente na porta " + socket.getPort());
		}

	}

}
