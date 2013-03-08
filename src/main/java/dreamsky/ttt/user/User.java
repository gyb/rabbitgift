package dreamsky.ttt.user;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.codec.digest.DigestUtils;

import dreamsky.ttt.dao.Column;
import dreamsky.ttt.dao.IdEntity;

/**
 * @author yibing
 *
 */
public class User implements IdEntity {
	private static final long serialVersionUID = -8182450292866499015L;

	private static final String SALT = "ut&st^et%r";
	
	@Column private Long id;
	
	@NotNull
	@Size(min=2, max=18)
	@Column private String login;
	
	@Column private String passwordMd5;
	
	@NotNull
	@Size(min=5, max=60)
	@Column private String email;
	private Date createTime;
	
	@NotNull
	@Size(min=6, max=30)
	private String password;
	
	public void setPassword(String password) {
		this.password = password;
		this.passwordMd5 = md5Pass(password);
	}
	
	public boolean checkPassword(String password) {
		return md5Pass(password).equals(passwordMd5);
	}
	
	private String md5Pass(String password) {
		return DigestUtils.md5Hex(login + User.SALT + password);
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
