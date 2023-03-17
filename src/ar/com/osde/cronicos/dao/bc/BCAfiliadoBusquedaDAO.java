package ar.com.osde.cronicos.dao.bc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.osde.cronicos.beans.AfiliadoBusqueda;
import ar.com.osde.cronicos.beans.AfiliadoBusquedaBCC;
import ar.com.osde.cronicos.beans.AfiliadoBusquedaBCR;
import ar.com.osde.cronicos.beans.AfiliadoBusquedaPK;
import ar.com.osde.cronicos.beans.ListaDeAfiliados;
import ar.com.osde.cronicos.dao.AfiliadoBusquedaDAO;
import ar.com.osde.cronicos.helper.GeneralHelper;
import ar.com.osde.framework.bconnector.BConnector;
import ar.com.osde.framework.config.DaoConfigEntry;
import ar.com.osde.framework.exception.BaseException;

/**
 * @author Sebastian Acevedo
 */

public class BCAfiliadoBusquedaDAO implements AfiliadoBusquedaDAO  {
	
	private Log logger = LogFactory.getLog(BCAfiliadoDAO.class);
	private BConnector pManager = new BConnector();

	/**
	 * Busqueda de Afiliados por Nombre
	 * @param nombre, apellido
	 * @return Lista de afiliados
	 * @throws BaseException
	 */
	public ListaDeAfiliados getByNombreODocumento(String nombre, String apellido)
		throws BaseException {

		// Comando
		AfiliadoBusquedaBCC dataf1 = new AfiliadoBusquedaBCC();
		dataf1.setApellido(apellido.toUpperCase() + "*");
		dataf1.setNombre(nombre.toUpperCase() + "*");

		// Resultado
		AfiliadoBusquedaBCR bcAfi = new AfiliadoBusquedaBCR();

		// Busca en el BC
		logger.debug("BUSCANDO EN BC...");
		Collection c = this.busquedaAfiliados(dataf1);

		// Crea la lista de afiliados
		ListaDeAfiliados listaAfiliados = new ListaDeAfiliados(new ArrayList(), false);
				
		// Recorre el resultado
		if ( c != null ) {
	 		for (Iterator it = c.iterator(); it.hasNext(); ) {
	
				AfiliadoBusqueda afiliado = new AfiliadoBusqueda();
				bcAfi = (AfiliadoBusquedaBCR) it.next();
	
				afiliado.setNombre(bcAfi.getNombre());
				afiliado.setApellido(bcAfi.getApellido());
				afiliado.setTipoDocumento(bcAfi.getDocType());
				afiliado.setNumeroDocumento(bcAfi.getDocNum());
				afiliado.setPartner(bcAfi.getPartner());
				
				AfiliadoBusquedaPK afiliadoPK = new AfiliadoBusquedaPK();
				afiliadoPK.setFilial(GeneralHelper.toInt(bcAfi.getNumeroSocio().substring(0, 2)));
				afiliadoPK.setSocio(GeneralHelper.toInt(bcAfi.getNumeroSocio().substring(2, 9)));
				afiliadoPK.setBeneficiario(GeneralHelper.toInt(bcAfi.getNroOrden()));
	
				afiliado.setAfiliadoBusquedaPK(afiliadoPK);
				
				listaAfiliados.add(afiliado);
				
			}
		}
		
		return listaAfiliados;

	}

	public void init(DaoConfigEntry entry) {

	}

	private Collection busquedaAfiliados(AfiliadoBusquedaBCC anData) {
	
		Collection items =
			(Collection) pManager.execute(anData, "consultaAfiliadosApeynom");
	
		return items;
	}
}
