/**
 * 
 */
package negocio;

/**
 * @author lm.gomezl
 *
 */
public class RFC1 {
	
	private long id_habitacion;
	
	private int suma;
	
	public RFC1()
	{
		this.id_habitacion = 0;
		this.suma = 0;
	}

	/**
	 * @return the id_habitacion
	 */
	public long getId_habitacion() {
		return id_habitacion;
	}

	/**
	 * @param id_habitacion the id_habitacion to set
	 */
	public void setId_habitacion(long id_habitacion) {
		this.id_habitacion = id_habitacion;
	}

	/**
	 * @return the suma
	 */
	public int getSuma() {
		return suma;
	}

	/**
	 * @param suma the suma to set
	 */
	public void setSuma(int suma) {
		this.suma = suma;
	}
	
	public String toString()
	{
		return "ID_Habitacion = " + id_habitacion + "; Suma = " + suma;
	}

}
