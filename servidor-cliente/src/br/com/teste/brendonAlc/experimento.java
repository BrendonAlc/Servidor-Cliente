package br.com.teste.brendonAlc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class experimento {

	private volatile boolean estaRodando = false;

	
	@Test
	public static void main(String[] args) throws InterruptedException {
		
		experimento servidor = new experimento();
		servidor.rodar();
		servidor.alterandoAtributo();
		
	}
	
	private void rodar() {
		new Thread(new Runnable() {
			
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("Servidor começando, estaRodando = " + estaRodando);
				
				while (!estaRodando) {
					System.out.println("Servidor rodando, estaRodando = " + estaRodando);
				}
				
				while (estaRodando) {}
					System.out.println("Servidor terminando, estaRodando = " + estaRodando);
			}
		}).start();
	}
	
	private void alterandoAtributo() throws InterruptedException {
		Thread.sleep(5000);
		System.out.println("Alterando estaRodando = true");
		estaRodando = true;
		
		Thread.sleep(5000);
		System.out.println("Alterando estaRodando = false");
		estaRodando = false;
		
	}

}
