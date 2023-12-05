package ModeloDominio;

import java.util.*;

public class Partida {

	Master master;
	List<Jugador> jugadores;
	Tablero tablero;
	boolean iniciado;
	
	public Partida() {
		
		jugadores=new ArrayList<>();
		iniciado=false;
		
	}
	
	public boolean isIniciada() {
		return iniciado;
	}
	
	public void iniciar() {
		this.iniciado=true;
	}
	
	public Tablero getTablero() {
		return tablero;
	}
	
	public void setTablero(Tablero t) {
		tablero=t;
	}
	
	public void addPlayer(Jugador j) {
		jugadores.add(j);
	}
	
	public void removePlayer(Jugador j) {
		jugadores.remove(j);
	}
	
	public List<Jugador> getJugadores() {
		return jugadores;
	}
	
	
	
}
