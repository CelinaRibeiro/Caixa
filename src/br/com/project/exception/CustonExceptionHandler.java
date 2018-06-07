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
//Classe responsável por interceptar os erros
public class CustonExceptionHandler extends ExceptionHandlerWrapper{
	
	private ExceptionHandler wrapperd;
	
	final FacesContext facesContext = FacesContext.getCurrentInstance();
	
	final Map<String, Object> requestMap = facesContext.getExternalContext().getRequestMap();
	
	final NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
	
	public CustonExceptionHandler(ExceptionHandler exceptionHandler) {
		this.wrapperd = exceptionHandler;
	}

	/*retorna a classe de erro.
	 * Sobrescreve o método ExceptionHandler que retorna a "pilha" de excessões
	 * (non-Javadoc)
	 * @see javax.faces.context.ExceptionHandlerWrapper#getWrapped()
	 */
	@Override
	public ExceptionHandler getWrapped() {
		return wrapperd;
	}
	
	/* Manipula todo o erro
	 * Sobrescreve o método handle que é responsável por manipular as excessões do JSF
	 * (non-Javadoc)
	 * @see javax.faces.context.ExceptionHandlerWrapper#handle()
	 */
	@Override
	public void handle() throws FacesException {
		final Iterator<ExceptionQueuedEvent> iterator = getUnhandledExceptionQueuedEvents().iterator();
		
		while(iterator.hasNext()) {
			ExceptionQueuedEvent event = iterator.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
			
			//Recuperar a excessão do contexto
			Throwable exception = context.getException();
			
			//Aqui trabalhamos a excessão
			//No try será manipulado o erro dos erros
			try {
				
				requestMap.put("exceptionMessage", exception.getMessage());
				
				//Passo a msg P2 se conter o erro de violação de chave no DB
				if(exception != null &&
						exception.getMessage() != null
						&& exception.getMessage().indexOf("ConstraintViolationException") != -1) {
					
					FacesContext.getCurrentInstance().
					addMessage("msg", new FacesMessage(FacesMessage.
							SEVERITY_WARN, "Registro não pode ser removido por"
							+ " estar associado.", ""));
				}
				//Passo a msg P2 se conter o erro org.hibernate.StaleObjectStateException
				//Esse erro é qdo dois usuários estão manipulando ao mesmo tempo o mesmo objeto
				else if(exception != null &&
						exception.getMessage()!= null
						&& exception.getMessage().indexOf("org.hibernate.StaleObjectStateException") != -1) {
					
					FacesContext.getCurrentInstance().
					addMessage("msg", new FacesMessage(FacesMessage.
							SEVERITY_ERROR, "Registro foi atualizado ou excluído por outro usuário."
							+ "Consulte novamente.", ""));
				}else {
					//Sessão de erros qdo for usado o AJAX
					//Avisa o usuário do erro
					FacesContext.getCurrentInstance().
					addMessage("msg", new FacesMessage(FacesMessage.
							SEVERITY_FATAL, "O sistema se recuperou de um erro inesperado.", ""));
					
					//Tranquiliza o usuário para que ele continue usando o sistema
					FacesContext.getCurrentInstance().
					addMessage("msg", new FacesMessage(FacesMessage.
							SEVERITY_INFO, "Você pode contiuar usando o sistema normalmente!", ""));
					
					FacesContext.getCurrentInstance().
					addMessage("msg", new FacesMessage(FacesMessage.
							SEVERITY_FATAL, "O erro foi causado por:\n" + exception.getMessage(), ""));
					
					//esse alert apenas é exibido se a página não redirecionar 
					//Casso não ocorra os alets acima, o primefaces mostra a msg em alet javascript
					RequestContext.getCurrentInstance().
					execute("alert('O sistema se recuperou de um erro inesperado.')");
					
					//Passando o alert javascript por meio de janela
					RequestContext.getCurrentInstance().
					showMessageInDialog(new FacesMessage(FacesMessage.
							SEVERITY_INFO, "Erro", "O sistema se recuperou de um erro inesperado.."));
					
					//Redireciona para página de erro
					navigationHandler.handleNavigation(facesContext, null,
							"/error/error.jsf?faces-redirect=true&expired=true");
					
				}
				//Renderiza a página de erro e exibe as mensagens
				facesContext.renderResponse();
				
						
			}finally {
				SessionFactory sf = HibernateUtil.getSessionFactory();
				if(sf.getCurrentSession().getTransaction().isActive()) {
					sf.getCurrentSession().getTransaction().rollback();
				}
				
				//imprime o erro no console -> printStackTrace
				exception.printStackTrace();
				
				//Remove o objeto de excessão
				iterator.remove();
			}
		}
		
		//Após as mensagens de erro finaliza-se aqui a manipulação
		getWrapped().handle();
		
		
	}

}
