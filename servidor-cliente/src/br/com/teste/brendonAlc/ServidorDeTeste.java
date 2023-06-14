package br.com.teste.brendonAlc;

/**
 * 
 * @author balcantara
 * Utilizando método synchronized para teste da thread estaRodando
 */
public class ServidorDeTeste {

	private boolean estaRodando = false;
	
	public static void main(String[] args) throws InterruptedException {
		ServidorDeTeste servidor = new ServidorDeTeste();
		servidor.rodar();
		servidor.alterandoAtributo();
		
	}
	
	
	private void rodar() {
		Thread thread = new Thread(new TarefaPararServidor(this));
		thread.start();
		
	}

	/*
	 * 3 métodos, todos sincronizados para encapsular o acesso aos atributos
	 */

	public synchronized boolean estaRodando() {
		return this.estaRodando;
	}
	
	public synchronized void parar() {
		this.estaRodando = false;
	}
	
	public synchronized void ligar() {
		this.estaRodando = true;
	}
	
	private void alterandoAtributo() throws InterruptedException {
		Thread.sleep(1000);
		System.out.println("Main alterando estaRodando = true");
		this.ligar();;
		
		Thread.sleep(5000);
		System.out.println("Main alterando estaRodando = false");
		this.parar();;
		
	}
	
}
