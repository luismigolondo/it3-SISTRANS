/**
 * 
 */
package interfaz;

/**
 * @author lm.gomezl
 *
 */
public class RFC9 {
	private long cedula;

	private long num_reservas;

	public RFC9() {
		this.cedula = 0;
		this.num_reservas = 0;
	}

	public long getCedula() {
		return cedula;
	}

	public void setCedula(long cedula) {
		this.cedula = cedula;
	}

	public long getNum_reservas() {
		return num_reservas;
	}

	public void setNum_reservas(long num_reservas) {
		this.num_reservas = num_reservas;
	}
	
	public String toString()
	{
		return "La cedula: " + cedula + " ha consumido el servicio " + num_reservas + " veces.";
	}
}
