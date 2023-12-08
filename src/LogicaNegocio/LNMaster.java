package LogicaNegocio;


import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ModeloDominio.*;

/*
 * La clase LNMaster tiene la funcionalidad del "Máster" de una partida de rol. Podrá crear la partida, introducir los monstruos,
 * alterar el campo, ver toda la información del tablero...
 */

public class LNMaster extends LNJugadorBase {

	private Master master; 
	private Partida partida;
	private List<HiloJugadorPartida> hilosJugadores;
	
	public LNMaster(Master m,Partida p) {
		this.master=m;
		this.partida=p;
		this.hilosJugadores = new ArrayList<HiloJugadorPartida>();
	}
	
	
	
	/*
	 * El método iniciarPartida crea el servidor, con el puerto que ha utilizado para conectarse al servidor de partidas. Permitirá la entrada de
	 * los distintos jugadores y les asignará a cada uno un hilo distinto.
	 * Todos los hilos comparten el mismo objeto Partida, que es el que tiene toda la información del estado del tablero.
	 */
	public void iniciarPartida() {
		ExecutorService poolJugadores = Executors.newCachedThreadPool();
		try(ServerSocket ss = new ServerSocket(partida.getPuertoPartida())){
			while(true) {
				try {
					Socket s = ss.accept();
					poolJugadores.execute(new HiloJugadorPartida(s,this.partida,this.hilosJugadores));
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			poolJugadores.shutdown();
		}
	}
	
	
	
	
}
