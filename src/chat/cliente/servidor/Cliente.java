package chat.cliente.servidor;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Properties;

public class Cliente {
	
	Socket cliente;
	String serverIP;
	Integer puerto;
	
	Cliente(){
		try{
		
		

		loadProperty("cliente.properties");
		cliente = new Socket(serverIP, puerto);
		DataInputStream lectura = new DataInputStream(
				cliente.getInputStream());
		System.out.println(lectura.readUTF());

		

		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void escuchar(){
		new ThreadClienteEscuchar(cliente).start();
	}
	
	public void enviar(String mensaje) throws IOException{
		

			DataOutputStream salida = new DataOutputStream(
					cliente.getOutputStream());
			salida.writeUTF(mensaje);


	}
	
	public void cerrar(){
		try {
			cliente.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void loadProperty(String dir) throws IOException{
		Properties propiedades = new Properties();
		InputStream entrada = null;	
		entrada = new FileInputStream(dir);
		propiedades.load(entrada);
		serverIP = propiedades.getProperty("serverIP");
		String puertoString = propiedades.getProperty("port");
		puerto =  Integer.parseInt(puertoString);
	}
	
	public static void main(String[] args) {
		Cliente cliente = new Cliente();
		cliente.escuchar();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String sTexto;
		boolean conectado = true;
		try {
			sTexto = br.readLine();	
			while(! sTexto.equals("FIN") && conectado) {		  
				cliente.enviar(sTexto);
				sTexto = br.readLine();	
			} 
		} catch (IOException e) {
			conectado = false;
			System.out.println("Servidor Desconectado.");
		}


		cliente.cerrar();

	}
}
