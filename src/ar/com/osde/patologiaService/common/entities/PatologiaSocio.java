package ar.com.osde.patologiaService.common.entities;
import ar.com.osde.framework.entities.FrameworkEntity;


public class PatologiaSocio implements FrameworkEntity {
	
	
	private static final long serialVersionUID = 1829880742109785637L;

	private String codigo;
	
	private String codigoPat;
	
	private Patologia patologia;
	private long vigDesde;
    private long vigHasta; 

	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}	

	public String getCodigoPat() {
		return codigoPat;
	}
	public void setCodigoPat(String codigo) {
		this.codigoPat = codigo;
	}
	
	public long getVigDesde() {
		return vigDesde;
	}
	
	public void setVigDesde(long vigDesde) {
		this.vigDesde = vigDesde;
	}

	public long getVigHasta() {
		return vigHasta;
	}

	public void setVigHasta(long vigHasta) {
		this.vigHasta = vigHasta;
	}
	public Patologia getPatologia() {
		return patologia;
	}
	public void setPatologia(Patologia patologia) {
		this.patologia = patologia;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((patologia == null) ? 0 : patologia.hashCode());
		result = prime * result + (int) (vigDesde ^ (vigDesde >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof PatologiaSocio)) {
			return false;
		}
		PatologiaSocio other = (PatologiaSocio) obj;
		if (codigo == null) {
			if (other.codigo != null) {
				return false;
			}
		} else if (!codigo.equals(other.codigo)) {
			return false;
		}
		if (patologia == null) {
			if (other.patologia != null) {
				return false;
			}
		} else if (!patologia.equals(other.patologia)) {
			return false;
		}
		if (vigDesde != other.vigDesde) {
			return false;
		}
		return true;
	}
	@Override
	public String toString() {
		return "PatologiaSocio [" + (codigo != null ? "codigo=" + codigo + ", " : "")
				+ (patologia != null ? "patologia=" + patologia + ", " : "") + "vigDesde=" + vigDesde + ", vigHasta="
				+ vigHasta + "]";
	}

	
}