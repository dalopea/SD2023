package Presentacion;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import LogicaNegocio.*;
import ModeloDominio.Casilla;
import ModeloDominio.JugadorBase;
import ModeloDominio.Personaje;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.net.*;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JSeparator;
import javax.swing.JComboBox;

public class PartidaJugador extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JPanel contentPane;
	private JTextField txtEscribir;
	private JTextArea txtLeer;
	private Socket s;
	private LNJugador logica;
	private JLabel show_Mapa;
	private JLabel lblNewLabel;
	private JList listPlayers;
	private JSeparator separator;
	private JLabel lblNewLabel_1;
	private static Canvas canvas;
	private JLabel lblNewLabel_2;
	private JTextField textField;
	private JLabel lblNewLabel_3;
	private JTextField textField_1;
	private JLabel lblNewLabel_4;
	private JTextField textField_2;
	private JLabel lblNewLabel_5;
	private JTextField textField_3;
	private JLabel lblNewLabel_6;
	private JTextField textField_4;
	private JLabel lblNewLabel_7;
	private JComboBox comboBox;
	private JButton btnNewButton;
	
	
	public static  void ColocaFicha(JugadorBase j,Personaje p,Casilla c) {
		aniadirfichaMons(p.getImagen(),p.getNombrePersonaje(),c.getCoordenadas()[0] ,c.getCoordenadas()[1]);
		Operaciones.colocarPersonaje(j, p.getNombrePersonaje(), c);	
	}
	
	public static void aniadirfichaMons(String img,String nom,int x,int y) {
		 JLabel lblIMG = new JLabel("");
		 lblIMG.setBounds(225+(x*canvas.getCellSize()),25+(y*canvas.getCellSize()), canvas.getCellSize(), canvas.getCellSize());
		setImage(lblIMG,"Mons/"+img);
		lblIMG.setName(nom);
		contentPane.add(lblIMG,0);
		contentPane.repaint();
	}
	public static void setImage(JLabel imgl,String dir) {
		BufferedImage img=null;
		try {
			File f=new File("src/images/"+dir);
			System.out.println(f.getName());
		    img = ImageIO.read(f);
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image dimg = img.getScaledInstance(imgl.getWidth(), imgl.getHeight(),Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(dimg);
		
		imgl.setIcon(imageIcon);
	}
	
	
	public void ManejadorChat() {
		this.txtLeer.append(logica.getJugador().getNombreUsuario()+": "+this.txtEscribir.getText()+"\n");
		this.logica.hiloEscritorJugador.enviarMensaje(logica.getJugador().getNombreUsuario()+": "+this.txtEscribir.getText());
		this.txtEscribir.setText("");
	}
	
	public void setFondoMapa(String dir) {
		BufferedImage img=null;
		try {
		    img = ImageIO.read(new File(dir));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image dimg = img.getScaledInstance(show_Mapa.getWidth(), show_Mapa.getHeight(),Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(dimg);
		
		show_Mapa.setIcon(imageIcon);
	}
	
	
	public PartidaJugador(Socket s,LNJugadorBase log) {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.s=s;
		this.logica=(LNJugador) log;
		logica.unirseAPartida(s);
		
		

		setResizable(false);
		setSize(new Dimension(1800, 1000));
	
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel Mapa = new JPanel();
		Mapa.setOpaque(false);
		Mapa.setBounds(225, 25, 1200, 900);
		contentPane.add(Mapa);
		Mapa.setLayout(new GridLayout(1, 0, 0, 0));
		 canvas = new Canvas(Mapa,20);
		canvas.setOpaque(false);
		getContentPane().add(canvas);
		
		JPanel Chat = new JPanel();
		Chat.setBounds(1450, 25, 300, 900);
		contentPane.add(Chat);
		Chat.setLayout(null);
		
		 txtLeer = new JTextArea();
		txtLeer.setLineWrap(true);
		txtLeer.setEditable(false);
		txtLeer.setBounds(0, 0, 300, 870);
		Chat.add(txtLeer);
		this.logica.setTxtArea(txtLeer);
		
		txtEscribir = new JTextField();
		txtEscribir.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					ManejadorChat();
					}
			}
		});
		txtEscribir.setBounds(0, 870, 300, 30);
		Chat.add(txtEscribir);
		txtEscribir.setColumns(10);
		
		 show_Mapa = new JLabel("");
		show_Mapa.setBounds(225, 25, 1200, 900);
		contentPane.add(show_Mapa);
		
		lblNewLabel = new JLabel("Jugadores");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel.setBounds(10, 25, 89, 26);
		contentPane.add(lblNewLabel);
		
		listPlayers = new JList();
		listPlayers.setBounds(10, 70, 200, 170);
		contentPane.add(listPlayers);
		
		separator = new JSeparator();
		separator.setBackground(Color.BLACK);
		separator.setBounds(10, 255, 200, 5);
		contentPane.add(separator);
		
		lblNewLabel_1 = new JLabel("Personaje");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel_1.setBounds(10, 271, 89, 34);
		contentPane.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("Nombre");
		lblNewLabel_2.setBounds(10, 316, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		textField = new JTextField();
		textField.setBounds(66, 313, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setBounds(10, 357, 46, 14);
		contentPane.add(lblNewLabel_3);
		
		textField_1 = new JTextField();
		textField_1.setBounds(66, 354, 86, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		lblNewLabel_4 = new JLabel("New label");
		lblNewLabel_4.setBounds(10, 400, 46, 14);
		contentPane.add(lblNewLabel_4);
		
		textField_2 = new JTextField();
		textField_2.setBounds(66, 397, 86, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		lblNewLabel_5 = new JLabel("New label");
		lblNewLabel_5.setBounds(10, 439, 46, 14);
		contentPane.add(lblNewLabel_5);
		
		textField_3 = new JTextField();
		textField_3.setBounds(66, 436, 86, 20);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		lblNewLabel_6 = new JLabel("New label");
		lblNewLabel_6.setBounds(10, 484, 46, 14);
		contentPane.add(lblNewLabel_6);
		
		textField_4 = new JTextField();
		textField_4.setBounds(66, 481, 86, 20);
		contentPane.add(textField_4);
		textField_4.setColumns(10);
		
		lblNewLabel_7 = new JLabel("");
		lblNewLabel_7.setBounds(20, 512, 155, 141);
		contentPane.add(lblNewLabel_7);
		
		comboBox = new JComboBox();
		comboBox.setBounds(30, 664, 122, 22);
		contentPane.add(comboBox);
		
		btnNewButton = new JButton("New button");
		btnNewButton.setBounds(51, 703, 89, 23);
		contentPane.add(btnNewButton);
	}
}
