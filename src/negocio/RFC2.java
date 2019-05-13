/**
 * 
 */
package negocio;

/**
 * @author lm.gomezl
 *
 */
public class RFC2 {

	private long id_producto;

	private int count;

	public RFC2()
	{
		this.id_producto = 0;
		this.count = 0;
	}

	/**
	 * @return the id_habitacion
	 */
	public long getId_prod() {
		return id_producto;
	}

	/**
	 * @param id_habitacion the id_habitacion to set
	 */
	public void setId_prod(long id_habitacion) {
		this.id_producto = id_habitacion;
	}

	/**
	 * @return the suma
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param suma the suma to set
	 */
	public void setCount(int suma) {
		this.count = suma;
	}

	public String toString()
	{
		return "ID_Producto = " + id_producto + "; Count = " + count;
	}

}
