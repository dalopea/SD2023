package Presentacion;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;



/*
 * Pantalla de seleccion de nombres
 * */
public class Inicio {

	private JFrame frmRol;
	private static Inicio window;
	private JTextField txtNombre = new JTextField();;
	/**
	 * Launch the application.
	 */
	public static void Invocar() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					 window = new Inicio();
					window.frmRol.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Inicio() {
		initialize();
	}
	
	//Este metodo permite que las label se adapten al tamaño de la label.
	public String StrToHtml(String texto) {
		return "<html><p>"+texto+"</p></html>";
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRol = new JFrame();
		frmRol.setTitle("Rol21");
		frmRol.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRol.setResizable(false);
		frmRol.setBounds(100, 100, 385, 203);
		
		JButton btnNewButton = new JButton("Confirmar Nombre");
		btnNewButton.setBounds(32, 97, 131, 37);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManejadorNombre();
			}
		});
		frmRol.getContentPane().setLayout(null);
		frmRol.getContentPane().add(btnNewButton);
		
		
		txtNombre.setBounds(207, 107, 125, 20);
		frmRol.getContentPane().add(txtNombre);
		txtNombre.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(32, 11, 292, 75);
		frmRol.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(StrToHtml("Bienvenido a Rol21, donde podrá jugar a las partidas de rol con sus amigos, pero primero establezca su usuario."));
		lblNewLabel.setBounds(10, 7, 272, 57);
		panel.add(lblNewLabel);
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
	}
	
	//Metodo para crear infoBox de error, pero podrían ser de otra cosa
	 public static void infoBox(String infoMessage, String titleBar)
	    {
	        JOptionPane.showMessageDialog(null, infoMessage, "Notificaión: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
	    }
	
	 //Comprueba que el nombre es valido, tras lo que creara el selector de partidas.
	public void ManejadorNombre() {
		if(this.txtNombre.getText().isEmpty() || this.txtNombre.getText().isBlank()) {
			Inicio.infoBox("Nombre no valido", "Error de Inicio de sesion");
		}else {
			SelectorPartida in;
			try {
				in = new SelectorPartida(this.txtNombre.getText(),new Socket("localhost",55555));
				in.setVisible(true);
				window.frmRol.setVisible(false);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
