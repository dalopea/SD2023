package ModeloDominio;
/*
 * El manejador de partidas se encarga de gestionar la lista de partidas abiertas que tiene el servidor: añadir, eliminar y retornar partidas.
 * Los métodos son synchronized para evitar conflictos.
 * 
 * Tiene un HashMap en el que almacena todas las partidas. El identificador (String), es de la siguiente estructura:  nombreMaster:nombrePartida.
 * Por lo tanto, puede haber varias partidas con el mismo nombre, siempre que el máster sea distinto.
 */
import java.util.HashMap;

public class ManejadorPartidas {

	HashMap<String,Integer> partidas;
	
	public ManejadorPartidas() {
		this.partidas = new HashMap<String,Integer>();
	}
	public synchronized HashMap<String,Integer> getPartidas(){
		return this.partidas;
	}
	
	public synchronized void setPartidas(HashMap<String,Integer> partidas) {
		this.partidas = partidas;
	}
	
	public synchronized boolean aniadirPartida(String nombreMaster, String nombrePartida, int puertoPartida) {
		String identificador = nombreMaster + ":" + nombrePartida;
		if (partidas.containsKey(identificador)) {
			return false;
		}
		else {
			partidas.put(identificador, puertoPartida);
			return true;
		}
	}
	
	public synchronized boolean eliminarPartida(String identificador) {
		if (partidas.containsKey(identificador)) {
			partidas.remove(identificador);
			return true;
		}
		else {
			return false;
		}
	}
	
	public synchronized HashMap<String,Integer> obtenerPartidas() {
		return this.partidas;
	}
	
	
}
