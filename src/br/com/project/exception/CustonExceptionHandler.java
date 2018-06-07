/**
 * 
 */
package br.com.project.exception;

import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.hibernate.SessionFactory;
import org.primefaces.context.RequestContext;

import br.com.framework.hibernate.session.HibernateUtil;

/**
 * @author Celina
 *
 */
//Classe respons�vel por interceptar os erros
public class CustonExceptionHandler extends ExceptionHandlerWrapper{
	
	private ExceptionHandler wrapperd;
	
	final FacesContext facesContext = FacesContext.getCurrentInstance();
	
	final Map<String, Object> requestMap = facesContext.getExternalContext().getRequestMap();
	
	final NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
	
	public CustonExceptionHandler(ExceptionHandler exceptionHandler) {
		this.wrapperd = exceptionHandler;
	}

	/*retorna a classe de erro.
	 * Sobrescreve o m�todo ExceptionHandler que retorna a "pilha" de excess�es
	 * (non-Javadoc)
	 * @see javax.faces.context.ExceptionHandlerWrapper#getWrapped()
	 */
	@Override
	public ExceptionHandler getWrapped() {
		return wrapperd;
	}
	
	/* Manipula todo o erro
	 * Sobrescreve o m�todo handle que � respons�vel por manipular as excess�es do JSF
	 * (non-Javadoc)
	 * @see javax.faces.context.ExceptionHandlerWrapper#handle()
	 */
	@Override
	public void handle() throws FacesException {
		final Iterator<ExceptionQueuedEvent> iterator = getUnhandledExceptionQueuedEvents().iterator();
		
		while(iterator.hasNext()) {
			ExceptionQueuedEvent event = iterator.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
			
			//Recuperar a excess�o do contexto
			Throwable exception = context.getException();
			
			//Aqui trabalhamos a excess�o
			//No try ser� manipulado o erro dos erros
			try {
				
				requestMap.put("exceptionMessage", exception.getMessage());
				
				//Passo a msg P2 se conter o erro de viola��o de chave no DB
				if(exception != null &&
						exception.getMessage() != null
						&& exception.getMessage().indexOf("ConstraintViolationException") != -1) {
					
					FacesContext.getCurrentInstance().
					addMessage("msg", new FacesMessage(FacesMessage.
							SEVERITY_WARN, "Registro n�o pode ser removido por"
							+ " estar associado.", ""));
				}
				//Passo a msg P2 se conter o erro org.hibernate.StaleObjectStateException
				//Esse erro � qdo dois usu�rios est�o manipulando ao mesmo tempo o mesmo objeto
				else if(exception != null &&
						exception.getMessage()!= null
						&& exception.getMessage().indexOf("org.hibernate.StaleObjectStateException") != -1) {
					
					FacesContext.getCurrentInstance().
					addMessage("msg", new FacesMessage(FacesMessage.
							SEVERITY_ERROR, "Registro foi atualizado ou exclu�do por outro usu�rio."
							+ "Consulte novamente.", ""));
				}else {
					//Sess�o de erros qdo for usado o AJAX
					//Avisa o usu�rio do erro
					FacesContext.getCurrentInstance().
					addMessage("msg", new FacesMessage(FacesMessage.
							SEVERITY_FATAL, "O sistema se recuperou de um erro inesperado.", ""));
					
					//Tranquiliza o usu�rio para que ele continue usando o sistema
					FacesContext.getCurrentInstance().
					addMessage("msg", new FacesMessage(FacesMessage.
							SEVERITY_INFO, "Voc� pode contiuar usando o sistema normalmente!", ""));
					
					FacesContext.getCurrentInstance().
					addMessage("msg", new FacesMessage(FacesMessage.
							SEVERITY_FATAL, "O erro foi causado por:\n" + exception.getMessage(), ""));
					
					//esse alert apenas � exibido se a p�gina n�o redirecionar 
					//Casso n�o ocorra os alets acima, o primefaces mostra a msg em alet javascript
					RequestContext.getCurrentInstance().
					execute("alert('O sistema se recuperou de um erro inesperado.')");
					
					//Passando o alert javascript por meio de janela
					RequestContext.getCurrentInstance().
					showMessageInDialog(new FacesMessage(FacesMessage.
							SEVERITY_INFO, "Erro", "O sistema se recuperou de um erro inesperado.."));
					
					//Redireciona para p�gina de erro
					navigationHandler.handleNavigation(facesContext, null,
							"/error/error.jsf?faces-redirect=true&expired=true");
					
				}
				//Renderiza a p�gina de erro e exibe as mensagens
				facesContext.renderResponse();
				
						
			}finally {
				SessionFactory sf = HibernateUtil.getSessionFactory();
				if(sf.getCurrentSession().getTransaction().isActive()) {
					sf.getCurrentSession().getTransaction().rollback();
				}
				
				//imprime o erro no console -> printStackTrace
				exception.printStackTrace();
				
				//Remove o objeto de excess�o
				iterator.remove();
			}
		}
		
		//Ap�s as mensagens de erro finaliza-se aqui a manipula��o
		getWrapped().handle();
		
		
	}

}
