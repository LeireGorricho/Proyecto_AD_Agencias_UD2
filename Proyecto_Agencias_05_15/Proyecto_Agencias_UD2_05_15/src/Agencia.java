
public class Agencia {
	String nombre;
	String fechaApertura;
	String direccion;
	String email;
	int telefono;
	
	//Contructores
	public Agencia(String nombre, String fechaApertura, String direccion, String email, int telefono) {
		super();
		this.nombre = nombre;
		this.fechaApertura = fechaApertura;
		this.direccion = direccion;
		this.email = email;
		this.telefono = telefono;
	}

	//getters y setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFechaApertura() {
		return fechaApertura;
	}

	public void setFechaApertura(String fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}
}
