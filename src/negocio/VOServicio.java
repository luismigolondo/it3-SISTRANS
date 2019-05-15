package negocio;

import java.sql.Date;
import java.util.List;

/**
 * Interfaz para los metodos get de SERVICIO
 * @author jd.diazc
 *
 */
public interface VOServicio {

	/**
	 * 
	 * @return el id del servicio
	 */
	public long getId();

	/**
	 * 
	 * @return el nombre del servicio
	 */
	public String getNombre();

	/**
	 * 
	 * @return la hora de apertura
	 */
	public int getHoraApertura();

	/**
	 * 
	 * @return la hora de cierre
	 */
	public int getHoraCierre();

	/**
	 * 
	 * @return el tipo de servicio
	 */
	public String getTipo();

	/**
	 * 
	 * @return la descripcion del servicio
	 */
	public String getDescripcion();

	public long getHotel();
}
