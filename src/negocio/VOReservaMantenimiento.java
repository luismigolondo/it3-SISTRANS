package negocio;

import negocio.ReservaMantenimiento.TIPOS_DE_MANTENIMIENTO;

public interface VOReservaMantenimiento {

	public long getId();
	
	public long getIdHabitacion();
	
	public long getIdServicio();
	
	public TIPOS_DE_MANTENIMIENTO getTipoMantenimiento();
	
	public boolean isEnMantenimiento();

	public String getFechaInicio();
	
	public String getFechaFin();
	
	
}
