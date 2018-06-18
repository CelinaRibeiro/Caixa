/**
 * 
 */
package br.com.project.geral.controller;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.servlet.http.HttpSession;

/**
 * @author Celina
 *
 */
@ApplicationScoped //única sessão
public interface SessionController extends Serializable{
	
	void addSession(String keyLoginUser, HttpSession httpSession);
	
	void invalidateSession(String keyLoginUser);

}
