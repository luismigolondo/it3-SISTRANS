package persistencia;



import java.math.BigDecimal;
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
		Query q = pm.newQuery(SQL, "INSERT INTO " + "CONVENCIONES" + "(ID, ID_HOTEL,FECHA_INICIO,FECHA_FIN,PAZ_Y_SALVO) values (?,?,?,?,?)");
        q.setParameters(id, idH, fIni,fFin,0);
        return (long) q.executeUnique();   
	}
	
	public String eliminarConvencion(PersistenceManager pm,long id)
	{
		String respuesta = "";
		try{
			
			String lel = "SELECT c.NOMBRE,p.VALOR " + "FROM ((CLIENTES c INNER JOIN GASTOS g ON c.ID_RESERVA_HABITACION=g.ID_RESERVA_HABITACION AND c.ID_CONVENCION="+id+")" 
					+" INNER JOIN PRODUCTOS p ON g.ID_PRODUCTO=p.ID) LEFT OUTER JOIN RESERVAS_CONVENCIONES rc ON p.ID_SERVICIO=rc.ID_SERVICIO WHERE rc.ID_SERVICIO IS NULL"
					+" ORDER BY c.ID_RESERVA_HABITACION ";
			System.out.println(lel);
			Query q = pm.newQuery(SQL,lel);
			System.out.println("LLEGA EL PELUDO");
			List<Object> objects = q.executeList();
			String autorAct = "";
			Double tot = 0.0;
			for (Object o : objects) {
				Object[] datos = (Object[]) o;
//				System.out.println(datos[0]);
//				System.out.println(datos[1]);
				if(!datos[0].equals(autorAct)){
					respuesta+=autorAct+" DEBE PAGAR "+tot +"\n";
					autorAct=datos[0].toString();
					tot=0.0;
				}
				tot+=((BigDecimal)datos[1]).longValue();
			}
			System.out.println(respuesta);
			//List<Object> objects = q.executeList();
			System.out.println("LLEGA AUN MAS LEJOS");
//			Object[] c = objects.toArray();
			
//			for(int e=0;e<objects.size();e++){
//				System.out.println(objects.get(e)+"");
//			}
			System.out.println("CHOLE?");
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
//		Query q = pm.newQuery(SQL, "UPDATE CONVENCIONES SET PAZ_Y_SALVO = 1 WHERE id = ?");
//        q.setParameters(id);
//        q.executeUnique();
        return respuesta;    
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
