package Presentacion;

import java.awt.BasicStroke;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;

/*
 * Esta clase hace de canvas para poder dibujar la malla del mapa de la partida, de tal forma que podamos tener la cantidad de casillas y su tamaño.
 * */

public class Canvas extends JPanel {
	
	private int gridRows;
	private int gridColumns;
	private int gridCellSize;
	
	
	public Canvas(JPanel placeholder,int col) {

		this.setBounds(placeholder.getBounds());
		this.setBackground(placeholder.getBackground());
		this.setBorder(placeholder.getBorder());
		
		
		gridColumns=col;
		gridCellSize=this.getBounds().width/gridColumns;
		gridRows =this.getBounds().height/gridCellSize;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		for(int y=0;y<gridRows;y++) {
			
			for(int x=0;x<gridColumns;x++) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setStroke(new BasicStroke(5));
				g2.drawRect(x*gridCellSize, y*gridCellSize, gridCellSize, gridCellSize);
			}
			
		}
	}
	
	public int getCellSize() {
		return this.gridCellSize;
	}
	public int getCols() {
		return this.gridColumns;
	}
	public int getRows() {
		return this.gridRows;
	}

	
}

