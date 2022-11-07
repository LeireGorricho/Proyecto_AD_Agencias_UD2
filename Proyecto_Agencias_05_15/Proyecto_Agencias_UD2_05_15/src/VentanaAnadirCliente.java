import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaAnadirCliente extends JFrame {
	private JPanel contentPane;
	private static Connection conexion;


	/**
	 * Create the frame.
	 */
	public VentanaAnadirCliente(Connection conexion) {
		this.conexion = conexion;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 287);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("NUEVO CLIENTE");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        lblNewLabel.setBounds(124, 17, 191, 25);
        contentPane.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Dni:");
        lblNewLabel_1.setBounds(55, 65, 61, 16);
        contentPane.add(lblNewLabel_1);
        
        JTextField dniField = new JTextField();
        dniField.setBounds(176, 60, 211, 26);
        contentPane.add(dniField);
        dniField.setColumns(10);
        
        JLabel lblNewLabel_2 = new JLabel("Nombre:");
        lblNewLabel_2.setBounds(55, 90, 61, 16);
        contentPane.add(lblNewLabel_2);
        
        JTextField nombreField = new JTextField();
        nombreField.setBounds(176, 85, 211, 26);
        contentPane.add(nombreField);
        nombreField.setColumns(10);
        
        JLabel lblNewLabel_3 = new JLabel("Apellidos:");
        lblNewLabel_3.setBounds(55, 115, 78, 16);
        contentPane.add(lblNewLabel_3);
        
        JTextField apellidosField = new JTextField();
        apellidosField.setBounds(176, 110, 211, 26);
        contentPane.add(apellidosField);
        apellidosField.setColumns(10);
        
        JLabel lblNewLabel_4 = new JLabel("Edad:");
        lblNewLabel_4.setBounds(55, 140, 140, 16);
        contentPane.add(lblNewLabel_4);
        
        JTextField edadField = new JTextField();
        edadField.setBounds(176, 135, 211, 26);
        contentPane.add(edadField);
        edadField.setColumns(10);
        
        JLabel lblNewLabel_5 = new JLabel("Profesión:");
        lblNewLabel_5.setBounds(55, 165, 147, 16);
        contentPane.add(lblNewLabel_5);
        
        JTextField profesionField = new JTextField();
        profesionField.setBounds(176, 160, 211, 26);
        contentPane.add(profesionField);
        profesionField.setColumns(10);
        
        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//Llamada a la ventana anterior
        		VentanaClientes frame = new VentanaClientes(conexion);
        		frame.setVisible(true);
        		dispose();
        	}
        });
        cancelarButton.setBounds(231, 210, 117, 29);
        contentPane.add(cancelarButton);
        
        JButton anadirButton = new JButton("Añadir");
        anadirButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
        			//Comprobar campos vacios y insercion del cliente a la base de datos atraves de una funcion
	        		String dni = dniField.getText();
	        		String nombre = nombreField.getText();
	        		String apellido = apellidosField.getText();
	        		int edad = Integer.parseInt(edadField.getText());
	        		String profesion = profesionField.getText();
	        		if (dni.trim().equals("") || dni.length() > 9 || nombre.trim().equals("") || apellido.trim().equals("") || profesion.trim().equals("")) {
	        			JOptionPane.showMessageDialog(null, "Para añadir un empleado tienes que rellenar todos los campos correctamente.");
	        		} else {
	        			anadirCliente(dni, nombre, apellido, edad, profesion);
	        			VentanaClientes frame = new VentanaClientes(conexion);
	            		frame.setVisible(true);
	            		dispose();
	        		}
        		} catch (NumberFormatException ex) {
        			JOptionPane.showMessageDialog(null, "Inserte valores numericos en los campos adecuados.");
        		}
        	}
        });
        anadirButton.setBounds(85, 210, 117, 29);
        contentPane.add(anadirButton);
    }
	
	//Funcion que se encarga de añadir el cliente a la base de datos
	public static void anadirCliente(String dni, String nombre, String apellido, int edad, String profesion) {
		try {
			//Preparar sentencia y ejecucion
			String query = "INSERT INTO CLIENTES(estado, dni, nombre, apellidos, edad, profesion, idagencia) VALUES(?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setString(1, "Alta");
			ps.setString(2, dni);
			ps.setString(3, nombre);
			ps.setString(4, apellido);
			ps.setInt(5, edad);
			ps.setString(6, profesion);
			ps.setInt(7, 1);
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERROR al insertar los datos.");
		}
	}

}
