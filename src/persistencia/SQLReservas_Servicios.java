package persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Clase de sql del reservas servicios de la cadena de hoteles.
 * @author Germ�n Bravo
 * MODIFICADO POR LUIS MIGUEL GOMEZ Y JUAN DAVID DIAZ PARA LA ITERACION 1 DE SISTEMAS TRANSACCIONALES.
 */
public class SQLReservas_Servicios {

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
	public SQLReservas_Servicios(PersistenciaCadenaHoteles ph)
	{
		this.ph = ph;
	}

	public long adicionarReserva(PersistenceManager pm, long id, String horaInicio, String horaFin,
			long pIdCliente, long pIdTipoId, long servicio) {
		Query q = pm.newQuery(SQL, "INSERT INTO RESERVAS_SERVICIOS (ID, ID_CLIENTE, TIPO_IDENTIFICACION, "
				+ "ID_SERVICIO, HORA_APERTURA, HORA_CIERRE) VALUES (?, ?, ?, ?, ?, ?)");
		q.setParameters(id, pIdCliente, pIdTipoId, servicio, horaInicio, horaFin);
		return (long) q.executeUnique();
	}

}
