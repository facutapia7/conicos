/*
 * Created on 17/08/2004
 */
package ar.com.osde.cronicos.business;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import ar.com.osde.cronicos.Constantes;
import ar.com.osde.cronicos.beans.AfiliadoCronico;
import ar.com.osde.cronicos.beans.DatoEpidemiologico;
import ar.com.osde.cronicos.beans.Diagnostico;
import ar.com.osde.cronicos.beans.GatewayCRM;
import ar.com.osde.cronicos.beans.GatewayDiagnostico;
import ar.com.osde.cronicos.beans.MedicoTratante;
import ar.com.osde.cronicos.beans.PatologiaBase;
import ar.com.osde.cronicos.dao.AfiliadoCronicoDAO;
import ar.com.osde.framework.business.BusinessObject;
import ar.com.osde.framework.config.Configuration;
import ar.com.osde.framework.dao.DAOFactory;
import ar.com.osde.framework.dao.DataAccessException;
import ar.com.osde.framework.exception.BaseException;
import ar.com.osde.framework.exception.UnexpectedException;

/**
 * Clase responsable de los procesos de negocio que 
 * tienen que ver con el afiliado cronico y esta aplicación.
 * @author Diego Naya
 * Created on 17/08/2004
 * Update Adrian C. Martinez 01/11/2004
 */
public class AfiliadoCronicoBO extends BusinessObject {
	/**
	 * Codigo PMI
	 */
	private static final String CODIGO_PMI = 
	    Configuration.getInstance().getParameter(Constantes.ID_PMI);
	/**
	 * Codigo PMI Hijo
	 */
	private static final String CODIGO_PMI_HIJO = 
	    Configuration.getInstance().getParameter(Constantes.ID_PMI_HIJO);		
	/**
	 * Dao
	 */
	private AfiliadoCronicoDAO afcDAO = null;
	
	/**
	* Crea dao de AfiliadoCronico 
	*/
	public AfiliadoCronicoBO() {
		super();
		afcDAO = (AfiliadoCronicoDAO)DAOFactory.getInstance().getDAO(AfiliadoCronicoDAO.class);
	}
	
	/**
	 * Obtiene un Objeto AfiliadoCronico
	 * @param afiliado Afiliado
	 * @return AfiliadoCronico
	 */
	public AfiliadoCronico obtenerAfiliadoCronico(AfiliadoCronico afiliadoCronico) 
		throws BaseException {
		
		/* Afiliado excepcion por nulo */
		if (afiliadoCronico != null && afiliadoCronico.getAfiliado() != null) {
		    
			GatewayCRMBO crmBO = new GatewayCRMBO();
			long fechaCarga = crmBO.existeTramiteIngresado(afiliadoCronico.getAfiliado().getNroBP());
		    
			if (fechaCarga >= 0 ) {
				logger.info("ES CRONICO.");
				afiliadoCronico.getAfiliado().setActualizacion(true);
				afiliadoCronico.setFechaCarga(fechaCarga);
				afiliadoCronico = this.recuperarDatosAfiliadoCronico(afiliadoCronico);

			} else {
				logger.info("NO ES CRONICO.");
				afiliadoCronico.getAfiliado().setActualizacion(false);							
				afiliadoCronico = new AfiliadoCronico(afiliadoCronico.getAfiliado());			
			}
			
			/* PMI */
			this.loadPMI(afiliadoCronico);
			
			return afiliadoCronico;
			
		} else {
			throw new UnexpectedException("El Afiliado recuperado, es NULO.");
		}
	}
 	
 	/**
  	* Recupera los datos existentes del Afiliado Cronico.
  	* @param afiliado Afiliado
  	* @return el Afiliado Cronico que corresponda a ese numero, filial y beneficiario.
  	* @throws UnexpectedException en caso de algun error irrecuperable.
  	*/
 	private AfiliadoCronico recuperarDatosAfiliadoCronico(AfiliadoCronico afiliadoCronico) 
 		throws BaseException {
		AfiliadoCronico localAfiliadoCronico = null;
		try {
			localAfiliadoCronico = afcDAO.recuperarDatosAfiliadoCronico(afiliadoCronico);

		} catch (DataAccessException e) {
			logger.error(e);
			e.printStackTrace();
			throw new UnexpectedException(e);
		}
		
		localAfiliadoCronico.setAfiliado(afiliadoCronico.getAfiliado());
		MedicoTratanteBO medicoBO = new MedicoTratanteBO();
		localAfiliadoCronico.setMedicoTratante(
			medicoBO.recuperarMedicoTratante(localAfiliadoCronico.getMedicoTratante().getCodigo()));
					
		/* Recupera Medicos de las Patologias */
		setMedicosDePatologias(localAfiliadoCronico);
		
		return localAfiliadoCronico;
 	}

 	/**
 	 * Retorna PMI
 	 * @param cronico
 	 * @param codigo
 	 * @return
 	 */
 	private Diagnostico getPMI(AfiliadoCronico cronico, String codigo) {
 	    try {
	 	    Diagnostico pmi = null;
	 	    GatewayDiagnostico gate = 
	 	        afcDAO.recuperarPatologiaPorBPyCodigo(
	 	                cronico.getAfiliado().getNroBP(), codigo);
	 	    
	 	    if (gate != null) {
	 	        pmi = new Diagnostico();
	 	        pmi.setCodigo(gate.getGatePK().getCodigo());
	 	        pmi.setDescripcion("PMI");
	 	        pmi.setFechaDesde(gate.getGatePK().getVigDesde());
	 	        pmi.setFechaHasta(gate.getVigHasta());
	 	    }
	 	    return pmi;
	 	    
 	    } catch (DataAccessException e) {
			logger.error(e);
			e.printStackTrace();
			throw new UnexpectedException(e);
 	    }
 	}
 	
 	/**
 	 * Setea Los Medicos Tratantes de las Patologias
 	 * @param afiliadoCronico
 	 */
 	private void setMedicosDePatologias(AfiliadoCronico afiliadoCronico) 
 	    throws BaseException {
 		if (afiliadoCronico.getMedicoTratante() == null) {
 			throw new BaseException("Error al intentar recuperar los datos del Medico Tratante...");
 		}
 		
 		Map medicosPersistidos = new HashMap();
 		medicosPersistidos.put( new Long(
 			afiliadoCronico.getMedicoTratante().getCodigo()), 
 			afiliadoCronico.getMedicoTratante());

		setMedicosDePatologiasItem(afiliadoCronico.getDiagnosticos(), medicosPersistidos); 		
		setMedicosDePatologiasItem(afiliadoCronico.getDatosEpidemiologicos(), medicosPersistidos);
 	}
 	
 	/**
 	 * Agrega Medicos a los Items
 	 * @param lista
 	 * @param map
 	 */
 	private void setMedicosDePatologiasItem(List lista, Map map) {
		MedicoTratanteBO medicoBO = new MedicoTratanteBO();
		
 		for(Iterator it = lista.iterator(); it.hasNext();) {
 			PatologiaBase pat = (PatologiaBase)it.next();
 			Long codigo = new Long(pat.getMedicoTratante().getCodigo());
 			
 			if (map.containsKey(codigo)) {
 				pat.setMedicoTratante((MedicoTratante)map.get(codigo));
 				
 			} else {
 				MedicoTratante medico = medicoBO.recuperarMedicoTratante(
 					pat.getMedicoTratante().getCodigo());
				map.put( new Long(medico.getCodigo()), medico);
				
 				pat.setMedicoTratante(medico);
 			}
 		}
 	}
 	
 	/**
	* Baja del Afiliado Cronico
	* @param afiliadoCronico
	* @exception DataAccessException
	*/
	public void darDeBajaAfiliadoCronico(AfiliadoCronico afiliadoCronico, AfiliadoCronico cronico) {
		logger.warn("DANDO DE BAJA");
	 	
		try {
			/* Agrega registro en CRM */			
			GatewayCRM crm = new GatewayCRM();
			crm.setNrobp(afiliadoCronico.getAfiliado().getNroBP());
			crm.setMarca("B");
			
			GatewayCRMBO crmBO = new GatewayCRMBO();
			crmBO.insertar(crm);
			
			/* Actualiza fechas de Patologias y Datos Ep. */
			afcDAO.darDeBajaAfiliadoCronico(afiliadoCronico);
			
			/* PMI */
			this.savePMI(cronico);
			
		} catch (DataAccessException e) {
			logger.error(e);
			e.printStackTrace();
			throw new UnexpectedException(e);
		}
	}
	
	/**
	 * Recupera datos de PMI
	 * @param afiliadoCronico
	 */
	private void loadPMI(AfiliadoCronico afiliadoCronico) {
		/* PMI */
		if (afiliadoCronico.isSon()) {
		    afiliadoCronico.setPmiHijo(this.getPMI(afiliadoCronico, CODIGO_PMI_HIJO));
		} else {
		    if (!"M".equals(afiliadoCronico.getAfiliado().getSexo())) {
		        afiliadoCronico.setPmi(this.getPMI(afiliadoCronico, CODIGO_PMI));
		    }
		}
	}
	
	/**
	 * Inserta o Actualiza o boora PMI
	 * @param cronico
	 * @throws DataAccessException
	 */
	private void savePMI(AfiliadoCronico cronico) throws DataAccessException {
	    if (!"M".equals(cronico.getAfiliado().getSexo())
	            && !cronico.isSon()) {
	        afcDAO.actualizarPMI(cronico.getPmi(), cronico);
	    }
	}

	/**
	* Ingresa un afilido cronico y lo marca como tal.
	* @param afc un Afiliado Cronico.
	*/
	public void ingresarAfiliadoCronico(AfiliadoCronico afiliadoCronico){
		logger.debug("INGRESANDO AFILIADO CRONICO");
			
		/* Primero ingreso el Medico y el Tramite que son objetos independientes */
		MedicoTratanteBO medicoBO = new MedicoTratanteBO();
		TramiteBO tramiteBO = new TramiteBO();
		GatewayCRMBO crmBO = new GatewayCRMBO();
		
		GatewayCRM crm = new GatewayCRM();
		crm.setNrobp(afiliadoCronico.getAfiliado().getNroBP());
		crm.setMarca("A");
		
		medicoBO.ingresarMedicoTratante(afiliadoCronico.getMedicoTratante());
		tramiteBO.ingresarTramite(afiliadoCronico.getTramite());
		
		//version-1.2.7 - INICIO		
		//logger.info("ALTA DE AFILIADO CRONICO [ BP="+crm.getNrobp()+", MedTratCod="+afiliadoCronico.getMedicoTratante().getCodigo());	
		
		logger.info("Carga Inicial - INICIAL");
		logger.info("Afiliado: " + afiliadoCronico.getAfiliado());
		logger.info("Datos Epidemiologicos: " + afiliadoCronico.getDatosEpidemiologicos());
		logger.info("Diagnosticos: " + afiliadoCronico.getDiagnosticos());
		logger.info("Fecha Carga: " + afiliadoCronico.getFechaCarga());
		logger.info("Fecha Carga DDMMYYYY: " + afiliadoCronico.getFechaCargaDDMMYYYY());
		logger.info("Medico Tratante: " + afiliadoCronico.getMedicoTratante());
		logger.info("PMI: " + afiliadoCronico.getPmi());
		logger.info("PMI Hijo: " + afiliadoCronico.getPmiHijo());
		logger.info("Tramite: " + afiliadoCronico.getTramite());
		logger.info("Carga Inicial - FIN");
		//version-1.2.7 - FIN
		
		crmBO.insertar(crm);
		
		/* Luego ingreso los datos de las tablas asociativas */
		try{
			afcDAO.ingresarAfiliadoCronico(afiliadoCronico);

			/* PMI */
			this.savePMI(afiliadoCronico);
			
		} catch(DataAccessException dae) {
			logger.error(dae);
			throw new UnexpectedException(dae);  
		}
	}
	
 	/**
  	* Actualiza los datos en el caso de que 
  	* se actualice el formulario de ingreso de datos.
  	* @param afc un Afiliado Cronico
  	*/
 	public void actualizarDatosAfiliadoCronico(
 		AfiliadoCronico newAfiliado, AfiliadoCronico oldAfiliado)
 	{
		try {
			MedicoTratanteBO medicoBO = new MedicoTratanteBO();
			GatewayCRMBO crmBO = new GatewayCRMBO();
			
			/* Elimina patologias y retorna si hay algo para agregar */
			afcDAO.actualizarPatologiasEliminadasAfiliadoCronico(newAfiliado);
			
			/* 
			 * Ingresando nuevas Patologias 
			 * Elimina de la Lista Nueva, las que existen en la Lista Anterior,
			 * luego inserta la lista restante, para agregar las nuevas.
			 */
			Predicate predicadoDiag = new QuitarPatologia(oldAfiliado.getDiagnosticos());
			CollectionUtils.filter(newAfiliado.getDiagnosticos(), predicadoDiag);
		
			Predicate predicadoEpid = new QuitarPatologia(oldAfiliado.getDatosEpidemiologicos());
			CollectionUtils.filter(newAfiliado.getDatosEpidemiologicos(), predicadoEpid);

			/* Si hay nuevas patologias las grabo */		
			if (newAfiliado.getDatosEpidemiologicos().size() != 0 
				|| newAfiliado.getDiagnosticos().size() != 0) {

				/* Inserta tramite nuevo*/
				TramiteBO tramiteBO = new TramiteBO();
				tramiteBO.ingresarTramite(newAfiliado.getTramite());
		
				/* Actualizao Medico Tratante o lo inserto si es nuevo */
				if (newAfiliado.getMedicoTratante().getNumeroMatricula() !=	
					oldAfiliado.getMedicoTratante().getNumeroMatricula() 
					|| newAfiliado.getMedicoTratante().getNumeroPrestador() !=
					   oldAfiliado.getMedicoTratante().getNumeroPrestador()) {
			   	
					medicoBO.ingresarMedicoTratante(newAfiliado.getMedicoTratante());
			
				} else {
			
					newAfiliado.getMedicoTratante().setCodigo(oldAfiliado.getMedicoTratante().getCodigo());
					medicoBO.actulizarMedicoTratante(newAfiliado.getMedicoTratante());
				}
				
				/* Inserta Patologias */
				afcDAO.ingresarAfiliadoCronico(newAfiliado);
				
			/* Solo Actualizao datos del medico Tratante */				
			} else {
				newAfiliado.getMedicoTratante().setCodigo(oldAfiliado.getMedicoTratante().getCodigo());				
				medicoBO.actulizarMedicoTratante(newAfiliado.getMedicoTratante());				
			}
			
			/* PMI */
			this.savePMI(newAfiliado);

			/* 
			 * Para Disparar Trigger en Updeta
			 * a pedido de Juan C Fernandez 07/2005
			 */
			crmBO.updateCRM(newAfiliado.getAfiliado().getNroBP());
			
		} catch (DataAccessException e) {
			logger.error(e);
			e.printStackTrace();
			throw new UnexpectedException(e);
		}
	}

	/**
	*
	* class Predicate para eliminar Existentes de Datos Epidemiologicos   
	*  
	* @author	Adrian C. Martinez
	*  
	* @version	1.0.0
	*  
	* @date		Nov 3, 2004
	 */
	class QuitarPatologia implements Predicate {
		private List lista;
		
		public QuitarPatologia(List list) {
			lista = list;
		}
			
		public boolean evaluate(Object input) {
			if (input instanceof DatoEpidemiologico 
				|| input instanceof Diagnostico) {
				PatologiaBase patNueva = (PatologiaBase)input;
				
				for (Iterator it = lista.iterator(); it.hasNext(); ) {
					PatologiaBase patAnterior = (PatologiaBase)it.next();
					
					if (patNueva.getCodigo().equals(patAnterior.getCodigo())) {
				        return false;
					}
				}
			}
			return true;
		}
	}	
}
