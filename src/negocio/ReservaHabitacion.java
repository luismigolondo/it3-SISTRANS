/**
 * 
 */
package negocio;

import java.util.Date;

/**
 * @author luisgomez
 *
 */
public class ReservaHabitacion implements VOReservaHabitacion{

	public enum TIPOS_DE_RESERVA {
		HABITACION_SUITE,
		HABITACION_SUITE_PRESIDENCIAL,
		HABITACION_SENCILLA
	}
	
	private long id;
	
	private String fechaInicio;
	
	private String fechaFin;
		
	private boolean checkedIn;
	
	private boolean checkedOut;
	
	private long [] cliente;
	
	private long idPlanDeConsumo;
	
	private long idHabitacion;
	
	public ReservaHabitacion () {
		this.id = 0;
		this.fechaInicio = null;
		this.fechaFin = null;
		this.checkedIn = false;
		this.checkedOut = false;
		this.cliente = new long[] {0,0};
		this.idPlanDeConsumo = 0;
		this.idHabitacion = 0;
	}
	
	/**
	 * @param id
	 * @param tipo
	 * @param fechaInicio
	 * @param fechaFin
	 * @param checkedIn
	 * @param checkedOut
	 */
	public ReservaHabitacion(long id, long [] cliente, long habitacion,
			long plandeconsumo, String fechaInicio, String fechaFin) {
		this.id = id;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.checkedIn = false;
		this.checkedOut = false;
		this.cliente = cliente;
		this.idPlanDeConsumo = plandeconsumo;
		this.idHabitacion = habitacion;
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
	 * @return the fechaInicio
	 */
	public String getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * @return the fechaFin
	 */
	public String getFechaFin() {
		return fechaFin;
	}

	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * @return the checkedIn
	 */
	public boolean isCheckedIn() {
		return checkedIn;
	}

	/**
	 * @param checkedIn the checkedIn to set
	 */
	public void setCheckedIn(boolean checkedIn) {
		this.checkedIn = checkedIn;
	}

	/**
	 * @return the checkedOut
	 */
	public boolean isCheckedOut() {
		return checkedOut;
	}

	/**
	 * @param checkedOut the checkedOut to set
	 */
	public void setCheckedOut(boolean checkedOut) {
		this.checkedOut = checkedOut;
	}

	/**
	 * @return the idPlanDeConsumo
	 */
	public long getIdPlanDeConsumo() {
		return idPlanDeConsumo;
	}

	/**
	 * @param idPlanDeConsumo the idPlanDeConsumo to set
	 */
	public void setIdPlanDeConsumo(long idPlanDeConsumo) {
		this.idPlanDeConsumo = idPlanDeConsumo;
	}

	/**
	 * @return the idHabitacion
	 */
	public long getIdHabitacion() {
		return idHabitacion;
	}

	/**
	 * @param idHabitacion the idHabitacion to set
	 */
	public void setIdHabitacion(long idHabitacion) {
		this.idHabitacion = idHabitacion;
	}

	/**
	 * @return the cliente
	 */
	public long [] getCliente() {
		return cliente;
	}

	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(long [] cliente) {
		this.cliente = cliente;
	}
	
	public String toString()
	{
		String ts = "Reserva Habitacion [id=" + id + 
				", Inicia el =" + fechaInicio + ", estadia hasta =" + fechaFin + 
				"/n , el estado de check in es = " + checkedIn + ", estado check out = " + checkedOut + 
				", Para el cliente con id = " + cliente[1] + 
				", con el plan de consumos = " + idPlanDeConsumo + 
				", asignado a la habitacion = " + idHabitacion + "];";
		return ts;
	}
	
}
