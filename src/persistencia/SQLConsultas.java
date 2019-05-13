/**
 * 
 */
package persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import negocio.RFC1;
import negocio.RFC2;
import negocio.RFC3;

/**
 * @author lm.gomezl
 *
 */
public class SQLConsultas {

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
	
	public SQLConsultas(PersistenciaCadenaHoteles p)
	{
		this.ph = p;
	}
	
	public List<RFC1> rfc1(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT g.ID_HABITACION, SUM(p.VALOR) "
				+ "FROM ((RESERVAS_HABITACIONES r INNER JOIN HABITACIONES h ON r.ID=h.ID AND r.FECHA_INICIO<='18/03/2019' AND r.FECHA_FIN>='19/03/2019') INNER JOIN GASTOS g on g.ID_HABITACION=h.ID) INNER JOIN PRODUCTOS p ON g.ID_PRODUCTO=p.ID "
				+ "GROUP BY g.ID_HABITACION;");
		q.setResultClass(RFC1.class);
		return (List<RFC1>) q.executeList();
	}
	
	public List<RFC2> rfc2(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT g.ID_PRODUCTO, COUNT(*) "
				+ "FROM (HABITACIONES h INNER JOIN GASTOS g ON h.id=g.ID_HABITACION) INNER JOIN PRODUCTOS p ON g.ID_PRODUCTO=p.ID "
				+ "GROUP BY g.ID_PRODUCTO "
				+ "FETCH FIRST 20 ROWS ONLY;");
		q.setResultClass(RFC2.class);
		return (List<RFC2>) q.executeList();
	}
	
	public List<RFC3> rfc3(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT r.ID_HABITACION as HABITACIONES_LIBRES "
				+ "FROM HABITACIONES h INNER JOIN RESERVAS_HABITACIONES r ON h.ID=r.ID_HABITACION AND r.FECHA_INICIO>='17/03/2019' AND r.FECHA_FIN<='17/03/2019';");
		q.setResultClass(RFC3.class);
		return (List<RFC3>) q.executeList();
	}
	
}
