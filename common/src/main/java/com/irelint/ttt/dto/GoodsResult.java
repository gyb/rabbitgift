package com.irelint.ttt.dto;

public class GoodsResult {
	public boolean result;
	public GoodsDto goods;
	
	public boolean success() {
		return result;
	}
	
	public boolean fail() {
		return !result;
	}
	
	public static GoodsResult fail(GoodsDto goods) {
		GoodsResult result = new GoodsResult();
		result.goods = goods;
		return result;
	}
	
	public static GoodsResult success(GoodsDto goods) {
		GoodsResult result = new GoodsResult();
		result.goods = goods;
		result.result = true;
		return result;
	}
}
