<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/osde.tld" prefix="osde" %>

<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">

<html>
<head>
	<html:base/>
	<title>Carga de Patologías Crónicas</title>
	<meta http-equiv=Content-Type content="text/html; charset=iso-8859-1">
	
	<osde:load noCache="true"/>
	<link href='<html:rewrite page="/jsp/cronicos/styles/style.css"/>' type=text/css rel=stylesheet>	
</head>

<body bgcolor="#FFFFFF">
	<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TBODY>
		<TR>			
		    <TD background="<html:rewrite page="/jsp/cronicos/images/fondotitulo.gif"/>" height=25 width=99% >
	    		<IMG height=25 src="<html:rewrite page="/jsp/cronicos/images/carga_patologias.gif"/>" width=650>	    		
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
							ERROR DE SEGURIDAD
						</TD>
					</TR>
			        
			        <TR><TD colSpan=3>&nbsp;</TD></TR>
			        <TR><TD align=left colSpan=3><I>&nbsp;<html:errors bundle="errors"/></I></TD></TR>
					<TR><TD colSpan=3>&nbsp;</TD></TR>
			        
					<TR id=tablestitulosgrises>
						<TD align=center colSpan=3 height=30>
							<input 	type="button" 
									class="tbotonazul" 
									value="Ingresar a Intranet" 
									onclick="document.location.href='http://intranet.osde/navegador/login.asp'"/>						
						</TD>
					</TR>
					
			        <TR><TD colSpan=3></TD></TR>
				</TBODY>
				</TABLE>
			</TD>
		</TR>
	</TBODY>
	</TABLE>
</body>
</html>
