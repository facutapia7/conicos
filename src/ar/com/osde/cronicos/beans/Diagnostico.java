/*
 * Created on 17/08/2004
 */
package ar.com.osde.cronicos.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import ar.com.osde.cronicos.Constantes;
import ar.com.osde.cronicos.helper.GeneralHelper;
import ar.com.osde.framework.config.Configuration;


/**
 * Esta clase representa un Diagnostico 
 * (también llamado Patología dentro de este dominio)
 * @author Diego Naya
 * @version 1.0
 * Created on 17/08/2004
 */
public class Diagnostico extends PatologiaBase {

    /**
     * Dias para agregarla a la fecha Hasta
     */
	private static final int FEHCA_PMI = GeneralHelper.toInt( 
		    Configuration.getInstance().getParameter(Constantes.FEHCA_PMI));
	/**
	 * Formatos de Fecha
	 */
	private static final SimpleDateFormat DMY = new SimpleDateFormat("dd/MM/yyyy");
	private static final SimpleDateFormat YMD = new SimpleDateFormat("yyyyMMdd");
	
	/**
	 * Constructor base.
	 *
	 */
	public Diagnostico() {
		super();
	}
	/**
	 * 
	 * @param cod El Codigo del Diagnostico.
	 * @param descrip La Descripcion del Diagnostico.
	 */
	public Diagnostico(String cod, String descrip) {
		super();
		setCodigo(cod);
		setDescripcion(descrip);
	}
	/**
	 * 
	 * @param cod El Codigo del Diagnostico
	 */
	public Diagnostico(String cod) {
		super();
		setCodigo(cod);
	}

	/**
	 * @param obj El objeto a comparar
	 * @return verdadero si los objetos son iguales, falso si no lo es
	 * Si el objeto es de tipo Diagnostico y tiene el mismo codigo,
	 *  entonces son iguales.
	 */
	public boolean equals(Object obj) {
		boolean retorno = false;
		if (obj instanceof Diagnostico) {
			if (this.getCodigo().equals(((Diagnostico) obj).getCodigo())) {
				retorno = true;
			}
		}
		return retorno;
	}
	
	/**
	 * Agrega fecha hasta (fecha + n dias)
	 * @param fecha
	 * @throws ParseException
	 */
	public void addFechaHasta(String fecha) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(DMY.parse(fecha));
        c.add(Calendar.DATE, FEHCA_PMI);
        
        long fechaHasta = GeneralHelper.toLong(YMD.format(c.getTime()));

        this.setFechaHasta(fechaHasta);
	}

	/**
	 * Agrega fecha hasta (fecha + n dias)
	 * @param fecha
	 * @throws ParseException
	 */
	public String subsFechaHasta() {
	    
	    try {
	        Calendar c = Calendar.getInstance();
	        c.setTime(YMD.parse("" + this.getFechaHasta()));
	        c.add(Calendar.DATE, FEHCA_PMI * -1);
	        
			return sdfDDMMYYYY.format(c.getTime());
	    } catch (ParseException p) {
	        return "##/##/####";
	    }
	}
	
		
}
