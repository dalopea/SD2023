package Presentacion;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CrearPersonaje extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNom;
	private JTextField txtATQ;
	private JTextField txtDEF;
	private JTextField txtVIT;
	private JTextField txtMOV;
	private String pathimg;

	
	public int getATQ() {
		return Integer.parseInt(this.txtATQ.getText());
	}
	public String getNOM() {
		return this.txtNom.getText();
	}
	public int getDEF() {
		return Integer.parseInt(this.txtDEF.getText());
	}
	public int getVIT() {
		return Integer.parseInt(this.txtVIT.getText());
	}
	public int getMOV() {
		return Integer.parseInt(this.txtMOV.getText());
	}
	
	
	
	
	private void ManejadorIMG(){
		
	}
	
	
	
	public CrearPersonaje() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 551, 351);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Creacion de personaje");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel.setBounds(27, 11, 176, 50);
		contentPane.add(lblNewLabel);
		
		txtNom = new JTextField();
		txtNom.setBounds(122, 88, 86, 20);
		contentPane.add(txtNom);
		txtNom.setColumns(10);
		
		JLabel lblNomPer = new JLabel("Nombre de Personaje:");
		lblNomPer.setBounds(10, 91, 107, 14);
		contentPane.add(lblNomPer);
		
		JLabel lblAtaque = new JLabel("Ataque Base:");
		lblAtaque.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAtaque.setBounds(10, 128, 107, 14);
		contentPane.add(lblAtaque);
		
		JLabel lblDefensa = new JLabel("Defensa Base:");
		lblDefensa.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDefensa.setBounds(10, 168, 107, 14);
		contentPane.add(lblDefensa);
		
		JLabel lblVida = new JLabel("Puntos de vida Max.:");
		lblVida.setHorizontalAlignment(SwingConstants.RIGHT);
		lblVida.setBounds(10, 207, 107, 14);
		contentPane.add(lblVida);
		
		JLabel lblMovimiento = new JLabel("Movimiento Max.:");
		lblMovimiento.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMovimiento.setBounds(10, 248, 107, 14);
		contentPane.add(lblMovimiento);
		
		txtATQ = new JTextField();
		txtATQ.setBounds(122, 125, 86, 20);
		contentPane.add(txtATQ);
		txtATQ.setColumns(10);
		
		txtDEF = new JTextField();
		txtDEF.setBounds(122, 165, 86, 20);
		contentPane.add(txtDEF);
		txtDEF.setColumns(10);
		
		txtVIT = new JTextField();
		txtVIT.setBounds(122, 204, 86, 20);
		contentPane.add(txtVIT);
		txtVIT.setColumns(10);
		
		txtMOV = new JTextField();
		txtMOV.setBounds(122, 245, 86, 20);
		contentPane.add(txtMOV);
		txtMOV.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBounds(251, 51, 245, 170);
		contentPane.add(panel);
		
		JButton btnImage = new JButton("Select Image");
		btnImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManejadorIMG();
				
			}
		});
		btnImage.setBounds(307, 232, 117, 23);
		contentPane.add(btnImage);
	}
}
