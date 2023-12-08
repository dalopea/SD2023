package LogicaNegocio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;


import ModeloDominio.Partida;

public class HiloEscritorJugador extends Thread{

	private Socket s;
	private Partida p;
	
	
	public HiloEscritorJugador(Socket s, Partida partida) {
		this.s = s;
		this.p = partida;
	}
	
	
	@Override
	public void run() {
		try (ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
			String linea;
			while (!(linea = br.readLine()).equals("Desconectar")) {
				oos.writeBytes(linea + "\n");
				oos.flush();
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				s.close();
			}
			catch(IOException e) {
				
			}
		}
	}

}
