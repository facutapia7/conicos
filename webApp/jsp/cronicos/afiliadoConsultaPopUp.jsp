<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/osde.tld" prefix="osde" %>
<%@ taglib uri="/WEB-INF/tld/displaytag-11.tld" prefix="display" %>

<%@ page language="java" %>
<%@ page import="ar.com.osde.cronicos.Constantes" %>
<%@ page import="ar.com.osde.framework.config.Configuration" %>

<html>
<head>
	<html:base/>
	<title>Consulta de Afiliado</title>
	<meta http-equiv=Content-Type content="text/html; charset=iso-8859-1">

 	<osde:load noCache="true"/>
	
	<link href='<html:rewrite page="/jsp/cronicos/styles/osde.css"/>' type=text/css rel=stylesheet>
	<link href='<html:rewrite page="/jsp/cronicos/styles/displaytag.css"/>' type=text/css rel=stylesheet>
	<link href='<html:rewrite page="/jsp/cronicos/styles/style.css"/>' type=text/css rel=stylesheet>
	
	<script language='javascript' src='<html:rewrite page="/jsp/cronicos/js/functions.js"/>'></script>
</head>

<script language="JavaScript">
	function submitForm() {
		if (checkForm(document.frmMain)) {
		    var obj1 = document.getElementById("process");
		    var obj2 = document.getElementById("genral");		

	        obj1.style.visibility = "visible";
    	    obj2.style.visibility = "hidden";			

			frmMain.submit();
			return;
		}
	}

	function checkForm(form) {

		var apellido = AllTrim(form.txtApellido.value);

		if ( apellido == "" ) 
		{
			alert("Debe completar el apellido del afiliado a buscar.");
			return false;
		}
		if ( apellido.length < 3 ) 
		{
			alert("Para realizar una busqueda debe ingresar al menos 3 letras del apellido.");
			return false;
		}
		
		return true;
	}
	
	function selectAfiliado() {
		var i = 0;
		var selectedSocio = "";
		if (String(frmMain.rdoSocio) != "undefined") {		
		
			var rdo = frmMain.rdoSocio;
		
			if (String(rdo.length) == "undefined") {
				selectedSocio = rdo.value;
			} else {
				for(i=0;i<rdo.length;++i) {
					if (rdo[i].checked) {
						selectedSocio = rdo[i].value;
					}
				}
			}
			window.opener.document.forms[0].filial.value = selectedSocio.substring(0,2);
			window.opener.document.forms[0].afiliado.value = selectedSocio.substring(3,10);
			window.opener.document.forms[0].beneficiario.value = selectedSocio.substring(11,13);
			window.close();
			
		} else {
			alert("Debe realizar una busqueda primero...");
		}
	}
	
	function isEnterSubmit(keycode) {
		if (keycode==13) {
			submitForm();
		}
	}
</script>

<body class="tahoma" style="font-size:8px;" bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<div id='process' align="center"  style='VISIBILITY: hidden; POSITION: absolute; LEFT:1px; TOP:1px'>
	<TABLE id=tablesgrises3 cellSpacing=2 cellPadding=0>
        <TR id=tablestitulosgrises vAlign=bottom>
        	<TD colSpan=3 height=18>&nbsp;<IMG height=10 src="<html:rewrite page="/jsp/cronicos/images/bullet.gif"/>" width=10> 
				BUSQUEDA DE AFILIADOS
			</TD>
		</TR>
		<tr>
			<td class="titulo" valign="top">&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<td class="titulo" valign="top">&nbsp;&nbsp;Procesando Informaci&oacute;n...</td>
		</tr>
	</table>
</div>
<div id='genral' style='VISIBILITY: visible; POSITION: absolute; LEFT:1px; TOP:1px'>

	<html:form 	action="afiliadoBusquedaEnd" 
				onsubmit="return checkForm(this)" styleId="frmMain">

	<TABLE id=tablesgrises3 cellSpacing=2 cellPadding=0 width="100%">
        <TR id=tablestitulosgrises vAlign=bottom>
        	<TD colSpan=3 height=18>&nbsp;<IMG height=10 src="<html:rewrite page="/jsp/cronicos/images/bullet.gif"/>" width=10> 
				BUSQUEDA DE AFILIADOS
			</TD>
		</TR>
	</table>
	<TABLE id=tablesgrises3 cellSpacing=2 cellPadding=0 width="100%">
        <TR>
			<TD width="30%">&nbsp;Nombre:</TD>
			<TD width="70%" colSpan=2>
       			<html:text  property="nombre" 
       						styleClass="tazul2c" 
       						size="30" maxlength="30" 
       						styleId="txtNombre" 
       						onkeyup="isEnterSubmit(event.keyCode)"/>
       		</td>
  		</tr>
        <TR>
			<TD width="30%">&nbsp;Apellido:</TD>
			<TD width="70%" colSpan=2>
       			<html:text  property="apellido" 
       						styleClass="tazul2c" 
       						size="30" maxlength="30" 
       						styleId="txtApellido" 
       						onkeyup="isEnterSubmit(event.keyCode)"/>
       		</td>
  		</tr>
 		<tr> 
    		<td colspan="3" align="center" height="30">
   				<img src="<html:rewrite page="/images/ico_buscar2.gif"/>" style='CURSOR: hand'
   					 width="82" height="18" border="0" align="absmiddle" alt="Buscar Afiliados"
   					 onclick="submitForm()"/>
    		</td>
  		</tr>

		<logic:present name="<%=Constantes.SOCIOS_BUSQUEDA_LISTA%>">
		<logic:equal name="<%=Constantes.SOCIOS_BUSQUEDA_LISTA%>" property="filtrado" value="true">
	        <tr> 
    	      <td align="center" class='titulos-suplementarios' colspan='3'>
    	      	*** La cantidad de Afiliados obtenidos por la busqueda, superan el limite permitido.
    	      	Solo se mostraran los primeros <%=Configuration.getInstance().getParameter("cronicos.maxRows")%> ***
    	      </td>
    	    </tr>  
		</logic:equal>
		</logic:present>
	</table>  
	
	<logic:present name="<%=Constantes.SOCIOS_BUSQUEDA_LISTA%>">
	
	<% 
		String mensaje = "";
		java.util.List listaResultados = (java.util.List) pageContext.getSession().getAttribute(Constantes.SOCIOS_BUSQUEDA_LISTA);
		if ( listaResultados.size() >= 100 ) {
			mensaje = "El resultado supera los 100 afiliados. Ingrese nombre/apellido más precisos.";
		}
	%>
	<table width="100%">
        <tr> 
          <td align="center" class='titulos-suplementarios'>
		    <%String lista = "sessionScope." + Constantes.SOCIOS_BUSQUEDA_LISTA;%>
			<display:table  uid="socio" 
							name="<%=lista%>" 
							pagesize="10"
							class="list">

				<display:setProperty 	name="basic.msg.empty_list">
					<bean:message key="cronicos.afiliadoNoEncontrado"/>
				</display:setProperty>
				
				<display:setProperty 	name="paging.banner.item_name" 
										value="Socio"/>

				<display:setProperty 	name="paging.banner.items_name" 
										value="Socios" />
	
				<display:column title="" align="center">
					<input type="radio" name="rdoSocio" value="<bean:write name="socio" property="numeroSocio"/>">
				</display:column>
	
				<display:column title="Partner" property="partner" valign="center"/>
				<display:column title="Nombre" property="nombre" valign="center"/>
				<display:column title="Apellido" property="apellido" valign="center"/>
				<display:column title="N&deg; Afiliado" property="numeroSocio" valign="center"/>
				<display:column title="Tipo" property="tipoDocumento" align="center" valign="center"/>
				<display:column title="Documento" property="numeroDocumento" align="right" valign="center"/>
				
			</display:table>		
          </td>
        </tr>
        <tr> 
          <td align="center" class='titulos-suplementarios'>
			 <%=mensaje%>
          </td>
        </tr>
	</table>
	</logic:present>
	
	<br>
	
	<TABLE id=tablesgrises3 cellSpacing=2 cellPadding=0 width="100%">
		<TR id=tablestitulosgrises align="center">
	  		<td height="30"> 
            	<input name="cmdAceptar"  type="Button" class="tbotonazul" value="Aceptar"  title="Aceptar"
            		   onclick="selectAfiliado()"/>&nbsp;
				<input name="cmdCancelar" type="Button" class="tbotonazul" value="Cancelar" title="Cancelar" 
					   onclick="javascript:window.close()"/>
    	  	</td>
		</tr>
	</table>
	
	</html:form>	
</div>
