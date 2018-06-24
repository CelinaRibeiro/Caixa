/**
 * 
 */
package br.com.srv.implementacao;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import br.com.repository.interfaces.RepositoryCidade;
import br.com.srv.interfaces.SrvCidade;

/**
 * @author Celina
 *
 */

@Service
public class SrvCidadadeImpl implements SrvCidade {

	private static final long serialVersionUID = 1L;
	
	@Resource
	private RepositoryCidade repositoryCidade;

}
