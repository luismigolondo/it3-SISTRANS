package negocio;

public class RFC10 {

	private long semana;
	
	private long id_servicio_max;
	
	private long maximo;
	
	private long id_servicio_min;
	
	private long minimo;
	
	private long id_habitacion_max;
	
	private long max_habitacion;
	
	private long id_habitacion_min;
	
	private long min_habitacion;
	
	/**
	 * @param semana
	 * @param id_servicio_max
	 * @param maximo
	 * @param id_servicio_min
	 * @param minimo
	 * @param id_habitacion_max
	 * @param max_habitacion
	 * @param id_habitacion_min
	 * @param min_habitacion
	 */
	public RFC10(long semana, long id_servicio_max, long maximo, long id_servicio_min, long minimo,
			long id_habitacion_max, long max_habitacion, long id_habitacion_min, long min_habitacion) {
		this.semana = semana;
		this.id_servicio_max = id_servicio_max;
		this.maximo = maximo;
		this.id_servicio_min = id_servicio_min;
		this.minimo = minimo;
		this.id_habitacion_max = id_habitacion_max;
		this.max_habitacion = max_habitacion;
		this.id_habitacion_min = id_habitacion_min;
		this.min_habitacion = min_habitacion;
	}

	/**
	 * @return the semana
	 */
	public long getSemana() {
		return semana;
	}

	/**
	 * @param semana the semana to set
	 */
	public void setSemana(long semana) {
		this.semana = semana;
	}

	/**
	 * @return the id_servicio_max
	 */
	public long getId_servicio_max() {
		return id_servicio_max;
	}

	/**
	 * @param id_servicio_max the id_servicio_max to set
	 */
	public void setId_servicio_max(long id_servicio_max) {
		this.id_servicio_max = id_servicio_max;
	}

	/**
	 * @return the maximo
	 */
	public long getMaximo() {
		return maximo;
	}

	/**
	 * @param maximo the maximo to set
	 */
	public void setMaximo(long maximo) {
		this.maximo = maximo;
	}

	/**
	 * @return the id_servicio_min
	 */
	public long getId_servicio_min() {
		return id_servicio_min;
	}

	/**
	 * @param id_servicio_min the id_servicio_min to set
	 */
	public void setId_servicio_min(long id_servicio_min) {
		this.id_servicio_min = id_servicio_min;
	}

	/**
	 * @return the minimo
	 */
	public long getMinimo() {
		return minimo;
	}

	/**
	 * @param minimo the minimo to set
	 */
	public void setMinimo(long minimo) {
		this.minimo = minimo;
	}

	/**
	 * @return the id_habitacion_max
	 */
	public long getId_habitacion_max() {
		return id_habitacion_max;
	}

	/**
	 * @param id_habitacion_max the id_habitacion_max to set
	 */
	public void setId_habitacion_max(long id_habitacion_max) {
		this.id_habitacion_max = id_habitacion_max;
	}

	/**
	 * @return the max_habitacion
	 */
	public long getMax_habitacion() {
		return max_habitacion;
	}

	/**
	 * @param max_habitacion the max_habitacion to set
	 */
	public void setMax_habitacion(long max_habitacion) {
		this.max_habitacion = max_habitacion;
	}

	/**
	 * @return the id_habitacion_min
	 */
	public long getId_habitacion_min() {
		return id_habitacion_min;
	}

	/**
	 * @param id_habitacion_min the id_habitacion_min to set
	 */
	public void setId_habitacion_min(long id_habitacion_min) {
		this.id_habitacion_min = id_habitacion_min;
	}

	/**
	 * @return the min_habitacion
	 */
	public long getMin_habitacion() {
		return min_habitacion;
	}

	/**
	 * @param min_habitacion the min_habitacion to set
	 */
	public void setMin_habitacion(long min_habitacion) {
		this.min_habitacion = min_habitacion;
	}
		
	
}
