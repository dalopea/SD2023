package Presentacion;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CreacionPersonaje extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	
	private String pathimg;
	private JTextField txtNom;
	private JTextField txtATQ;
	private JTextField txtDEF;
	private JTextField txtVIT;
	private JTextField txtMOV;
	
	
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
	
	
	public void setATQ(int atq) {
		this.txtATQ.setText(Integer.toString(atq));
		this.txtATQ.setEditable(false);
	}
	public void setNOM(String nom) {
		this.txtNom.setText(nom);
		this.txtNom.setEditable(false);
	}
	public void setDEF(int def) {
		this.txtDEF.setText(Integer.toString(def));
		this.txtDEF.setEditable(false);
	}
	public void settVIT(int vit) {
		this.txtVIT.setText(Integer.toString(vit));
		this.txtVIT.setEditable(false);
	}
	public void setMOV(int mov) {
		this.txtMOV.setText(Integer.toString(mov));
		this.txtMOV.setEditable(false);
	}
	
	
	
	
	public void ManejadorCierre(){
		if(PersValido()) {
			this.setVisible(false);
		}else {
			Inicio.infoBox("Creacion Erronea", "Error");
		}
		
	}
	public void ManejadorCrear(){
		if(PersValido()) {
			this.setVisible(false);
		}else {
			Inicio.infoBox("Creacion Erronea", "Error");
		}
	}
	
	
	private void ManejadorIMG(){
		
	}
	
	public boolean PersValido() {
		if( (txtATQ.getText().isBlank()|| txtATQ.getText().isEmpty() || !isInt(txtATQ.getText())) || 
			(txtNom.getText().isBlank()|| txtNom.getText().isEmpty()) ||
			(txtDEF.getText().isBlank()|| txtDEF.getText().isEmpty() || !isInt(txtDEF.getText())) ||
			(txtVIT.getText().isBlank()|| txtVIT.getText().isEmpty() || !isInt(txtVIT.getText())) ||
			(txtMOV.getText().isBlank()|| txtMOV.getText().isEmpty() || !isInt(txtMOV.getText())) ) {
			return false;
		}else {return true;}
	}
	
	public boolean isInt(String n) {
		try{
			Integer.parseInt(n);
			System.out.println("numero ok");
			return true;
		}catch(NumberFormatException e) {
			return false;
		}
		
	}
	
	
//	public static void main(String[] args) {
//		try {
//			CreacionPersonaje dialog = new CreacionPersonaje();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public CreacionPersonaje() {
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setModal(true);
		setBounds(100, 100, 482, 310);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("CrearPersonaje");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
			lblNewLabel.setBounds(20, 11, 119, 14);
			contentPanel.add(lblNewLabel);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Nombre de Personaje:");
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNewLabel_1.setBounds(20, 43, 119, 14);
			contentPanel.add(lblNewLabel_1);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("Ataque Base:");
			lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNewLabel_2.setBounds(20, 79, 119, 14);
			contentPanel.add(lblNewLabel_2);
		}
		{
			JLabel lblNewLabel_3 = new JLabel("Defensa Base:");
			lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNewLabel_3.setBounds(20, 114, 119, 14);
			contentPanel.add(lblNewLabel_3);
		}
		{
			JLabel lblNewLabel_4 = new JLabel("Vitalidad Maxima:");
			lblNewLabel_4.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNewLabel_4.setBounds(20, 154, 119, 14);
			contentPanel.add(lblNewLabel_4);
		}
		{
			JLabel lblNewLabel_5 = new JLabel("Movimiento Maximo:");
			lblNewLabel_5.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNewLabel_5.setBounds(20, 191, 119, 14);
			contentPanel.add(lblNewLabel_5);
		}
		{
			txtNom = new JTextField();
			txtNom.setBounds(149, 40, 86, 20);
			contentPanel.add(txtNom);
			txtNom.setColumns(10);
		}
		{
			txtATQ = new JTextField();
			txtATQ.setBounds(149, 76, 86, 20);
			contentPanel.add(txtATQ);
			txtATQ.setColumns(10);
		}
		{
			txtDEF = new JTextField();
			txtDEF.setBounds(149, 111, 86, 20);
			contentPanel.add(txtDEF);
			txtDEF.setColumns(10);
		}
		{
			txtVIT = new JTextField();
			txtVIT.setBounds(149, 151, 86, 20);
			contentPanel.add(txtVIT);
			txtVIT.setColumns(10);
		}
		{
			txtMOV = new JTextField();
			txtMOV.setBounds(149, 188, 86, 20);
			contentPanel.add(txtMOV);
			txtMOV.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("CREAR");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ManejadorCrear();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

}
