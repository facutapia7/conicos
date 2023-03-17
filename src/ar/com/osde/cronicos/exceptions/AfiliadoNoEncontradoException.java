/*
 * Created on 17/08/2004
 */
package ar.com.osde.cronicos.exceptions;

import ar.com.osde.framework.exception.BaseException;

/**
 * Exception para el caso de que no se encuentre el Afiliado buscado
 * @author Diego Naya
 * Created on 17/08/2004
 */
public class AfiliadoNoEncontradoException extends BaseException {
 public AfiliadoNoEncontradoException(){
 	super();
 }
 
 public AfiliadoNoEncontradoException(String msg){
 	super(msg);
 }
}
