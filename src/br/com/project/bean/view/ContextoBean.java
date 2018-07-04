/**
 * 
 */
package br.com.project.bean.view;

import java.io.Serializable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.project.geral.controller.EntidadeController;
import br.com.project.geral.controller.SessionController;
import br.com.project.model.classes.Entidade;

/**
 * @author Celina
 *
 */
@Scope(value = "session")
@Component(value = "contextoBean")
public class ContextoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String USER_LOGADO_SESSAO = "userLogadoSessao";

	@Autowired
	private EntidadeController entidadeController;
	
	@Autowired
	private SessionController sessionController;

	/*
	 * Retorna todas as informações do usuário logado
	 * 
	 * @ return Authentication
	 */
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public Entidade getEntidadeLogada() throws Exception {
		Entidade entidade = (Entidade) getExternalContext().getSessionMap().get(USER_LOGADO_SESSAO);

		if (entidade == null || (entidade != null && !entidade.getEnt_login().equals(getUserPrincipal()))) {

			if (getAuthentication().isAuthenticated()) {
				entidadeController.updateUltimoAcessoUser(getAuthentication().getName());
				entidade = entidadeController.findUserLogado(getAuthentication().getName());
				getExternalContext().getSessionMap().put(USER_LOGADO_SESSAO, entidade);
				sessionController.addSession(entidade.getEnt_login(), 
						(HttpSession) getExternalContext().getSession(true));

			}

		}
		return entidade;
	}

	private String getUserPrincipal() {
		return getExternalContext().getUserPrincipal().getName();
	}

	public ExternalContext getExternalContext() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		return externalContext;

	}
	
	public boolean possuiAcesso(String... acessos) {
		for (String acesso : acessos) {
			for(GrantedAuthority authority: getAuthentication().getAuthorities()) {
				if(authority.getAuthority().trim().equals(acesso.trim())) {
					return true;
				}
				
			}
			
		}
		return false;
		
	}

}
