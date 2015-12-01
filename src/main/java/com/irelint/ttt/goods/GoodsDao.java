package com.irelint.ttt.goods;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.irelint.ttt.dao.jdbc.VersionDao;

@Repository
public class GoodsDao extends VersionDao<Goods> {
	
	public GoodsDao() {
		super(rowMapper, parameterMapper);
	}

	private static RowMapper<Goods> rowMapper = new RowMapper<Goods>() {
		public Goods mapRow(ResultSet rs, int rowNum) throws SQLException {
			Goods goods = new Goods();
			goods.setId(rs.getLong("id"));
			goods.setUserId(rs.getLong("user_id"));
			goods.setCategoryId(rs.getInt("category_id"));
			goods.setName(rs.getString("name"));
			goods.setDescription(rs.getString("description"));
			goods.setPicUrl(rs.getString("pic_url"));
			goods.setPrice(rs.getLong("price"));
			goods.setAvailableNumber(rs.getInt("available_number"));
			goods.setSelledNumber(rs.getInt("selled_number"));
			goods.setState(Goods.State.valueOf(rs.getString("state")));
			goods.setOnlineTime(rs.getTimestamp("online_time"));
			goods.setOfflineTime(rs.getTimestamp("offline_time"));
			goods.setRatingNumber(rs.getInt("rating_number"));
			goods.setRatingTimes(rs.getInt("rating_times"));
			goods.setAverageRating(rs.getFloat("average_rating"));
			goods.setRatingStars1(rs.getInt("rating_stars1"));
			goods.setRatingStars2(rs.getInt("rating_stars2"));
			goods.setRatingStars3(rs.getInt("rating_stars3"));
			goods.setRatingStars4(rs.getInt("rating_stars4"));
			goods.setRatingStars5(rs.getInt("rating_stars5"));
			goods.setVersion(rs.getInt("version"));
			return goods;
		}
	};
	
	private static ParameterMapper<Goods> parameterMapper = new ParameterMapper<Goods>() {
		public SqlParameterSource mapParameter(Goods t) {
			MapSqlParameterSource parameterSource = new MapSqlParameterSource();
			parameterSource.addValue("userId", t.getUserId(), java.sql.Types.BIGINT);
			parameterSource.addValue("categoryId", t.getCategoryId(), java.sql.Types.INTEGER);
			parameterSource.addValue("name", t.getName(), java.sql.Types.VARCHAR);
			parameterSource.addValue("description", t.getDescription(), java.sql.Types.CLOB);
			parameterSource.addValue("picUrl", t.getPicUrl(), java.sql.Types.VARCHAR);
			parameterSource.addValue("price", t.getPrice(), java.sql.Types.BIGINT);
			parameterSource.addValue("availableNumber", t.getAvailableNumber(), java.sql.Types.INTEGER);
			parameterSource.addValue("selledNumber", t.getSelledNumber(), java.sql.Types.INTEGER);
			parameterSource.addValue("state", t.getState().name(), java.sql.Types.VARCHAR);
			parameterSource.addValue("onlineTime", t.getOnlineTime(), java.sql.Types.TIMESTAMP);
			parameterSource.addValue("offlineTime", t.getOfflineTime(), java.sql.Types.TIMESTAMP);
			parameterSource.addValue("ratingNumber", t.getRatingNumber(), java.sql.Types.INTEGER);
			parameterSource.addValue("ratingTimes", t.getRatingTimes(), java.sql.Types.INTEGER);
			parameterSource.addValue("averageRating", t.getAverageRating(), java.sql.Types.FLOAT);
			parameterSource.addValue("ratingStars1", t.getRatingStars1(), java.sql.Types.INTEGER);
			parameterSource.addValue("ratingStars2", t.getRatingStars2(), java.sql.Types.INTEGER);
			parameterSource.addValue("ratingStars3", t.getRatingStars3(), java.sql.Types.INTEGER);
			parameterSource.addValue("ratingStars4", t.getRatingStars4(), java.sql.Types.INTEGER);
			parameterSource.addValue("ratingStars5", t.getRatingStars5(), java.sql.Types.INTEGER);
			parameterSource.addValue("version", t.getVersion(), java.sql.Types.INTEGER);
			return parameterSource;
		}

		public SqlParameterSource mapParameterWithId(Goods t) {
			MapSqlParameterSource parameterSource = (MapSqlParameterSource)mapParameter(t);
			parameterSource.addValue("id", t.getId(), java.sql.Types.BIGINT);
			return parameterSource;
		}
	};

}
