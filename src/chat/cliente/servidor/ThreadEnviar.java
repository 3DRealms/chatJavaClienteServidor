package chat.cliente.servidor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class ThreadEnviar extends Thread{
	
	private List<Socket> clientes;
	String mensaje;

	
	public ThreadEnviar(List<Socket> clientes, String mensaje){
		this.clientes = clientes;		
		this.mensaje = mensaje;
	}
	
	@Override
	public void run(){

			
			
			for(Socket cliente : clientes)
			{
				DataOutputStream salida;
				try {
					salida = new DataOutputStream(
							cliente.getOutputStream());
					salida.writeUTF(mensaje);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
			
		}
	}

}
