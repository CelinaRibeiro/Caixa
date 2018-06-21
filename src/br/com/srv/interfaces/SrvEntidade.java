/**
 * 
 */
package br.com.srv.interfaces;

import java.io.Serializable;
import java.util.Date;

import org.springframework.stereotype.Service;

/**
 * @author Celina
 *
 */
@Service
public interface SrvEntidade extends Serializable{
	
	Date getUltimoAcessoEntidadeLogada(String name);
	void updateUltimoAcessoUser(String login);
	boolean existeUsuario(String ent_login);
	
}
