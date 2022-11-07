import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import java.awt.event.ActionEvent;

public class VentanaVerEmpleado extends JFrame {
	private JPanel contentPane;
	private Connection conexion;
	private Empleado empleado = new Empleado();
	private int id;
	private List<Visita> visitas = new ArrayList<Visita>();

	/**
	 * Create the frame.
	 */
	public VentanaVerEmpleado(Connection conexion, int id) {
		this.conexion = conexion;
		this.id = id;
		setBounds(100, 100, 700, 402);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel nombreApellidoLabel = new JLabel("");
        nombreApellidoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nombreApellidoLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        nombreApellidoLabel.setBounds(140, 24, 397, 25);
        contentPane.add(nombreApellidoLabel);
        
        JLabel lblNewLabel = new JLabel("Estado:");
        lblNewLabel.setBounds(55, 90, 61, 16);
        contentPane.add(lblNewLabel);
        
        JLabel estadoLabel = new JLabel("");
        estadoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        estadoLabel.setBounds(190, 90, 117, 16);
        contentPane.add(estadoLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Dni:");
        lblNewLabel_1.setBounds(55, 120, 61, 16);
        contentPane.add(lblNewLabel_1);
        
        JLabel dniLabel = new JLabel("");
        dniLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        dniLabel.setBounds(190, 120, 117, 16);
        contentPane.add(dniLabel);
        
        JLabel lblNewLabel_4 = new JLabel("Fecha de nacimiento:");
        lblNewLabel_4.setBounds(55, 150, 140, 16);
        contentPane.add(lblNewLabel_4);
        
        JLabel fechaNacLabel = new JLabel("");
        fechaNacLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        fechaNacLabel.setBounds(214, 150, 93, 16);
        contentPane.add(fechaNacLabel);
        
        JLabel lblNewLabel_5 = new JLabel("Fecha de contratación:");
        lblNewLabel_5.setBounds(55, 180, 147, 16);
        contentPane.add(lblNewLabel_5);
        
        JLabel fechaContratoLabel = new JLabel("");
        fechaContratoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        fechaContratoLabel.setBounds(214, 180, 93, 16);
        contentPane.add(fechaContratoLabel);
        
        JLabel lblNewLabel_6 = new JLabel("Nacionalidad:");
        lblNewLabel_6.setBounds(55, 210, 86, 16);
        contentPane.add(lblNewLabel_6);
        
        JLabel nacionalidadLabel = new JLabel("");
        nacionalidadLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nacionalidadLabel.setBounds(190, 210, 117, 16);
        contentPane.add(nacionalidadLabel);
        
        JLabel lblNewLabel_7 = new JLabel("Cargo:");
        lblNewLabel_7.setBounds(55, 240, 61, 16);
        contentPane.add(lblNewLabel_7);
        
        JLabel cargoLabel = new JLabel("");
        cargoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        cargoLabel.setBounds(140, 240, 167, 16);
        contentPane.add(cargoLabel);
        
        JButton volverButton = new JButton("Volver");
        volverButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//Carga la ventana anterior
        		VentanaEmpleados frame = new VentanaEmpleados(conexion);
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
        
        JLabel lblNewLabel_8 = new JLabel("SUS VISITAS GUIADAS");
        lblNewLabel_8.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
        lblNewLabel_8.setBounds(336, 90, 167, 16);
        contentPane.add(lblNewLabel_8);
        cargarDatos();
        
        //cargar datos a los labels
        nombreApellidoLabel.setText(empleado.getNombre() + " " + empleado.getApellido());
        estadoLabel.setText(empleado.getEstado());
        dniLabel.setText(empleado.getDni());
        fechaContratoLabel.setText(empleado.getFechaCon());
        fechaNacLabel.setText(empleado.getFechaNac());
        nacionalidadLabel.setText(empleado.getNacionalidad());
        cargoLabel.setText(empleado.getCargo());
        
        //Cargar tabla
        cargarTabla();
        int cantidad = visitas.size();
		String[][] d = new String[cantidad][6];
		String[] nombreColumnas = {"id", "estado", "Nombre", "max_clientes", "Punto Partida", "Tematica"};
		for (int i = 0; i < visitas.size(); i++) {
	            d[i][0] = String.valueOf(visitas.get(i).getId());
	            d[i][1] = String.valueOf(visitas.get(i).getEstado());
	            d[i][2] = String.valueOf(visitas.get(i).getNombre());
	            d[i][3] = String.valueOf(visitas.get(i).getMax_c());
	            d[i][4] = String.valueOf(visitas.get(i).getPunto_partida());
	            d[i][5] = String.valueOf(visitas.get(i).getTematica());
	    }
		table.setModel(new DefaultTableModel(d, nombreColumnas));
	}
	
	//Recupera los datos del empleado seleccionado
	public void cargarDatos() {
		try {
			//Prepara la sentencia y la ejecuta
			String query = "SELECT * FROM EMPLEADOS WHERE id = ?";
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			//Recupera los datos
			while(rs.next()) {
				empleado = new Empleado(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
			}
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERROR al recuperar los datos.");
		}
	}
	
	//Recupera los datos para cargar la tabla
	public void cargarTabla() {
		visitas.clear();
		try {
			//Prepara la sentencia y la ejecuta
			String query = "SELECT * FROM VISITAS_GUIADAS WHERE idempleado = ? AND estado = 'Alta'";
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			//Recupera los datos
			while(rs.next()) {
				visitas.add(new Visita(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getDouble(7))) ;
			}
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERROR al recuperar los datos.");
		}
	}
}
