package negocio;

import java.util.List;

import negocio.Cliente.TIPOS_DE_DOCUMENTO;

/**
 * Interfaz para los metodos get de USUARIO
 * @author jd.diazc
 *
 */
public interface VOCliente {

	/**
	 * @return the id
	 */
	public long[] getId() ;

	/**
	 * @return the nombre
	 */
	public String getNombre() ;

	/**
	 * @return the correoElectronico
	 */
	public String getCorreoElectronico();

	/**
	 * @return the reservasHabitaciones
	 */
	public List<Long> getReservasHabitaciones();


	/**
	 * @return the idHotel
	 */
	public long getIdHotel();


	/**
	 * @return the reservasServicios
	 */
	public List<Long> getReservasServicios();

	
}
