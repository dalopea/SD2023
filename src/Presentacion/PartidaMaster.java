package Presentacion;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.net.Socket;
import java.awt.GridLayout;
import javax.swing.border.MatteBorder;

import LogicaNegocio.*;


import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Font;
import javax.swing.JTable;

public class PartidaMaster extends JFrame {

	private static final long serialVersionUID = 1L;
	private  JPanel contentPane;
	private static JTextField ChatEscribir;
	private static JTextArea ChatLeer;
	private  LNMaster logica;
	private JPanel Partida;
	private JLabel lblNewLabel;


	public static void addText(String m) {
		ChatLeer.append(m);
	}
	
	public static String getText() {
		return ChatEscribir.getText();
	}
	
	public void ManejadorChat() {
		
	}
	
	

	
	public PartidaMaster(LNJugadorBase ln) {
		setResizable(false);
		setSize(new Dimension(1800, 1000));
		
		
		this.logica=(LNMaster) ln;
		this.logica.iniciarPartida();
		
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		 Partida = new JPanel();
		Partida.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		Partida.setBounds(225, 25, 1200, 900);
		contentPane.add(Partida);
		Partida.setLayout(new GridLayout(1, 0, 0, 0));
		Partida.add(new Canvas());
		
		JPanel Chat = new JPanel();
		Chat.setBounds(1450, 25, 300, 900);
		contentPane.add(Chat);
		Chat.setLayout(null);
		
		ChatEscribir = new JTextField();
		ChatEscribir.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
				ManejadorChat();
				}
			}
		});
		ChatEscribir.setBounds(0, 870, 300, 30);
		Chat.add(ChatEscribir);
		ChatEscribir.setColumns(10);
		
		 ChatLeer = new JTextArea();
		ChatLeer.setEditable(false);
		ChatLeer.setLineWrap(true);
		ChatLeer.setBounds(0, 0, 300, 870);
		Chat.add(ChatLeer);
		
		lblNewLabel = new JLabel("Jugadores");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(26, 25, 129, 40);
		contentPane.add(lblNewLabel);

		
	}
}