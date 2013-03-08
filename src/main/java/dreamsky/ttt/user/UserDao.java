package dreamsky.ttt.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import dreamsky.ttt.dao.jdbc.JdbcDao;

@Repository
public class UserDao extends JdbcDao<User> {

	public UserDao() {
		super(rowMapper, parameterMapper);
	}

	private static RowMapper<User> rowMapper = new RowMapper<User>() {
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getLong("id"));
			user.setLogin(rs.getString("login"));
			user.setPasswordMd5(rs.getString("password_md5"));
			user.setEmail(rs.getString("email"));
			user.setCreateTime(rs.getTimestamp("create_time"));
			return user;
		}
	};
	
	private static ParameterMapper<User> parameterMapper = new ParameterMapper<User>() {
		public SqlParameterSource mapParameter(User t) {
			MapSqlParameterSource parameterSource = new MapSqlParameterSource();
			parameterSource.addValue("login", t.getLogin(), java.sql.Types.VARCHAR);
			parameterSource.addValue("passwordMd5", t.getPasswordMd5(), java.sql.Types.CHAR);
			parameterSource.addValue("email", t.getEmail(), java.sql.Types.VARCHAR);
			return parameterSource;
		}

		public SqlParameterSource mapParameterWithId(User t) {
			MapSqlParameterSource parameterSource = (MapSqlParameterSource)mapParameter(t);
			parameterSource.addValue("id", t.getId(), java.sql.Types.BIGINT);
			return parameterSource;
		}
	};
}
