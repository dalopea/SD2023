package ModeloDominio;

import java.util.*;

public class Tablero {

	List<Casilla> mapa=new ArrayList<>();
	
	
	public Tablero(int ancho,int largo) {
		
		for(int i=1;i<=largo;i++) {
			for(int j=1;j<ancho;j++) {
				mapa.add(new Casilla(j,i));
			}
		}
		
		
	}
	
	
}
