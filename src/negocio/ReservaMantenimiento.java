package negocio;

public class ReservaMantenimiento implements VOReservaMantenimiento{

	public enum TIPOS_DE_MANTENIMIENTO{
		MEDIO,
		FULL,
		FULL_JUGUETES
	}
	
	private long id;
	
	private long idHabitacion;
	
	private long idServicio;
	
	private TIPOS_DE_MANTENIMIENTO tipoMantenimiento;
	
	private boolean enMantenimiento;
	
	private String fechaInicio;
	
	private String fechaFin;
	
	public ReservaMantenimiento()
	{
		this.id=0;
		this.idHabitacion=0;
		this.idServicio=0;
		this.tipoMantenimiento=null;
		this.enMantenimiento=false;
		this.fechaInicio="";
		this.fechaFin="";
	}
	
	
	
	/**
	 * @param id
	 * @param idHabitacion
	 * @param idServicio
	 * @param tipoDeMantenimiento
	 * @param enMantenimiento
	 * @param fechaInicio
	 * @param fechaFin
	 */
	public ReservaMantenimiento(long id, long idHabitacion, long idServicio, TIPOS_DE_MANTENIMIENTO tipoDeMantenimiento,
			boolean enMantenimiento, String fechaInicio, String fechaFin) {
		super();
		this.id = id;
		this.idHabitacion = idHabitacion;
		this.idServicio = idServicio;
		this.tipoMantenimiento = tipoDeMantenimiento;
		this.enMantenimiento = enMantenimiento;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
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
	 * @return the tipoDeMantenimiento
	 */
	public TIPOS_DE_MANTENIMIENTO getTipoMantenimiento() {
		return tipoMantenimiento;
	}

	/**
	 * @param tipoDeMantenimiento the tipoDeMantenimiento to set
	 */
	public void setTipoMantenimiento(TIPOS_DE_MANTENIMIENTO tipoDeMantenimiento) {
		this.tipoMantenimiento = tipoDeMantenimiento;
	}

	/**
	 * @return the enMantenimiento
	 */
	public boolean isEnMantenimiento() {
		return enMantenimiento;
	}

	/**
	 * @param enMantenimiento the enMantenimiento to set
	 */
	public void setEnMantenimiento(boolean enMantenimiento) {
		this.enMantenimiento = enMantenimiento;
	}

	/**
	 * @return the fechaInicio
	 */
	public String getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * @return the fechaFin
	 */
	public String getFechaFin() {
		return fechaFin;
	}

	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	

	
}
