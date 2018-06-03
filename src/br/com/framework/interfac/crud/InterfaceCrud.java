/**
 * 
 */
package br.com.framework.interfac.crud;

import java.io.Serializable;
import java.util.List;

import org.apache.poi.hssf.record.formula.functions.T;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Celina
 *
 */
@Component
@Transactional
public interface InterfaceCrud<T> extends Serializable {

	// salva os dados
	void save(T obj) throws Exception;

	void persist(T obj) throws Exception;

	// salva ou atualiza
	void saveOrUpdate(T obj) throws Exception;

	// realiza o update/atualiza��o de dados
	void update(T obj) throws Exception;

	// realiza o delete de dados
	void delete(T obj) throws Exception;

	// salva ou atualiza e retorna o objeto em estado persistente
	T merge(T obj) throws Exception;

	// carrega a lista de dados de determinada classe
	List<T> findList(Class<T> objs) throws Exception;

	// busca o objeto
	Object findById(Class<T> entidade, Long id) throws Exception;

	T findByPorId(Class<T> entidade, Long id) throws Exception;

	// lista passando uma query din�mica e recebe uma query
	List<T> findListByQueryDinamica(String s) throws Exception;

	// executar update com HQL
	void executeUpdateQueryDinamica(String s) throws Exception;

	// executar update com SQL
	void executeUpdateSQLDinamica(String s) throws Exception;

	// limpa a sess�o do Hibernate
	void clearSession() throws Exception;

	// retira um objeto da sess�o do hibernate
	void evict(Object objs) throws Exception;

	Session getSession() throws Exception;

	List<?> getListSQLDinamica(String sql) throws Exception;

	/// JDBC do Spring - cria-se as classes e busca-as na classe ImplementacaoCrud
	JdbcTemplate getJdbcTemplate();

	SimpleJdbcTemplate getSimpleJdbcTemplate();

	SimpleJdbcInsert getSimpleJdbcInsert();
	
	Long totalRegistro(String table) throws Exception;

	Query obterQuery(String query) throws Exception;

	// carregamento din�mico com JSF e PrimeFaces
	List<T> findListByQueryDinamica(String query, int iniciaNoRegistro, int maximoResultado) throws Exception;

}
