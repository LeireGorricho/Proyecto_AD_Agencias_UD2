import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaMetaDatos extends JFrame {
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public VentanaMetaDatos(Connection conexion) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("INFORMACIÓN SOBRE LA BASE DE DATOS");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        lblNewLabel.setBounds(17, 20, 425, 23);
        contentPane.add(lblNewLabel);
        
        JLabel nombre = new JLabel("");
        nombre.setBounds(41, 60, 183, 16);
        contentPane.add(nombre);
        
        JLabel driver = new JLabel("");
        driver.setBounds(41, 84, 340, 16);
        contentPane.add(driver);
        
        JLabel url = new JLabel("");
        url.setBounds(41, 112, 340, 16);
        contentPane.add(url);
        
        JLabel cliente = new JLabel("");
        cliente.setBounds(41, 139, 183, 16);
        contentPane.add(cliente);
        
        //carga los meta datos a la vista
        DatabaseMetaData dbmd;
        try {
			dbmd = conexion.getMetaData();
			nombre.setText("Nombre: " + dbmd.getDatabaseProductName());
			driver.setText("Driver: " + dbmd.getDriverName());
			url.setText("URL: " + dbmd.getURL());
			cliente.setText("Cliente: " + dbmd.getUserName());
			
			JButton btnNewButton = new JButton("Volver");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//Carga la ventana anterior
					MenuAgencia frame = new MenuAgencia(conexion);
					frame.setVisible(true);
					dispose();
				}
			});
			btnNewButton.setBounds(210, 227, 89, 23);
			contentPane.add(btnNewButton);
			
			JButton empleadosButton = new JButton("Empleados");
			empleadosButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//Carga la ventana para mostrar los meta datos de una tabla especifica
					VentanaMetaDatosTablas frame = new VentanaMetaDatosTablas(conexion, dbmd, "empleados");
					frame.setVisible(true);
					dispose();
				}
			});
			empleadosButton.setBounds(32, 182, 107, 23);
			contentPane.add(empleadosButton);
			
			JButton clientesButton = new JButton("Clientes");
			clientesButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//Carga la ventana para mostrar los meta datos de una tabla especifica
					VentanaMetaDatosTablas frame = new VentanaMetaDatosTablas(conexion, dbmd, "clientes");
					frame.setVisible(true);
					dispose();
				}
			});
			clientesButton.setBounds(149, 182, 95, 23);
			contentPane.add(clientesButton);
			
			JButton agenciaButton = new JButton("Agencia");
			agenciaButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//Carga la ventana para mostrar los meta datos de una tabla especifica
					VentanaMetaDatosTablas frame = new VentanaMetaDatosTablas(conexion, dbmd, "agencias");
					frame.setVisible(true);
					dispose();
				}
			});
			agenciaButton.setBounds(254, 182, 95, 23);
			contentPane.add(agenciaButton);
			
			JButton visitasButton = new JButton("Visitas");
			visitasButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//Carga la ventana para mostrar los meta datos de una tabla especifica
					VentanaMetaDatosTablas frame = new VentanaMetaDatosTablas(conexion, dbmd, "visitas_guiadas");
					frame.setVisible(true);
					dispose();
				}
			});
			visitasButton.setBounds(361, 182, 95, 23);
			contentPane.add(visitasButton);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERROR al recuperar los meta datos.");
		}
	}
}
