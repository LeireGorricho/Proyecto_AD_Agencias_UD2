import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class VentanaAnadirEmpleado extends JFrame {
	private JPanel contentPane;
	private static Connection conexion;

	/**
	 * Create the frame.
	 */
	public VentanaAnadirEmpleado(Connection conexion) {
		this.conexion = conexion;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 350);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("NUEVO EMPLEADO");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        lblNewLabel.setBounds(124, 17, 191, 25);
        contentPane.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Dni:");
        lblNewLabel_1.setBounds(55, 65, 61, 16);
        contentPane.add(lblNewLabel_1);
        
        JTextField dniField = new JTextField();
        dniField.setBounds(207, 60, 180, 26);
        contentPane.add(dniField);
        dniField.setColumns(10);
        
        JLabel lblNewLabel_2 = new JLabel("Nombre:");
        lblNewLabel_2.setBounds(55, 90, 61, 16);
        contentPane.add(lblNewLabel_2);
        
        JTextField nombreField = new JTextField();
        nombreField.setBounds(207, 85, 180, 26);
        contentPane.add(nombreField);
        nombreField.setColumns(10);
        
        JLabel lblNewLabel_3 = new JLabel("Apellido:");
        lblNewLabel_3.setBounds(55, 115, 61, 16);
        contentPane.add(lblNewLabel_3);
        
        JTextField apellidoField = new JTextField();
        apellidoField.setBounds(207, 110, 180, 26);
        contentPane.add(apellidoField);
        apellidoField.setColumns(10);
        
        JLabel lblNewLabel_4 = new JLabel("Fecha de nacimiento:");
        lblNewLabel_4.setBounds(55, 140, 140, 16);
        contentPane.add(lblNewLabel_4);
        
        JTextField fechaNacField = new JTextField();
        fechaNacField.setBounds(207, 135, 180, 26);
        contentPane.add(fechaNacField);
        fechaNacField.setColumns(10);
        
        JLabel lblNewLabel_5 = new JLabel("Fecha de contratación:");
        lblNewLabel_5.setBounds(55, 165, 147, 16);
        contentPane.add(lblNewLabel_5);
        
        JTextField fechaContratoField = new JTextField();
        fechaContratoField.setBounds(207, 160, 180, 26);
        contentPane.add(fechaContratoField);
        fechaContratoField.setColumns(10);
        
        JLabel lblNewLabel_6 = new JLabel("Nacionalidad:");
        lblNewLabel_6.setBounds(55, 190, 86, 16);
        contentPane.add(lblNewLabel_6);
        
        JTextField nacionalidadField = new JTextField();
        nacionalidadField.setBounds(207, 185, 180, 26);
        contentPane.add(nacionalidadField);
        nacionalidadField.setColumns(10);
        
        JLabel lblNewLabel_7 = new JLabel("Cargo:");
        lblNewLabel_7.setBounds(55, 215, 61, 16);
        contentPane.add(lblNewLabel_7);
        
        JTextField cargoField = new JTextField();
        cargoField.setBounds(207, 210, 180, 26);
        contentPane.add(cargoField);
        cargoField.setColumns(10);
        
        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//Llamada a la ventana anterior
        		VentanaEmpleados frame = new VentanaEmpleados(conexion);
        		frame.setVisible(true);
        		dispose();
        	}
        });
        cancelarButton.setBounds(231, 269, 117, 29);
        contentPane.add(cancelarButton);
        
        JButton anadirButton = new JButton("Añadir");
        anadirButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//Comprobar campos vacios y insercion del empleado a la base de datos atraves de una funcion
        		String dni = dniField.getText();
        		String nombre = nombreField.getText();
        		String apellido = apellidoField.getText();
        		String fecha_con = fechaContratoField.getText();
        		String fecha_nac = fechaNacField.getText();
        		String nacionalidad = nacionalidadField.getText();
        		String cargo = cargoField.getText();
        		if (dni.trim().equals("") || dni.length() > 9 || nombre.trim().equals("") || apellido.trim().equals("") || fecha_con.trim().equals("") || fecha_nac.trim().equals("") || nacionalidad.trim().equals("") || cargo.trim().equals("")) {
        			JOptionPane.showMessageDialog(null, "Para añadir un empleado tienes que rellenar todos los campos correctamente.");
        		} else {
        			anadirEmpleado(dni, nombre, apellido, fecha_con, fecha_nac, nacionalidad, cargo);
        			VentanaEmpleados frame = new VentanaEmpleados(conexion);
            		frame.setVisible(true);
            		dispose();
        		}
        	}
        });
        anadirButton.setBounds(85, 269, 117, 29);
        contentPane.add(anadirButton);
	}
	
	//Funcion que se encarga de añadir el empleado a la base de datos
	public static void anadirEmpleado(String dni, String nombre, String apellido, String fecha_con, String fecha_nac, String nacionalidad, String cargo) {
		try {
			//Preparar sentencia y ejecucion
			String query = "INSERT INTO EMPLEADOS(estado, dni, nombre, apellido, fecha_nac, fecha_contrato, nacionalidad, cargo, idagencia) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setString(1, "Alta");
			ps.setString(2, dni);
			ps.setString(3, nombre);
			ps.setString(4, apellido);
			ps.setString(5, fecha_nac);
			ps.setString(6, fecha_con);
			ps.setString(7, nacionalidad);
			ps.setString(8, cargo);
			ps.setInt(9, 1);
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERROR al insertar los datos.");
		}
	}
}
