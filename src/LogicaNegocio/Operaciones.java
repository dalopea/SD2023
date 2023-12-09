package LogicaNegocio;

import java.util.List;

import ModeloDominio.Casilla;
import ModeloDominio.JugadorBase;
import ModeloDominio.Master;
import ModeloDominio.Personaje;

/*
 * Esta clase sirve de interfaz para las operaciones que pueden realizar los diferentes jugadores (y el máster). Cuando alguien realiza algún cambio en la partida (movimiento,
 * ataque, creación de criatura...) la información se transmite en forma de mensaje a cada participante. Tras esto, en función del tipo de mensaje, se invocará una de estas 
 * operaciones (y posteriormente, si es necesario, una de las operaciones de la capa de presentación).
 */
public class Operaciones {

	/*
	 * El método colocarPersonaje coloca un personaje en una casilla, siempre que este personaje esté en la lista de personajes de la partida y la casilla esté desocupada y
	 * disponible.
	 */
	public boolean colocarPersonaje(JugadorBase jugador, String nombrePersonaje, Casilla casilla) {
		List<Personaje> personajes = jugador.getPartida().getPersonajes();
		Personaje personaje = null;
		for (Personaje p : personajes) {
			if (p.getNombrePersonaje().equals(nombrePersonaje)) {
				personaje = p;
			}
		}
		if (casilla.getPersonaje() == null && casilla.isDisponible() && personaje != null) {
			casilla.setPersonaje(personaje);
			personaje.setPosicion(casilla);
			return true;
		}
		return false;
	}
	
	/*
	 * El método comprobarMovimiento devuelve true si la casilla destino está a menor o igual movimiento (en diagonal, horizontal o vertical) que la casilla origen para ese 
	 * personaje.
	 */
	public boolean comprobarMovimiento(Personaje personaje, Casilla casillaOrigen, Casilla casillaDestino) {
		int movimientoPersonaje = personaje.getMovimiento();
		int[] coordenadasIniciales = casillaOrigen.getCoordenadas();
		int[] coordenadasFinales = casillaDestino.getCoordenadas();
		
		if(Math.abs(coordenadasFinales[0]-coordenadasIniciales[0])<=movimientoPersonaje && Math.abs(coordenadasFinales[1]-coordenadasIniciales[1])<=movimientoPersonaje) {
			return true;
		}
		return false;
	}
	
	/*
	 * El método mover mueve a un personaje a la casillaDestino desde la casillaOrigen. Esto implica cambiar el personaje de la casilla original a null, cambiar el personaje de
	 * la casilla destino al personaje en cuestión y cambiar la posición del personaje a la casilla destino.
	 */
	public boolean moverPersonaje(JugadorBase jugador, Personaje personaje, Casilla casillaOrigen, Casilla casillaDestino) {
		if (jugador.getPartida().getPersonajesManejables().contains(personaje)) {
			if (casillaDestino.getPersonaje() == null && casillaDestino.isDisponible()) {
				if (comprobarMovimiento(personaje,casillaOrigen,casillaDestino)) {
					if (casillaOrigen.getPersonaje().equals(personaje)) {
						casillaOrigen.setPersonaje(null);
						casillaDestino.setPersonaje(personaje);
						personaje.setPosicion(casillaDestino);
						return true;
					}
					else {
						return false;
					}
				}
				else {
					return false;
				}
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}

	}
	
	/*
	 * Añade el personaje a la lista de personajes disponibles. Además, si el jugador es el propietario de la criatura o el máster, 
	 * se le añadirá a la lista de personajes manejables.
	 */
	public void aniadirPersonaje(JugadorBase jugador,Personaje personaje) {
		jugador.getPartida().getPersonajes().add(personaje);
		if (personaje.getPropietario().equals(jugador) || jugador.getPartida().getMaster().equals(jugador)) {
			jugador.getPartida().getPersonajesManejables().add(personaje);
		}
	}
	
	/*
	 * 
	 */
	public boolean nuevoPersonaje(JugadorBase jugador, int jugadorEnLista, String nombre, int PA, int PD, int PVM, int mov, String imgPath) {
		try{
			if (!existePersonaje(jugador,nombre)){
				Personaje personaje = new Personaje(jugador.getPartida().getJugadores().get(jugadorEnLista),nombre,PA,PD,PVM,mov,imgPath);
				aniadirPersonaje(jugador, personaje);
				return true;
			}
			else {
				return false;
			}
			
		}
		catch(Exception e) {
			return false;
		}
	}
	
	public void modificarVidaPersonaje(JugadorBase jugador, String nombrePersonaje, int vidaActual) {
		List<Personaje> personajes = jugador.getPartida().getPersonajes();
		for (Personaje p : personajes) {
			if (p.getNombrePersonaje().equals(nombrePersonaje)) {
				p.setPuntosVidaActuales(vidaActual);
			}
		}
	}
	
	public void modificarAtaquePersonaje(JugadorBase jugador, String nombrePersonaje, int puntosAtaque) {
		List<Personaje> personajes = jugador.getPartida().getPersonajes();
		for (Personaje p : personajes) {
			if (p.getNombrePersonaje().equals(nombrePersonaje)) {
				p.setPuntosAtaque(puntosAtaque);
			}
		}
	}
	
	public void modificarDefensaPersonaje(JugadorBase jugador, String nombrePersonaje, int puntosDefensa) {
		List<Personaje> personajes = jugador.getPartida().getPersonajes();
		for (Personaje p : personajes) {
			if (p.getNombrePersonaje().equals(nombrePersonaje)) {
				p.setPuntosDefensa(puntosDefensa);
			}
		}
	}
	
	public void modificarMovimientoPersonaje(JugadorBase jugador, String nombrePersonaje, int movimiento) {
		List<Personaje> personajes = jugador.getPartida().getPersonajes();
		for (Personaje p : personajes) {
			if (p.getNombrePersonaje().equals(nombrePersonaje)) {
				p.setMovimiento(movimiento);
			}
		}
	}
	
	public void modificarNombrePersonaje(JugadorBase jugador, String nombrePersonaje, String nombre) {
		List<Personaje> personajes = jugador.getPartida().getPersonajes();
		boolean existe = existePersonaje(jugador,nombre);
		
		if (!existe) {
			for (Personaje p : personajes) {
				if (p.getNombrePersonaje().equals(nombrePersonaje)) {
					p.setNombrePersonaje(nombrePersonaje);
				}
			}
		}

	}
	
	
	public void modificarImagenPersonaje(JugadorBase jugador, String nombrePersonaje, String path) {
		List<Personaje> personajes = jugador.getPartida().getPersonajes();
		for (Personaje p : personajes) {
			if (p.getNombrePersonaje().equals(nombrePersonaje)) {
				p.setImagen(path);
			}
		}
	}
	
	public void atacarPersonaje(JugadorBase jugador, String nombrePersonajeAtacante, String nombrePersonajeDefensor) {
		List<Personaje> personajes = jugador.getPartida().getPersonajes();
		Personaje atacante = null;
		Personaje defensor = null;
		for (Personaje p : personajes) {
			if (p.getNombrePersonaje().equals(nombrePersonajeAtacante)) {
				atacante = p;
			}
			else if (p.getNombrePersonaje().equals(nombrePersonajeDefensor)) {
				defensor = p;
			}
		}
		
		if (atacante != null && defensor != null) {
			if(comprobarAdyacencia(atacante,defensor)) {
				if (atacante.getPuntosAtaque() >= defensor.getPuntosDefensa()) {
					modificarVidaPersonaje(jugador,nombrePersonajeDefensor,defensor.getPuntosVidaActuales()-(atacante.getPuntosAtaque() - defensor.getPuntosDefensa()));
				}
			}
		}
	}
	
	public boolean existePersonaje (JugadorBase jugador, String nombrePersonaje) {
		List<Personaje> personajes = jugador.getPartida().getPersonajes();
		for (Personaje p : personajes) {
			if (p.getNombrePersonaje().equals(nombrePersonaje)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean comprobarAdyacencia(Personaje personaje1, Personaje personaje2) {
		Casilla casilla1 = personaje1.getPosicion();
		Casilla casilla2 = personaje2.getPosicion();
		if (casilla1.getCoordenadas()[0] < casilla2.getCoordenadas()[0] - 1 || casilla1.getCoordenadas()[0] > casilla2.getCoordenadas()[0]+1) {
			return false;
		}
		else if (casilla1.getCoordenadas()[1] < casilla2.getCoordenadas()[1] - 1 || casilla1.getCoordenadas()[1] > casilla2.getCoordenadas()[1]+1){
			return false;
		}
		return true;
	}
}
