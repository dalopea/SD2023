package LogicaNegocio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import ModeloDominio.Jugador;
import ModeloDominio.JugadorBase;

//Comentario
public class LNJugador {

	private JugadorBase jugador; //DAVID: Lo mismo que en lo de máster. Además, de ser necesarios... ¿no sería mejor que fuesen Jugador y Máster en vez de JugadorBase?
	
	public LNJugador (JugadorBase jugador) {
		this.jugador = jugador;
	}
	public HashMap<String,Integer> obtenerPartidas(){
		HashMap<String,Integer> partidas = null;
		try(Socket s = new Socket ("localhost", 55555);
				ObjectOutputStream oos = new ObjectOutputStream (s.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream (s.getInputStream())){
			boolean obtenido = false;
			
			while(!obtenido){
				oos.writeBytes("Obtener partidas\n");
				oos.flush();
				partidas = (HashMap<String,Integer>) ois.readObject();
				oos.writeBytes("Desconectar\n");
				oos.flush();
				obtenido = true;
			}
			
		}
		catch(IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return partidas;
	}
}
