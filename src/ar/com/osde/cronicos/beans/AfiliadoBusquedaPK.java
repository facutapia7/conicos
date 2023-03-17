/**
 * Created on 01/12/2004
 *
 */
package ar.com.osde.cronicos.beans;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author PG26589512
 *
 */
public class AfiliadoBusquedaPK implements Serializable {
	
	private int filial;
	private int socio;
	private int beneficiario;

	/**
	 * @return
	 */
	public int getBeneficiario() {
		return beneficiario;
	}

	/**
	 * @return
	 */
	public int getFilial() {
		return filial;
	}

	/**
	 * @return
	 */
	public int getSocio() {
		return socio;
	}

	/**
	 * @param i
	 */
	public void setBeneficiario(int i) {
		beneficiario = i;
	}

	/**
	 * @param i
	 */
	public void setFilial(int i) {
		filial = i;
	}

	/**
	 * @param i
	 */
	public void setSocio(int i) {
		socio = i;
	}

	public boolean equals(Object object) {
		if (!(object instanceof AfiliadoBusquedaPK)) {
			return false;
		}
		AfiliadoBusquedaPK afPK = (AfiliadoBusquedaPK) object;
		return new EqualsBuilder()
			.appendSuper(super.equals(object))
			.append(this.filial, afPK.filial)
			.append(this.socio, afPK.socio)
			.append(this.beneficiario, afPK.beneficiario)
			.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-787904809, -2095798911)
			.appendSuper(super.hashCode())
			.append(this.filial)
			.append(this.socio)
			.append(this.beneficiario)
			.toHashCode();
	}

}
