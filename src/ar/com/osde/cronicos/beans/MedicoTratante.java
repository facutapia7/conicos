/*
 * Created on 17/08/2004
 */
package ar.com.osde.cronicos.beans;

import ar.com.osde.framework.entity.VersionedEntityObject;

/**
 * Esta clase representa un Medico Tratante de un Afiliado Cronico.
 * @author Diego A. Naya
 * @version 1.0
 * Created on 17/08/2004
 * Update Adrian C. Martinez 03/11/2004
 */
public class MedicoTratante extends VersionedEntityObject {
 /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MedicoTratante [codigo=" + codigo + ", " + "\n" 
				+ (nombre != null ? "nombre=" + nombre + ", " : "") + "\n" 
				+ (tipoMatricula != null ? "tipoMatricula=" + tipoMatricula	+ ", " : "")  + "\n" 
				+ "numeroMatricula=" + numeroMatricula	+ ", " + "\n" 
				+ (provincia != null ? "provincia=" + provincia + ", " : "") + "\n" 
				+ "numeroPrestador=" + numeroPrestador + ", " + "\n" 
				+ (telefono != null ? "telefono=" + telefono : "") + "]"; 
	}

 private long codigo;
 private String nombre;
 private String tipoMatricula;
 private long numeroMatricula;
 private String provincia;
 private int numeroPrestador;
 private String telefono;
 
/**
 * @return El Codigo del Medico Tratante.
 */
public long getCodigo() {
	return codigo;
}

/**
 * @return El Nombre y Apellido del Medico Tratante.
 */
public String getNombre() {
	return nombre;
}


/**
 * @return La Provincia donde reside el Medico Tratante.
 */
public String getProvincia() {
	return provincia;
}

/**
 * @return El Telefono del Medico Tratante.
 */
public String getTelefono() {
	return telefono;
}

	/**
 	* @return El tipo de Matricula en un caracter, ejemplo 'M'
 	*/
	public String getTipoMatricula() {
		return tipoMatricula;
	}

/**
 * @param i Codigo del Medico Tratante
 */
public void setCodigo(long cod) {
	codigo = cod;
}

/**
 * @param string El Nombre del Medico Tratante.
 */
public void setNombre(String string) {
	nombre = string;
}


/**
 * @param string El Codigo de Provincia
 */
public void setProvincia(String string) {
	provincia = string;
}

/**
 * @param string El telefono, sin ningun tipo de formato en especial
 */
public void setTelefono(String string) {
	telefono = string;
}

	/**
 	* @param string El tipo de matricula, debe ser un caracter solo
 	*/
	public void setTipoMatricula(String string) {
		tipoMatricula = string;
	}

/**
 * @return
 */
public long getNumeroMatricula() {
	return numeroMatricula;
}

/**
 * @return
 */
public int getNumeroPrestador() {
	return numeroPrestador;
}

/**
 * @param i
 */
public void setNumeroMatricula(long i) {
	numeroMatricula = i;
}

/**
 * @param i
 */
public void setNumeroPrestador(int i) {
	numeroPrestador = i;
}

}
