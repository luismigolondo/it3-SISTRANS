/**
 * 
 */
package negocio;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luisgomez
 *
 */
public class Habitacion implements VOHabitacion{

	public enum TIPOS_DE_HABITACION {
		SUITE,
		SUITE_PRESIDENCIAL,
		SENCILLA
	}
	
	private long id;
	
	private int capacidad;
	
	private String dotacion;
	
	private long tipo;
	
	private long idHotel;
	
	private List<Long> reservasHabitaciones;
	
	private List<Long> gastos;
	
	public Habitacion () {
		this.id = 0;
		this.capacidad = 0;
		this.dotacion = "";
		this.tipo = 0;
		this.idHotel = 0;
		this.reservasHabitaciones = new ArrayList<>();
		this.gastos = new ArrayList<>();
	}

	/**
	 * @param id
	 * @param capacidad
	 * @param dotacion
	 * @param tipo
	 * @param idHotel
	 * @param reservasHabitaciones
	 * @param gastos
	 */
	public Habitacion(long id, int capacidad, String dotacion, long tipo, long idHotel, List<Long> reservasHabitaciones,
			List<Long> gastos) {
		this.id = id;
		this.capacidad = capacidad;
		this.dotacion = dotacion;
		this.tipo = tipo;
		this.idHotel = idHotel;
		this.reservasHabitaciones = reservasHabitaciones;
		this.gastos = gastos;
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
	 * @return the capacidad
	 */
	public int getCapacidad() {
		return capacidad;
	}

	/**
	 * @param capacidad the capacidad to set
	 */
	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	/**
	 * @return the dotacion
	 */
	public String getDotacion() {
		return dotacion;
	}

	/**
	 * @param dotacion the dotacion to set
	 */
	public void setDotacion(String dotacion) {
		this.dotacion = dotacion;
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
	public void setTipo(long tipo) {
		this.tipo = tipo;
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
	 * @return the gastos
	 */
	public List<Long> getGastos() {
		return gastos;
	}

	/**
	 * @param gastos the gastos to set
	 */
	public void setGastos(List<Long> gastos) {
		this.gastos = gastos;
	}

	
}
