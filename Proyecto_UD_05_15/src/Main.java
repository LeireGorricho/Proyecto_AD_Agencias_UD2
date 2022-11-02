import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static void main(String[] args) {
		int opcion = -1;
		do {
			System.out.println("--- Selección de agencias --- \n 1. Visita Mundo \n 2. GuiaExperience \n 3. VIPGuies \n 4. Salir");
			System.out.println("Opción: ");
			try {
				opcion = Integer.parseInt(br.readLine());
				switch(opcion) {
					case 1:
						System.out.println("Entrando a Visita Mundo...");
						menuVisitaMundo();
						break;
					case 2:
						System.out.println("Entrando a GuiaExperience...");
						menuGuiaExperience();
						break;
					case 3:
						System.out.println("Entrando a VIPGuide...");
						menuVipGuies();
						break;
					case 4:
						System.out.println("Saliendo del sistema...");
						break;
					default:
						System.out.println("Valor incporrecto, inténtelo de nuevo");
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("ERROR en el formato de la entrada");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("ERROR");
			}
		} while(opcion != 4);
	}
	
	public static boolean login(Connection conexion) {
		String usuario = "";
		String contrasena = "";
		boolean acierto = false;
		try {
			System.out.println("Usuario: ");
			usuario = br.readLine();
			System.out.println("Contraseña: ");
			contrasena = br.readLine();
		} catch (IOException e) {
			System.out.println("ERROR en la lectura del usuario o contraseña");
		}
		try {
			Statement sentencia = conexion.createStatement();
			ResultSet resul = sentencia.executeQuery("SELECT * FROM usuarios WHERE usuario = '" + usuario + "'  AND clave = '" + contrasena + "'");
			while (resul.next()) {
				acierto = true;
			}
			
			resul.close();
			sentencia.close();
		} catch (SQLException e) {
			System.out.println("ERROR al intentar recuperar los datos.");
		}
		return acierto;
	}
	
	public static void menuVisitaMundo() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/Proyecto_AD2","root", "lgp200267");
			if (login(conexion)) {
			int opcion = -1;
				do {
					try {
						System.out.println("--- Menu de Visita Mundo --- \n 1. Gestion de empleados \n 2. Gestion de clientes \n 3. Gestion de visitas guiadas \n 4. Salir");
						System.out.println("Opción: ");
						opcion = Integer.parseInt(br.readLine());
						
						switch(opcion) {
						case 1:
							empleadosGestion(conexion);
							break;
						case 2:
							clientesGestion();
							break;
						case 3:
							visitasGestion();
							break;
						case 4:
							System.out.println("Saliendo de Visita Mundo...");
							break;
						default:
							System.out.println("Valor incorrecto, inténtelo de nuevo");
						break;
					}
					} catch (NumberFormatException e) {
						System.out.println("ERROR insete un valor numerico.");
					} catch (IOException e) {
						System.out.println("ERROR");			}
				} while (opcion != 4);
			}
		} catch (ClassNotFoundException e1) {
			System.out.println("ERROR no se a encontrado el controlador.");
		} catch (SQLException e1) {
			System.out.println("ERROR no se a podido establecer la conexion con la base de datos.");
		}
	}
	
	public static void menuGuiaExperience() {
		int opcion = -1;
		do {
			try {
				System.out.println("--- Menu de Visita Mundo --- \n 1. Gestion de empleados \n 2. Gestion de clientes \n 3. Gestion de visitas guiadas \n 4. Salir");
				System.out.println("Opción: ");
				opcion = Integer.parseInt(br.readLine());
				
				switch(opcion) {
				case 1:
					//empleadosGestion();
					break;
				case 2:
					clientesGestion();
					break;
				case 3:
					visitasGestion();
					break;
				case 4:
					System.out.println("Saliendo de Visita Mundo...");
					break;
				default:
					System.out.println("Valor incorrecto, inténtelo de nuevo");
				break;
				}
			} catch (NumberFormatException e) {
				System.out.println("ERROR insete un valor numerico.");
			} catch (IOException e) {
				System.out.println("ERROR");			}
		} while (opcion != 4);
	}
	
	public static void menuVipGuies() {
		int opcion = -1;
		do {
			try {
				System.out.println("--- Menu de Visita Mundo --- \n 1. Gestion de empleados \n 2. Gestion de clientes \n 3. Gestion de visitas guiadas \n 4. Salir");
				System.out.println("Opción: ");
				opcion = Integer.parseInt(br.readLine());
				
				switch(opcion) {
				case 1:
					//empleadosGestion();
					break;
				case 2:
					clientesGestion();
					break;
				case 3:
					visitasGestion();
					break;
				case 4:
					System.out.println("Saliendo de Visita Mundo...");
					break;
				default:
					System.out.println("Valor incorrecto, inténtelo de nuevo");
				break;
				}
			} catch (NumberFormatException e) {
				System.out.println("ERROR insete un valor numerico.");
			} catch (IOException e) {
				System.out.println("ERROR");			}
		} while (opcion != 4);
	}
	
	public static void empleadosGestion(Connection conexion) {
		int opcion = -1;
		do {
			try {
				System.out.println("--- Gestion de Empleados --- \n 1. Mostrar empleados \n 2. Nuevo empleado \n 3. Eliminar empleado \n 4. Editar empleado \n 5. Atras");
				System.out.println("Opción: ");
				opcion = Integer.parseInt(br.readLine());
				
				switch(opcion) {
				case 1:
					mostrarEmpleados(conexion);
					break;
				case 2:
					anadirEmpleado(conexion);
					break;
				case 3:
					eliminarEmpleado(conexion);
					break;
				case 4:
					editarEmpleado();
					break;
				case 5:
					System.out.println("Volviendo al menu anterior...");
					break;
				default:
					System.out.println("Valor incporrecto, inténtelo de nuevo");
				break;
				}
			} catch (NumberFormatException e) {
				System.out.println("ERROR insete un valor numerico.");
			} catch (IOException e) {
				System.out.println("ERROR");			
			}
		} while (opcion != 5);
	}
	
	public static void mostrarEmpleados(Connection conexion) {
		try {
			Statement sentencia = conexion.createStatement();
			ResultSet resul = sentencia.executeQuery("SELECT * FROM empleados");
			while (resul.next()){
				System.out.println ("ID: " + resul.getInt(1) + " "+ resul.getString(2)+ " " + resul.getString(3) + " " + resul.getString(4) + " " + resul.getString(5) + " " + resul.getString(6) + " " + resul.getString(7) + " " + resul.getString(8) + " " + resul.getInt(9));
			}
			resul.close();
			sentencia.close();
		} catch (SQLException e) {
			System.out.println("ERROR al recuperar los datos.");
		}
		
	}
	
	public static void anadirEmpleado(Connection conexion) {
		System.out.println("Cargando dato de empleados.");
	}
	
	public static void eliminarEmpleado(Connection conexion) {
		boolean eli = false;
		System.out.println("Cargando dato de empleados.");
		mostrarEmpleados(conexion);
		System.out.println("Cual es el id del empleado que quieres eliminar.");
		try {
			int id = Integer.parseInt(br.readLine());
			
			Statement sentencia = conexion.createStatement();
			ResultSet resul = sentencia.executeQuery("SELECT * FROM empleados");
			while (resul.next()){
				if (id == resul.getInt(1)) {
					eli = true;
				}
			}
			
			resul.close();
			sentencia.close();
			
			if (eli) {
				sentencia = conexion.createStatement();
				sentencia.executeQuery("DELETE FROM empleados WHERE id = " + id);
				System.out.println("Eliminado correctamente");
			}
			
			resul.close();
			sentencia.close();
		} catch (SQLException e) {
			e.printStackTrace();
			//System.out.println("ERROR al recuperar los datos.");
		} catch (NumberFormatException e) {
			System.out.println("ERROR introduce un numero.");
		} catch (IOException e) {
			System.out.println("");
		}
		
	}
	
	public static void editarEmpleado() {
		System.out.println("Cargando dato de empleados.");
	}
	
	public static void clientesGestion() {
		int opcion = -1;
		do {
			try {
				System.out.println("--- Gestion de Clientes --- \n 1. Mostrar clientes \n 2. Nuevo cliente \n 3. Eliminar cliente \n 4. Editar cliente \n 5. Atras");
				System.out.println("Opción: ");
				opcion = Integer.parseInt(br.readLine());
				
				switch(opcion) {
				case 1:
					mostrarClientes();
					break;
				case 2:
					anadirCliente();
					break;
				case 3:
					eliminarCliente();
					break;
				case 4:
					editarCliente();
					break;
				case 5:
					System.out.println("Volviendo al menu anterior...");
					break;
				default:
					System.out.println("Valor incporrecto, inténtelo de nuevo");
				break;
			}
			} catch (NumberFormatException e) {
				System.out.println("ERROR insete un valor numerico.");
			} catch (IOException e) {
				System.out.println("ERROR");			}
		} while (opcion != 5);
	}
	
	public static void mostrarClientes() {
		System.out.println("Cargando dato de clientes.");
	}
	
	public static void anadirCliente() {
		System.out.println("Cargando dato de clientes.");
	}
	
	public static void eliminarCliente() {
		System.out.println("Cargando dato de clientes.");
	}
	
	public static void editarCliente() {
		System.out.println("Cargando dato de clientes.");
	}
	
	public static void visitasGestion() {
		int opcion = -1;
		do {
			try {
				System.out.println("--- Gestion de Visitas Guiadas --- \n 1. Mostrar visitas \n 2. Nueva visita \n 3. Eliminar visita \n 4. Editar visita \n 5. Atras");
				System.out.println("Opción: ");
				opcion = Integer.parseInt(br.readLine());
				
				switch(opcion) {
				case 1:
					mostrarVisitas();
					break;
				case 2:
					anadirVisita();
					break;
				case 3:
					eliminarVisita();
					break;
				case 4:
					editarVisita();
					break;
				case 5:
					System.out.println("Volviendo al menu anterior...");
					break;
				default:
					System.out.println("Valor incporrecto, inténtelo de nuevo");
				break;
			}
			} catch (NumberFormatException e) {
				System.out.println("ERROR insete un valor numerico.");
			} catch (IOException e) {
				System.out.println("ERROR");			}
		} while (opcion != 5);
	}
	
	public static void mostrarVisitas() {
		System.out.println("Cargando dato de clientes.");
	}
	
	public static void anadirVisita() {
		System.out.println("Cargando dato de clientes.");
	}
	
	public static void eliminarVisita() {
		System.out.println("Cargando dato de clientes.");
	}
	
	public static void editarVisita() {
		System.out.println("Cargando dato de clientes.");
	}
}
