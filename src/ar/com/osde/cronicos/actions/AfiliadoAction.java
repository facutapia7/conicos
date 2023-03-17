 /*
 * Created on Aug 17, 2004
 *
 */
package ar.com.osde.cronicos.actions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;

import ar.com.osde.cronicos.Constantes;
import ar.com.osde.cronicos.beans.Afiliado;
import ar.com.osde.cronicos.beans.AfiliadoCronico;
import ar.com.osde.cronicos.business.AfiliadoBO;
import ar.com.osde.cronicos.exceptions.AfiliadoNoEncontradoException;
import ar.com.osde.cronicos.forms.AfiliadoForm;
import ar.com.osde.framework.action.BaseAction;
import ar.com.osde.framework.action.BaseActionMapping;
import ar.com.osde.framework.action.ContextHandler;
import ar.com.osde.framework.exception.BaseException;

/** 
* Este Action es el primero en invocarse en la secuencia
* Valida el Afiliado ingresado.
*  
* @author	Adrian C. Martinez
*  
* @version	Aug 17, 2004 
*/
public class AfiliadoAction extends BaseAction {
	
	private static final Log logger = LogFactory.getLog(AfiliadoAction.class); 

	/**
 	* Metodo de entrada del BaseAction del Framework.
 	* @param mapping el Base Action Mapping.
 	* @param actionForm el Form de Struts.
 	* @param ctx el Context Handler.
 	* @throws BaseException en caso de un error que se pueda manejar.
 	* @return ActionForward de Struts para seguir el flujo.
 	*/
	public ActionForward performAction(BaseActionMapping mapping,ActionForm actionForm,ContextHandler ctx) throws BaseException {
			
		String retorno = "";
		logger.debug("INGRESANDO A AFILIADO ACTION...");

		try {
			AfiliadoForm afiliadoForm = (AfiliadoForm)actionForm;
			
			int fili = Integer.parseInt(afiliadoForm.getFilial());
			int afil = Integer.parseInt(afiliadoForm.getAfiliado());
			int bene = Integer.parseInt(afiliadoForm.getBeneficiario());						
			
			AfiliadoBO afBO = new AfiliadoBO();
			Afiliado afiliado = afBO.obtenerAfiliado(fili, afil, bene);

			synchronized(ctx.getRequest().getSession()){
				AfiliadoCronico afiliadoCronico = new AfiliadoCronico(afiliado);
				ctx.setSessionAttribute(Constantes.AFILIADO, afiliadoCronico);
			}
			retorno = "success";
			
		} catch (AfiliadoNoEncontradoException e) {
			
			logger.debug("ESTOY EN EL CATCH DE AFILIADO NO ENCONTRADO");
			
			addMessagesAndErrors( ctx, ActionErrors.GLOBAL_ERROR,	
				new ActionError("jsp.nuevoTramite.afiliadoInexistente"));
			retorno = "failure";
			
		}
		
		return mapping.findForward(retorno);
	}
	
}
