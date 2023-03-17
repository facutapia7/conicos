/*
 * Created on 27/08/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package ar.com.osde.cronicos.actions;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;

import ar.com.osde.cronicos.Constantes;
import ar.com.osde.cronicos.business.DatoEpidemiologicoBO;
import ar.com.osde.cronicos.business.DiagnosticoBO;
import ar.com.osde.cronicos.business.ProvinciaBO;
import ar.com.osde.framework.action.BaseAction;
import ar.com.osde.framework.action.BaseActionMapping;
import ar.com.osde.framework.action.ContextHandler;
import ar.com.osde.framework.config.Configuration;
import ar.com.osde.framework.exception.BaseException;

/**
 * Action que verifica la seguridad de la aplicación. Si el usuario tiene
 * permisos va hacia un forward "success" sino va hacia Handler de Login.
 * @author Diego Naya
 * @version 1.0
 * Update: Adrian C. Martinez 01/11/2004
 */
public class IngresoAction extends BaseAction {
    /**
     * Codigo de PMI
     */
    private static final String CODIGO_PMI = 
        Configuration.getInstance().getParameter(Constantes.ID_PMI);
	/**
	 * Log4j
	 */
	private static final Log logger = LogFactory.getLog(IngresoAction.class); 	
	
	/**
	 * Metodo de entrada del BaseAction del Framework.
	 * @param mapping el Base Action Mapping.
	 * @param form el Form de Struts.
	 * @param context el Context Handler.
	 * @throws BaseException en caso de un error que se pueda manejar.
	 * @return ActionForward de Struts para seguir el flujo.
	 */
	public ActionForward performAction(
		BaseActionMapping mapping,
		ActionForm form,
		ContextHandler context)
		throws BaseException {
			
		synchronized (context.getRequest().getSession()) {
			
			logger.debug("Obteniendo Diagnosticos...");
			if (context.getSessionAttribute("diagnosticosValues") == null) {
				DiagnosticoBO diagnosticoBO = new DiagnosticoBO();
				List diagnosticos = diagnosticoBO.obtenerDiagnosticos();
				context.setSessionAttribute("diagnosticosValues", (Serializable) diagnosticos);
				
				if (context.getSessionAttribute("pmiBeans") == null) {
					context.setSessionAttribute("pmiBeans", (Serializable)
					        	diagnosticoBO.obtenerDiagnostico(CODIGO_PMI, diagnosticos));
				}
			}

			logger.debug("Obteniendo Datos Epidemiologicos...");
			if (context.getSessionAttribute("datosEpidemiologicosValues") == null) {
				DatoEpidemiologicoBO datoEpidemiologicoBO =	new DatoEpidemiologicoBO();
				List datosEpidemiologicos =	datoEpidemiologicoBO.obtenerDatosEpidemiologicos();
				context.setSessionAttribute("datosEpidemiologicosValues", (Serializable) datosEpidemiologicos);
			}

			logger.debug("Obteniendo Provincias...");
			if (context.getSessionAttribute("proviciasValues") == null) {
				ProvinciaBO provinciaBO = new ProvinciaBO();
				List provicias = provinciaBO.obtenerProvincias();
				context.setSessionAttribute("proviciasValues", (Serializable) provicias);
			}
		}

		logger.debug("mapping success...");
		return	mapping.findForward("success");
		
	}
}
