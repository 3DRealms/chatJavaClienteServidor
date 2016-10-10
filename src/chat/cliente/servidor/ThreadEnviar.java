package chat.cliente.servidor;

import java.io.IOException;
import java.util.List;

public class ThreadEnviar extends Thread{
	
	private List<SocketChat> clientes;
	String mensaje;
	String remitente;

	
	public ThreadEnviar(List<SocketChat> clientes, String mensaje, String remitente){
		this.clientes = clientes;		
		this.mensaje = mensaje;
		this.remitente = remitente;
	}
	
	@Override
	public void run(){

			
			
			for(SocketChat cliente : clientes)
			{
				
				try {
					cliente.enviarMensaje(mensaje, remitente);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
					clientes.remove(cliente);
					try {
						cliente.cerrar();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						//e1.printStackTrace();
					}
				}
				
			
			
		}
	}

}
