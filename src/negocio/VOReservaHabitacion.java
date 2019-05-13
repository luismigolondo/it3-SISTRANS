package negocio;

import java.util.Date;

import negocio.ReservaHabitacion.TIPOS_DE_RESERVA;

public interface VOReservaHabitacion {

	/**
	 * @return the id
	 */
	public long getId() ;


	/**
	 * @return the fechaInicio
	 */
	public String getFechaInicio();


	/**
	 * @return the fechaFin
	 */
	public String getFechaFin();


	/**
	 * @return the checkedIn
	 */
	public boolean isCheckedIn() ;


	/**
	 * @return the checkedOut
	 */
	public boolean isCheckedOut();

	/**
	 * @return the cliente
	 */
	public long [] getCliente();


	/**
	 * @return the idPlanDeConsumo
	 */
	public long getIdPlanDeConsumo();


	/**
	 * @return the idHabitacion
	 */
	public long getIdHabitacion();

}
