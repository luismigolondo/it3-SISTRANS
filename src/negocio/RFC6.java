/**
 * 
 */
package negocio;

import java.sql.Date;

/**
 * @author lm.gomezl
 *
 */
public class RFC6 {

	private long consumido_x_veces;
	
	private Date gasto_generado_el;
	
	public RFC6() {
		this.consumido_x_veces = 0;
		this.gasto_generado_el = null;
	}

	/**
	 * @return the consumido_x_veces
	 */
	public long getConsumido_x_veces() {
		return consumido_x_veces;
	}

	/**
	 * @param consumido_x_veces the consumido_x_veces to set
	 */
	public void setConsumido_x_veces(long consumido_x_veces) {
		this.consumido_x_veces = consumido_x_veces;
	}

	/**
	 * @return the gasto_generado_el
	 */
	public Date getGasto_generado_el() {
		return gasto_generado_el;
	}

	/**
	 * @param gasto_generado_el the gasto_generado_el to set
	 */
	public void setGasto_generado_el(Date gasto_generado_el) {
		this.gasto_generado_el = gasto_generado_el;
	}
	
	public String toString() {
		return "Consumido " + consumido_x_veces + " veces en la fecha " + this.gasto_generado_el.toString();
	}
	
}
