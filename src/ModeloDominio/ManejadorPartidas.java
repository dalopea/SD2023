package ModeloDominio;
/*
 * El manejador de partidas se encarga de gestionar la lista de partidas abiertas que tiene el servidor: añadir, eliminar y retornar partidas.
 * Los métodos son synchronized para evitar conflictos.
 */
import java.util.HashMap;

public class ManejadorPartidas {

	HashMap<String,Integer> partidas;//VICTOR: al final no haremos una lista de objetos partida? Lo digo por que puede ser mejor, ya que la partida guarda el puerto y así
									//puedo usarla para mostrarla en la interfaz gráfica.
									//Respuesta: Por ahora voy a hacer lo que dije, voy a añadir el nombre de máster tmb en el String.
									//El orden será nombreMaster:nombrePartida.
	
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
	
	public synchronized boolean eliminarPartida(String nombreMaster, String nombrePartida) {
		String identificador = nombreMaster + ":" + nombrePartida;
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
