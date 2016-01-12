package com.irelint.ttt.user.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.irelint.ttt.dto.AddressDto;

@Entity
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotNull
	@Column private Long userId;

	@NotNull
	@Size(min=2,max=20)
	@Column private String receiverName;
	
	@NotNull
	@Size(min=5,max=100)
	@Column private String address;

	@NotNull
	@Size(min=7,max=20)
	@Column private String phone;
	
	public AddressDto toDto() {
		AddressDto dto = new AddressDto();
		dto.setId(this.id);
		dto.setUserId(this.userId);
		dto.setReceiverName(this.receiverName);
		dto.setAddress(this.address);
		dto.setPhone(this.phone);
		return dto;
	}
	
	public static Address fromDto(AddressDto dto) {
		Address address = new Address();
		address.setAddress(dto.getAddress());
		address.setPhone(dto.getPhone());
		address.setReceiverName(dto.getReceiverName());
		address.setUserId(dto.getUserId());
		return address;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
