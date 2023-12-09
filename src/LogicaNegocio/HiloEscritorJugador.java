package LogicaNegocio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

import ModeloDominio.Partida;

public class HiloEscritorJugador extends Thread{

	private Socket s;
	private Partida p;
	private ObjectOutputStream oos;
	
	
	public HiloEscritorJugador(Socket s, Partida partida) {
		this.s = s;
		this.p = partida;
		abrirStream();
	}
	
	
	public void abrirStream() {
		
		try {
			if(oos==null) {
			oos=new ObjectOutputStream(s.getOutputStream());}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean streamAbierto() {
		return !s.isClosed();
	}
	
	public void enviarMensaje(String mensaje) {
		if(streamAbierto()) {
			try {
				oos.writeBytes(mensaje+"\n");
				oos.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	

}
