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
@ApplicationScoped //�nica sess�o
public interface SessionController extends Serializable{
	
	void addSession(String keyLoginUser, HttpSession httpSession);
	
	void invalidateSession(String keyLoginUser);

}
