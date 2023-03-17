package ar.com.osde.cronicos.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Validador {
	
	public static boolean islengthValido(String cadena, int longitud) {
		boolean ret = false;
		if (cadena.length() == longitud)
			ret = true;
		return ret;
	}
	public static boolean isNull(String cadena){
		boolean ret = false;
		if(cadena==null||cadena.trim().isEmpty())
			ret = true;
		return ret;	
	}

	public static boolean isFechaValida(Date fecha,FormatoFecha formatoFecha  ) {
		return toDate(fecha, formatoFecha);
	}
	public static boolean isSoloNumeros(String cadena){
		boolean ret = false;
		if(cadena.matches("[0-9]*"))
			ret = true;
		return ret;	
	}

	private static boolean toDate(Date fecha, FormatoFecha formatoFecha){
		    String cadenaFecha=covertFechaString(fecha,formatoFecha);
		    if(cadenaFecha==null)return false;
			SimpleDateFormat formato = new SimpleDateFormat(formatoFecha.getFormato());
			formato.setLenient(false);	
			try {
				@SuppressWarnings("unused")			
				Date fechaAEnviar = formato.parse(cadenaFecha);
			} catch (ParseException e) {
				return false;
			}
			return true;
		
	}
	
	public static String covertFechaString(Date fecha,FormatoFecha formatoFecha) {
		SimpleDateFormat formatoCorrecto= new SimpleDateFormat(
				formatoFecha.getFormato());
		String fechaAEnviar;
		formatoCorrecto.setLenient(false);	
		try {
		fechaAEnviar = formatoCorrecto.format(fecha);
		} catch (IllegalArgumentException e) {
		return null;
		}
		return fechaAEnviar.substring(0, 8);	
		}

}
