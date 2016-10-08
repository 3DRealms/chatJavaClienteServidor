package chat.cliente.servidor;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Cliente {
	
	Socket cliente;
	
	Cliente(){
		try{
		cliente = new Socket("localhost", 1000);
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
	
	public void enviar(String mensaje){
		
		
		try {
			DataOutputStream salida = new DataOutputStream(
					cliente.getOutputStream());
			salida.writeUTF(mensaje);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void cerrar(){
		try {
			cliente.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Cliente cliente = new Cliente();
		cliente.escuchar();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String sTexto;
		try {
		do {
			  
				sTexto = br.readLine();
				System.out.println(sTexto);
				cliente.enviar(sTexto);
		} while(! sTexto.equals("FIN"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  
			
		cliente.cerrar();

	}
}
