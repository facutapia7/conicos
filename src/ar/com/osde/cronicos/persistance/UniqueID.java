/*
 * Created on Nov 4, 2004
 *
 */
package ar.com.osde.cronicos.persistance;

/** 
* Obtiene ID unico (Solo en Single JVM)
*  
* @author	Adrian C. Martinez
*  
* @version	1.0.0
*  
* @date		Nov 4, 2004 
*/
public class UniqueID {
	static long current= System.currentTimeMillis();
	
	static public synchronized long get(){
		return current++;
	}
}
