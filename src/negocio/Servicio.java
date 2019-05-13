/**
 * 
 */
package negocio;

import java.sql.Date;
import java.util.List;

/**
 * @author luisgomez
 *
 */
public class Servicio implements VOServicio{

	private Long id;
	
	private String nombre;
	
	private String horaApertura;
	
	private String horaCierre;
	
	private String tipo;
	
	private String descripcion;
	
	private Hotel hotel;
	
	private List<Long> productos;
	
	public Servicio() {
		this.id = null;
		this.nombre = null;
		this.horaApertura = null;
		this.horaCierre = null;
		this.tipo = null;
		this.descripcion = null;
		hotel=null;
		productos=null;
	}
	
	public Servicio(Long id, String nombre, String horaApertura, String horaCierre, String tipo, String descripcion, Hotel hotel, List productos) {
		this.id = id;
		this.nombre = nombre;
		this.horaApertura = horaApertura;
		this.horaCierre = horaCierre;
		this.tipo = tipo;
		this.descripcion = descripcion;
		this.hotel=hotel;
		this.productos=productos;
	}


	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public List<Long> getProductos() {
		return productos;
	}

	public void setProductos(List<Long> productos) {
		this.productos = productos;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}


	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}


	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	/**
	 * @return the horaApertura
	 */
	public String getHoraApertura() {
		return horaApertura;
	}


	/**
	 * @param horaApertura the horaApertura to set
	 */
	public void setHoraApertura(String horaApertura) {
		this.horaApertura = horaApertura;
	}


	/**
	 * @return the horaCierre
	 */
	public String getHoraCierre() {
		return horaCierre;
	}


	/**
	 * @param horaCierre the horaCierre to set
	 */
	public void setHoraCierre(String horaCierre) {
		this.horaCierre = horaCierre;
	}


	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}


	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}


	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
