package LogicaNegocio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import ModeloDominio.Partida;
import Presentacion.PartidaMaster;

public class HiloLectorJugador extends Thread{

	private Socket s;
	private Partida p;
	
	public HiloLectorJugador(Socket s, Partida p) {
		this.s = s;
		this.p = p;
	}
	
	
	@Override
	public void run() {
		try(ObjectInputStream ois = new ObjectInputStream(s.getInputStream())){
			String linea;
			Partida partida;
			while((linea = ois.readLine())!= null) {
				PartidaMaster.addText(linea);
				System.out.println(linea);
				if (linea.startsWith("Partida")) {
					this.p = (Partida) ois.readObject();
					System.out.println(this.p.getNombrePartida());
				}
			}
		}
		catch(IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			try {
				s.close();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
