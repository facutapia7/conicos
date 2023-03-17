/*
 * Created on 17/08/2004
 */
package ar.com.osde.cronicos.business;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ar.com.osde.cronicos.beans.DatoEpidemiologico;

import ar.com.osde.framework.business.BusinessObject;
import ar.com.osde.framework.dao.DAOFactory;
import ar.com.osde.framework.dao.DataAccessException;
import ar.com.osde.framework.dao.GenericDAO;

/**
 * @author Diego Naya
 * Created on 17/08/2004
 * Update: Adrian C. Martinez 01/11/2004 
 */
public class DatoEpidemiologicoBO extends BusinessObject {
	
	/**
	 * Obtiene lista de Datos Epidemiologicos
	 * @return Lista de Datos Epidemiologicos
	 */
	public List obtenerDatosEpidemiologicos(){
	   GenericDAO gdao = (GenericDAO)DAOFactory.getInstance().getDAO(GenericDAO.class);
	   List diags = null;
	   try {
			diags = gdao.getAllObjects(DatoEpidemiologico.class);
			Collections.sort(diags, new SortByCodigo());
	   } catch (DataAccessException e) {
		   logger.error(e);
		   e.printStackTrace();
	   }
	   return diags;
	}
	
	private class SortByCodigo implements Comparator {
 		public int compare(Object obj1, Object obj2) {
			return ((DatoEpidemiologico)obj1)
						.getCodigo().compareTo(
							((DatoEpidemiologico)obj2).getCodigo());
 		}
 	}
	
	
}
