/**
 * 2010-8-13
 */
package dreamsky.ttt.dao.jdbc;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import net.sf.cglib.beans.BeanMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.Assert;

import dreamsky.ttt.dao.Column;
import dreamsky.ttt.dao.Dao;
import dreamsky.ttt.dao.IdEntity;

/**
 * @author yibing
 * JDBC DAO Base Class
 */
public abstract class JdbcDao<T extends IdEntity> implements Dao<T> {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
    
	protected JdbcTemplate jdbcTpl;
	protected NamedParameterJdbcTemplate namedJdbcTpl;
	
	protected String tableName;
	protected String sqlInsert;
	protected String sqlGetById;
	protected String sqlGetAll;
	protected String sqlDeleteById;
	protected String sqlUpdate;
	private RowMapper<T> rowMapper;
	protected ParameterMapper<T> parameterMapper;
	
	protected BeanMap protoBeanMap;
	
	private Map<Integer, String> inClauseMap = new HashMap<Integer, String>();
	private final static int TINY_SIZE = 4;
	private final static int SMALL_SIZE = 10;
	private final static int MEDIUM_SIZE = 20;
	private final static int LARGE_SIZE = 51;

	public JdbcDao(RowMapper<T> rowMapper, ParameterMapper<T> parameterMapper) {
		this(rowMapper, parameterMapper, null);
	}

	public JdbcDao(RowMapper<T> rowMapper, ParameterMapper<T> parameterMapper, String tableName) {
		Class<T> classT = getClassT();
		if (tableName == null) {
			tableName = convertPascalOrCamelToUnderscore(classT.getSimpleName());
		}
		this.tableName = tableName;
		this.sqlGetById = "select * from " + tableName + " where id = ?";
		this.sqlGetAll = "select * from " + tableName;
		this.sqlDeleteById = "delete from " + tableName + " where id = ?";
		this.sqlInsert = getInsertSql(classT);
		this.sqlUpdate = getUpdateSql(classT);
		this.rowMapper = rowMapper;
		this.parameterMapper = parameterMapper;

		try {
			this.protoBeanMap = BeanMap.create(classT.newInstance());
		} catch (Exception e) {
			throw new RuntimeException("Initial DAO failed", e);
		}

		inClauseMap.put(TINY_SIZE, "select * from " + tableName + " where id in (?,?,?,?)");
		inClauseMap.put(SMALL_SIZE, "select * from " + tableName + " where id in (?,?,?,?,?,?,?,?,?,?)");
		inClauseMap.put(MEDIUM_SIZE, "select * from " + tableName + " where id in (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		inClauseMap.put(LARGE_SIZE, "select * from " + tableName + " where id in (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	}

	@SuppressWarnings("unchecked")
	private Class<T> getClassT() {
		return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		jdbcTpl = new JdbcTemplate(dataSource);
		namedJdbcTpl = new NamedParameterJdbcTemplate(jdbcTpl);
	}
	
    /**
     * save object to DB and will return generated key
     */
	public T save(T t) {
		if (t.getId() != null) {
			merge(t);
			return t;
		}
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		int result = namedJdbcTpl.update(sqlInsert, parameterMapper.mapParameter(t), keyHolder);
		if (result != 1) {
			//I cannot find a suitable DataAccessException Type, so just throw a RuntimeException
			throw new RuntimeException("insert statement failed!");
		}
		
		t.setId(keyHolder.getKey().longValue());
        logger.debug("save entity: {}", t);
        return t;
	}
    
    /**
     * update object to DB
     */
	public void merge(T t) {
        Assert.notNull(t.getId(), "id cannot be null");
		namedJdbcTpl.update(sqlUpdate, parameterMapper.mapParameterWithId(t));
        logger.debug("merge entity: {}", t);
	}
	
	/**
	 * delete object
	 */
	public void delete(T t) {
		if (t != null) {
			delete(t.getId());
		}
	}
	
    /**
     * delete object by object id
     */
    public void delete(final Long id) {
        Assert.notNull(id, "id cannot be null");
        jdbcTpl.update(sqlDeleteById, id);
        logger.debug("delete entity, id is {}", id);
    }
    
    /**
     * fetch object through id from DB
     */
    public T get(final Long id) {
	    Assert.notNull(id, "id cannot be null");
	    return jdbcTpl.queryForObject(sqlGetById, rowMapper, id);
	}
	
    /**
     * get all objects of this type
     */
    public List<T> getAll() {
        return jdbcTpl.query(sqlGetAll, rowMapper);
    }
    
    /**
     * find objects by property. use equals to match
     */
    public List<T> findBy(final String propertyName, final Object value) {
        Assert.hasText(propertyName, "propertyName cannot be null");
    	String sql = "select * from " + tableName +  " where " + convertPascalOrCamelToUnderscore(propertyName) + " = ?";
        return jdbcTpl.query(sql, rowMapper, value);
    }

    /**
     * count object numbers
     */
    public int count(final String propertyName, final Object value) {
        Assert.hasText(propertyName, "propertyName cannot be null");
    	String sql = "select count(*) from " + tableName +  " where " + convertPascalOrCamelToUnderscore(propertyName) + " = ?";
    	return jdbcTpl.queryForInt(sql, value);
    }
    
    /**
     * count object numbers
     */
    public int count(final String[] propertyNames, final Object[] values) {
    	StringBuilder sql = new StringBuilder("select count(*) from ").append(tableName);
    	boolean first = true;
    	for (String name : propertyNames) {
    		if (first) {
    			sql.append(" where ");
    			first = false;
    		} else {
    			sql.append(" and ");
    		}
    		sql.append(convertPascalOrCamelToUnderscore(name)).append(" = ?");
    	}
    	return jdbcTpl.queryForInt(sql.toString(), values);
    }
    
    /**
     * find objects by property. use equals to match
     */
    public List<T> findBy(final String[] propertyNames, final Object[] values) {
    	StringBuilder sql = new StringBuilder("select * from ").append(tableName);
    	boolean first = true;
    	for (String name : propertyNames) {
    		if (first) {
    			sql.append(" where ");
    			first = false;
    		} else {
    			sql.append(" and ");
    		}
    		sql.append(convertPascalOrCamelToUnderscore(name)).append(" = ?");
    	}
    	return jdbcTpl.query(sql.toString(), rowMapper, values);
    }

    /**
     * find page
     */
    public List<T> findPageBy(String propertyName, Object value, int start, int count) {
        Assert.hasText(propertyName, "propertyName cannot be null");
    	String sql = "select * from " + tableName +  " where " + convertPascalOrCamelToUnderscore(propertyName) + " = ?"
    			+ " limit ?,?";
        return jdbcTpl.query(sql, rowMapper, value, start, count);
	}

    /**
     * find page
     */
	public List<T> findPageBy(String[] propertyNames, Object[] values, int start, int count) {
    	StringBuilder sql = new StringBuilder("select * from ").append(tableName);
    	boolean first = true;
    	for (String name : propertyNames) {
    		if (first) {
    			sql.append(" where ");
    			first = false;
    		} else {
    			sql.append(" and ");
    		}
    		sql.append(convertPascalOrCamelToUnderscore(name)).append(" = ?");
    	}
    	sql.append(" limit ").append(start).append(",").append(count);
    	return jdbcTpl.query(sql.toString(), rowMapper, values);
	}

    /**
     * find unique object
     */
    public T findByUnique(final String propertyName, final Object value) {
        Assert.hasText(propertyName, "propertyName cannot be null");
    	String sql = "select * from " + tableName +  " where " + convertPascalOrCamelToUnderscore(propertyName) + " = ?";
    	return jdbcTpl.queryForObject(sql, rowMapper, value);
    }

    /**
     * find unique object
     */
    public T findByUnique(final String[] propertyNames, final Object[] values) {
    	StringBuilder sql = new StringBuilder("select * from ").append(tableName);
    	boolean first = true;
    	for (String name : propertyNames) {
    		if (first) {
    			sql.append(" where ");
    			first = false;
    		} else {
    			sql.append(" and ");
    		}
    		sql.append(convertPascalOrCamelToUnderscore(name)).append(" = ?");
    	}
    	return jdbcTpl.queryForObject(sql.toString(), rowMapper, values);
    }

    /**
     * find by in clause
     */
    public Map<Long, T> findByInClause(List<Long> ids) {
    	int size = ids.size();
    	Map<Long, T> results = new HashMap<Long, T>();

    	int offset = 0;
    	while (size >= LARGE_SIZE) {
    		Object[] inIds = new Object[LARGE_SIZE];
    		for (int i=0; i<LARGE_SIZE; i++) {
    			inIds[i] = ids.get(i + offset);
    		}
    		for (T t : jdbcTpl.query(inClauseMap.get(LARGE_SIZE), rowMapper, inIds)) {
    			results.put(t.getId(), t);
    		}
    		size -= LARGE_SIZE;
    		offset += LARGE_SIZE;
    	}
    	while (size >= MEDIUM_SIZE) {
    		Object[] inIds = new Object[MEDIUM_SIZE];
    		for (int i=0; i<MEDIUM_SIZE; i++) {
    			inIds[i] = ids.get(i + offset);
    		}
    		for (T t : jdbcTpl.query(inClauseMap.get(MEDIUM_SIZE), rowMapper, inIds)) {
    			results.put(t.getId(), t);
    		}
    		size -= MEDIUM_SIZE;
    		offset += MEDIUM_SIZE;
    	}
    	while (size >= SMALL_SIZE) {
    		Object[] inIds = new Object[SMALL_SIZE];
    		for (int i=0; i<SMALL_SIZE; i++) {
    			inIds[i] = ids.get(i + offset);
    		}
    		for (T t : jdbcTpl.query(inClauseMap.get(SMALL_SIZE), rowMapper, inIds)) {
    			results.put(t.getId(), t);
    		}
    		size -= SMALL_SIZE;
    		offset += SMALL_SIZE;
    	}
    	while (size >= TINY_SIZE) {
    		Object[] inIds = new Object[TINY_SIZE];
    		for (int i=0; i<TINY_SIZE; i++) {
    			inIds[i] = ids.get(i + offset);
    		}
    		for (T t : jdbcTpl.query(inClauseMap.get(TINY_SIZE), rowMapper, inIds)) {
    			results.put(t.getId(), t);
    		}
    		size -= TINY_SIZE;
    		offset += TINY_SIZE;
    	}
    	while (size > 0) {
    		T t = this.get(ids.get(offset));
    		results.put(t.getId(), t);
    		size--;
    		offset++;
    	}

    	return results;
    }
    
    /**
     * Join another table, for one-to-one or many-to-one relations
     */
    public List<T> join(List<T> resultList, String fieldId, String field, Dao<?> dao) {
    	if (resultList == null || resultList.isEmpty()) {
    		return resultList;
    	}

		BeanMap beanMap = this.protoBeanMap.newInstance(resultList.get(0));
    	putFields(resultList, fieldId, field, dao, beanMap);

    	return resultList;
    }

	private void putFields(List<T> resultList, String fieldId, String field, Dao<?> dao, BeanMap beanMap) {
   		List<Long> ids = new ArrayList<Long>(resultList.size());
   		for (T t : resultList) {
   			beanMap.setBean(t);
   			ids.add((Long)beanMap.get(fieldId));
   		}
   		Map<Long, ?> fieldsResult = dao.findByInClause(ids);
   		int index = 0;
   		for (T t : resultList) {
   			beanMap.setBean(t);
   			beanMap.put(field, fieldsResult.get(ids.get(index++)));
   		}
	}
    
    
    /**
     * Join other tables, for one-to-one or many-to-one relations
     */
    public List<T> join(List<T> resultList, String[] fieldIds, String[] fields, Dao<?>[] daos) {
    	if (resultList == null || resultList.isEmpty()) {
    		return resultList;
    	}

    	BeanMap beanMap = this.protoBeanMap.newInstance(resultList.get(0));
    	for (int i=0; i<fields.length; i++) {
    		putFields(resultList, fieldIds[i], fields[i], daos[i], beanMap);
    	}

    	return resultList;
    }
    
    public List<T> joinProperty(List<T> resultList, String fieldId, String property, JdbcDao<?> dao) {
    	if (resultList == null || resultList.isEmpty()) {
    		return resultList;
    	}

		BeanMap beanMap = this.protoBeanMap.newInstance(resultList.get(0));
		BeanMap fieldBeanMap = dao.protoBeanMap.newInstance(dao.getClassT());
   		List<Long> ids = new ArrayList<Long>(resultList.size());
   		for (T t : resultList) {
   			beanMap.setBean(t);
   			ids.add((Long)beanMap.get(fieldId));
   		}
   		Map<Long, ?> fieldsResult = dao.findByInClause(ids);
   		int index = 0;
   		for (T t : resultList) {
   			beanMap.setBean(t);
   			fieldBeanMap.setBean(fieldsResult.get(ids.get(index++)));
   			beanMap.put(property, fieldBeanMap.get(property));
   		}

    	return resultList;
    }

    protected String getInsertSql(Class<T> classT) {
		StringBuilder sb = new StringBuilder("insert into ").append(tableName).append(" ( ");
		
		for (Class<? super T> c = classT; c != null; c = c.getSuperclass()) {
			for (Field field : c.getDeclaredFields()) {
				if (field.isAnnotationPresent(Column.class)) {
					String fieldName = field.getName();
					if (fieldName.equals("id")) continue;
					String underscoreName = convertPascalOrCamelToUnderscore(fieldName);
					sb.append(underscoreName).append(",");
				}
			}
		}
		sb.replace(sb.length() - 1, sb.length(), ")");
		
		sb.append(" values (");
		for (Class<? super T> c = classT; c != null; c = c.getSuperclass()) {
			for (Field field : c.getDeclaredFields()) {
				if (field.isAnnotationPresent(Column.class)) {
					String fieldName = field.getName();
					if (fieldName.equals("id")) continue;
					sb.append(":").append(fieldName).append(",");
				}
			}
		}
		sb.replace(sb.length() - 1, sb.length(), ")");
		return sb.toString();
    }

    protected String getUpdateSql(Class<T> classT) {
		StringBuilder sb = new StringBuilder("update ").append(tableName).append(" set ");
		
		//travel all fields of this and parents
		for (Class<? super T> c = classT; c != null; c = c.getSuperclass()) {
			for (Field field : c.getDeclaredFields()) {
				if (field.isAnnotationPresent(Column.class)) {
					String fieldName = field.getName();
					if (fieldName.equals("id")) continue;
					String underscoreName = convertPascalOrCamelToUnderscore(fieldName);
					sb.append(underscoreName).append("=:").append(fieldName).append(",");
				}
			}
		}
		
		sb.replace(sb.length() - 1, sb.length(), " ").append("where id=:id");
		return sb.toString();
	}

    protected static String convertPascalOrCamelToUnderscore(String src) {
    	if (src == null) return null;
    	
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < src.length(); i++) {
            char c = src.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append("_").append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        String ret = sb.toString();
        return ret.startsWith("_") ? ret.substring(1) : ret;
    }

    public static interface ParameterMapper<T> {
    	SqlParameterSource mapParameter(T t); 
    	SqlParameterSource mapParameterWithId(T t); 
    }
}