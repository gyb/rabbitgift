package dreamsky.ttt.order;

import java.sql.Timestamp;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import dreamsky.ttt.dao.Column;
import dreamsky.ttt.dao.VersionIdEntity;
import dreamsky.ttt.goods.Goods;
import dreamsky.ttt.user.User;

public class Order implements VersionIdEntity {

	private static final long serialVersionUID = 1L;
	@Column private Long id;

	@NotNull
	@Column private Long buyerId;
	
	@Column private Long sellerId;

	@Column private long money;
	
	@NotNull
	@Column private Long goodsId;

	@Min(1)
	@Column private int num;
	
	@NotNull
	@Column private String receiverName;
	@NotNull
	@Column private String address;
	@NotNull
	@Column private String phone;
	
	@Column private State state = State.CREATED;
	@Column private Timestamp lastUpdateTime;
	@Column private int version;
	
	private Goods goods;
	private User buyer;
	private User seller;
	
	public enum State {
		CREATED, PAYED, DELIVERED, RECEIVED, COMPLETED, CANCELED, REFUNDED
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public Long getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}
	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	public long getMoney() {
		return money;
	}
	public void setMoney(long money) {
		this.money = money;
	}
	public Long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
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
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public User getBuyer() {
		return buyer;
	}
	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}
	public User getSeller() {
		return seller;
	}
	public void setSeller(User seller) {
		this.seller = seller;
	}
}
