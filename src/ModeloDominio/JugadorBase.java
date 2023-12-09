package ModeloDominio;

import java.io.Serializable;

/*
 * La clase JugadorBase es una clase abstracta que se utiliza para representar los aspectos comunes de Master y Jugador.
 */
public abstract class JugadorBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String nombreUsuario;
	Partida partida;

	public JugadorBase(String nombreUsuario) {
		this.nombreUsuario=nombreUsuario;
	}
	
	public String getNombreUsuario() {
		return this.nombreUsuario;
	}
	
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	
	public Partida getPartida(){
		return this.partida;
	}
	
	public void setPartida(Partida partida) {
		this.partida = partida;
	}
	
}
