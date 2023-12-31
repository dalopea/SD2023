package LogicaNegocio;


import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

import javax.swing.JTextArea;

import ModeloDominio.*;

/*
 * La clase LNMaster tiene la funcionalidad del "Máster" de una partida de rol. Podrá crear la partida, introducir los monstruos,
 * alterar el campo, ver toda la información del tablero...
 */

public class LNMaster extends LNJugadorBase{

	private Master master; 
	private List<HiloJugadorPartida> hilosJugadores;
	private int numJugadores;
	private JTextArea txtLeer;
	
	public LNMaster(Master m,Partida p,int n) {
		super(p);
		this.master=m;
		this.hilosJugadores = new ArrayList<HiloJugadorPartida>();
		this.numJugadores=n;
		this.getP().setMaster(m);
	}
	
	public int getnumJugadores() {
		return this.numJugadores;
	}
	
	public Master getMaster() {
		return this.master;
	}
	
	public Partida getPartida() {
		return super.getP();
	}
	
	
	public void addTXT(JTextArea txtLeer) {
		this.txtLeer=txtLeer;
	}
	
	
	public synchronized void broadcast(String mensaje) {
		System.out.println(hilosJugadores.size());
		for(HiloJugadorPartida hjp:hilosJugadores) {
			hjp.enviarATodos(mensaje);
		}
	}
	
	/*
	 * El método iniciarPartida crea el servidor, con el puerto que ha utilizado para conectarse al servidor de partidas. Permitirá la entrada de
	 * los distintos jugadores y les asignará a cada uno un hilo distinto.
	 * Todos los hilos comparten el mismo objeto Partida, que es el que tiene toda la información del estado del tablero.
	 */
	public void iniciarPartida(CyclicBarrier barrera) {
		try(ServerSocket ss = new ServerSocket(this.getP().getPuertoPartida())){
			for (int i = 0; i<this.numJugadores; i++) {
				try {
					Socket s = ss.accept();
					HiloJugadorPartida par=new HiloJugadorPartida(s,this.hilosJugadores,this,barrera);
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
		eliminarPartidaLista();
	}
	
	public void eliminarPartidaLista() {
		try (Socket s = new Socket("localhost",55555);
				ObjectOutputStream oos = new ObjectOutputStream (s.getOutputStream())){
			oos.writeBytes("Eliminar Partida\n");
			oos.writeBytes(this.master.getNombreUsuario() + ":" + this.getP().getNombrePartida() + "\n");
			oos.writeBytes("Desconectar\n");
			oos.flush();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void nuevoPersonaje(Personaje personaje) {
		this.getP().nuevoPersonaje(personaje);
	}
	
	public void eliminarPersonaje(Personaje personaje) {
		this.getP().eliminarPersonaje(personaje);
	}
	
	public void personajeACasilla(Personaje personaje, int x, int y) {
		this.getP().getTablero().getCasilla(x,y).setPersonaje(personaje);
	}
	
	
	
	
}
