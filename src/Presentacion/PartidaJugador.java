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
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class PartidaJugador extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JPanel contentPane;
	private JTextField txtEscribir;
	private JTextArea txtLeer;
	private Socket s;
	private static LNJugador logica;
	
	private static JLabel show_Mapa;
	private JLabel lblNewLabel;
	private JSeparator separator;
	private JLabel lblNewLabel_1;
	private static Canvas canvas;
	private JLabel lblNewLabel_2;
	private static JTextField txtNombre;
	private JLabel lblNewLabel_3;
	private JTextField txtVitalidad;
	private JLabel lblNewLabel_4;
	private JTextField txtAtaque;
	private JLabel lblNewLabel_5;
	private JTextField txtDefensa;
	private JLabel lblNewLabel_6;
	private JTextField txtMovimientos;
	private JLabel lblImage;
	private JButton btnCrear;
	private DefaultListModel<String> players=new DefaultListModel<>();
	private JComboBox<String> comboImage = new JComboBox<>();
	private JLabel lblVitAct;
	private static JTextField txtVitAct;
	private JTextField txtCoordX;
	private JTextField txtCoordY;
	private JButton btnAdd;
	private JButton btnMover;
	private JLabel lblX;
	private JLabel lblY;
	
	//---------Manejadores----------
	
	
	private void ManejadorImagen(){
		setImage(lblImage, "Personaje/"+(String)this.comboImage.getSelectedItem());
	}
	
	
	public void ManejadorChat() {
		this.txtLeer.append(logica.getJugador().getNombreUsuario()+": "+this.txtEscribir.getText()+"\n");
		logica.hiloEscritorJugador.enviarMensaje(logica.getJugador().getNombreUsuario()+": "+this.txtEscribir.getText());
		this.txtEscribir.setText("");
	}
	
	
	
	
	public void ManejadorCrearPersonaje() {
		if( this.txtAtaque.getText().isBlank() || this.txtAtaque.getText().isEmpty() || !isInt(this.txtAtaque.getText()) ||
			this.txtDefensa.getText().isBlank() || this.txtDefensa.getText().isEmpty() || !isInt(this.txtDefensa.getText()) ||
			this.txtMovimientos.getText().isBlank() || this.txtMovimientos.getText().isEmpty() || !isInt(this.txtMovimientos.getText()) ||
			this.txtVitalidad.getText().isBlank() || this.txtVitalidad.getText().isEmpty() || !isInt(this.txtVitalidad.getText()) ||
			this.txtNombre.getText().isBlank() || this.txtNombre.getText().isEmpty() 
			) {
			Inicio.infoBox("Error de creacion", "Error");
		}else {
			logica.hiloEscritorJugador.enviarMensaje("/ROL21/Crear?Nombre="+this.txtNombre.getText()+"&Ataque="+ this.txtAtaque.getText()+"&Defensa="+this.txtDefensa.getText()+"&Movimiento="+  this.txtMovimientos.getText()+"&Vida="+this.txtVitalidad.getText()+"&Imagen="+this.comboImage.getSelectedItem());
			this.btnCrear.setVisible(false);
			this.lblVitAct.enable(true);
			txtVitAct.enable(true);
			txtVitAct.setEditable(false);
			txtVitAct.setText(this.txtVitalidad.getText());
			txtNombre.setEditable(false);
			this.txtAtaque.setEditable(false);
			this.txtDefensa.setEditable(false);
			this.txtMovimientos.setEditable(false);
			this.txtVitalidad.setEditable(false);
			this.comboImage.setEditable(false);
			this.txtCoordX.setVisible(true);
			this.txtCoordY.setVisible(true);
			this.lblX.setVisible(true);
			this.lblY.setVisible(true);
			this.btnAdd.setVisible(true);
			this.btnMover.setVisible(true);
			
		}
		
	}
	
	public void Manejadoraniadirse() {
		if(!this.txtCoordX.getText().isBlank() &&
		!this.txtCoordX.getText().isEmpty()&&
		!this.txtCoordY.getText().isBlank()&&
		!this.txtCoordY.getText().isEmpty()){
			
			int x=Integer.parseInt(this.txtCoordX.getText());
			int y=Integer.parseInt(this.txtCoordY.getText());
			
			logica.hiloEscritorJugador.enviarMensaje("/ROL21/Colocar?Personaje="+this.txtNombre.getText()+"&Coords=["+x+","+y+"]");
			this.btnAdd.setVisible(false);
			
		}else {
			Inicio.infoBox("No se pudo añadir", "Error");
		}
	}
	
	public void ManejadorMoverse() {
		if(!this.txtCoordX.getText().isBlank() &&
				!this.txtCoordX.getText().isEmpty()&&
				!this.txtCoordY.getText().isBlank()&&
				!this.txtCoordY.getText().isEmpty()){
			
			Personaje j=null;
			List<Personaje> personajes =logica.getPartida().getPersonajes();
			for(Personaje p:personajes) {
				if(p.getNombrePersonaje().equals(this.txtNombre)) { 
					j=p;
				}
			}
			int x=Integer.parseInt(this.txtCoordX.getText());
			int y=Integer.parseInt(this.txtCoordY.getText());
			Casilla c=j.getPosicion();
			int actx=c.getCoordenadas()[0];
			int acty=c.getCoordenadas()[1];
			if(Math.abs(actx-x)>j.getMovimiento() ||Math.abs(acty-y)>j.getMovimiento()
					) {
				Inicio.infoBox("No se puede mover hasta allí.", "Error");
			}else {
				logica.hiloEscritorJugador.enviarMensaje("/ROL21/Mover?Personaje="+this.txtNombre.getText()+"&Coords=["+x+","+y+"]");
			}
			
			
			
			
			
			
		}else {
			Inicio.infoBox("No se pudo mover", "Error");
		}
	}
	
	
	//--------Metodos-----------
	public static void eliminarFichaMapa(String nombre) {
		eliminarMons(nombre);
		List<Personaje> personajes=logica.getPartida().getPersonajes();
		Personaje j=null;
		for(Personaje p:personajes) {
			if(p.getNombrePersonaje().equals(nombre)) {
				j=p;
			}
		}
		Operaciones.eliminarPersonajeDeCasilla(j);
	}
	
	
	public boolean isInt(String n) {
		try{
			Integer.parseInt(n);
			return true;
		}catch(NumberFormatException e) {
			return false;
		}
		
	}
	
	
	public static void eliminarMons(String nombre) {
		Component[] componentList = contentPane.getComponents();

		for(Component c : componentList){

		    if(c instanceof JLabel && c.getName()!=null) {

		     if(c.getName().equals(nombre)){

		    	contentPane.remove(c);
		    }
		}
		}
		contentPane.revalidate();
		contentPane.repaint();
		
		
	}
	
	
	
	
	public static void setFondoMapa(String dir) {
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
	
	public static void cerrar() {
		System.exit(0);
	}
	
	public static  void ColocaFicha(Personaje p,Casilla c) {
		aniadirfichaMons(p.getImagen(),p.getNombrePersonaje(),c.getCoordenadas()[0] ,c.getCoordenadas()[1]);
		Operaciones.colocarPersonaje(logica, p.getNombrePersonaje(), c);	
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
	
	public static void cambiarVida(String nombre,int vida) {
		Operaciones.modificarVidaPersonaje(logica, nombre, vida);
		if(nombre.equals(txtNombre.getText())) {
			System.out.println("Lo suponia");
			txtVitAct.setText(Integer.toString(vida));
		}
	}
	
	public static void CrearFicha(Personaje p) {
		Operaciones.nuevoPersonaje(logica, p);
	}
	
	
	
	
	public PartidaJugador(Socket s,LNJugadorBase log) {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.s=s;
		logica=(LNJugador) log;
		logica.unirseAPartida(s);
		while(logica.getP()==null) {
			Thread.onSpinWait();
		}
		if(logica.getP()==null) {
			System.out.println("Partida nula");
		}

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
		logica.setTxtArea(txtLeer);
		
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
		
		JList listaPlayers = new JList(this.players);
		listaPlayers.setBounds(10, 70, 200, 170);
		
		List<String> jugs= logica.getPartida().getJugadores();
		List<String> noms=new ArrayList<>();
		for(String j:jugs) {
			noms.add("Jugador: "+j);
		}
		noms.add("Master: "+logica.getP().getMaster().getNombreUsuario());

		
		this.players.addAll(noms);
		contentPane.add(listaPlayers);
		
		separator = new JSeparator();
		separator.setBackground(Color.BLACK);
		separator.setBounds(10, 255, 200, 5);
		contentPane.add(separator);
		
		lblNewLabel_1 = new JLabel("Personaje");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel_1.setBounds(10, 271, 89, 34);
		contentPane.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("Nombre:");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setBounds(10, 316, 104, 14);
		contentPane.add(lblNewLabel_2);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(124, 313, 86, 20);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		lblNewLabel_3 = new JLabel("Vitalidad:");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_3.setBounds(10, 439, 104, 14);
		contentPane.add(lblNewLabel_3);
		
		txtVitalidad = new JTextField();
		txtVitalidad.setBounds(124, 436, 86, 20);
		contentPane.add(txtVitalidad);
		txtVitalidad.setColumns(10);
		
		lblNewLabel_4 = new JLabel("Ataque:");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_4.setBounds(10, 357, 104, 14);
		contentPane.add(lblNewLabel_4);
		
		txtAtaque = new JTextField();
		txtAtaque.setBounds(124, 354, 86, 20);
		contentPane.add(txtAtaque);
		txtAtaque.setColumns(10);
		
		lblNewLabel_5 = new JLabel("Defensa:");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_5.setBounds(10, 397, 104, 14);
		contentPane.add(lblNewLabel_5);
		
		txtDefensa = new JTextField();
		txtDefensa.setBounds(124, 397, 86, 20);
		contentPane.add(txtDefensa);
		txtDefensa.setColumns(10);
		
		lblNewLabel_6 = new JLabel("Movimiento:");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_6.setBounds(8, 484, 106, 14);
		contentPane.add(lblNewLabel_6);
		
		txtMovimientos = new JTextField();
		txtMovimientos.setBounds(124, 481, 86, 20);
		contentPane.add(txtMovimientos);
		txtMovimientos.setColumns(10);
		
		lblImage = new JLabel("");
		lblImage.setBounds(20, 542, 155, 141);
		contentPane.add(lblImage);
		
		
		
		comboImage.setBounds(30, 694, 122, 22);
		
		File img=new File("src/images/Personaje");
		File[] imgs=img.listFiles();
		for(File f:imgs) {
		String name=f.getName();
		comboImage.addItem(name);
		}
		
		
		contentPane.add(comboImage);
		
		btnCrear = new JButton("Crear Personaje");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManejadorCrearPersonaje();
			}
		});
		btnCrear.setBounds(40, 733, 120, 23);
		contentPane.add(btnCrear);
		
		lblVitAct = new JLabel("Vitalidad Actual:");
		lblVitAct.setEnabled(false);
		lblVitAct.setHorizontalAlignment(SwingConstants.RIGHT);
		lblVitAct.setBounds(10, 517, 104, 14);
		contentPane.add(lblVitAct);
		
		txtVitAct = new JTextField();
		txtVitAct.setEnabled(false);
		txtVitAct.setBounds(124, 512, 86, 20);
		contentPane.add(txtVitAct);
		txtVitAct.setColumns(10);
		
		 btnAdd = new JButton("Añadirse");
		 btnAdd.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		Manejadoraniadirse();
		 	}
		 });
		btnAdd.setBounds(10, 803, 89, 23);
		btnAdd.setVisible(false);
		contentPane.add(btnAdd);
		
		 btnMover = new JButton("Moverse");
		 btnMover.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		ManejadorMoverse();
		 	}
		 });
		btnMover.setBounds(10, 837, 89, 23);
		btnMover.setVisible(false);
		contentPane.add(btnMover);
		
		txtCoordX = new JTextField();
		txtCoordX.setBounds(160, 804, 50, 20);
		contentPane.add(txtCoordX);
		txtCoordX.setVisible(false);
		txtCoordX.setColumns(10);
		
		txtCoordY = new JTextField();
		txtCoordY.setBounds(160, 838, 50, 20);
		contentPane.add(txtCoordY);
		txtCoordY.setVisible(false);
		txtCoordY.setColumns(10);
		
		 lblX = new JLabel("x:");
		lblX.setHorizontalAlignment(SwingConstants.RIGHT);
		lblX.setBounds(109, 807, 46, 14);
		lblX.setVisible(false);
		contentPane.add(lblX);
		
		 lblY = new JLabel("y:");
		lblY.setHorizontalAlignment(SwingConstants.RIGHT);
		lblY.setBounds(109, 841, 46, 14);
		lblX.setVisible(false);
		contentPane.add(lblY);
		
		comboImage.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				ManejadorImagen();
			}
		});
		
	}
}
