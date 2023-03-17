package ar.com.osde.patologiaService.backend.bo;


import java.util.List;

import ar.com.osde.framework.dao.DataAccessException;
import ar.com.osde.patologiaService.common.entities.PatologiaSocio;

public interface PatologiaSocioBO {

	public List<PatologiaSocio> searchPatologiasByIc(String codigoIc, String fecha) throws DataAccessException;
}