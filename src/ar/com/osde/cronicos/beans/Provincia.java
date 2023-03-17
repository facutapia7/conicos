/*
 * Created on 17/08/2004
 */
package ar.com.osde.cronicos.beans;

import ar.com.osde.framework.entity.EntityObject;

/**
 * Clase que representa a una Provincia.
 * @author Diego Naya
 * @version 1.0
 * Created on 17/08/2004
 */
public class Provincia implements EntityObject {
	private String nombre;
	private String codigo;
	/**
	 * Constructor base.
	 *
	 */
	public Provincia() {
		super();
	}
	/**
	 * Constructor base.
	 * @param cod El Codigo de la Provincia.
	 * @param nomb El Nombre de la Provincia.
	 */
	public Provincia(String cod, String nomb) {
		super();
		codigo = cod;
		nombre = nomb;
	}
	/**
	 * Constructor base.
	 * @param cod El Codigo de la Provincia.
	 */
	public Provincia(String cod) {
		super();
		codigo = cod;
	}

	/**
	 * @return El Codigo de la Provincia.
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @return El Nombre de la Provincia.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param string El Codigo de la Provincia.
	 */
	public void setCodigo(String string) {
		codigo = string;
	}

	/**
	 * @param string El Nombre de la Provincia.
	 */
	public void setNombre(String string) {
		nombre = string;
	}

}
