package ar.com.osde.cronicos.beans;

import ar.com.osde.framework.entity.VersionedEntityObject;

/**
 * @author Diego A. Naya
 * @version 1.0
 * Created on 28/10/2004
 */
public class GatewayCRM extends VersionedEntityObject {
/**
 * @return Returns the estado.
 */
public String getEstado() {
	return estado;
}
/**
 * @param estado The estado to set.
 */
public void setEstado(String estado) {
	this.estado = estado;
}
/**
 * @return Returns the fecha.
 */
public long getFecha() {
	return fecha;
}
/**
 * @param fecha The fecha to set.
 */
public void setFecha(long fecha) {
	this.fecha = fecha;
}
/**
 * @return Returns the marca.
 */
public String getMarca() {
	return marca;
}
/**
 * @param marca The marca to set.
 */
public void setMarca(String marca) {
	this.marca = marca;
}
/**
 * @return Returns the nrobp.
 */
public String getNrobp() {
	return nrobp;
}
/**
 * @param nrobp The nrobp to set.
 */
public void setNrobp(String nrobp) {
	this.nrobp = nrobp;
}
 private String nrobp;
 private String marca;
 private String estado;
 private long fecha;
 
}
