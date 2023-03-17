package ar.com.osde.cronicos.dao;

import ar.com.osde.cronicos.beans.AfiliadoCronico;
import ar.com.osde.cronicos.beans.Diagnostico;
import ar.com.osde.cronicos.beans.GatewayDiagnostico;
import ar.com.osde.framework.dao.DataAccessException;
import ar.com.osde.framework.dao.DataAccessObject;

/**
 * Interfaz para el DAO de un Afiliado Cronico.
 * @author Diego Naya
 * @version 1.0
 */
public interface AfiliadoCronicoDAO extends DataAccessObject {

	public AfiliadoCronico recuperarDatosAfiliadoCronico(AfiliadoCronico afiliadoCronico)
		throws DataAccessException;
	
	public void darDeBajaAfiliadoCronico(AfiliadoCronico afiliadoCronico)
		throws DataAccessException;

	public void ingresarAfiliadoCronico(AfiliadoCronico afiliadoCronico)
		throws DataAccessException;
		
	public void actualizarPatologiasEliminadasAfiliadoCronico(AfiliadoCronico afiliadoCronico) 
		throws DataAccessException;
	
	public GatewayDiagnostico recuperarPatologiaPorBPyCodigo(String bp, String codigo)
		throws DataAccessException;
	
	public void actualizarPMI(Diagnostico pmi, AfiliadoCronico cronico)
		throws DataAccessException;	
}
