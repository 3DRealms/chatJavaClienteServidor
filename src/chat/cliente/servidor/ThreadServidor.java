package chat.cliente.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThreadServidor extends Thread{
	
	private Map <String, List<SocketChat>> canales = new HashMap<String, List<SocketChat>>();
	private int puerto;
	
	ThreadServidor(int puerto){
		this.puerto = puerto;
		
		canales.put("CANAL 1", new ArrayList<SocketChat>());
		canales.put("CANAL 2", new ArrayList<SocketChat>());
		canales.put("CANAL 3", new ArrayList<SocketChat>());
	}
	
	@Override
	public void run(){
		
		try{
			ServerSocket server = new ServerSocket(puerto);
			SocketChat cliente;
			while(true){
				cliente = new SocketChat(server.accept());
				//cliente.enviarMensajeServidor("Conectado!");
				System.out.println("Servidor:Cliente Conectado!");
				//clientes.add(cliente);
				new ThreadEscuchar(canales,cliente).start();
				cliente = null;
			}
			
	
			}
			catch(IOException e){
				e.printStackTrace();
			}

		
	}
	
	public static void main(String[] args) {
		ThreadServidor escuchar = new ThreadServidor(1000);
		escuchar.start();

	}

}
