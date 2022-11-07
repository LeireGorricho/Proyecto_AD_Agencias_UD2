import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class VentanaMetaDatosTablas extends JFrame {
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public VentanaMetaDatosTablas(Connection conexion, DatabaseMetaData dbmd, String tabla) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel tituloLabel = new JLabel("New label");
		tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
		tituloLabel.setBounds(152, 11, 134, 14);
		contentPane.add(tituloLabel);
		
		JLabel tablaLabel = new JLabel("");
		tablaLabel.setBounds(30, 37, 371, 229);
		contentPane.add(tablaLabel);
		
		JButton atrasButton = new JButton("Atras");
		atrasButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaMetaDatos frame = new VentanaMetaDatos(conexion);
				frame.setVisible(true);
				dispose();
			}
		});
		atrasButton.setBounds(175, 277, 89, 23);
		contentPane.add(atrasButton);
		
		tituloLabel.setText(tabla.toUpperCase());
		try {
			tablaLabel.setText("<html>");
			ResultSet tablas = dbmd.getTables(null, null, null, null);
			while(tablas.next()) {
				if (tablas.getString(3) == tabla) {
					System.out.println(tablas.getString(1));
					tablaLabel.setText(tablaLabel.getText() + tablas.getString(2) + " Nombre: " + tablas.getString(3) + " Clave: " + tablas.getString(9));
				}
			}
			ResultSet columnas = dbmd.getColumns(null, null, tabla, null);
			while(columnas.next()) {
				System.out.println(columnas.getString("COLUMN_NAME"));
				tablaLabel.setText(tablaLabel.getText() + "Columna: " + columnas.getString("COLUMN_NAME") + " Tipo: " + columnas.getString("TYPE_NAME") + " Tamaño: " + columnas.getString("COLUMN_SIZE") + " Null?: " + columnas.getString("IS_NULLABLE") + "<br>");	
			}
			tablaLabel.setText(tablaLabel.getText() + "</html>");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERROR al recuperar los meta datos de la tabla intentalo mas tarde.");
		}
	}
}
