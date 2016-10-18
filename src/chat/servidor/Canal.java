package chat.servidor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import chat.mensaje.Mensaje;

public class Canal {
	
	List<SocketChat> canal;
	String nombre;
	public Canal(String nombre) {

		this.canal = new ArrayList<SocketChat>();
		this.nombre = nombre;
	}
	
	public boolean esMiNombre(String nombre){
		return nombre.equals(this.nombre);
	}
	
	public void agregarCliente(SocketChat cliente){
		canal.add(cliente);
	}
	
	public String toString(){
		return nombre;
	}
	
	public void quitarCliente(SocketChat cliente){
		canal.remove(cliente);
	}
	
	public void enviarMensaje(Mensaje men){
		
		for(SocketChat cliente : canal)
		{
			
			try {
				cliente.enviarMensaje(men);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				this.quitarCliente(cliente);
				
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
