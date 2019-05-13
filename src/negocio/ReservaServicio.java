package negocio;

import java.sql.Date;

public class ReservaServicio implements VOReservaServicio {

	/**
	 *  id del servicio
	 */
	private long id;
	
	/**
	 * hora de inicio del servicio
	 */
	private String horaInicio;
	
	/**
	 * hora de finalizacion del servicio
	 */
	private String horaFin;

	private long[] cliente;
	
	private long servicio;
		
	public ReservaServicio() {
		this.id = 0;
		this.horaInicio = null;
		this.horaFin = null;
		this.cliente = new long[] {0,0};
		this.servicio = 0;
	}
	
	public ReservaServicio(long id, String horaInicio, String horaFin, long[] cliente, long planDeConsumo) {
		this.id = id;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.cliente = cliente;
		this.servicio = planDeConsumo;
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
	 * @return the horaInicio
	 */
	public String getHoraInicio() {
		return horaInicio;
	}

	/**
	 * @param horaInicio the horaInicio to set
	 */
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	/**
	 * @return the horaFin
	 */
	public String getHoraFin() {
		return horaFin;
	}

	/**
	 * @param horaFin the horaFin to set
	 */
	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	public long[] getCliente() {
		return cliente;
	}

	public void setCliente(long[] cliente) {
		this.cliente = cliente;
	}

	public long getServicio() {
		return servicio;
	}

	public void setServicio(long servicio) {
		this.servicio = servicio;
	}
	
	public String toString()
	{
		String ts = "Reserva Servicio [id=" + id + 
				", Inicia el =" + horaInicio + ", hasta =" + horaFin + 
				"/n , Para el cliente con id = " + cliente[1] + 
				",para el servicio = " + servicio + "];";
		return ts;
	}
	
}
