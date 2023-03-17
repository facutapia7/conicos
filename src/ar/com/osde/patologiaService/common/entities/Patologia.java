package ar.com.osde.patologiaService.common.entities;
import ar.com.osde.framework.entities.FrameworkEntity;

public class Patologia implements FrameworkEntity {
	private static final long serialVersionUID = 2491447967399656945L;
	private String codigo;
	private String descripcion;
	private Long vigDesde;
	private Long vigHasta;
	

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	

	public Long getVigDesde() {
		return vigDesde;
	}
	public void setVigDesde(Long vigDesde) {
		this.vigDesde = vigDesde;
	}
	public Long getVigHasta() {
		return vigHasta;
	}
	public void setVigHasta(Long vigHasta) {
		this.vigHasta = vigHasta;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		if (!(obj instanceof Patologia)) {
			return false;
		}
		Patologia other = (Patologia) obj;
		if (codigo == null) {
			if (other.codigo != null) {
				return false;
			}
		} else if (!codigo.equals(other.codigo)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Patologia ["
				+ (codigo != null ? "codigo=" + codigo + ", " : "")
				+ (descripcion != null ? "descripcion=" + descripcion + ", "
						: "") + "vigDesde=" + vigDesde + ", vigHasta="
				+ vigHasta + "]";
	}
	
	
}
