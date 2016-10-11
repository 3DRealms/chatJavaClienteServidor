package chat.cliente.servidor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ThreadServidor extends Thread{
	
	private Map <String, List<SocketChat>> canales = new HashMap<String, List<SocketChat>>();
	private int puerto;
	
	ThreadServidor() throws IOException{
		loadProperty("server.properties");

		
		canales.put("CANAL 1", new ArrayList<SocketChat>());
		canales.put("CANAL 2", new ArrayList<SocketChat>());
		canales.put("CANAL 3", new ArrayList<SocketChat>());
	}
	
	@Override
	public void run(){
		
		try{
			ServerSocket server = new ServerSocket(puerto);
			SocketChat cliente;
			while(true){
				cliente = new SocketChat(server.accept());
				//cliente.enviarMensajeServidor("Conectado!");
				System.out.println("Servidor:Cliente Conectado!");
				//clientes.add(cliente);
				new ThreadEscuchar(canales,cliente).start();
				cliente = null;
			}
			
	
			}
			catch(IOException e){
				e.printStackTrace();
			}

		
	}
	
	private void loadProperty(String dir) throws IOException{
		Properties propiedades = new Properties();
		InputStream entrada = null;	
		entrada = new FileInputStream(dir);
		propiedades.load(entrada);
		
		String puertoString = propiedades.getProperty("port");
		puerto =  Integer.parseInt(puertoString);
	}
	
	public static void main(String[] args) {
		ThreadServidor escuchar;
		try {
			escuchar = new ThreadServidor();
			escuchar.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
