package negocio;

/**
 * 
 * @author luisgomez
 *
 */
public class Gasto implements VOGasto {

	private long id;
	
	private long idHabitacion;
	
	private long idProducto;
	
	/**
	 * Constructor de gasto
	 */
	public Gasto(){
		this.id=0;
		this.idHabitacion = 0;
		this.idProducto = 0;
	}

	/**
	 * @param idHabitacion
	 * @param idProducto
	 */
	public Gasto(long id, long idHabitacion, long idProducto) {
		this.id = id;
		this.idHabitacion = idHabitacion;
		this.idProducto = idProducto;
	}
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the idHabitacion
	 */
	public long getIdHabitacion() {
		return idHabitacion;
	}

	/**
	 * @param idHabitacion the idHabitacion to set
	 */
	public void setIdHabitacion(long idHabitacion) {
		this.idHabitacion = idHabitacion;
	}

	/**
	 * @return the idProducto
	 */
	public long getIdProducto() {
		return idProducto;
	}

	/**
	 * @param idProducto the idProducto to set
	 */
	public void setIdProducto(long idProducto) {
		this.idProducto = idProducto;
	}
	
	public String toString()
	{
		return "Gasto para la habitacion = " + idHabitacion + ", con el item = " + idProducto;
	}
	
}
