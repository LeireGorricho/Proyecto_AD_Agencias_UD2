import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaEditarCliente extends JFrame {
	private JPanel contentPane;
	private Connection conexion;
	private int id;
	private Cliente cliente;

	/**
	 * Create the frame.
	 */
	public VentanaEditarCliente(Connection conexion, int id) {
		this.id = id;
		this.conexion = conexion;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 330);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("EDITAR CLIENTE");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        lblNewLabel.setBounds(124, 25, 191, 25);
        contentPane.add(lblNewLabel);
        
        JLabel lblNewLabel_6 = new JLabel("Estado:");
        lblNewLabel_6.setBounds(55, 75, 61, 16);
        contentPane.add(lblNewLabel_6);
        
        JComboBox comboBox = new JComboBox();
        comboBox.setBounds(176, 70, 211, 27);
        contentPane.add(comboBox);
        
        JLabel lblNewLabel_1 = new JLabel("Dni:");
        lblNewLabel_1.setBounds(55, 100, 61, 16);
        contentPane.add(lblNewLabel_1);
        
        JTextField dniField = new JTextField();
        dniField.setBounds(176, 95, 211, 26);
        contentPane.add(dniField);
        dniField.setColumns(10);
        
        JLabel lblNewLabel_2 = new JLabel("Nombre:");
        lblNewLabel_2.setBounds(55, 125, 61, 16);
        contentPane.add(lblNewLabel_2);
        
        JTextField nombreField = new JTextField();
        nombreField.setBounds(176, 120, 211, 26);
        contentPane.add(nombreField);
        nombreField.setColumns(10);
        
        JLabel lblNewLabel_3 = new JLabel("Apellidos:");
        lblNewLabel_3.setBounds(55, 150, 78, 16);
        contentPane.add(lblNewLabel_3);
        
        JTextField apellidosField = new JTextField();
        apellidosField.setBounds(176, 145, 211, 26);
        contentPane.add(apellidosField);
        apellidosField.setColumns(10);
        
        JLabel lblNewLabel_4 = new JLabel("Edad:");
        lblNewLabel_4.setBounds(55, 175, 140, 16);
        contentPane.add(lblNewLabel_4);
        
        JTextField edadField = new JTextField();
        edadField.setBounds(176, 170, 211, 26);
        contentPane.add(edadField);
        edadField.setColumns(10);
        
        JLabel lblNewLabel_5 = new JLabel("Profesión:");
        lblNewLabel_5.setBounds(55, 200, 147, 16);
        contentPane.add(lblNewLabel_5);
        
        JTextField profesionField = new JTextField();
        profesionField.setBounds(176, 195, 211, 26);
        contentPane.add(profesionField);
        profesionField.setColumns(10);
        
        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//Volver a la ventana anterior
        		VentanaClientes frame = new VentanaClientes(conexion);
        		frame.setVisible(true);
        		dispose();
        	}
        });
        cancelarButton.setBounds(230, 247, 117, 29);
        contentPane.add(cancelarButton);
        
        JButton editarButton = new JButton("Editar");
        editarButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//comprueba los campos vacion y si todo esta bien edita el cliente
        		if(dniField.getText().trim().equals("") || dniField.getText().length() > 9 || nombreField.getText().trim().equals("") || apellidosField.getText().trim().equals("") || edadField.getText().trim().equals("") || profesionField.getText().trim().equals("")) {
        			JOptionPane.showMessageDialog(null, "Tienes que rellenar todos los campos correctamente para poder editar el cliente.");
        		} else {
        			if (comboBox.getSelectedIndex() == 0) {
        				cliente.setEstado("Alta");
        			} else {
        				cliente.setEstado("Baja");
        			}
        			cliente.setDni(dniField.getText());
        			cliente.setNombre(nombreField.getText());
        			cliente.setApellido(apellidosField.getText());
        			cliente.setEdad(Integer.parseInt(edadField.getText()));
        			cliente.setProfesion(profesionField.getText());
        			editarCliente();
        			VentanaClientes frame = new VentanaClientes(conexion);
            		frame.setVisible(true);
            		dispose();
        		}
        	}
        });
        editarButton.setBounds(85, 247, 117, 29);
        contentPane.add(editarButton);
        //Anadir datos combobox
        comboBox.addItem("Alta");
        comboBox.addItem("Baja");
        
        //Cargar datos en la vista
        cargarDatos();
        if (cliente.getEstado().equals("Baja")) {
        	comboBox.setSelectedIndex(1);
        } else {
        	comboBox.setSelectedIndex(0);
        }
        dniField.setText(cliente.getDni());
        nombreField.setText(cliente.getNombre());
        apellidosField.setText(cliente.getApellido());
        edadField.setText(String.valueOf(cliente.getEdad()));
        profesionField.setText(cliente.getProfesion());
	}
	
	//Se encarga de editar los datos del cliente
	public void editarCliente() {
		try {
			//Preparar sentencia y ejecutar
			String query = "UPDATE CLIENTES SET estado = ?, dni = ?, nombre = ?, apellidos = ?, edad = ?, profesion = ? WHERE id = ?";
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setString(1, cliente.getEstado());
			ps.setString(2, cliente.getDni());
			ps.setString(3, cliente.getNombre());
			ps.setString(4, cliente.getApellido());
			ps.setInt(5, cliente.getEdad());
			ps.setString(6, cliente.getProfesion());
			ps.setInt(7, id);
			ps.execute();
			ps.close();
			JOptionPane.showMessageDialog(null, "Se han actualizado los datos.");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERROR al actualizar los datos.");
		}
	}

	//Carga los datos del cliente seleccionado
	public void cargarDatos() {
		try {
			//Preparar sentencia y ejecutara
			String query = "SELECT * FROM CLIENTES WHERE id = ?";
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			//Recuperar datos
			while(rs.next()) {
				cliente = new Cliente(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getString(7));
			}
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERROR al recuperar los datos.");
		}
	}
}
