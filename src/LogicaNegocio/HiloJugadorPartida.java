package LogicaNegocio;

import java.net.Socket;

import ModeloDominio.Partida;

public class HiloJugadorPartida implements Runnable{

	private Socket s;
	private Partida partida;
	
	public HiloJugadorPartida(Socket s, Partida partida) {
		this.s = s;
		this.partida = partida;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
