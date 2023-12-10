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
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
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
	private static  JPanel contentPane;
	private  JTextField ChatEscribir;
	private  JTextArea ChatLeer;
	private static  LNMaster logica;
	private JPanelBackGround Partida;
	private JLabel lblNewLabel;
	private JLabel show_Mapa;
	private DefaultListModel<String> players=new DefaultListModel<>();
	private static DefaultListModel<String> pers=new DefaultListModel<>();
	private JLabel lblPersonajes;
	private JList listPersonajes;
	private JTextField txtCoordX;
	private JTextField txtCoordY;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private static Canvas canvas; 
	private JButton btnVida;
	private JButton btnEliminar;
	private JButton btnMover;
	private JButton btnAdd;
	private JButton btnCambiarMapa;
	private JComboBox comboMapas;
	private JButton btnDisponibilidad;
	private JButton btnTerminar;

	
	//---------------Manejadores----------
	public void ManejadorChat() {
		this.ChatLeer.append(logica.getMaster().getNombreUsuario()+": "+this.ChatEscribir.getText()+"\n");
		logica.broadcast(logica.getMaster().getNombreUsuario()+": "+this.ChatEscribir.getText());
		this.ChatEscribir.setText("");
	}
	

	
	public void ManejadorCrearPersonaje() {
		CreacionPersonaje crea=new CreacionPersonaje();
		crea.setVisible(true);
		boolean existe=false;
		List<Personaje> personajes=logica.getPartida().getPersonajes();
		
		for(Personaje p:personajes) {
			if(p.getNombrePersonaje().equals(crea.getNOM())) {
				existe=true;
			}
		}
		if(!existe) {
		logica.broadcast("/ROL21/Crear?Nombre="+crea.getNOM()+"&Ataque="+crea.getATQ()+"&Defensa="+crea.getDEF()+"&Movimiento="+crea.getMOV()+"&Vida="+crea.getVIT()+"&Imagen="+crea.getIMG());
		Personaje p=new Personaje(crea.getNOM(),crea.getATQ(),crea.getDEF(),crea.getVIT(),crea.getMOV(),crea.getIMG());
		logica.getPartida().nuevoPersonaje(p);
		pers.add(pers.size(), p.getNombrePersonaje());
		}else {
			Inicio.infoBox("El personaje ya existe", "Error");
		}
	}
	
	
	public void ManejadorVer(){
		if(this.listPersonajes.getSelectedIndex()!=-1) {
			
		String nombre=(String) this.listPersonajes.getSelectedValue();
		List<Personaje> pers=logica.getPartida().getPersonajes();
		Personaje p = null;
		for(Personaje j:pers) {
			if(j.getNombrePersonaje().equals(nombre)) {
				p=j;
			}
		}
		CreacionPersonaje crea=new CreacionPersonaje();
		crea.setATQ(p.getPuntosAtaque());
		crea.setNOM(p.getNombrePersonaje());
		crea.setDEF(p.getPuntosDefensa());
		crea.settVIT(p.getPuntosVidaMaximos());
		crea.setMOV(p.getMovimiento());
		crea.setIMG(p.getImagen());
		crea.setVitAct(p.getPuntosVidaActuales());
		crea.setVisible(true);
		}else {
			
		}
		
	}
	
	
	public void ManejadorVida() {
		if(this.listPersonajes.getSelectedIndex()!=-1) {
			
			String nombre=(String) this.listPersonajes.getSelectedValue();
			List<Personaje> pers=logica.getPartida().getPersonajes();
			Personaje p = null;
			for(Personaje j:pers) {
				if(j.getNombrePersonaje().equals(nombre)) {
					p=j;
				}
			}
			CreacionPersonaje crea=new CreacionPersonaje();
			crea.setATQ(p.getPuntosAtaque());
			crea.setNOM(p.getNombrePersonaje());
			crea.setDEF(p.getPuntosDefensa());
			crea.settVIT(p.getPuntosVidaMaximos());
			crea.setMOV(p.getMovimiento());
			crea.setIMG(p.getImagen());
			crea.setVitAct(p.getPuntosVidaActuales());
			crea.setVidaEditable();
			crea.setVisible(true);
			
			int nuevaVida=crea.getVitAct();
			logica.broadcast("/ROL21/Vida?Personaje="+crea.getNOM()+"&Vida="+nuevaVida);
			Operaciones.modificarVidaPersonaje(logica, nombre, nuevaVida);
			}else {
				Inicio.infoBox("Personaje no seleccionado", "Error");
			}
	}
	
	
	public void ManejadorFinalizar() {
		logica.broadcast("/ROL21/Desconectar");
		System.exit(0);
	}
	
	
	
	
	public void ManejadorAniadir() {
		if(comprobanteCasilla() &&
			this.listPersonajes.getSelectedIndex()!=-1) {
			
			int x=Integer.parseInt(txtCoordX.getText());
			int y=Integer.parseInt(txtCoordY.getText());
			Casilla c=logica.getPartida().getTablero().getCasilla(x, y);
			
			if(c.isDisponible() && c.getPersonaje()==null) {
				
			
			String nombre=(String) this.listPersonajes.getSelectedValue();
			logica.broadcast("/ROL21/Colocar?Personaje="+nombre+"&Coords=["+txtCoordX.getText()+","+txtCoordY.getText()+"]");
			
			List<Personaje> personajes=logica.getPartida().getPersonajes();
			Personaje j=null;
			for(Personaje p:personajes) {
				if(nombre.equals(p.getNombrePersonaje())) {
					j=p;
				}
				
			}
			
			
			aniadirfichaMons(j.getImagen(),j.getNombrePersonaje(),x,y);
			Operaciones.colocarPersonaje(logica, nombre, logica.getPartida().getTablero().getCasilla(x, y));
			}else {
				Inicio.infoBox("Casilla ocupada", "Error");
			}
			
	}else {
		Inicio.infoBox("El personaje no se pudo añadir", "Error");
	}
	}
	
	
	public void ManejadorCambioCasilla() {
		if(comprobanteCasilla()) {
			
			int x=Integer.parseInt(this.txtCoordX.getText());
			int y=Integer.parseInt(this.txtCoordY.getText());
			Casilla c=logica.getPartida().getTablero().getCasilla(x, y);
			
			if(c.isDisponible() && c.getPersonaje()==null) {
				JLabel lblIMG = new JLabel("");
				 lblIMG.setBounds(225+(x*canvas.getCellSize()),25+(y*canvas.getCellSize()), canvas.getCellSize(), canvas.getCellSize());
				setImage(lblIMG,"placeholder/placeholder_x.png");
				lblIMG.setName("Casilla"+x+y);
				contentPane.add(lblIMG,0);
				contentPane.repaint();
				
				logica.broadcast("/ROL21/AlterarDisponible?Coords=["+x+","+y+"]");
				Operaciones.cambiarDisponibilidad(c);
			}else if(!c.isDisponible()){
				eliminarMons("Casilla"+x+y);
				logica.broadcast("/ROL21/AlterarDisponible?Coords=["+x+","+y+"]");
				Operaciones.cambiarDisponibilidad(c);
			}else {
				Inicio.infoBox("Casilla ocupada", "Error");
			}
		}else {
			Inicio.infoBox("Casilla no seleccionada", "Error");
		}
		
		
		
	}
	
	
	public void ManejadorMover(){
		if(comprobanteCasilla() &&
				this.listPersonajes.getSelectedIndex()!=-1) {
				int x=Integer.parseInt(this.txtCoordX.getText());
				int y=Integer.parseInt(this.txtCoordY.getText());
				Casilla ca=logica.getPartida().getTablero().getCasilla(x, y);
				
				if(ca.isDisponible() && ca.getPersonaje()==null) {
				Personaje j=null;
				List<Personaje> personajes =logica.getPartida().getPersonajes();
				for(Personaje p:personajes) {
					if(p.getNombrePersonaje().equals((String)this.listPersonajes.getSelectedValue())) {
						j=p;
					}
				}
				
				Casilla c=j.getPosicion();
				int actx=c.getCoordenadas()[0];
				int acty=c.getCoordenadas()[1];
				if(Math.abs(actx-x)>j.getMovimiento() ||Math.abs(acty-y)>j.getMovimiento()
						) {
					Inicio.infoBox("No se puede mover hasta allí.", "Error");
				}else {
					if(this.ManejadorEliminar()) {
						this.btnAdd.doClick();
					}
				}
				}else {
					Inicio.infoBox("Casilla Ocupada.", "Error");
				}
				
		}else {
			Inicio.infoBox("El personaje no se pudo mover", "Error");
		}
	}
	
	

	public boolean ManejadorEliminar() {
		if(this.listPersonajes.getSelectedIndex()!=-1) {
			String nombre=(String) this.listPersonajes.getSelectedValue();
			if(eliminarMons(nombre)) {
				logica.broadcast("/ROL21/Eliminar?Nombre="+this.listPersonajes.getSelectedValue());
				
				List<Personaje> personajes=logica.getPartida().getPersonajes();
				Personaje j=null;
				for(Personaje p:personajes) {
					if(p.getNombrePersonaje().equals(nombre)) {
						j=p;
					}
				}
				Operaciones.eliminarPersonajeDeCasilla(j);
				return true;
			}else {
				Inicio.infoBox("La ficha no está en el tablero", "Error");
				return false;
			}
			
			
		}else {
			Inicio.infoBox("El personaje no se puso quitar del mapa", "Error");
			return false;
		}
	}
	
	
	public void ManejadorCambiaMapa() {
		String mapa=(String)this.comboMapas.getSelectedItem();
		logica.broadcast("/ROL21/Map?Imagen="+mapa);
		cambiaMapa(mapa);
	}
	
	//-------Metodos-----------
	
	public static void addPersonajeJugador(String nombre,int atq,int def,int vit,int mov,String img) {
		logica.broadcast("/ROL21/Crear?Nombre="+nombre+"&Ataque="+atq+"&Defensa="+def+"&Movimiento="+mov+"&Vida="+vit+"&Imagen="+img);
		Personaje p=new Personaje(nombre,atq,def, vit,mov,img);
		logica.getPartida().nuevoPersonaje(p);
		pers.add(pers.size(), p.getNombrePersonaje());
	}
	
	public static void colocaPersonajeJugador(Personaje j,int x,int y) {
		logica.broadcast("/ROL21/Colocar?Personaje="+j.getNombrePersonaje()+"&Coords=["+x+","+y+"]");
		
		aniadirfichaMons(j.getImagen(),j.getNombrePersonaje(),x,y);
		Operaciones.colocarPersonaje(logica, j.getNombrePersonaje(), logica.getPartida().getTablero().getCasilla(x, y));
	}
	
	public static void moverPersonajeJugador(Personaje j,int x,int y) {
		logica.broadcast("/ROL21/Eliminar?Nombre="+j.getNombrePersonaje());
		eliminarMons(j.getNombrePersonaje());
		Operaciones.eliminarPersonajeDeCasilla(j);
		
		logica.broadcast("/ROL21/Colocar?Personaje="+j.getNombrePersonaje()+"&Coords=["+x+","+y+"]");
		aniadirfichaMons(j.getImagen(),j.getNombrePersonaje(),x,y);
		Operaciones.colocarPersonaje(logica, j.getNombrePersonaje(), logica.getPartida().getTablero().getCasilla(x, y));
	}
	
	public static void aniadirfichaMons(String img,String nom,int x,int y) {
		 JLabel lblIMG = new JLabel("");
		 lblIMG.setBounds(225+(x*canvas.getCellSize()),25+(y*canvas.getCellSize()), canvas.getCellSize(), canvas.getCellSize());
		setImage(lblIMG,"Personaje/"+img);
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
	
	
	public boolean comprobanteCasilla() {
		if( !this.txtCoordX.getText().isBlank() && !this.txtCoordX.getText().isEmpty()&&
			!this.txtCoordY.getText().isBlank()&& !this.txtCoordY.getText().isEmpty()  
			&& isInt(this.txtCoordX.getText()) && isInt(this.txtCoordY.getText())){
			return true;
		}else {
			return false;
		}
	}
	
	public boolean isInt(String n) {
		try{
			Integer.parseInt(n);
			return true;
		}catch(NumberFormatException e) {
			return false;
		}
		
	}
	
	public  void cambiaMapa(String im) {
		setFondoMapa(im);
	}
	
	
	public static boolean eliminarMons(String nombre) {
		Component[] componentList = contentPane.getComponents();
		boolean borr=false;
		for(Component c : componentList){

		    if(c instanceof JLabel && c.getName()!=null) {

		     if(c.getName().equals(nombre)){

		    	contentPane.remove(c);
		    	borr=true;
		    }
		}
		}
		contentPane.revalidate();
		contentPane.repaint();
		return borr;
		
		
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
		
		logica=(LNMaster) ln;
		
		
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
		logica.addTXT(ChatLeer);
		
		lblNewLabel = new JLabel("Jugadores");
		lblNewLabel.setBounds(10, 25, 143, 37);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		contentPane.add(lblNewLabel);
		Partida.setBackground("");
		
		show_Mapa = new JLabel("");
		show_Mapa.setBounds(225, 25, 1200, 900);
		
		this.setFondoMapa("game_initialise.jpg");
		
		
		
		contentPane.add(show_Mapa);


		
		
		lblPersonajes = new JLabel("Personajes");
		lblPersonajes.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblPersonajes.setBounds(10, 267, 143, 37);
		contentPane.add(lblPersonajes);
		
		listPersonajes = new JList(this.pers);
		listPersonajes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listPersonajes.setBounds(10, 300, 200, 350);
		contentPane.add(listPersonajes);
		
		JButton btnCrear = new JButton("Crear");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManejadorCrearPersonaje();
			}
		});
		btnCrear.setBounds(5, 660, 89, 23);
		contentPane.add(btnCrear);
		
		 btnAdd = new JButton("Añadir");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManejadorAniadir();
			}
		});
		btnAdd.setBounds(5, 690, 89, 23);
		contentPane.add(btnAdd);
		
		JButton btnVer = new JButton("Ver");
		btnVer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManejadorVer();
			}
		});
		btnVer.setBounds(109, 660, 89, 23);
		contentPane.add(btnVer);
		
		txtCoordX = new JTextField();
		txtCoordX.setBounds(150, 690, 50, 20);
		contentPane.add(txtCoordX);
		txtCoordX.setColumns(10);
		
		txtCoordY = new JTextField();
		txtCoordY.setBounds(150, 720, 50, 20);
		contentPane.add(txtCoordY);
		txtCoordY.setColumns(10);
		
		lblNewLabel_1 = new JLabel("x:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(104, 690, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("y:");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setBounds(104, 720, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		btnMover = new JButton("Mover");
		btnMover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManejadorMover();
			}
		});
		btnMover.setBounds(5, 720, 89, 23);
		contentPane.add(btnMover);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(UIManager.getColor("Button.focus"));
		separator.setBounds(10, 255, 200, 5);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBackground(UIManager.getColor("Button.focus"));
		separator_1.setBounds(10, 810, 200, 5);
		contentPane.add(separator_1);
		
		btnVida = new JButton("Vida");
		btnVida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManejadorVida();
			}
		});
		btnVida.setBounds(5, 750, 89, 23);
		contentPane.add(btnVida);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManejadorEliminar();
			}
		});
		btnEliminar.setBounds(5, 780, 89, 23);
		contentPane.add(btnEliminar);
		
		btnCambiarMapa = new JButton("Cambiar");
		btnCambiarMapa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManejadorCambiaMapa();
			}
		});
		btnCambiarMapa.setBounds(5, 820, 89, 23);
		contentPane.add(btnCambiarMapa);
		
		comboMapas = new JComboBox();
		comboMapas.setBounds(109, 820, 89, 22);
		contentPane.add(comboMapas);
		
		File img=new File("src/images/mapas");
		File[] imgs=img.listFiles();
		for(File f:imgs) {
		String name=f.getName();
		comboMapas.addItem(name);
		}
		
		
		
		
		
		btnDisponibilidad = new JButton("<html>Cambiar Disponible</html>");
		btnDisponibilidad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManejadorCambioCasilla();
			}
		});
		btnDisponibilidad.setVerticalAlignment(SwingConstants.TOP);
		btnDisponibilidad.setBounds(109, 750, 89, 40);
		contentPane.add(btnDisponibilidad);
		
		btnTerminar = new JButton("Finalizar Partida");
		btnTerminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManejadorFinalizar();
			}
		});
		btnTerminar.setBounds(25, 892, 150, 23);
		contentPane.add(btnTerminar);
		
		
		CyclicBarrier barrera=new CyclicBarrier(logica.getnumJugadores()+1);
		logica.iniciarPartida(barrera);
		try {
			barrera.await();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (BrokenBarrierException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JList listaPlayers = new JList(this.players);
		listaPlayers.setBounds(10, 70, 200, 170);
		List<String> jugs= logica.getPartida().getJugadores();
		List<String> noms=new ArrayList<>();
		for(String j:jugs) {
			noms.add("Jugador: "+j);
		}
		noms.add("Master: "+logica.getMaster().getNombreUsuario());

		
		this.players.addAll(noms);
		contentPane.add(listaPlayers);
	}
}
