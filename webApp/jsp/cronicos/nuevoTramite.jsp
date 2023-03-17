<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/osde.tld" prefix="osde" %>

<%@ page language="java" %>

<html>
<head>
	<html:base/>
	<title>Carga de Patologías Crónicas</title>
	<meta http-equiv=Content-Type content="text/html; charset=iso-8859-1">
	
	<osde:load noCache="true"/>
	
	<link href='<html:rewrite page="/jsp/cronicos/styles/style.css"/>' type=text/css rel=stylesheet>
	<script language='javascript' src='<html:rewrite page="/jsp/cronicos/js/functions.js"/>'></script>
</head>

<body bgcolor="#FFFFFF">

<script language='javascript'>

function openConsulta(completeUrl,ancho,largo,wndName) {
	if ( String(wndName) == "undefined" ) {
    	wndName = "wndConsulta";
    }

	if ( String(ancho) == "undefined" ) {
    	ancho = 750;
    }

    if ( String(largo) == "undefined" ) {
		largo = 500;
    }
    
	var topPos = screen.availHeight/2 - largo/2; // center
	var leftPos = screen.availWidth/2 - ancho/2; // center
    var params = 'width=' + ancho +' , height=' + largo + ',left=' + leftPos + ', top=' + topPos + 
    			 ',location=no, directories=no, menubar=no, status=no, toolbar=no, scrollbars=yes, resizable=yes';
    			 
    valOpened = window.open(completeUrl, wndName, params);
	valOpened.focus();
}

	function ValidarDatos() {
		if (frmMain.filial.value.length == 0 ||
			frmMain.afiliado.value.length == 0 ||
			frmMain.beneficiario.value.length == 0) {
			
			alert("Debe ingresar el número de afiliado completo");
			
			return false;
		}
		return true;
	}
</script>

<html:form 	styleId="frmMain" action='nuevoTramite' onsubmit="return ValidarDatos()" focusIndex="0">			

	<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TBODY>
		<TR>			
		    <TD background="<html:rewrite page="/jsp/cronicos/images/fondotitulo.gif"/>" height=25 width=99% >
			    <osde:security permission="OSGAFICRON-PUSUREC-ADM" hasPermission="false">
	    			<IMG height=25 src="<html:rewrite page="/jsp/cronicos/images/consulta_patologias.gif"/>" width=650>	    		
	    		</osde:security>		    
		    	<osde:security permission="OSGAFICRON-PUSUREC-ADM" hasPermission="true">
	    			<IMG height=25 src="<html:rewrite page="/jsp/cronicos/images/carga_patologias.gif"/>" width=650>	    		
	    		</osde:security>		    
			</TD>
		    <TD class='version' align='right' width=1% 
		    	background="<html:rewrite page="/jsp/cronicos/images/fondotitulo.gif"/>">
	    		<%=ar.com.osde.framework.config.Configuration.getInstance().getParameter("Version")%>
			</TD>
		</TR>
		
		<TR><TD height=10 colspan='2'><IMG height=1 src="" width=1></TD></TR>
		
		<TR>
			<TD vAlign=top align=center width="100%" colspan='2'>
			
				<TABLE id=tablesgrises cellSpacing=2 cellPadding=0 width="100%">
			  	<TBODY>
			  	
			        <TR id=tablestitulosgrises vAlign=bottom>
			        	<TD colSpan=3 height=18>&nbsp;<IMG height=10 src="<html:rewrite page="/jsp/cronicos/images/bullet.gif"/>" width=10> 
							INGRESE EL NUMERO DE AFILIADO
						</TD>
					</TR>
					
			        <TR>
						<TD width="30%">&nbsp;Número de Afiliado</TD>
						<TD width="70%" colSpan=2>&nbsp;
						
							<html:text 	styleClass="tazul2c" 
										property='filial' 
										maxlength="2" 
										size="2" 
										onkeypress="event.returnValue = OnlyNumber(event.keyCode)"
										onkeyup="AutoTab(event.keyCode,this,afiliado,2)"/>&nbsp;-&nbsp; 
										
							<html:text 	styleClass="tazul2c" 
										property='afiliado' 
										maxlength="7" 
										size="7" 
										onkeypress="event.returnValue = OnlyNumber(event.keyCode)"
										onkeyup="AutoTab(event.keyCode,this,beneficiario,7)"/>&nbsp;-&nbsp; 
							
							<html:text 	styleClass="tazul2c" 
										property='beneficiario' 
										maxlength="2" 
										size="2"
										onkeypress="event.returnValue = OnlyNumber(event.keyCode)"/>

		      			    <img src="<html:rewrite page="/images/ico_buscar.jpg"/>" 
		      				     border="0" align="absmiddle" alt="Buscar Afiliado"
		      				     style='CURSOR: hand'
		      				     onClick="openConsulta('<html:rewrite page="/execute/afiliadoBusquedaBegin"/>')">										
			            </TD>
			        </TR>
			        
			        <TR><TD colSpan=3>&nbsp;</TD></TR>
			        <TR><TD align=center colSpan=3><I>&nbsp;<html:errors /></I></TD></TR>
					<TR><TD colSpan=3>&nbsp;</TD></TR>
			        
					<TR id=tablestitulosgrises>
						<TD align="center" colSpan=3 height=30>
							<html:submit styleClass="tbotonazul">Continuar</html:submit>						
						</TD>
					</TR>
					
			        <TR><TD colSpan=3></TD></TR>
				</TBODY>
				</TABLE>
			</TD>
		</TR>
	</TBODY>
	</TABLE>

	<script language='javascript'>frmMain.filial.focus();</script>
</html:form>
</body>
</html>