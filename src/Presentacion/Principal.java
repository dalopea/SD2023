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
				Master master=new Master(nombre);
				LNMaster lnMaster=new LNMaster(master);
				lnMaster.crearPartida();
				lnMaster.iniciarPartida();
				
			}else if(dec.equalsIgnoreCase("Unir")) {
				System.out.println("Has seleccionado unirte a una partida");
				Jugador jugador = new Jugador(nombre);
				LNJugador lnJugador = new LNJugador(jugador);
				HashMap<String, Integer> partidas = lnJugador.obtenerPartidas();
				for (String s : partidas.keySet()) {
					System.out.println("-------Nueva Partida------");
					System.out.println("Nombre de máster: " + s.split(":")[0]);
					System.out.println("Nombre de partida: " + s.split(":")[1] + " con puerto " + partidas.get(s)); 
				}
				System.out.println("Escoge una partida a la que conectarte (puerto)");
				String puerto = bf.readLine();
				int puertoEntero = Integer.parseInt(puerto);
				lnJugador.unirseAPartida(puertoEntero);
				
			}
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
