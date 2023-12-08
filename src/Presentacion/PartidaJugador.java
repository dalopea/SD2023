package Presentacion;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import LogicaNegocio.*;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.GridLayout;
import java.net.*;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PartidaJugador extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtEscribir;
	private JTextArea txtLeer;
	private Socket s;
	private LNJugador logica;
	
	
	
	
	public PartidaJugador(Socket s,LNJugadorBase log) {
		
		this.s=s;
		this.logica=(LNJugador) log;
		logica.unirseAPartida(s);
		
		
		setPreferredSize(new Dimension(1800, 1000));
		setResizable(false);
		setSize(new Dimension(1800, 1000));
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel Mapa = new JPanel();
		Mapa.setBounds(225, 25, 1200, 900);
		contentPane.add(Mapa);
		Mapa.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel Chat = new JPanel();
		Chat.setBounds(1450, 25, 300, 900);
		contentPane.add(Chat);
		Chat.setLayout(null);
		
		 txtLeer = new JTextArea();
		txtLeer.setLineWrap(true);
		txtLeer.setEditable(false);
		txtLeer.setBounds(0, 0, 300, 870);
		Chat.add(txtLeer);
		
		txtEscribir = new JTextField();
		txtEscribir.setBounds(0, 870, 300, 30);
		Chat.add(txtEscribir);
		txtEscribir.setColumns(10);
	}

}