package LogicaNegocio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import ModeloDominio.Jugador;
import ModeloDominio.JugadorBase;
import ModeloDominio.Partida;

//Comentario
public class LNJugador {

	private Jugador jugador; //DAVID: Pues lo paso a la clase concreta
	private Partida partida;
	private HiloEscritorJugador hiloEscritorJugador;
	private HiloLectorJugador hiloLectorJugador;
	
	public LNJugador (Jugador jugador) {
		this.jugador = jugador;
		this.partida = new Partida();
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
	
	public void unirseAPartida(int numeroPuerto) {
		try(Socket s = new Socket("localhost",numeroPuerto)){
			this.hiloEscritorJugador = new HiloEscritorJugador(s,partida);
			this.hiloLectorJugador = new HiloLectorJugador(s,partida);
			this.hiloEscritorJugador.start();
			this.hiloLectorJugador.start();
			this.hiloEscritorJugador.join();
			this.hiloLectorJugador.join();	
		}
		catch(IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
