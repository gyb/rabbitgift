package com.irelint.ttt.goods;

public class GoodsResult {
	public boolean result;
	public Goods goods;
	
	public boolean success() {
		return result;
	}
	
	public boolean fail() {
		return !result;
	}
	
	public static GoodsResult fail(Goods goods) {
		GoodsResult result = new GoodsResult();
		result.goods = goods;
		return result;
	}
	
	public static GoodsResult success(Goods goods) {
		GoodsResult result = new GoodsResult();
		result.goods = goods;
		result.result = true;
		return result;
	}
}
