/*
 * Created on Nov 10, 2004
 *
 */
package ar.com.osde.cronicos.actions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.validator.DynaValidatorForm;

import ar.com.osde.cronicos.Constantes;
import ar.com.osde.cronicos.beans.AfiliadoBusqueda;
import ar.com.osde.cronicos.business.AfiliadoBusquedaBO;
import ar.com.osde.framework.action.BaseActionMapping;
import ar.com.osde.framework.action.ContextHandler;
import ar.com.osde.framework.action.LifeCycleBaseAction;
import ar.com.osde.framework.exception.BaseException;

/** 
* class description 
*  
* @author	Adrian C. Martinez
*  
* @version	1.0.0
*  
* @date		Nov 10, 2004 
*/
public class AfiliadoBusquedaAction extends LifeCycleBaseAction {
	
	/**
	 * Log4j
	 */
	private static final Log logger = LogFactory.getLog(AfiliadoBusquedaAction.class); 	

	/**
	 * Perform Action Pre
	 * @param mapping
	 * @param form
	 * @param context
	 * @exception BaseException
	 */
	public ActionForward beginPerformAction(
		BaseActionMapping mapping,
		ActionForm form,
		ContextHandler context)
		throws BaseException 
	{
		logger.debug("Removiendo Lista de Socios y Form de Busqueda de la Session...");		
		synchronized (context.getRequest().getSession()) {
			context.removeSessionAttribute(Constantes.SOCIOS_BUSQUEDA_LISTA);
			context.removeSessionAttribute("afiliadoBusquedaForm");
		}
		return mapping.findForward("success");		
	}

	/**
	 * Perform Action Post
	 * @param mapping
	 * @param form
	 * @param context
	 * @exception BaseException
	 */
	public ActionForward endPerformAction(
		BaseActionMapping mapping,
		ActionForm form,
		ContextHandler context)
		throws BaseException 
	{
		DynaValidatorForm formBusqueda = (DynaValidatorForm)form;	
		AfiliadoBusqueda socio = new AfiliadoBusqueda();
		
		socio.setNombre((String)formBusqueda.get("nombre"));
		socio.setApellido((String)formBusqueda.get("apellido"));
		
		logger.debug("Cargando lista de Busqueda de Afiliado...");

		AfiliadoBusquedaBO socioBO = new AfiliadoBusquedaBO();

		synchronized (context.getRequest().getSession()) {
			context.removeSessionAttribute(Constantes.SOCIOS_BUSQUEDA_LISTA);
			context.setSessionAttribute(Constantes.SOCIOS_BUSQUEDA_LISTA, 
				socioBO.recuperarPorNombreODocumento(socio));
		}

		logger.debug("... Lista de Busqueda de Afiliado cargada");
		
		return mapping.findForward("success");
		
	}
}
