/*
 * Created on Nov 4, 2004
 *
 */
package ar.com.osde.cronicos.persistance;

/** 
* Interface para ConcurrencyProxy 
*  
* @author	Adrian C. Martinez
*  
* @version	1.0.0
*  
* @date		Nov 4, 2004 
*/
public interface IObjectConcurrency {
	
	/***
	 * Retorna ID del Objeto
	 * @return long
	 */
	public String getCheckID();
	/**
	 * Retorna el Valor de control
	 * @return long
	 */
	public Long getCheckValue();
	/**
	 * Setea el valor de control
	 * @param value Valor de control
	 */
	public void setCheckValue(Long value);
}
