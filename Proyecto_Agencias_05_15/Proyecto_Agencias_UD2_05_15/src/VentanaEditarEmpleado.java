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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class VentanaEditarEmpleado extends JFrame {
	private JPanel contentPane;
	private static Connection conexion;
	private int id;
	private Empleado empleado = new Empleado();

	/**
	 * Create the frame.
	 */
	public VentanaEditarEmpleado(Connection conexion, int id) {
		this.conexion = conexion;
		this.id = id;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 452, 376);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("EDITAR EMPLEADO");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        lblNewLabel.setBounds(124, 17, 191, 25);
        contentPane.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Dni:");
        lblNewLabel_1.setBounds(55, 96, 61, 16);
        contentPane.add(lblNewLabel_1);
        
        JTextField dniField = new JTextField();
        dniField.setBounds(207, 91, 180, 26);
        contentPane.add(dniField);
        dniField.setColumns(10);
        
        JLabel lblNewLabel_2 = new JLabel("Nombre:");
        lblNewLabel_2.setBounds(55, 121, 61, 16);
        contentPane.add(lblNewLabel_2);
        
        JTextField nombreField = new JTextField();
        nombreField.setBounds(207, 116, 180, 26);
        contentPane.add(nombreField);
        nombreField.setColumns(10);
        
        JLabel lblNewLabel_3 = new JLabel("Apellido:");
        lblNewLabel_3.setBounds(55, 146, 61, 16);
        contentPane.add(lblNewLabel_3);
        
        JTextField apellidoField = new JTextField();
        apellidoField.setBounds(207, 141, 180, 26);
        contentPane.add(apellidoField);
        apellidoField.setColumns(10);
        
        JLabel lblNewLabel_4 = new JLabel("Fecha de nacimiento:");
        lblNewLabel_4.setBounds(55, 171, 140, 16);
        contentPane.add(lblNewLabel_4);
        
        JTextField fechaNacField = new JTextField();
        fechaNacField.setBounds(207, 166, 180, 26);
        contentPane.add(fechaNacField);
        fechaNacField.setColumns(10);
        
        JLabel lblNewLabel_5 = new JLabel("Fecha de contratación:");
        lblNewLabel_5.setBounds(55, 196, 147, 16);
        contentPane.add(lblNewLabel_5);
        
        JTextField fechaContratoField = new JTextField();
        fechaContratoField.setBounds(207, 191, 180, 26);
        contentPane.add(fechaContratoField);
        fechaContratoField.setColumns(10);
        
        JLabel lblNewLabel_6 = new JLabel("Nacionalidad:");
        lblNewLabel_6.setBounds(55, 221, 86, 16);
        contentPane.add(lblNewLabel_6);
        
        JTextField nacionalidadField = new JTextField();
        nacionalidadField.setBounds(207, 216, 180, 26);
        contentPane.add(nacionalidadField);
        nacionalidadField.setColumns(10);
        
        JLabel lblNewLabel_7 = new JLabel("Cargo:");
        lblNewLabel_7.setBounds(55, 246, 61, 16);
        contentPane.add(lblNewLabel_7);
        
        JTextField cargoField = new JTextField();
        cargoField.setBounds(207, 241, 180, 26);
        contentPane.add(cargoField);
        cargoField.setColumns(10);
        
        JComboBox comboBox = new JComboBox();
        comboBox.setBounds(207, 67, 180, 25);
        
        contentPane.add(comboBox);
        
        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//Volver a la venatana anterior
        		VentanaEmpleados frame = new VentanaEmpleados(conexion);
        		frame.setVisible(true);
        		dispose();
        	}
        });
        cancelarButton.setBounds(231, 300, 117, 29);
        contentPane.add(cancelarButton);
        
        JButton editarButton = new JButton("Editar");
        editarButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//comprueba los campos vacion y si todo esta bien edita el empleado
        		if(dniField.getText().trim().equals("") || dniField.getText().length() > 9 || nombreField.getText().trim().equals("") || apellidoField.getText().trim().equals("") || fechaNacField.getText().trim().equals("") || fechaContratoField.getText().trim().equals("") || nacionalidadField.getText().trim().equals("") || cargoField.getText().trim().equals("")) {
        			JOptionPane.showMessageDialog(null, "Tienes que rellenar todos los campos correctamente para poder editar el empleado.");
        		} else {
        			if (comboBox.getSelectedIndex() == 0) {
        				empleado.setEstado("Alta");
        			} else {
        				empleado.setEstado("Baja");
        			}
        			empleado.setDni(dniField.getText());
        			empleado.setNombre(nombreField.getText());
        			empleado.setApellido(apellidoField.getText());
        			empleado.setFechaNac(fechaNacField.getText());
        			empleado.setFechaCon(fechaContratoField.getText());
        			empleado.setNacionalidad(nacionalidadField.getText());
        			empleado.setCargo(cargoField.getText());
        			editarEmpleado();
        			VentanaEmpleados frame = new VentanaEmpleados(conexion);
            		frame.setVisible(true);
            		dispose();
        		}
        	}
        });
        
        JLabel lblNewLabel_8 = new JLabel("Estado");
        lblNewLabel_8.setBounds(55, 71, 46, 14);
        contentPane.add(lblNewLabel_8);
        
        editarButton.setBounds(85, 300, 117, 29);
        contentPane.add(editarButton);
        //Anadir datos combobox
        comboBox.addItem("Alta");
        comboBox.addItem("Baja");
        
        //Cargar datos en la vista
        cargarDatos();
        if (empleado.getEstado().equals("Baja")) {
        	comboBox.setSelectedIndex(1);
        } else {
        	comboBox.setSelectedIndex(0);
        }
        dniField.setText(empleado.getDni());
        nombreField.setText(empleado.getNombre());
        apellidoField.setText(empleado.getApellido());
        fechaNacField.setText(empleado.getFechaNac());
        fechaContratoField.setText(empleado.getFechaCon());
        nacionalidadField.setText(empleado.getNacionalidad());
        cargoField.setText(empleado.getCargo());
        
        
	}
	
	//Se encarga de editar los datos del empleado
	public void editarEmpleado() {
		try {
			//Preparar sentencia y ejecutar
			String query = "UPDATE EMPLEADOS SET estado = ?, dni = ?, nombre = ?, apellido = ?, fecha_nac = ?, fecha_contrato = ?, nacionalidad = ?, cargo = ? WHERE id = ?";
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setString(1, empleado.getEstado());
			ps.setString(2, empleado.getDni());
			ps.setString(3, empleado.getNombre());
			ps.setString(4, empleado.getApellido());
			ps.setString(5, empleado.getFechaNac());
			ps.setString(6, empleado.getFechaCon());
			ps.setString(7, empleado.getNacionalidad());
			ps.setString(8, empleado.getCargo());
			ps.setInt(9, id);
			ps.execute();
			JOptionPane.showMessageDialog(null, "Se han actualizado los datos.");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERROR al actualizar los datos.");
		}
	}
	
	//Carga los datos del empleado seleccionado
	public void cargarDatos() {
		try {
			//Preparar sentencia y ejecutara
			String query = "SELECT * FROM EMPLEADOS WHERE id = ?";
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			//Recuperar datos
			while(rs.next()) {
				empleado = new Empleado(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
			}
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERROR al recuperar los datos.");
		}
	}
}
