/**
 * Created on 01/12/2004
 *
 */
package ar.com.osde.cronicos.business;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.osde.cronicos.beans.AfiliadoBusqueda;
import ar.com.osde.cronicos.beans.ListaDeAfiliados;
import ar.com.osde.cronicos.dao.AfiliadoBusquedaDAO;
import ar.com.osde.framework.business.BusinessObject;
import ar.com.osde.framework.dao.DAOFactory;
import ar.com.osde.framework.dao.DataAccessException;
import ar.com.osde.framework.exception.BaseException;
import ar.com.osde.framework.exception.UnexpectedException;


/**
 * @author PG26589512
 *
 */
public class AfiliadoBusquedaBO extends BusinessObject {
	transient Log logger = LogFactory.getLog(AfiliadoBO.class);
	
	/**
	 * Recupera lista de Afiliados
	 * @param afiliado Afiliado
	 * @return List
	 * @throws BaseException
	 */
	public ListaDeAfiliados recuperarPorNombreODocumento(AfiliadoBusqueda afiliado) 
		throws BaseException 
	{
		ListaDeAfiliados listaAfiliados = null;

		try {
			
			AfiliadoBusquedaDAO afiliadoBusquedaDAO = (AfiliadoBusquedaDAO)DAOFactory.getInstance().getDAO(AfiliadoBusquedaDAO.class);

			listaAfiliados = afiliadoBusquedaDAO.getByNombreODocumento(afiliado.getNombre(), afiliado.getApellido());
			
		} catch (DataAccessException e) {
			logger.error(e);
			e.printStackTrace();
			throw new UnexpectedException(e);
		}
		return listaAfiliados;
	}

}
