package chat.servidor;

import java.io.IOException;

import java.util.List;
import java.util.Map;

import chat.mensaje.Mensaje;

public class ThreadEscuchar extends Thread{
	
	private List <Canal> canales;
	private SocketChat cliente;
	
	public ThreadEscuchar(List <Canal> canales, SocketChat cliente){
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
			cliente.setUsuario(cliente.pedirMensaje().getMensaje());
			cliente.enviarMensajeServidor("Bienvenido "+cliente.getUsuario()+ ", ¿a que numero de canal desea unirse?");
			listarCanales();
			boolean acerto=false;
			do{
				
				try{
					String numero = cliente.pedirMensaje().getMensaje();
					
					int index = Integer.parseInt(numero);
					Canal canalElejido = canales.get(index-1);
					acerto = true;
					canalElejido.agregarCliente(cliente);					
					new ThreadEnviar(canalElejido, new Mensaje("Servidor", cliente.getUsuario()+" se ha unido al canal.")).start();
					//cliente.enviarMensajeServidor("Te has unido a " + canalElejido);
					escuchar(canalElejido);
				}
				catch(IndexOutOfBoundsException e){
					cliente.enviarMensajeServidor("Canal Inexistente, escribalo nuevamente.");
					listarCanales();
					
				}
				catch(NumberFormatException e){
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
		for(Canal auxCanal : canales){
			cliente.enviarMensajeServidor((canales.indexOf(auxCanal)+1) + " - " + auxCanal.toString());
		}
	}

	private void escuchar(Canal can){
		boolean conetado = true;
		while(conetado){
			
			try {
				Mensaje mens = new Mensaje(cliente.getUsuario(),cliente.pedirMensaje().getMensaje());
				new ThreadEnviar(can, mens).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				can.quitarCliente(cliente);
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
