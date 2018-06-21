/**
 * 
 */
package br.com.repository.interfaces;

import java.io.Serializable;
import java.util.Date;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Celina
 *
 */

@Transactional //parte mais pr�xima do bd - far� insert, update, delete...
@Repository
public interface RepositoryEntidade extends Serializable{
	
	Date getUltimoAcessoEntidadeLogada(String name);
	void updateUltimoAcessoUser(String login);
	boolean existeUsuario(String ent_login);

}
