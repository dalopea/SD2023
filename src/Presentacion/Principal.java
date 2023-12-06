package Presentacion;

import java.io.*;
import java.util.HashMap;

import LogicaNegocio.*;
import ModeloDominio.*;
//Comentario
public class Principal {

	public static void main(String[] args) {
		
		
		try(BufferedReader bf=new BufferedReader(new InputStreamReader(System.in))){
			System.out.println("Eliga el nombre de jugador");
			String nombre=bf.readLine();
			
			System.out.println("¿Qué deseas hacer, crear una partida (Crear) o unirte a una partida existente (Unir)?");
			String dec=bf.readLine();
			
			if(dec.equalsIgnoreCase("Crear")) {
				JugadorBase master=new Master(nombre);
				LNMaster lnMaster=new LNMaster(master);
				Partida p= lnMaster.crearPartida();
				PartidaTextualMaster ptm=new PartidaTextualMaster(p, lnMaster);
				ptm.mostrarInterfaz();
				
			}else if(dec.equalsIgnoreCase("Unir")) {
				System.out.println("Has seleccionado unirte a una partida");
				JugadorBase jugador = new Jugador(nombre);
				LNJugador lnJugador = new LNJugador(jugador);
				HashMap<String, Integer> partidas = lnJugador.obtenerPartidas();
				for (String s : partidas.keySet()) {
					System.out.println("Nombre de partida " + s + " con puerto " + partidas.get(s));
				}
				
			}
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
