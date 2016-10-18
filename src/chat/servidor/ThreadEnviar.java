package chat.servidor;

import java.io.IOException;
import java.util.List;

import chat.mensaje.Mensaje;

public class ThreadEnviar extends Thread{
	
	private Canal clientes;
	Mensaje men;
	String remitente;

	
	public ThreadEnviar(Canal clientes, Mensaje men){
		this.clientes = clientes;		
		this.men = men;
	}
	
	@Override
	public void run(){
			clientes.enviarMensaje(men);						
	}

}
