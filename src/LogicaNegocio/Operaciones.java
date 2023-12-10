package LogicaNegocio;

import java.util.List;

import ModeloDominio.Casilla;
import ModeloDominio.Jugador;
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
	public static boolean colocarPersonaje(LNJugadorBase logica, String nombrePersonaje, Casilla casilla) {
		List<Personaje> personajes = logica.getP().getPersonajes();
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
	public boolean moverPersonaje(LNJugadorBase logica, Personaje personaje, Casilla casillaOrigen, Casilla casillaDestino) {
		if (logica.getP().getPersonajes().contains(personaje)) {
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
	 * 
	 */
	public static boolean nuevoPersonaje(LNJugadorBase logica, Personaje p) {
		try{
			if (!existePersonaje(logica,p.getNombrePersonaje())){
				logica.getP().getPersonajes().add(p);
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
	
	public void modificarVidaPersonaje(LNJugadorBase logica, String nombrePersonaje, int vidaActual) {
		List<Personaje> personajes = logica.getP().getPersonajes();
		for (Personaje p : personajes) {
			if (p.getNombrePersonaje().equals(nombrePersonaje)) {
				p.setPuntosVidaActuales(vidaActual);
			}
		}
	}
	
	public void modificarAtaquePersonaje(LNJugadorBase logica, String nombrePersonaje, int puntosAtaque) {
		List<Personaje> personajes = logica.getP().getPersonajes();
		for (Personaje p : personajes) {
			if (p.getNombrePersonaje().equals(nombrePersonaje)) {
				p.setPuntosAtaque(puntosAtaque);
			}
		}
	}
	
	public void modificarDefensaPersonaje(LNJugadorBase logica, String nombrePersonaje, int puntosDefensa) {
		List<Personaje> personajes = logica.getP().getPersonajes();
		for (Personaje p : personajes) {
			if (p.getNombrePersonaje().equals(nombrePersonaje)) {
				p.setPuntosDefensa(puntosDefensa);
			}
		}
	}
	
	public void modificarMovimientoPersonaje(LNJugadorBase logica, String nombrePersonaje, int movimiento) {
		List<Personaje> personajes = logica.getP().getPersonajes();
		for (Personaje p : personajes) {
			if (p.getNombrePersonaje().equals(nombrePersonaje)) {
				p.setMovimiento(movimiento);
			}
		}
	}
	
	public void modificarNombrePersonaje(LNJugadorBase logica, String nombrePersonaje, String nombre) {
		List<Personaje> personajes = logica.getP().getPersonajes();
		boolean existe = existePersonaje(logica,nombre);
		
		if (!existe) {
			for (Personaje p : personajes) {
				if (p.getNombrePersonaje().equals(nombrePersonaje)) {
					p.setNombrePersonaje(nombrePersonaje);
				}
			}
		}

	}
	
	
	public void modificarImagenPersonaje(LNJugadorBase logica, String nombrePersonaje, String path) {
		List<Personaje> personajes = logica.getP().getPersonajes();
		for (Personaje p : personajes) {
			if (p.getNombrePersonaje().equals(nombrePersonaje)) {
				p.setImagen(path);
			}
		}
	}
	
	/*
	 * Este metodo no se utiliza ya que no puedo llegar a la versión final por tiempo y para evitar demasiada extensión.
	 * 
	 * */
	public void atacarPersonaje(LNJugadorBase logica, String nombrePersonajeAtacante, String nombrePersonajeDefensor,int modificador) {
		List<Personaje> personajes = logica.getP().getPersonajes();
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
				if (atacante.getPuntosAtaque() + modificador >= defensor.getPuntosDefensa()) {
					modificarVidaPersonaje(logica,nombrePersonajeDefensor,defensor.getPuntosVidaActuales()-(atacante.getPuntosAtaque()+modificador - defensor.getPuntosDefensa()));
				}
			}
		}
	}
	
	public static boolean existePersonaje (LNJugadorBase logica, String nombrePersonaje) {
		List<Personaje> personajes = logica.getP().getPersonajes();
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
	
	public static void eliminarPersonajeDeCasilla(Personaje personaje) {
		personaje.getPosicion().setPersonaje(null);
		personaje.setPosicion(null);
	}
}
