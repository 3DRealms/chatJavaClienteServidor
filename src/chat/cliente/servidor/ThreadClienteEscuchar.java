package chat.cliente.servidor;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class ThreadClienteEscuchar extends Thread{
	
private Socket cliente;
	
	public ThreadClienteEscuchar(Socket cliente){
		this.cliente = cliente;
	}
	
	@Override
	public void run(){
		while(true){
			
			try {
				DataInputStream lectura = new DataInputStream(
						cliente.getInputStream());
				System.out.println(lectura.readUTF());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
