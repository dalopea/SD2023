package Presentacion;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.JMenu;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SelectorPartida extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombrePartida;
	private String nombre;
	private JLabel lblGreet;
	JList listPartidas = new JList();
	


	/**
	 * Create the frame.
	 */
	public String StrToHtml(String texto) {
		return "<html><p>"+texto+"</p></html>";
	}
	//Comprueba si se ha seleccionado una partida para unirse, encaso contrario salta un mensaje de error.
	public void ManejadorUnirse() {
		if(this.listPartidas.getSelectedIndex()==-1) {
			Inicio.infoBox("No se pudo unir a ninguna partida ya que no se ha seleccionado ninguna", "Error de Selección");
		}else {
			//unirse a partida
		}
	}
	
	//Comprueba que la partida a crear es valida
	public void ManejadorCrear() {
		if(this.txtNombrePartida.getText().isEmpty() || this.txtNombrePartida.getText().isBlank()) {
			Inicio.infoBox("Nombre de partida no valido", "Error de Creación");
		}else if(false) {
			//si existe la partida error
		}else {
			//crear la partida
		}
	}
	
	
	public SelectorPartida(String nombre) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				System.exit(0);
			}
		});
		this.nombre=nombre;
		

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
		listPartidas.setModel(new AbstractListModel() {
			String[] values = new String[] {"asd", "sd", "asd", "f"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		listPartidas.setBounds(80, 81, 364, 140);
		contentPane.add(listPartidas);
		
		JLabel lblNewLabel = new JLabel("Partidas disponibles:");
		lblNewLabel.setBounds(60, 56, 128, 14);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Actualizar");
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
}
