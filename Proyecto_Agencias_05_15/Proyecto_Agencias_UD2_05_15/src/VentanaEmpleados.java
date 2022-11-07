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

public class VentanaEmpleados extends JFrame {
	private JPanel contentPane;
	private static Connection conexion;
	private static List<Empleado> empleados = new ArrayList<Empleado>();
	private static JTable table;
	private JTextField filtroField;
	private static TableRowSorter<DefaultTableModel> sorter;

	/**
	 * Create the frame.
	 */
	public VentanaEmpleados(Connection conexion) {
		this.conexion = conexion;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("EMPLEADOS");
        lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 22));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(261, 25, 189, 22);
        contentPane.add(lblNewLabel);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(29, 85, 501, 262);
        contentPane.add(scrollPane);
        
        table = new JTable();
        scrollPane.setViewportView(table);
        
        JButton verButton = new JButton("Ver");
        verButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//Comprobar que se ha seleccionado un campo en la tabla y cagar la nueva ventana.
        		if (table.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Para ver el empleado debes seleccionar en la tabla.");
                } else {
                    int id = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
                    VentanaVerEmpleado frame = new VentanaVerEmpleado(conexion, id);
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
        		VentanaAnadirEmpleado frame = new VentanaAnadirEmpleado(conexion);
        		frame.setVisible(true);
        		dispose();
        	}
        });
        anadirButton.setBounds(550, 100, 117, 29);
        contentPane.add(anadirButton);
        
        JButton eliminarButton = new JButton("Dar de baja");
        eliminarButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//Comprobar que se ha seleccionado un campo en la tabla y dar de baja.
        		if (table.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Para dar de baja debes seleccionar en la tabla.");
                } else {
                    int id = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
                    darBajaEmpleado(id);
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
                    VentanaEditarEmpleado frame = new VentanaEditarEmpleado(conexion, id);
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
        
        filtroField = new JTextField();
        filtroField.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyReleased(KeyEvent e) {
        		//Filtros de busqueda para la tabla
        		sorter.setRowFilter(RowFilter.regexFilter("(?i)"+filtroField.getText()));
        	}
        });
        filtroField.setBounds(89, 54, 117, 20);
        contentPane.add(filtroField);
        filtroField.setColumns(10);
        
        JLabel lblNewLabel_1 = new JLabel("Buscar:");
        lblNewLabel_1.setBounds(29, 60, 64, 14);
        contentPane.add(lblNewLabel_1);
        cargarTabla();
	}

	//Funcion que carga la tabla con los datos
	public static void cargarTabla() {
		cargarDatos();
		int cantidad = empleados.size();
		String[][] d = new String[cantidad][6];
		String[] nombreColumnas = {"id", "Estado", "DNI", "Nombre", "Apellido", "Cargo"};
		for (int i = 0; i < empleados.size(); i++) {
	            d[i][0] = String.valueOf(empleados.get(i).getId());
	            d[i][1] = String.valueOf(empleados.get(i).getEstado());
	            d[i][2] = String.valueOf(empleados.get(i).getDni());
	            d[i][3] = String.valueOf(empleados.get(i).getNombre());
	            d[i][4] = String.valueOf(empleados.get(i).getApellido());
	            d[i][5] = String.valueOf(empleados.get(i).getCargo());
	    }
		DefaultTableModel modelo = new DefaultTableModel(d, nombreColumnas);
		table.setModel(modelo);
		table.setAutoCreateRowSorter(true);
		sorter = new TableRowSorter<>(modelo);	
		table.setRowSorter(sorter);
	}
	
	public static void cargarDatos() {
		empleados.clear();
		try {
			//Preparar sentencia y ejecutar
			Statement sentencia = conexion.createStatement();
			ResultSet resul = sentencia.executeQuery("SELECT * FROM EMPLEADOS");
			//Recuperar los datos
			while (resul.next()) {
				Empleado e = new Empleado(resul.getInt(1), resul.getString(2), resul.getString(3), resul.getString(4), resul.getString(5), resul.getString(6), resul.getString(7), resul.getString(8), resul.getString(9), resul.getInt(10));
				empleados.add(e);
			}
			resul.close();
			sentencia.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "ERROR al recuperar los datos.");
		}
	}
	
	//Funcion que da de baja a un empleado
	public void darBajaEmpleado(int id) {
		try {
			//Preparar sentencia y ejecutar
			String query = "UPDATE EMPLEADOS SET estado = ? WHERE id = ?";
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
