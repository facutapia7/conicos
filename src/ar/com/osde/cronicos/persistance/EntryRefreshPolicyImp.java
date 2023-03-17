/*
 * Created on Oct 4, 2004
 *
 */
package ar.com.osde.cronicos.persistance;

import java.util.Date;

import com.opensymphony.oscache.base.CacheEntry;
import com.opensymphony.oscache.base.EntryRefreshPolicy;

/** 
* class description 
*  
* @author	Adrian C. Martinez
*  
* @version	1.0.0
*  
* @date		Oct 4, 2004 
*/
public class EntryRefreshPolicyImp implements EntryRefreshPolicy {
	
	/* Tiempo en milisegundos para el control de actualizacion de Entradas */
	private static final long REFRESH_TIME = 900000;

	/**
	 * Retorna true si es necesario refrescar.
	 * @param entry Entry Object
	 * @return true o false
	 */
	public boolean needsRefresh(CacheEntry entry) {
		boolean retorno = false;

		Date now = new Date();
		Date lastUpdate = new Date(entry.getLastUpdate());

		/* Diferencia en milisegundos */
		long diff = Math.abs(now.getTime() - lastUpdate.getTime());

		/* Si expiro el tiempo para verificar reglas de refresh */
		if (diff > REFRESH_TIME) {
			retorno = true;
		}

		return retorno;
	}
}
