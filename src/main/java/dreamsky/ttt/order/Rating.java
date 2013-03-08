package dreamsky.ttt.order;

import java.sql.Timestamp;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import dreamsky.ttt.dao.Column;
import dreamsky.ttt.dao.IdEntity;

public class Rating implements IdEntity {
	private static final long serialVersionUID = 1L;
	
	@Column private Long id;
	
	@NotNull
	@Column private Long goodsId;
	
	@NotNull
	@Column private Long orderId;
	
	@NotNull
	@Column private Long userId;
	
	@Column private Timestamp ratingTime;
	
	@NotNull
	@Min(1)
	@Max(5)
	@Column private int number;
	
	@Size(max=1024) @Column private String comment;

	@Column private Timestamp buyTime;
	@Column private long buyPrice;
	
	private String login;

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

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Timestamp getRatingTime() {
		return ratingTime;
	}

	public void setRatingTime(Timestamp ratingTime) {
		this.ratingTime = ratingTime;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public Timestamp getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(Timestamp buyTime) {
		this.buyTime = buyTime;
	}

	public long getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(long buyPrice) {
		this.buyPrice = buyPrice;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
}
