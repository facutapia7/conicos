/**
 * Created on 17/12/2004
 *
 */
package ar.com.osde.cronicos.beans;

import ar.com.osde.framework.entity.EntityObject;

/**
 * @author PG26589512
 *
 */
public class TipoDocumento implements EntityObject {

	private int codigo;
	private String descripcion = null;

	/**
	 * @return
	 */
	public int getCodigo() {
		return codigo;
	}

	/**
	 * @return
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param i
	 */
	public void setCodigo(int i) {
		codigo = i;
	}

	/**
	 * @param string
	 */
	public void setDescripcion(String string) {
		descripcion = string;
	}

}
