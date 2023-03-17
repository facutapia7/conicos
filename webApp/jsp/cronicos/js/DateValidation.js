/*

Validacion de la Fecha de Parto para PMI
========================================

Partimos de una fecha base que es la fecha de alta + 10 meses. 

Fecha Base = F. de Alta + 10 Meses.

El segundo paso, es calcular la antiguedad del socio. Si con la antiguedad del socio 
alcanza, no se controla la fecha probable de parto.

Entonces:

Pasar los meses reconocidos que figuran en ISCD a dias 
por ejemplo 5 meses x 30 dias = 150 Campo 1.
Calcular la diferencia en dias entre Fecha del dia ? Fecha de alta Campo 2 
(antiguedad calculada por tiempo de alta en Osde).

Fecha Calculada = Obtener este dato a partir de la F. de Alta + los dias 
de Campo 1 (dias de meses reconocidos) + los dias de Campo 2 
(dias de antiguedad calculada).

Si Fecha Calculada es >= que Fecha Base, PMI OK, el socio por su antiguedad 
ya cumple con la carencia, por lo tanto no miro F. Probable de Parto. Sino, 
significa que por si mismo no cubre la antiguedad, pero puede llegar a 
cubrirla con la F. Probable de Parto.

Si Fecha Probable de Parto es  >=  Fecha Base, PMI OK.

Si no cubre ni por antiguedad ni por fecha probable de parto, se verifica 
el margen de diferencia. Obtener diferencia en dias entre Fecha Base y Fecha 
Probable de Parto. Si la misma es menor a xx dias (cantidad a definir) mostrar 
un warning de confirmacion y dejar seguir si asi lo solicitan, sino no. Si la 
diferencia es mayor a xx dias, mostrar error y no dejar cargar.

--> Modificaciones Pedidas en Enero 2006

* Se cambio el mensaje que envia si no cumple antiguedad
* Para socios del Tipo O y C no se valida antiguedad en PMI

*/

function ValidarAntiguedad (pFechaAlta, mesesReconocidos, pFechaNacimiento, umbralDias, tipoSocio) {

	var fechaAlta = StringToDate(pFechaAlta);
	var fechaNacimiento = StringToDate(pFechaNacimiento);

	if ( mesesReconocidos.replace(/^\s*|\s*$/g,"") == '') {
		mesesReconocidos = 0;
	} 

	// Calcula la fecha base -> fechaAlta + 10 meses
	var fechaBase = new Date(fechaAlta.getYear(), fechaAlta.getMonth() + 10, fechaAlta.getDate());

	// Meses reconocidos a dias - Campo 1
	var antiguedadMeses = mesesReconocidos * 30;

	// Dias de antiguedad - Campo 2
	var now = new Date();
	var antiguedadDias = ((now.getTime() - fechaAlta.getTime()) / (24*60*60*1000));

	// Calcula la fecha calculada
	var fechaCalculada = new Date(fechaAlta.getTime() + ((antiguedadMeses + antiguedadDias) *24*60*60*1000));

	// Verifica el tipo de socio
	tipoSocio = String(tipoSocio).substring(0,1);

	// Para Obligatorios o con Convenio Obligatorio
	// no se validan las fechas para cargar PMI
	if ( tipoSocio == 'O' || tipoSocio == 'C' ) {
		return true;
	}
	
	// Valida si fechaCalculada es mayor a la fechaBase			
	if ( fechaCalculada < fechaBase ) {
				
		// Chequea fecha probable de parto
		if ( fechaNacimiento < fechaBase ) {

			// Muestra Warning
			if (confirm('El afiliado está en tiempo de espera. ¿desea continuar?')) {

				// Puede Cargar PMI
				return true;

			} else {
			
				// No puede cargar PMI
				return false;
			}

		} else {

			// Puede Cargar PMI
			return true;

		}

	} else {

		// Puede Cargar PMI
		return true;

	}
}

function StringToDate(value) {

	var pattern = "dd/MM/yyyy"
	var displayPattern = "dd/mm/aaaa"
	var datePattern = pattern;
		
    var MONTH = "MM";
	var DAY = "dd";
    var YEAR = "yyyy";
    
    var orderMonth = datePattern.indexOf(MONTH);
    var orderDay = datePattern.indexOf(DAY);
    var orderYear = datePattern.indexOf(YEAR);
    
    var iDelim1 = orderDay + DAY.length;
    var iDelim2 = orderMonth + MONTH.length;
    
    var delim1 = datePattern.substring(iDelim1, iDelim1 + 1);
    var delim2 = datePattern.substring(iDelim2, iDelim2 + 1);
    
    if (iDelim1 == orderMonth && iDelim2 == orderYear) {
    	dateRegexp = new RegExp("^(\\d{2})(\\d{2})(\\d{4})$");
    } else if (iDelim1 == orderMonth) {
        dateRegexp = new RegExp("^(\\d{2})(\\d{2})[" + delim2 + "](\\d{4})$");
    } else if (iDelim2 == orderYear) {
        dateRegexp = new RegExp("^(\\d{2})[" + delim1 + "](\\d{2})(\\d{4})$");
    } else {
        dateRegexp = new RegExp("^(\\d{2})[" + delim1 + "](\\d{2})[" + delim2 + "](\\d{4})$");
    }
    var matched = dateRegexp.exec(value);
    
    if ( matched != null) {

    	var day = matched[1];
    	var month = matched[2] - 1;
    	var year = matched[3];

		// Devuelve la fecha parseada
		return new Date(year, month, day);
	}	
}
function showDate(fecha) {
	return fecha.getDate() + "/" + (fecha.getMonth() + 1) + "/" + fecha.getYear();
}
