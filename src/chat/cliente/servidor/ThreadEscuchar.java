package chat.cliente.servidor;

import java.io.IOException;

import java.util.List;
import java.util.Map;

public class ThreadEscuchar extends Thread{
	
	private Map <String, List<SocketChat>> canales;
	private SocketChat cliente;
	
	public ThreadEscuchar(Map <String, List<SocketChat>> canales, SocketChat cliente){
		this.canales = canales;
		this.cliente = cliente;
	}
	
	@Override
	public void run(){
		
		
		intro();
		
	}
	
	private void intro(){
		try {
			cliente.enviarMensajeServidor("Hola, por favor diganos su nombre de usuario");
			cliente.setUsuario(cliente.pedirMensaje());
			cliente.enviarMensajeServidor("Bienvenido "+cliente.getUsuario()+ ", ¿a que canal desea unirse?");
			listarCanales();
			boolean acerto=false;
			do{
				String canalElejido = cliente.pedirMensaje();
				List<SocketChat> canal = canales.get(canalElejido);
				if(canal != null){
					acerto = true;
					canal.add(cliente);
					new ThreadEnviar(canal, cliente.getUsuario()+" se ha unido al canal.", "Servidor").start();
					//cliente.enviarMensajeServidor("Te has unido a " + canalElejido);
					escuchar(canal);
					
				}
				else{
					cliente.enviarMensajeServidor("Canal Inexistente, escribalo nuevamente.");
					listarCanales();
				}
			}while(!acerto);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void listarCanales() throws IOException{
		for(String canal : canales.keySet()){
			cliente.enviarMensajeServidor(canal);
		}
	}
	
	private void escuchar(List<SocketChat> canal){
		boolean conetado = true;
		while(conetado){
			
			try {
				
				new ThreadEnviar(canal, cliente.pedirMensaje(), cliente.getUsuario()).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				canal.remove(cliente);
				try {
					cliente.cerrar();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				conetado = false;
				
				System.out.println("Servidor:Cliente Desconectado!");
			}
			
		}
	}
	
	

}
