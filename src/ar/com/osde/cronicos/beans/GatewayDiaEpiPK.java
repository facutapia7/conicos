/*
 * Created on Nov 2, 2004
 *
 */
package ar.com.osde.cronicos.beans;

import java.io.Serializable;

/** 
* class description 
*  
* @author	Adrian C. Martinez
*  
* @version	1.0.0
*  
* @date		Nov 2, 2004 
*/
public class GatewayDiaEpiPK implements Serializable {
	
	private String nrobp;
	private String codigo;
	private long   vigDesde;	
	
	/**
	 * @return true si el objeto coincide en cada property
	 * con el objeto enviado por parametro. Falso si no coincide.
	 * @param obj el objeto a verificar la igualdad.
	 */
	public boolean equals(Object obj){
		if(obj == this){
			return true;
		}
		
		if(obj instanceof GatewayDiaEpiPK){
			GatewayDiaEpiPK ope = (GatewayDiaEpiPK)obj;
			if(ope.getNrobp().equals(this.getNrobp())
				&& ope.getCodigo().equals(this.getCodigo())
				&& ope.getVigDesde() == this.getVigDesde()){
					return true;
				}
		}
		
		return false;
	}

	/**
	 * @return la orden preexistente en formato String
	 */
	public String toString(){
		return "nrobp: " + nrobp + "\n" 
				+ "codigo: " + codigo + "\n" 
				+ "vigDesde: " + vigDesde;
	}
	
	/**
	 * @return
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @return
	 */
	public String getNrobp() {
		return nrobp;
	}

	/**
	 * @return
	 */
	public long getVigDesde() {
		return vigDesde;
	}

	/**
	 * @param string
	 */
	public void setCodigo(String string) {
		codigo = string;
	}

	/**
	 * @param string
	 */
	public void setNrobp(String string) {
		nrobp = string;
	}

	/**
	 * @param l
	 */
	/*
	public void setVigDesde(long l) {
		vigDesde = l;
	}
*/

	public void setVigDesde(long hasta)
	{
		this.vigDesde=hasta;
		String cantidadFecha;

		cantidadFecha=String.valueOf(vigDesde);
		if(!(cantidadFecha.length()==8))
			{
			throw new IllegalArgumentException("El texto ingresado no es una fecha válida, verificar cantidad de caracteres");
			}
		
		vigDesde=Long.parseLong(cantidadFecha);
	}

	/**
	* HasCode
	*/
	public int hashCode() {
		return (nrobp + codigo + vigDesde).hashCode(); 
	}
}
