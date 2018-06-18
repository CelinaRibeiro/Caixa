/**
 * 
 */
package br.com.repository.interfaces;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

/**
 * @author Celina
 *
 */

@Repository //repository identifica q é uma inteface de conexão com o bd
public interface RepositoryLogin extends Serializable{
	
	boolean autentico(String login, String senha) throws Exception;

}
