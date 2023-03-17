<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/osde.tld" prefix="osde" %>

<%@ page language="java" %>
<%@ page import = "ar.com.osde.cronicos.Constantes"%>
<%@ page import = "java.util.Map"%>
<%@ page import = "java.util.Date"%>
<%@ page import = "java.util.Calendar"%>
<%@ page import = "org.apache.commons.lang.time.FastDateFormat"%>
<%@ page import = "java.util.HashMap"%>
<%@ page import = "java.util.Iterator"%>
<%@ page import = "ar.com.osde.cronicos.beans.PatologiaBase"%>
<%@ page import = "ar.com.osde.cronicos.beans.Diagnostico"%>
<%@ page import = "ar.com.osde.framework.config.Configuration"%>

<bean:define  	id="afiliadoCronico" type="ar.com.osde.cronicos.beans.AfiliadoCronico" name="<%=Constantes.AFILIADO%>"/>

<%
	Calendar c = Calendar.getInstance();
	c.add(Calendar.MONTH, 9);

	String fechaParto = FastDateFormat.getInstance("dd/MM/yyyy").format(c.getTime());
	String fechaPartoYMD = FastDateFormat.getInstance("yyyyMMdd").format(c.getTime());
	String now = FastDateFormat.getInstance("yyyyMMdd").format(new Date());

	Diagnostico diagnosticoPMI = (Diagnostico)session.getAttribute("pmiBeans");

	boolean diagnosInicial = false; 

	//* Armo Map con Patologias Asignadas *//
	Map patologias = new HashMap();
	if (afiliadoCronico.getDiagnosticos() != null) {
		for (Iterator it = afiliadoCronico.getDiagnosticos().iterator(); it.hasNext();) {
			PatologiaBase patBase = (PatologiaBase)it.next();
			patologias.put("D#" + patBase.getCodigo(), patBase);
		}
		diagnosInicial= true;
	}
	if (afiliadoCronico.getDatosEpidemiologicos() != null) {
		for (Iterator it = afiliadoCronico.getDatosEpidemiologicos().iterator(); it.hasNext();) {
			PatologiaBase patBase = (PatologiaBase)it.next();
			patologias.put("E#" +patBase.getCodigo(), patBase);
		}
	}
%>

<html>
<head>
	<html:base/>
	<title>Carga de Patologías Crónicas</title>
	<meta http-equiv=Content-Type content="text/html; charset=iso-8859-1">

	<osde:load noCache="true"/>

	<link href='<html:rewrite page="/jsp/cronicos/styles/style.css"/>' type=text/css rel=stylesheet>
	<link href="<html:rewrite page="/jsp/cronicos/styles/calendario.css"/>"  rel="stylesheet" type="text/css">

	<script language='javascript' src='<html:rewrite page="/jsp/cronicos/js/functionsAux.js"/>'></script>
	<script language='javascript' src='<html:rewrite page="/jsp/cronicos/js/DateValidation.js"/>'></script>
	<script type="text/javascript" src="<html:rewrite page="/jsp/cronicos/js/calendar.js"/>"></script>
	<script language="Javascript" src="<html:rewrite page="/jsp/cronicos/calendar.jsp"/>"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>

	<script language='javascript'>

		var fechaPartoOriginal = "<bean:write name="IngresoDatosTramiteForm" property="fechaProbableDeParto" />"
		var diagnosInicial = <%=diagnosInicial%>;

		var pattern = "<bean:message key="date.pattern"/>";
		var displayPattern = "<bean:message key="date.displayPattern"/>";

		var lastObj = null;
		var medicos = new Array(1);

		<logic:present name="<%=Constantes.AFILIADO%>" property="diagnosticos">
	    <logic:iterate 	id="diagnostico" property="diagnosticos"
				    	name="<%=Constantes.AFILIADO%>"
				        type="ar.com.osde.cronicos.beans.Diagnostico">
			medicos[medicos.length-1] = new MedicoTratante(
				"D<bean:write name="diagnostico" property="codigo"/>",
				"<bean:write name="diagnostico" property="medicoTratante.numeroPrestador"/>",
				"<bean:write name="diagnostico" property="medicoTratante.numeroMatricula"/>",
				"<bean:write name="diagnostico" property="medicoTratante.nombre"/>",
				"<bean:write name="diagnostico" property="medicoTratante.telefono"/>",
				"<bean:write name="diagnostico" property="medicoTratante.tipoMatricula"/>",
				"<bean:write name="diagnostico" property="medicoTratante.provincia"/>"
			);
			medicos.length = medicos.length + 1;
	    </logic:iterate>
	    </logic:present>

	    <logic:present name="<%=Constantes.AFILIADO%>" property="datosEpidemiologicos">
	    <logic:iterate 	id="datosEpi" property="datosEpidemiologicos"
				    	name="<%=Constantes.AFILIADO%>">
			medicos[medicos.length-1] = new MedicoTratante(
				"E<bean:write name="datosEpi" property="codigo"/>",
				"<bean:write name="datosEpi" property="medicoTratante.numeroPrestador"/>",
				"<bean:write name="datosEpi" property="medicoTratante.numeroMatricula"/>",
				"<bean:write name="datosEpi" property="medicoTratante.nombre"/>",
				"<bean:write name="datosEpi" property="medicoTratante.telefono"/>",
				"<bean:write name="datosEpi" property="medicoTratante.tipoMatricula"/>",
				"<bean:write name="datosEpi" property="medicoTratante.provincia"/>"
			);
			medicos.length = medicos.length + 1;
	    </logic:iterate>
	    </logic:present>

		function CheckDesmarcaALL() {

			for( a=0; a < frmMain.diagnosticos.length; a++ ) {
				if ( frmMain.diagnosticos[a].checked == true ) {
					return true;
				}
			}

			return false;
		}
		
		// PRES-244
		function VerificarSoloAltaPMI(){
			
			if(!CheckDesmarcaALL() && (frmMain.pmi.checked) && (frmMain.fechaProbableDeParto.value != "")){
				
				frmMain.txtNombre.value = "";
				frmMain.txtTelefono.value = "";
				frmMain.txtNumeroMatricula.value = "";
				frmMain.txtCodigoPrestador.value = "";
				
				frmMain.prestadorOsde.checked = false;
				return true;
			}
			return false;
		}

	    function CheckDesmarca(key, obj) {

		   	if (!obj.checked) {
				var i=0;
				for(i=0; i < medicos.length; i++) {
					if (medicos[i] != null) {
						if (key + obj.value == medicos[i].codigo) {
							if (key == "E") {
								return (confirm("Esta quitando un Dato Epidemiológico grabado con anterioridad... Confirma quitarla ?"));
							} else {
								if ( !CheckDesmarcaALL() ) {
								//version-1.2.7 - INICIO
									var e= 0;
									for( a=0; a < frmMain.diagnosticos.length; a++ ) {
										if (frmMain.diagnosticos[a].checked) {
											e=e+1;
										}
									}
									if(e==0){
										if ( confirm("Al eliminar todas las Patologías, también se eliminarán los Datos Epidemiológicos, si los hubiera. Confirmar Afiliado NO Crónico?") ) {
											frmMain.txtNombre.value = "";
											frmMain.txtTelefono.value = "";
											frmMain.txtNumeroMatricula.value = "";
											frmMain.txtCodigoPrestador.value = "";											
											frmMain.prestadorOsde.checked = false;
											Activar(false);

											for(var j=0; j < frmMain.datosEpidemiologicos.length; j++) {
												frmMain.datosEpidemiologicos[j].checked = false;
											}
											 return true;
										}else{
										
											return false;

										}
									}else if ( confirm("Esta quitando una Patología grabada con anterioridad... Confirma quitarla ?") ) {
											return true;
									} else {
										return false;
									}
									} else {
									var e= 0;
									for( a=0; a < frmMain.diagnosticos.length; a++ ) {
										if (frmMain.diagnosticos[a].checked) {
											e=e+1;
										}
									}
									if(e==0){
										if ( confirm("Al eliminar todas las Patologías, también se eliminarán los Datos Epidemiológicos, si los hubiera. Confirmar Afiliado NO Crónico?") ) {
											frmMain.txtNombre.value = "";
											frmMain.txtTelefono.value = "";
											frmMain.txtNumeroMatricula.value = "";
											frmMain.txtCodigoPrestador.value = "";
											frmMain.prestadorOsde.checked = false;
											Activar(false);

											for(var j=0; j < frmMain.datosEpidemiologicos.length; j++) {
												frmMain.datosEpidemiologicos[j].checked = false;
											}
											return true;
										}else{
										return false;
									}
									}else if ( confirm("Esta quitando una Patología grabada con anterioridad... Confirma quitarla?") ) {
											return true;
								} else {
										return false;
									}
								//version-1.2.7 - FIN
								}
							}
						}
					}
				}
	    	}
	    	return true;
	   	}

		function DisplayData(obj) {
			if (lastObj != null) lastObj.style.color = "#000000";
			lastObj = obj;
			obj.style.color = "#7755ff";

			var i=0;
			for(i=0; i < medicos.length; i++) {
				if (medicos[i] != null) {
					if (obj.id == medicos[i].codigo) {
						frmMain.cboProvincia.value = medicos[i].provincia;
						frmMain.cboMatricula.value = medicos[i].tipoMatricula;
						frmMain.txtNombre.value = medicos[i].nombre;
						frmMain.txtTelefono.value = medicos[i].telefono;
						frmMain.txtNumeroMatricula.value = medicos[i].matricula;
						frmMain.txtCodigoPrestador.value = medicos[i].prestador;

						frmMain.prestadorOsde.checked = !(medicos[i].prestador=="0");
						Activar(frmMain.prestadorOsde);
						document.location.href="#final";

						return;
					}
				}
			}
		}

	    function Trim(s) {
			return s.replace( /^\s*/, "" ).replace( /\s*$/, "" );
		}

		function MedicoTratante(codigo,prestador,numeroMatricula,nombre,telefono,tipoMatricula,provincia) {
			this.codigo=Trim(codigo)
			this.prestador=Trim(prestador)
			this.matricula=Trim(numeroMatricula)
			this.nombre=Trim(nombre)
			this.telefono=Trim(telefono)
			this.tipoMatricula=Trim(tipoMatricula)
			this.provincia=Trim(provincia) + " "
		}

		function Activar(chk) {
	    	<osde:security permission="OSGAFICRON-PUSUREC-ADM" hasPermission="true">
			<logic:equal name="IngresoDatosTramiteForm"  property="readOnly" value="N">
				frmMain.cboProvincia.disabled = (chk.checked);
				frmMain.cboMatricula.disabled = (chk.checked);
				frmMain.txtNombre.disabled = (chk.checked);
				frmMain.txtTelefono.disabled = (chk.checked);
				frmMain.txtNumeroMatricula.disabled = (chk.checked);
				frmMain.txtCodigoPrestador.disabled = (!chk.checked);
			</logic:equal>
	    	</osde:security>

		}

		function Modificado(frmMain){
			
			if('<bean:write name="<%=Constantes.AFILIADO%>" property="afiliado.sexo"/>' =="F" || '<bean:write name="<%=Constantes.AFILIADO%>" property="afiliado.sexo"/>' =="X" ){
				if(frmMain.fechaProbableDeParto.value != 
					'<bean:write name="IngresoDatosTramiteForm" property="fechaProbableDeParto" />'){
					return true;
				}
			}
			if(frmMain.numeroDeMatricula.value != 
				'<bean:write name="IngresoDatosTramiteForm" property="numeroDeMatricula" />'){
				return true;
			}
			if(frmMain.codigoPrestador.value != 
				'<bean:write name="IngresoDatosTramiteForm" property="codigoPrestador" />'){
				return true;
			}
			if(frmMain.txtNombre.value != 
				'<bean:write name="IngresoDatosTramiteForm" property="apellidoNombre" />'){
				return true;
			}
			if(frmMain.txtTelefono.value != 
				'<bean:write name="IngresoDatosTramiteForm" property="telefono" />'){
				return true;
			}
			
			var presCheck = "off";
				
			if(frmMain.prestadorOsde.checked){
				presCheck = "on";
			}else{
				presCheck = "off";
			}
			
			if(presCheck !=	'<bean:write name="IngresoDatosTramiteForm" property="prestadorOsde" />'){
				return true;				
			}
				
			var diagInicial = ""; 
			var diagFinal = "";
			<%
			if (afiliadoCronico.getDiagnosticos() != null) {
			for (Iterator it = afiliadoCronico.getDiagnosticos().iterator(); it.hasNext();) {
				PatologiaBase patBase = (PatologiaBase)it.next();
			%>
			diagInicial= diagInicial+<%= Integer.parseInt(patBase.getCodigo())%>+"-";
			<%}
			}%>
			
			 for(var a=0; a < frmMain.diagnosticos.length; a++ ) {
				if (frmMain.diagnosticos[a].checked) {
					diagFinal = diagFinal + (parseFloat(frmMain.diagnosticos[a].value))+"-";
				}
			 }
		 
			 if(diagInicial != diagFinal){
				 return true;
			 }
			
			var epiInicial = ""; 
			var epiFinal = "";
			<%
			if (afiliadoCronico.getDatosEpidemiologicos() != null) {
			for (Iterator it = afiliadoCronico.getDatosEpidemiologicos().iterator(); it.hasNext();) {
				PatologiaBase patBase = (PatologiaBase)it.next();
			%>
			epiInicial= epiInicial+<%= Integer.parseInt(patBase.getCodigo())%>+"-";
			<%}
			}%>
			
			 for(var a=0; a < frmMain.datosEpidemiologicos.length; a++ ) {
				if (frmMain.datosEpidemiologicos[a].checked) {
					epiFinal = epiFinal + (parseFloat(frmMain.datosEpidemiologicos[a].value))+"-";
				}
			 }
			if(epiInicial != epiFinal){
				 return true;
			}
					
			return false;
		}

		function ValidarDatos(frmMain) {
			
			if(!Modificado(frmMain)){
				alert("Debe generar alguna acción con anterioridad para realizar algún cambio.");
				return false;
			}
			
			var FechaAltaCronico = '<bean:write name="<%=Constantes.AFILIADO%>" property="afiliado.fechaAltaFormatted"/>';
			
			if (frmMain.prestadorOsde.checked) {
				if (frmMain.codigoPrestador.value.length == 0 ||
					Number(frmMain.codigoPrestador.value) <= 0) {
					alert("Debe ingresar código de Prestador.");
					return false;
				}
				
				// Si se tildo que es prestador de osde y ademas es solo alta de pmi
				 if(VerificarSoloAltaPMI() && diagnosInicial==false){
					alert("No es necesario ingresar datos de médico tratante");
						return false;
				}
	
			}else {

				<logic:notEqual name="<%=Constantes.AFILIADO%>" property="afiliado.sexo" value="M">
   	    		<logic:notEqual name="IngresoDatosTramiteForm"  property="esHijo" value="true">
					if (!setFechaVigencia(frmMain.fechaProbableDeParto)) {
						return false;
					}
					if (frmMain.pmi.checked && frmMain.fechaProbableDeParto.value == "") {
						alert("Debe completar la fecha probable de parto.");
						return false;
					}
				</logic:notEqual>
   	    		</logic:notEqual>

				var i = 0;
				if (frmMain.numeroDeMatricula.value.length != 0) i++;
				if (frmMain.txtNombre.value.length != 0) i++;
				if (frmMain.txtTelefono.value.length != 0) i++;
			
				if (i != 3 && i != 0) {
					alert("Debe completar número de Matrícula, Apellido y Nombre y Teléfono Contacto.");
					return false;
				}else if( i==3 && VerificarSoloAltaPMI() && diagnosInicial==false){
					alert("No es necesario ingresar datos de médico tratante");
					return false;
				}

				var Sexo =  '<bean:write name="<%=Constantes.AFILIADO%>" property="afiliado.sexo"/>';
				
				<logic:notEqual name="<%=Constantes.AFILIADO%>" property="afiliado.sexo" value="M">
   	    		<logic:notEqual name="IngresoDatosTramiteForm"  property="esHijo" value="true">
					if (frmMain.fechaProbableDeParto.value != "") {
						var obj = frmMain.fechaProbableDeParto.onblur
						frmMain.fechaProbableDeParto.onblur = null;
						if (validarFechaParto(frmMain.fechaProbableDeParto, false)) {

							// Valida las fechas para cargar PMI
							var FechaAlta = '<bean:write name="<%=Constantes.AFILIADO%>" property="afiliado.fechaAltaFormatted"/>';
							var mesesReconocidos = '<bean:write name="<%=Constantes.AFILIADO%>" property="afiliado.mesesReconocidos"/>';
							var FechaParto = frmMain.fechaProbableDeParto.value;
							var umbralDias = 10;

							if (!ValidarAntiguedad(FechaAlta, mesesReconocidos, FechaParto, umbralDias, '<bean:write name="<%=Constantes.AFILIADO%>" property="afiliado.tipo"/>') ) {
								return false;
							}
							
							return goSubmit();

						} else {

							frmMain.fechaProbableDeParto.onblur = obj;
							return false;

						}
					} else {
						<logic:equal name="<%=Constantes.AFILIADO%>" property="actualizacionPMI" value="true">
							return true;
   	    				</logic:equal>
					}
				</logic:notEqual>
   	    		</logic:notEqual>

   	    		var e= 0;
				for( a=0; a < frmMain.diagnosticos.length; a++ ) {
					if (frmMain.diagnosticos[a].checked) {
						e=e+1;
					}
				}
   	    		
				if(String(FechaAltaCronico) == "undefined"){
					if(String(Sexo) == "F" || String(Sexo) == "X" ){
						if(frmMain.pmi.checked == false){
							if(e == 0) {
								alert("Debe generar alguna acción con anterioridad para realizar algún cambio.");
								return false;	
							}
						}
					}else{
						if(e == 0) {
							alert("Debe generar alguna acción con anterioridad para realizar algún cambio.");
							return false;	
						}
					}
				}
					
   	    		//si fecha de alta es distinto a valor y no tiene ningun check chequeado no te permite dar actulizar.
				if (frmMain.numeroDeMatricula.value.length == 0 ||
					frmMain.txtNombre.value.length == 0 ||
					frmMain.txtTelefono.value.length == 0) {
					alert("Debe completar número de Matrícula, Apellido y Nombre y Teléfono Contacto.");
					return false;
				}
   	    		
			}
			
			// Valida las fechas para cargar PMI
			if(frmMain.pmi.checked == true){		
			var FechaAlta = '<bean:write name="<%=Constantes.AFILIADO%>" property="afiliado.fechaAltaFormatted"/>';
			var mesesReconocidos = '<bean:write name="<%=Constantes.AFILIADO%>" property="afiliado.mesesReconocidos"/>';
			var FechaParto = frmMain.fechaProbableDeParto.value;
			var umbralDias = 10;

			if (!ValidarAntiguedad(FechaAlta, mesesReconocidos, FechaParto, umbralDias, '<bean:write name="<%=Constantes.AFILIADO%>" property="afiliado.tipo"/>') ) {
				return false;
			}
			}
			// va al submit
			return goSubmit();

		}
		function goSubmit() {
			frmMain.submitir.disabled = true;
			frmMain.anular.value = "";
			if (frmMain.anularButton != null) {
				frmMain.anularButton.disabled = true;
			}
			return true;
		}
		function clearPatologyChecks(frmMain){
			if (confirm("Confirma ANULAR la Solicitud ?")) {
				var i=0;
				while (frmMain.diagnosticos(i) != null) {
					frmMain.diagnosticos(i).checked = false
					i++;
				}

				frmMain.anular.value = 'anular';
				frmMain.anularButton.disabled = true;
				frmMain.submitir.disabled = true;
				frmMain.submit();
			}
		}

		function onlyNumberForDate(key) {
	   		if (key == 47 || key == 45) {
				return true;
	   		} else {
				return LocalOnlyNumber(key);
			}
		}

		function LocalOnlyNumber(key){
			if(key == 13){
				if (ValidarDatos(frmMain)) {
			    	frmMain.submit();
				} else {
					return false;
				}
		   	}
		    if (key < 48 || key > 57) {
		   	    return false;
		    }

   			return true;
		}
		function validarFechaParto(obj, boo) {
			if (validarFecha(obj,pattern,displayPattern) || boo) {

				if (obj.value != '<bean:write name="IngresoDatosTramiteForm" property="fechaProbableDeParto" />') {
					if ( parseToAMD(obj.value) > '<%=now%>' || boo ) {
						if (parseToAMD(obj.value) <= '<%=fechaPartoYMD%>') {

							return true;

						} else {
							alert('La fecha de parto no puede ser mayor a 9 meses.');
							//version-1.2.7 - INICIO
							obj.value = "";
							obj.focus();
							return false;
							//version-1.2.7 - FIN
						}
					} else {
						//version-1.2.7 - INICIO
						//alert('La fecha de parto no puede ser menor a la fecha del día.');
						alert('La fecha de parto no puede ser Menor o Igual a la fecha del día.');
						obj.value = "";
						obj.focus();
						return false;
						//version-1.2.7 - FIN
					}
				} else {
					return true;
				}
			}
			obj.focus();
			return false;
		}

		function setFechaVigencia(obj, boo) {

			if (String(boo) == "undefined") boo = false;

			var divObj = document.getElementById("fechaProbable");
			var texto = "Fecha de Vig. Final: __/__/____";
			var check = false;
			var ret = true;

			if (frmMain.pmi.checked && obj.value != "") {
				if (validarFechaParto(obj,boo)) {

				    <logic:empty name="IngresoDatosTramiteForm"
				        				property="fechaProbableDeParto">
				    ret = true;
					fechaPartoOriginal = frmMain.fechaProbableDeParto.value;
				    </logic:empty>

				    <logic:notEmpty name="IngresoDatosTramiteForm"
				        				property="fechaProbableDeParto">
					if ( fechaPartoOriginal != obj.value ) {
						if ( !confirm('Ha cambiado la Fecha Probable de Parto. Esto modificará la Fecha Final de PMI. ¿Continúa?') ) {
							  ret = false;
							  frmMain.fechaProbableDeParto.value = fechaPartoOriginal;
						  } else {
							  ret = true;
							  fechaPartoOriginal = frmMain.fechaProbableDeParto.value;
						  }
				    }
				    </logic:notEmpty>

					var d = new Date(parseToMDA(obj.value));
					d.setDate(d.getDate() + <%=Configuration.getInstance().getParameter(Constantes.FEHCA_PMI)%>);

					texto = "Fecha de Vig. Final: " + getDateDMA(d);
					check = true;
				}
			}else if(frmMain.pmi.checked && obj.value == "" && fechaPartoOriginal != ""){
				if ( !confirm('Ha cambiado la Fecha Probable de Parto. Esto modificará la Fecha Final de PMI. ¿Continúa?') ) {
					  ret = false;
					  frmMain.fechaProbableDeParto.value = fechaPartoOriginal;
					  var d = new Date(parseToMDA(obj.value));					
					  d.setDate(d.getDate() + <%=Configuration.getInstance().getParameter(Constantes.FEHCA_PMI)%>);
					  texto = "Fecha de Vig. Final: " + getDateDMA(d);
					  check = true;
				  } else {
					  ret = true;
					  fechaPartoOriginal = frmMain.fechaProbableDeParto.value;
				  }
			}
			divObj.innerHTML = texto;
			//frmMain.pmi.checked = check;

			return ret;
		}
		function parseToAMD(value) {
			var day   = value.substring(0,2);
			var month = value.substring(3,5);
			var year  = value.substring(6,11);

			return String(year + month + day);
		}
		function parseToMDA(value) {
			var month = value.substring(3,5);
			var day   = value.substring(0,2);
			var year  = value.substring(6,11);
			
			return month + "/" + day + "/" + year;
		}
		function getDateDMA(d) {
			var day   = String(d.getDate());
			var month = String(d.getMonth() + 1);
			var year  = String(d.getFullYear());
			
			if (month.length == 1) month = "0" + month;
			if (day.length == 1) day = "0" + day;

			return day + "/" + month  + "/" + year;
		}
		function setFechas(obj) {
			if (obj.checked) {
				//frmMain.fechaProbableDeParto.value = "<%=fechaParto%>";
			} else {
				frmMain.fechaProbableDeParto.value = "";
				setFechaVigencia(frmMain.fechaProbableDeParto);
			}

		}
		function setDivBlanck() {
	
			var divObj = document.getElementById("fechaProbable");
			setFechaVigencia(frmMain.fechaProbableDeParto);
			//divObj.innerHTML = "Fecha de Vig. Final: __/__/____";
		}

		function ModoVista() {
			var i = 0;
			while (String(frmMain.elements[i]) != "undefined"
					&& String(frmMain.elements[i]) != "null") {
				if (String(frmMain.elements[i].disabled) != "undefined"
					&& String(frmMain.elements[i].disabled) != "null") {
					frmMain.elements[i].disabled = true;
				}
				i++;
			}
			frmMain.newIngreso.disabled = false;
		}
		
		function PMIClick(obj) {

			// Verifica si el PMI fue deschekeado
			if ( !frmMain.pmi.checked ) {

				<logic:equal name="<%=Constantes.AFILIADO%>" property="actualizacionPMI" value="true">

					// Pide confirmacion
					if ( confirm("¿Confirma Baja de PMI Vigente?") ) {
						//version-1.2.7 - INICIO
						// Pide segunda confirmacion
						//if ( !confirm("Este movimiento generara una nueva credencial, continua ?") ) {
						//	frmMain.pmi.checked = true;
						//}							
						//version-1.2.7 - FIN
						var divObj = document.getElementById("fechaProbable");
						divObj.innerHTML = "Fecha de Vig. Final: __/__/____";
						frmMain.fechaProbableDeParto.value = "";
						return true;
					} else {

						frmMain.pmi.checked = true;

					}

				</logic:equal>

			}

			// Setea las fechas
			setFechas(obj);

		}

		/*
			Al hacer click sobre el check de muestra un alert si el afiliado NO se encuentra entre los siguientes parentescos.
			- Titular
			- Conyuge
			- Conyuge Adicional
			- Concubina/o
		*/
		function PMIAlertClick(){
			var parentesto = '<bean:write name="<%=Constantes.AFILIADO%>" property="afiliado.parentescoId"/>';
			var t = '<bean:message key="jsp.parentesco.titular"/>';
			var c = '<bean:message key="jsp.parentesco.conyuge"/>';
			var ca = '<bean:message key="jsp.parentesco.conyugeAdicional"/>';
			var co = '<bean:message key="jsp.parentesco.concubina"/>';
			if (frmMain.pmi.checked){
				//alert (parentesto!= t && parentesto!= c && parentesto!= ca && parentesto!= co);
				if (parentesto!= t && parentesto!= c && parentesto!= ca && parentesto!= co){
					alert('<bean:message key="jsp.parentesco.mensaje"/>')
				}
			}
		}

	</script>
</head>

<body bgcolor="#FFFFFF">

<html:form 	action='ingresoDatosTramite'
			styleId="frmMain"
			onsubmit="return ValidarDatos(this)">
	<html:hidden property="anular"/>
	<html:hidden property="esHijo"/>

	<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TBODY>
		<TR>
		    <TD background="<html:rewrite page="/jsp/cronicos/images/fondotitulo.gif"/>" height=25 width=99% >
			    <osde:security permission="OSGAFICRON-PUSUREC-ADM" hasPermission="false">
	    			<IMG height=25 src="<html:rewrite page="/jsp/cronicos/images/consulta_patologias.gif"/>" width=650>
	    		</osde:security>
		    	<osde:security permission="OSGAFICRON-PUSUREC-ADM" hasPermission="true">
					<logic:equal name="IngresoDatosTramiteForm" property="readOnly" value="N">
	    				<IMG height=25 src="<html:rewrite page="/jsp/cronicos/images/carga_patologias.gif"/>" width=650>
					</logic:equal>
					<logic:equal name="IngresoDatosTramiteForm" property="readOnly" value="S">
	    				<IMG height=25 src="<html:rewrite page="/jsp/cronicos/images/consulta_patologias.gif"/>" width=650>
					</logic:equal>
	    		</osde:security>
			</TD>
		    <TD class='version' align='right' width=1%
		    	background="<html:rewrite page="/jsp/cronicos/images/fondotitulo.gif"/>">
	    		<%=Configuration.getInstance().getParameter("Version")%>
			</TD>
		</TR>

		<TR><TD height=10 colspan='2'><IMG height=1 src="" width=1></TD></TR>

		<TR>
	    	<TD vAlign=top align="center" width="100%"  colspan='2'><html:errors />

				<TABLE id=tablesgrises cellSpacing=2 cellPadding=0 width="100%">
		        <TBODY>
		        	<TR id=tablestitulosgrises vAlign=bottom>
		          		<TD colSpan=3 height=18>&nbsp;
		          			<IMG height=10 src="<html:rewrite page="/jsp/cronicos/images/bullet.gif"/>" width=10>&nbsp;DATOS PERSONALES
				        </TD>
				    </TR>

		        	<TR id=tablestitulosgrises vAlign=bottom>
		          		<TD colSpan=3 height=18>&nbsp;

							<TABLE cellSpacing=2 cellPadding=0 width="100%">
					        <TBODY>

					        	<TR id=tablesgrises2>
					          		<TD height=20 width="20%"><b><font color='black'>&nbsp;Número de Afiliado:</font></b></TD>
				          			<TD width="20%"><font color='black'>
											<bean:write name="<%=Constantes.AFILIADO%>" property="afiliado.filial" />&nbsp;-&nbsp;
											<bean:write name="<%=Constantes.AFILIADO%>" property="afiliado.nroAfiliado" />&nbsp;-&nbsp;
											<bean:write name="<%=Constantes.AFILIADO%>" property="afiliado.beneficiario" /></font>
				          			</TD>
					          		<TD width="15%"><b><font color='black'>&nbsp;Fecha Alta:</font></b></TD>
					          		<TD width="15%"><font color='black'><bean:write name="<%=Constantes.AFILIADO%>"	property="afiliado.fechaAltaFormatted"/></font></TD>
					          		<TD width="15%"><b><font color='black'>&nbsp;Fecha Baja:</font></b></TD>
					          		<TD width="15%"><font color='black'>&nbsp;<bean:write name="<%=Constantes.AFILIADO%>" property="afiliado.fechaBajaFormatted"/></font></TD>
					        	</TR>

						        <TR id=tablesgrises2>
					    	      	<TD height=20><b><font color='black'>&nbsp;Apellido y Nombre:</font></b></TD>
									<TD colSpan=5><font color='black'>
										<bean:write name="<%=Constantes.AFILIADO%>"	property="afiliado.apellido"/>&nbsp;
										<bean:write name="<%=Constantes.AFILIADO%>"	property="afiliado.nombre"/></font>
									</TD>
								</TR>

					        	<TR id=tablesgrises2>
					          		<TD height=20><b><font color='black'>&nbsp;Fecha de Nacimiento:</font></b></TD>
					          		<TD><font color='black'><bean:write name="<%=Constantes.AFILIADO%>"	property="afiliado.fechaNacimientoFormatted" /></font></TD>
					          		<TD width="15%"><b><font color='black'>&nbsp;Sexo:</font></b></TD>
					          		<TD width="15%"><font color='black'>&nbsp;<bean:write name="<%=Constantes.AFILIADO%>" property="afiliado.sexo"  /></font></TD>
					          		<TD width="15%"><b><font color='black'>&nbsp;Plan:</font></b></TD>
					          		<TD width="15%"><font color='black'>&nbsp;<bean:write name="<%=Constantes.AFILIADO%>" property="afiliado.plan"/></font></TD>
					          	</TR>

						        <TR id=tablesgrises2>
					    	      	<TD height=20><b><font color='black'>&nbsp;Meses Reconocidos:</font></b></TD>
					        	  	<TD><font color='black'><bean:write name="<%=Constantes.AFILIADO%>" property="afiliado.mesesReconocidos"/></font></TD>
					          		<TD><b><font color='black'>&nbsp;Carencias:</font></b></TD>
					          		<TD><font color='black'>&nbsp;<bean:write name="<%=Constantes.AFILIADO%>" property="afiliado.carencias"/></font></TD>
					        	</TR>

						        <TR id=tablesgrises2>
					    	      	<TD height=20><b><font color='black'>&nbsp;Parentesco:</font></b></TD>
					        	  	<TD><font color='black'><bean:write name="<%=Constantes.AFILIADO%>" property="afiliado.parentesco"/></font></TD>
					          		<TD><b><font color='black'>&nbsp;Tipo de Afiliado:</font></b></TD>
					          		<TD><font color='black'>&nbsp;<bean:write name="<%=Constantes.AFILIADO%>" property="afiliado.tipo"/></font></TD>
					        	</TR>

						        <TR id=tablesgrises2>
					    	      	<TD height=20><b><font color='black'>&nbsp;Alta del Formulario:</font></b></TD>
									<TD colSpan=5><font color='black'><bean:write name="<%=Constantes.AFILIADO%>" property="fechaCargaDDMMYYYY"/></font></TD>
								</TR>

							</TBODY>
							</TABLE>
		          		</TD>
		        	</TR>

			        <TR><TD colSpan=3>&nbsp;</TD></TR>

			        <logic:equal name="<%=Constantes.AFILIADO%>" property="menor" value="false">
				        <logic:notEqual name="<%=Constantes.AFILIADO%>" property="afiliado.sexo" value="M">
					        <%@ include file="showPMI.j" %>
						</logic:notEqual>
				        <logic:equal name="<%=Constantes.AFILIADO%>" property="afiliado.sexo" value="M">
						<logic:equal name="IngresoDatosTramiteForm"  property="esHijo" value="true">
					        <%@ include file="showPMI.j" %>
						</logic:equal>
						</logic:equal>
				        <logic:equal name="<%=Constantes.AFILIADO%>" property="afiliado.sexo" value="X">
						<logic:equal name="IngresoDatosTramiteForm"  property="esHijo" value="true">
					        <%@ include file="showPMI.j" %>
						</logic:equal>
						</logic:equal>
					</logic:equal>

		        	<TR id=tablestitulosgrises vAlign=bottom>
		          		<TD colSpan=3 height=18>&nbsp;
		          			<IMG height=10 src="<html:rewrite page="/jsp/cronicos/images/bullet.gif"/>" width=10>
	          				&nbsp;DIAGNÓSTICO&nbsp;&nbsp;&nbsp;(vigencia desde)
		          		</TD>
		          	</TR>

			        <TR><TD height='1px' style='BACKGROUND: white;' colSpan=3></TD></TR>

		        	<TR id=tablesgrises>
		          		<TD id=tablesgrises colSpan=3>

		            		<TABLE cellSpacing=1 cellPadding=0 border=0 width="100%">
		              		<TBODY>
		              			<%int indexID=0;%>

						        <logic:iterate
						        	id="diagnostico"
				    		    	name="diagnosticosValues"
				        			type="ar.com.osde.cronicos.beans.Diagnostico">

									<logic:notEqual name="diagnostico"
													property="codigo"
													value="<%=diagnosticoPMI.getCodigo()%>">

					        			<%if (indexID==0) out.print("<TR>");%>
										<TD width="1px%" height=20>
											<html:multibox 	property="diagnosticos"
															onclick="return CheckDesmarca('D', this);">
												<bean:write name="diagnostico" property="codigo"/>
											</html:multibox>
										</TD>

										<TD height=20>
											<span 	style='CURSOR: hand'
													onclick='DisplayData(this)'
													id='D<bean:write name="diagnostico" property="codigo"/>'>
												<bean:write name="diagnostico" property="descripcion"/>
											</span>
										</TD>

										<TD height=20 align="right">
											<% 	if (patologias.containsKey("D#" + diagnostico.getCodigo())) {
													out.print("&nbsp;" +
														((PatologiaBase)patologias
															.get("D#" + diagnostico.getCodigo()))
															.getFechaDesdeDDMMYYYY().trim());
												} else {
													out.print("&nbsp;");
												}
											%>
										</TD>

			              				<%indexID++;%>

			              				<%if (indexID==4) {
			              					indexID=0;
			              					out.print("</TR>");
			              				  } else {
			              				    out.print("<TD width='1px' style='BACKGROUND: white;' height='20'>&nbsp;</TD>");
			              				  }
			              				%>
									</logic:notEqual>
			        			</logic:iterate>

			                </TBODY>
	        		        </TABLE>
	                	</TD>
	                </TR>

			        <TR><TD height='1px' style='BACKGROUND: white;' colSpan=3></TD></TR>

	        		<TR id=tablestitulosgrises vAlign=bottom>
	          			<TD colSpan=3 height=18>&nbsp;
	          				<IMG height=10 src="<html:rewrite page="/jsp/cronicos/images/bullet.gif"/>" width=10>
	          				&nbsp;DATOS EPIDEMIOLÓGICOS&nbsp;&nbsp;(vigencia desde)
	            		</TD>
	            	</TR>

			        <TR><TD height='1px' style='BACKGROUND: white;' colSpan=3></TD></TR>

				    <TR id=tablesgrises>
		        		<TD id=tablesgrises colSpan=3>

	            			<TABLE id=tablesgrises cellSpacing=1 cellPadding=0 border=0>
	              			<TBODY>
	              				<%indexID=0;%>

						        <logic:iterate
						        	id="datoEpidemiologico"
						        	name="datosEpidemiologicosValues"
						        	type="ar.com.osde.cronicos.beans.DatoEpidemiologico">

				        			<%if (indexID%2==0) out.print("<TR>");%>

	                				<TD noWrap width="25%" height=20>
										<html:multibox property="datosEpidemiologicos"
  													   onclick="return CheckDesmarca('E', this);">
											<bean:write name="datoEpidemiologico" property="codigo"/>
										</html:multibox>
										<span 	style='CURSOR: hand'
												onclick='DisplayData(this)'
												id='E<bean:write name="datoEpidemiologico" property="codigo"/>'>
											<bean:write name="datoEpidemiologico" property="descripcion"/>
										</span>
	                				</TD>

									<TD width="25%" height=20 nowrap>
										<% 	if (patologias.containsKey("E#" + datoEpidemiologico.getCodigo())) {
												out.print("&nbsp;" +
													((PatologiaBase)patologias
														.get("E#" + datoEpidemiologico.getCodigo()))
														.getFechaDesdeDDMMYYYY());
											} else {
												out.print("&nbsp;");
											}
										%>
									</TD>

		              				<%indexID++;%>
		              				<%if (indexID%2==0) {
		              					out.print("</TR>");
 		              				 } else {
		              				    out.print("<TD width='1px' style='BACKGROUND: white;' height='20'>&nbsp;</TD>");
		              				 }
		              				%>
			        			</logic:iterate>

							</TBODY>
							</TABLE>
						</TD>
					</TR>

			        <TR><TD height='1px' style='BACKGROUND: white;' colSpan=3></TD></TR>

				    <TR id=tablestitulosgrises vAlign=bottom>
				    	<TD colSpan=3 height=18>&nbsp;
				    		<IMG height=10 src="<html:rewrite page="/jsp/cronicos/images/bullet.gif"/>" width=10>&nbsp;DATOS DEL MÉDICO TRATANTE
				        </TD>
				    </TR>

			        <TR>
	        			<TD width="50%" height=20>&nbsp;Es prestador de OSDE:</TD>
	          			<TD colSpan=2>&nbsp;
							<html:checkbox 	property="prestadorOsde" onclick="Activar(this)"/>
	          			</TD>
	          		</TR>

	        		<TR>
	          			<TD colSpan=3 height=25>
	          				<I>-&gt; Si es prestador (puede buscar el prestador por número o por &nbsp;matrícula)</I>
	          			</TD>
	          		</TR>

			        <TR>
	        			<TD>&nbsp;Número de prestador:</TD>
	          			<TD colSpan=2>&nbsp;
		          			<html:text 	property="codigoPrestador" styleId="txtCodigoPrestador"
		          						maxlength="6"
		          						size="6"
		          						onkeypress="event.returnValue = LocalOnlyNumber(event.keyCode)"></html:text>
		       			</TD>
	          		</TR>

			        <TR>
	          			<TD colSpan=3 height=20>
	          				<I>-&gt; Si NO es prestador carga toda la información </I>
	          			</TD>
	          		</TR>

	        		<TR>
	          			<TD>&nbsp;N° de Matrícula:</TD>
	          			<TD colSpan=2>&nbsp;
	          				<html:text 	property="numeroDeMatricula" styleId="txtNumeroMatricula"
	          							maxlength="10"
	          							size="10"
	          						    onkeypress="event.returnValue = LocalOnlyNumber(event.keyCode)"></html:text>
	          			</TD>
	          		</TR>

	        		<TR>
	          			<TD>&nbsp;Apellido y Nombre:<BR></TD>
	          			<TD colSpan=2>&nbsp;
	          				<html:text 	property='apellidoNombre'
	          							maxlength="20"
	          							size="20"
	          							styleId="txtNombre"></html:text>
	          			</TD>
	          		</TR>

			        <TR>
	          			<TD height=20>&nbsp;Matrícula:<BR></TD>
	          			<TD colSpan=2>&nbsp;
							<html:select 	property="tipoDeMatricula"
											styleClass="tazul2c"
											styleId="cboMatricula">
								<html:option value="N">Nacional</html:option>
								<html:option value="M">Provincial</html:option>
							</html:select>
						</TD>
					</TR>

	        		<TR>
						<TD height=20>&nbsp;Provincia:</TD>
	          			<TD colSpan=2>&nbsp;
	          				<html:select property="provincia"
	          							 styleClass="tazul2c"
	          							 styleId="cboProvincia">
	          					<html:options 	collection="proviciasValues"
	          									property="codigo"
	          									labelProperty="nombre"
	          									labelName="proviciasValues" />
	              			</html:select>
	              		</TD>
	              	</TR>

		        	<TR>
	          			<TD>&nbsp;Teléfono de contacto:</TD>
	          			<TD colSpan=2>&nbsp;
	          				<html:text 	property="telefono"
	          							maxlength="15"
	          							size="15"
          							 	styleId="txtTelefono"></html:text>
	          			</TD>
	          		</TR>

	        		<TR><TD colSpan=3>&nbsp;</TD></TR>

	        		<TR id=tablestitulosgrises>
	          			<TD align="center" colSpan=3 height=30>
							<html:button property="button" styleId="newIngreso"
										 styleClass="tbotonazul"
										 onclick="GoHome()"> Ingresar Otro Afiliado</html:button>&nbsp;
							<osde:security permission="OSGAFICRON-PUSUREC-ADM">
							<logic:equal name="IngresoDatosTramiteForm"  property="readOnly" value="N">

								<logic:present name="<%=Constantes.AFILIADO%>">
								<logic:equal name="<%=Constantes.AFILIADO%>" property="actualizacion" value="true">
									<html:button property="anularButton"
											 styleClass="tbotonazul"
											 onclick="clearPatologyChecks(frmMain)"> Anulación </html:button>&nbsp;
								</logic:equal>
								</logic:present>
								<html:submit property="button" styleId="submitir"
											 styleClass="tbotonazul"> ACTUALIZAR ESTA FICHA </html:submit>

							</logic:equal>
							</osde:security>
						</TD>
					</TR>

	        		<TR><TD colSpan=3 id='final'></TD></TR>
	        	</TBODY>
	        	</TABLE>
       		</TD>
		</TR>
	</TBODY>
	</TABLE>

	<script language='javascript'>
		Activar(frmMain.prestadorOsde);

		<osde:security permission="OSGAFICRON-PUSUREC-ADM" hasPermission="false">
			ModoVista()
		</osde:security>
		<logic:equal name="IngresoDatosTramiteForm"  property="readOnly" value="S">
			ModoVista()
        </logic:equal>

        <logic:equal name="<%=Constantes.AFILIADO%>" property="menor" value="false">
			<logic:notEqual name="<%=Constantes.AFILIADO%>" property="afiliado.sexo" value="M">
	        <logic:notEqual name="IngresoDatosTramiteForm"  property="esHijo" value="true">
				setFechaVigencia(frmMain.fechaProbableDeParto, true);
	        </logic:notEqual>
    	    </logic:notEqual>
    	</logic:equal>
	</script>
</html:form>

</body>

</html>