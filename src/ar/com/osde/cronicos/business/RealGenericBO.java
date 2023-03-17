package ar.com.osde.cronicos.business;

import java.util.List;

import ar.com.osde.framework.business.BusinessObject;
import ar.com.osde.framework.dao.DataAccessException;
import ar.com.osde.framework.dao.GenericDAO;
import ar.com.osde.framework.exception.BaseException;
import ar.com.osde.framework.exception.UnexpectedException;

/**
 * Generic Business Object for ABM, can be subclassed to
 * wrap the object casts.
 * @author Diego A. Naya
 * @version 1.0
 * Created on 26/10/2004
 */
public class RealGenericBO extends BusinessObject {
	
	protected GenericDAO gdao = null;
	
	
	
	/**
	 * Genera un GenericDAO
	 *
	 */	
	public RealGenericBO() {
		gdao = (GenericDAO) daoFactory.getDAO(GenericDAO.class);
	}
	
	/**
	 * Genera Custom DAO
	 * @param dao Class 
	 */
	public RealGenericBO(Class dao) {
		gdao = (GenericDAO) daoFactory.getDAO(dao);
	}
	
	/**
	 * Callback useful for Object validation
	 * @param o
	 * @throws BaseException
	 */
	protected void preAlta(Object o) throws BaseException{
	}
	protected void postAlta(Object o) throws BaseException{
	}
	protected void preBaja(Object o) throws BaseException{
	}
	protected void postBaja(Object o) throws BaseException{
	}
	protected void preModificacion(Object o) throws BaseException{
	}
	protected void postModificacion(Object o) throws BaseException{
	}
	
	protected void preRecuperar(long id, Class c) throws BaseException{
	}
	protected void postRecuperar(long id, Class c) throws BaseException{
	}
	
	protected void preRecuperarTodos(Class c) throws BaseException{
	}
	protected void postRecuperarTodos(Class c, List todos) throws BaseException{
	}
	
	public List recuperarTodos(Class c) throws BaseException{
		this.preRecuperarTodos(c);
		List l = null;
		try {
			l =  gdao.getAllObjects(c);
		} catch (DataAccessException e) {
			logger.error(e);
			e.printStackTrace();
			throw new UnexpectedException(e);
		}
		this.postRecuperarTodos(c,l);
		return l;
		
	}
	
	public void altaObjecto(Object o) throws BaseException{
		this.preAlta(o);
		try {
			gdao.newObject(o);
		} catch (DataAccessException dae) {
			logger.error("Error creando el Objeto", dae);
			dae.printStackTrace();
			throw new UnexpectedException(dae);
		}
		this.postAlta(o);
	}

	public void bajarObject(Object o) throws BaseException {
		this.preBaja(o);
		try {
			gdao.deleteObject(o);
		} catch (DataAccessException dae) {
			throw new BaseException(
				"No se pudo borrar el Objeto de la DB.",
				dae);
		}
		this.postBaja(o);
	}


	public void modificarObjeto(Object o) throws BaseException {
		this.preModificacion(o);
		try {
			gdao.updateObject(o);
		} catch (DataAccessException dae) {
			throw new BaseException(
				"No se pudo modificar el Objeto de la DB.",
				dae);
		}
		this.postModificacion(o);
	}

	public Object recuperarObjeto(long id,Class c) throws BaseException{
		this.preRecuperar(id, c);
		Object o = this.doRecuperarObjeto(id, c);
		this.postRecuperar(id, c);
		return o;
	}
	
	protected Object doRecuperarObjeto(long id, Class c)
		throws BaseException {
		try {
			return gdao.getObjectByID(
				c,
				id);
		} catch (DataAccessException dae) {
			throw new BaseException(
				"No se encontró el Objeto en la DB.",
				dae);
		}
	}
	
}
