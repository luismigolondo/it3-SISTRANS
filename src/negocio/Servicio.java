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
	
	private int hora_apertura;
	
	private int hora_cierre;
	
	private String tipo;
	
	private String descripcion;
		
	public Servicio() {
		this.id = 0;
		this.nombre = null;
		this.hora_apertura = 0;
		this.hora_cierre = 0;
		this.tipo = null;
		this.descripcion = null;
		id_hotel=0;
	}
	
	public Servicio(Long id, String nombre, int horaApertura, int horaCierre, String tipo, String descripcion, long hotel) {
		this.id = id;
		this.nombre = nombre;
		this.hora_apertura = horaApertura;
		this.hora_cierre = horaCierre;
		this.tipo = tipo;
		this.descripcion = descripcion;
		this.id_hotel=hotel;
	}


	public long getHotel() {
		return id_hotel;
	}

	public void setHotel(long hotel) {
		this.id_hotel = hotel;
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
	public int getHoraApertura() {
		return hora_apertura;
	}


	/**
	 * @param horaApertura the horaApertura to set
	 */
	public void setHoraApertura(int horaApertura) {
		this.hora_apertura = horaApertura;
	}


	/**
	 * @return the horaCierre
	 */
	public int getHoraCierre() {
		return hora_cierre;
	}


	/**
	 * @param horaCierre the horaCierre to set
	 */
	public void setHoraCierre(int horaCierre) {
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
