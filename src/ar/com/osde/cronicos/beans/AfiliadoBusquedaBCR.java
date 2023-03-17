package ar.com.osde.cronicos.beans;

import ar.com.osde.framework.bconnector.BCResult;

/**
 * 
 * Represents a Business Connector Result for IAS.
 * @author Tiago F. Frazao
 * 
 * @version 1.00 Created on Jun 8, 2004
 * 
 */
public class AfiliadoBusquedaBCR implements BCResult {

	private String partner;
	private String apellido;
	private String nombre;
	private String docType;
	private String docNum;
	private String numeroSocio;
	private String nroOrden;

	/**
	 * Construtos da classe.
	 */
	public AfiliadoBusquedaBCR() {

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
	 * @return Returns the docNum.
	 */
	public String getDocNum() {
		return docNum;
	}
	/**
	 * @param docNum The docNum to set.
	 */
	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}
	/**
	 * @return Returns the docType.
	 */
	public String getDocType() {
		return docType;
	}
	/**
	 * @param docType The docType to set.
	 */
	public void setDocType(String docType) {
		this.docType = docType;
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
	/**
	 * @return Returns the nroOrden.
	 */
	public String getNroOrden() {
		return nroOrden;
	}
	/**
	 * @param nroOrden The nroOrden to set.
	 */
	public void setNroOrden(String nroOrden) {
		this.nroOrden = nroOrden;
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
	 * @return Returns the partner.
	 */
	public String getPartner() {
		return partner;
	}
	/**
	 * @param partner The partner to set.
	 */
	public void setPartner(String partner) {
		this.partner = partner;
	}
}
