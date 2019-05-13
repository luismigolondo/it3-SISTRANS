package persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import negocio.Convencion;

public class SQLConvenciones {
	
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
	public SQLConvenciones(PersistenciaCadenaHoteles ph)
	{
		this.ph = ph;
	}
	
	public long adicionarConvencion(PersistenceManager pm,long id,String fIni, String fFin, long idH)
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + "CONVENCIONES" + "(ID, ID_HOTEL,FECHA_INICIO,FECHA_FIN) values (?,?,?,?)");
        q.setParameters(id, idH, fIni,fFin);
        return (long) q.executeUnique();   
	}
	
	public long eliminarConvencion(PersistenceManager pm,long id)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + "CONVENCIONES" + " WHERE id = ?");
        q.setParameters(id);
        return (long) q.executeUnique();    
	}
	
	public Convencion darConvencion(PersistenceManager pm, long id)
	{	
		Query q = pm.newQuery(SQL, "SELECT * FROM CONVENCIONES WHERE ID = ?");
		//q.setResultClass(Convencion.class);
		q.setParameters(id);
	//	q.setUnique(false);
		return  (Convencion) q.executeResultUnique(Convencion.class);
	}
	
	public List<Convencion> darConvenciones (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaConvenciones  ());
		q.setResultClass(Convencion.class);
		return (List<Convencion>) q.executeList();
	}
	
	
}
