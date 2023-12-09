package LogicaNegocio;

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
	
	public void modificarVidaPersonaje(JugadorBase jugador, int posicionPersonaje, int vidaActual) {
		jugador.getPartida().getPersonajes().get(posicionPersonaje).setPuntosVidaActuales(vidaActual);
	}
	
	public void modificarAtaquePersonaje(JugadorBase jugador, int posicionPersonaje, int puntosAtaque) {
		jugador.getPartida().getPersonajes().get(posicionPersonaje).setPuntosAtaque(puntosAtaque);
	}
	
	public void modificarDefensaPersonaje(JugadorBase jugador, int posicionPersonaje, int puntosDefensa) {
		jugador.getPartida().getPersonajes().get(posicionPersonaje).setPuntosDefensa(puntosDefensa);
	}
	
	public void modificarMovimientoPersonaje(JugadorBase jugador, int posicionPersonaje, int movimiento) {
		jugador.getPartida().getPersonajes().get(posicionPersonaje).setMovimiento(movimiento);
	}
	
	public void modificarNombrePersonaje(JugadorBase jugador, int posicionPersonaje, String nombre) {
		jugador.getPartida().getPersonajes().get(posicionPersonaje).setNombrePersonaje(nombre);
	}
	
	public void modificarImagenPersonaje(JugadorBase jugador, int posicionPersonaje, String path) {
		jugador.getPartida().getPersonajes().get(posicionPersonaje).setImagen(path);
	}
	
	public void atacarPersonaje(JugadorBase jugador, int personajeAtacante, int personajeDefensor0) {
		Personaje personajeAtacante = jugador.getPartida().getPersonajes()
	}
}
