package Presentacion;

import java.io.*;
import java.net.*;
import java.util.*;

import ModeloDominio.ManejadorPartidas;

/*
 * Hilo principal del servidor, que se encarga de añadir las nuevas partidas al manejador de partidas, eliminar las partidas del manejador
 * de partidas y mostrar las partidas del manejador de partidas.
 */
public class HiloServerPartidas extends Thread {

	private Socket s;
	ManejadorPartidas manejadorPartidas;
	
	
	public HiloServerPartidas(Socket s,ManejadorPartidas manejadorPartidas) {
		this.s=s;
		this.manejadorPartidas = manejadorPartidas;
	}
	
	
	public void run() {
			try(ObjectOutputStream oos = new ObjectOutputStream (s.getOutputStream());
					ObjectInputStream ois = new ObjectInputStream (s.getInputStream())){
				
				String peticion=ois.readLine();
				boolean exito = false;
				while(!peticion.equals("Desconectar")) {
					if (peticion.startsWith("Nueva")) {
						peticion = ois.readLine();
						String nombreMaster = peticion.split(":")[1];
						peticion = ois.readLine();
						String nombrePartida = peticion.split(":")[1];
						exito = manejadorPartidas.aniadirPartida(nombreMaster,nombrePartida,s.getPort());
						if (!exito) {
							oos.writeBytes("ERROR: no se ha podido crear la partida\n");
						}
						else {
							oos.writeBytes("Éxito en la creación de la partida\n");
						}
						oos.flush();
					}
					else if (peticion.startsWith("Obtener")) {
						oos.writeObject(manejadorPartidas.getPartidas());
						oos.flush();
						oos.reset();
					}
					peticion = ois.readLine();
				}
				
				
				
			}catch(IOException e) {
				e.printStackTrace();
			}finally {
				try {
					s.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		
		
	}
}
