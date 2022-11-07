
public class Empleado {
	int id;
	String estado;
	String dni;
	String nombre;
	String apellido;
	String fechaNac;
	String fechaCon;
	String nacionalidad;
	String cargo;
	int idAgencia;
	
	//Constructores
	public Empleado(int id, String estado, String dni, String nombre, String apellido, String fechaNac, String fechaCon, String nacionalidad, String cargo, int idAgencia) {
		super();
		this.id = id;
		this.estado = estado;
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNac = fechaNac;
		this.fechaCon = fechaCon;
		this.nacionalidad = nacionalidad;
		this.cargo = cargo;
		this.idAgencia = idAgencia;
	}
	
	public Empleado(int id, String estado, String dni, String nombre, String apellido, String fechaNac, String fechaCon, String nacionalidad, String cargo) {
		super();
		this.id = id;
		this.estado = estado;
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNac = fechaNac;
		this.fechaCon = fechaCon;
		this.nacionalidad = nacionalidad;
		this.cargo = cargo;
	}
	
	public Empleado(String estado, String dni, String nombre, String apellido, String fechaNac, String fechaCon, String nacionalidad, String cargo) {
		this.estado = estado;
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNac = fechaNac;
		this.fechaCon = fechaCon;
		this.nacionalidad = nacionalidad;
		this.cargo = cargo;
	}	
	
	public Empleado() {
	}
	
	
	//Getters y setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getFechaNac() {
		return fechaNac;
	}
	public void setFechaNac(String fechaNac) {
		this.fechaNac = fechaNac;
	}
	public String getFechaCon() {
		return fechaCon;
	}
	public void setFechaCon(String fechaCon) {
		this.fechaCon = fechaCon;
	}
	public String getNacionalidad() {
		return nacionalidad;
	}
	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public int getIdAgencia() {
		return idAgencia;
	}
	public void setIdAgencia(int idAgencia) {
		this.idAgencia = idAgencia;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return nombre;
	}
	
}
