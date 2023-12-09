package LogicaNegocio;


import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JTextArea;

import ModeloDominio.*;

/*
 * La clase LNMaster tiene la funcionalidad del "Máster" de una partida de rol. Podrá crear la partida, introducir los monstruos,
 * alterar el campo, ver toda la información del tablero...
 */

public class LNMaster extends LNJugadorBase{

	private Master master; 
	private Partida partida;
	private List<HiloJugadorPartida> hilosJugadores;
	private List<Personaje> personajes;
	private JTextArea txtLeer;
	
	public LNMaster(Master m,Partida p) {
		this.master=m;
		this.partida=p;
		this.hilosJugadores = new ArrayList<HiloJugadorPartida>();
		this.personajes = new ArrayList<Personaje>();
	}
	
	public Master getMaster() {
		return this.master;
	}
	
	public Partida getPartida() {
		return this.partida;
	}
	
	public void addTXT(JTextArea txtLeer) {
		this.txtLeer=txtLeer;
	}
	
	
	public void broadcast(String mensaje) {
		System.out.println(hilosJugadores.size());
		for(HiloJugadorPartida hjp:hilosJugadores) {
			hjp.enviarAMi(mensaje);
		}
	}
	
	/*
	 * El método iniciarPartida crea el servidor, con el puerto que ha utilizado para conectarse al servidor de partidas. Permitirá la entrada de
	 * los distintos jugadores y les asignará a cada uno un hilo distinto.
	 * Todos los hilos comparten el mismo objeto Partida, que es el que tiene toda la información del estado del tablero.
	 */
	public void iniciarPartida() {
		try(ServerSocket ss = new ServerSocket(partida.getPuertoPartida())){
			for (int i = 0; i<1; i++) {
				try {
					Socket s = ss.accept();
					HiloJugadorPartida par=new HiloJugadorPartida(s,this.partida,this.hilosJugadores);
					par.setTxtArea(txtLeer);
					Thread thJugador = new Thread(par);
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
	
	public void eliminarPartidaLista() {
		try (Socket s = new Socket("localhost",55555);
				ObjectOutputStream oos = new ObjectOutputStream (s.getOutputStream())){
			oos.writeBytes("Eliminar Partida\n");
			oos.writeBytes(this.master.getNombreUsuario() + ":" + this.partida.getNombrePartida() + "\n");
			oos.writeBytes("Desconectar\n");
			oos.flush();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void nuevoPersonaje(Personaje personaje) {
		this.personajes.add(personaje);
	}
	
	public void eliminarPersonaje(Personaje personaje) {
		this.personajes.remove(personaje);
	}
	
	public void personajeACasilla(Personaje personaje, int x, int y) {
		this.partida.getTablero().getCasilla(x,y).setPersonaje(personaje);
	}
	
	
	
	
}
