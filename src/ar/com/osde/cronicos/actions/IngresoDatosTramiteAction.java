/*
 * Created on Aug 17, 2004
 *
 */
package ar.com.osde.cronicos.actions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;

import ar.com.osde.cronicos.Constantes;
import ar.com.osde.cronicos.beans.AfiliadoCronico;
import ar.com.osde.cronicos.beans.DatoEpidemiologico;
import ar.com.osde.cronicos.beans.Diagnostico;
import ar.com.osde.cronicos.beans.MedicoTratante;
import ar.com.osde.cronicos.beans.Tramite;
import ar.com.osde.cronicos.business.AfiliadoCronicoBO;
import ar.com.osde.cronicos.forms.IngresoDatosTramiteForm;
import ar.com.osde.cronicos.helper.GeneralHelper;
import ar.com.osde.cronicos.persistance.ConcurrencyProxy;
import ar.com.osde.framework.action.BaseActionMapping;
import ar.com.osde.framework.action.ContextHandler;
import ar.com.osde.framework.action.LifeCycleBaseAction;
import ar.com.osde.framework.config.Configuration;
import ar.com.osde.framework.exception.BaseException;
import ar.com.osde.framework.exception.UnexpectedException;
import ar.com.osde.framework.security.intranet.IntranetUser;

/** 
* Action de Struts que carga datos necesarios para el formulario,
*  en memoria.
*  
* @author	Adrian C. Martinez 
*  
* @version	Aug 17, 2004 
*  
* @see	
*/
public class IngresoDatosTramiteAction extends LifeCycleBaseAction {

	private static final Log logger = LogFactory.getLog(IngresoDatosTramiteAction.class);
	private static final SimpleDateFormat spf = new SimpleDateFormat("yyyyMMdd");
	private static final String CODIGO_PMI = Configuration.getInstance().getParameter(Constantes.ID_PMI);

	/**
	 * Carga Previa de Datos antes del Ingreso
	 * @return ActionForward
	 */
	public ActionForward beginPerformAction(
		BaseActionMapping mapping,
		ActionForm form,
		ContextHandler context)
		throws BaseException {
		
		ActionForward forward = mapping.findForward("success");	
		ConcurrencyProxy pxy = new ConcurrencyProxy();			
		AfiliadoCronicoBO afcBO = new AfiliadoCronicoBO();
		AfiliadoCronico afiliadoCronico = afcBO.obtenerAfiliadoCronico(
			(AfiliadoCronico)context.getSessionAttribute(Constantes.AFILIADO));
		
		//version-1.2.7 - INICIO
		logger.info(" Carga Previa de Datos antes del Ingreso - INICIAL");
		logger.info("-----------------------------------------------------------");
		logger.info(context.getSessionAttribute(Constantes.AFILIADO).toString());
		logger.info("-----------------------------------------------------------");
		logger.info(afiliadoCronico.toString());
		logger.info(" Carga Previa de Datos antes del Ingreso - FIN");
		//version-1.2.7 - FIN
		
		/* Afiliado de Baja y sin PMI */
		if (afiliadoCronico.isAfiliadoBaja() && (!afiliadoCronico.isCronicoPMI())) {
			addMessagesAndErrors( context, ActionErrors.GLOBAL_ERROR,
					new ActionError("jsp.nuevoTramite.afiliadoDeBaja"));
			forward = new ActionForward (mapping.getInput());
			
		} else {
		
			/* Si el usuario tiene permiso de consulta y 
			 * no es un cronico retorna al inicio con mensaje */
			if ((! afiliadoCronico.getAfiliado().isActualizacion())
				&& (! security.hasPermission(security.getUser(context), 
					"OSGAFICRON-PUSUREC-ADM"))
					&& (afiliadoCronico.getPmi() == null)
					&& (afiliadoCronico.getPmiHijo() == null)) {
				
				addMessagesAndErrors( context, ActionErrors.GLOBAL_ERROR,
						new ActionError("jsp.nuevoTramite.afiliadoNoCronico"));
				forward = new ActionForward (mapping.getInput());
				
			/* Si el usuario tiene permiso de 
			 * Administrador continua */			
			} else {
				
				synchronized(context.getRequest().getSession()){
					pxy.controlBegin(afiliadoCronico.getAfiliado());
					context.setSessionAttribute(Constantes.AFILIADO, afiliadoCronico);
				}
				setBeanToForm((IngresoDatosTramiteForm)form, afiliadoCronico);
			}
		}
		return forward;
	}

	/**
	 * Action posterior a la carga de Datos
	 * @return ActionForward
	 */
	public ActionForward endPerformAction(
		BaseActionMapping mapping,
		ActionForm formAction,
		ContextHandler context)
		throws BaseException {
			
		IngresoDatosTramiteForm form = (IngresoDatosTramiteForm) formAction;
		if (form == null) {
			throw new BaseException("Error al intentar capturar los datos del formulario.");
		}

		ConcurrencyProxy pxy = new ConcurrencyProxy();
		AfiliadoCronicoBO afCronicoBO = new AfiliadoCronicoBO();
		AfiliadoCronico afiliadoCronico = new AfiliadoCronico();
		AfiliadoCronico afiliadoCronicoOld = (AfiliadoCronico)
			context.getRequest().getSession().getAttribute(Constantes.AFILIADO);
			
		pxy.controlEnd(afiliadoCronicoOld.getAfiliado());			
		setFormToBean(afiliadoCronico, form, context, afiliadoCronicoOld);
		
		//version-1.2.7 - INICIO
		logger.info("Action posterior a la carga de Datos - INICIAL");
		logger.info("-----------------------------------------------------------");
		logger.info(context.getSessionAttribute(Constantes.AFILIADO).toString());
		logger.info("-----------------------------------------------------------");
		logger.info(afiliadoCronico.toString());
		logger.info("Action posterior a la carga de Datos - FIN");
		//version-1.2.7 - FIN
		
		/* Si no tiene diagnosticos dar de baja como cronico */		
		if (form.getDiagnosticos().length == 0) {
			logger.debug("DANDO DE BAJA AFILIADO CRONICO....");
			afCronicoBO.darDeBajaAfiliadoCronico(afiliadoCronicoOld, afiliadoCronico);
				
		} else {
			
			if (afiliadoCronicoOld.isActualizacion()) {
				logger.debug("ACTUALIZANDO DATOS....");
				afCronicoBO.actualizarDatosAfiliadoCronico(afiliadoCronico, afiliadoCronicoOld);
					
			} else {
				logger.debug("INGRESANDO DATOS....");
				afCronicoBO.ingresarAfiliadoCronico(afiliadoCronico);
			}
		}
		
		return mapping.findForward("success");
	}
	
	/**
	 * Set Datos del Form de Ingreso si es Actualizacion
	 * @param form Form
	 * @param afiliadoCronico Afiliado Cronico
	 */
	private void setBeanToForm (
	        IngresoDatosTramiteForm form, 
	        AfiliadoCronico afiliadoCronico)
			
		throws BaseException {
	    
	    /* PMI */
	    this.setPMI(afiliadoCronico, form);

		/* Setea el flag que indica si el form es ReadOnly */
		form.setReadOnly(afiliadoCronico.isAfiliadoReadOnly());

		//version-1.2.7 - INICIO
		logger.info("Set Datos del Form de Ingreso si es Actualizacion - INICIAL");
		logger.info("-----------------------------------------------------------");
		logger.info(afiliadoCronico.toString());
		logger.info("Action posterior a la carga de Datos - FIN");
		//version-1.2.7 - FIN
		
		/* Si es Actualizacion, cargo datos al Form */
		if (afiliadoCronico.getAfiliado().isActualizacion()) {
			
			if (afiliadoCronico.getMedicoTratante()==null) {
				throw new UnexpectedException(
					"No se pudo obtener el medico tratante, verifique vigencias de Patologias o Datos Ep."); 
			}
			
			String[] deSelected = new String[afiliadoCronico.getDatosEpidemiologicos().size()];
			String[] diSelected = new String[afiliadoCronico.getDiagnosticos().size()];
					
			Iterator it = afiliadoCronico.getDatosEpidemiologicos().iterator();
			for (int i = 0; i < afiliadoCronico.getDatosEpidemiologicos().size(); i++) {
				deSelected[i] = ((DatoEpidemiologico) it.next()).getCodigo();
			}
	
			it = afiliadoCronico.getDiagnosticos().iterator();
			for (int i = 0; i < afiliadoCronico.getDiagnosticos().size(); i++) {
				diSelected[i] = ((Diagnostico) it.next()).getCodigo();
			}
	
			form.setDatosEpidemiologicos(deSelected);
			form.setDiagnosticos(diSelected);
			form.setNumeroDeMatricula(
			        this.valueOf(
			                afiliadoCronico.getMedicoTratante().getNumeroMatricula()));
			form.setCodigoPrestador(
			        this.valueOf(
			                afiliadoCronico.getMedicoTratante().getNumeroPrestador()));
			
			form.setApellidoNombre(afiliadoCronico.getMedicoTratante().getNombre());
			form.setTelefono(afiliadoCronico.getMedicoTratante().getTelefono());
			form.setTipoDeMatricula(afiliadoCronico.getMedicoTratante().getTipoMatricula());
						
			/* El codigo de provincia es Char(2) pero en MED???? es Char(1) */
			form.setProvincia(
				(afiliadoCronico.getMedicoTratante().getProvincia() + " ").substring(0, 2));
			
			if (form.getApellidoNombre().trim().equals("") 
			        && !form.getCodigoPrestador().equals("")) {
				form.setPrestadorOsde("on");
			}

		}
	}
	
	/**
	 * Setea PMI
	 * @param afiliadoCronico
	 * @param form
	 */
	private void setPMI(
	        AfiliadoCronico afiliadoCronico,
	        IngresoDatosTramiteForm form) {
	    
	    if (afiliadoCronico.isSon()) {
	        form.setEsHijo("true");
	        if (afiliadoCronico.getPmiHijo() != null) {
	            form.setFechaProbableDeParto(afiliadoCronico.getPmiHijo().getFechaHastaDDMMYYYY());
	            form.setFechaVigenciaDesde(afiliadoCronico.getPmiHijo().getFechaDesdeDDMMYYYY());
	        }
	    } else {
	        form.setEsHijo("false");	        
	    }
        if (afiliadoCronico.getPmi() != null) {
            
            form.setFechaProbableDeParto(afiliadoCronico.getPmi().subsFechaHasta());
            form.setPmi("on");
        }
	}
	
	/**
	 * Filtra 0 por blanco
	 * @param value
	 * @return
	 */
	private String valueOf(long value) {
	    String aux = String.valueOf(value);
	    if ("0".equals(aux)) {
	        aux = "";
	    }
	    return aux;
	}
	
	/**
	 * Llena un Afiliado Cronico con los datos del Form.
	 * @param afiliadoCronico un Afiliado Cronico
	 * @param form un IngresoDatosTramiteForm
	 * @param context el context handler
	 */
	private void setFormToBean(
		AfiliadoCronico afiliadoCronico,
		IngresoDatosTramiteForm form,
		ContextHandler context, 
		AfiliadoCronico afiliadoCronicoOld) 
	{
		if (afiliadoCronicoOld != null && afiliadoCronicoOld.getAfiliado() != null) {
			
			IntranetUser user = (IntranetUser)security.getUser(context);		
			long nroMat = GeneralHelper.toLong(form.getNumeroDeMatricula());
				
			MedicoTratante medico = new MedicoTratante();
			medico.setNombre(form.getApellidoNombre().trim());
			medico.setNumeroMatricula(nroMat);
			medico.setNumeroPrestador(GeneralHelper.toInt(form.getCodigoPrestador()));
			medico.setProvincia(form.getProvincia().trim());
			medico.setTelefono(form.getTelefono().trim());
			medico.setTipoMatricula(form.getTipoDeMatricula().trim());

			Tramite tramite = new Tramite();
			tramite.setCapReceptor(Integer.parseInt(user.getFilial().getCapID()));
			tramite.setFilial(Integer.parseInt(user.getFilial().getId()));
			tramite.setUsuario(user.getPersonalData().getUserName());
			tramite.setFechaCarga(
				GeneralHelper.toLong(
					spf.format(Calendar.getInstance().getTime()).toString()));

			List listaDiagnostico = getListaDiagnostico(form.getDiagnosticos());
			List listaEpidemiolog =	getListaEpidemiologico(form.getDatosEpidemiologicos());

			afiliadoCronico.setAfiliado(afiliadoCronicoOld.getAfiliado());
			afiliadoCronico.setTramite(tramite);
			afiliadoCronico.setMedicoTratante(medico);
			afiliadoCronico.setDiagnosticos(listaDiagnostico);
			afiliadoCronico.setDatosEpidemiologicos(listaEpidemiolog);

			/* PMI */
			if (form.getFechaProbableDeParto() != null 
			        && !"".equals(form.getFechaProbableDeParto())) {
			    Diagnostico diag = new Diagnostico(CODIGO_PMI);
			    
			    try {
			        diag.addFechaHasta(form.getFechaProbableDeParto());
			    } catch (ParseException p) {
			        throw new UnexpectedException("Error al intentar dar formato a la fecha de PMI.");
			    }
			    
			    afiliadoCronico.setPmi(diag);
			}
			
		} else {
			throw new UnexpectedException("Error al intentar obtener datos de la Session.");
		}
	}
	
	/**
	 * Crea una lista de diagnosticos desde el input del usuario.
	 * @param selected los diagnosticos seleccionados por el usuario.
	 * @return una lista con los diagnosticos del afiliado.
	 */
	private List getListaDiagnostico(String[] selected) {
		List retorno = new ArrayList();

		for (int i = 0; i < selected.length; i++) {
			Diagnostico diag = new Diagnostico();
			diag.setCodigo(selected[i]);
			retorno.add(diag);
		}
		return retorno;
	}
	
	/**
	 * Crea un lista de Datos Epidemiologicos desde el input del usuario.
	 * @param selected Los datos epidemiologicos seleccionados.
	 * @return una lista con los datos epidemiologicos.
	 */
	private List getListaEpidemiologico(String[] selected) {
		List retorno = new ArrayList();

		for (int i = 0; i < selected.length; i++) {
			DatoEpidemiologico epid = new DatoEpidemiologico();
			epid.setCodigo(selected[i]);
			retorno.add(epid);
		}
		return retorno;
	}
}