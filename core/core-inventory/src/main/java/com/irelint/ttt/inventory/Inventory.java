package com.irelint.ttt.inventory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.Min;

@Entity
public class Inventory {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private Long goodsId;
	
	@Min(0)
	private int availableNumber;
	
	private int selledNumber;
	
	@Version
	private int version;
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public boolean sell(int number) {
		if (this.availableNumber < number) return false;
		
		this.availableNumber -= number;
		this.selledNumber += number;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
