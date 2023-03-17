/**
 * Created on 01/12/2004
 *
 */
package ar.com.osde.cronicos.beans;

import java.io.Serializable;
import java.text.ParseException;

import ar.com.osde.cronicos.helper.GeneralHelper;


/**
 * @author PG26589512
 *
 */
public class AfiliadoBusqueda implements Serializable {

	private int filialServicio;
	private String nombre;
	private String apellido;
	private String plan;
	private String sexo;
	private int fechaAlta;
	private String tipoDocumento;
	private String numeroDocumento;
	private int fechaNacimiento;
	private String partner;
	
	private AfiliadoBusquedaPK afiliadoBusquedaPK;

	/**
	 * Constructor
	 *
	 */	
	public AfiliadoBusqueda() {
		/* nothing to do */
	}

	/**
	 * Constructor con PK
	 * @param filial
	 * @param socio
	 * @param beneficiario
	 */
	public AfiliadoBusqueda(int filial, int socio, int beneficiario) {
		AfiliadoBusquedaPK afiliadoBusquedaPK = new AfiliadoBusquedaPK();
		afiliadoBusquedaPK.setFilial(filial);
		afiliadoBusquedaPK.setSocio(socio);
		afiliadoBusquedaPK.setBeneficiario(beneficiario);
		
		this.setAfiliadoBusquedaPK(afiliadoBusquedaPK);
	}

	/**
	 * @return
	 */
	public int getBeneficiario() {
		return getAfiliadoBusquedaPK().getBeneficiario();
	}

	/**
	 * @return
	 */
	public int getFechaAlta() {
		return fechaAlta;
	}

	/**
	 * @return
	 */
	public int getFilial() {
		return getAfiliadoBusquedaPK().getFilial();
	}

	/**
	 * @return
	 */
	public int getFilialServicio() {
		return filialServicio;
	}

	/**
	 * @return
	 */
	public String getPlan() {
		return plan;
	}

	/**
	 * @return
	 */
	public String getSexo() {
		return sexo;
	}

	/**
	 * @return
	 */
	public long getSocio() {
		return getAfiliadoBusquedaPK().getSocio();
	}

	/**
	 * @param i
	 */
	public void setFechaAlta(int i) {
		fechaAlta = i;
	}

	/**
	 * @param i
	 */
	public void setFilialServicio(int i) {
		filialServicio = i;
	}

	/**
	 * @param string
	 */
	public void setPlan(String string) {
		plan = string;
	}

	/**
	 * @param string
	 */
	public void setSexo(String string) {
		sexo = string;
	}

	/**
	 * @return
	 */
	public AfiliadoBusquedaPK getAfiliadoBusquedaPK() {
		return afiliadoBusquedaPK;
	}

	/**
	 * @param socioPK
	 */
	public void setAfiliadoBusquedaPK(AfiliadoBusquedaPK afiliadoBusquedaPK) {
		this.afiliadoBusquedaPK = afiliadoBusquedaPK;
	}

	/**
	 * Equals
	 */	
	public boolean equals(Object obj) {
		if(obj == this) {
			return true;
		}
				
		if (obj instanceof AfiliadoBusqueda) {
			if (((AfiliadoBusqueda)obj).getAfiliadoBusquedaPK().equals(this.getAfiliadoBusquedaPK())) {
				return true;
			}
		}
		
		return false;
	}

	/**
	 * HasCode
	 */
	public int hashCode() {
		return getAfiliadoBusquedaPK().hashCode();
	}
	/**
	 * @return
	 */
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	/**
	 * @param i
	 */
	public void setTipoDocumento(String i) {
		tipoDocumento = i;
	}

	/**
	 * @return
	 */
	public int getFechaNacimiento() {
		return fechaNacimiento;
	}

	/**
	 * @param i
	 */
	public void setFechaNacimiento(int i) {
		fechaNacimiento = i;
	}

	/**
	 * Retorna Numero de Afiliado Completo
	 * @return String
	 */
	public String getNumeroSocio() {
		return GeneralHelper.maskString(new Integer(getAfiliadoBusquedaPK().getFilial()), "00") + "-" +
			   GeneralHelper.maskString(new Integer(getAfiliadoBusquedaPK().getSocio()), "0000000") + "-" +
			   GeneralHelper.maskString(new Integer(getAfiliadoBusquedaPK().getBeneficiario()), "00");
	}
	
	/**
	 * Fecha de Alta en Formato dd/mm/yyyy
	 * @return
	 */
	public String getFechaAltaString() {
		try {
			return GeneralHelper.parseDateToString(
					GeneralHelper.parseDateASToDate(fechaAlta));
		} catch (ParseException p) {
			return "";
		}
	}
	
	/**
	 * Retorna la Edad
	 * @return
	 */
	public String getEdad() {
		try {
			return "" + GeneralHelper.getOldYear(
					    GeneralHelper.parseDateASToDate(fechaNacimiento));
		} catch (Exception e) {
			return "";
		}
		
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

	/**
	 * @return Returns the numeroDocumento.
	 */
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	/**
	 * @param numeroDocumento The numeroDocumento to set.
	 */
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
}
