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

	private long id;
	
	private long id_hotel;

	private String nombre;
	
	private String hora_apertura;
	
	private String hora_cierre;
	
	private String tipo;
	
	private String descripcion;
	
	
	private List<Long> productos;
	
	public Servicio() {
		this.id = 0;
		this.nombre = null;
		this.hora_apertura = null;
		this.hora_cierre = null;
		this.tipo = null;
		this.descripcion = null;
		id_hotel=0;
		productos=null;
	}
	
	public Servicio(Long id, String nombre, String horaApertura, String horaCierre, String tipo, String descripcion, long hotel, List productos) {
		this.id = id;
		this.nombre = nombre;
		this.hora_apertura = horaApertura;
		this.hora_cierre = horaCierre;
		this.tipo = tipo;
		this.descripcion = descripcion;
		this.id_hotel=hotel;
		this.productos=productos;
	}


	public long getHotel() {
		return id_hotel;
	}

	public void setHotel(long hotel) {
		this.id_hotel = hotel;
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
	public long getId() {
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
		return hora_apertura;
	}


	/**
	 * @param horaApertura the horaApertura to set
	 */
	public void setHoraApertura(String horaApertura) {
		this.hora_apertura = horaApertura;
	}


	/**
	 * @return the horaCierre
	 */
	public String getHoraCierre() {
		return hora_cierre;
	}


	/**
	 * @param horaCierre the horaCierre to set
	 */
	public void setHoraCierre(String horaCierre) {
		this.hora_cierre = horaCierre;
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
