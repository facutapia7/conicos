/*
 * Created on 17/08/2004
 */
package ar.com.osde.cronicos.business;

import ar.com.osde.cronicos.beans.Tramite;
import ar.com.osde.framework.business.BusinessObject;
import ar.com.osde.framework.dao.DAOFactory;
import ar.com.osde.framework.dao.DataAccessException;
import ar.com.osde.framework.dao.GenericDAO;
import ar.com.osde.framework.exception.UnexpectedException;

/**
 * Business Object para Tramites.
 * @author Diego Naya
 * @version 1.0
 * Created on 17/08/2004
 */
public class TramiteBO extends BusinessObject {
	/**
	 * Ingresa un Tramite y lo persiste.
	 * @param tramite el tramite a ingresar.
	 */
	public void ingresarTramite(Tramite tramite) {
		logger.debug("PERSISTIENDO TRAMITE");
		logger.debug("CODIGO TRAMITE: " + tramite.getCodigo());
		logger.debug("FECHA TRAMITE: " + tramite.getFechaCarga());
		logger.debug("USUARIO TRAMITE: " + tramite.getUsuario());
		logger.debug("FILIAL TRAMITE: " + tramite.getFilial());
		logger.debug("CAP TRAMITE: " + tramite.getCapReceptor());

		GenericDAO gdao =
			(GenericDAO) DAOFactory.getInstance().getDAO(GenericDAO.class);

		try {
			gdao.newObject(tramite);
		} catch (DataAccessException e) {
			logger.error(e);
			e.printStackTrace();
			throw new UnexpectedException(e);
		}
	}
}
