package dreamsky.ttt.order;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import dreamsky.ttt.dao.jdbc.JdbcDao;

@Repository
public class OrderHistoryDao extends JdbcDao<OrderHistory> {
	public OrderHistoryDao() {
		super(rowMapper, parameterMapper);
	}
	
	private final static RowMapper<OrderHistory> rowMapper = new RowMapper<OrderHistory>() {
		public OrderHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
			OrderHistory orderHistory = new OrderHistory();
			orderHistory.setId(rs.getLong("id"));
			orderHistory.setOrderId(rs.getLong("order_id"));
			orderHistory.setUserId(rs.getLong("user_id"));
			orderHistory.setType(OrderHistory.Type.valueOf(rs.getString("type")));
			orderHistory.setTime(rs.getTimestamp("time"));
			return orderHistory;
		}
	};
	
	private final static ParameterMapper<OrderHistory> parameterMapper = new ParameterMapper<OrderHistory>() {
		public SqlParameterSource mapParameter(OrderHistory t) {
			MapSqlParameterSource parameterSource = new MapSqlParameterSource();
			parameterSource.addValue("orderId", t.getOrderId(), java.sql.Types.BIGINT);
			parameterSource.addValue("userId", t.getUserId(), java.sql.Types.BIGINT);
			parameterSource.addValue("type", t.getType().toString(), java.sql.Types.VARCHAR);
			parameterSource.addValue("time", t.getTime(), java.sql.Types.TIMESTAMP);
			return parameterSource;
		}

		public SqlParameterSource mapParameterWithId(OrderHistory t) {
			MapSqlParameterSource parameterSource = (MapSqlParameterSource)mapParameter(t);
			parameterSource.addValue("id", t.getId(), java.sql.Types.BIGINT);
			return parameterSource;
		}
	};
}
