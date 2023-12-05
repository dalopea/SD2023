package ModeloDominio;

import java.util.*;

public class Partida {

	String nombrePartida;
	int puertoPartida;
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
	
	public String getNombrePartida() {
		return this.nombrePartida;
	}
	
	public void setNombrePartida(String nombrePartida) {
		this.nombrePartida = nombrePartida;
	}
	
	public int getPuertoPartida() {
		return this.puertoPartida;
	}
	
	public void setPuertoPartida(int puertoPartida) {
		this.puertoPartida = puertoPartida;
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
