package ar.com.osde.cronicos.dao.bc;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.osde.cronicos.beans.Afiliado;
import ar.com.osde.cronicos.beans.AfiliadoBCC;
import ar.com.osde.cronicos.beans.AfiliadoBCR;
import ar.com.osde.cronicos.dao.AfiliadoDAO;
import ar.com.osde.cronicos.exceptions.AfiliadoNoEncontradoException;
import ar.com.osde.framework.bconnector.BConnector;
import ar.com.osde.framework.config.DaoConfigEntry;
import ar.com.osde.framework.dao.DataAccessException;

/**
 * 
 * DAO para el Business Connector
 * 
 * @author	Diego Naya
 * @version	Agosto 17, 2004
 * Update Adrian C. Martinez 01/11/2004 
 */
public class BCAfiliadoDAO implements AfiliadoDAO {

	private Log logger = LogFactory.getLog(BCAfiliadoDAO.class);
	private BConnector pManager = new BConnector();

	private Collection consultaDatosAfiliados1(AfiliadoBCC anData) {
		
		Collection items = (Collection) pManager.execute(anData,"consultaDatosAfiliadosPMI");

		return items;
	
	}

	public void init(DaoConfigEntry entry) {

	}

	public Afiliado consultaAfiliado(
		int filial,
		int nroAfiliado,
		int beneficiario)
		throws DataAccessException, AfiliadoNoEncontradoException {
			
		Afiliado afiliado = new Afiliado();
		AfiliadoBCC dataf1 = new AfiliadoBCC();
		String numero =
			Afiliado.parseAfiliadoString(filial, nroAfiliado, beneficiario);

		logger.info("BUSCANDO EN BC EL AFILIADO: " + numero);
			
		dataf1.setFilial(String.valueOf(filial));
		dataf1.setNumeroSocio(String.valueOf(nroAfiliado));
		dataf1.setOrden(String.valueOf(beneficiario));

		AfiliadoBCR bcAfi = new AfiliadoBCR();
		Collection c = this.consultaDatosAfiliados1(dataf1);

		if (c == null) {
			logger.warn("NO SE ENCONTRO EL AFILIADO - EL BC FRAMEWORK DEVOLVIO NULL");
			throw new AfiliadoNoEncontradoException();
			
		} else {
			
			bcAfi = (AfiliadoBCR) c.toArray()[0];
			
			
			if ( (new Integer(bcAfi.getNumeroSocio())).intValue() == nroAfiliado ) {

				afiliado.setNombre(bcAfi.getNombre());
				afiliado.setApellido(bcAfi.getApellido());
				afiliado.setNroBP(bcAfi.getNumeroIC());
				afiliado.setFechaNacimiento(bcAfi.getFnac());
				afiliado.setCodigo(Long.parseLong(bcAfi.getFilial() + bcAfi.getNumeroSocio() + bcAfi.getOrden()));
				if(bcAfi.getSexo().equalsIgnoreCase("D")) {
					afiliado.setSexo("X");
				} else {
					afiliado.setSexo(bcAfi.getSexo());
				}
				afiliado.setActualizacion(false);
				afiliado.setFechaBaja(bcAfi.getFecbaj()); 
				afiliado.setParentesco(bcAfi.getDesParentesco());
				afiliado.setFechaAlta(bcAfi.getFechaAlta());
				afiliado.setMesesReconocidos(bcAfi.getMesesReconocidos());
				afiliado.setPlan(bcAfi.getPlanServ());
				afiliado.setTipo(bcAfi.getDescrTipsoc());
				afiliado.setCarencias(bcAfi.getTpoEsp());
				afiliado.setParentescoId(bcAfi.getParentesco());
				logger.warn(
					"SE ENCONTRO EL NUMERO DE SOCIO:" + bcAfi.getNumeroSocio());
				return afiliado;
				
			} else {
				logger.warn(
					"NO SE ENCONTRO EL AFILIADO + BUG DE BUSINESS CONNECTOR, ME DEVOLVIO:"
						+ bcAfi.getNumeroSocio());
				throw new AfiliadoNoEncontradoException();
			}
		}
	}
}
