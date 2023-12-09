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
import java.util.ArrayList;
import java.util.List;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.border.MatteBorder;

import LogicaNegocio.*;
import ModeloDominio.Jugador;
import ModeloDominio.Personaje;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Font;
import javax.swing.JTable;
import java.awt.SystemColor;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Point;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;

public class PartidaMaster extends JFrame {

	private static final long serialVersionUID = 1L;
	private  JPanel contentPane;
	private  JTextField ChatEscribir;
	private  JTextArea ChatLeer;
	private  LNMaster logica;
	private JPanelBackGround Partida;
	private JLabel lblNewLabel;
	private JLabel show_Mapa;
	private DefaultListModel<String> players=new DefaultListModel<>();
	private DefaultListModel<Personaje> pers=new DefaultListModel<>();
	private JLabel lblPersonajes;
	private JList listPersonajes;
	private JTextField textField;
	private JTextField textField_1;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JButton btnMover;


	
	
	public void ManejadorChat() {
		this.ChatLeer.append(logica.getMaster().getNombreUsuario()+": "+this.ChatEscribir.getText()+"\n");
		this.logica.broadcast(logica.getMaster().getNombreUsuario()+": "+this.ChatEscribir.getText());
		this.ChatEscribir.setText("");
	}
	
	public void ManejadorCrearPersonaje() {
		CrearPersonaje crea=new CrearPersonaje();
		crea.setAlwaysOnTop(true);
		crea.setVisible(true);
		while(crea.isEnabled()) {}
		Personaje p=new Personaje(crea.getNOM(),crea.getATQ(),crea.getDEF(),crea.getVIT(),crea.getMOV(),"src/images/Players/Player.png");
		this.logica.getPartida().nuevoPersonaje(p);
		pers.add(pers.size(), p);
	}
	
	
	public void setFondoMapa(String dir) {
		BufferedImage img=null;
		try {
		    img = ImageIO.read(new File("src/images/mapas/"+dir));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image dimg = img.getScaledInstance(show_Mapa.getWidth(), show_Mapa.getHeight(),Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(dimg);
		
		show_Mapa.setIcon(imageIcon);
	}
	

	
	public PartidaMaster(LNJugadorBase ln) {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		System.out.println("Entrando al constructor");
		setResizable(false);
		setSize(new Dimension(1800, 1000));
		
		this.logica=(LNMaster) ln;
		
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Partida = new JPanelBackGround();
		Partida.setBackground(UIManager.getColor("Button.disabledShadow"));
		Partida.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		Partida.setBounds(225, 25, 1200, 900);
		contentPane.add(Partida);
		Partida.setLayout(new GridLayout(1, 0, 0, 0));
		Canvas canvas = new Canvas(Partida,20);
		canvas.setOpaque(false);
		getContentPane().add(canvas);
		
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
//		logica.addTXT(ChatLeer);
		
		lblNewLabel = new JLabel("Jugadores");
		lblNewLabel.setBounds(10, 25, 143, 37);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		contentPane.add(lblNewLabel);
		Partida.setBackground("");
		
		show_Mapa = new JLabel("");
		show_Mapa.setBounds(225, 25, 1200, 900);
		
		this.setFondoMapa("claro_Mapa.jpg");
		
		
		
		contentPane.add(show_Mapa);
		
		JList listaPlayers = new JList(this.players);
		listaPlayers.setBounds(20, 73, 178, 153);
//		List<Jugador> jugs= logica.getPartida().getJugadores();
		List<String> noms=new ArrayList<>();
//		for(Jugador j:jugs) {
//			noms.add(j.getNombreUsuario());
//		}
//		noms.add(logica.getMaster().getNombreUsuario());
		noms.add("f");
		
		this.players.addAll(noms);
		contentPane.add(listaPlayers);
		
		lblPersonajes = new JLabel("Personajes");
		lblPersonajes.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPersonajes.setBounds(10, 267, 143, 37);
		contentPane.add(lblPersonajes);
		
		listPersonajes = new JList(this.pers);
		listPersonajes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listPersonajes.setBounds(10, 307, 188, 400);
		contentPane.add(listPersonajes);
		
		JButton btnCrear = new JButton("Crear");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManejadorCrearPersonaje();
			}
		});
		btnCrear.setBounds(5, 718, 89, 23);
		contentPane.add(btnCrear);
		
		JButton btnAdd = new JButton("Añadir");
		btnAdd.setBounds(5, 748, 89, 23);
		contentPane.add(btnAdd);
		
		JButton btnVer = new JButton("Ver");
		btnVer.setBounds(109, 718, 89, 23);
		contentPane.add(btnVer);
		
		textField = new JTextField();
		textField.setBounds(150, 749, 50, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(150, 780, 50, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		lblNewLabel_1 = new JLabel("x:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(104, 752, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("y:");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setBounds(104, 783, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		btnMover = new JButton("Mover");
		btnMover.setBounds(5, 779, 89, 23);
		contentPane.add(btnMover);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(UIManager.getColor("Button.focus"));
		separator.setBounds(10, 255, 200, 5);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBackground(UIManager.getColor("Button.focus"));
		separator_1.setBounds(10, 824, 200, 5);
		contentPane.add(separator_1);
		
//		this.logica.iniciarPartida();
	}
}
