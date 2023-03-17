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
public class AfiliadoBCR implements BCResult {

	private String docnum;

	private String estado;

	private String fnac;

	private String descrTipsoc;

	private String docType;

	private String sexo;

	private String fecbaj;

	private String tipSoc;

	private String estitular;

	private String nombre;

	private String planServ;

	private String numeroSocio;

	private String ppLanServ;

	private String apellido;
	
	private String tpoEsp;

	private String parentesco;

	private String desParentesco;

	private String fechaAlta;

	private String mesesReconocidos;

	private String numeroIC;

	private String filial;

	private String orden;
	
	/**
	 * Construtos da classe.
	 */
	public AfiliadoBCR() {

	}

	/**
	 * Metodo que retorna Apellido. 
	 * @return apellido.
	 */
	public String getApellido() {

		return apellido;

	}

	/**
	 * Metodo que retorna Descr. Tip. Soc. 
	 * @return descrTipsoc.
	 */
	public String getDescrTipsoc() {

		return descrTipsoc;

	}

	/**
	 * Metodo que retorna Doc. Num.
	 * @return docnum.
	 */
	public String getDocnum() {

		return docnum;

	}

	/**
	 * Metodo que retorna Doc. Type. 
	 * @return docType.
	 */
	public String getDocType() {

		return docType;

	}

	/**
	 * Metodo que retorna Estado. 
	 * @return estado.
	 */
	public String getEstado() {

		return estado;

	}

	/**
	 * Metodo que retorna Estitular.
	 * @return estitular.
	 */
	public String getEstitular() {

		return estitular;

	}

	/**
	 * Metodo que retorna Fec Baj.
	 * @return fecbaj.
	 */
	public String getFecbaj() {

		return fecbaj;

	}

	/**
	 * Metodo que retorna Fnac. 
	 * @return fnac.
	 */
	public String getFnac() {

		return fnac;

	}

	/**
	 * Metodo que retorna Nombre. 
	 * @return nombre.
	 */
	public String getNombre() {

		return nombre;

	}

	/**
	 * Metodo que retorna Numero Socio.
	 * @return numeroSocio.
	 */
	public String getNumeroSocio() {

		return numeroSocio;

	}

	/**
	 * Metodo que retorna Plan. Serv.
	 * @return planServ.
	 */
	public String getPlanServ() {

		return planServ;

	}

	/**
	 * Metodo que retorna PP Lan Serv.
	 * @return ppLanServ.
	 */
	public String getPpLanServ() {

		return ppLanServ;

	}

	/**
	 * Metodo que retorna Sexo. 
	 * @return sexo.
	 */
	public String getSexo() {

		return sexo;

	}

	/**
	 * Metodo que retorna Tip Soc. 
	 * @return tipsoc.
	 */
	public String getTipSoc() {

		return tipSoc;

	}

	/**
	 * Metodo que armazena Apellido.
	 * @param anApellido Apellido.
	 */
	public void setApellido(String anApellido) {

		this.apellido = anApellido;

	}

	/**
	 * Metodo que armazena Descr. Tip. Soc. 
	 * @param anDescrTipsoc Descr. Tip. Soc.
	 */
	public void setDescrTipsoc(String anDescrTipsoc) {

		this.descrTipsoc = anDescrTipsoc;

	}

	/**
	 * Metodo que armazena Doc Num. 
	 * @param anDocnum Doc Num.
	 */
	public void setDocnum(String anDocnum) {

		this.docnum = anDocnum;

	}

	/**
	 * Metodo que armazena Doc Type.
	 * @param anDocType Doc Type.
	 */
	public void setDocType(String anDocType) {

		this.docType = anDocType;

	}

	/**
	 * Metodo que armazena Estado. 
	 * @param anEstado Estado.
	 */
	public void setEstado(String anEstado) {

		this.estado = anEstado;

	}

	/**
	 * Metodo que armazena Estitular.
	 * @param anEstitular Estitular.
	 */
	public void setEstitular(String anEstitular) {

		this.estitular = anEstitular;

	}

	/**
	 * Metodo que armazena FecBaj.
	 * @param anFecbaj FecBaj.
	 */
	public void setFecbaj(String anFecbaj) {

		this.fecbaj = anFecbaj;

	}

	/**
	 * Metodo que armazena Fnac. 
	 * @param anFnac Fnac.
	 */
	public void setFnac(String anFnac) {

		this.fnac = anFnac;

	}

	/**
	 * Metodo que armazena Nombre.
	 * @param anNombre Nombre.
	 */
	public void setNombre(String anNombre) {

		this.nombre = anNombre;

	}

	/**
	 * Metodo que armazena Numero Socio.
	 * @param anNumeroSocio Numero Socio.
	 */
	public void setNumeroSocio(String anNumeroSocio) {

		this.numeroSocio = anNumeroSocio;

	}

	/**
	 * Metodo que armazena o Plan Serv.
	 * @param anPlanServ Plan Ser.
	 */
	public void setPlanServ(String anPlanServ) {

		this.planServ = anPlanServ;

	}

	/**
	 * Metodo que armazena o PP Lan Serv.
	 * @param anPpLanServ PP Lan Serv.
	 */
	public void setPpLanServ(String anPpLanServ) {

		this.ppLanServ = anPpLanServ;

	}

	/**
	 * Metodo que armazena o Sexo.
	 * @param anSexo Sexo.
	 */
	public void setSexo(String anSexo) {

		this.sexo = anSexo;

	}

	/**
	 * Metodo que armazena o tipSoc.
	 * @param anTipSoc Tip Soc.
	 */
	public void setTipSoc(String anTipSoc) {

		this.tipSoc = anTipSoc;

	}

	/**
	 * @return Returns the fechaAlta.
	 */
	public String getFechaAlta() {
		return fechaAlta;
	}
	/**
	 * @param fechaAlta The fechaAlta to set.
	 */
	public void setFechaAlta(String pfechaAlta) {
		fechaAlta = pfechaAlta;
	}
	/**
	 * @return Returns the mesesReconocidos.
	 */
	public String getMesesReconocidos() {
		return mesesReconocidos;
	}
	/**
	 * @param mesesReconocidos The mesesReconocidos to set.
	 */
	public void setMesesReconocidos(String pmesesReconocidos) {
		mesesReconocidos = pmesesReconocidos;
	}
	/**
	 * @return Returns the parentesco.
	 */
	public String getParentesco() {
		return parentesco;
	}
	/**
	 * @param parentesco The parentesco to set.
	 */
	public void setParentesco(String parentesco) {
		this.parentesco = parentesco;
	}
	/**
	 * @return Returns the tpoesp.
	 */
	public String getTpoEsp() {
		return tpoEsp;
	}
	/**
	 * @param tpoesp The tpoesp to set.
	 */
	public void setTpoEsp(String tpoesp) {
		tpoEsp = tpoesp;
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
	public void setFilial(String pFilial) {
		filial = pFilial;
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
	public void setOrden(String pOrden) {
		orden = pOrden;
	}


	/**
	 * @return Returns the desParentesco.
	 */
	public String getDesParentesco() {
		return desParentesco;
	}
	/**
	 * @param desParentesco The desParentesco to set.
	 */
	public void setDesParentesco(String desParentesco) {
		this.desParentesco = desParentesco;
	}
	/**
	 * @return Returns the numeroIC.
	 */
	public String getNumeroIC() {
		return numeroIC;
	}
	/**
	 * @param numeroIC The numeroIC to set.
	 */
	public void setNumeroIC(String numeroIC) {
		this.numeroIC = numeroIC;
	}
}
