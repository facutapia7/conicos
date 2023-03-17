package ar.com.osde.cronicos.beans;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ar.com.osde.cronicos.helper.GeneralHelper;


/**
 * @author Adrian C. Martinez
 * @version 1.0
 * Created on 01/11/2004
 */
public class GatewayDiagnostico extends GatewayPatologiaBase {
	/**
	 * SDF
	 */
	private static final SimpleDateFormat sdf = 
	    new SimpleDateFormat("yyyyMMdd");
	
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
	
	/**
	 * Setea valores de anulacion
	 */
	public void anular() {
	    Calendar cal = Calendar.getInstance();
	    
	    cal.setTime(new Date());
	    long now = GeneralHelper.toLong(sdf.format(cal.getTime()));
	    
	    cal.add(Calendar.DATE, -1);
	    long ayer = GeneralHelper.toLong(sdf.format(cal.getTime()));
	
	    if (ayer < this.getGatePK().getVigDesde()) {
	        this.setVigHasta(now);
	    } else {
	        this.setVigHasta(ayer);	        
	    }
	}
}
