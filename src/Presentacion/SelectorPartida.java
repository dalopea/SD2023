package Presentacion;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import ModeloDominio.*;

import LogicaNegocio.*;

import javax.swing.border.CompoundBorder;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

import java.net.*;
import java.util.*;

import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SelectorPartida extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombrePartida;
	private String nombre;
	private JLabel lblGreet;
	DefaultListModel<String> model=new DefaultListModel<>();
	JList listPartidas = new JList(model);
	HashMap<String,Integer> partidas;
	
	private Socket s;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	

	public void cerrarTodo() {
		try {
			if (ois != null) {
				ois.close();
			}
			if (oos != null) {
				oos.close();
			}
			if (s!= null) {
				s.close();
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}


	public String StrToHtml(String texto) {
		return "<html><p>"+texto+"</p></html>";
	}
	
	//Comprueba si se ha seleccionado una partida para unirse, encaso contrario salta un mensaje de error.
	public void ManejadorUnirse() {
		if(this.listPartidas.getSelectedIndex()==-1) {
			Inicio.infoBox("No se pudo unir a ninguna partida ya que no se ha seleccionado ninguna", "Error de Selección");
		}else {
			try {
				Socket s=new Socket("localhost",partidas.get(this.listPartidas.getSelectedValue().toString()));
				Jugador j=new Jugador(this.nombre);
				LNJugadorBase ln=new LNJugador(j);
				PartidaJugador pj=new PartidaJugador(s,ln);
				pj.setVisible(true);
				this.setVisible(false);
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//Comprueba que la partida a crear es valida
	public void ManejadorCrear() {
		if(this.txtNombrePartida.getText().isEmpty() || this.txtNombrePartida.getText().isBlank()) {
			Inicio.infoBox("Error de Creación", "Nombre de partida no valido");
		}else {
			Partida p=crearPartida();
			if(p!=null) {
				try {
					s.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				Master m=new Master(this.nombre);
				LNJugadorBase ln=new LNMaster(m,p);
				PartidaMaster pm=new PartidaMaster(ln);
				pm.setVisible(true);
				this.setVisible(false);
			}
		}
	}
	
	public void ManejadorActualizar() {
				this.partidas=obtenerPartidas();
				this.model.removeAllElements();
				model.addAll(partidas.keySet());

	}
	
	
	public SelectorPartida(String nombre,Socket s) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				System.exit(0);
			}
		});
		this.nombre=nombre;
		this.s=s;
		try {
			this.oos = new ObjectOutputStream(s.getOutputStream());
			this.ois = new ObjectInputStream(s.getInputStream());
		}catch(IOException e) {
			cerrarTodo();
		}
		

		setResizable(false);
		setTitle("Selector de partidas");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 556, 361);
		contentPane = new JPanel();
		contentPane.setBorder(new CompoundBorder());

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnCrear = new JButton("Crear");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			ManejadorCrear();
			
			}
		});
		btnCrear.setBounds(109, 232, 89, 32);
		contentPane.add(btnCrear);
		
		JButton btnActualizar = new JButton("Unirse");
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManejadorUnirse();
			}
		});
		btnActualizar.setBounds(156, 275, 89, 36);
		contentPane.add(btnActualizar);
		
		
		listPartidas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listPartidas.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		
		listPartidas.setBounds(80, 81, 364, 140);
		contentPane.add(listPartidas);
		
		JLabel lblNewLabel = new JLabel("Partidas disponibles:");
		lblNewLabel.setBounds(60, 56, 128, 14);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Actualizar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManejadorActualizar();
			}
		});
		btnNewButton.setBounds(304, 275, 104, 36);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre:");
		lblNewLabel_1.setBounds(245, 241, 49, 14);
		contentPane.add(lblNewLabel_1);
		
		txtNombrePartida = new JTextField();
		txtNombrePartida.setBounds(294, 238, 150, 20);
		contentPane.add(txtNombrePartida);
		txtNombrePartida.setColumns(10);
		
		 lblGreet = new JLabel("New label");
		 lblGreet.setVerticalAlignment(SwingConstants.TOP);
		 this.lblGreet.setText(StrToHtml("Bienvenido/a "+nombre+" seleccione una partida entre las disponibles o cree una nueva."));
		lblGreet.setFont(new Font("Malgun Gothic", Font.BOLD, 13));
		lblGreet.setBounds(54, 11, 418, 44);
		contentPane.add(lblGreet);
	}
	
	public HashMap<String,Integer> obtenerPartidas(){
		HashMap<String,Integer> partidas = null;
		try{
				
				oos.writeBytes("Obtener partidas\n");
				oos.flush();
				partidas = (HashMap<String,Integer>) ois.readObject();

		}
		catch(IOException | ClassNotFoundException e) {
			cerrarTodo();
		}
		return partidas;
	}
	
	
	
	
	
	/*
	 * El método crearPartida() se conecta con el servidor de la aplicación para incluir una nueva partida (en un futuro, se 
	 * podrá incluir una partida importada desde un documento xml). Tras dar los datos de la partida al servidor (nombre y número de puerto),
	 * se desconecta del servidor y devuelve el objeto de tipo Partida creado.
	 */
	public Partida crearPartida() {
		Partida p = null;
		try{
			
			//TODO: Aquí es donde debería dar la opción de importar partida desde un xml, creo yo
			
					
					oos.writeBytes("Nueva partida\n");
					oos.writeBytes("Nombre:" + this.nombre + "\n");
					oos.writeBytes("Nombre:" + this.txtNombrePartida.getText()+"\n");
					oos.flush();
					String respuesta = ois.readLine();
					
					if (!respuesta.startsWith("ERROR")) {
						p = new Partida();
						p.setNombrePartida(this.txtNombrePartida.getText());
						p.setPuertoPartida(s.getLocalPort());
						System.out.println(s.getLocalPort());
						oos.writeBytes("Desconectar\n");
						oos.flush();
						
					}
					Inicio.infoBox(respuesta,"INFO");
				

		} catch (IOException e) {
			cerrarTodo();
		}
		
		return p;
	}
	
	
	
}
