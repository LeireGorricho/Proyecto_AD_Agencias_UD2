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
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class VentanaAnadirVisita extends JFrame {
	private JPanel contentPane;
	private static Connection conexion;
	private static List<Empleado> empleados = new ArrayList<Empleado>();

	/**
	 * Create the frame.
	 */
	public VentanaAnadirVisita(Connection conexion) {
		this.conexion = conexion;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 357);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("NUEVA VISITA");
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
        comboBox.setBounds(207, 212, 180, 25);
        contentPane.add(comboBox);
        
        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		VentanaVisitasGuiadas frame = new VentanaVisitasGuiadas(conexion);
        		frame.setVisible(true);
        		dispose();
        	}
        });
        cancelarButton.setBounds(232, 278, 117, 29);
        contentPane.add(cancelarButton);
        
        JButton anadirButton = new JButton("Añadir");
        anadirButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//Comprobar campos vacios y insercion del empleado a la base de datos atraves de una funcion
        		try {
	        		String nombre = nombreField.getText();
	        		int maxClientes = Integer.parseInt(maxClientesField.getText());
	        		String puntoPartida = puntoPartidaField.getText();
	        		String tematica = tematicaField.getText();
	        		double coste = Double.parseDouble(costeField.getText());
	        		if (nombre.trim().equals("") || puntoPartida.trim().equals("") || tematica.trim().equals("")) {
	        			JOptionPane.showMessageDialog(null, "Para añadir un empleado tienes que rellenar todos los campos.");
	        		} else {
	        			anadirVisita(nombre, maxClientes, puntoPartida, tematica, coste, (Empleado) comboBox.getSelectedItem());
	        			VentanaVisitasGuiadas frame = new VentanaVisitasGuiadas(conexion);
	        			frame.setVisible(true);
	        			dispose();
	        		}
        		} catch (NumberFormatException ex) {
        			JOptionPane.showMessageDialog(null, "Inserte valores numericos en los campos adecuados.");
        		}
        	}
        });
        anadirButton.setBounds(86, 278, 117, 29);
        contentPane.add(anadirButton);
        
        JLabel lblNewLabel_1 = new JLabel("Empleado a cargo:");
        lblNewLabel_1.setBounds(55, 217, 140, 14);
        contentPane.add(lblNewLabel_1);
        
        //Cargar el combobox con los empleados
        cargarCombo();
        for (int i = 0; i < empleados.size(); i++) {
            comboBox.addItem(empleados.get(i));
        }
	}
	
	//Funcion que se encarga de añadir el empleado a la base de datos
	public static void anadirVisita(String nombre, int maxc, String puntoPartida, String tematica, double coste, Empleado empleado) {
		try {
			//Preparar sentencia y ejecucion
			String query = "INSERT INTO VISITAS_GUIADAS(estado, nombre, max_clientes, punto_partida, tematica, coste, idagencia, idempleado) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setString(1, "Alta");
			ps.setString(2, nombre);
			ps.setInt(3, maxc);
			ps.setString(4, puntoPartida);
			ps.setString(5, tematica);
			ps.setDouble(6, coste);
			ps.setInt(7, 1);
			ps.setInt(8, empleado.getId());
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERROR al insertar los datos.");
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "ERROR no hay empleados seleccionados");
		}
	}
	
	//Funcion que rellena una lista que se usa para rellenar el combobox con empleados
	public static void cargarCombo() {
		try {
		//Preparar sentencia y ejecucion
		String query = "SELECT * FROM EMPLEADOS WHERE estado = 'Alta'";
		PreparedStatement ps = conexion.prepareStatement(query);
		ResultSet resul = ps.executeQuery();
		//Recoger datos
		while (resul.next()) {
			empleados.add(new Empleado(resul.getInt(1), resul.getString(2), resul.getString(3), resul.getString(4), resul.getString(5), resul.getString(6), resul.getString(7), resul.getString(8), resul.getString(9)));
		}
		resul.close();
		ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERROR al insertar los datos.");
		}
	}
}
