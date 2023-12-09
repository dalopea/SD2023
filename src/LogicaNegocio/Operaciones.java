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

	public boolean comprobarMovimiento(Personaje personaje, Casilla casillaOrigen, Casilla casillaDestino) {
		int movimientoPersonaje = personaje.getMovimiento();
		int[] coordenadasIniciales = casillaOrigen.getCoordenadas();
		int[] coordenadasFinales = casillaDestino.getCoordenadas();
		
		if(Math.abs(coordenadasFinales[0]-coordenadasIniciales[0])<=movimientoPersonaje && Math.abs(coordenadasFinales[1]-coordenadasIniciales[1])<=movimientoPersonaje) {
			return true;
		}
		return false;
	}
	
	public boolean moverPersonaje(JugadorBase jugador, Personaje personaje, Casilla casillaOrigen, Casilla casillaDestino) {
		if (jugador.getPartida().getPersonajesManejables().contains(personaje)) {
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
	
	public void aniadirPersonaje(JugadorBase jugador,Personaje personaje) {
		jugador.getPartida().getPersonajes().add(personaje);
		if (personaje.getPropietario().equals(jugador) || jugador.getPartida().getMaster().equals(jugador)) {
			jugador.getPartida().getPersonajesManejables().add(personaje);
		}
	}
	
	public boolean nuevoPersonaje(JugadorBase jugador, int jugadorEnLista, String nombre, int PA, int PD, int PVM, int mov, String imgPath) {
		try{
			Personaje personaje = new Personaje(jugador.getPartida().getJugadores().get(jugadorEnLista),nombre,PA,PD,PVM,mov,imgPath);
			aniadirPersonaje(jugador, personaje);
			return true;
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
	
	
	public void modificarImagenPersonaje(JugadorBase jugador, int posicionPersonaje, String path) {
		jugador.getPartida().getPersonajes().get(posicionPersonaje).setImagen(path);
	}
	
	public void atacarPersonaje(JugadorBase jugador, int personajeAtacante, int personajeDefensor0) {
		Personaje personajeAtacante = jugador.getPartida().getPersonajes()
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
}
