/**
 * 
 */
package br.com.project.bean.view;

import java.util.Date;

import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.project.bean.geral.BeanManagedViewAbstract;

/**
 * @author Celina
 *
 */
@Controller
@Scope(value="session")
@ManagedBean(name="entidadeBeanView")
public class EntidadeBeanView extends BeanManagedViewAbstract{
	
	@Autowired
	private ContextoBean contextoBean;
	
	private static final long serialVersionUID = 1L;

	public String getUsuarioLogadoSecurity() {
		return contextoBean.getAuthentication().getName();
	}
	
	public Date getUltimoAcesso() throws Exception{
		return contextoBean.getEntidadeLogada().getEnt_ultimoacesso();
	}

}
