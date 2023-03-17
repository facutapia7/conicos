package ar.com.osde.cronicos.exceptions.handlers;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ExceptionConfig;

import ar.com.osde.framework.action.ContextHandler;
import ar.com.osde.framework.exception.handlers.BaseExceptionHandler;


/** 
* class description 
*  
* @author	Adrian C. Martinez
*  
* @version	1.0.0
*  
* @date		Nov 4, 2004 
*/
public class ConcurrencyExceptionHandler extends BaseExceptionHandler {

	/**
	 * Handles the exception
	 * 
	 * @param	ex		 the exception itself
	 * @param	exConfig the exception config
	 * @param	mapping  the struts mapping
	 * @param	form	 the struts form
	 * @param	ctx		 the context handler
	 * 
	 * @return	ActionForward an ActionForward
	 */
	public ActionForward handleException(
		Exception ex,
		ExceptionConfig exConfig,
		ActionMapping mapping,
		ActionForm form,
		ContextHandler ctx) {
			
		this.errors.clear();
		addError(ActionErrors.GLOBAL_ERROR,	
			new ActionError("errors.handlers.concurrency", ex.getMessage()));
		
		return mapping.findForward("osde.cronicos.persistance.concurrencyException.redirectURL");
	}
}
