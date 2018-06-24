/**
 * 
 */
package br.com.project.model.classes;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.envers.Audited;

import br.com.project.annotation.IdentificaCampoPesquisa;

/**
 * @author Celina
 *
 */

@Audited
@Entity
@Table(name = "cidade")
@SequenceGenerator(name = "cidade_seq", sequenceName = "cidade_seq", initialValue = 1, allocationSize = 1)
public class Cidade implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@IdentificaCampoPesquisa(descricaoCampo = "C�digo", campoConsulta = "cid_codigo" )
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cidade_seq")
	private Long cid_codigo;
	
	@IdentificaCampoPesquisa(descricaoCampo = "Descri��o", campoConsulta = "cid_descri��o", principal = 1)
	@Column(length = 100, nullable = false)
	private String cid_descricao;
	
	@IdentificaCampoPesquisa(descricaoCampo = "Estado", campoConsulta = "estado.est_nome")
	@Basic
	@ManyToOne
	@JoinColumn(name = "estado", nullable = false)
	@ForeignKey(name = "estado_fk")
	private Estado estado = new Estado();
	
	@Version
	@Column(name = "versionNum")
	private int versionNum;

	public Long getCid_codigo() {
		return cid_codigo;
	}

	public void setCid_codigo(Long cid_codigo) {
		this.cid_codigo = cid_codigo;
	}

	public String getCid_descricao() {
		return cid_descricao;
	}

	public void setCid_descricao(String cid_descricao) {
		this.cid_descricao = cid_descricao;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public int getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(int versionNum) {
		this.versionNum = versionNum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cid_codigo == null) ? 0 : cid_codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cidade other = (Cidade) obj;
		if (cid_codigo == null) {
			if (other.cid_codigo != null)
				return false;
		} else if (!cid_codigo.equals(other.cid_codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cidade [cid_codigo=" + cid_codigo + ", cid_descricao=" + cid_descricao + "]";
	}
	
	
	
	

}
