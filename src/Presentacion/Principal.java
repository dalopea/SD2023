package Presentacion;

import java.io.*;

import LogicaNegocio.*;
import ModeloDominio.*;

public class Principal {

	public static void main(String[] args) {
		
		
		try(BufferedReader bf=new BufferedReader(new InputStreamReader(System.in))){
			System.out.println("Eliga el nombre de jugador");
			String nombre=bf.readLine();
			
			System.out.println("Que desea hacer, Crear o Unirse");
			String dec=bf.readLine();
			
			if(dec.equalsIgnoreCase("Crear")) {
				JugadorBase master=new Master(nombre);
				LNMaster lnMaster=new LNMaster(master);
				Partida p=new Partida();//Le faltan las cosas, e igual no se le sa a la partida textual
				PartidaTextualMaster ptm=new PartidaTextualMaster(p, lnMaster);
				ptm.mostrarInterfaz();
				
			}else if(dec.equalsIgnoreCase("unirse")) {
				System.out.println();
				
			}
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
