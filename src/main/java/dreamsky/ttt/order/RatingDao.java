package dreamsky.ttt.order;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import dreamsky.ttt.dao.jdbc.JdbcDao;

@Repository
public class RatingDao extends JdbcDao<Rating> {
	public RatingDao() {
		super(rowMapper, parameterMapper);
	}

	private final static RowMapper<Rating> rowMapper = new RowMapper<Rating>() {
		public Rating mapRow(ResultSet rs, int rowNum) throws SQLException {
			Rating rating = new Rating();
			rating.setId(rs.getLong("id"));
			rating.setGoodsId(rs.getLong("goods_id"));
			rating.setOrderId(rs.getLong("order_id"));
			rating.setUserId(rs.getLong("user_id"));
			rating.setRatingTime(rs.getTimestamp("rating_time"));
			rating.setNumber(rs.getInt("number"));
			rating.setComment(rs.getString("comment"));
			rating.setBuyTime(rs.getTimestamp("buy_time"));
			rating.setBuyPrice(rs.getLong("buy_price"));
			return rating;
		}
	};
	
	private final static ParameterMapper<Rating> parameterMapper = new ParameterMapper<Rating>() {
		public SqlParameterSource mapParameter(Rating t) {
			MapSqlParameterSource parameterSource = new MapSqlParameterSource();
			parameterSource.addValue("goodsId", t.getGoodsId(), java.sql.Types.BIGINT);
			parameterSource.addValue("orderId", t.getOrderId(), java.sql.Types.BIGINT);
			parameterSource.addValue("userId", t.getUserId(), java.sql.Types.BIGINT);
			parameterSource.addValue("ratingTime", t.getRatingTime(), java.sql.Types.TIMESTAMP);
			parameterSource.addValue("number", t.getNumber(), java.sql.Types.INTEGER);
			parameterSource.addValue("comment", t.getComment(), java.sql.Types.VARCHAR);
			parameterSource.addValue("buyTime", t.getBuyTime(), java.sql.Types.TIMESTAMP);
			parameterSource.addValue("buyPrice", t.getBuyPrice(), java.sql.Types.BIGINT);
			return parameterSource;
		}

		public SqlParameterSource mapParameterWithId(Rating t) {
			MapSqlParameterSource parameterSource = (MapSqlParameterSource)mapParameter(t);
			parameterSource.addValue("id", t.getId(), java.sql.Types.BIGINT);
			return parameterSource;
		}
	};
}
