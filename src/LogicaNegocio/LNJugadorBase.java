package LogicaNegocio;

import ModeloDominio.Partida;

/*
 * La lógica de negocio del JugadorBase, común a Máster y a Jugador, tiene el objeto partida.
 */
public class LNJugadorBase  {

	
	private Partida partida;
	
	public LNJugadorBase() {
		this.partida = new Partida();
	}
	public LNJugadorBase(Partida p) {
		this.partida = p;
	}

	public Partida getP() {
		return partida;
	}

	public void setP(Partida p) {
		this.partida = p;
	}
	
}
