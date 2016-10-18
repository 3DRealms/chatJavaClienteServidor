package chat.servidor;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ThreadServidor extends Thread{
	
	private List <Canal> canales = new ArrayList<Canal>();
	private int puerto;
	
	ThreadServidor() throws IOException{
		loadProperty("server.properties");

		
		canales.add(new Canal("General"));
		canales.add(new Canal("Comercio"));
		canales.add(new Canal("Guerra"));
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
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String sTexto;
			boolean conectado = true;

			sTexto = br.readLine();	
			while(! sTexto.equals("FIN") && conectado) {		  
				//aca hago comandos locos
				sTexto = br.readLine();
			}
			System.exit(0);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
