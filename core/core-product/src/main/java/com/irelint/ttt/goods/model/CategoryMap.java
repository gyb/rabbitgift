package com.irelint.ttt.goods.model;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class CategoryMap {
	private Map<Integer, String> map;
	
	public CategoryMap() {
		Map<Integer, String> m = new HashMap<>();
		m.put(1, "服装鞋帽");
		m.put(2, "家用电器");
		m.put(3, "酒水饮料");
		m.put(4, "图书音像");
		m.put(5, "电脑数码");
		m.put(6, "母婴用品");
		map = Collections.unmodifiableMap(m);
	}
	
	public Map<Integer, String> getMap() {
		return map; 
	}
}
