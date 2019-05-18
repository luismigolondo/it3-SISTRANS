package negocio;

public class RFC10 {

	private long cedula;
	
	private long total_servicios;
	
	public RFC10(){
		this.cedula=0;
		this.total_servicios=0;
	}

	public long getCedula() {
		return cedula;
	}

	public void setCedula(long cedula) {
		this.cedula = cedula;
	}

	public long getTotal_servicios() {
		return total_servicios;
	}

	public void setTotal_servicios(long total_servicios) {
		this.total_servicios = total_servicios;
	}
	
	public String toString(){
		return "La cedula " + cedula + " ha consumido " + total_servicios;
	}
	
	
}
