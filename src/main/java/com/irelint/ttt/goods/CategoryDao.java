package com.irelint.ttt.goods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryDao {
	private JdbcTemplate jdbcTpl;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		jdbcTpl = new JdbcTemplate(dataSource);
	}

	public Map<Integer, String> getAll() {
		Map<Integer, String> result = new HashMap<Integer, String>();
		List<Map<String, Object>> list = jdbcTpl.queryForList("select * from category");
		for (Map<String, Object> map : list) {
			result.put((Integer)map.get("id"), (String)map.get("name"));
		}
		return result;
	}
}
