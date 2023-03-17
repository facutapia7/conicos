/*
 * Created on 17/08/2004
 */
package ar.com.osde.cronicos.business;

import ar.com.osde.cronicos.beans.MedicoTratante;
import ar.com.osde.framework.business.BusinessObject;
import ar.com.osde.framework.dao.DAOFactory;
import ar.com.osde.framework.dao.DataAccessException;
import ar.com.osde.framework.dao.GenericDAO;
import ar.com.osde.framework.exception.UnexpectedException;

/**
 * Business Object para Medico Tratante.
 * @author Diego Naya
 * Created on 17/08/2004
 * Update Adrian C. Martinez 03/11/2004
 */
public class MedicoTratanteBO extends BusinessObject {
	 /**
	  * Mediante el codigo recupera el Medico Tratante desde un soporte persistente.
	  * @param codigoMedico el codigo del Medico Tratante.
	  * @return el Medico Tratante correspondiente a ese codigo.
	  */
	 public MedicoTratante recuperarMedicoTratante(long codigoMedico){
		logger.debug("RECUPERANDO MEDICO TRATANTE");
		MedicoTratante mt = null;
			GenericDAO gdao = (GenericDAO)DAOFactory.getInstance().getDAO(GenericDAO.class);
			try {
				 mt = (MedicoTratante)
				 	gdao.getObjectByID(MedicoTratante.class, codigoMedico);
			} catch (DataAccessException e) {
				logger.error(e);
				logger.error(e.getRootCause().getMessage());
				throw new UnexpectedException(e);
			}
			return mt;
	 }
 
 	/**
 	 * Ingresa nuevo medico
 	 * @param mt Medico Tratante
 	 */
	 public void ingresarMedicoTratante(MedicoTratante mt){
	    logger.debug("PERSISTIENDO MEDICO....");
	    GenericDAO gdao = (GenericDAO)DAOFactory.getInstance().getDAO(GenericDAO.class);
	 	try {
		     gdao.newObject(mt);
		} catch (DataAccessException e) {
		    logger.error(e);
			throw new UnexpectedException(e);
		}
	 }
 
	public void actulizarMedicoTratante(MedicoTratante mt) {
		logger.debug("ACTULIZANDO MEDICO....");
		GenericDAO gdao = (GenericDAO)DAOFactory.getInstance().getDAO(GenericDAO.class);
		try {
			 gdao.updateObject(mt);
			 
		} catch (DataAccessException e) {
			logger.error(e);
			throw new UnexpectedException(e);
		}
	}
}
