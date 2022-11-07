
public class Visita {
	int id;
	String estado;
	String nombre;
	int max_c;
	String punto_partida;
	String tematica;
	double coste;
	int idagencia;
	int idempleado;
	String fecha;
	String hora;
	
	//Contructores
	public Visita(int id, String estado, String nombre, int max_c, String punto_partida, String tematica, double coste, int idagencia, int idempleado) {
		this.id = id;
		this.estado = estado;
		this.nombre = nombre;
		this.max_c = max_c;
		this.punto_partida = punto_partida;
		this.tematica = tematica;
		this.coste = coste;
		this.idagencia = idagencia;
		this.idempleado = idempleado;
	}
	
	public Visita(int id, String estado, String nombre, int max_c, String punto_partida, String tematica, double coste) {
		this.id = id;
		this.estado = estado;
		this.nombre = nombre;
		this.max_c = max_c;
		this.punto_partida = punto_partida;
		this.tematica = tematica;
		this.coste = coste;
	}
	
	public Visita(String estado, String nombre, int max_c, String punto_partida, String tematica, double coste) {
		this.estado = estado;
		this.nombre = nombre;
		this.max_c = max_c;
		this.punto_partida = punto_partida;
		this.tematica = tematica;
		this.coste = coste;
	}
	
	public Visita(int id, String nombre) {
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getMax_c() {
		return max_c;
	}

	public void setMax_c(int max_c) {
		this.max_c = max_c;
	}

	public String getPunto_partida() {
		return punto_partida;
	}

	public void setPunto_partida(String punto_partida) {
		this.punto_partida = punto_partida;
	}

	public String getTematica() {
		return tematica;
	}

	public void setTematica(String tematica) {
		this.tematica = tematica;
	}

	public double getCoste() {
		return coste;
	}

	public void setCoste(double coste) {
		this.coste = coste;
	}

	public int getIdagencia() {
		return idagencia;
	}

	public void setIdagencia(int idagencia) {
		this.idagencia = idagencia;
	}

	public int getIdempleado() {
		return idempleado;
	}

	public void setIdempleado(int idempleado) {
		this.idempleado = idempleado;
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
