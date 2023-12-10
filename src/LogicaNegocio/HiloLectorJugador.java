package LogicaNegocio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

import javax.swing.JTextArea;

import ModeloDominio.Partida;
import ModeloDominio.Personaje;
import Presentacion.PartidaJugador;
import Presentacion.PartidaMaster;

public class HiloLectorJugador extends Thread{

	private Socket s;
	private JTextArea txtLeer;
	private LNJugador logicaJ;
	
	public HiloLectorJugador(Socket s,LNJugador l) {
		this.s = s;
		this.logicaJ=l;
	}
	
	public void setTextArea( JTextArea txtLeer) {
		this.txtLeer=txtLeer;
	}
	
	@Override
	public void run() {
		try(ObjectInputStream ois = new ObjectInputStream(s.getInputStream())){
			String linea;
			Partida partida;
			boolean desconectar = false;
			while((linea = ois.readLine())!= null) {

				if (linea.startsWith("Partida")) {
					this.logicaJ.setP((Partida) ois.readObject());
					System.out.println(this.logicaJ.getP());
					continue;
				}
				if (linea.startsWith("/ROL21/")){
					String peticion = linea.substring(7);
					String[] partesPeticion = peticion.split("\\?");
					if (partesPeticion[0].equals("Colocar")) {
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
						List<Personaje> personajes=logicaJ.getPartida().getPersonajes();
						Personaje j=null;
						for(Personaje p:personajes) {
							if(nombrePersonaje.equals(p.getNombrePersonaje())) {
								j=p;
							}
							
						}
						
						
						
						PartidaJugador.ColocaFicha(j, logicaJ.getPartida().getTablero().getCasilla(coordenadas[0], coordenadas[1]));
						//Llamar a método de la gráfica.
					}
					/*
					 * Esto no resultaria muy dificil, pero da una complejidad que no merece la pena en un trabajo de estas dimensiones.
					 * */
//					else if (partesPeticion[0].equals("Atacar")) {
//						String nombreAtacante = null;
//						String nombreDefensor = null;
//						int modificador = 0;
//						String[] argumentos = partesPeticion[1].split("&");
//						for (String argumento : argumentos) {
//							String[] nombreValor = argumento.split("=");
//							if (nombreValor[0].equals("Atacante")) {
//								nombreAtacante = nombreValor[1];
//							}
//							if (nombreValor[0].equals("Defensor")) {
//								nombreDefensor = nombreValor[1];
//							}
//							if (nombreValor[0].equals("Modificador")) {
//								modificador = Integer.parseInt(nombreValor[1]);
//							}
//						}
//						//
//					}
					else if (partesPeticion[0].equals("Map")) {
						String nombreImagen = null;
						String[] argumentos = partesPeticion[1].split("&");
						for (String argumento : argumentos) {
							String[] nombreValor = argumento.split("=");
							if (nombreValor[0].equals("Imagen")) {
								nombreImagen = nombreValor[1];
							}
						}
						PartidaJugador.setFondoMapa("src/images/mapas/"+nombreImagen);
					}
					else if (partesPeticion[0].equals("Crear")) {
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
						
						Personaje personaje = new Personaje(nombrePersonaje,puntosAtaque,puntosDefensa,puntosVida,movimiento,imagen);
						PartidaJugador.CrearFicha(personaje);
						
					}
					else if (partesPeticion[0].equals("Eliminar")) {
						String nombrePersonaje = "";
						String[] argumentos = partesPeticion[1].split("&");
						for (String argumento : argumentos) {
							String[] nombreValor = argumento.split("=");
							if (nombreValor[0].equals("Nombre")) {
								nombrePersonaje = nombreValor[1];
							}
						}
						PartidaJugador.eliminarFichaMapa(nombrePersonaje);
					}
					else if (partesPeticion[0].equals("Vida")){
						String nombrePersonaje = "";
						int pv = 0;
						String[] argumentos = partesPeticion[1].split("&");
						for (String argumento : argumentos) {
							String[] nombreValor = argumento.split("=");
							if (nombreValor[0].equals("Personaje")) {
								nombrePersonaje = nombreValor[1];
							}
							if (nombreValor[0].equals("Vida")) {
								pv = Integer.parseInt(nombreValor[1]);
							}
						}
						PartidaJugador.cambiarVida(nombrePersonaje,pv);
					}
					else if (partesPeticion[0].equals("AlterarDisponible")) {
						int[] coordenadas = new int[2];
						String[] argumentos = partesPeticion[1].split("&");
						for (String argumento : argumentos) {
							String[] nombreValor = argumento.split("=");
							if (nombreValor[0].equals("Coords")) {
								String coordeanas=nombreValor[1].substring(1,nombreValor[1].length()-1);
								String[] coordsSeparadas=coordeanas.split(",");
								coordenadas[0] = Integer.valueOf(coordsSeparadas[0]);
								coordenadas[1] = Integer.valueOf(coordsSeparadas[1]);
							}
						}
						//PartidaJugador.alterarDisponible(coordenadas);
					}
					else if (partesPeticion[0].equals("Desconectar")) {
						desconectar = true;
						PartidaJugador.cerrar();
					}
				}else {
				this.txtLeer.append(linea+"\n");
				}
				if (desconectar) {
					break;
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
