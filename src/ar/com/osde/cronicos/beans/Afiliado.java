/*
 * Created on 17/08/2004
 */
package ar.com.osde.cronicos.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import ar.com.osde.cronicos.Constantes;
import ar.com.osde.cronicos.helper.GeneralHelper;
import ar.com.osde.cronicos.persistance.IObjectConcurrency;
import ar.com.osde.framework.entity.EntityObject;

/**
 * Esta clase representa un Afiliado Basico. No esta preparada para ser persistida por Hibernate 
 * (no extiende de VersionedEntityObject)
 * @author Diego Naya
 * @version 1.0
 * Created on 17/08/2004
 * Update Adrian C. Martinez 01/11/2004
 * Update Adrian C. Martinez 17/01/2005
 */
public class Afiliado implements EntityObject, IObjectConcurrency {
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Afiliado ["
				+ (nroBP != null ? "nroBP=" + nroBP + ", " : "") + "\n"
				+ (nombre != null ? "nombre=" + nombre + ", " : "") + "\n"
				+ (apellido != null ? "apellido=" + apellido + ", " : "") + "\n"
				+ (fechaNacimiento != null ? "fechaNacimiento="	+ fechaNacimiento + ", " : "") + "\n"
				+ (sexo != null ? "sexo=" + sexo + ", " : "") + "\n"
				+ "actualizacion="	+ actualizacion	+ ", " + "\n"
				+ (concurrency != null ? "concurrency=" + concurrency + ", " : "") + "\n"
				+ (fechaBaja != null ? "fechaBaja=" + fechaBaja + ", " : "") + "\n"
				+ (parentesco != null ? "parentesco=" + parentesco + ", " : "") + "\n"
				+ (parentescoId != null ? "parentescoId=" + parentescoId + ", "	: "") + "\n"
				+ (fechaAlta != null ? "fechaAlta=" + fechaAlta + ", " : "") + "\n"
				+ (tipo != null ? "tipo=" + tipo + ", " : "") + "\n"
				+ (mesesReconocidos != null ? "mesesReconocidos="+ mesesReconocidos + ", " : "") + "\n"
				+ (carencias != null ? "carencias=" + carencias + ", " : "") + "\n"
				+ (plan != null ? "plan=" + plan + ", " : "") + "\n" 
				+ "codigo="	+ codigo + "]";
	}

	/* Para formatear fechas */
	private static SimpleDateFormat sdfDMY = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");

	private String  nroBP;
	private String  nombre;
	private String  apellido;
	private String  fechaNacimiento;
	private String  sexo = "M";
	private boolean actualizacion = false;
	private Long    concurrency;
	private String  fechaBaja;

	private String  parentesco;
	private String	parentescoId;
	private String  fechaAlta;
	private String  tipo;
	private String  mesesReconocidos;
	private String  carencias;
	private String  plan;

	/**
  	* Numero de Afiliado Completo
  	*/
 	private long codigo;
 	
	/**
	* Transforma el numero de filial, el numero de afiliado y el de beneficiario
	* en una representación en forma de String, con la cantidad de ceros correcta.
	* @param filial la filial del afiliado
	* @param nroAfiliado el numero de afiliado
	* @param beneficiario el numero de beneficiario
	* @return un String que contiene los tres numeros 
	* con sus correspondientes ceros.
	*/
	public static String parseAfiliadoString(
		int filial, int nroAfiliado, int beneficiario)
	{
		String beneString = GeneralHelper.completarCon(beneficiario, "00");
		String filiString = GeneralHelper.completarCon(filial, "00");
		String afilString = GeneralHelper.completarCon(nroAfiliado, "0000000");
		 
		return filiString + afilString + beneString;
	}

	/**
 	* @return
 	*/
	public String getApellido() {
		return apellido;
	}

	/**
 	* @return
 	*/
	public long getCodigo() {
		return codigo;
	}

	/**
 	* @return
 	*/
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	/**
	 * @return
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * @return
	 */
	public String getNroBP() {
		return nroBP;
	}
	
	/**
	 * @return
	 */
	public String getSexo() {
		return sexo;
	}
	
	/**
	 * @param string el Apellido del Afiliado
	 */
	public void setApellido(String string) {
		apellido = string;
	}
	
	/**
	 * @param l  El Codigo del Afiliado
	 */
	public void setCodigo(long l) {
		codigo = l;
	}
	
	/**
	 * @param string
	 */
	public void setFechaNacimiento(String string) {
		fechaNacimiento = string;
	}
	
	/**
	 * @param string
	 */
	public void setNombre(String string) {
		nombre = string;
	}
	
	/**
	 * @param string
	 */
	public void setNroBP(String string) {
		nroBP = string;
	}
	
	/**
	 * @param string
	 */
	public void setSexo(String string) {
		sexo = string;
	}
	
	public String getFilial(){
		String filial = this.getCodigoAsString();
		// La filial son los dos primeros digitos del codigo
		return filial.substring(0, 2);
	}
	
	public String getNroAfiliado(){
		String nroAfiliado = this.getCodigoAsString();
		// El nro de afiliado son los 7 digitos despues de la filial
		return nroAfiliado.substring(2, 9);
		
	}
	
	public String getBeneficiario(){
		String beneficiario = this.getCodigoAsString();
		// El nro de beneficiario son los dos ultimos digitos
		return beneficiario.substring(9, 11);
	}
	
	private String getCodigoAsString(){
		String retorno = String.valueOf(this.codigo);
		if(retorno.length() < 11){
			retorno = "0" + retorno;
		}
		return retorno;
	}
	
	/**
	 * @return
	 */
	public boolean isActualizacion() {
		return actualizacion;
	}
	
	/**
	 * @param b
	 */
	public void setActualizacion(boolean b) {
		actualizacion = b;
	}
	
	/**
	 * Retorna ID para Concurrencia
	 * @return String 
	 */
	public String getCheckID() {
		return this.getNroBP();
	}
	
	/**
	 * Retorna check value para Concurrencia
	 * @return Long
	 */
	public Long getCheckValue() {
		return concurrency;
	}

	/**
	 * Setea valor para concurrencia
	 * @param value 
	 */
	public void setCheckValue(Long value) {
		this.concurrency = value;
	}
	
	/**
	 * Retorna fecha de nacimiento en formato dd/mm/yyyy
	 * @return String
	 */
	public String getFechaNacimientoDDMMYYYY() {
		String fecha = getFechaNacimiento();
		
		try {
			return sdfDMY.format(sdfYMD.parse(fecha));
			
		} catch (ParseException p) {
			
			return fecha; 
		}
	}
	public String getFechaNacimientoFormatted() {
		if (this.getFechaNacimientoDDMMYYYY().equals(Constantes.FECHA_NULL)) {
			return " ";
		} else {
			return this.getFechaNacimientoDDMMYYYY();
		}
	}
	/**
	 * @return Returns the fechaBaja.
	 */
	public String getFechaBaja() {
		return fechaBaja;
	}
	/**
	 * @param fechaBaja The fechaBaja to set.
	 */
	public void setFechaBaja(String fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	public String getFechaBajaDDMMYYYY() {
		String fecha = getFechaBaja();
		
		try {
			
			if (fecha.equals(Constantes.BC_FECHA_NULL)) {
				return Constantes.FECHA_NULL;
			}
			
			return sdfDMY.format(sdfYMD.parse(fecha));
			
		} catch (ParseException p) {
			
			return fecha; 
		}
	}
	public String getFechaBajaFormatted() {
		if (this.getFechaBajaDDMMYYYY().equals(Constantes.FECHA_NULL)) {
			return " ";
		} else {
			return this.getFechaBajaDDMMYYYY();
		}
	}

	/**
	 * @return Returns the carencias.
	 */
	public String getCarencias() {
	
		if ( this.carencias.trim().equals(Constantes.BC_CARENCIA_NULL )) {
			return " ";
		} else {
			return carencias;
		}
	}
	/**
	 * @param carencias The carencias to set.
	 */
	public void setCarencias(String carencias) {
		this.carencias = carencias;
	}
	/**
	 * @return Returns the fechaAlta.
	 */
	public String getFechaAlta() {
		return fechaAlta;
	}
	
	/**
	 * Retorna fecha de alta en formato dd/mm/yyyy
	 * @return String
	 */
	public String getFechaAltaDDMMYYYY() {
		String fecha = getFechaAlta();
		
		try {
			return sdfDMY.format(sdfYMD.parse(fecha));
			
		} catch (ParseException p) {
			
			return fecha; 
		}
	}
	public String getFechaAltaFormatted() {
		if (this.getFechaAltaDDMMYYYY().equals(Constantes.FECHA_NULL)) {
			return " ";
		} else {
			return this.getFechaAltaDDMMYYYY();
		}
	}
	/**
	 * @param fechaAlta The fechaAlta to set.
	 */
	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
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
	public void setMesesReconocidos(String mesesReconocidos) {
		this.mesesReconocidos = mesesReconocidos;
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
	 * @return Returns the plan.
	 */
	public String getPlan() {
		return plan;
	}
	/**
	 * @param plan The plan to set.
	 */
	public void setPlan(String plan) {
		this.plan = plan;
	}
	/**
	 * @return Returns the tipo.
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param tipo The tipo to set.
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getParentescoId() {
		return parentescoId;
	}

	public void setParentescoId(String parentescoId) {
		this.parentescoId = parentescoId;
	}
}
