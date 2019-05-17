/**
 * 
 */
package negocio;

/**
 * @author lm.gomezl
 *
 */
public class RFC7 {

	private long cedula;
	
	private long total_consumido;
	
	public RFC7 () {
		this.cedula = 0;
		this.total_consumido = 0;
	}

	/**
	 * @return the cedula
	 */
	public long getCedula() {
		return cedula;
	}

	/**
	 * @param cedula the cedula to set
	 */
	public void setCedula(long cedula) {
		this.cedula = cedula;
	}

	/**
	 * @return the total_consumido
	 */
	public long getTotal_consumido() {
		return total_consumido;
	}

	/**
	 * @param total_consumido the total_consumido to set
	 */
	public void setTotal_consumido(long total_consumido) {
		this.total_consumido = total_consumido;
	}
	
	public String toString() {
		return "La cedula " + cedula + " ha consumido " + total_consumido;
	}

}
