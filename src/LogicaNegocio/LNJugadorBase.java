package LogicaNegocio;

import ModeloDominio.Partida;

//Comentario
public class LNJugadorBase {

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
