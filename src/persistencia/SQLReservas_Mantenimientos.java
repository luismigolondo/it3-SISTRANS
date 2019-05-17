package persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLReservas_Mantenimientos {

	private final static String SQL = PersistenciaCadenaHoteles.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicaci�n
	 */
	private PersistenciaCadenaHoteles ph;

	/* ****************************************************************
	 * 			M�todos
	 *****************************************************************/
	/**
	 * Constructor
	 * @param ph - El Manejador de persistencia de la aplicaci�n
	 */
	public SQLReservas_Mantenimientos(PersistenciaCadenaHoteles ph)
	{
		this.ph = ph;
	}
	
	public String registrarFinMantenimiento(PersistenceManager pm, long idHotel, String habs, String servs) {
		String[]  habitaciones = {habs};
		if(habs.length()!=1){
			habitaciones = habs.split(",");
		}

//		for(int e=0; e<habitaciones.length;e++) {
//			Query q = pm.newQuery(SQL,"UPDATE "+ "RESERVAS_MANTENIMIENTOS" + " SET EN_MANTENIMIENTO = ? WHERE ID_HABITACION="
//					+habitaciones[e]);
//			q.setParameters(0);
//			q.executeUnique();
//		}
//		
//		String[] serviciones = {servs};
//		if(servs.length()!=1){
//			serviciones = servs.split(",");
//		}
//		for(int e=0; e<serviciones.length;e++) {
//			Query q = pm.newQuery(SQL,"UPDATE "+ "RESERVAS_MANTENIMIENTOS" + " SET EN_MANTENIMIENTO = ? WHERE ID_SERVICIO="
//					+serviciones[e]);
//			q.setParameters(0);
//			q.executeUnique();
//		}
		return "HABITACIONES EN MANTENIMIENTO: "+habs+" SERVICIOS EN MANTENIMIENTO: "+servs;
	}
}
