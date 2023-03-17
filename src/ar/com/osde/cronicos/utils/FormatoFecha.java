package ar.com.osde.cronicos.utils;

public enum FormatoFecha{

/** Formato yyyyMMdd */
yyyyMMdd ("yyyyMMdd"),
/** Formato yyyyMMddHHmmss */
yyyyMMddHHmmss ("yyyyMMddHHmmss"),
/** Formato ddMMyyyy */
ddMMyyyy ("ddMMyyyy"),
/** Formato yyyy/MM/dd */
yyyy_MM_dd ("yyyy/MM/dd"),
/** Formato yyyy/MM/dd HH:mm:ss */
yyyy_MM_dd_HH_mm_ss ("yyyy/MM/dd HH:mm:ss"),
/** Formato dd/MM/yyyy */
dd_MM_yyyy ("dd/MM/yyyy"),
/** Formato HH:mm:ss */
HH_mm_ss ("HH:mm:ss"),
/** Formato ddMMyy */
ddMMyy ("ddMMyy"),
/** Formato MMMMM yyyy */
MMMMM_yyyy("MMMMM yyyy"),
/** Formato yyMMdd */
yyMMdd ("yyMMdd"),
/** Formato dd/MM/yy */
dd_MM_yy ("dd/MM/yy"),
/** Formato	dd-MM-yyyy */
dd$MM$yyyy ("dd-MM-yyyy"),
/** Formato dd/MM/yyyy HH:mm:ss */
dd_MM_yyyy_HH_mm_ss ("dd/MM/yyyy HH:mm:ss"),
/** Formato HHmmss */
HHmmss ("HHmmss"),
/** Formato MMdd */
MMdd ("MMdd"),
/** Formato HHmm */
HHmm ("HHmm"),
/** Formato yyyy-MM-dd */
yyyy$MM$dd ("yyyy-MM-dd"),
/** Formato ddMMyyyyHHmm */
ddMMyyyyHHmm ("ddMMyyyyHHmm");

private String formato;

private FormatoFecha(String formato) {
this.formato = formato;
}

public String getFormato() {
return formato;
}
}