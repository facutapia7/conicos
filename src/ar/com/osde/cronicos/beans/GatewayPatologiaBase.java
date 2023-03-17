/*
 * Created on Nov 3, 2004
 *
 */
package ar.com.osde.cronicos.beans;

import ar.com.osde.framework.entity.VersionedEntityObject;

/** 
* class description 
*  
* @author	Adrian C. Martinez
*  
* @version	1.0.0
*  
* @date		Nov 3, 2004 
*/
public class GatewayPatologiaBase extends VersionedEntityObject {

	private GatewayDiaEpiPK gatePK = new GatewayDiaEpiPK();
		
	private long   medicoID;
	private long   vigHasta;
	private long   tramiteID;
	
	/**
	 * @return
	 */
	public long getTramiteID() {
		return tramiteID;
	}

	/**
	 * @return
	 */
	public long getVigHasta() {
		return vigHasta;
	}

	/**
	 * @param l
	 */
	public void setTramiteID(long l) {
		tramiteID = l;
	}

	/**
	 * @param l
	 */

	
	
	
	public void setVigHasta(long l)
	{
		vigHasta=l;
		String cantidadFecha;
	
		cantidadFecha=String.valueOf(vigHasta);
		if(!(cantidadFecha.length()==8))
			{
			throw new IllegalArgumentException("El texto ingresado no es una fecha válida, verificar cantidad de caracteres");
			}
		
		vigHasta=Long.parseLong(cantidadFecha);
	}
	


	
	/**
	 * @return
	 */
	public long getMedicoID() {
		return medicoID;
	}

	/**
	 * @param l
	 */
	public void setMedicoID(long l) {
		medicoID = l;
	}

	/**
	 * @return
	 */
	public GatewayDiaEpiPK getGatePK() {
		return gatePK;
	}

	/**
	 * @param diagnosticoPK
	 */
	public void setGatePK(GatewayDiaEpiPK diagnosticoPK) {
		gatePK = diagnosticoPK;
	}
	
	/**
	 * @param diagnosticoPK
	 */
	public void setGatePK(String nrobo, String codigo, long vigDesde) {
		gatePK.setNrobp(nrobo);
		gatePK.setCodigo(codigo);
		gatePK.setVigDesde(vigDesde);
	}
	
	/**
	 * Implementación de Equals
	 * @return true si es igual.
	 */
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (obj instanceof GatewayPatologiaBase) {
			GatewayPatologiaBase ope = (GatewayPatologiaBase) obj;
			if (ope.getGatePK().equals(this.getGatePK())) {
				return true;
			}
		}

		return false;
	}
	
	 	
	/**
	 * HashCode
	 * @return int HasChode
	 */
	public int hasCode() {
		return gatePK.hashCode();
	}
	
	/**
	 * Retorna string de datos
	 * @return String
	 */	
	public String toString() {
		return "pk: " + gatePK.toString() + "\n" 
				+ "medico: " + medicoID + "\n" 
				+ "vigHasta: " + vigHasta + "\n"
				+ "tremite: " + tramiteID + "\n";
	}				
}
