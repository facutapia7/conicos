package ar.com.osde.patologiaService.backend.webservice;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebParam.Mode;

import ar.com.osde.cronicos.exceptions.PatologiaServiceException;
import ar.com.osde.cronicos.utils.FormatoFecha;
import ar.com.osde.cronicos.utils.Validador;
import ar.com.osde.framework.dao.DataAccessException;
import ar.com.osde.framework.persistence.SessionManager;
import ar.com.osde.patologiaService.backend.bo.PatologiaSocioBO;
import ar.com.osde.patologiaService.backend.bo.impl.PatologiaSocioBOImpl;
import ar.com.osde.patologiaService.common.entities.PatologiaSocio;
import ar.com.osde.patologiaService.common.webService.PatologiaService;


public class PatologiaServiceImpl implements PatologiaService {
	private PatologiaSocioBO patologiaSocioBO=null;
	private static final int longitudIC= 10;
	private static final FormatoFecha formato = FormatoFecha.yyyyMMdd;
	private static Logger LOGGER = Logger.getLogger(PatologiaServiceImpl.class);

	public PatologiaServiceImpl() {
	}

	
	@WebMethod
	
	public PatologiaSocio[] searchPatologiasSocioByIc(@WebParam(name = "ic", mode = Mode.IN) String ic,
			@WebParam(name = "fecha", mode = Mode.IN) Date fecha) throws PatologiaServiceException, DataAccessException  {
		
		// ESAPI.encoder().canonicalize(fechaString);
		try {
			long init = System.currentTimeMillis();
			comprobarDatosValidos(ic,fecha);
			String fechaAEnviar = Validador.covertFechaString(fecha,formato);
			LOGGER.info("SearchPatologiasSocioByIc - IC Socio: " + ic + " Fecha Vigencia: " + fechaAEnviar);
			if(patologiaSocioBO == null) patologiaSocioBO = new PatologiaSocioBOImpl();
			List<PatologiaSocio> listaPatologiasSocio = patologiaSocioBO.searchPatologiasByIc(ic, fechaAEnviar);
			/*if (listaPatologiasSocio == null|| listaPatologiasSocio.isEmpty()) {
				LOGGER.info(" No se encontraron datos en la base con IC = "+ ic + " y fecha = "+fechaAEnviar);
				return null;
			}*/
			PatologiaSocio[] searchResult = listaPatologiasSocio
					.toArray(new PatologiaSocio[listaPatologiasSocio.size()]);
					/*	para que solo devuelva 	 
					 				 codigo de la Patologia
									 descripcion de la Patología
									 fechaDesde
									 fechaHasta 
							demás datos en  null*/
						for (PatologiaSocio patSoc : searchResult) {
								if(patSoc!=null&&patSoc.getPatologia()!=null){
								patSoc.setCodigoPat(null);
								patSoc.getPatologia().setVigDesde(null);
								patSoc.getPatologia().setVigHasta(null);	
								}
						}
			LOGGER.info("Tiempo de respuesta SearchPatologiasSocioByIc: " + ((System.currentTimeMillis()-init)/1000) + " segundos");
			return searchResult;
		} catch (PatologiaServiceException e) {
			LOGGER.error(e.getMessage());
			throw new PatologiaServiceException(" Error en los datos ingresados ");
		}catch(DataAccessException e){ 
			LOGGER.error(" Error de datos en la base "+ e);
			throw new DataAccessException(e.getMessage());
		}finally {
			SessionManager manager = SessionManager.getInstance();
			manager.closeAllSessions();
		}

	}



	private void comprobarDatosValidos(String ic, Date date) throws PatologiaServiceException {
		if(Validador.isNull(ic)){
			throw new PatologiaServiceException(" Código del socio vacio ");
		}else if(!Validador.isSoloNumeros(ic)){
				throw new PatologiaServiceException(" Código del socio contiene carácteres incorrectos "+ ic );
		}else if (!Validador.islengthValido(ic, longitudIC)){
			throw new PatologiaServiceException(" Longitud del código del socio = "+ ic + " no es válida");
		}else if(date==null){
			throw new PatologiaServiceException(" fecha vacia ");
		}else if(!Validador.isFechaValida(date, formato)){
			throw new PatologiaServiceException(" Fecha no válida " + date.toString());
		}
	}




}