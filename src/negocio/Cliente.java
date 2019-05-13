/**
 * 
 */
package negocio;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase de cliente de la cadena de hoteles.
 * @author Luis Miguel Gomez
 * MODIFICADO POR LUIS MIGUEL GOMEZ Y JUAN DAVID DIAZ PARA LA ITERACION 1 DE SISTEMAS TRANSACCIONALES.
 */
public class Cliente implements VOCliente {

	public enum TIPOS_DE_DOCUMENTO {
		CC,
		TI,
		PASAPORTE
	}
	private long [] id;
	
	private String nombre;
				
	private String correoElectronico;
	
	private List<Long> reservasHabitaciones;
	
	private long idHotel;
	
	private List<Long> reservasServicios;
	
	public Cliente () 
	{
		this.id = new long [] {0,0};
		this.nombre = "";
		this.correoElectronico = "";
		this.reservasHabitaciones = new ArrayList<>();
		this.idHotel = 0;
		this.reservasServicios = new ArrayList<>();
	}

	/**
	 * @param id
	 * @param nombre
	 * @param numeroIdentificacion
	 * @param correoElectronico
	 * @param reservasHabitaciones
	 * @param idHotel
	 * @param reservasServicios
	 */
	public Cliente(long[] id, String nombre, String correoElectronico,
			List<Long> reservasHabitaciones, long idHotel, List<Long> reservasServicios) {
		this.id = id;
		this.nombre = nombre;;
		this.correoElectronico = correoElectronico;
		this.reservasHabitaciones = reservasHabitaciones;
		this.idHotel = idHotel;
		this.reservasServicios = reservasServicios;
	}

	/**
	 * @return the id
	 */
	public long[] getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long[] id) {
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
	 * @return the correoElectronico
	 */
	public String getCorreoElectronico() {
		return correoElectronico;
	}

	/**
	 * @param correoElectronico the correoElectronico to set
	 */
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	/**
	 * @return the reservasHabitaciones
	 */
	public List<Long> getReservasHabitaciones() {
		return reservasHabitaciones;
	}

	/**
	 * @param reservasHabitaciones the reservasHabitaciones to set
	 */
	public void setReservasHabitaciones(List<Long> reservasHabitaciones) {
		this.reservasHabitaciones = reservasHabitaciones;
	}

	/**
	 * @return the idHotel
	 */
	public long getIdHotel() {
		return idHotel;
	}

	/**
	 * @param idHotel the idHotel to set
	 */
	public void setIdHotel(long idHotel) {
		this.idHotel = idHotel;
	}

	/**
	 * @return the reservasServicios
	 */
	public List<Long> getReservasServicios() {
		return reservasServicios;
	}

	/**
	 * @param reservasServicios the reservasServicios to set
	 */
	public void setReservasServicios(List<Long> reservasServicios) {
		this.reservasServicios = reservasServicios;
	}

}
