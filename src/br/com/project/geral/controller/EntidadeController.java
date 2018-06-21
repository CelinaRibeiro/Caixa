/**
 * 
 */
package br.com.project.geral.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.framework.implementacao.crud.ImplementacaoCrud;
import br.com.framework.interfac.crud.InterfaceCrud;
import br.com.project.model.classes.Entidade;
import br.com.srv.interfaces.SrvEntidade;

/**
 * @author Celina
 *
 */
@Controller
public class EntidadeController extends ImplementacaoCrud<Entidade> implements InterfaceCrud<Entidade>{

	@Autowired
	private SrvEntidade srvEntidade;
	
	private static final long serialVersionUID = 1L;
	
	public Entidade findUserLogado(String userLogado) throws Exception{
		return super.findInuqueByProperty(Entidade.class,
				userLogado, "ent_login", "and entity.ent_inativo is false");
	}
	
	public Date getUltimoAcessoEntidadeLogada(String login) {
		return srvEntidade.getUltimoAcessoEntidadeLogada(login);
	}

	public void updateUltimoAcessoUser(String name) {
		srvEntidade.updateUltimoAcessoUser(name);
		
	}
}
