package LogicaNegocio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import javax.swing.JTextArea;

import ModeloDominio.Personaje;
import Presentacion.PartidaMaster;

/*
 * Este hilo es invocado por la lógica de negocio del máster, siendo el que se encarga de comunicarse con un Jugador en específico.
 * Entre sus atributos tiene un ObjectInputStream y un ObjectOutputStream, que permiten la comunicación con el jugador, así como una lista de todos los
 * HiloJugadorPartida creados, para poder recorrerla al hacer broadcast (mensaje a todos los jugadores).
 * 
 * El máster puede recibir, y enviar, dos tipos de mensajes:
 *  1-> Mensajes normales que llegarán a los Jugadores, mostrándose en el chat.
 *  2 -> Mensajes de gestión o control (aquellos que empiezan por /Rol21/), que se utilizan para sincronizar la partida.
 * 
 * Cuando el Máster recibe un mensaje de control de un Jugador, invoca al método correspondiente de la clase PartidaMáster con los datos obtenidos de los parámetros de
 * la petición.
 * 
 * Por ejemplo, una posible petición es:
 * 
 * 														/ROL21/Mover?Personaje=nombre&Coords=[x,y]
 * 
 * De donde podemos obtener el nombre del personaje que se está moviendo y las coordenadas de la casilla a la que se quiere mover.
 *
 */
public class HiloJugadorPartida implements Runnable{

	private Socket s;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private List<HiloJugadorPartida> hilosJugadores;
	private JTextArea txtLeer;
	private LNMaster logicaM;
	private CyclicBarrier barrera;
	
	
	public HiloJugadorPartida(Socket s,List<HiloJugadorPartida> hilosJugadores,LNMaster l,CyclicBarrier barrera) {
		
		try{
			this.s = s;
			this.oos = new ObjectOutputStream(s.getOutputStream());
			this.ois = new ObjectInputStream(s.getInputStream());
			this.hilosJugadores = hilosJugadores;
			hilosJugadores.add(this);
			this.logicaM=l;
			this.barrera = barrera;
		}
		catch(IOException e) {
			cerrarTodo();
		}
		
		
		
	}
	public void setTxtArea(JTextArea txtLeer) {
		this.txtLeer=txtLeer;
	}
	
	
	public void enviarATodos(String mensaje) {
		try {
			//TODO: check
			oos.writeBytes(mensaje+"\n");
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void run() {
		String mensaje;
		try {
			String nombreJugador = ois.readLine();
			System.out.println(nombreJugador);
			this.logicaM.getP().addPlayer(nombreJugador);
			barrera.await();
			oos.writeBytes("Partida\n");
			oos.writeObject(logicaM.getP());
			oos.flush();
			mensaje = ois.readLine();
			while (mensaje != null) {
				//TODO: check
				if (mensaje.startsWith("/ROL21/")){
					String peticion = mensaje.substring(7);
					String[] partesPeticion = peticion.split("\\?");
					if (partesPeticion[0].equals("Crear")) {
						String nombrePersonaje = null;
						int puntosAtaque = 0;
						int puntosDefensa = 0;
						int movimiento = 0;
						int puntosVida = 0;
						String imagen = null;
						String[] argumentos = partesPeticion[1].split("&");
						for (String argumento : argumentos) {
							String[] nombreValor = argumento.split("=");
							if (nombreValor[0].equals("Nombre")) {
								nombrePersonaje = nombreValor[1];
							}
							if (nombreValor[0].equals("Ataque")) {
								puntosAtaque = Integer.parseInt(nombreValor[1]);
							}
							if (nombreValor[0].equals("Defensa")) {
								puntosDefensa = Integer.parseInt(nombreValor[1]);
							}
							if (nombreValor[0].equals("Movimiento")) {
								movimiento = Integer.parseInt(nombreValor[1]);
							}
							if (nombreValor[0].equals("Vida")) {
								puntosVida = Integer.parseInt(nombreValor[1]);
							}
							if (nombreValor[0].equals("Imagen")) {
								imagen = nombreValor[1];
							}
						}
						
						PartidaMaster.addPersonajeJugador(nombrePersonaje, puntosAtaque, puntosDefensa, puntosVida, movimiento, imagen);
						
					}else if (partesPeticion[0].equals("Colocar")) {
						String nombrePersonaje = null;
						int[] coordenadas = new int[2];
						String[] argumentos = partesPeticion[1].split("&");
						for (String argumento : argumentos) {
							String[] nombreValor = argumento.split("=");
							if (nombreValor[0].equals("Personaje")) {
								nombrePersonaje = nombreValor[1];
							}
							if (nombreValor[0].equals("Coords")) {
								String coordeanas=nombreValor[1].substring(1,nombreValor[1].length()-1);
								String[] coordsSeparadas=coordeanas.split(",");
								coordenadas[0] = Integer.valueOf(coordsSeparadas[0]);
								coordenadas[1] = Integer.valueOf(coordsSeparadas[1]);
							}
						}
						List<Personaje> personajes=logicaM.getPartida().getPersonajes();
						Personaje j=null;
						for(Personaje p:personajes) {
							if(nombrePersonaje.equals(p.getNombrePersonaje())) {
								j=p;
							}
							
						}
						PartidaMaster.colocaPersonajeJugador(j, coordenadas[0], coordenadas[1]);
					}
					else if (partesPeticion[0].equals("Mover")) {
						String nombrePersonaje = null;
						int[] coordenadas = new int[2];
						String[] argumentos = partesPeticion[1].split("&");
						for (String argumento : argumentos) {
							String[] nombreValor = argumento.split("=");
							if (nombreValor[0].equals("Personaje")) {
								nombrePersonaje = nombreValor[1];
							}
							if (nombreValor[0].equals("Coords")) {
								String coordeanas=nombreValor[1].substring(1,nombreValor[1].length()-1);
								String[] coordsSeparadas=coordeanas.split(",");
								coordenadas[0] = Integer.valueOf(coordsSeparadas[0]);
								coordenadas[1] = Integer.valueOf(coordsSeparadas[1]);
							}
						}
						List<Personaje> personajes=logicaM.getPartida().getPersonajes();
						Personaje j=null;
						for(Personaje p:personajes) {
							if(nombrePersonaje.equals(p.getNombrePersonaje())) {
								j=p;
							}
							
						}
						PartidaMaster.moverPersonajeJugador(j, coordenadas[0], coordenadas[1]);
					}
					
					
				}else {
				mensajeBroadcast(mensaje+"\n");
				}
				mensaje = ois.readLine();
			}
		}
		catch(IOException | InterruptedException | BrokenBarrierException e) {
			
		}
		finally {
			cerrarTodo();
			System.out.println("Cerrando este hilo");
		}
		
		
	}
	
	public void mensajeBroadcast(String mensaje) {
		for (HiloJugadorPartida hilo : this.hilosJugadores) {
			try {
				if (!hilo.equals(this)) {
					hilo.oos.writeBytes(mensaje + "\n");
					hilo.oos.flush();
				}
			}
			catch(IOException e) {
				cerrarTodo();
			}
			
		}
		
		synchronized(this.txtLeer) {
			this.txtLeer.append(mensaje);
		}
		
	}
	
	public void eliminarHiloJugador() {
		hilosJugadores.remove(this);
	}
	
	
	public void cerrarTodo() {
		eliminarHiloJugador();
		try {
			if (ois != null) {
				ois.close();
			}
			if (oos != null) {
				oos.close();
			}
			if (s!= null) {
				s.close();
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

}
