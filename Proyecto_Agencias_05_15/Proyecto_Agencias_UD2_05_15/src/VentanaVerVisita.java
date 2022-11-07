import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaVerVisita extends JFrame {
	private JPanel contentPane;
	private Connection conexion;
	private int id;
	private Visita visita;
	private List<Cliente> clientes = new ArrayList<Cliente>();

	/**
	 * Create the frame.
	 */
	public VentanaVerVisita(Connection conexion, int id) {
		this.conexion = conexion;
		this.id = id;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 426);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel nombreLabel = new JLabel("");
        nombreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nombreLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        nombreLabel.setBounds(140, 24, 397, 25);
        contentPane.add(nombreLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Estado:");
        lblNewLabel_1.setBounds(55, 90, 61, 16);
        contentPane.add(lblNewLabel_1);
        
        JLabel estadoLabel = new JLabel("");
        estadoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        estadoLabel.setBounds(190, 90, 117, 16);
        contentPane.add(estadoLabel);
        
        JLabel lblNewLabel_4 = new JLabel("Máximo de clientes:");
        lblNewLabel_4.setBounds(55, 120, 140, 16);
        contentPane.add(lblNewLabel_4);
        
        JLabel maxClientesLabel = new JLabel("");
        maxClientesLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        maxClientesLabel.setBounds(231, 120, 76, 16);
        contentPane.add(maxClientesLabel);
        
        JLabel lblNewLabel_5 = new JLabel("Punto partida:");
        lblNewLabel_5.setBounds(55, 150, 93, 16);
        contentPane.add(lblNewLabel_5);
        
        JLabel puntoPartidaLabel = new JLabel("");
        puntoPartidaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        puntoPartidaLabel.setBounds(151, 150, 156, 16);
        contentPane.add(puntoPartidaLabel);
        
        JLabel lblNewLabel_6 = new JLabel("Temática:");
        lblNewLabel_6.setBounds(55, 180, 86, 16);
        contentPane.add(lblNewLabel_6);
        
        JLabel tematicaLabel = new JLabel("");
        tematicaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        tematicaLabel.setBounds(140, 180, 167, 16);
        contentPane.add(tematicaLabel);
        
        JLabel lblNewLabel_7 = new JLabel("Coste/Persona:");
        lblNewLabel_7.setBounds(55, 210, 106, 16);
        contentPane.add(lblNewLabel_7);
        
        JLabel costeLabel = new JLabel("");
        costeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        costeLabel.setBounds(231, 210, 76, 16);
        contentPane.add(costeLabel);
        
        JButton volverButton = new JButton("Volver");
        volverButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//carga la ventana anterior
        		VentanaVisitasGuiadas frame = new VentanaVisitasGuiadas(conexion);
        		frame.setVisible(true);
        		dispose();
        	}
        });
        volverButton.setBounds(55, 334, 117, 29);
        contentPane.add(volverButton);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(336, 117, 319, 210);
        contentPane.add(scrollPane);
        
        JTable table = new JTable();
        scrollPane.setViewportView(table);
        
        JLabel lblNewLabel = new JLabel("CLIENTES");
        lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
        lblNewLabel.setBounds(336, 90, 167, 16);
        contentPane.add(lblNewLabel);
        
        JLabel fechaLabel = new JLabel("Fecha:");
        fechaLabel.setBounds(55, 245, 86, 16);
        contentPane.add(fechaLabel);
        
        JComboBox fechaComboBox = new JComboBox();
        fechaComboBox.setBounds(159, 240, 156, 27);
        contentPane.add(fechaComboBox);
        
        JComboBox horaComboBox = new JComboBox();
		horaComboBox.setBounds(169, 272, 145, 25);
		contentPane.add(horaComboBox);
		
		JLabel lblNewLabel_2 = new JLabel("Hora: ");
		lblNewLabel_2.setBounds(55, 277, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		JButton btnNewButton = new JButton("Filtrar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarTabla((String)fechaComboBox.getSelectedItem(), (String)horaComboBox.getSelectedItem().toString());
		        int cantidad = clientes.size();
				String[][] d = new String[cantidad][8];
				String[] nombreColumnas = {"id", "Nombre", "Apellido", "Edad"};
				for (int i = 0; i < clientes.size(); i++) {
			            d[i][0] = String.valueOf(clientes.get(i).getId());
			            d[i][1] = String.valueOf(clientes.get(i).getNombre());
			            d[i][2] = String.valueOf(clientes.get(i).getApellido());
			            d[i][3] = String.valueOf(clientes.get(i).getEdad());
			    }
				table.setModel(new DefaultTableModel(d, nombreColumnas));
			}
		});
		btnNewButton.setBounds(190, 334, 117, 29);
		contentPane.add(btnNewButton);
		
		//Cargar los datos y mostrarlos en la vista
        cargarDatos();
        nombreLabel.setText(visita.getNombre());
        estadoLabel.setText(visita.getEstado());
        maxClientesLabel.setText(String.valueOf(visita.getMax_c()));
        puntoPartidaLabel.setText(visita.getPunto_partida());
        tematicaLabel.setText(visita.getTematica());
        costeLabel.setText(String.valueOf(visita.getCoste()));
        
        //cargar combobox de las horas
        horaComboBox.addItem("13:00");
        horaComboBox.addItem("15:00");
        horaComboBox.addItem("17:00");
        horaComboBox.addItem("19:00");
        
        //cargar combobox de las fechas
        List<String> fechas = cargarFechas();
        for (int i = 0; i < fechas.size(); i++) {
           fechaComboBox.addItem(fechas.get(i));
        }
        
        //cargar todos los clientes de una visita
        cargarTodo(id);
        int cantidad = clientes.size();
		String[][] d = new String[cantidad][8];
		String[] nombreColumnas = {"id", "Nombre", "Apellido", "Edad"};
		for (int i = 0; i < clientes.size(); i++) {
	            d[i][0] = String.valueOf(clientes.get(i).getId());
	            d[i][1] = String.valueOf(clientes.get(i).getNombre());
	            d[i][2] = String.valueOf(clientes.get(i).getApellido());
	            d[i][3] = String.valueOf(clientes.get(i).getEdad());
	    }
		table.setModel(new DefaultTableModel(d, nombreColumnas));
	}

	//Recupera los datos de la visita
	public void cargarDatos() {
		try {
			//Prepara la sentencia y la ejecuta
			String query = "SELECT * FROM VISITAS_GUIADAS WHERE id = ?";
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			//Recupera los datos
			while(rs.next()) {
				visita = new Visita(id, rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getDouble(7));
			}
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERROR al recuperar los datos.");
		}
	}
	
	//Recupera los datos de los clientes que han estado en la visita en una fecha y hora concretos
	public void cargarTabla(String fecha, String hora) {
		clientes.clear();
		try {
			//Prepara la sentencia y la ejecuta
			String query = "SELECT * FROM CLIENTES WHERE id IN (SELECT idcliente FROM CLIENTE_VISITA WHERE idvisita = ? AND hora = ? AND fecha = ?)";
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, id);
			ps.setString(2, hora);
			ps.setString(3, fecha);
			ResultSet rs = ps.executeQuery();
			//Recupera los datos
			while(rs.next()) {
				clientes.add(new Cliente(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getString(7), rs.getInt(8)));
			}
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERROR al recuperar los datos.");
		}
	}
	
	//Recupera todas las fechas en las que se ha echo la visita
	public List<String> cargarFechas() {
		List<String> fechas = new ArrayList<String>();
		try {
			//Prepara la sentencia y la ejecuta
			String query = "SELECT fecha FROM CLIENTE_VISITA WHERE idvisita = ?";
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			//Recupera los datos
			while(rs.next()) {
				fechas.add(rs.getString(1));
			}
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERROR al recuperar los datos.");
		}
		return fechas;
	}
	
	//carga todos los clientes que se han apuntado a esa visita
	public void cargarTodo(int id) {
		clientes.clear();
		try {
			//Prepara la sentencia y la ejecuta
			String query = "SELECT * FROM CLIENTES WHERE id IN (SELECT idcliente FROM CLIENTE_VISITA WHERE idvisita = ?)";
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			//Recupera los datos
			while(rs.next()) {
				clientes.add(new Cliente(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getString(7), rs.getInt(8)));
			}
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERROR al recuperar los datos.");
		}
	}
}
