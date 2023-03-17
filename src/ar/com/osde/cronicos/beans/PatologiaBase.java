/*
 * Created on Nov 3, 2004
 *
 */
package ar.com.osde.cronicos.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import ar.com.osde.framework.entity.VersionedEntityObject;

/** 
* class description 
*  
* @author	Adrian C. Martinez
*  
* @version	1.0.0
*  
* @date		Nov 3, 2004 
*/
public class PatologiaBase extends VersionedEntityObject  {
	
	private static final long serialVersionUID = 384650648006103691L;
	
	protected static final SimpleDateFormat sdfYYYYMMDD = new SimpleDateFormat("yyyyMMdd"); 
	protected static final SimpleDateFormat sdfDDMMYY = new SimpleDateFormat("dd/MM/yy");
	protected static final SimpleDateFormat sdfDDMMYYYY = new SimpleDateFormat("dd/MM/yyyy");
	
	private String codigo;
	private String descripcion;
	private MedicoTratante medicoTratante;
	private long fechaDesde;
    private long fechaHasta;
    
	/**
	 * Constructor base.
	 *
	 */
	public PatologiaBase() {
		super();
	}

	/**
	 * @return El codigo.
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @return La descripción.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param cod El codigo.
	 */
	public void setCodigo(String cod) {
		codigo = cod;
	}

	/**
	 * @param string La descripción.
	 */
	public void setDescripcion(String string) {
		descripcion = string;
	}

	/**
	 * @param obj El objeto a comparar
	 * @return verdadero si los objetos son iguales, falso si no lo es
	 * Si el objeto es del tipo y tiene el mismo codigo,
	 * entonces son iguales.
	 */
	public boolean equals(Object obj) {
		boolean retorno =  (obj instanceof PatologiaBase) && (this.getCodigo().equals(((PatologiaBase) obj).getCodigo()));
		return retorno;
	}
	/**
	 * @return el Hash, en este caso el codigo.
	 */
	public int hashCode() {
		return Integer.parseInt(this.codigo);
	}

	/**
	 * @return El Codigo formato String.
	 */
	public String toString() {
		return String.valueOf(this.getCodigo());
	}
	
	/**
	 * @return
	 */
	public long getFechaDesde() {
		return fechaDesde;
	}

	/**
	 * @param l
	 */
	public void setFechaDesde(long l) {
		fechaDesde = l;
	}

	/**
	 * @return
	 */
	public String getFechaDesdeDDMMYYYY() {
			if (getFechaDesde() <= 0) {
				return "";				
				
			}
		try {
			return sdfDDMMYYYY.format(sdfYYYYMMDD.parse("" + getFechaDesde()));
						
		} catch (ParseException p ) {
			return "##/##/####";
		}		
	}
	
	/**
	 * @return
	 */
	public String getFechaHastaDDMMYYYY() {
			if (getFechaHasta() <= 0) {
				return "";				
				
			}
		try {
			return sdfDDMMYYYY.format(sdfYYYYMMDD.parse("" + getFechaHasta()));
						
		} catch (ParseException p ) {
			return "##/##/##";
		}		
	}	
	
	/**
	 * @return
	 */
	public MedicoTratante getMedicoTratante() {
		return medicoTratante;
	}

	/**
	 * @param tratante
	 */
	public void setMedicoTratante(MedicoTratante tratante) {
		medicoTratante = tratante;
	}
	
    /**
     * @return Returns the fechaHasta.
     */
    public long getFechaHasta() {
        return fechaHasta;
    }
    /**
     * @param fechaHasta The fechaHasta to set.
     */
    /*
    public void setFechaHasta(long fechaHasta) {
        this.fechaHasta = fechaHasta;
    }
    */
    
    
	public void setFechaHasta(long hasta) {
		if (String.valueOf(hasta).length() != 8) {
			throw new IllegalArgumentException("El texto ingresado no es una fecha válida, verificar cantidad de caracteres");
			}
		
		fechaHasta = hasta;
	}
	
}
