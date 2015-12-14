package com.irelint.ttt.user.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.util.DigestUtils;

/**
 * @author yibing
 *
 */
@Entity
public class User implements Serializable {
	private static final long serialVersionUID = -8182450292866499015L;

	private static final String SALT = "ut&st^et%r";
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotNull
	@Size(min=2, max=18)
	@Column(unique=true)
	private String login;
	
	private String passwordMd5;
	
	@NotNull
	@Size(min=5, max=60)
	@Column(unique=true)
	private String email;

	private Date createTime;
	
	@NotNull
	@Size(min=6, max=30)
	private String password;
	
	public User() {}
	
	public User(Long id) {
		this.id = id;
	}
	
	public void setPassword(String password) {
		this.password = password;
		this.passwordMd5 = md5Pass(password);
	}
	
	/* (non-Javadoc)
	 * @see com.irelint.ttt.user.model.IUser#checkPassword(java.lang.String)
	 */
	public boolean checkPassword(String password) {
		return md5Pass(password).equals(passwordMd5);
	}
	
	private String md5Pass(String password) {
		return DigestUtils.md5DigestAsHex((login + User.SALT + password).getBytes());
	}
		
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public String getPasswordMd5() {
		return passwordMd5;
	}
	public void setPasswordMd5(String passwordMd5) {
		this.passwordMd5 = passwordMd5;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
