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
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class VentanaEditarVisita extends JFrame {
	private JPanel contentPane;
	private static Connection conexion;
	private static int id;
	private static Visita visita;

	/**
	 * Create the frame.
	 */
	public VentanaEditarVisita(Connection conexion, int id) {
		this.conexion = conexion;
		this.id = id;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 332);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        
        JLabel lblNewLabel = new JLabel("EDITAR VISITA");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        lblNewLabel.setBounds(124, 40, 191, 25);
        contentPane.add(lblNewLabel);
        
        JLabel lblNewLabel_2 = new JLabel("Nombre:");
        lblNewLabel_2.setBounds(55, 90, 61, 16);
        contentPane.add(lblNewLabel_2);
        
        JTextField nombreField = new JTextField();
        nombreField.setBounds(207, 85, 180, 26);
        contentPane.add(nombreField);
        nombreField.setColumns(10);
        
        JLabel lblNewLabel_3 = new JLabel("Máximo de clientes:");
        lblNewLabel_3.setBounds(55, 115, 140, 16);
        contentPane.add(lblNewLabel_3);
        
        JTextField maxClientesField = new JTextField();
        maxClientesField.setBounds(207, 110, 180, 26);
        contentPane.add(maxClientesField);
        maxClientesField.setColumns(10);
        
        JLabel lblNewLabel_4 = new JLabel("Punto de partida:");
        lblNewLabel_4.setBounds(55, 140, 140, 16);
        contentPane.add(lblNewLabel_4);
        
        JTextField puntoPartidaField = new JTextField();
        puntoPartidaField.setBounds(207, 135, 180, 26);
        contentPane.add(puntoPartidaField);
        puntoPartidaField.setColumns(10);
        
        JLabel lblNewLabel_5 = new JLabel("Temática:");
        lblNewLabel_5.setBounds(55, 165, 147, 16);
        contentPane.add(lblNewLabel_5);
        
        JTextField tematicaField = new JTextField();
        tematicaField.setBounds(207, 160, 180, 26);
        contentPane.add(tematicaField);
        tematicaField.setColumns(10);
        
        JLabel lblNewLabel_6 = new JLabel("Coste/Persona:");
        lblNewLabel_6.setBounds(55, 190, 109, 16);
        contentPane.add(lblNewLabel_6);
        
        JTextField costeField = new JTextField();
        costeField.setBounds(207, 185, 180, 26);
        contentPane.add(costeField);
        costeField.setColumns(10);
        
        JComboBox comboBox = new JComboBox();
        comboBox.setBounds(207, 211, 180, 25);
        contentPane.add(comboBox);
        
        JLabel lblNewLabel_1 = new JLabel("Estado");
        lblNewLabel_1.setBounds(55, 217, 46, 14);
        contentPane.add(lblNewLabel_1);
        
        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//Volver a la ventana anterior
        		VentanaVisitasGuiadas frame = new VentanaVisitasGuiadas(conexion);
        		frame.setVisible(true);
        		dispose();
        	}
        });
        cancelarButton.setBounds(232, 255, 117, 29);
        contentPane.add(cancelarButton);
        
        JButton editarButton = new JButton("Editar");
        editarButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
        			//comprueba los campos vacion y si todo esta bien edita la vista
	        		String nombre = nombreField.getText();
	        		int maxClientes = Integer.parseInt(maxClientesField.getText());
	        		String puntoPartida = puntoPartidaField.getText();
	        		String tematica = tematicaField.getText();
	        		double coste = Double.parseDouble(costeField.getText());
	        		if (nombre.trim().equals("") || puntoPartida.trim().equals("") || tematica.trim().equals("")) {
	        			JOptionPane.showMessageDialog(null, "Para añadir un empleado tienes que rellenar todos los campos.");
	        		} else {
	        			visita.setNombre(nombre);
	        			visita.setMax_c(maxClientes);
	        			visita.setPunto_partida(puntoPartida);
	        			visita.setTematica(tematica);
	        			visita.setCoste(coste);
	        			if (comboBox.getSelectedIndex() == 0) {
	        				visita.setEstado("Alta");
	        			} else {
	        				visita.setEstado("Baja");
	        			}
	        			editarVisita();
	        			VentanaVisitasGuiadas frame = new VentanaVisitasGuiadas(conexion);
	        			frame.setVisible(true);
	        			dispose();
	        		}
        		} catch (NumberFormatException ex) {
        			JOptionPane.showMessageDialog(null, "Inserte valores numericos en los campos adecuados.");
        		}
        	}
        });
        editarButton.setBounds(86, 255, 117, 29);
        contentPane.add(editarButton);
        contentPane.setLayout(null);
        //Anadir datos combobox
        comboBox.addItem("Alta");
        comboBox.addItem("Baja");
        //Cargar datos en la vista
        cargarDatos();
        nombreField.setText(visita.getNombre());
        maxClientesField.setText(String.valueOf(visita.getMax_c()));
        puntoPartidaField.setText(visita.getPunto_partida());
        tematicaField.setText(visita.getTematica());
        costeField.setText(String.valueOf(visita.getCoste())); 
        if (visita.getEstado().equals("Alta")) {
        	comboBox.setSelectedIndex(0);
        } else {
        	comboBox.setSelectedIndex(1);
        }
	}

	//Se encarga de editar los datos de la visita
	public static void editarVisita() {
		try {
			//Preparar sentencia y ejecutar
			String query = "UPDATE VISITAS_GUIADAS SET estado = ?, nombre = ?, max_clientes = ?, punto_partida = ?, tematica = ?, coste = ? WHERE id = ?";
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setString(1, visita.getEstado());
			ps.setString(2, visita.getNombre());
			ps.setInt(3, visita.getMax_c());
			ps.setString(4, visita.getPunto_partida());
			ps.setString(5, visita.getTematica());
			ps.setDouble(6, visita.getCoste());
			ps.setInt(7, id);
			ps.execute();
			ps.close();
			JOptionPane.showMessageDialog(null, "Se han actualizado los datos.");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERROR al actualizar los datos.");
		}
	}

	//Carga los datos del cliente seleccionado
	public static void cargarDatos() {
		try {
			//Preparar sentencia y ejecutara
			String query = "SELECT * FROM VISITAS_GUIADAS WHERE id = ?";
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			//Recuperar datos
			while(rs.next()) {
				visita = new Visita(rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getDouble(7));
			}
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERROR al recuperar los datos.");
		}	
	}
	
}
