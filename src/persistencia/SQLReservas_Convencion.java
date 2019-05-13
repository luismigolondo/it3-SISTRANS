package persistencia;

import java.util.Date;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

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
	
	public String registrarHabsServs(PersistenceManager pm,long id,long idConvencion, long idHotel, String habs, String servs) throws Exception
	{
		String respuesta = "";
		//EL ID DE CADA RESERVA ES A PARTIR DEL ID QUE PROVEE EL USUARIO (llega por parametro a este metodo)
		//ES DECIR, LA PRIMERA RESERVA VA A TENER EL ID DADO PRO EL USUARIO, LUEGO SE SUMA DE A 1
		String[] habitacionesCant = habs.split(";");
		System.out.println(habitacionesCant.length+"HABITACIONES CANT");
		
		//AQUI AVERIGUO LA FECHA INICIO DE LA CONVENCION SOBRE LA CUAL QUIERO HACER RESERVAS
		System.out.println("LLEGA AQUIFULL1");
		Query consultaFechaInic = pm.newQuery(SQL,"SELECT c.FECHA_INICIO FROM CONVENCIONES c WHERE c.ID="+idConvencion);
		System.out.println("LLEGA AQUIFULL2");
		Object objectsfecha = consultaFechaInic.execute();
		String anio = objectsfecha.toString().split(" ")[0].split("-")[0];
		String mes = objectsfecha.toString().split(" ")[0].split("-")[1];
		System.out.println(mes+"MES");
		String dia = objectsfecha.toString().split(" ")[0].split("-")[2];
		System.out.println(dia+"DIA");
		System.out.println("LLEGA AQUIFULL3");
		String fecha=objectsfecha.toString();
		System.out.println("LLEGA AQUIFULL4");
		System.out.println(fecha+"FECHAFULL4");
		System.out.println(dia+"/"+mes+"/"+anio);
		Date fechainicialConv = convertStringToDate(dia+"/"+mes+"/"+anio.substring(1, anio.length()));
		System.out.println("LLEGA AQUIFULL");
		
		//tipoHabitacion:cantidad;tipoHabitacion:cantidad
		long contador = id;
		int contHabAgreg=0;
		int contServAgreg=0;
		for(int e=0; e<habitacionesCant.length;e++) {
			String[] tipoHabAct = habitacionesCant[e].split(":");
			System.out.println(tipoHabAct[1]+"TIPO HAB ACTUAL");
			if(Integer.parseInt(tipoHabAct[1])>0) {
				Query x = pm.newQuery(SQL, "SELECT h.ID FROM "+"HABITACIONES"+" h "
						+ "LEFT JOIN "+"RESERVAS_HABITACIONES"+" hr ON hr.ID=h.ID"
						+ " WHERE hr.ID IS NULL AND h.TIPO_HABITACION="+tipoHabAct[0]);
				System.out.println("LLEGA AQUIIAISKDLJASLKDJSAKLJDKLSAJDKLSAJ");
				List<Object> objects = x.executeList();
				
				try{
					for(int p=0;p<objects.size() && p<Integer.parseInt(tipoHabAct[1]);p++){
						System.out.println(objects.get(p).toString()+"TO STREEENG");
						long idHabitacion = (long)Long.parseLong(objects.get(p).toString());

						//AQUI REVISO QUE LA HABITACION QUE QUIERO RESERVAR PARA LA CONVENCION NO ESTE YA RESERVADA PARA OTRA CONVENCION.
						//SI YA ESTA RESERVADA PARA OTRA CONVENCION, REVISO LA FECHA DE TERMINACION DE DICHA CONVENCION SEA ANTES QUE LA DE INICIO DE LA CONVENCION ACTUAL
						Query consulta = pm.newQuery(SQL,"SELECT c.FECHA_FIN FROM CONVENCIONES c INNER JOIN RESERVAS_CONVENCIONES rc ON c.ID=rc.ID_CONVENCION AND rc.ID_HABITACION="+idHabitacion);
						Object objectsfech = consulta.execute();
						if(objectsfech!=null){
							String fechaConvAct=objectsfech.toString();
							System.out.println("ESTA ES LA FECHA A MODIFICAR--> "+fechaConvAct);
							System.out.println("ESTA ES LA FECHA SPLIT1  "+fechaConvAct.split(" "));
							
							String anio1 = fechaConvAct.toString().split(" ")[0].split("-")[0];
							String mes1 = fechaConvAct.toString().split(" ")[0].split("-")[1];
							String dia1 = fechaConvAct.toString().split(" ")[0].split("-")[2];
							Date DateConvAct = convertStringToDate(dia1+"/"+mes1+"/"+anio1.substring(1, anio1.length()));

							//SI LA FECHA INICIAL DE LA CONVENCION DONDE QUIERO RESERVAR ES DESPUES DE LA FECHA FINAL DE LA CONVENCION QUE TAMBIEN TIENE RESERVADA LA HABITACION A RESERVAR, ENTONCES SE PUEDE
							//RESERVAR
							if(fechainicialConv.compareTo(DateConvAct)>0){							
								Query xy = pm.newQuery(SQL,"INSERT INTO "+"RESERVAS_CONVENCIONES"+" (ID,ID_CONVENCION,ID_HABITACION,ID_SERVICIO)"
										+ "VALUES (?,?,?,?)");
								xy.setParameters(contador,idConvencion,idHabitacion,null);
								xy.executeUnique();
								contador++;
								contHabAgreg++;
							}
						}
					}
				}
				catch(Exception q){
					q.printStackTrace();
				}
			}
		}
		System.out.println("-----------------EL PELUDO TERMINA--------------------");
		//USO VARIABLE CONTADOR PORQUE EL PRIMER ID LO USA LA PRIMERA HABITACION, LA SIGUIENTE HABITACION USA ID+1
		//ENTONCES PARA NO GENERAR DOS RESERVAS CON EL MISMO ID, LLEVO LA CUENTA DEL ULTIMO ID CREADO CON EL CONTADOR
		String[] servicios = servs.split(",");
		for(int e=0;e<servicios.length;e++) {
			
			//AQUI REVISO QUE EL SERVICIO QUE QUIERO RESERVAR PARA LA CONVENCION NO ESTE YA RESERVADO PARA OTRA CONVENCION
			//SI YA ESTA RESERVAO PARA OTRA CONVENCION, REVISO LA FECHA DE TERMINACION DE DICHA CONVENCION SEA ANTES QUE LA DE INICIO DE LA CONVENCION ACTUAL
			Query consulta = pm.newQuery(SQL,"SELECT c.FECHA_FIN FROM CONVENCIONES c INNER JOIN RESERVAS_CONVENCIONES rc ON c.ID=rc.ID_CONVENCION AND rc.ID_SERVICIO="+servicios[e]);
			Object objectsfech = consulta.executeUnique();
			String fechaConvAct=objectsfech.toString();
			String anio1 = fechaConvAct.toString().split(" ")[0].split("-")[0];
			String mes1 = fechaConvAct.toString().split(" ")[0].split("-")[1];
			String dia1 = fechaConvAct.toString().split(" ")[0].split("-")[2];
			Date DateConvAct = convertStringToDate(dia1+"/"+mes1+"/"+anio1.substring(1,anio1.length()));
			
			if(fechainicialConv.compareTo(DateConvAct)>0){				
				Query xy = pm.newQuery(SQL,"INSERT INTO "+"RESERVAS_CONVENCIONES"+" (ID,ID_CONVENCION,ID_HABITACION,ID_SERVICIO)"
						+ "VALUES (?,?,?,?)");
				long idd2 = contador+e;
				xy.setParameters(idd2,idConvencion,null,servicios[e]);
				xy.executeUnique();
			}
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
	
	public Date convertStringToDate(String dateInString) throws Exception
	{
		 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	        try {
	            Date date = formatter.parse(dateInString);
	            System.out.println(date);
	            System.out.println(formatter.format(date));
	            return date;

	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
			return null;
	}
	
	

}
