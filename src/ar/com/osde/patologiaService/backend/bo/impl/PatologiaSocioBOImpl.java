package ar.com.osde.patologiaService.backend.bo.impl;


import java.util.List;

import ar.com.osde.framework.business.BusinessObject;
import ar.com.osde.framework.dao.DAOFactory;
import ar.com.osde.framework.dao.DataAccessException;
import ar.com.osde.patologiaService.backend.bo.PatologiaSocioBO;
import ar.com.osde.patologiaService.backend.dao.PatologiaSocioDAO;
import ar.com.osde.patologiaService.common.entities.PatologiaSocio;



/**
 * @author MT26268233
 *
 */
public class PatologiaSocioBOImpl extends BusinessObject implements PatologiaSocioBO  {

	PatologiaSocioDAO patologiaSocioDAO;
	
	@Override
	public List<PatologiaSocio> searchPatologiasByIc(String codigoIc, String fecha) throws DataAccessException {
		//TODO agregar validaciones ESAPI y demas
		try {
			PatologiaSocioDAO patologiaSocioDAO = (PatologiaSocioDAO)DAOFactory.getInstance().getDAO(PatologiaSocioDAO.class);
			return patologiaSocioDAO.searchPatologiasByIc(codigoIc, fecha);
		} catch (DataAccessException ex) {
			throw new DataAccessException(ex);
		}
	}

	public PatologiaSocioDAO getPatologiaSocioDAO() {
		return patologiaSocioDAO;
	}

	public void setPatologiaSocioDAO(PatologiaSocioDAO patologiaSocioDAO) {
		this.patologiaSocioDAO = patologiaSocioDAO;
	}

}
