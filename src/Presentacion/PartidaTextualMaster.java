package Presentacion;

import LogicaNegocio.*;
import ModeloDominio.*;
//Comentario
public class PartidaTextualMaster {

	Partida p;
	LNMaster lnm;

	
	/*
	 * esto será para la interfaz textual, que tendrá la ln de master  y una partida
	 * 
	 * */
	public PartidaTextualMaster(Partida p,LNMaster m) {
		this.p=p;
		this.lnm=m;
	}
	
	
	
	public  void mostrarInterfaz() {
		
		
		System.out.println("Bienvenido a la partida, esperando a que la partida sea iniciada(para ello diga iniciar).");
		
		
		
	}

}
