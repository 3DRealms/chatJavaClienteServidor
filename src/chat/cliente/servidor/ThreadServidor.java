package chat.cliente.servidor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ThreadServidor extends Thread{
	
	private List<Socket> clientes = new ArrayList<Socket>();
	private int puerto;
	
	ThreadServidor(int puerto){
		this.puerto = puerto;
	}
	
	@Override
	public void run(){
		
		try{
			ServerSocket server = new ServerSocket(puerto);
			Socket cliente;
			while(true){
				cliente = server.accept();
				// para los objetos usamos ObjetOutputStream y tenemos que implementar la interfaz serializable
				DataOutputStream salida = new DataOutputStream(
						cliente.getOutputStream());
				salida.writeUTF("Conectado!");
				System.out.println("Servidor:Cliente Conectado!");
				clientes.add(cliente);
				new ThreadEscuchar(clientes,cliente).start();
				cliente = null;
			}
			
	
			}
			catch(IOException e){
				e.printStackTrace();
			}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		ThreadServidor escuchar = new ThreadServidor(1000);
		escuchar.start();

	}

}
