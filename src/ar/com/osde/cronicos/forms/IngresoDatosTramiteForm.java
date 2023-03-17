/*
 * Created on Aug 17, 2004
 *
 */
package ar.com.osde.cronicos.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import ar.com.osde.cronicos.Constantes;
import ar.com.osde.cronicos.beans.AfiliadoCronico;

/** 
* Action Form para el ingreso de datos del formulario principal.
*  
* @author	Adrian C. Martinez 
*  
* @version	Aug 17, 2004 
*  
* @see	
*/
public class IngresoDatosTramiteForm extends ActionForm {

	private static final Log logger = LogFactory.getLog(IngresoDatosTramiteForm.class);
	
	private String[] diagnosticos = new String[0];
	private String[] datosEpidemiologicos = new String[0];

	private String prestadorOsde = "off";

	private String apellidoNombre = "";
	private String tipoDeMatricula = "";
	private String numeroDeMatricula = "";
	private String codigoPrestador = "";
	private String provincia = "";
	private String telefono = "";
	private String button = "";
	private String anular = "";
	private String pmi = "off";
	private String fechaProbableDeParto = "";
	private String fechaVigenciaDesde = "";	
	private String esHijo = "";	
	private String readOnly = "N";
	
	private String fechaAlta = "";
	private String fechaBaja = "";
	private String plan = "";
	private String mesesReconocidos = "";
	private String carencias = "";
	private String parentesco = "";
	private String tipoAfiliado = "";
	private String altaFormulario = "";
	
	
	/**
	 * Limpia los check. Si desmarco se envian en el request !!!
	 * 
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		prestadorOsde = "off";
		pmi = "off";
		datosEpidemiologicos = new String[0];
		diagnosticos = new String[0];
		button = "";
	}

	/**
	 * Valida que el afiliado exista
	 * 
	 */
	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest req) {
		ActionErrors aes = new ActionErrors();
		
		AfiliadoCronico afiliadoCronico = (AfiliadoCronico)req.getSession().getAttribute(Constantes.AFILIADO);
		
		//version-1.2.7 - INICIO
		logger.info("Validate - INICIAL");
		logger.info(afiliadoCronico.toString());
		logger.info("Validate - FIN");
		//version-1.2.7 - FIN
		
		if (afiliadoCronico != null && afiliadoCronico.getAfiliado() != null) {
		    
		    if (!"anular".equals(anular)) {
			    if ("".equals(fechaProbableDeParto) && !afiliadoCronico.isActualizacionPMI()) {
			    
					if (this.noHayDiagnosticos(afiliadoCronico)) {
						aes.add(
								ActionErrors.GLOBAL_ERROR,
								new ActionError("jsp.ingresoDatosTramiteForm.ingresoDeDiagnosticos"));		                
			        }
			    	
					if (prestadorOsde.equals("on")
						&& codigoPrestador.trim().equals("")
						&& numeroDeMatricula.trim().equals("")) {
						aes.add(
							ActionErrors.GLOBAL_ERROR,
							new ActionError("jsp.ingresoDatosTramiteForm.ingresoDePrestador"));
					}
		
					if (prestadorOsde.equals("off") && apellidoNombre.equals("")) {
						aes.add(
							ActionErrors.GLOBAL_ERROR,
							new ActionError("jsp.ingresoDatosTramiteForm.ingresoDeNombre"));
					}
					
			    } else {
			    	// Si se ingreso Datos epi, pero no se cargo diagnosticos
			        if (!this.noHayDatoE() && this.noHayDiagnosticos(afiliadoCronico)) {
						aes.add(
								ActionErrors.GLOBAL_ERROR,
								new ActionError("jsp.ingresoDatosTramiteForm.ingresoDeDiagnosticos"));		                
			        }
			        
			        // Si no hay diagnosticos y se ingreso medico
			        if (this.noHayDiagnosticos(afiliadoCronico) && !noHayMedico()) {
						aes.add(
								ActionErrors.GLOBAL_ERROR,
								new ActionError("jsp.ingresoDatosTramiteForm.ingresoDeDiagnosticos"));		                
			        }
			        // Si hay diagnosticos y no se ingreso medico
			        if (!this.noHayDiagnosticos(afiliadoCronico) && this.noHayMedico()) {		        
	                    aes.add(
								ActionErrors.GLOBAL_ERROR,
								new ActionError("jsp.ingresoDatosTramiteForm.ingresoDePrestador"));
			        }		        
			    }
		    }

			if (!aes.isEmpty()) {
		        return aes;
			}
		}

		return null;
	}

	/**
	 * Retorna si no Hay Medico
	 * @return
	 */
	private boolean noHayMedico() {
		return (codigoPrestador.trim().equals("")
				&& numeroDeMatricula.trim().equals("")
				&& apellidoNombre.equals(""));
	}

	/**
	 * Verifica si No Hay Diagnosticos
	 * @param afiliadoCronico
	 * @return
	 */
	private boolean noHayDiagnosticos(
	        AfiliadoCronico afiliadoCronico) {
	    return diagnosticos.length == 0 && (! afiliadoCronico.isActualizacion());	    
	}
	
	/**
	 * Verifica si no Hay Datos Epid.
	 *  @return true o false
	 */
	private boolean noHayDatoE() {
	    return (datosEpidemiologicos.length == 0); 
	}
	
	/**
	 * 
	 * @return
	 */
	public String[] getDiagnosticos() {
		return diagnosticos;
	}
	/**
	 * 
	 * @param strings
	 */
	public void setDiagnosticos(String[] strings) {
		diagnosticos = strings;
	}
	/**
	 * 
	 * @return
	 */
	public String[] getDatosEpidemiologicos() {
		return datosEpidemiologicos;
	}
	/**
	 * 
	 * @param strings
	 */
	public void setDatosEpidemiologicos(String[] strings) {
		datosEpidemiologicos = strings;
	}
	/**
	 * 
	 * @return
	 */
	public String getNumeroDeMatricula() {
		return numeroDeMatricula;
	}
	/**
	 * 
	 * @return
	 */
	public String getPrestadorOsde() {
		return prestadorOsde;
	}
	/**
	 * 
	 * @return
	 */
	public String getProvincia() {
		return provincia;
	}
	/**
	 * 
	 * @return
	 */
	public String getTelefono() {
		return telefono.trim();
	}
	/**
	 * 
	 * @return
	 */
	public String getTipoDeMatricula() {
		return tipoDeMatricula;
	}
	/**
	 * 
	 * @param string
	 */
	public void setNumeroDeMatricula(String string) {
		numeroDeMatricula = string;
	}
	/**
	 * 
	 * @param string
	 */
	public void setPrestadorOsde(String string) {
		prestadorOsde = string;
	}
	/**
	 * 
	 * @param string
	 */
	public void setProvincia(String string) {
		provincia = string;
	}
	/**
	 * 
	 * @param string
	 */
	public void setTelefono(String string) {
		telefono = string;
	}
	/**
	 * 
	 * @param string
	 */
	public void setTipoDeMatricula(String string) {
		tipoDeMatricula = string;
	}
	/**
	 * 
	 * @return
	 */
	public String getApellidoNombre() {
		return apellidoNombre.trim();
	}
	/**
	 * 
	 * @return el Codigo del Prestador 
	 */
	public String getCodigoPrestador() {
		return codigoPrestador;
	}
	/**
	 * 
	 * @param string
	 */
	public void setApellidoNombre(String string) {
		apellidoNombre = string;
	}
	/**
	 * 
	 * @param string
	 */
	public void setCodigoPrestador(String string) {
		codigoPrestador = string;
	}

	/**
	 * @return
	 */
	public String getButton() {
		return button;
	}

	/**
	 * @param string
	 */
	public void setButton(String string) {
		button = string;
	}

	/**
	 * @return
	 */
	public String getAnular() {
		return anular;
	}

	/**
	 * @param string
	 */
	public void setAnular(String string) {
		anular = string;
	}

    /**
     * @return Returns the pmi.
     */
    public String getPmi() {
        return pmi;
    }
    /**
     * @param pmi The pmi to set.
     */
    public void setPmi(String pmi) {
        this.pmi = pmi;
    }
    /**
     * @return Returns the fechaProbableDeParto.
     */
    public String getFechaProbableDeParto() {
        return fechaProbableDeParto;
    }
    /**
     * @param fechaProbableDeParto The fechaProbableDeParto to set.
     */
    public void setFechaProbableDeParto(String fechaProbableDeParto) {
        this.fechaProbableDeParto = fechaProbableDeParto;
    }
    /**
     * @return Returns the esHijo.
     */
    public String getEsHijo() {
        return esHijo;
    }
    /**
     * @param esHijo The esHijo to set.
     */
    public void setEsHijo(String esHijo) {
        this.esHijo = esHijo;
    }
    
    /**
     * @return Returns the fechaVigenciaDesde.
     */
    public String getFechaVigenciaDesde() {
        return fechaVigenciaDesde;
    }
    /**
     * @param fechaVigenciaDesde The fechaVigenciaDesde to set.
     */
    public void setFechaVigenciaDesde(String fechaVigenciaDesde) {
        this.fechaVigenciaDesde = fechaVigenciaDesde;
    }
    
    
	/**
	 * @return Returns the readOnly.
	 */
	public String getReadOnly() {
		return readOnly;
	}
	/**
	 * @param readOnly The readOnly to set.
	 */
	public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}


	/**
	 * @return Returns the altaFormulario.
	 */
	public String getAltaFormulario() {
		return altaFormulario;
	}
	/**
	 * @param altaFormulario The altaFormulario to set.
	 */
	public void setAltaFormulario(String altaFormulario) {
		this.altaFormulario = altaFormulario;
	}
	/**
	 * @return Returns the carencias.
	 */
	public String getCarencias() {
		return carencias;
	}
	/**
	 * @param carencias The carencias to set.
	 */
	public void setCarencias(String carencias) {
		this.carencias = carencias;
	}
	/**
	 * @return Returns the fechaAlta.
	 */
	public String getFechaAlta() {
		return fechaAlta;
	}
	/**
	 * @param fechaAlta The fechaAlta to set.
	 */
	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	/**
	 * @return Returns the fechaBaja.
	 */
	public String getFechaBaja() {
		return fechaBaja;
	}
	/**
	 * @param fechaBaja The fechaBaja to set.
	 */
	public void setFechaBaja(String fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	/**
	 * @return Returns the mesesReconocidos.
	 */
	public String getMesesReconocidos() {
		return mesesReconocidos;
	}
	/**
	 * @param mesesReconocidos The mesesReconocidos to set.
	 */
	public void setMesesReconocidos(String mesesReconocidos) {
		this.mesesReconocidos = mesesReconocidos;
	}
	/**
	 * @return Returns the parentesco.
	 */
	public String getParentesco() {
		return parentesco;
	}
	/**
	 * @param parentesco The parentesco to set.
	 */
	public void setParentesco(String parentesco) {
		this.parentesco = parentesco;
	}
	/**
	 * @return Returns the plan.
	 */
	public String getPlan() {
		return plan;
	}
	/**
	 * @param plan The plan to set.
	 */
	public void setPlan(String plan) {
		this.plan = plan;
	}
	/**
	 * @return Returns the tipoAfiliado.
	 */
	public String getTipoAfiliado() {
		return tipoAfiliado;
	}
	/**
	 * @param tipoAfiliado The tipoAfiliado to set.
	 */
	public void setTipoAfiliado(String tipoAfiliado) {
		this.tipoAfiliado = tipoAfiliado;
	}
}
