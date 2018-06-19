/**
 * 
 */
package br.com.project.bean.view;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.project.bean.geral.BeanManagedViewAbstract;
import br.com.project.geral.controller.SessionController;
import br.com.srv.interfaces.SrvLogin;

/**
 * @author Celina
 *
 */

@Controller
@Scope(value="request")
@ManagedBean(name="loginBeanView")
public class LoginBeanView extends BeanManagedViewAbstract{

	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	
	@Resource
	private SessionController sessionController;
	
	@Resource
	private SrvLogin srvLogin;
	
	public void invalidar(ActionEvent actionEvent)  throws Exception{
		
		RequestContext context = RequestContext.getCurrentInstance();
		FacesMessage message = null;
		boolean loggedIn = false;
		
		if(srvLogin.autentico(getUsername(), getPassword())) {
			sessionController.invalidateSession(getUsername());
			loggedIn = true;
			
		}else {
			loggedIn = false;
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Acesso negado", "Login ou senha incorretos");
		}
		
		if(message != null) {
			FacesContext.getCurrentInstance().addMessage("msg", message);
		}
		
		context.addCallbackParam("loggedIn", "Java avan�ado com jsf e primefaces");
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	

}
