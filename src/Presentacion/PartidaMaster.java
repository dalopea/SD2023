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
import ModeloDominio.Casilla;
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
	private DefaultListModel<String> pers=new DefaultListModel<>();
	private JLabel lblPersonajes;
	private JList listPersonajes;
	private JTextField txtCoordX;
	private JTextField txtCoordY;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JButton btnMover;
	private Canvas canvas; 

	
	
	Personaje prueba=new Personaje(null,"Ejemplo",5,6,4,3,"\"src/images/Players/Player.png\"");
	private JButton btnEditarPers;
	private JLabel lblNewLabel_3;

	
	
	public void ManejadorChat() {
		this.ChatLeer.append(logica.getMaster().getNombreUsuario()+": "+this.ChatEscribir.getText()+"\n");
		this.logica.broadcast(logica.getMaster().getNombreUsuario()+": "+this.ChatEscribir.getText());
		this.ChatEscribir.setText("");
	}
	public void ManejadorEditar() {
		
	}
	
	public void ManejadorCrearPersonaje() {
		CreacionPersonaje crea=new CreacionPersonaje();
		crea.setVisible(true);
		
		
		Personaje p=new Personaje(null,crea.getNOM(),crea.getATQ(),crea.getDEF(),crea.getVIT(),crea.getMOV(),"src/images/Players/Player.png");
//		this.logica.getPartida().nuevoPersonaje(p);
		//if lo añade nice, si no le envia mensaje de error
		pers.add(pers.size(), p.getNombrePersonaje());
	}
	
	public void ManejadorVer(){
//		List<Personaje> pers=logica.getPartida();
		
		CreacionPersonaje crea=new CreacionPersonaje();
		crea.setATQ(prueba.getPuntosAtaque());
		crea.setNOM(prueba.getNombrePersonaje());
		crea.setDEF(prueba.getPuntosDefensa());
		crea.settVIT(prueba.getMovimiento());
		crea.setMOV(prueba.getMovimiento());
		crea.setVisible(true);
	}
	
	public void aniadirfichaMons(String img,String nom,int x,int y) {
//		System.out.println("metodo entra");
//		JLabel lblIMG=new JLabel("");
//		lblIMG.setName(nom);
//		lblIMG.setBounds(500,500,20,20);
//		setImage(lblIMG,"Mons/"+img);
//		contentPane.add(lblIMG);
		lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setBounds(10, 840, 20, 20);
		contentPane.add(lblNewLabel_3);
		contentPane.repaint();
	}
	
	public void setImage(JLabel imgl,String dir) {
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
	
	
	public void ManejadorAniadir() {
//		if(this.txtCoordX.getText().isBlank()||this.txtCoordX.getText().isEmpty()||
//				this.txtCoordY.getText().isBlank()||this.txtCoordY.getText().isEmpty() ||
//			this.listPersonajes.getSelectedIndex()==-1) {
//			Casilla c=logica.getPartida().getTablero().getCasilla(Integer.parseInt(txtCoordX.getText()), Integer.parseInt(txtCoordY.getText()));
//			
//			if(!c.isDisponible()) {
//				List<Personaje> lp=logica.getPartida().getPersonajes();
//				String img=null;
//				String nombre=null;
//				for(Personaje p:lp) {
//					if(p.getNombrePersonaje().equals(this.listPersonajes.getSelectedValue())) {
//						img=p.getImagen();
//						nombre=(String) this.listPersonajes.getSelectedValue();
//					}
//				}
//				if(img!=null) {
//					canvas.aniadirfichaMons(img, nombre, Integer.parseInt(txtCoordX.getText()), Integer.parseInt(txtCoordY.getText()));
//				}
//				
//			}else {
//				
//			}
//			
//		}else {
//			
//		}
		
		aniadirfichaMons("Monster.png", "Ejemplo", 6, 5);
		
		
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
		canvas= new Canvas(Partida,20);
		contentPane.add(Partida);
		Partida.setLayout(new GridLayout(1, 0, 0, 0));

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
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManejadorAniadir();
			}
		});
		btnAdd.setBounds(5, 748, 89, 23);
		contentPane.add(btnAdd);
		
		JButton btnVer = new JButton("Ver");
		btnVer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManejadorVer();
			}
		});
		btnVer.setBounds(109, 718, 89, 23);
		contentPane.add(btnVer);
		
		txtCoordX = new JTextField();
		txtCoordX.setBounds(150, 749, 50, 20);
		contentPane.add(txtCoordX);
		txtCoordX.setColumns(10);
		
		txtCoordY = new JTextField();
		txtCoordY.setBounds(150, 780, 50, 20);
		contentPane.add(txtCoordY);
		txtCoordY.setColumns(10);
		
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
		
		btnEditarPers = new JButton("Editar Jugador");
		btnEditarPers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManejadorEditar();
			}
		});
		btnEditarPers.setBounds(90, 34, 103, 23);
		contentPane.add(btnEditarPers);
		
		
		
		pers.add(pers.size(), prueba.getNombrePersonaje());
		
		
//		this.logica.iniciarPartida();
	}
}
