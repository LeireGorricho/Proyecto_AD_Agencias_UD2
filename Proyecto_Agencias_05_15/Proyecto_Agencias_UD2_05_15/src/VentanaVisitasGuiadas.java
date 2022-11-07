import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class VentanaVisitasGuiadas extends JFrame {

	private JPanel contentPane;
	private static JTable table;
	private static List<Visita> visitas = new ArrayList<Visita>();
	private static Connection conexion;
	private JTextField filtroField;
	private static TableRowSorter<DefaultTableModel> sorter;

	/**
	 * Create the frame.
	 */
	public VentanaVisitasGuiadas(Connection conexion) {
		this.conexion = conexion;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("VISITAS GUIADAS");
        lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 22));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(221, 25, 228, 22);
        contentPane.add(lblNewLabel);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(29, 93, 501, 254);
        contentPane.add(scrollPane);
        
        table = new JTable();
        scrollPane.setViewportView(table);
        
        JButton verButton = new JButton("Ver");
        verButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//Comprobar que se ha seleccionado un campo en la tabla y cagar la nueva ventana.
        		if (table.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Para ver la visita debes seleccionar en la tabla.");
                } else {
                	int id = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
	        		VentanaVerVisita frame = new VentanaVerVisita(conexion, id);
	        		frame.setVisible(true);
	        		dispose();
                }
        	}
        });
        verButton.setBounds(550, 70, 117, 29);
        contentPane.add(verButton);
        
        JButton anadirButton = new JButton("Añadir");
        anadirButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//Redireccion a otra ventana.
        		VentanaAnadirVisita frame = new VentanaAnadirVisita(conexion);
        		frame.setVisible(true);
        		dispose();
        	}
        });
        anadirButton.setBounds(550, 100, 117, 29);
        contentPane.add(anadirButton);
        
        JButton eliminarButton = new JButton("Eliminar");
        eliminarButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//Comprobar que se ha seleccionado un campo en la tabla y dar de baja.
        		if (table.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Para dar de baja debes seleccionar en la tabla.");
                } else {
                    int id = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
                    darBajaVisita(id);
                }
        	}
        });
        eliminarButton.setBounds(550, 130, 117, 29);
        contentPane.add(eliminarButton);
        
        JButton editarButton = new JButton("Editar");
        editarButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//Comprobar que se ha seleccionado un campo en la tabla y cagar la nueva ventana.
        		if (table.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Para editar debes seleccionar en la tabla.");
                } else {
                    int id = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
                    VentanaEditarVisita frame = new VentanaEditarVisita(conexion, id);
                    frame.setVisible(true);
                    dispose();
                }
        	}
        });
        editarButton.setBounds(550, 160, 117, 29);
        contentPane.add(editarButton);
        
        JButton volverButton = new JButton("Volver");
        volverButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//Cargar ventana anterior
        		MenuAgencia frame = new MenuAgencia(conexion);
        		frame.setVisible(true);
        		dispose();
        	}
        });
        volverButton.setBounds(550, 322, 117, 29);
        contentPane.add(volverButton);
        
        JLabel lblNewLabel_1 = new JLabel("Buscar:");
        lblNewLabel_1.setBounds(29, 68, 64, 14);
        contentPane.add(lblNewLabel_1);
        
        filtroField = new JTextField();
        filtroField.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyReleased(KeyEvent e) {
        		//Filtros de busqueda para la tabla
        		sorter.setRowFilter(RowFilter.regexFilter("(?i)"+filtroField.getText()));
        	}
        });
        filtroField.setColumns(10);
        filtroField.setBounds(87, 62, 117, 20);
        contentPane.add(filtroField);
        cargarTabla();
	}
	
	//Funcion que carga la tabla con los datos
	public static void cargarTabla() {
		cargarDatos();
		int cantidad = visitas.size();
		String[][] d = new String[cantidad][9];
		String[] nombreColumnas = {"id", "Estado", "Nombre", "Max Clientes","Tematica", "Coste"};
		for (int i = 0; i < visitas.size(); i++) {
	            d[i][0] = String.valueOf(visitas.get(i).getId());
	            d[i][1] = String.valueOf(visitas.get(i).getEstado());
	            d[i][2] = String.valueOf(visitas.get(i).getNombre());
	            d[i][3] = String.valueOf(visitas.get(i).getMax_c());
	            d[i][4] = String.valueOf(visitas.get(i).getTematica());
	            d[i][5] = String.valueOf(visitas.get(i).getCoste());
	    }
		DefaultTableModel modelo = new DefaultTableModel(d, nombreColumnas);
		table.setModel(modelo);
		table.setAutoCreateRowSorter(true);
		sorter = new TableRowSorter<>(modelo);	
		table.setRowSorter(sorter);
	}
	
	public static void cargarDatos() {
		visitas.clear();
		try {
			//Preparar sentencia y ejecutar
			Statement sentencia = conexion.createStatement();
			ResultSet resul = sentencia.executeQuery("SELECT * FROM VISITAS_GUIADAS");
			//Recuperar los datos
			while (resul.next()) {
				Visita v = new Visita(resul.getInt(1), resul.getString(2), resul.getString(3), resul.getInt(4), resul.getString(5), resul.getString(6), resul.getDouble(7), resul.getInt(8), resul.getInt(9));
				visitas.add(v);
			}
			resul.close();
			sentencia.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERROR al recuperar los datos.");
		}
	}
	
	//Funcion que da de baja a una visita
	public void darBajaVisita(int id) {
		try {
			//Preparar sentencia y ejecutar
			String query = "UPDATE VISITAS_GUIADAS SET estado = ? WHERE id = ?";
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setString(1, "Baja");
			ps.setInt(2, id);
			ps.execute();
			JOptionPane.showMessageDialog(null, "Se le ha dado de baja corectamente.");
			ps.close();
			cargarTabla();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERROR al insertar los datos.");
		}
	}
}
