package LogicaNegocio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class HiloLectorJugador implements Runnable{

	private Socket s;
	
	public HiloLectorJugador(Socket s) {
		this.s = s;
	}
	
	
	@Override
	public void run() {
		try(ObjectInputStream ois = new ObjectInputStream(s.getInputStream())){
			String linea;
			while((linea = ois.readLine())!= null) {
				System.out.println(linea);
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
				e.printStackTrace();
			}
		}
		
	}

}
