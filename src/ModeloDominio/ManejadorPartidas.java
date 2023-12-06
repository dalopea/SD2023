package ModeloDominio;
/*
 * El manejador de partidas se encarga de gestionar la lista de partidas abiertas que tiene el servidor: añadir, eliminar y retornar partidas.
 * Los métodos son synchronized para evitar conflictos.
 */
import java.util.HashMap;

public class ManejadorPartidas {

	HashMap<String,Integer> partidas;//VICTOR: al final no haremos una lista de objetos partida? Lo digo por que puede ser mejor, ya que la partida guarda el puerto y así
									//puedo usarla para mostrarla en la interfaz gráfica.
	
	public ManejadorPartidas() {
		this.partidas = new HashMap<String,Integer>();
	}
	public synchronized HashMap<String,Integer> getPartidas(){
		return this.partidas;
	}
	
	public synchronized void setPartidas(HashMap<String,Integer> partidas) {
		this.partidas = partidas;
	}
	
	public synchronized boolean aniadirPartida(String nombrePartida, int puertoPartida) {
		if (partidas.containsKey(nombrePartida)) {
			return false;
		}
		else {
			partidas.put(nombrePartida, puertoPartida);
			return true;
		}
	}
	
	public synchronized boolean eliminarPartida(String nombrePartida) {
		if (partidas.containsKey(nombrePartida)) {
			partidas.remove(nombrePartida);
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
