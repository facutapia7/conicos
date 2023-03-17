<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>


<%@ page language="java"
contentType="application/x-javascript"
%>


// ** I18N
Calendar._DN = new Array
("<bean:message key="calendar.sunday"/>",
 "<bean:message key="calendar.monday"/>",
 "<bean:message key="calendar.tuesday"/>",
 "<bean:message key="calendar.wednesday"/>",
 "<bean:message key="calendar.thursday"/>",
 "<bean:message key="calendar.friday"/>",
 "<bean:message key="calendar.saturday"/>",
 "<bean:message key="calendar.sunday"/>");

Calendar._MN = new Array
("<bean:message key="calendar.january"/>",
 "<bean:message key="calendar.february"/>",
 "<bean:message key="calendar.march"/>",
 "<bean:message key="calendar.april"/>",
 "<bean:message key="calendar.may"/>",
 "<bean:message key="calendar.june"/>",
 "<bean:message key="calendar.july"/>",
 "<bean:message key="calendar.august"/>",
 "<bean:message key="calendar.september"/>",
 "<bean:message key="calendar.october"/>",
 "<bean:message key="calendar.november"/>",
 "<bean:message key="calendar.december"/>");

Calendar._TT = {};
Calendar._TT["TOGGLE"] = "<bean:message key="calendar.label.toggle"/>";
Calendar._TT["PREV_YEAR"] = "<bean:message key="calendar.label.prev_year"/>";
Calendar._TT["PREV_MONTH"] = "<bean:message key="calendar.label.prev_month"/>";
Calendar._TT["GO_TODAY"] = "<bean:message key="calendar.label.go_today"/>";
Calendar._TT["NEXT_MONTH"] = "<bean:message key="calendar.label.next_month"/>";
Calendar._TT["NEXT_YEAR"] = "<bean:message key="calendar.label.next_year"/>";
Calendar._TT["SEL_DATE"] = "<bean:message key="calendar.label.sel_date"/>";
Calendar._TT["DRAG_TO_MOVE"] = "<bean:message key="calendar.label.drag_to_move"/>";
Calendar._TT["PART_TODAY"] = "<bean:message key="calendar.label.part_today"/>";
Calendar._TT["MON_FIRST"] = "<bean:message key="calendar.label.mon_first"/>";
Calendar._TT["SUN_FIRST"] = "<bean:message key="calendar.label.sun_first"/>";
Calendar._TT["CLOSE"] = "<bean:message key="calendar.label.close"/>";
Calendar._TT["TODAY"] = "<bean:message key="calendar.label.today"/>";


var calendar = null;


function showCalendar(id) 
{
	var el = document.getElementById(id);
	if (el.disabled) return;
	
	var format='<bean:message key="date.pattern" />';

	if (calendar != null) {
		// we already have some calendar created
		calendar.hide();                 // so we hide it first.
	} else {
		// first-time call, create the calendar.
		var cal = new Calendar(true, null, selected, closeHandler);
		calendar = cal;                  // remember it in the global var
		cal.setRange(1900, 2070);        // min/max year allowed.
		cal.create();
	}
	calendar.setDateFormat(convertJavaDateFormat(format));    // set the specified date format
	calendar.parseDate(el.value);      // try to parse the text in field
	calendar.sel = el;                 // inform it what input field we use
	calendar.showAtElement(el);        // show the calendar below it
	
	// catch "mousedown" on document
	Calendar.addEvent(document, "mousedown", checkCalendar);
	
	el.focus();
	return false;
}

function selected(cal, date) 
{
	cal.sel.value = date; 
	cal.callCloseHandler();
	cal.sel.onchange()
}

function closeHandler(cal) 
{
	cal.hide();                        // hide the calendar
	Calendar.removeEvent(document, "mousedown", checkCalendar);
}

function checkCalendar(ev) 
{
  	var el = Calendar.is_ie ? Calendar.getElement(ev) : Calendar.getTargetElement(ev);
  	for (; el != null; el = el.parentNode)
    // FIXME: allow end-user to click some link without closing the
    // calendar.  Good to see real-time stylesheet change :)
	if (el == calendar.element || el.tagName == "A") break;
  	if (el == null) {
    	// calls closeHandler which should hide the calendar.
    	calendar.callCloseHandler();
    	Calendar.stopEvent(ev);
	}
}


function convertJavaDateFormat(dateFormat) {
  dateFormat = replaceAll(dateFormat, "MMM", "mm");
  dateFormat = replaceAll(dateFormat, "MM", "m");
  dateFormat = replaceAll(dateFormat, "yyyy", "y");
  return dateFormat;
}

function replaceAll(aString, c1, c2)
{
	if (aString == "") return aString
	if (c1 == "") return aString
	
	// avoid infinite recursion when substituting aa for a by
	// providing an offset into the string.
	var argc = replaceAll.arguments.length
	if (argc < 4) {n = 0} else {n = replaceAll.arguments[3]}

	// find the first occurrence of c1 after the threshold
	var i = aString.indexOf(c1, n)
	
	// stop recursion and return the current string when c1 not found
	if (i < 0) return aString
	
	// extract substrings s1 and s2 around the c1
	var s1 = aString.substring(0, i)
	var s2 = aString.substring(i+c1.length, aString.length)
	
	// recurse with this new string
	return replaceAll(s1+c2+s2, c1, c2, (i+c2.length))
}

