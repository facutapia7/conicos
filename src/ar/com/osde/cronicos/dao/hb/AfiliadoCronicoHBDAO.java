package ar.com.osde.cronicos.dao.hb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.osde.cronicos.Constantes;
import ar.com.osde.cronicos.beans.Afiliado;
import ar.com.osde.cronicos.beans.AfiliadoCronico;
import ar.com.osde.cronicos.beans.DatoEpidemiologico;
import ar.com.osde.cronicos.beans.Diagnostico;
import ar.com.osde.cronicos.beans.GatewayDatoEpidemiologico;
import ar.com.osde.cronicos.beans.GatewayDiaEpiPK;
import ar.com.osde.cronicos.beans.GatewayDiagnostico;
import ar.com.osde.cronicos.beans.GatewayPatologiaBase;
import ar.com.osde.cronicos.beans.MedicoTratante;
import ar.com.osde.cronicos.beans.PatologiaBase;
import ar.com.osde.cronicos.dao.AfiliadoCronicoDAO;
import ar.com.osde.cronicos.helper.GeneralHelper;
import ar.com.osde.framework.config.Configuration;
import ar.com.osde.framework.config.DaoConfigEntry;
import ar.com.osde.framework.dao.DAOFactory;
import ar.com.osde.framework.dao.DataAccessException;
import ar.com.osde.framework.dao.GenericDAO;
import ar.com.osde.framework.exception.UnexpectedException;

/**
 * Clase responsable de persistir los datos del afiliado cronico.
 * @author Diego Naya
 * Created on 17/08/2004
 */
public class AfiliadoCronicoHBDAO implements AfiliadoCronicoDAO  {
	/**
	 * Log4J
	 */
	private static final Log logger = LogFactory.getLog(AfiliadoCronicoHBDAO.class);
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
	 * Fecha Vigencia
	 */
	private static final String fechaHasta = 
	    Configuration.getInstance().getParameter(Constantes.FECHA_VIGENCIA);
	/**
	 * SDF
	 */
	private static final SimpleDateFormat sdf = 
	    new SimpleDateFormat("yyyyMMdd");
	
	private String hoy = "";
	private long codMedico = 0;
	private long tramiteID = 0;
	
	/**
	 * Sin uso en esta clase.
	 */
	public void init(DaoConfigEntry arg0) {
		/* Nothing todo */
	}
	
	/**
	 * Recupera datos de Afiliados
	 * @param afiliado
	 * @exception DataAccessException
	 */
	public AfiliadoCronico recuperarDatosAfiliadoCronico(AfiliadoCronico afiliadoCronico)
		throws DataAccessException 
	{
		tramiteID = 0;
		codMedico = 0;
		
		List listaDiag = this.recuperarPatologias(afiliadoCronico.getAfiliado(), 
			GatewayDiagnostico.class, Diagnostico.class);
			
		List listaEpid = this.recuperarPatologias(afiliadoCronico.getAfiliado(),
			GatewayDatoEpidemiologico.class, DatoEpidemiologico.class);		
		
		MedicoTratante medicoTratante = new MedicoTratante();
		medicoTratante.setCodigo(codMedico);
		
		afiliadoCronico.setDiagnosticos(listaDiag);
		afiliadoCronico.setDatosEpidemiologicos(listaEpid);
		afiliadoCronico.setMedicoTratante(medicoTratante);
		
		return afiliadoCronico;
	}
	
	/**
	 * Inserta o Actualiza o boora PMI
	 * @param pmi
	 * @throws DataAccessException
	 */
	public void actualizarPMI(Diagnostico pmi, AfiliadoCronico cronico)
		throws DataAccessException {

	    GenericDAO gdao = (GenericDAO)DAOFactory.getInstance().getDAO(GenericDAO.class);
	    String nroBp = cronico.getAfiliado().getNroBP();
	    
        GatewayDiagnostico gate = this.createGatePMI(pmi, nroBp);	    
        GatewayDiagnostico gateOld = this.recuperarPatologiaPorBPyCodigo(nroBp, CODIGO_PMI, true);
        
        if (gateOld != null) {
            if (gate != null && gate.equals(gateOld)) {
                gdao.deleteObject(gateOld);
                
            } else {
                gateOld.anular();
                gdao.updateObject(gateOld);
            }
        }
			    
		if (gate != null) {
			
			//version-1.2.7 - INICIO
			//logger.info("ALTA PMI { bP="+gate.getGatePK().getNrobp()+", codigo="+gate.getGatePK().getCodigo()+" }");
			
			logger.info("Alta y Modificacion de PMI - Datos - INICIO");
			logger.info(pmi.getCodigo());
			logger.info(pmi.getDescripcion() );
			logger.info(pmi.getFechaDesde());
			logger.info(pmi.getFechaDesdeDDMMYYYY());
			logger.info(pmi.getFechaHasta());
			logger.info(pmi.getFechaHastaDDMMYYYY());
			logger.info(pmi.getMedicoTratante());
			logger.info(pmi.getTimestamp());
			logger.info("Alta y Modificacion de PMI - Datos - FIN");
			//version-1.2.7 - FIN

            gdao.newObject(gate, gate.getGatePK());
	    }
	}
	
	/**
	 * Crea GateWay PMI
	 * @param diag
	 * @param bp
	 * @return
	 */
	private GatewayDiagnostico createGatePMI(
	        Diagnostico diag, String bp) {
	    	    
	    GatewayDiagnostico gate = null;
	    
        if (diag != null) {
            long now = GeneralHelper.toLong(sdf.format(new Date()));
            
	        gate = new GatewayDiagnostico();
	        gate.setGatePK(bp, CODIGO_PMI, now);
            gate.setVigHasta(diag.getFechaHasta());
            gate.setTramiteID(0);
            gate.setMedicoID(0);
        }
        
        return gate;
	}
	
	/**
	 * Recupera Lista de Patologias Asignados al Cronico
	 * @param afiliado
	 * @return Lista
	 */
	private List recuperarPatologias(Afiliado afiliado, Class classGate, Class classBean) {
		try {
			
			List lista = this.recuperarGatewayPatologias(afiliado, classGate);
			List listaBean = new ArrayList();
		
			for (Iterator it = lista.iterator(); it.hasNext(); ) {
				GatewayPatologiaBase gate = (GatewayPatologiaBase)it.next();
				PatologiaBase bean = (PatologiaBase)classBean.newInstance();
				MedicoTratante medico = new MedicoTratante();
				 
				medico.setCodigo(gate.getMedicoID());
				bean.setCodigo(gate.getGatePK().getCodigo());
				bean.setFechaDesde(gate.getGatePK().getVigDesde());
				bean.setFechaHasta(gate.getVigHasta());				
				bean.setMedicoTratante(medico);
				listaBean.add(bean);
			
				logger.debug("CODIGO DE " + classBean.toString() + ":" + gate.getGatePK().getCodigo());
			
				if (gate.getTramiteID() > tramiteID) {
					codMedico = gate.getMedicoID();
					tramiteID = gate.getTramiteID();
					logger.debug("CODIGO DE MEDICO TRATANTE: " + codMedico);
				}
			}
			
			return listaBean;			
		
		} catch (IllegalAccessException il) {
			logger.error(il.getMessage(), il);
			throw new UnexpectedException(il.getMessage(), il);
						
			
		} catch (InstantiationException it) {			
			logger.error(it.getMessage(), it);
			throw new UnexpectedException(it.getMessage(), it);
		}
	}
	
	/**
	 * Recupera Lista de Patologias
	 * @param afiliado
	 * @return Lista
	 */
	private List recuperarGatewayPatologias(Afiliado afiliado, Class cls) {
		
		try {
			hoy = sdf.format(new Date());
			
			StringBuffer sql = new StringBuffer(" as pat ");
			sql.append(" WHERE pat.gatePK.nrobp='" + afiliado.getNroBP() + "'");
			sql.append(" AND   pat.vigHasta > " + hoy);
			/* PMI */
			sql.append(" AND   pat.gatePK.codigo <> '" + CODIGO_PMI_HIJO + "'");			
			sql.append(" AND   pat.gatePK.codigo <> '" + CODIGO_PMI + "'");			
			
			GenericDAO gdao = (GenericDAO)DAOFactory.getInstance().getDAO(GenericDAO.class);			
			List lista = gdao.getObjectsByCriteria(cls, sql.toString());
			
			return lista;
						
		} catch (DataAccessException e) {
			logger.error(e);
			e.printStackTrace();
			throw new UnexpectedException(e);
		}
	}
	
	/**
	 * Dar de baja implica agregar un registro en la tabla de CRM
	 * con la marca de 'B' (Baja) y actualizar patologias y datos Ep. 
	 * con fecha hasta al dia de la baja.
	 */
	public void darDeBajaAfiliadoCronico(AfiliadoCronico afiliadoCronico)
		throws DataAccessException 
	{
		logger.debug("ELIMINANDO DATOS RELACIONADOS");

		List gateDiagList = this.recuperarGatewayPatologias(
			afiliadoCronico.getAfiliado(), GatewayDiagnostico.class);
			
		List gateEpidList = this.recuperarGatewayPatologias(
			afiliadoCronico.getAfiliado(), GatewayDatoEpidemiologico.class);
		
		deletePatologias(gateDiagList, gateEpidList);
	}
	
	/**
	 * Marca fisca de baja
	 * @param gateDiagList
	 * @param gateEpidList
	 * @throws DataAccessException
	 */
	private void deletePatologias(
		List gateDiagList, List gateEpidList)
		throws DataAccessException
	{
		GenericDAO gdao = (GenericDAO)DAOFactory.getInstance().getDAO(GenericDAO.class);
		long now = GeneralHelper.toLong(sdf.format(new Date()));		
				
		/* Marca como de Baja las Patologias */
		for (Iterator it = gateDiagList.iterator(); it.hasNext();) {
			GatewayDiagnostico gateDiag = (GatewayDiagnostico)it.next();
			
			gateDiag.setVigHasta(now);
			
			gdao.updateObject(gateDiag, gateDiag.getGatePK());			
			logger.debug("ELIMINACION LOGICA DE PATOLOGIA: " + gateDiag.toString());
		}

		/* Marca como de Baja los datos Ep. */		
		for (Iterator it = gateEpidList.iterator(); it.hasNext();) {
			GatewayDatoEpidemiologico gateEpid = (GatewayDatoEpidemiologico)it.next();
			
			gateEpid.setVigHasta(now);
			
			gdao.updateObject(gateEpid, gateEpid.getGatePK());
			logger.debug("ELIMINACION LOGICA DE EPIDEM.: " + gateEpid.toString());
		}
	}
	
	/**
	 * Inserta patologias y epidem.
	 * @param afiliadoCronico
	 */	
	public void ingresarAfiliadoCronico(AfiliadoCronico afiliadoCronico)
		throws DataAccessException	 
	{
		hoy = sdf.format(new Date());		
		
		try {
			logger.debug("INGRESANDO PATOLOGIAS");
			
			insertarPatologias(afiliadoCronico.getDiagnosticos(), 
				afiliadoCronico, GatewayDiagnostico.class);

			insertarPatologias(afiliadoCronico.getDatosEpidemiologicos(), 
				afiliadoCronico, GatewayDatoEpidemiologico.class);
			
		} catch (IllegalAccessException il) {
			logger.error(il.getMessage(), il);
			throw new UnexpectedException(il.getMessage(), il);
			
		} catch (InstantiationException it) {			
			logger.error(it.getMessage(), it);
			throw new UnexpectedException(it.getMessage(), it);
		}
	}

	/**
	 * Inserta Patologias del Afiliado Cronico
	 * @param lista Lista de Patologias
	 * @param afiliadoCronico Afiliado Cronico
	 * @param cls Clase para Hibernate
	 * @throws DataAccessException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private void insertarPatologias(List lista, AfiliadoCronico afiliadoCronico, Class cls)
		throws DataAccessException, IllegalAccessException, InstantiationException	 
	{
		GenericDAO gdao = (GenericDAO)DAOFactory.getInstance().getDAO(GenericDAO.class);
						
		for (Iterator it = lista.iterator(); it.hasNext(); ) {
			PatologiaBase dato = (PatologiaBase)it.next();
			GatewayDiaEpiPK pk = new GatewayDiaEpiPK();
			
			pk.setNrobp(afiliadoCronico.getAfiliado().getNroBP());
			pk.setCodigo(dato.getCodigo());
			pk.setVigDesde(GeneralHelper.toLong(hoy));
			
			/* Obtengo fechaHasta del bean (x PMI) */
			long fecha = GeneralHelper.toLong(fechaHasta);
			if (dato.getFechaHasta() != 0) {
			    fecha = dato.getFechaHasta();
			}
			
			GatewayPatologiaBase gate = (GatewayPatologiaBase)gdao.getObjectByCompositeID(cls, pk);
			
			if (gate == null ) {
				logger.debug("INSERT " + cls.toString() + ":" + pk.toString());
				
				gate = (GatewayPatologiaBase)cls.newInstance();			
				gate.setGatePK(pk);
				gate.setMedicoID(afiliadoCronico.getMedicoTratante().getCodigo());
				gate.setTramiteID(afiliadoCronico.getTramite().getCodigo());
				gate.setVigHasta(fecha);
				gdao.newObject(gate, gate.getGatePK());
				
			} else {
				logger.debug("UPDATE " + cls.toString() + ":" + pk.toString());
								
				gate.setMedicoID(afiliadoCronico.getMedicoTratante().getCodigo());
				gate.setTramiteID(afiliadoCronico.getTramite().getCodigo());
				gate.setVigHasta(fecha);
				gdao.updateObject(gate, gate.getGatePK());
			}
		}
	}
	
	/**
	 * Elimina Patologias Eliminadas.
	 * @param afiliadoCronico
	 * @param afiliadoCronicoOld
	 * @exception DataAccessException
	 */
	public void actualizarPatologiasEliminadasAfiliadoCronico(
		AfiliadoCronico afiliadoCronico)
		throws DataAccessException
	{
		logger.debug("ACTUALIZANDO PATOLOGIAS ELIMINADAS....");
		hoy = sdf.format(new Date());		

		/* 
		 * Elimina de la lista de Patologias Grabadas las que existen en la lista
		 * de Patologias Nuevas. Luego elimina la lista que se obtiene, asi borra
		 * las desmarcadas. 
		 */ 
		Predicate predicadoGateDiag = new QuitarGate(afiliadoCronico.getDiagnosticos());		
		Predicate predicadoGateEpid = new QuitarGate(afiliadoCronico.getDatosEpidemiologicos());
		
		List gateDiagList = this.recuperarGatewayPatologias(
			afiliadoCronico.getAfiliado(), GatewayDiagnostico.class);
		CollectionUtils.filter(gateDiagList, predicadoGateDiag);

		List gateEpidList = this.recuperarGatewayPatologias(
			afiliadoCronico.getAfiliado(), GatewayDatoEpidemiologico.class);
		CollectionUtils.filter(gateEpidList, predicadoGateEpid);
		
		this.deletePatologias(gateDiagList, gateEpidList);
	}		
	
	/**
	 * Recupera Patologia 
	 * @param bp
	 * @param codigo
	 * @return GatewayDiagnostico
	 * @throws DataAccessException
	 */
	public GatewayDiagnostico recuperarPatologiaPorBPyCodigo(
	        String bp, String codigo) 
		throws DataAccessException {
	    return this.recuperarPatologiaPorBPyCodigo(bp, codigo, false);
	}
	
	/**
	 * Recupera Patologia 
	 * @param bp
	 * @param codigo
	 * @param deHoy 
	 * @return GatewayDiagnostico
	 * @throws DataAccessException
	 */
	public GatewayDiagnostico recuperarPatologiaPorBPyCodigo(
	        String bp, String codigo, boolean deHoy) 
		throws DataAccessException {
	    
	    String now = sdf.format(new Date());
	    
		StringBuffer sql = new StringBuffer(" as pat ");
		sql.append(" WHERE pat.gatePK.nrobp='" + bp + "'");
		sql.append(" AND   pat.gatePK.codigo='" + codigo + "'");			
		sql.append(" AND   pat.vigHasta>=" + now);
		
		if (!deHoy) {
		    sql.append(" AND   pat.gatePK.vigDesde <> pat.vigHasta");
		}
		
		GenericDAO gdao = (GenericDAO)DAOFactory.getInstance().getDAO(GenericDAO.class);			
		List lista = gdao.getObjectsByCriteria( GatewayDiagnostico.class, sql.toString());
		if (lista != null && lista.size() != 0) {
		    return (GatewayDiagnostico)lista.get(0);
		} else {
		    return null;
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
	class QuitarGate implements Predicate {
		private List lista;
	
		public QuitarGate(List list) {
			lista = list;
		}
		
		public boolean evaluate(Object input) {
			if (input instanceof GatewayDatoEpidemiologico
				|| input instanceof GatewayDiagnostico) {
				GatewayPatologiaBase gate = (GatewayPatologiaBase)input;
			
				for (Iterator it = lista.iterator(); it.hasNext(); ) {
					PatologiaBase dato = (PatologiaBase)it.next();
				
					if (dato.getCodigo().equals(gate.getGatePK().getCodigo())) {
				        return false;
					}
				}
			}
			return true;
		}
	}
}
