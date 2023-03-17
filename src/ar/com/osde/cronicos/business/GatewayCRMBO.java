/*
 * Created on 17/08/2004
 */
package ar.com.osde.cronicos.business;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import ar.com.osde.cronicos.beans.GatewayCRM;
import ar.com.osde.cronicos.helper.GeneralHelper;
import ar.com.osde.framework.business.BusinessObject;
import ar.com.osde.framework.dao.DAOFactory;
import ar.com.osde.framework.dao.DataAccessException;
import ar.com.osde.framework.dao.GenericDAO;
import ar.com.osde.framework.exception.UnexpectedException;

/**
 * @author Diego naya
 * Created on 17/08/2004
 * Update: Adrian C. Martinez 01/11/2004
 */
public class GatewayCRMBO extends BusinessObject {

    /**
     * Actualiza CRM para disparar trigger
     * @param bp BP
     */
 	public void updateCRM(String bp) {
		try {
			GenericDAO gdao = (GenericDAO)DAOFactory.getInstance().getDAO(GenericDAO.class);
			GatewayCRM crm = new GatewayCRM();
			
			List lista = this.obtenerLista(bp);
			if( lista != null && lista.size() != 0 ) {
				crm = ((GatewayCRM)lista.get(0));
			}
			gdao.deleteObject(crm);
			gdao.newObject(crm);
			
		} catch (DataAccessException e) {
			logger.error(e);
			e.printStackTrace();
			throw new UnexpectedException(e);
		}
 	}
	
	/**
	* Verifica que el afiliado cronico exista ya en la base.
	* @param afiliado Afiliado
	* @return long con fecha de carga si ya estaba cargado 
	* 		  si no, retorna numero negativo
	* @exception DataAccessException
	* @exception AfiliadoNoEncontradoException
	*/
	public long existeTramiteIngresado(String nroBP){
		long fechaCarga = -1;
		
		try {
			List lista = this.obtenerLista(nroBP);
			if( lista != null && lista.size() != 0 ) {
				fechaCarga = ((GatewayCRM)lista.get(0)).getFecha();
			}
			
		} catch (DataAccessException e) {
			logger.error(e);
			e.printStackTrace();
			throw new UnexpectedException(e);
		}
					
		return fechaCarga;
	}
	
	/**
	 * Obtiene lista de CRM existentes
	 * @param nroBP
	 * @return
	 * @throws DataAccessException
	 */
	private List obtenerLista(String nroBP) throws DataAccessException {
		return obtenerLista(nroBP, false);		
	}
	
	/**
	 * Obtiene lista de CRM existentes
	 * @param nroBP
	 * @return
	 * @throws DataAccessException
	 */
	private List obtenerLista(String nroBP, boolean all) throws DataAccessException {
		StringBuffer sql = new StringBuffer(" as crm ");
		sql.append(" WHERE crm.nrobp='" + nroBP + "'");
		if (! all) {
			sql.append(" AND   crm.marca='A'");			
		}
			
		GenericDAO gdao = (GenericDAO)DAOFactory.getInstance().getDAO(GenericDAO.class);
		List lista = gdao.getObjectsByCriteria(GatewayCRM.class, sql.toString());
		
		return lista;		
	}
	
	/**
	 * Inserta CRM
	 * Por definicion del Funcional, debe Eliminar y despues Insertar.
	 * (No se explico la causa, pero puede ser por el numero de registro)
	 * @param crm CRM
	 */
	public void insertar(GatewayCRM crm) {
		try {
			logger.debug("ELIMINANDO E INGRESANDO CRM");
			List lista = this.obtenerLista(crm.getNrobp(), true);						
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			GenericDAO gdao = (GenericDAO)DAOFactory.getInstance().getDAO(GenericDAO.class);
						
			/* Configura fecha y estado */			
			crm.setEstado("P");
			crm.setFecha(GeneralHelper.toLong(sdf.format(new Date())));

			if (lista.size() > 0) {
				for (Iterator it = lista.iterator(); it.hasNext(); ) {
					gdao.deleteObject((GatewayCRM)it.next());
				}
			}
			logger.debug("FINwww");
			gdao.newObject(crm);
			
		} catch (DataAccessException e) {
			logger.error(e);
			e.printStackTrace();
			throw new UnexpectedException(e);
		}
	}
}
