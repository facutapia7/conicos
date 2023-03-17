/**
 * Created on 01/12/2004
 *
 */
package ar.com.osde.cronicos.beans;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author PG26589512
 *
 */
public class ListaDeAfiliados extends LinkedList implements Serializable {

	private boolean filtrado;
	
	/**
	 * Crea Array List
	 * @param lista Lista
	 * @param filtrado Si la lista se filtro o es completa
	 */
	public ListaDeAfiliados(List lista, boolean filtrado) {
		super(lista);
		this.filtrado = filtrado; 
	}
	
	/**
	 * @return
	 */
	public boolean isFiltrado() {
		return filtrado;
	}

	/**
	 * @param b
	 */
	public void setFiltrado(boolean b) {
		filtrado = b;
	}
}
