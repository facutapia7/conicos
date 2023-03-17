/*
 * Created on Oct 4, 2004
 *
 */
package ar.com.osde.cronicos.persistance;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

/** 
* class description 
*  
* @author	Adrian C. Martinez
*  
* @version	1.0.0
*  
* @date		Oct 4, 2004 
*/
public class ConcurrencyCache {

	/**
	 * Log
	 */
	private static final Log logger = LogFactory.getFactory().getInstance(ConcurrencyCache.class);
	/**
	 * Cache
	 */
	private GeneralCacheAdministrator oscache = null;
	/**
	 * Tiempo de vida del Objeto
	 * 
	 */
	private static final int secondsLife = 900;
	/**
	 * Unica Instancia
	 */
	private static ConcurrencyCache concurrencyCache = null; 

	/**
	 * Contructor
	 */
	private ConcurrencyCache() {
		oscache = new GeneralCacheAdministrator();
	}
	
	/**
	 * Retorna instancia de la clase
	 * @return ConcurrencyCache
	 */
	public static ConcurrencyCache getIntance() {
		if (concurrencyCache == null) {
			concurrencyCache = new ConcurrencyCache();
		}
		return concurrencyCache;
	}


	/**
	 * Retorna String cacheado
	 * @param object 
	 * @return IObjectConcurrency
	 */
	public Object get(String checkID) {
		try {
			return oscache.getFromCache(checkID, secondsLife); //CacheEntry.INDEFINITE_EXPIRY
			
		} catch (NeedsRefreshException n) {
			logger.debug("Concurrency: Not Found " + checkID);
			return null;
		}
	}

	/**
	 * Guarda el objeto en Cahce
	 * @param checkID
	 * @param object
	 */
	public void set(String checkID, Object value) {
		boolean updated = false;

		try {

			oscache.putInCache(checkID, value); //, new EntryRefreshPolicyImp());
			updated = true;

		} finally {

			if (!updated) {
				oscache.cancelUpdate(checkID);
			}

		}
	}
}
