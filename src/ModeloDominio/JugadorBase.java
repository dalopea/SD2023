package ModeloDominio;
/*
 * La clase JugadorBase es una clase abstracta que se utiliza para representar los aspectos comunes de Master y Jugador.
 */
public abstract class JugadorBase {

	String nombreUsuario;

	public JugadorBase(String nombreUsuario) {
		this.nombreUsuario=nombreUsuario;
	}
	
	public String getNombreUsuario() {
		return this.nombreUsuario;
	}
	
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	
}
