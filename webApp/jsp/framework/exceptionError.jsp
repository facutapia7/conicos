<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/osde.tld" prefix="osde" %>

<%@ page import="ar.com.osde.framework.Constants"%>

<!DOCTYPE HTML PUBLIC "-//w3c//dtd html 4.0 transitional//en">

<html>
<head>
	<html:base/>
	<title></title>
	<meta http-equiv=Content-Type content="text/html; charset=iso-8859-1">

	<osde:load noCache="true"/>
</head>


<body>
<h1>
<bean:message bundle='errors' key='error.unexpectedException.titulo'/>
</h1>

<div align='left'>
	<bean:message bundle='errors' key='error.unexpectedException.descripcion'/>
	<br>
	<bean:message bundle='errors' key='error.unexpectedException.codigoLog'/>:
	<bean:write  name='<%=Constants.BEAN_EXCEPTION%>' property='pk' />
	<br>
	<bean:message bundle='errors' key='error.unexpectedException.mensaje'/>:
	<bean:write name='<%=Constants.BEAN_EXCEPTION%>' property='message'/>
	<br>
	<bean:message bundle='errors' key='error.unexpectedException.contacto'/>
</div>

</body>
</html>