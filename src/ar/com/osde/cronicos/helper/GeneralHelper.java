/*
 * Created on Aug 18, 2004
 *
 */
package ar.com.osde.cronicos.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.osde.cronicos.beans.Afiliado;

/** 
* class description 
*  
* @author	Ricardo Stamato (Accenture) 
*  
* @version	Aug 18, 2004 
*  
* @see	
*/
public class GeneralHelper {

	public static boolean isNumber(String value) {
		boolean isOk = (value != null && value.trim().length() > 0 );
		
		if (isOk) {
			char digit [] = value.trim().toCharArray();
			for (int i = 0; i < digit.length; i++) {
				if (! Character.isDigit(digit[i])) {
					return false;
				}
			}
		}   
		return isOk;
	}
	
	public static int toInt(String value) {
		int i = 0;
		
		if (isNumber(value)) {
			return Integer.parseInt(value.trim());
		}
		
		return i;
	}

	public static long toLong(String value) {
		long l = 0;
		
		if (isNumber(value)) {
			return Long.parseLong(value);
		}
		
		return l;
	}

	/**
	 * Pasa un numerico YYYYMMDD a un Date
	 * @param date
	 * @return
	 */
	public static Date parseDateASToDate(int date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.parse(String.valueOf(date));			
	}
	/**
	 * Pasa un date a un String DD/MM/YYYY
	 * @param date
	 * @return
	 */
	public static String parseDateToString(Date date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(date);			
	}

/**
	 * Pasa un date a un integer yyyyMMdd
	 * @param date
	 * @return
	 */
	public static int parseDateToInt(Date date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return Integer.parseInt(sdf.format(date));			
	}

	/**
	 * Pasa un String dd/MM/yyyy a formato yyyyMMdd
	 * @param date
	 * @return
	 */
	public static int parseDateToInt(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return parseDateToInt(sdf.parse(date));			
	}
	
	public static Date parseStringToDate(String date) throws ParseException{
		
		int intDate = parseDateToInt(date);
		return parseDateASToDate(intDate);
		
	}
	
	/**
	 * Obtiene los anos
	 * @param date fecha de nacimiento
	 * @return int
	 */	
	public static int getOldYear(Date date) {
		Calendar nacimiento = GregorianCalendar.getInstance();
		nacimiento.setTime(date);
		
		Calendar now = GregorianCalendar.getInstance();
		now.add(Calendar.YEAR, -nacimiento.get(Calendar.YEAR));
		now.add(Calendar.MONTH, -nacimiento.get(Calendar.MONTH));
		now.add(Calendar.DAY_OF_MONTH, -nacimiento.get(Calendar.DAY_OF_MONTH));

		return now.get(Calendar.YEAR);
	}

	/**
	 * 
	 * @param value objeto a completar.
	 * @param mask  mascara con lenght 
	 * @return un merge entre la mascara y el objeto 
	 * reemplazando bancos a la izquierda.
	 */
	public static String maskString(Object value, String mask) {
		String valueString = String.valueOf(value);
		String retorno = valueString;

		if (mask.length() > valueString.length()) {
			retorno = mask + valueString;
			retorno = retorno.substring(retorno.length() - mask.length());
		}
		return retorno;
	}
	/**
	* Transforma el valor de entrada en otro con una cantidad de ceros
	* definida en mask
	* @param value el valor a completar con ceros
	* @param mask la cantidad de ceros
	* @return un String con las mascara mas el numero.
	*/
	public static String completarCon( int value, String mask){
		String valueString = String.valueOf(value);
	 	String retorno     = valueString;
	 	
	 	if (mask.length() > valueString.length()) {
	 		retorno = mask + valueString;
	 		retorno = retorno.substring(retorno.length() - mask.length());
	 	}
	 	
		Log log = LogFactory.getLog(Afiliado.class);
		log.debug("Completar con 0.. " + retorno);
		return retorno;
	}

	
}
