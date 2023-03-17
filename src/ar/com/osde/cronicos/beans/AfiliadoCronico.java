/*
 * Created on 17/08/2004
 */
package ar.com.osde.cronicos.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.osde.cronicos.Constantes;
import ar.com.osde.cronicos.helper.GeneralHelper;
import ar.com.osde.framework.entity.EntityObject;

/**
 * Esta Clase representa un Afiliado Cronico , es el objeto clave del dominio.
 * @author Diego Naya
 * @version 1.0
 * Created on 17/08/2004
 * Update Adrian C. Martinez 01/11/2004
 */
public class AfiliadoCronico implements EntityObject {
    private static final Log logger = LogFactory.getLog(AfiliadoCronico.class);
    
	private static final SimpleDateFormat sdfYYYYMMDD = new SimpleDateFormat("yyyyMMdd"); 
	private static final SimpleDateFormat sdfDDMMYYYY = new SimpleDateFormat("dd/MM/yyyy");
	
	private Afiliado afiliado;
 	private List datosEpidemiologicos;
 	private List diagnosticos;
 	private MedicoTratante medicoTratante;
 	private Tramite tramite;
 	private long fechaCarga;
 	private Diagnostico pmiHijo;
 	private Diagnostico pmi;
 	private boolean actualizacionPMI;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AfiliadoCronico ["
				+ (afiliado != null ? "afiliado=" + afiliado.toString() + ", " : "") + "\n"
				+ (datosEpidemiologicos != null ? "datosEpidemiologicos=" + datosEpidemiologicos + ", " : "") + "\n"
				+ (diagnosticos != null ? "diagnosticos=" + diagnosticos + ", "	: "") + "\n"
				+ (medicoTratante != null ? "medicoTratante=" + medicoTratante.toString() + ", " : "") + "\n"
				+ (tramite != null ? "tramite=" + tramite.toString() + ", " : "") + "\n"
				+ "fechaCarga=" + fechaCarga + ", " + "\n"
				+ (pmiHijo != null ? "pmiHijo=" + pmiHijo + ", " : "") + "\n"
				+ (pmi != null ? "pmi=" + pmi + ", " : "") + "\n"
				+ "actualizacionPMI=" + actualizacionPMI + "]";
	}

	/**
	* Constructor base.
	*/
	public AfiliadoCronico() {
		super();
	}
	
	/**
	* Constructor que toma un afiliado.
	* @param afili El Afiliado a ser tratado como Cronico.
	*/
	public AfiliadoCronico(Afiliado afili) {
		super();
		afiliado = afili;
		fechaCarga = -1;
	}

	/**
 	* @return El Afiliado asignado como Cronico.
 	*/
	public Afiliado getAfiliado() {
		return afiliado;
	}

	/**
	 * @return Una lista con los Datos Epidemiologicos del Afiliado Cronico.
	 */
	public List getDatosEpidemiologicos() {
		return datosEpidemiologicos;
	}
	
	/**
	 * @return Una lista con los Diagnosticos del Afiliado Cronico.
	 */
	public List getDiagnosticos() {
		return diagnosticos;
	}

	/**
	 * @return el medico tratante del Afiliado Cronico
	 */
	public MedicoTratante getMedicoTratante() {
		return medicoTratante;
	}
	
	/**
	 * @return el Tramite asociado con el Afiliado Cronico
	 */
	public Tramite getTramite() {
		return tramite;
	}
	
	/**
	 * @param afili El Afiliado a designar como cronico
	 */
	public void setAfiliado(Afiliado afili) {
		this.afiliado = afili;
	}
	
	/**
	 * @param list Una lista con Datos Epidemiologicos asignados al Afiliado Cronico
	 */
	public void setDatosEpidemiologicos(List list) {
		datosEpidemiologicos = list;
	}
	
	/**
	 * @param list Una lista con Diagnosticos asignados al Afiliado Cronico.
	 */
	public void setDiagnosticos(List list) {
		diagnosticos = list;
	}
	
	/**
	 * @param tratante el Medico Tratante del Afiliado Cronico.
	 */
	public void setMedicoTratante(MedicoTratante tratante) {
		medicoTratante = tratante;
	}
	
	/**
	 * @param tram el Tramite asociado con la designacion 
	 * de un Afiliado como Cronico.
	 */
	public void setTramite(Tramite tram) {
		this.tramite = tram;
	}
	
	/**
	 * True si es actualizacion
	 * @return true or false
	 */
	public boolean isActualizacion() {
		boolean retorno = false;
		if (afiliado != null) {
			retorno = afiliado.isActualizacion();
		} 
		return  retorno;
	}
	
	/**
	 * Retorna fecha de carga del Afiliado Cronico
	 * @return long con fecha
	 */
	public long getFechaCarga() {
		return fechaCarga;
	}

	/**
	 * Setea fecha de carga con el Afiliado Cronico
	 * @param l
	 */
	public void setFechaCarga(long l) {
		fechaCarga = l;
	}
	
	/**
	 * Retorna fecha de carga String
	 * @return String con fecha
	 */
	public String getFechaCargaDDMMYYYY() {
		try {
			if (getFechaCarga() < 0) {
				return sdfDDMMYYYY.format(new Date());				

			} else if (getFechaCarga() == 0) {
				return "";				
				
			} else {
				return sdfDDMMYYYY.format(sdfYYYYMMDD.parse("" + getFechaCarga()));				
			}
						
		} catch (ParseException p ) {
			return "##/##/####";
		}
	}
	
	/**
	 * Retorna true si es un hijo
//	 * @param afiliado
	 * @return true o false
	 */
	public boolean isSon() {
	    boolean respuesta = false;
	    try {
	        long now = Long.parseLong(sdfYYYYMMDD.format(new Date()));
	        long fecha = Long.parseLong(
	                		StringUtils.replaceChars(
	                		        afiliado.getFechaNacimiento(), "-",""))
	                		        	+ 10000l;
	        respuesta = (fecha > now);
	        
	    } catch (Exception e) {
	        /* nothing todo */
	    }
	    return respuesta;
	}
	
	/**
	 * Retorna true si es Menor de 8 anos
//	 * @param afiliado
	 * @return true o false
	 */
	public boolean isMenor() {
	    boolean respuesta = false;
	    if (!this.isSon()) {
		    try {
		        long now = Long.parseLong(sdfYYYYMMDD.format(new Date()));
		        long fecha = Long.parseLong(
		                		StringUtils.replaceChars(
		                		        afiliado.getFechaNacimiento(), "-",""))
		                		        	+ 80000l;
		        respuesta = (fecha > now);
		        
		    } catch (Exception e) {
		        /* nothing todo */
		    }
	    }
	    return respuesta;
	}	
	
    /**
     * @return Returns the pmiHijo.
     */
    public Diagnostico getPmiHijo() {
        return pmiHijo;
    }
    
    /**
     * @param pmiHijo The pmiHijo to set.
     */
    public void setPmiHijo(Diagnostico pmiHijo) {
        this.pmiHijo = pmiHijo;
    }
    /**
     * @return Returns the pmi.
     */
    public Diagnostico getPmi() {
        return pmi;
    }
    /**
     * @param pmi The pmi to set.
     */
    public void setPmi(Diagnostico pmi) {
        this.pmi = pmi;
        this.setActualizacionPMI((pmi!=null));
    }
    
    /**
     * @return Returns the actualizacionPMI.
     */
    public boolean isActualizacionPMI() {
        return actualizacionPMI;
    }
    /**
     * @param actualizacionPMI The actualizacionPMI to set.
     */
    public void setActualizacionPMI(boolean actualizacionPMI) {
        this.actualizacionPMI = actualizacionPMI;
    }
    
    /**
     * Retorna si el afiliado esta de baja
     * @return true o false
     */
	public boolean isAfiliadoBaja() {
	    boolean retorno = false;
	    
	    try {
	        Date now = new Date();
		    
		    if (!afiliado.getFechaBajaDDMMYYYY().equals(Constantes.FECHA_NULL) 
		            && GeneralHelper.parseDateToInt(afiliado.getFechaBajaDDMMYYYY()) 
		            	<= GeneralHelper.parseDateToInt(now) ) {
		        retorno = true;
			}
		    
	    } catch (ParseException p) {
	        logger.error(p.getMessage(), p);
	        retorno = true;
	    }
	    return retorno;
	}
	
	/**
	 * Retorna si el Afiliado tiene datos para visualizar
	 * @return true or false
	 */
	public boolean isCronicoPMI() {
        return this.getAfiliado().isActualizacion()
				|| this.getPmi() != null 
				|| this.getPmiHijo() != null;
	}

    /**
     * Retorna si el afiliado esta de baja y tiene PMI o Cronico
     * @return true o false
     */
	public String isAfiliadoReadOnly() {
	    if (this.isAfiliadoBaja() && this.isCronicoPMI()) {
	        return "S";
	    } else {
	        return "N";
	    }
	}	
}
