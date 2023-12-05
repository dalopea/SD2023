package ModeloDominio;

public class Casilla {

	private int x;
	private int y;
	
	private boolean ocupada;//ocupada hace referncia a si hay alguien o algo en la casilla
	private boolean disponible;//disponible hace referencia a si se puede poner cualquier cosa
	
	
	public Casilla(int x,int y) {
		this.x=x;
		this.y=y;
		ocupada=false;
		disponible=true;
	}
	
	public int[] getCoordenadas() {
		int[] coords=new int[2];
		coords[0]=x;
		coords[1]=x;
		return coords;
	}
	
	
	
	public boolean isDisponible() {
		return disponible;
	}
	
	public boolean isOcupada() {
		return ocupada;
	}
	
	public void cambiarDisponibilidad() {
		
		if(disponible) {
			disponible=false;
		}else {
			disponible=true;
		}
	}
	
	public void cambiarOcupacion() {
		if(ocupada) {
			ocupada=false;
		}else {
			ocupada=true;
		}
	}
	
	
}
