/**
 * 
 */
package br.com.project.listener;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author Celina
 *
 */
@ApplicationScoped //instância única p/ todo projeto
public class ContextLoaderListenerCaxakiUtils extends ContextLoaderListener implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//Retorna todo o contexto do spring, do ambiente de execução dele
	private static WebApplicationContext getWac() {
		return WebApplicationContextUtils.getWebApplicationContext(getCurrentWebApplicationContext().getServletContext());
			}
	
	public static Object getBean(String idNomeBean) {
		return getWac().getBean(idNomeBean);
	}
	
	public static Object getBean(Class<?> classe) {
		return getWac().getBean(classe);
	}

}
