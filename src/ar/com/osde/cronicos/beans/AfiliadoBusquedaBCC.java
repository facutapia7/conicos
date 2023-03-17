package ar.com.osde.cronicos.beans;

import ar.com.osde.framework.bconnector.BCCommand;

/**
 * Represents a Business Connector Command for IAS.
 * 
 * @author Tiago F. Frazao
 * 
 * @version 1.00 Created on Jun 2, 2004
 * 
 */
public class AfiliadoBusquedaBCC implements BCCommand {

	private String nombre;
	private String apellido;
	
	/**
	 * Constructor.
	 */
	public AfiliadoBusquedaBCC() {

	}

	
	/**
	 * @return Returns the apellido.
	 */
	public String getApellido() {
		return apellido;
	}
	/**
	 * @param apellido The apellido to set.
	 */
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	/**
	 * @return Returns the nombre.
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre The nombre to set.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
