/*
 * Created on Aug 17, 2004
 *
 */
package ar.com.osde.cronicos.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import ar.com.osde.cronicos.helper.GeneralHelper;

/** 
* class description 
*  
* @author	Adrian C. Martinez 
*  
* @version	Aug 17, 2004 
*  
* @see	
*/
public class AfiliadoForm extends ActionForm {
	private static final long serialVersionUID = 1L;
	
	private String filial       = "";
	private String afiliado     = "";
	private String beneficiario = "";
	
	/**
	 * Valida que el afiliado exista
	 * 
	 */
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest req) {
		
		if( ! GeneralHelper.isNumber(this.filial)   
			|| ! GeneralHelper.isNumber(this.afiliado) 
			|| ! GeneralHelper.isNumber(this.beneficiario)) {
	    	
			ActionErrors aes = new ActionErrors();
			aes.add(ActionErrors.GLOBAL_ERROR, new ActionError("jsp.nuevoTramite.afiliadoInvalido"));
			
			return aes;
		}
		
		return null;
	}
	
	/**
	 * @return
	 */
	public String getAfiliado() {
		return afiliado;
	}

	/**
	 * @return
	 */
	public String getBeneficiario() {
		return beneficiario;
	}

	/**
	 * @return
	 */
	public String getFilial() {
		return filial;
	}

	/**
	 * @param string
	 */
	public void setAfiliado(String string) {
		afiliado = string;
	}

	/**
	 * @param string
	 */
	public void setBeneficiario(String string) {
		beneficiario = string;
	}

	/**
	 * @param string
	 */
	public void setFilial(String string) {
		filial = string;
	}
}
