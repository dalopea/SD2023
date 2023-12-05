package ModeloDominio;

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
	
	public synchronized boolean aniadirPartida(Partida p) {
		if (partidas.containsKey(p.nombrePartida)) {
			return false;
		}
		else {
			partidas.put(p.nombrePartida, p.puertoPartida);
			return true;
		}
	}
	
	
}
