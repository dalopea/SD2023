package LogicaNegocio;


import java.io.*;

import java.net.*;

import ModeloDominio.*;

public class LNMaster extends LNJugadorBase {

	JugadorBase master;
	ServerSocket servPartida;/*Esto deberia estar aqui o en master*/
	
	public LNMaster(JugadorBase m) {
		this.master=m;
	}
	
	public void crearPartida() {
		try(Socket s=new Socket("localhost",55555);
			BufferedReader bfser=new BufferedReader(new InputStreamReader(s.getInputStream()));
			PrintWriter pw=new PrintWriter(s.getOutputStream());
			BufferedReader bf=new BufferedReader(new InputStreamReader(System.in)) ) {
			
			System.out.println("Introduzca nombre de la partida");
			String partida=bf.readLine();
			//Sería así? Enviar la partida y el puerto de este socket?
			pw.println("Crear,"+partida+","+s.getLocalPort());
			pw.flush();
			/*
			 * Esto me da error por que el puerto ya lo está usando para conectarse al server.
			 * */
			this.servPartida=new ServerSocket(s.getLocalPort());
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void 
	
	
}
