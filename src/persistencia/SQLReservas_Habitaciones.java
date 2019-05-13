package persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Clase de sql de reservas de habitaciones de la cadena de hoteles.
 * @author Germ�n Bravo
 * MODIFICADO POR LUIS MIGUEL GOMEZ Y JUAN DAVID DIAZ PARA LA ITERACION 1 DE SISTEMAS TRANSACCIONALES.
 */
public class SQLReservas_Habitaciones {

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
	public SQLReservas_Habitaciones(PersistenciaCadenaHoteles ph)
	{
		this.ph = ph;
	}

	public long adicionarReserva(PersistenceManager pm, long pId, long pIdCliente, long pIdTipoId, long pIdHabitacion,
			long pIdPlanDeConsumo, String pFechaInicio, String pFechaFin) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + "RESERVAS_HABITACIONES" + 
				"(ID, ID_CLIENTE, TIPO_IDENTIFICACION, ID_HABITACION, ID_PLAN_DE_CONSUMO, "
				+ "FECHA_INICIO, FECHA_FIN, CHECKED_IN, CHECKED_OUT) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
		q.setParameters(pId, pIdCliente, pIdTipoId, pIdHabitacion, pIdPlanDeConsumo, pFechaInicio, pFechaFin, 0, 0);
		return (long) q.executeUnique();
	}

	public long registrarSalidaCliente(PersistenceManager pm, long idReserva) {
		Query q = pm.newQuery(SQL,"UPDATE "+ "RESERVAS_HABITACIONES" + " SET CHECKED_OUT = ? WHERE ID = ?");
		q.setParameters(1, idReserva);
		return (long) q.executeUnique();
	}

	public long registrarLlegadaCliente(PersistenceManager pm, long pIdReserva) {
		Query q = pm.newQuery(SQL,"UPDATE "+ "RESERVAS_HABITACIONES" + " SET CHECKED_IN = ? WHERE ID = ?");
		q.setParameters(1, pIdReserva);
		return (long) q.executeUnique();
	}
}
