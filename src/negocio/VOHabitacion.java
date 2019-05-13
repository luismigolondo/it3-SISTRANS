package negocio;

import java.util.List;

/**
 * Interfaz para los metodos get de Habitacion
 * @author jd.diazc
 *
 */
public interface VOHabitacion {

	/**
	 * @return the id
	 */
	public long getId();

	/**
	 * @return the capacidad
	 */
	public int getCapacidad() ;

	/**
	 * @return the dotacion
	 */
	public String getDotacion();

	/**
	 * @return the tipo
	 */
	public long getTipo() ;

	/**
	 * @return the idHotel
	 */
	public long getIdHotel() ;

	/**
	 * @return the reservasHabitaciones
	 */
	public List<Long> getReservasHabitaciones();

	/**
	 * @return the gastos
	 */
	public List<Long> getGastos();
}
