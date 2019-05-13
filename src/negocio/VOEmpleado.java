package negocio;

import negocio.Empleado.ROLES;
import negocio.Cliente.TIPOS_DE_DOCUMENTO;

public interface VOEmpleado {

	public String getNombre();
	
	public TIPOS_DE_DOCUMENTO getTipoDeIdentificacion();
	
	public Long getNumeroIdentificacion();
	
	public ROLES getRol();
	
	public Double getSalario();
}
