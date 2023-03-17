package ar.com.osde.patologiaService.common.webService;


import java.util.Date;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebParam.Mode;
import javax.jws.WebService;
import org.apache.avalon.framework.service.ServiceException;
import ar.com.osde.cronicos.exceptions.PatologiaServiceException;
import ar.com.osde.framework.dao.DataAccessException;
import ar.com.osde.patologiaService.common.entities.PatologiaSocio;



@WebService
public interface PatologiaService {

	/**
	 * Obtenemos las PatologiaSocio que cumplen con el codigo pasado como parametro.
	 * 
	 * @param codigo
	 *            codigo
	 * @return instancia de PatologiaSocio[]
	 * @throws PatologiaServiceException 
	 * @throws DataAccessException 
	 * @throws ServiceException
	 *             Excepcion de Servicios
	 */

	@WebMethod 
	PatologiaSocio[] searchPatologiasSocioByIc(@WebParam(name = "ic", mode = Mode.IN) String ic, @WebParam(name = "fecha") Date fecha) throws PatologiaServiceException, DataAccessException;
}
