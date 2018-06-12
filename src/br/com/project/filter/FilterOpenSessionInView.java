/**
 * 
 */
package br.com.project.filter;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.filter.DelegatingFilterProxy;

import br.com.framework.hibernate.session.HibernateUtil;
import br.com.framework.utils.UtilFramework;
import br.com.project.listener.ContextLoaderListenerCaxakiUtils;
import br.com.project.model.classes.Entidade;

/**
 * @author Celina
 *
 */

@WebFilter(filterName="conexaoFilter")
public class FilterOpenSessionInView extends DelegatingFilterProxy implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private static SessionFactory sf;
	
	//Executado apenas uma vez
	//Executado quando a aplicação está sendo iniciada
	@Override
	protected void initFilterBean() throws ServletException {
		sf = HibernateUtil.getSessionFactory();
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		
		//JDBC SPring
		BasicDataSource springBasicDataSource = (BasicDataSource) ContextLoaderListenerCaxakiUtils.getBean("springDataSource");
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		PlatformTransactionManager transactionManager = new DataSourceTransactionManager(springBasicDataSource); 
		TransactionStatus status = transactionManager.getTransaction(def);
		
		try {
			request.setCharacterEncoding("UTF-8");//Define codificação
			
			//captura usuário que faz a operação
			HttpServletRequest request2 = (HttpServletRequest) request;
			HttpSession sessao = request2.getSession();
			Entidade userLogadoSessao = (Entidade) sessao.getAttribute("userLogadoSessao");
			
			if(userLogadoSessao != null) {
				UtilFramework.getThreadLocal().set(userLogadoSessao.getEnt_codigo());
			}
		
		sf.getCurrentSession().beginTransaction();
		//Antes de executar ação (Request)
		chain.doFilter(request, response); //executa a ação no servidor
		//após executa ação (Resposta)
		
		transactionManager.commit(status);
		
		if(sf.getCurrentSession().getTransaction().isActive()) {
			sf.getCurrentSession().flush();
			sf.getCurrentSession().getTransaction().commit();
		}
		
		if(sf.getCurrentSession().isOpen()) {
			sf.getCurrentSession().close();
		}
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
	}catch(Exception e) {
		
		transactionManager.rollback(status);
		
		e.printStackTrace();
		
		if(sf.getCurrentSession().getTransaction().isActive()) {
			sf.getCurrentSession().getTransaction().rollback();
		}
		
		if(sf.getCurrentSession().isOpen()) {
			sf.getCurrentSession().close();
		}
		
		}finally {
			if(sf.getCurrentSession().isOpen()) {
				if(sf.getCurrentSession().beginTransaction().isActive()) {
					sf.getCurrentSession().flush();
					sf.getCurrentSession().clear();
				}
				
				if(sf.getCurrentSession().isOpen()) {
					sf.getCurrentSession().close();
				}
			}
		}
	}
}
