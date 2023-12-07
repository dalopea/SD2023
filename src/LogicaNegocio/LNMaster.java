package LogicaNegocio;


import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ModeloDominio.*;

/*
 * La clase LNMaster tiene la funcionalidad del "Máster" de una partida de rol. Podrá crear la partida, introducir los monstruos,
 * alterar el campo, ver toda la información del tablero...
 */

public class LNMaster extends LNJugadorBase {

	private Master master; 
	private Partida partida;
	
	public LNMaster(Master m) {
		this.master=m;
	}
	
	/*
	 * El método crearPartida() se conecta con el servidor de la aplicación para incluir una nueva partida (en un futuro, se 
	 * podrá incluir una partida importada desde un documento xml). Tras dar los datos de la partida al servidor (nombre y número de puerto),
	 * se desconecta del servidor y devuelve el objeto de tipo Partida creado.
	 */
	public Partida crearPartida() {
		Partida p = null;
		try(Socket s=new Socket("localhost",55555);
			ObjectOutputStream oos = new ObjectOutputStream (s.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
			BufferedReader bf=new BufferedReader(new InputStreamReader(System.in)) ) {
			
			//TODO: Aquí es donde debería dar la opción de importar partida desde un xml, creo yo
			boolean creada = false;	
			while(!creada) {
					p = new Partida();
					System.out.println("Introduzca nombre de la partida");
					String nombrePartida=bf.readLine();
					p.setNombrePartida(nombrePartida);
					p.setPuertoPartida(s.getLocalPort());
					oos.writeBytes("Nueva partida\n");
					oos.writeBytes("Nombre:" + master.getNombreUsuario() + "\n");
					oos.writeBytes("Nombre:" + nombrePartida+"\n");
					oos.flush();
					String respuesta = ois.readLine();
					if (!respuesta.startsWith("ERROR")) {
						creada = true;
						oos.writeBytes("Desconectar\n");
						oos.flush();
					}
					System.out.println(respuesta);
				}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return p;
	}
	
	public void iniciarPartida() {
		ExecutorService poolJugadores = Executors.newCachedThreadPool();
		try(ServerSocket ss = new ServerSocket(partida.getPuertoPartida())){
			while(true) {
				try {
					Socket s = ss.accept();
					poolJugadores.execute(new HiloJugadorPartida(s,partida));
				}
				catch(IOException e) {
					e.printStackTrace();
				}
				
				
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			poolJugadores.shutdown();
		}
	}
	
	
	
	
}
