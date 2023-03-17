package ar.com.osde.patologiaService.backend.bo.impl;

import ar.com.osde.patologiaService.backend.bo.PatologiaBO;
import ar.com.osde.patologiaService.backend.dao.PatologiaDAO;

public class PatologiaBOImpl implements PatologiaBO {
	PatologiaDAO diagnosticoDAO;
	


	public PatologiaDAO getDiagnosticoDAO() {
		return diagnosticoDAO;
	}

	public void setDiagnosticoDAO(PatologiaDAO diagnosticoDAO) {
		this.diagnosticoDAO = diagnosticoDAO;
	}
	
	
}
