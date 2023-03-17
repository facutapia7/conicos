package ar.com.osde.cronicos.dao;

import ar.com.osde.cronicos.beans.Afiliado;
import ar.com.osde.cronicos.exceptions.AfiliadoNoEncontradoException;
import ar.com.osde.framework.dao.DataAccessException;
import ar.com.osde.framework.dao.DataAccessObject;

/**
 * Interfaz para el DAO de un Afiliado
 * @author Diego Naya
 * @version 1.0
 */
public interface AfiliadoDAO extends DataAccessObject {

	Afiliado consultaAfiliado(int filial, int nroAfiliado, int beneficiario)
		throws DataAccessException, AfiliadoNoEncontradoException;
	
}
