package dreamsky.ttt.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import dreamsky.ttt.dao.jdbc.JdbcDao;

@Repository
public class AddressDao extends JdbcDao<Address> {
	public AddressDao() {
		super(rowMapper, parameterMapper);
	}
	
	private final static RowMapper<Address> rowMapper = new RowMapper<Address>() {
		public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
			Address address = new Address();
			address.setId(rs.getLong("id"));
			address.setUserId(rs.getLong("user_id"));
			address.setReceiverName(rs.getString("receiver_name"));
			address.setAddress(rs.getString("address"));
			address.setPhone(rs.getString("phone"));
			return address;
		}
	};
	
	private final static ParameterMapper<Address> parameterMapper = new ParameterMapper<Address>() {
		public SqlParameterSource mapParameter(Address t) {
			MapSqlParameterSource parameterSource = new MapSqlParameterSource();
			parameterSource.addValue("userId", t.getUserId(), java.sql.Types.BIGINT);
			parameterSource.addValue("receiverName", t.getReceiverName(), java.sql.Types.VARCHAR);
			parameterSource.addValue("address", t.getAddress(), java.sql.Types.VARCHAR);
			parameterSource.addValue("phone", t.getPhone(), java.sql.Types.VARCHAR);
			return parameterSource;
		}

		public SqlParameterSource mapParameterWithId(Address t) {
			MapSqlParameterSource parameterSource = (MapSqlParameterSource)mapParameter(t);
			parameterSource.addValue("id", t.getId(), java.sql.Types.BIGINT);
			return parameterSource;
		}
	};

}
