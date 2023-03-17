/*
 * Created on 17/08/2004
 */
package ar.com.osde.cronicos.business;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import ar.com.osde.cronicos.beans.Diagnostico;
import ar.com.osde.framework.business.BusinessObject;
import ar.com.osde.framework.dao.DAOFactory;
import ar.com.osde.framework.dao.DataAccessException;
import ar.com.osde.framework.dao.GenericDAO;

/**
 * @author Diego Naya
 * Created on 17/08/2004
 * Update: Adrian C. Martinez 01/11/2004
 */
public class DiagnosticoBO extends BusinessObject {
	
	/**
	 * Retorna lista de Diagnosticos
	 * @return Disgnosticos
	 */
	public List obtenerDiagnosticos(){
    	GenericDAO gdao = (GenericDAO)DAOFactory.getInstance().getDAO(GenericDAO.class);
 		List diags = null;
 		try {
	    	diags = gdao.getAllObjects(Diagnostico.class);
	    	Collections.sort(diags, new SortByCodigo());
	    	
		} catch (DataAccessException e) {
	    	logger.error(e);
			e.printStackTrace();
		}
		return diags;
 	}
	
	/**
	 * Recupera diagnostico
	 * @param codigo
	 * @param diagnosticos
	 * @return Diagnostico
	 */
	public Diagnostico obtenerDiagnostico(String codigo, List diagnosticos) {
    	GenericDAO gdao = (GenericDAO)DAOFactory.getInstance().getDAO(GenericDAO.class);
    	Diagnostico diags = null; 
    	
	    for (Iterator iter = diagnosticos.iterator(); iter.hasNext();) {
            Diagnostico element = (Diagnostico) iter.next();
            
            if (element.getCodigo().equals(codigo)) {
                diags = element;
            }
            
        }
		return diags;
	}
 	
 	/**
 	*  
 	* Comparator para ordenar por descripcion 
 	*  
 	* @author	Adrian C. Martinez
 	*  
 	* @version	1.0.0
 	*  
 	* @date		Jan 18, 2005
 	*/
 	private class SortByDescription implements Comparator {
 		public int compare(Object obj1, Object obj2) {
			return ((Diagnostico)obj1)
						.getDescripcion().compareTo(
							((Diagnostico)obj2).getDescripcion());
 		}
 	}
	
	
	private class SortByCodigo implements Comparator {
 		public int compare(Object obj1, Object obj2) {
			return ((Diagnostico)obj1)
						.getCodigo().compareTo(
							((Diagnostico)obj2).getCodigo());
 		}
 	}
	
	
}
