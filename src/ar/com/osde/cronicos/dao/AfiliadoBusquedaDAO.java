package ar.com.osde.cronicos.dao;

import ar.com.osde.cronicos.beans.ListaDeAfiliados;
import ar.com.osde.framework.dao.DataAccessObject;
import ar.com.osde.framework.exception.BaseException;

/**
 * Interfaz para el DAO de un Afiliado
 * @author Diego Naya
 * @version 1.0
 */
public interface AfiliadoBusquedaDAO extends DataAccessObject {

	/**
	 * Busqueda Blanda de Afiliados por Nombre y/o Documento
	 * @param nombre, apellido
	 * @return Lista de Afiliados
	 * @throws BaseException
	 */
	public ListaDeAfiliados getByNombreODocumento(String nombre, String apellido) throws BaseException;
	
}
