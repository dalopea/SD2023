package ModeloDominio;

import java.io.Serializable;
import java.util.*;

/*
 * La clase partida guarda la información relativa a la partida en curso:
 * 	- Nombre de partida
 * 	- Puerto de la partida
 *  - Lista de jugadores
 *  - Estado del tablero (y, por tanto, de cada una de las casillas)
 *  - Iniciada o no iniciada
 */
public class Partida implements Serializable{

	String nombrePartida;
	int puertoPartida;
	Master master;
	List<Jugador> jugadores;
	Tablero tablero;
	boolean iniciado;
	
	public Partida() {
		
		this.jugadores=new ArrayList<Jugador>();
		this.iniciado=false;
		
		
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
