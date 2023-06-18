package br.com.teste.brendonAlc;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class TesteFila {

	public static void main(String[] args) throws InterruptedException {
		
		BlockingQueue<String> fila = new ArrayBlockingQueue<>(3);
		
		fila.put("a1");
		fila.put("a2");
		fila.put("a3");
		fila.put("a4");
		
		System.out.println(fila.take());
		System.out.println(fila.take());
		System.out.println(fila.take());
		System.out.println(fila.take());
		
		System.out.println(fila.size());
	}

}
