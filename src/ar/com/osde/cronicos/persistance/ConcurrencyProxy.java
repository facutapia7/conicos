/*
 * Created on Nov 4, 2004
 *
 */
package ar.com.osde.cronicos.persistance;

import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.osde.framework.persistence.ConcurrencyException;

/** 
* Proxy para control de Concurrencia 
*  
* @author	Adrian C. Martinez
*  
* @version	1.0.0
*  
* @date		Nov 4, 2004 
*/
public class ConcurrencyProxy {
	
	private ResourceBundle bundle = ResourceBundle.getBundle("ar.com.osde.cronicos.conf.properties.ErrorsResource");
	private Log logger = LogFactory.getFactory().getInstance(ConcurrencyProxy.class);
	
	/**
	 * Inicio de Control
	 * @param object
	 */
	public void controlBegin(IObjectConcurrency object) {
		ConcurrencyCache cache = ConcurrencyCache.getIntance();
		
		Long checkValue = (Long)cache.get(object.getCheckID()); 
		if (checkValue == null) {
			object.setCheckValue(new Long(UniqueID.get()));
			logger.debug("Concurrency: Create " + object.getCheckID());
						
		} else {
			object.setCheckValue(checkValue);
			logger.debug("Concurrency: Update " + object.getCheckID());
		}
		
		cache.set(object.getCheckID(), object.getCheckValue());
	}
	
	/**
	 * Fin del control
	 * @param object
	 * @throws ConcurrencyException
	 */
	public void controlEnd(IObjectConcurrency object)
		throws ConcurrencyException  
	{
		ConcurrencyCache cache = ConcurrencyCache.getIntance();
		
		Long checkValue = (Long)cache.get(object.getCheckID());
		if (checkValue == null) {
			throw new ConcurrencyException(bundle.getString("errors.handlers.concurrency.timeout"));
		} else {
			
			if (checkValue.longValue() != object.getCheckValue().longValue()) {
				throw new ConcurrencyException(bundle.getString("errors.handlers.concurrency.check"));
			}
		}
		
		object.setCheckValue(new Long(UniqueID.get()));
		cache.set(object.getCheckID(), object.getCheckValue());
		logger.debug("Concurrency: Change " + object.getCheckID());
	}
}
