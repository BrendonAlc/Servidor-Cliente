package br.com.servidorTarefas.brendonAlc;

import java.util.concurrent.ThreadFactory;

public class FabricaDeThreads implements ThreadFactory {

//	private ThreadFactory defaulFactory;
	private static int numero = 1;
	
//	public FabricaDeThreads(ThreadFactory defaultFactory) {
//		this.defaulFactory = defaultFactory;
//	}
	
	@Override
	public Thread newThread(Runnable tarefa) {
		
		
		Thread thread = new Thread(tarefa, " Servidor Tarefas " + numero);
		numero++;
		
		thread.setUncaughtExceptionHandler(new TratadorDeExcecao());
//		thread.setDaemon(true); //thread de serviço
		return thread;
	}

}
