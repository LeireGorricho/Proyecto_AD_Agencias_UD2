import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class MenuAgencia extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public MenuAgencia(Connection conexion) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 315);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("MENÚ AGENCIA");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        lblNewLabel.setBounds(121, 19, 192, 25);
        contentPane.add(lblNewLabel);
        
        JButton datosAgenciaButton = new JButton("Datos de la agencia");
        datosAgenciaButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//lamada a otra ventana
        		VentanaDatosAgencia frame = new VentanaDatosAgencia(conexion);
        		frame.setVisible(true);
        		dispose();
        	}
        });
        datosAgenciaButton.setBounds(128, 55, 175, 29);
        contentPane.add(datosAgenciaButton);
        
        JButton visitasGuiadasButton = new JButton("Visitas guiadas");
        visitasGuiadasButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//lamada a otra ventana
        		VentanaVisitasGuiadas frame = new VentanaVisitasGuiadas(conexion);
        		frame.setVisible(true);
        		dispose();
        	}
        });
        visitasGuiadasButton.setBounds(128, 90, 175, 29);
        contentPane.add(visitasGuiadasButton);
        
        JButton empleadosButton = new JButton("Empleados");
        empleadosButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//lamada a otra ventana
        		VentanaEmpleados frame = new VentanaEmpleados(conexion);
        		frame.setVisible(true);
        		dispose();
        	}
        });
        empleadosButton.setBounds(128, 125, 175, 29);
        contentPane.add(empleadosButton);
        
        JButton clientesButton = new JButton("Clientes");
        clientesButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//lamada a otra ventana
        		VentanaClientes frame = new VentanaClientes(conexion);
        		frame.setVisible(true);
        		dispose();
        	}
        });
        clientesButton.setBounds(128, 160, 175, 29);
        contentPane.add(clientesButton);
        
        JButton metadatosButton = new JButton("Metadatos de la bd");
        metadatosButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//lamada a otra ventana
        		VentanaMetaDatos frame = new VentanaMetaDatos(conexion);
        		frame.setVisible(true);
        		dispose();
        	}
        });
        metadatosButton.setBounds(128, 195, 175, 29);
        contentPane.add(metadatosButton);
        
        JButton salirButton = new JButton("Salir de la agencia");
        salirButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//cerrar la conexion y la sesion de esa base de datos
        		try {
					conexion.close();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "ERROR al cerrar la base de datos.");
				}
        		VentanaPrincipal frame = new VentanaPrincipal();
        		frame.setVisible(true);
        		dispose();
        	}
        });
        salirButton.setBounds(128, 230, 175, 29);
        contentPane.add(salirButton);
	}

}
