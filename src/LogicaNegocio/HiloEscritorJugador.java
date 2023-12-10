package LogicaNegocio;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import ModeloDominio.Partida;

/*
 * Esta clase, aunque su nombre indique lo contrario, NO es un hilo. Es la clase que se encarga de enviar los mensajes del jugador al máster.
 * En el pasado lo hacíamos con un hilo, pero nos dimos cuenta de que no era necesario.
 */
public class HiloEscritorJugador{

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
				e.printStackTrace();
			}
		}
	}
	
	
	

}
