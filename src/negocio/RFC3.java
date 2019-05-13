/**
 * 
 */
package negocio;

/**
 * @author lm.gomezl
 *
 */
public class RFC3 {
	
	private long id_habitacion;
	
	public RFC3() {
		this.setId_habitacion(0);
	}

	public long getId_habitacion() {
		return id_habitacion;
	}

	public void setId_habitacion(long id_habitacion) {
		this.id_habitacion = id_habitacion;
	}

	public String toString() {
		return "Id habitacion = " + id_habitacion;
	}
	
}
