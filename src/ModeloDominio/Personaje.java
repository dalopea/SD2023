package ModeloDominio;

import java.io.Serializable;

/*
 * La clase personaje representa a los personajes de los jugadores (PJs) y a los personajes no jugadores (PNJs), includos los monstruos.
 * Todo personaje está identificado por un nombre de personaje, un movimiento (número de casillas que puede moverse en total en un único movimiento), un ataque, una defensa
 * y unos puntos vitales.
 * 
 * Cuenta con el path a su imagen y la casilla en la que se encuentra.
 * 
 * Cuando los puntos vitales están por debajo de 0, el personaje está muerto. Un personaje nunca puede tener más puntos de vida que su máximo de puntos de vida.
 */

public class Personaje implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String nombrePersonaje;
	private int puntosAtaque;
	private int puntosDefensa;
	private int puntosVidaMaximos;
	private int puntosVidaActuales;
	private int movimiento;
	private String imgPath;
	private Casilla posicion;
	
	public Personaje (String nombrePersonaje, int puntosAtaque, int puntosDefensa, int puntosVida, int movimiento,String img) {
		this.nombrePersonaje = nombrePersonaje;
		this.puntosAtaque = puntosAtaque;
		this.puntosDefensa = puntosDefensa;
		this.puntosVidaActuales = puntosVida;
		this.puntosVidaMaximos = puntosVida;
		this.movimiento = movimiento;
		this.imgPath=img;
		this.setPosicion(null);
	}
	
	
	public String getNombrePersonaje() {
		return nombrePersonaje;
	}
	public void setNombrePersonaje(String nombrePersonaje) {
		this.nombrePersonaje = nombrePersonaje;
	}
	public int getPuntosAtaque() {
		return puntosAtaque;
	}
	public void setPuntosAtaque(int puntosAtaque) {
		this.puntosAtaque = puntosAtaque;
	}
	public int getPuntosDefensa() {
		return puntosDefensa;
	}
	public void setPuntosDefensa(int puntosDefensa) {
		this.puntosDefensa = puntosDefensa;
	}
	public int getPuntosVidaMaximos() {
		return puntosVidaMaximos;
	}
	public void setPuntosVidaMaximos(int puntosVidaMaximos) {
		this.puntosVidaMaximos = puntosVidaMaximos;
	}
	public int getPuntosVidaActuales() {
		return puntosVidaActuales;
	}
	public void setPuntosVidaActuales(int puntosVidaActuales) {
		this.puntosVidaActuales = puntosVidaActuales;
	}
	public int getMovimiento() {
		return movimiento;
	}
	public void setMovimiento(int movimiento) {
		this.movimiento = movimiento;
	}
	
	public String getImagen() {
		return this.imgPath;
	}
	
	public void setImagen(String imagen) {
		this.imgPath = imagen;
	}


	public Casilla getPosicion() {
		return posicion;
	}


	public void setPosicion(Casilla posicion) {
		this.posicion = posicion;
	}
	
	
}
