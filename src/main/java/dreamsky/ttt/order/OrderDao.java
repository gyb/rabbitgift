package dreamsky.ttt.order;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import dreamsky.ttt.dao.jdbc.VersionDao;

@Repository
public class OrderDao extends VersionDao<Order> {

	public OrderDao() {
		super(rowMapper, parameterMapper, "orders");
	}
	
	private final static RowMapper<Order> rowMapper = new RowMapper<Order>() {
		public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
			Order order = new Order();
			order.setId(rs.getLong("id"));
			order.setBuyerId(rs.getLong("buyer_id"));
			order.setSellerId(rs.getLong("seller_id"));
			order.setGoodsId(rs.getLong("goods_id"));
			order.setMoney(rs.getLong("money"));
			order.setNum(rs.getInt("num"));
			order.setReceiverName(rs.getString("receiver_name"));
			order.setAddress(rs.getString("address"));
			order.setPhone(rs.getString("phone"));
			order.setState(Order.State.valueOf(rs.getString("state")));
			order.setLastUpdateTime(rs.getTimestamp("last_update_time"));
			order.setVersion(rs.getInt("version"));
			return order;
		}
	};
	
	private final static ParameterMapper<Order> parameterMapper = new ParameterMapper<Order>() {
		public SqlParameterSource mapParameter(Order t) {
			MapSqlParameterSource parameterSource = new MapSqlParameterSource();
			parameterSource.addValue("buyerId", t.getBuyerId(), java.sql.Types.BIGINT);
			parameterSource.addValue("sellerId", t.getSellerId(), java.sql.Types.BIGINT);
			parameterSource.addValue("goodsId", t.getGoodsId(), java.sql.Types.BIGINT);
			parameterSource.addValue("money", t.getMoney(), java.sql.Types.BIGINT);
			parameterSource.addValue("num", t.getNum(), java.sql.Types.INTEGER);
			parameterSource.addValue("state", t.getState().toString(), java.sql.Types.VARCHAR);
			parameterSource.addValue("receiverName", t.getReceiverName(), java.sql.Types.VARCHAR);
			parameterSource.addValue("address", t.getAddress(), java.sql.Types.VARCHAR);
			parameterSource.addValue("phone", t.getPhone(), java.sql.Types.VARCHAR);
			parameterSource.addValue("lastUpdateTime", t.getLastUpdateTime(), java.sql.Types.TIMESTAMP);
			parameterSource.addValue("version", t.getVersion(), java.sql.Types.INTEGER);
			return parameterSource;
		}

		public SqlParameterSource mapParameterWithId(Order t) {
			MapSqlParameterSource parameterSource = (MapSqlParameterSource)mapParameter(t);
			parameterSource.addValue("id", t.getId(), java.sql.Types.BIGINT);
			return parameterSource;
		}
	};
}
