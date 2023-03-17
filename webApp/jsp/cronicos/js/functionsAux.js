//
// Author: 	Adrian C. Martinez
// date:	2004/08
//



function AutoTab(key,origen,destino,caracteres){
     if(key != 9 && key != 16){
        if(origen.value.length >= caracteres){
            destino.focus();
        }
    }   
}

function GoHome(){
 	document.location.href = '../jsp/cronicos/nuevoTramite.jsp';
	return true;
}

function AllTrim(s) {
	return s.replace( /^\s*/, "" ).replace( /\s*$/, "" );
}

function validarFecha(obj, pattern, displayPattern) {

	if ( typeof displayPattern=="undefined" ) {
		displayPattern=pattern;
	}
		
	var bValid = true;
	var value = obj.value;
	
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
    
    if(matched != null) {
    	if (!isValidDate(matched[1], matched[2], matched[3])) {
            bValid =  false;
        }
    } else {
        bValid =  false;        
    }
    
    if (! bValid) {
    	alert("La fecha " + obj.value + " es invalida... (ejemplo: " + displayPattern + ")");
    	obj.value = "";
        obj.focus();
    }
	return bValid;
}	

function isValidDate(day, month, year) {
    if (month < 1 || month > 12) {
            return false;
     }
    if (day < 1 || day > 31) {
        return false;
    }
    if ((month == 4 || month == 6 || month == 9 || month == 11) &&
        (day == 31)) {
        return false;
    }
    if (month == 2) {
        var leap = (year % 4 == 0 &&
                   (year % 100 != 0 || year % 400 == 0));
        if (day>29 || (day == 29 && !leap)) {
            return false;
        }
    }
    return true;
}		
