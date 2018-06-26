/**
 * 
 */
package br.com.project.converters;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.framework.hibernate.session.HibernateUtil;
import br.com.project.model.classes.Estado;

/**
 * @author Celina
 *
 */

@FacesConverter(forClass = Estado.class)
public class EstadoConverter implements Converter, Serializable{

	private static final long serialVersionUID = 1L;

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String codigo) {
		if(codigo != null && !codigo.isEmpty()) {
			return (Estado) HibernateUtil.getCurrentSession().get(Estado.class, new Long(codigo));
		}
		return codigo;
		
		
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object objeto) {
		if(objeto != null) {
			Estado c = (Estado) objeto;
			return c.getEst_id() != null && c.getEst_id() > 0 ? c.getEst_id().toString() : null;
			
		}
		return null;
	}

}
