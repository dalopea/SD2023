package LogicaNegocio;

import java.net.Socket;

import javax.swing.JTextArea;

import ModeloDominio.Jugador;
import ModeloDominio.Partida;

/*
 * La lógica de negocio del jugador. Se encarga de unirse a la partida creada por el máster, iniciando el hilo lector e instanciando un objeto de la clase HiloEscritor.
 * También manda un primer mensaje al máster con su nombre de usuario, para añadirlo a la lista de jugadores de la partida.
 */
public class LNJugador extends LNJugadorBase {
	
	private Jugador jugador; 
	public HiloEscritorJugador hiloEscritorJugador;
	private HiloLectorJugador hiloLectorJugador;
	
	public LNJugador (Jugador jugador) {
		super();
		this.setP(null);
		this.jugador = jugador;
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
		this.hiloEscritorJugador = new HiloEscritorJugador(s,this.getPartida());
		this.hiloLectorJugador = new HiloLectorJugador(s,this);
		this.hiloLectorJugador.start();
		this.hiloEscritorJugador.enviarMensaje(jugador.getNombreUsuario());
	}
}
