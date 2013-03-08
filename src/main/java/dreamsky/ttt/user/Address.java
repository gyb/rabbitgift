package dreamsky.ttt.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import dreamsky.ttt.dao.Column;
import dreamsky.ttt.dao.IdEntity;

public class Address implements IdEntity {
	private static final long serialVersionUID = 1L;
	
	@Column private Long id;
	
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
