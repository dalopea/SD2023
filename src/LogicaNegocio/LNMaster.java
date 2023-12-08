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

public class LNMaster extends LNJugadorBase{

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
		try(ServerSocket ss = new ServerSocket(partida.getPuertoPartida())){
			for (int i = 0; i<this.partida.getNumeroJugadores(); i++) {
				try {
					Socket s = ss.accept();
					Thread thJugador = new Thread(new HiloJugadorPartida(s,this.partida,this.hilosJugadores));
					thJugador.start();
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
