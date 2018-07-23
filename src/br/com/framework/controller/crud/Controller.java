/**
 * 
 */
package br.com.framework.controller.crud;

import org.springframework.transaction.annotation.Transactional;

import br.com.framework.implementacao.crud.ImplementacaoCrud;

/**
 * @author Celina
 *
 */

@Transactional
public class Controller extends ImplementacaoCrud<Object>{

	private static final long serialVersionUID = 1L;

}
