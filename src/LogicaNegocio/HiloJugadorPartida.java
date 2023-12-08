package LogicaNegocio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import ModeloDominio.Partida;

public class HiloJugadorPartida implements Runnable{

	private Socket s;
	private Partida partida;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private List<HiloJugadorPartida> hilosJugadores;
	
	
	public HiloJugadorPartida(Socket s, Partida partida, List<HiloJugadorPartida> hilosJugadores) {
		
		try{
			this.s = s;
			this.partida = partida;
			this.oos = new ObjectOutputStream(s.getOutputStream());
			this.ois = new ObjectInputStream(s.getInputStream());
			this.hilosJugadores = hilosJugadores;
			hilosJugadores.add(this);
		}
		catch(IOException e) {
			cerrarTodo();
		}
		
	}
	@Override
	public void run() {
		String mensaje;
		try {
			oos.writeBytes("Partida\n");
			oos.writeObject(partida);
			oos.flush();
			mensaje = ois.readLine();
			while (mensaje != null) {
				mensajeBroadcast(mensaje);
				mensaje = ois.readLine();
			}
		}
		catch(IOException e) {
			
		}
		finally {
			cerrarTodo();
			System.out.println("Cerrando este hilo");
		}
		
		
	}
	
	public void mensajeBroadcast(String mensaje) {
		for (HiloJugadorPartida hilo : this.hilosJugadores) {
			try {
				if (!hilo.equals(this)) {
					hilo.oos.writeBytes(mensaje + "\n");
					hilo.oos.flush();
				}
			}
			catch(IOException e) {
				cerrarTodo();
			}
			
		}
	}
	
	public void eliminarHiloJugador() {
		hilosJugadores.remove(this);
	}
	
	
	public void cerrarTodo() {
		eliminarHiloJugador();
		try {
			if (ois != null) {
				ois.close();
			}
			if (oos != null) {
				oos.close();
			}
			if (s!= null) {
				s.close();
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

}
