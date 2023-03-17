/*
 * Created on 17/08/2004
 */
package ar.com.osde.cronicos.beans;


/**
 * Esta clase representa un Dato Epidemiologico que se debe asignar a un Afiliado Cronico, si
 * este debe contener el Dato.
 * @version 1.0
 * @author Diego Naya
 * Created on 17/08/2004
 */
public class DatoEpidemiologico extends PatologiaBase {

	/**
	 * Constructor base
	 */
	public DatoEpidemiologico() {
		super();
	}
	
	/**
	 * Constructor base.
	 * @param cod El codigo del Dato Epidemiologico.
	 */
	public DatoEpidemiologico(String cod) {
		super();
		setCodigo(cod);
	}

	/**
 	* @param obj un Objeto
 	* @return verdado si el otro obj. es un dato epid
 	*  y tiene el mismo codigo que esta instancia.
 	*/
	public boolean equals(Object obj){
		boolean retorno = false;
		if(obj instanceof DatoEpidemiologico){
			if (this.getCodigo().equals(((DatoEpidemiologico)obj).getCodigo())){
				retorno = true;
			}
		}
		return retorno;
	}
}
