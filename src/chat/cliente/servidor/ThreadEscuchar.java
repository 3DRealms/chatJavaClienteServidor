package chat.cliente.servidor;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class ThreadEscuchar extends Thread{
	
	private List<Socket> clientes;
	private Socket cliente;
	
	public ThreadEscuchar(List<Socket> clientes, Socket cliente){
		this.clientes = clientes;
		this.cliente = cliente;
	}
	
	@Override
	public void run(){
		while(true){
			
			try {
				DataInputStream lectura = new DataInputStream(
						cliente.getInputStream());
				new ThreadEnviar(clientes, lectura.readUTF()).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
