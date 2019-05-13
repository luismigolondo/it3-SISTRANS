/**
 * 
 */
package negocio;

/**
 * @author luisgomez
 *
 */
public class PlanesDeConsumo implements VOPlanesDeConsumo{
	
	private long id;
	
	private long tipo;
	
	private Double descuento;
	
	private Integer minNoches;
	
	private Double costoFijo;
	
	private Hotel hotel;
	
	public void setTipo(long tipo) {
		this.tipo = tipo;
	}

	public PlanesDeConsumo () {
		this.tipo = 0;
		this.descuento = 0.0;
		this.minNoches = 0;
		this.costoFijo = 0.0;
		this.id=0;
		this.hotel=null;
	}

	/**
	 * @param tipo
	 * @param descuento
	 * @param minNoches
	 * @param costoFijo
	 */
	public PlanesDeConsumo(Long id,int tipo, Double descuento, Integer minNoches, Double costoFijo, Hotel hotel) {
		this.tipo = tipo;
		this.descuento = descuento;
		this.minNoches = minNoches;
		this.costoFijo = costoFijo;
		this.id=id;
		this.hotel=hotel;
	}
	
	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
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
	 * @return the tipo
	 */
	public long getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the descuento
	 */
	public Double getDescuento() {
		return descuento;
	}

	/**
	 * @param descuento the descuento to set
	 */
	public void setDescuento(Double descuento) {
		this.descuento = descuento;
	}

	/**
	 * @return the minNoches
	 */
	public Integer getMinNoches() {
		return minNoches;
	}

	/**
	 * @param minNoches the minNoches to set
	 */
	public void setMinNoches(Integer minNoches) {
		this.minNoches = minNoches;
	}

	/**
	 * @return the costoFijo
	 */
	public Double getCostoFijo() {
		return costoFijo;
	}

	/**
	 * @param costoFijo the costoFijo to set
	 */
	public void setCostoFijo(Double costoFijo) {
		this.costoFijo = costoFijo;
	}
	
	
}
