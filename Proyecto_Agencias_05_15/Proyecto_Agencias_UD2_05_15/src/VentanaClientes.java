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
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.RowFilter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class VentanaClientes extends JFrame {
	private JPanel contentPane;
	private static JTable table;
	private static List<Cliente> clientes = new ArrayList<Cliente>();
	private static Connection conexion;
	private JTextField filtroField;
	private static TableRowSorter<DefaultTableModel> sorter;

	/**
	 * Create the frame.
	 */
	public VentanaClientes(Connection conexion) {
		this.conexion = conexion;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("CLIENTES");
        lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 22));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(242, 25, 189, 22);
        contentPane.add(lblNewLabel);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(29, 87, 501, 260);
        contentPane.add(scrollPane);
        
        table = new JTable();
        scrollPane.setViewportView(table);
        
        JButton verButton = new JButton("Ver");
        verButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//Comprobar que se ha seleccionado un campo en la tabla y cagar la nueva ventana.
        		if (table.getSelectedRow() == -1) {
        			JOptionPane.showMessageDialog(null, "Para ver debes seleccionar en la tabla.");
        		} else {
        			int id = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
	        		VentanaVerCliente frame = new VentanaVerCliente(conexion, id);
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
        		VentanaAnadirCliente frame = new VentanaAnadirCliente(conexion);
        		frame.setVisible(true);
        		dispose();
        	}
        });
        anadirButton.setBounds(550, 100, 117, 29);
        contentPane.add(anadirButton);
        
        JButton eliminarButton = new JButton("Dar de baja");
        eliminarButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//Comprobar que se ha seleccionado un campo en la tabla y cagar la nueva ventana.
        		if (table.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Para dar de baja debes seleccionar en la tabla.");
                } else {
                    int id = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
                    darBajaCliente(id);
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
	        		VentanaEditarCliente frame = new VentanaEditarCliente(conexion, id);
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
        
        JButton apuntarButton = new JButton("Apuntarse");
        apuntarButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//Cargar ventana de apuntar
        		VentanaApuntar frame = new VentanaApuntar(conexion);
        		frame.setVisible(true);
        		dispose();
        	}
        });
        apuntarButton.setBounds(550, 189, 117, 29);
        contentPane.add(apuntarButton);
        
        filtroField = new JTextField();
        filtroField.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyReleased(KeyEvent e) {
        		//Filtros de busqueda para la tabla
        		sorter.setRowFilter(RowFilter.regexFilter("(?i)"+filtroField.getText()));
        	}
        });
        filtroField.setColumns(10);
        filtroField.setBounds(85, 56, 117, 20);
        contentPane.add(filtroField);
        
        JLabel lblNewLabel_1 = new JLabel("Buscar:");
        lblNewLabel_1.setBounds(29, 62, 64, 14);
        contentPane.add(lblNewLabel_1);
        cargarTabla();
	}
	
	//Funcion que carga la tabla con los datos
	public static void cargarTabla() {
		cargarDatos();
		int cantidad = clientes.size();
		String[][] d = new String[cantidad][8];
		String[] nombreColumnas = {"id", "estado", "DNI", "Nombre", "Apellido"};
		for (int i = 0; i < clientes.size(); i++) {
	            d[i][0] = String.valueOf(clientes.get(i).getId());
	            d[i][1] = String.valueOf(clientes.get(i).getEstado());
	            d[i][2] = String.valueOf(clientes.get(i).getDni());
	            d[i][3] = String.valueOf(clientes.get(i).getNombre());
	            d[i][4] = String.valueOf(clientes.get(i).getApellido());
	    }
		DefaultTableModel modelo = new DefaultTableModel(d, nombreColumnas);
		table.setModel(modelo);
		table.setAutoCreateRowSorter(true);
		sorter = new TableRowSorter<>(modelo);	
		table.setRowSorter(sorter);
	}
	
	//Funcion que recoge los datos de la base de datos
	public static void cargarDatos() {
		clientes.clear();
		try {
			//Preparar sentencia y ejecutar
			Statement sentencia = conexion.createStatement();
			ResultSet resul = sentencia.executeQuery("SELECT * FROM CLIENTES");
			//Recuperar los datos
			while (resul.next()) {
				Cliente c = new Cliente(resul.getInt(1), resul.getString(2), resul.getString(3), resul.getString(4), resul.getString(5), resul.getInt(6), resul.getString(7), resul.getInt(8));
				clientes.add(c);
			}
			resul.close();
			sentencia.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERROR al recuperar los datos.");
		}
	}
	
	//Funcion que da de baja a un cliente
	public void darBajaCliente(int id) {
		try {
			//Preparar sentencia y ejecutar
			String query = "UPDATE CLIENTES SET estado = ? WHERE id = ?";
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setString(1, "Baja");
			ps.setInt(2, id);
			ps.execute();
			JOptionPane.showMessageDialog(null, "Se le ha dado de baja correctamente.");
			ps.close();
			cargarTabla();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERROR al dar de baja.");
		}
    }
}
