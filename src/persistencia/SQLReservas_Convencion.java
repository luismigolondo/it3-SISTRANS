package persistencia;

import java.sql.ResultSet;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLReservas_Convencion {
	
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
	public SQLReservas_Convencion(PersistenciaCadenaHoteles ph)
	{
		this.ph = ph;
	}
	
	public String registrarHabsServs(PersistenceManager pm,long id,long idConvencion, long idHotel, String habs, String servs)
	{
		String respuesta = "";
		//EL ID DE CADA RESERVA ES A PARTIR DEL ID QUE PROVEE EL USUARIO (llega por parametro a este metodo)
		//ES DECIR, LA PRIMERA RESERVA VA A TENER EL ID DADO PRO EL USUARIO, LUEGO SE SUMA DE A 1
		String[] habitacionesCant = habs.split(";");
		System.out.println(habitacionesCant.length+"HABITACIONES CANT");
		
		//tipoHabitacion:cantidad;tipoHabitacion:cantidad
		long contador = id;
		for(int e=0; e<habitacionesCant.length;e++) {
			String[] tipoHabAct = habitacionesCant[e].split(":");
			if(Integer.parseInt(tipoHabAct[1])>0) {
				Query x = pm.newQuery(SQL, "SELECT h.ID FROM "+"HABITACIONES"+" h "
						+ "LEFT JOIN "+"RESERVAS_HABITACIONES"+" hr ON hr.ID=h.ID"
						+ " WHERE hr.ID IS NULL AND h.TIPO_HABITACION="+tipoHabAct[0]);
				x.setResultClass();
				
				try{
					ResultSet o = (ResultSet)x.execute();
					while(o.next()){
						System.out.println("IMPRIMIENDO LO QUE REOTRNA EL SELECT: "+o.getString(1));
					}
					
				}
				catch(Exception q){
					
				}
				
//				for(int i=0;i<oA.length;i++) {
//					Object[] datosAct = (Object[])oA[i];
//					if(oA!=null)
//					{
//						long idHab = (long)datosAct[0];
//						Query xy = pm.newQuery(SQL,"INSERT INTO "+"RESERVAS_CONVENCIONES"+" (ID,ID_CONVENCION,ID_HABITACION,ID_SERVICIO)"
//								+ "VALUES (?,?,?,?)");
//						long idd = id+i;
//						xy.setParameters(idd,idConvencion,idHab,null);
//						respuesta+=idd;
//						contador++;
//					}					
//				}
			}
		}
		//USO VARIABLE CONTADOR PORQUE EL PRIMER ID LO USA LA PRIMERA HABITACION, LA SIGUIENTE HABITACION USA ID+1
		//ENTONCES PARA NO GENERAR DOS RESERVAS CON EL MISMO ID, LLEVO LA CUENTA DEL ULTIMO ID CREADO CON EL CONTADOR
		String[] servicios = servs.split(",");
		for(int e=0;e<servicios.length;e++) {
			Query xy = pm.newQuery(SQL,"INSERT INTO "+"RESERVAS_CONVENCIONES"+" (ID,ID_CONVENCION,ID_HABITACION,ID_SERVICIO)"
					+ "VALUES (?,?,?,?)");
			long idd2 = contador+e;
			xy.setParameters(idd2,idConvencion,null,servicios[e]);
			respuesta+=idd2;
		}
		
		return respuesta;
	}
	
	public String eliminarReservas(PersistenceManager pm,long idHotel, long idConvencion, String habs, String servs)
	{
		//LAS "RESERVAS" EN REALIDAD SON INGRESAR EN LA TABLA RESERVACONVENCIONES LOS IDS DE HABITACIONES QUE SE
		//USARAN DURANTE LA CONVENCION. DE IGUAL SE HACE CON LOS SERVICIOS
		//ESTO SE HACE DE ESTA FORMA PUES NO SE PUEDE REALIZAR UNA RESERVA AL NO TENER EL CLIENTE.
		//PERO CON AGREGARLOS EN LA MENCIONADA TABLA BASTA, PUES AL INTENTAR RESERVAR UNA HABITACION SE PUEDE VERIFICAR
		//QUE NO ESTE PRESENTE EN LA TABLA DE HABITACIONES RESERVADAS PARA LA CONVENCION PARA ESA FECHA
		try {
			
			String[] habitaciones = habs.split(",");
			for(int e=0;e<habitaciones.length;e++)
			{
				Query xy=pm.newQuery(SQL,"DELETE FROM "+"RESERVAS_CONVENCIONES"+" WHERE ID_HABITACION="+habitaciones[e] + "AND "
						+ "ID_CONVENCION="+idConvencion+" AND ID_HOTEL="+idHotel);
			}
			
			String[] servicios = servs.split(",");
			
			for(int e=0;e<servicios.length;e++)
			{
				Query xy=pm.newQuery(SQL,"DELETE FROM "+"RESERVAS_CONVENCIONES"+" WHERE ID_SERVOCOP="+servicios[e] + "AND "
						+ "ID_CONVENCION="+idConvencion+" AND ID_HOTEL="+idHotel);		}
			
			return "Habitaciones desreservadas:"+habs+"Servicios desreservados:"+servs;
		}
		catch(Exception e) {
			return "No se desreservo ninguna habitacion ni servicio";
		}
	}
	
	

}
