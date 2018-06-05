/**
 * 
 */
package br.com.project.bean.geral;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.web.context.request.FacesRequestAttributes;

/**
 * @author Celina
 *
 */
@SuppressWarnings("unchecked")
public class ViewScope implements Scope, Serializable{
	
	//deve ser criado antes de subscrever o m�todo remove
	public static final String VIEW_SCOPE_CALLBACKS = "viewScope.callbacks";

	private static final long serialVersionUID = 1L;

	@Override
	public Object get(String name, ObjectFactory<?> objectFactory) {
		Object instance = getViewMap().get(name);
		if(instance == null) {
			instance = objectFactory.getObject();
			getViewMap().put(name, instance);
		}
		return instance;
	}

	
	@Override
	//m�todo que carrega o ID que identifica os escopos
	public String getConversationId() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesRequestAttributes facesRequestAttributes = new FacesRequestAttributes(facesContext);
		return facesRequestAttributes.getSessionId() + "-" + facesContext.getViewRoot().getViewId();
	}

	@Override
	//registra a destrui��o do escopo
	public void registerDestructionCallback(String name, Runnable runnable) {
		Map<String, Runnable> callbacks = (Map<String, Runnable>) getViewMap().get(VIEW_SCOPE_CALLBACKS);
		if(callbacks != null) {
			callbacks.put(VIEW_SCOPE_CALLBACKS, runnable);
		}
		
	}

	@Override
	public Object remove(String name) {
		Object instance = getViewMap().remove(name);
		if(instance != null) {
			Map<String, Runnable> callBacks = (Map<String, Runnable>) getViewMap().get(VIEW_SCOPE_CALLBACKS);
			if(callBacks != null) {
				callBacks.remove(name);
			}
		}
		return instance;
	}

	@Override
	//objeto que resolve refer�ncia dos objetos do escopo
	public Object resolveContextualObject(String name) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesRequestAttributes facesRequestAttributes = new FacesRequestAttributes(facesContext);
		return facesRequestAttributes.resolveReference(name);
	}
	
	//getViewRoot ()
	//Retorna o componente raiz que est� associado a esta solicita��o(request)
	//getViewMap - retorna um Map(lista) que atua como a interface para o armazenamento de dados
	private Map<String, Object> getViewMap(){
		return FacesContext.getCurrentInstance() != null ?
				FacesContext.getCurrentInstance().getViewRoot().getViewMap() : new HashMap<String, Object>();
	}

}
