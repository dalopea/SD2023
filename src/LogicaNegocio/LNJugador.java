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
public class LNJugador extends LNJugadorBase {

	private Jugador jugador; //DAVID: Pues lo paso a la clase concreta
	private Partida partida;
	private HiloEscritorJugador hiloEscritorJugador;
	private HiloLectorJugador hiloLectorJugador;
	
	public LNJugador (Jugador jugador) {
		this.jugador = jugador;
		this.partida = new Partida();
	}
	
	
	public void unirseAPartida(Socket s) {
		try{
			this.hiloEscritorJugador = new HiloEscritorJugador(s,partida);
			this.hiloLectorJugador = new HiloLectorJugador(s,partida);
			this.hiloEscritorJugador.start();
			this.hiloLectorJugador.start();
			this.hiloEscritorJugador.join();
			this.hiloLectorJugador.join();	
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
}
