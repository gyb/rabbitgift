package com.irelint.ttt.dto;

public class InventoryDto {
	private Long goodsId;
	private int availableNumber;
	private int selledNumber;
	
	public Long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
	public int getAvailableNumber() {
		return availableNumber;
	}
	public void setAvailableNumber(int availableNumber) {
		this.availableNumber = availableNumber;
	}
	public int getSelledNumber() {
		return selledNumber;
	}
	public void setSelledNumber(int selledNumber) {
		this.selledNumber = selledNumber;
	}
	
}