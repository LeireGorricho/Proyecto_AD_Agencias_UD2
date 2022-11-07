import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaDatosAgencia extends JFrame {
	private JPanel contentPane;
	private Connection conexion;
	private Agencia agencia;

	/**
	 * Create the frame.
	 */
	public VentanaDatosAgencia(Connection conexion) {
		this.conexion = conexion;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel nombreLabel = new JLabel("");
        nombreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nombreLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        nombreLabel.setBounds(75, 28, 285, 33);
        contentPane.add(nombreLabel);
        
        JLabel lblNewLabel = new JLabel("Fecha de apertura:");
        lblNewLabel.setBounds(65, 85, 124, 16);
        contentPane.add(lblNewLabel);
        
        JLabel fechaAperturaLabel = new JLabel("");
        fechaAperturaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        fechaAperturaLabel.setBounds(254, 85, 113, 16);
        contentPane.add(fechaAperturaLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Dirección:");
        lblNewLabel_1.setBounds(65, 115, 77, 16);
        contentPane.add(lblNewLabel_1);

        JLabel direccionLabel = new JLabel("");
        direccionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        direccionLabel.setBounds(141, 115, 226, 16);
        contentPane.add(direccionLabel);
        
        JLabel lblNewLabel_2 = new JLabel("Email:");
        lblNewLabel_2.setBounds(65, 145, 61, 16);
        contentPane.add(lblNewLabel_2);

        JLabel emailLabel = new JLabel("");
        emailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        emailLabel.setBounds(141, 145, 226, 16);
        contentPane.add(emailLabel);
        
        JLabel lblNewLabel_3 = new JLabel("Teléfono:");
        lblNewLabel_3.setBounds(65, 175, 69, 16);
        contentPane.add(lblNewLabel_3);
        
        JLabel telefonoLabel = new JLabel("");
        telefonoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        telefonoLabel.setBounds(212, 175, 155, 16);
        contentPane.add(telefonoLabel);
        
        JButton volverButton = new JButton("Volver");
        volverButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//Volver a la ventana anterior
        		MenuAgencia frame = new MenuAgencia(conexion);
        		frame.setVisible(true);
        		dispose();
        	}
        });
        volverButton.setBounds(160, 212, 117, 29);
        contentPane.add(volverButton);

        //cargar datos en la ventana
        cargarDatos();
        nombreLabel.setText(agencia.getNombre());
        fechaAperturaLabel.setText(agencia.getFechaApertura());
        direccionLabel.setText(agencia.getDireccion());
        emailLabel.setText(agencia.getEmail());
        telefonoLabel.setText(String.valueOf(agencia.getTelefono()));
	}
	
	//Se encarga de cargar los datos de la agencia
	public void cargarDatos() {
		try {
			//Preparar sentencia y ejecutar
			String query = "SELECT * FROM AGENCIAS WHERE id = 1";
			PreparedStatement ps = conexion.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			//Recuperar los datos
			while(rs.next()) {
				agencia = new Agencia(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6));
			}
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERROR al recuperar los datos.");
		}
	}
}
