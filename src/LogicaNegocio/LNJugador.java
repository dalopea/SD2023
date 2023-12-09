package LogicaNegocio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import javax.swing.JTextArea;

import ModeloDominio.Jugador;
import ModeloDominio.JugadorBase;
import ModeloDominio.Partida;

//Comentario
public class LNJugador extends LNJugadorBase {

	private Jugador jugador; 
	private Partida partida;
	public HiloEscritorJugador hiloEscritorJugador;
	private HiloLectorJugador hiloLectorJugador;
	
	public LNJugador (Jugador jugador) {
		this.jugador = jugador;
		this.partida = new Partida();
	}
	
	public Jugador getJugador() {
		return this.jugador;
	}
	
	public Partida getPartida() {
		return super.getP();
	}
	
	public void setTxtArea( JTextArea txtLeer) {
		this.hiloLectorJugador.setTextArea(txtLeer);
	}
	
	
	public void unirseAPartida(Socket s) {
		this.hiloEscritorJugador = new HiloEscritorJugador(s,partida);
		this.hiloLectorJugador = new HiloLectorJugador(s,this);
		this.hiloLectorJugador.start();
		this.hiloEscritorJugador.enviarMensaje(jugador.getNombreUsuario());
	}
}
