
public class Cliente {
	int id;
	String estado;
	String dni;
	String nombre;
	String apellido;
	int edad;
	String profesion;
	int idagencia;
	String fecha;
	String hora;
	
	//Construtores
	public Cliente(int id, String estado, String dni, String nombre, String apellido, int edad, String profesion,int idagencia) {
		this.id = id;
		this.estado = estado;
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.edad = edad;
		this.profesion = profesion;
		this.idagencia = idagencia;
	}
	
	public Cliente(String estado, String dni, String nombre, String apellido, int edad, String profesion) {
		this.estado = estado;
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.edad = edad;
		this.profesion = profesion;
	}
	
	public Cliente(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
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
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	public String getProfesion() {
		return profesion;
	}
	public void setProfesion(String profesion) {
		this.profesion = profesion;
	}
	public int getIdagencia() {
		return idagencia;
	}
	public void setIdagencia(int idagencia) {
		this.idagencia = idagencia;
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
