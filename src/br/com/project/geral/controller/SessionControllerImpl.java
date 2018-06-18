/**
 * 
 */
package br.com.project.geral.controller;

import java.util.HashMap;

import javax.faces.bean.ApplicationScoped;
import javax.servlet.http.HttpSession;

/**
 * @author Celina
 *
 */
@ApplicationScoped
public class SessionControllerImpl implements SessionController {

	private static final long serialVersionUID = 1L;

	private HashMap<String, HttpSession> hashMap = new HashMap<String, HttpSession>();

	@Override
	public void addSession(String keyLoginUser, HttpSession httpSession) {
		hashMap.put(keyLoginUser, httpSession);
	}

	@Override
	public void invalidateSession(String keyLoginUser) {
		HttpSession session = hashMap.get(keyLoginUser);

		if (session != null) { // remove sessão do usuário passado como parêmtros
			try {
				session.invalidate();

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Não tem usuário");
		}

		// retira o objeto da lista de controler
		hashMap.remove(keyLoginUser);

	}

}
