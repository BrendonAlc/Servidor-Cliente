package br.com.clienteTarefas.brendonAlc;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTarefas {

	public static void main(String[] args) throws IOException {
		Socket socket = new Socket("localhost", 12345);
		System.out.println("Conexão Estabelecida.");
		
		//Enviando comando para servidor
		PrintStream saida = new PrintStream(socket.getOutputStream());
		saida.println("A1");
		
		//aguarda o enter do usuário
		Scanner teclado = new Scanner(System.in);
		teclado.nextLine();
		
		saida.close();
		teclado.close();
		socket.close();


	}

}
