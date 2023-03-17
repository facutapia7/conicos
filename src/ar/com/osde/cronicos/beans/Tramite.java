/*
 * Created on 17/08/2004
 */
package ar.com.osde.cronicos.beans;

import java.text.SimpleDateFormat;

import ar.com.osde.framework.entity.VersionedEntityObject;

/**
 * Esta clase representa un Tramite de ingreso de un Afiliado Crónico.
 * @author Diego A. Naya
 * Created on 17/08/2004
 */
public class Tramite extends VersionedEntityObject {
private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	return "Tramite [codigo=" + codigo  + "\n" 
			+ ", fechaCarga=" + fechaCarga + ", " + "\n" 
			+ (usuario != null ? "usuario=" + usuario + ", " : "")  + "\n" 
			+ "filial="	+ filial + ", capReceptor=" + capReceptor + ", " + "\n" 
			+ (sdf != null ? "sdf=" + sdf : "") + "]";
}

	private int codigo;
	private long fechaCarga;
	private String usuario;
	private int filial;
	private int capReceptor;
	SimpleDateFormat sdf = new SimpleDateFormat(); 
	 
 
 
/**
 * @return
 */
public int getCapReceptor() {
	return capReceptor;
}

/**
 * @return
 */
public int getCodigo() {
	return codigo;
}



/**
 * @return
 */
public int getFilial() {
	return filial;
}

/**
 * @return
 */
public String getUsuario() {
	return usuario;
}

/**
 * @param i
 */
public void setCapReceptor(int i) {
	capReceptor = i;
}

/**
 * @param i
 */
public void setCodigo(int i) {
	codigo = i;
}


/**
 * @param i
 */
public void setFilial(int i) {
	filial = i;
}

/**
 * @param string
 */
public void setUsuario(String string) {
	usuario = string;
}

/**
 * @return
 */
public long getFechaCarga() {
	return fechaCarga;
}

/**
 * @param l
 */
public void setFechaCarga(long l) {
	fechaCarga = l;
}


}
