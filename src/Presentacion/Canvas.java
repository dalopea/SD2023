package Presentacion;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;



public class Canvas extends JPanel {
	
	
	public Canvas() {
		this.setBounds(0,0,100,100);
		this.setBackground(Color.blue);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.fillRect(0, 0, 50, 50);
	}
	
	
}

