/**
 * 
 */
package br.com.dao.implementacao;

import java.util.Date;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import br.com.framework.implementacao.crud.ImplementacaoCrud;
import br.com.project.model.classes.Entidade;
import br.com.repository.interfaces.RepositoryEntidade;

/**
 * @author Celina
 *
 */

@Repository
public class DaoEntidade extends ImplementacaoCrud<Entidade> implements RepositoryEntidade {

	private static final long serialVersionUID = 1L;

	@Override
	public Date getUltimoAcessoEntidadeLogada(String name) {
		SqlRowSet rowSet = super.getJdbcTemplate().queryForRowSet(
				"select ent_ultimoacesso from entidade where ent_inativo is false and ent_login = ?",
				new Object[] { name });

		return rowSet.next() ? rowSet.getDate("ent_ultimoacesso") : null;
	}

	@Override
	public void updateUltimoAcessoUser(String login) {
		/*
		String sql = "update entidade set ent_ultimoacesso = current_timestamp where ent_inativo is false and ent_login = ? ";
		super.getSimpleJdbcTemplate().update(sql, login);
		*/
		
		//outra forma para inserir a hora no BD -  pega a data do bd
		try {
			String sql = "update entidade set ent_ultimoacesso = current_timestamp "
			 + " where ent_inativo is false and ent_login = '" + login + "'";
			super.executeUpdateSQLDinamica(sql);
			super.getSession().beginTransaction().commit();			
		}catch (Exception e) {
			e.printStackTrace();
			}
			
		
	}

	@Override
	public boolean existeUsuario(String ent_login) {
		StringBuilder builder = new StringBuilder();
		builder.append(" select count(1) >= 1 from entidade where ent_login = '").append(ent_login).append("' ");
		return super.getJdbcTemplate().queryForObject(builder.toString(), Boolean.class);
	}

}
