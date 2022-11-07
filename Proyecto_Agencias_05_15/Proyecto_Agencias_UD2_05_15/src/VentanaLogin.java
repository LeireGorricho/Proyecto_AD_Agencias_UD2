import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.awt.event.ActionEvent;

public class VentanaLogin extends JFrame {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private JPanel contentPane;
	private Usuario usuario;
	/**
	 * Create the frame.
	 */
	public VentanaLogin(Connection conexion) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("INICIO DE SESIÓN");
        lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        lblNewLabel.setBounds(136, 25, 179, 25);
        contentPane.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Usuario:");
        lblNewLabel_1.setBounds(103, 65, 61, 16);
        contentPane.add(lblNewLabel_1);
        
        JTextField usuarioField = new JTextField();
        usuarioField.setBounds(100, 90, 240, 26);
        contentPane.add(usuarioField);
        usuarioField.setColumns(10);
        
        JLabel lblNewLabel_2 = new JLabel("Contraseña:");
        lblNewLabel_2.setBounds(103, 140, 81, 16);
        contentPane.add(lblNewLabel_2);
        
        JTextField contrasenaField = new JTextField();
        contrasenaField.setBounds(100, 165, 240, 26);
        contentPane.add(contrasenaField);
        contrasenaField.setColumns(10);
        
        JButton iniciarSesionButton = new JButton("Iniciar sesión");
        iniciarSesionButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//Comprueba lo que le ha devuelto la funcion y si es acertado guarda el log
        		try {
	        		if (login(conexion, usuarioField.getText(), contrasenaField.getText())) {
	        			String query = "INSERT INTO LOG(idusuario, fechahora) VALUES(?, ?)";
	        			PreparedStatement ps = conexion.prepareStatement(query);
	        			ps.setInt(1, usuario.getId());
	        			ps.setString(2, LocalDateTime.now().toString());
	        			ps.execute();
	        			MenuAgencia frame = new MenuAgencia(conexion);
	            		frame.setVisible(true);
	            		dispose();
	        		} else {
	        			JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecto.");
	        		}
        		} catch (SQLException ex) {
        			ex.printStackTrace();
        		}
        	}
        });
        iniciarSesionButton.setBounds(75, 219, 140, 29);
        contentPane.add(iniciarSesionButton);
        
        JButton salirButton = new JButton("Cancelar");
        salirButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//Carga la ventana anterior
        		VentanaPrincipal frame = new VentanaPrincipal();
        		frame.setVisible(true);
        		dispose();
        	}
        });
        salirButton.setBounds(235, 219, 140, 29);
        contentPane.add(salirButton);
	}

	//Devuelve un bolea dependiendo de si ha encontrado el usuario y contrasena en la base de datos
	public boolean login(Connection conexion, String nombre, String contrasena) {
		boolean acierto = false;
		try {
			//Prepara sentencia y ejecuta
			String query = "SELECT * FROM USUARIOS WHERE usuario = ? AND clave = ?";
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setString(1, nombre);
			ps.setString(2, contrasena);
			ResultSet resul = ps.executeQuery();
			//Recupera datos
			while (resul.next()) {
				usuario = new Usuario(resul.getInt(1), resul.getString(2));
				acierto = true;
			}
			resul.close();
			ps.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERROR al recuperar los datos.");
		}
		return acierto;
	}
}
