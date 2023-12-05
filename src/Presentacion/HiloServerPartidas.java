package Presentacion;

import java.io.*;
import java.net.*;
import java.util.*;

import ModeloDominio.ManejadorPartidas;
//Comentario
public class HiloServerPartidas extends Thread {

	private Socket s;
	ManejadorPartidas manejadorPartidas;
	
	
	public HiloServerPartidas(Socket s,ManejadorPartidas manejadorPartidas) {
		this.s=s;
		this.manejadorPartidas = manejadorPartidas;
	}
	
	
	public void run() {
			try(BufferedReader bf=new BufferedReader(new InputStreamReader(s.getInputStream()));
					PrintWriter pw=new PrintWriter(s.getOutputStream())){
				String peticion=bf.readLine();
				
				
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
