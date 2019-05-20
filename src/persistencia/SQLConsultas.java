/**
 * 
 */
package persistencia;

import java.sql.ResultSet;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import negocio.RFC1;
import negocio.RFC11;
import negocio.RFC2;
import negocio.RFC3;
import negocio.RFC4;
import negocio.RFC6;
import negocio.RFC7;
import negocio.RFC9;
import negocio.Servicio;

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

	public List<RFC1> rfc1(PersistenceManager pm, String fechaInicio, String fechaFin)
	{
		Query q = pm.newQuery(SQL, "SELECT g.ID_HABITACION, SUM(p.VALOR) "
				+ "FROM ((RESERVAS_HABITACIONES r INNER JOIN HABITACIONES h ON r.ID=h.ID AND r.FECHA_INICIO<= ? AND r.FECHA_FIN>= ? ) INNER JOIN GASTOS g on g.ID_HABITACION=h.ID) INNER JOIN PRODUCTOS p ON g.ID_PRODUCTO=p.ID "
				+ "GROUP BY g.ID_HABITACION");
		q.setResultClass(RFC1.class);
		q.setParameters(fechaInicio, fechaFin);
		return (List<RFC1>) q.executeList();
	}

	public List<RFC2> rfc2(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT g.ID_PRODUCTO, COUNT(*) "
				+ "FROM (HABITACIONES h INNER JOIN GASTOS g ON h.id=g.ID_HABITACION) INNER JOIN PRODUCTOS p ON g.ID_PRODUCTO=p.ID "
				+ "GROUP BY g.ID_PRODUCTO "
				+ "FETCH FIRST 20 ROWS ONLY");
		q.setResultClass(RFC2.class);
		return (List<RFC2>) q.executeList();
	}

	public List<RFC3> rfc3(PersistenceManager pm, String fechaInicio, String fechaFin)
	{
		Query q = pm.newQuery(SQL, "SELECT"
				+ " r.ID_HABITACION as HABITACIONES_LIBRES "
				+ "FROM HABITACIONES h INNER JOIN RESERVAS_HABITACIONES r ON h.ID=r.ID_HABITACION AND r.FECHA_INICIO>= ? AND r.FECHA_FIN<= ?");
		q.setResultClass(RFC3.class);
		q.setParameters(fechaInicio, fechaFin);
		return (List<RFC3>) q.executeList();
	}

	public List<Servicio> rfc4(PersistenceManager pm, long idServicio, long idHotel, String nombre, int horaA, int horaC, String tipo)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM SERVICIOS s "
				+ "WHERE s.ID= " + idServicio + " AND s.ID_HOTEL= " + idHotel + " AND s.NOMBRE= '" + nombre + "' AND s.HORA_APERTURA<= '" + horaA + "' AND s.HORA_CIERRE >= '" + horaC + "' AND s.TIPO= '" + tipo + "' ");
		q.setResultClass(Servicio.class);
		return (List<Servicio>) q.executeList();
	}

	public List<RFC6> rfc6(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT COUNT(ROUND(FECHA_DE_GASTO, 'DDD')) CONSUMIDO_X_VECES, ROUND(FECHA_DE_GASTO, 'DDD') GASTO_GENERADO_EL "
				+ "FROM GASTOS g INNER JOIN HABITACIONES h ON h.ID = g.ID_HABITACION "
				+ "WHERE g.FECHA_DE_GASTO < SYSTIMESTAMP "
				+ "AND g.FECHA_DE_GASTO > add_months(SYSTIMESTAMP, -1) "
				+ "AND TIPO_HABITACION = 3 "
				+ "GROUP BY ROUND(FECHA_DE_GASTO, 'DDD') "
				+ "ORDER BY COUNT(ROUND(FECHA_DE_GASTO, 'DDD')) DESC, ROUND(FECHA_DE_GASTO, 'DDD')");
		q.setResultClass(RFC6.class);
		return (List<RFC6>) q.executeList();
	}

	public List<RFC7> rfc7(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * "
				+ "FROM (SELECT c.ID as CEDULA, SUM(p.Valor) as TOTAL_CONSUMIDO "
				+ "FROM ((CLIENTES c INNER JOIN RESERVAS_HABITACIONES rH ON c.ID_RESERVA_HABITACION = rH.ID) "
				+ "INNER JOIN GASTOS g ON g.ID_HABITACION = rH.ID_HABITACION) "
				+ "INNER JOIN PRODUCTOS p ON p.ID = g.ID_PRODUCTO "
				+ "GROUP BY c.ID ORDER BY TOTAL_CONSUMIDO DESC) WHERE TOTAL_CONSUMIDO >= 15000000");
		q.setResultClass(RFC7.class);
		return (List<RFC7>) q.executeList();
	}

	public List<RFC9> rfc9(PersistenceManager pm, int servicioSeleccionado, String ascdesc, String inic, String fin) {
		Query q = pm.newQuery(SQL, "SELECT c.ID AS CEDULA, COUNT(r.ID) as NUM_RESERVAS "
				+ "FROM CLIENTES c INNER JOIN RESERVAS_SERVICIOS r ON c.ID = r.ID_CLIENTE "
				+ "WHERE r.ID_SERVICIO ="+ servicioSeleccionado + 
				" AND r.HORA_APERTURA >= '" + inic + "' "
				+ "AND r.HORA_CIERRE <= '" + fin + "' "
				+ " AND ROWNUM<126 GROUP BY c.ID ORDER BY NUM_RESERVAS " + ascdesc);
		q.setResultClass(RFC9.class);
		List<RFC9> l = (List<RFC9>) q.executeList();
		System.out.println("EL PELUDO");
		return l;
	}

	public String rfc10(PersistenceManager pm, int servicioSeleccionado, String ascdesc,String inic, String fin) {
		// TODO Auto-generated method stub
		String lel = "select id  from clientes where ID not in (SELECT c.ID FROM CLIENTES c INNER JOIN RESERVAS_SERVICIOS r ON c.ID = r.ID_CLIENTE"
				+ " WHERE r.ID_SERVICIO =7 AND r.HORA_APERTURA >= '25/05/2018' AND r.HORA_CIERRE <= '28/05/2019') AND ROWNUM<126 GROUP BY ID";
		Query q = pm.newQuery(SQL,lel);

		List<Object> objects = q.executeList();
		String r = "";
		int i=1;
		for(Object o: objects){
			r+=i+") EL cliente con id "+o.toString()+" nunca consumio el servicio con id 7. \n";
			i++;
		}
		System.out.println("CARGARON");
		return r;
	}

	public List<RFC11> rfc11(PersistenceManager pm) {
		String sentencia = "SELECT p.SEMANA, p.ID_SERVICIO_MAX, p.MAXIMO, p.ID_SERVICIO_MIN, p.MINIMO, d.ID_HABITACION_MAX, d.MAXIMO AS MAX_HABITACION, d.ID_HABITACION_MIN, d.MINIMO AS MIN_HABITACION\n" + 
				"FROM (SELECT w.SEMANA, w.SERVICIOS AS ID_SERVICIO_MAX, w.MAXIMO, j.SERVICIOS AS ID_SERVICIO_MIN, j.MINIMO\n" + 
				"FROM (SELECT z.SEMANA, l.SERVICIOS, z.MAXIMO\n" + 
				"FROM (SELECT SEMANA, MAX(CUENTA) MAXIMO\n" + 
				"FROM (SELECT to_char(r.HORA_APERTURA,'WW') SEMANA, r.ID_SERVICIO SERVICIOS, COUNT(ID_SERVICIO) CUENTA\n" + 
				"FROM RESERVAS_SERVICIOS r\n" + 
				"GROUP BY to_char(r.HORA_APERTURA,'WW'), r.ID_SERVICIO\n" + 
				"ORDER BY SEMANA ASC) tabla\n" + 
				"GROUP BY SEMANA) z, (SELECT to_char(r.HORA_APERTURA,'WW') SEMANA, r.ID_SERVICIO SERVICIOS, COUNT(ID_SERVICIO) CUENTA\n" + 
				"FROM RESERVAS_SERVICIOS r\n" + 
				"GROUP BY to_char(r.HORA_APERTURA,'WW'), r.ID_SERVICIO\n" + 
				"ORDER BY SEMANA ASC) l\n" + 
				"WHERE z.MAXIMO = l.CUENTA) w, (SELECT z.SEMANA, l.SERVICIOS, z.MINIMO\n" + 
				"FROM (SELECT SEMANA, MIN(CUENTA) MINIMO\n" + 
				"FROM (SELECT to_char(r.HORA_APERTURA,'WW') SEMANA, r.ID_SERVICIO SERVICIOS, COUNT(ID_SERVICIO) CUENTA\n" + 
				"FROM RESERVAS_SERVICIOS r\n" + 
				"GROUP BY to_char(r.HORA_APERTURA,'WW'), r.ID_SERVICIO\n" + 
				"ORDER BY SEMANA ASC) tabla\n" + 
				"GROUP BY SEMANA) z, (SELECT to_char(r.HORA_APERTURA,'WW') SEMANA, r.ID_SERVICIO SERVICIOS, COUNT(ID_SERVICIO) CUENTA\n" + 
				"FROM RESERVAS_SERVICIOS r\n" + 
				"GROUP BY to_char(r.HORA_APERTURA,'WW'), r.ID_SERVICIO\n" + 
				"ORDER BY SEMANA ASC) l\n" + 
				"WHERE z.MINIMO = l.CUENTA) j\n" + 
				"WHERE w.SEMANA = j.SEMANA) p, (SELECT x.SEMANA, x.HABITACIONES AS ID_HABITACION_MAX, x.MAXIMO, n.HABITACIONES AS ID_HABITACION_MIN, n.MINIMO\n" + 
				"FROM (SELECT v.SEMANA, m.HABITACIONES, v.MAXIMO\n" + 
				"FROM (SELECT SEMANA, MAX(CUENTA) MAXIMO\n" + 
				"FROM (SELECT to_char(c.FECHA_INICIO,'WW') SEMANA, c.ID_HABITACION HABITACIONES, COUNT(ID_HABITACION) CUENTA\n" + 
				"FROM RESERVAS_HABITACIONES c\n" + 
				"GROUP BY to_char(c.FECHA_INICIO,'WW'), c.ID_HABITACION\n" + 
				"ORDER BY SEMANA ASC) a\n" + 
				"GROUP BY SEMANA) v, (SELECT to_char(c.FECHA_INICIO,'WW') SEMANA, c.ID_HABITACION HABITACIONES, COUNT(ID_HABITACION) CUENTA\n" + 
				"FROM RESERVAS_HABITACIONES c\n" + 
				"GROUP BY to_char(c.FECHA_INICIO,'WW'), c.ID_HABITACION\n" + 
				"ORDER BY SEMANA ASC) m\n" + 
				"WHERE v.MAXIMO = m.CUENTA) x, (SELECT v.SEMANA, m.HABITACIONES, v.MINIMO\n" + 
				"FROM (SELECT SEMANA, MIN(CUENTA) MINIMO\n" + 
				"FROM (SELECT to_char(c.FECHA_INICIO,'WW') SEMANA, c.ID_HABITACION HABITACIONES, COUNT(ID_HABITACION) CUENTA\n" + 
				"FROM RESERVAS_HABITACIONES c\n" + 
				"GROUP BY to_char(c.FECHA_INICIO,'WW'), c.ID_HABITACION\n" + 
				"ORDER BY SEMANA ASC) a\n" + 
				"GROUP BY SEMANA) v, (SELECT to_char(c.FECHA_INICIO,'WW') SEMANA, c.ID_HABITACION HABITACIONES, COUNT(ID_HABITACION) CUENTA\n" + 
				"FROM RESERVAS_HABITACIONES c\n" + 
				"GROUP BY to_char(c.FECHA_INICIO,'WW'), c.ID_HABITACION\n" + 
				"ORDER BY SEMANA ASC) m\n" + 
				"WHERE v.MINIMO = m.CUENTA) n\n" + 
				"WHERE x.SEMANA = n.SEMANA) d\n" + 
				"WHERE p.SEMANA = d.SEMANA";
		Query q = pm.newQuery(SQL,sentencia);
		q.setResultClass(RFC11.class);
		List<RFC11> l = (List<RFC11>) q.executeList();
		return l;
	}
	
	public String rfc12(PersistenceManager pm) {
		// TODO Auto-generated method stub
		String sentencia = "SELECT c.NOMBRE,c.ID, COUNT(r.ID) AS SERVICIOS_CONSUMIDOS FROM CLIENTES c, RESERVAS_HABITACIONES r, GASTOS g, PRODUCTOS p "
				+ "WHERE c.ID=r.ID_CLIENTE AND g.ID_RESERVA_HABITACION=r.ID AND g.ID_PRODUCTO=p.ID "
				+ "AND ((r.CHECKED_IN=1 AND r.CHECKED_OUT=1) OR (p.VALOR>300000) OR  (p.ID_SERVICIO=8 OR p.ID_SERVICIO=11)) "
				+ " AND ROWNUM<126 GROUP BY c.NOMBRE,c.ID order by COUNT(r.ID) DESC";
		Query q = pm.newQuery(SQL,sentencia);
		List<Object> objects = q.executeList();
		String r = "";
		int i=1;
		for(Object o: objects){
			System.out.println("CHOLEEEE"+i);
			Object[] datos=(Object[])o;
			//Dependiendo del atributo que se este seleccionando en el SELECT se hara el acceso al arreglo datos
			r+=i+") " +datos[0]+" CON ID "+ datos[1]+" ES UN BUEN CLIENTE Y CONSUMIO "+ datos[2]+" VECES\n";
			i++;
		}
		return r;
		
	}

}
