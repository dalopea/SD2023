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

	
	private static final long serialVersionUID = 1L;
	private String nombrePartida;
	private int puertoPartida;
	private Master master;
	private int numeroJugadores;
	private List<String> jugadores;
	private Tablero tablero;
	private boolean iniciado;
	private List<Personaje> personajes;
	
	public Partida() {
		
		this.jugadores=new ArrayList<String>();
		this.iniciado=false;
		this.personajes = new ArrayList<Personaje>();
		this.tablero=new Tablero(20, 20);
		
		
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
	
	public void addPlayer(String nombre) {
		jugadores.add(nombre);
	}
	
	public void removePlayer(Jugador j) {
		jugadores.remove(j);
	}
	
	public List<String> getJugadores() {
		return jugadores;
	}
	
	public int getNumeroJugadores() {
		return this.numeroJugadores;
	}
	
	public void setNumeroJugadores(int numeroJugadores) {
		this.numeroJugadores = numeroJugadores;
	}

	public Master getMaster() {
		return master;
	}

	public void setMaster(Master master) {
		this.master = master;
	}
	
	public List<Personaje> getPersonajes() {
		return personajes;
	}
	public void setPersonajes(List<Personaje> personajes) {
		this.personajes = personajes;
	}
	public void nuevoPersonaje(Personaje personaje) {
		this.personajes.add(personaje);
	}
	
	public void eliminarPersonaje(Personaje personaje) {
		this.personajes.remove(personaje);
	}
	
	public boolean existePersonaje(Personaje p) {
		
		for(Personaje j:personajes) {
			if(j.getNombrePersonaje().equals(p.getNombrePersonaje())) {
				return true;
			}
		}
		return false;
	}
	
	
}
