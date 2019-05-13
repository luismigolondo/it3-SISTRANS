package negocio;

public class Convencion implements VOConvencion{
	
	/**
	 * id 
	 */
	private long id;
	
	/**
	 * fechaInicio
	 */
	private String fechaInicio;
	
	/**
	 *  fechaFin
	 */
	private String fechaFin;
	
	/**
	 *  id del hotel
	 */
	private long idHotel;
	
	/**
	 * constructor
	 */
	public Convencion()
	{
		this.id = 0;
		this.fechaInicio="";
		this.fechaFin="";
		this.idHotel=0;
	}
	
	/**
	 * constructor
	 * @param id
	 * @param fechaInicio
	 * @param fechaFin
	 * @param idHotel
	 */
	public Convencion(long id, String fechaInicio, String fechaFin, long idHotel) {
		this.id = id;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.idHotel = idHotel;
	}

	/**
	 * 
	 * @return
	 */
	public long getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * 
	 * @return
	 */
	public String getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * 
	 * @param fechaInicio
	 */
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * 
	 * @return
	 */
	public String getFechaFin() {
		return fechaFin;
	}

	/**
	 * 
	 * @param fechaFin
	 */
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * 
	 * @return
	 */
	public long getIdHotel() {
		return idHotel;
	}

	/**
	 * 
	 * @param idHotel
	 */
	public void setIdHotel(long idHotel) {
		this.idHotel = idHotel;
	}
	
	
	
	
}
