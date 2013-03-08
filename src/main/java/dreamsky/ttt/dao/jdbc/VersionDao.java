package dreamsky.ttt.dao.jdbc;

import java.lang.reflect.Field;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

import dreamsky.ttt.dao.Column;
import dreamsky.ttt.dao.VersionIdEntity;

public abstract class VersionDao<T extends VersionIdEntity> extends JdbcDao<T> {
	
	public VersionDao(RowMapper<T> rowMapper, ParameterMapper<T> parameterMapper) {
		super(rowMapper, parameterMapper);
	}

	public VersionDao(RowMapper<T> rowMapper, ParameterMapper<T> parameterMapper, String tableName) {
		super(rowMapper, parameterMapper, tableName);
	}

	@Override
	public void merge(T t) {
        Assert.notNull(t.getId(), "id cannot be null");
        if (namedJdbcTpl.update(sqlUpdate, parameterMapper.mapParameterWithId(t)) < 1) {
			String msg = "concurrent modification for entity " + tableName + " " + t.getId();
			logger.warn(msg);
			throw new OptimisticLockingFailureException(msg);
        }
        t.setVersion(t.getVersion() + 1);
        logger.debug("merge entity: {}", t);
	}

	@Override
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
		
		sb.append("version=:version+1 where id=:id and version=:version");
		return sb.toString();
	}

}
