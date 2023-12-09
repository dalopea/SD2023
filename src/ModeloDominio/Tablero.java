package ModeloDominio;

import java.io.Serializable;
import java.util.*;
//Comentario
public class Tablero implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Casilla> mapa=new ArrayList<>();
	private int ancho;
	private int largo;
	
	
	public Tablero(int ancho,int largo) {
		this.setAncho(ancho);
		this.setLargo(largo);
		for(int i=1;i<=largo;i++) {
			for(int j=1;j<=ancho;j++) {
				mapa.add(new Casilla(j,i));
			}
		}
	}

	public Casilla getCasilla(int x, int y) {
		return this.mapa.get((y-1)*ancho+x-1);
	}
	
	public List<Casilla> getMapa(){
		return this.mapa;
	}

	public int getAncho() {
		return ancho;
	}


	public void setAncho(int ancho) {
		this.ancho = ancho;
	}


	public int getLargo() {
		return largo;
	}


	public void setLargo(int largo) {
		this.largo = largo;
	}
	
	
}
