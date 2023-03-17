package ar.com.osde.patologiaService.backend.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import ar.com.osde.framework.config.DaoConfigEntry;
import ar.com.osde.framework.config.FrameworkConfig;
import ar.com.osde.framework.dao.DAOFactory;
import ar.com.osde.framework.dao.DataAccessException;
import ar.com.osde.framework.dao.GenericDAO;
import ar.com.osde.framework.persistence.HibernateDataAccess;
import ar.com.osde.patologiaService.backend.dao.PatologiaSocioDAO;
import ar.com.osde.patologiaService.common.entities.PatologiaSocio;

public class PatologiaSocioDAOImpl extends HibernateDataAccess implements PatologiaSocioDAO {

	private static String[] patologiasExcluidas = null;
	private static final Logger LOGGER = Logger.getLogger(PatologiaSocioDAOImpl.class);

	static {
		String patExclVal = FrameworkConfig.getValue("patologias.excluidas");
		if ((patExclVal != null) && (patExclVal.length()>0)) {
			patologiasExcluidas = patExclVal.split(",");
		}
		}

	@SuppressWarnings("unchecked")
	public List<PatologiaSocio> searchPatologiasByIc(String codigoIc, String fecha) throws  DataAccessException {
		
		GenericDAO gdao = (GenericDAO)DAOFactory.getInstance().getDAO(GenericDAO.class);
		try {			
			long fecVig = Long.parseLong(fecha);
	
			LOGGER.debug("search patologias socio hibernate datos[" + codigoIc + "fechaAEnviar" + fecha + "]");
			StringBuffer sql = new StringBuffer("as pat WHERE pat.codigo='" + codigoIc + "'");
			sql.append(" AND   pat.vigHasta >= " + fecVig);
			sql.append(" AND   pat.patologia.codigo not in ('" + join("', '", patologiasExcluidas) + "')");
			List<PatologiaSocio> listaPatologias = gdao.getObjectsByCriteria(PatologiaSocio.class, sql.toString());
			return listaPatologias;
		} catch (DataAccessException e) {
			LOGGER.warn("Error al buscar patologias del socio", e);
			throw new DataAccessException("Error al buscar patologias del socio", e);
			
		}
	}
	
	public static String join(String separator, String[] data) {
	    StringBuilder sb = new StringBuilder();
		for (int i = 0; i < data.length - 1; i++) {
			sb.append(data[i]);
	            sb.append(separator);
	    }
		sb.append(data[data.length - 1].trim());
	    return sb.toString();
	}

	@Override
	public void init(DaoConfigEntry arg0) {
		// TODO Auto-generated method stub
		
	}

}