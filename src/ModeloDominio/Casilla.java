package ModeloDominio;

import java.io.Serializable;

//Comentario
public class Casilla implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	
	private Personaje personaje;
	private boolean disponible;//disponible hace referencia a si se puede poner cualquier cosa
	
	
	public Casilla(int x,int y) {
		this.x=x;
		this.y=y;
		this.personaje = null;
		this.disponible=true;
	}
	
	public int[] getCoordenadas() {
		int[] coords=new int[2];
		coords[0]=x;
		coords[1]=y;
		return coords;
	}
	
	
	
	public synchronized boolean isDisponible() {
		return disponible;
	}
	
	public synchronized void cambiarDisponibilidad() {
		disponible = !disponible;
	}
	
	public synchronized void setPersonaje(Personaje personaje) {
		this.personaje = personaje;
	}
	
	public synchronized Personaje getPersonaje() {
		return this.personaje;
	}
	
	
	
	
}
