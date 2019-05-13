package persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Clase de sql del gasto de la cadena de hoteles.
 * @author Germ�n Bravo
 * MODIFICADO POR LUIS MIGUEL GOMEZ Y JUAN DAVID DIAZ PARA LA ITERACION 1 DE SISTEMAS TRANSACCIONALES.
 */
public class SQLGastos {

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
	public SQLGastos(PersistenciaCadenaHoteles ph)
	{
		this.ph = ph;
	}
	
	public long registrarConsumoServicio(PersistenceManager pm, long idGasto,long idHabitacion, long idProducto, String pDate)
	{
		Query q = pm.newQuery(SQL,"INSERT INTO GASTOS (ID, ID_PRODUCTO, ID_HABITACION, FECHA_DE_GASTO) VALUES"
				+ " (?, ?, ?, ?)");
		q.setParameters(idGasto, idProducto, idHabitacion, pDate);
		return (long) q.executeUnique();
	}
}
