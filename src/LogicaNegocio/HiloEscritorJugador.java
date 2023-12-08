package LogicaNegocio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HiloEscritorJugador implements Runnable{

	private Socket s;
	
	
	public HiloEscritorJugador(Socket s) {
		this.s = s;
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
