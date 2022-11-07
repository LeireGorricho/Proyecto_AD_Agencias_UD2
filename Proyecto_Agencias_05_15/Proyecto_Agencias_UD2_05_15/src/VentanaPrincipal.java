import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.h2.message.DbException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class VentanaPrincipal extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal frame = new VentanaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("VENTANA PRINCIPAL");
        lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        lblNewLabel.setBounds(120, 34, 214, 16);
        contentPane.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("¿A qué agencia quieres conectarte?");
        lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
        lblNewLabel_1.setBounds(128, 62, 184, 16);
        contentPane.add(lblNewLabel_1);
        
        JButton agencia1Button = new JButton("Agencia MySQL");
        agencia1Button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
        			//Creacion del conector y llamar a la ventana mandandole el conector
	        		Class.forName("com.mysql.cj.jdbc.Driver");
	    			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/Proyecto_AD","root", "admin");
	        		VentanaLogin frame = new VentanaLogin(conexion);
	        		frame.setVisible(true);
	        		dispose();
        		} catch (ClassNotFoundException e1) {
        			JOptionPane.showMessageDialog(null, "No se ha encontrado el conector");
        		} catch (SQLException e1) {
        			JOptionPane.showMessageDialog(null, "ERROR al conectarse a la base de datos");
				}
        	}
        });
        agencia1Button.setBounds(120, 90, 192, 29);
        contentPane.add(agencia1Button);
        
        JButton agencia2Button = new JButton("Agencia H2");
        agencia2Button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
        			//Creacion del conector y llamar a la ventana mandandole el conector
	        		Class.forName("org.h2.Driver");
	    			Connection conexion = DriverManager.getConnection("jdbc:h2:C:\\Users\\omega\\Desktop\\Bases de Datos\\BBDD", "admin","admin"); 
	        		VentanaLogin frame = new VentanaLogin(conexion);
	        		frame.setVisible(true);
	        		dispose();
        		} catch (ClassNotFoundException e1) {
        			JOptionPane.showMessageDialog(null, "No se ha encontrado el conector");
        		} catch (SQLException e1) {
        			JOptionPane.showMessageDialog(null, "No se puede conectar a la base de datos por que ya esta en uso");
				}
        	}
        });
        agencia2Button.setBounds(120, 120, 192, 29);
        contentPane.add(agencia2Button);
        
        JButton agencia3Button = new JButton("Agencia MariaDB");
        agencia3Button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
        			//Creacion del conector y llamar a la ventana mandandole el conector
	        		Class.forName("com.mysql.cj.jdbc.Driver");
	    			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3380/proyecto", "root", "admin"); 
	        		VentanaLogin frame = new VentanaLogin(conexion);
	        		frame.setVisible(true);
	        		dispose();
        		} catch (ClassNotFoundException e1) {
        			JOptionPane.showMessageDialog(null, "No se ha encontrado el conector");
        		} catch (SQLException e1) {
        			JOptionPane.showMessageDialog(null, "ERROR al conectarse a la base de datos");
				}
        	}
        });
        agencia3Button.setBounds(120, 150, 192, 29);
        contentPane.add(agencia3Button);
        
        JButton agencia4Button = new JButton("Agencia Oracle");
        agencia4Button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
        			//Creacion del conector y llamar a la ventana mandandole el conector
	        		Class.forName("oracle.jdbc.driver.OracleDriver");
	    			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "proyecto","123"); 
	        		VentanaLogin frame = new VentanaLogin(conexion);
	        		frame.setVisible(true);
	        		dispose();
        		} catch (ClassNotFoundException e1) {
        			JOptionPane.showMessageDialog(null, "No se ha encontrado el conector");
        		} catch (SQLException e1) {
        			JOptionPane.showMessageDialog(null, "ERROR al conectarse a la base de datos");
				}
        	}
        });
        agencia4Button.setBounds(120, 180, 192, 29);
        contentPane.add(agencia4Button);
        
        JButton salirButton = new JButton("Salir");
        salirButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//Boton para cerrar la aplicacion
        		dispose();
        	}
        });
        salirButton.setBounds(160, 210, 117, 29);
        contentPane.add(salirButton);
	}

}
