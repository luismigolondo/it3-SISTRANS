package persistencia;



import java.math.BigDecimal;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.swing.JOptionPane;

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
			
			String lel = "SELECT c.NOMBRE,p.VALOR,g.ID_RESERVA_HABITACION " + "FROM ((CLIENTES c INNER JOIN GASTOS g ON c.ID_RESERVA_HABITACION=g.ID_RESERVA_HABITACION AND c.ID_CONVENCION="+id+")" 
					+" INNER JOIN PRODUCTOS p ON g.ID_PRODUCTO=p.ID) LEFT OUTER JOIN RESERVAS_CONVENCIONES rc ON p.ID_SERVICIO=rc.ID_SERVICIO WHERE rc.ID_SERVICIO IS NULL"
					+" ORDER BY c.ID_RESERVA_HABITACION ";
			
			Query q = pm.newQuery(SQL,lel);
			
			List<Object> objects = q.executeList();
			String autorAct = "-";
			Double tot = 0.0;
			for (Object o : objects) {
				Object[] datos = (Object[]) o;
				if(!datos[0].equals(autorAct)){
					respuesta+=autorAct+" *"+ (Double.parseDouble(datos[2].toString())-1)+"* DEBE PAGAR "+tot +", \n";
					autorAct=datos[0].toString();
					tot=0.0;
				}
				tot+=((BigDecimal)datos[1]).longValue();
			}

			
			String lel2 = "SELECT p.VALOR FROM ((CLIENTES c INNER JOIN GASTOS g ON c.ID_RESERVA_HABITACION=g.ID_RESERVA_HABITACION AND c.ID_CONVENCION="+id+") "
							+"INNER JOIN PRODUCTOS p ON g.ID_PRODUCTO=p.ID) INNER JOIN RESERVAS_CONVENCIONES rc ON p.ID_SERVICIO=rc.ID_SERVICIO ORDER BY c.ID_RESERVA_HABITACION";
			Query q2 = pm.newQuery(SQL,lel2);
			List<Object> objects2 = q2.executeList();
			Double tot2=0.0;
			respuesta+="LA CONVENCION CON ID *"+id+"*DEBE PAGAR ";
			
			for (Object o : objects2) {

				tot2+=((BigDecimal)o).longValue();
			}
			respuesta+=tot2;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
        return respuesta;    
	}
	
	public long pazYSalvoConvencion(PersistenceManager pm,long idConv)
	{
		try{
			
			Query q = pm.newQuery(SQL, "UPDATE CONVENCIONES SET PAZ_Y_SALVO = 1 WHERE id = ?");
			q.setParameters(idConv);
			q.executeUnique();
			return 1;
		}
		catch(Exception e){
			return -1;
		}
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
