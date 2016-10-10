package chat.cliente.servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketChat {
	
	String usuario = "Anonimo";
	Socket cliente;
	
	public SocketChat(Socket cliente){
		this.cliente = cliente;
	}
	
	public void setUsuario(String usuario){
		this.usuario = usuario;
	}
	
	public void enviarMensaje(String mensaje, String remitente) throws IOException{
		DataOutputStream salida = new DataOutputStream(
				cliente.getOutputStream());
		salida.writeUTF(remitente+":"+mensaje);
	}
	
	public void enviarMensajeServidor(String mensaje) throws IOException{
		this.enviarMensaje(mensaje, "Servidor");
	}
	
	public void cerrar() throws IOException{
		cliente.close();
	}
	
	public String getUsuario(){
		return usuario;
	}
	
	public String pedirMensaje() throws IOException{
		DataInputStream lectura = new DataInputStream(
				cliente.getInputStream());
		return lectura.readUTF();
	}
	
	

}
