package ar.com.osde.cronicos.beans;

/**
 * @author Adrian C. Martinez
 * @version 1.0
 * Created on 01/11/2004
 */
public class GatewayDatoEpidemiologico extends GatewayPatologiaBase {
	
	/**
	 * Implementación de Equals
	 * @return true si es igual.
	 */
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (obj instanceof GatewayDiagnostico) {
			GatewayDiagnostico ope = (GatewayDiagnostico) obj;
			if (ope.getGatePK().equals(this.getGatePK())) {
				return true;
			}
		}

		return false;
	}
}
