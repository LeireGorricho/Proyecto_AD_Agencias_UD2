import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
import javax.swing.JTextField;

public class VentanaApuntar extends JFrame {
	private JPanel contentPane;
	private static Connection conexion;
	private static List<Visita> visitas = new ArrayList<Visita>();
	private static List<Cliente> clientes = new ArrayList<Cliente>();
	private JTextField fechaField;

	/**
	 * Create the frame.
	 */
	public VentanaApuntar(Connection conexion) {
		this.conexion = conexion;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 310);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("APUNTAR CLIENTE A VISITA");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        lblNewLabel.setBounds(68, 25, 293, 25);
        contentPane.add(lblNewLabel);
        
        JLabel lblNewLabel_3 = new JLabel("Cliente:");
        lblNewLabel_3.setBounds(70, 80, 97, 16);
        contentPane.add(lblNewLabel_3);
        
        JComboBox clienteComboBox = new JComboBox();
        clienteComboBox.setBounds(179, 75, 192, 27);
        contentPane.add(clienteComboBox);
        
        JLabel lblNewLabel_1 = new JLabel("Visita guiada:");
        lblNewLabel_1.setBounds(70, 110, 97, 16);
        contentPane.add(lblNewLabel_1);
        
        JComboBox visitaComboBox = new JComboBox();
        visitaComboBox.setBounds(179, 105, 192, 27);
        contentPane.add(visitaComboBox);
        
        JLabel lblNewLabel_2 = new JLabel("Horario:");
        lblNewLabel_2.setBounds(70, 140, 97, 16);
        contentPane.add(lblNewLabel_2);
        
        JComboBox horarioComboBox = new JComboBox();
        horarioComboBox.setBounds(179, 135, 192, 27);
        contentPane.add(horarioComboBox);
        
        JButton anadirButton = new JButton("Apuntar");
        anadirButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//Comprobar campos vacios y insercion de la visita a la base de datos atraves de una funcion
        		if (fechaField.getText().trim().equals("")) {
        			JOptionPane.showMessageDialog(null, "Para añadir una visita tienes que rellenar todos los campos.");
        		} else {
        			apuntarCliente((Cliente) clienteComboBox.getSelectedItem(), (Visita) visitaComboBox.getSelectedItem(), fechaField.getText(), horarioComboBox.getSelectedItem().toString());
        			VentanaClientes frame = new VentanaClientes(conexion);
        			frame.setVisible(true);
        			dispose();
        		}
        	}
        });
        anadirButton.setBounds(81, 231, 117, 29);
        contentPane.add(anadirButton);
        
        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//Llamada a la ventana anterior
        		VentanaClientes frame = new VentanaClientes(conexion);
        		frame.setVisible(true);
        		dispose();
        	}
        });
        cancelarButton.setBounds(231, 231, 117, 29);
        contentPane.add(cancelarButton);
        
        JLabel lblNewLabel_4 = new JLabel("Fecha:");
        lblNewLabel_4.setBounds(68, 172, 46, 14);
        contentPane.add(lblNewLabel_4);
        
        fechaField = new JTextField();
        fechaField.setBounds(179, 167, 192, 25);
        contentPane.add(fechaField);
        fechaField.setColumns(10);
        
        //Cargar los combobox con los clientes y las visitas
        cargarClientes();
        cargarVisitas();
        for (int i = 0; i < clientes.size(); i++) {
            clienteComboBox.addItem(clientes.get(i));
        }
        for (int i = 0; i < visitas.size(); i++) {
            visitaComboBox.addItem(visitas.get(i));
        }
        //Anadir los horarios a los combobox
        horarioComboBox.addItem("13:00");
        horarioComboBox.addItem("15:00");
        horarioComboBox.addItem("17:00");
        horarioComboBox.addItem("19:00");
	}
	
	//Funcion que se encarga de anadir el cliente a la visita
	public static void apuntarCliente(Cliente cliente, Visita visita, String fecha, String hora) {
		try {
			//Preparar sentencia y ejecucion
			String query = "INSERT INTO CLIENTE_VISITA(fecha, hora, idcliente, idvisita) VALUES(?, ?, ?, ?)";
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setString(1, fecha);
			ps.setString(2, hora);
			ps.setInt(3, cliente.getId());
			ps.setInt(4, visita.getId());
			ps.execute();
			JOptionPane.showMessageDialog(null, "Se ha añadido a la visita correctamente.");
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERROR al insertar los datos.");
		}	
	}
	
	//Funcion que se encarga de recuperar los clientes
	public static void cargarClientes() {
		clientes.clear();
		try {
			//Preparar sentencia y ejecucion
			String query = "SELECT id, nombre FROM CLIENTES WHERE estado = 'Alta'";
			PreparedStatement ps = conexion.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			//Recuperacion de datos
			while(rs.next()) {
				clientes.add(new Cliente(rs.getInt(1), rs.getString(2)));
			}
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERROR al recuperar los datos.");
		}	
	}
	
	//Funcion que se encarga de recuperar las visitas
	public static void cargarVisitas() {
		visitas.clear();
		try {
			//Preparar sentencia y ejecucion
			String query = "SELECT id, nombre FROM VISITAS_GUIADAS WHERE estado = 'Alta'";
			PreparedStatement ps = conexion.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			//Recuperacion de datos
			while(rs.next()) {
				visitas.add(new Visita(rs.getInt(1), rs.getString(2)));
			}
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERROR al recuperar los datos.");
		}	
	}

}
