/*
 * Created on 17/08/2004
 */
package ar.com.osde.cronicos.business;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.osde.cronicos.beans.Afiliado;
import ar.com.osde.cronicos.dao.AfiliadoDAO;
import ar.com.osde.cronicos.exceptions.AfiliadoNoEncontradoException;
import ar.com.osde.framework.business.BusinessObject;
import ar.com.osde.framework.dao.DAOFactory;
import ar.com.osde.framework.dao.DataAccessException;
import ar.com.osde.framework.exception.UnexpectedException;

/**
 * @author Diego Naya
 * Created on 17/08/2004
 */
public class AfiliadoBO extends BusinessObject {
	transient Log logger = LogFactory.getLog(AfiliadoBO.class);
	
	/**
  	* Obtiene un Afiliado a traves del Business Connector
  	* @param filial la filial a la que corresponde el afiliado.
  	* @param nroAfiliado el numero de afiliado
  	* @param beneficiario el numero de beneficiario del afiliado
  	* @return el Afiliado si existe, null si no existe
  	*/
 	public Afiliado obtenerAfiliado(int filial, int nroAfiliado, int beneficiario) 
 		throws AfiliadoNoEncontradoException
  	{
	    
    	logger.debug("PARAMETROS A BUSCAR:" + filial + ":" + nroAfiliado + ":" + beneficiario);
    	AfiliadoDAO afdao = (AfiliadoDAO)DAOFactory.getInstance().getDAO(AfiliadoDAO.class);
 	    
		try {
			return afdao.consultaAfiliado(filial,nroAfiliado,beneficiario);
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw new UnexpectedException(e);
		}
 	}
}
