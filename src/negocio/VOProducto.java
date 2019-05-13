package negocio;

/**
 * Interfaz para los metodos get de PRODUCTO
 * @author jd.diazc
 *
 */
public interface VOProducto {

	/**
	 * @return the id
	 */
	public long getId();

	/**
	 * @return the nombre
	 */
	public String getNombre();

	/**
	 * @return the valor
	 */
	public Double getValor();

	/**
	 * @return the idServicio
	 */
	public long getIdServicio();

}
