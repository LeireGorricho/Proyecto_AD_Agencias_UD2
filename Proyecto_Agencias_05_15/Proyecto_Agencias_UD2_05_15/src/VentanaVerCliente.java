import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class VentanaVerCliente extends JFrame {
	private JPanel contentPane;
	private Connection conexion;
	private int id;
	private Cliente cliente;
	private List<Visita> visitas = new ArrayList<Visita>();

	/**
	 * Create the frame.
	 */
	public VentanaVerCliente(Connection conexion, int id) {
		this.conexion = conexion;
		this.id = id;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel nombreApellidosLabel = new JLabel("");
        nombreApellidosLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nombreApellidosLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        nombreApellidosLabel.setBounds(72, 24, 534, 25);
        contentPane.add(nombreApellidosLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Estado:");
        lblNewLabel_1.setBounds(55, 90, 61, 16);
        contentPane.add(lblNewLabel_1);
        
        JLabel estadoLabel = new JLabel("");
        estadoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        estadoLabel.setBounds(190, 90, 117, 16);
        contentPane.add(estadoLabel);
        
        JLabel lblNewLabel_4 = new JLabel("Dni:");
        lblNewLabel_4.setBounds(55, 120, 140, 16);
        contentPane.add(lblNewLabel_4);
        
        JLabel dniLabel = new JLabel("");
        dniLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        dniLabel.setBounds(151, 120, 156, 16);
        contentPane.add(dniLabel);
        
        JLabel lblNewLabel_5 = new JLabel("Edad:");
        lblNewLabel_5.setBounds(55, 150, 93, 16);
        contentPane.add(lblNewLabel_5);
        
        JLabel edadLabel = new JLabel("");
        edadLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        edadLabel.setBounds(151, 150, 156, 16);
        contentPane.add(edadLabel);
        
        JLabel lblNewLabel_6 = new JLabel("Profesión:");
        lblNewLabel_6.setBounds(55, 180, 86, 16);
        contentPane.add(lblNewLabel_6);
        
        JLabel profesionLabel = new JLabel("");
        profesionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        profesionLabel.setBounds(126, 180, 181, 16);
        contentPane.add(profesionLabel);
        
        JButton volverButton = new JButton("Volver");
        volverButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//carga la ventana anterior
        		VentanaClientes frame = new VentanaClientes(conexion);
        		frame.setVisible(true);
        		dispose();
        	}
        });
        volverButton.setBounds(55, 298, 117, 29);
        contentPane.add(volverButton);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(336, 117, 319, 210);
        contentPane.add(scrollPane);
        
        JTable table = new JTable();
        scrollPane.setViewportView(table);
        
        JLabel lblNewLabel = new JLabel("SUS VISITAS");
        lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
        lblNewLabel.setBounds(336, 90, 167, 16);
        contentPane.add(lblNewLabel);
        
        //Cargar los datos y mostrarlos en la vista
        cargarDatos();
        cargarTabla();
        nombreApellidosLabel.setText(cliente.getNombre() + " " + cliente.getApellido());
        estadoLabel.setText(cliente.getEstado());
        dniLabel.setText(cliente.getDni());
        edadLabel.setText(String.valueOf(cliente.getEdad()));
        profesionLabel.setText(cliente.getProfesion());
        //Cargar la tabla
        int cantidad = visitas.size();
		String[][] d = new String[cantidad][5];
		String[] nombreColumnas = {"id", "estado", "Nombre", "Tematica", "Coste"};
		for (int i = 0; i < visitas.size(); i++) {
	            d[i][0] = String.valueOf(visitas.get(i).getId());
	            d[i][1] = String.valueOf(visitas.get(i).getEstado());
	            d[i][2] = String.valueOf(visitas.get(i).getNombre());
	            d[i][3] = String.valueOf(visitas.get(i).getTematica());
	            d[i][4] = String.valueOf(visitas.get(i).getCoste());
	    }
		table.setModel(new DefaultTableModel(d, nombreColumnas));
	}
	
	//Recupera los datos del cliente seleccionado
	public void cargarDatos() {
		try {
			//Prepara la sentencia y la ejecuta
			String query = "SELECT * FROM CLIENTES WHERE id = ?";
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, id);
			//Recupera los datos
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				cliente = new Cliente(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getString(7));
			}
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERROR al recuperar los datos del cliente.");
		}
	}
	
	//Recupera los datos para cargar la tabla
	public void cargarTabla() {
		try {
			//Prepara la sentencia y la ejecuta
			String query = "SELECT * FROM VISITAS_GUIADAS WHERE id IN (SELECT idvisita FROM CLIENTE_VISITA WHERE idcliente = ?)";
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			//Recupera los datos
			while(rs.next()) {
				visitas.add(new Visita(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getDouble(7)));
			}
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERROR al recuperar los datos de las visitas.");
		}
	}
}
