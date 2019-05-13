package negocio;

public class ReservaConvencion implements VOReservaConvencion {
	
	/**
	 * id
	 */
	private long id;
	
	/**
	 * idServicio
	 */
	private long idServicio;
	
	/**
	 * idConvencion
	 */
	private long idConvencion;
	
	private long idHabitacion;

	public ReservaConvencion()
	{
		this.id = 0;
		this.idServicio = 0;
		this.idConvencion = 0;
		this.idHabitacion=0;
	}
		
	/**
	 * @param id
	 * @param idServicio
	 * @param idConvencion
	 */
	public ReservaConvencion(long id, long idServicio, long idHabitacion, long idConvencion) {
		this.id = id;
		this.idServicio = idServicio;
		this.idHabitacion=idHabitacion;
		this.idConvencion = idConvencion;
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
	 * @return the idServicio
	 */
	public long getIdServicio() {
		return idServicio;
	}

	/**
	 * @param idServicio the idServicio to set
	 */
	public void setIdServicio(long idServicio) {
		this.idServicio = idServicio;
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
	 * @return the idConvencion
	 */
	public long getIdConvencion() {
		return idConvencion;
	}

	/**
	 * @param idConvencion the idConvencion to set
	 */
	public void setIdConvencion(long idConvencion) {
		this.idConvencion = idConvencion;
	}
	
	
	
}
