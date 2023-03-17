package ar.com.osde.cronicos.exceptions;

@SuppressWarnings("serial")
public class PatologiaServiceException extends Exception {

	 public PatologiaServiceException(){
		 	super();
		 }
		 
		 public PatologiaServiceException(String msg){
		 	super(msg);
		 }
		 
		 public PatologiaServiceException(String msg, Exception e){
			 	super(msg,e);
			 }
}

