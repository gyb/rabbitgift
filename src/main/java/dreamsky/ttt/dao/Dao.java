/**
 * 
 */
package dreamsky.ttt.dao;

import java.util.List;
import java.util.Map;

/**
 * @author yibing
 *
 */
public interface Dao<T extends IdEntity> {
	T save(T t);
	void merge(T t);
	void delete(T t);
	void delete(Long id);
	T get(Long id);
	List<T> getAll();
	List<T> findBy(String property, Object value);
	List<T> findBy(String[] properties, Object[] values);
	List<T> findPageBy(String property, Object value, int start, int count);
	List<T> findPageBy(String[] properties, Object[] values, int start, int count);
	T findByUnique(String property, Object value);
	T findByUnique(String[] properties, Object[] values);
	Map<Long, T> findByInClause(List<Long> ids);
	List<T> join(List<T> resultList, String fieldId, String field, Dao<?> dao);
	List<T> join(List<T> resultList, String[] fieldIds, String[] fields, Dao<?>[] daos);
	int count(String property, Object value);
	int count(String[] property, Object[] values);
}
