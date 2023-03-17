<TR id=tablestitulosgrises vAlign=bottom>
	<TD colSpan=3 height=18>&nbsp;
		<IMG height=10 src="<html:rewrite page="/jsp/cronicos/images/bullet.gif"/>" width=10>
		&nbsp;P.M.I.
	</TD>
</TR>

<TR><TD height='1px' style='BACKGROUND: white;' colSpan=3></TD></TR>

<TR id=tablesgrises>
	<TD id=tablesgrises colSpan=3>

		<TABLE cellSpacing=1 cellPadding=0 border=0 width="100%">
  		<TBODY>
  			<TR>
				<TD height=20 nowrap="nowrap">
			        <logic:notEqual name="IngresoDatosTramiteForm"
	        				 		property="esHijo" value="true">
						<html:checkbox 	property="pmi" onclick="PMIClick(this);PMIAlertClick();"/>
					</logic:notEqual>
			        <logic:equal name="IngresoDatosTramiteForm"
	        				 property="esHijo" value="true">
				        <logic:notEmpty name="IngresoDatosTramiteForm"
				        				property="fechaProbableDeParto">
				        	&nbsp;<strong>SI</strong>
		        		</logic:notEmpty>
				        <logic:empty name="IngresoDatosTramiteForm"
				        				property="fechaProbableDeParto">
				        	&nbsp;<strong>NO</strong>
		        		</logic:empty>
					</logic:equal>
					&nbsp;<%=diagnosticoPMI.getDescripcion()%>
				</TD>

				<TD height=20 align="right" nowrap="nowrap">
			        <logic:notEqual name="IngresoDatosTramiteForm"
			        				property="esHijo" value="true">
						Fecha Probable de Parto:&nbsp;
					</logic:notEqual>
			        <logic:equal name="IngresoDatosTramiteForm"
			        			 property="esHijo" value="true">
						Fecha de Vig. Desde:&nbsp;
					</logic:equal>
				</TD>
				<TD height=20 align="left" nowrap="nowrap">
			        <logic:notEqual name="IngresoDatosTramiteForm"
			        				property="esHijo" value="true">
						<osde:calendar property="fechaProbableDeParto"
									   onfocus="setDivBlanck()"
									   onchange="setFechaVigencia(this);"
								   	   onkeypress="event.returnValue = onlyNumberForDate(event.keyCode)"
								   	   styleButton="CURSOR: hand;"/>
					</logic:notEqual>
			        <logic:equal name="IngresoDatosTramiteForm"
			        			 property="esHijo" value="true">
				        <logic:empty name="IngresoDatosTramiteForm"
				        			 property="fechaVigenciaDesde">
							__/__/____
						</logic:empty>
				        <logic:notEmpty name="IngresoDatosTramiteForm"
					        			property="fechaVigenciaDesde">
				        	<bean:write name="IngresoDatosTramiteForm"
				        				property="fechaVigenciaDesde"/>
				        </logic:notEmpty>
					</logic:equal>
				</TD>
				<TD height=20 align="right" nowrap="nowrap">
					<DIV id="fechaProbable">Fecha de Vig. Final :
				        <logic:notEqual name="IngresoDatosTramiteForm"
				        			    property="esHijo" value="true">
								__/__/____
						</logic:notEqual>
				        <logic:equal name="IngresoDatosTramiteForm"
				        			 property="esHijo" value="true">
					        <logic:empty name="IngresoDatosTramiteForm"
					        			 property="fechaProbableDeParto">
								__/__/____
							</logic:empty>
					        <logic:notEmpty name="IngresoDatosTramiteForm"
						        			property="fechaProbableDeParto">
					        	<bean:write name="IngresoDatosTramiteForm"
					        				property="fechaProbableDeParto"/>
					        </logic:notEmpty>
						</logic:equal>
					</DIV>
				</TD>
   			</TR>
        </TBODY>
        </TABLE>
  	</TD>
</TR>

<TR><TD height='1px' style='BACKGROUND: white;' colSpan=3></TD></TR>
