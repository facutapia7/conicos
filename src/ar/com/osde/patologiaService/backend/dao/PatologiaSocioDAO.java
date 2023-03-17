package ar.com.osde.patologiaService.backend.dao;

import java.util.List;
import ar.com.osde.framework.dao.DataAccessException;
import ar.com.osde.framework.dao.DataAccessObject;
import ar.com.osde.patologiaService.common.entities.PatologiaSocio;


public interface PatologiaSocioDAO extends DataAccessObject {	
	
	public List<PatologiaSocio> searchPatologiasByIc(String codigoIc, String fecha) throws DataAccessException;

	
}