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
public class AfiliadoBCC implements BCCommand {

	private String filial;
	private String numeroSocio;
	private String orden;
	
	/**
	 * Constructor.
	 */
	public AfiliadoBCC() {

	}

	/**
	 * @return Returns the filial.
	 */
	public String getFilial() {
		return filial;
	}
	/**
	 * @param filial The filial to set.
	 */
	public void setFilial(String filial) {
		this.filial = filial;
	}
	/**
	 * @return Returns the numeroSocio.
	 */
	public String getNumeroSocio() {
		return numeroSocio;
	}
	/**
	 * @param numeroSocio The numeroSocio to set.
	 */
	public void setNumeroSocio(String numeroSocio) {
		this.numeroSocio = numeroSocio;
	}
	/**
	 * @return Returns the orden.
	 */
	public String getOrden() {
		return orden;
	}
	/**
	 * @param orden The orden to set.
	 */
	public void setOrden(String orden) {
		this.orden = orden;
	}
}
