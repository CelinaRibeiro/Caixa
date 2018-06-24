/**
 * 
 */
package br.com.repository.interfaces;

import java.io.Serializable;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Celina
 *
 */

@Transactional //camada que faz parte do bd
@Repository
public interface RepositoryCidade extends Serializable {

}
