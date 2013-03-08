package dreamsky.ttt.goods;

import java.sql.Timestamp;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import dreamsky.ttt.dao.Column;
import dreamsky.ttt.dao.VersionIdEntity;

public class Goods implements VersionIdEntity {

	private static final long serialVersionUID = 1L;
	
	@Column private Long id;
	
	@Column
	@NotNull private Long userId;
	
	@Column private int categoryId;
	
	@Column 
	@NotBlank 
	@Size(max=40)private String name;
	
	@Column private String description;
	
	@Column private String picUrl;
	
	@Min(1)
	@Column private long price;
	
	@Min(0)
	@Column private int availableNumber;
	
	@Column private int selledNumber;
	
	@Column private Timestamp onlineTime;
	@Column private Timestamp offlineTime;
	@Column private State state = State.CREATED;
	
	@Column private int ratingNumber;
	@Column private int ratingTimes;
	@Column private float averageRating;

	@Column private int ratingStars1;
	@Column private int ratingStars2;
	@Column private int ratingStars3;
	@Column private int ratingStars4;
	@Column private int ratingStars5;
	
	@Column private int version;
	
	public boolean isOnline() {
		return state == State.ONLINE;
	}
	
	public boolean isNew() {
		return state == State.CREATED;
	}
	
	public boolean isOffline() {
		return state == State.OFFLINE;
	}

	public enum State {
		CREATED,
		ONLINE,
		OFFLINE
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public Timestamp getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(Timestamp onlineTime) {
		this.onlineTime = onlineTime;
	}

	public Timestamp getOfflineTime() {
		return offlineTime;
	}

	public void setOfflineTime(Timestamp offlineTime) {
		this.offlineTime = offlineTime;
	}

	public int getAvailableNumber() {
		return availableNumber;
	}

	public void setAvailableNumber(int availableNumber) {
		this.availableNumber = availableNumber;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getSelledNumber() {
		return selledNumber;
	}

	public void setSelledNumber(int selledNumber) {
		this.selledNumber = selledNumber;
	}

	public int getRatingNumber() {
		return ratingNumber;
	}

	public void setRatingNumber(int ratingNumber) {
		this.ratingNumber = ratingNumber;
	}

	public int getRatingTimes() {
		return ratingTimes;
	}

	public void setRatingTimes(int ratingTimes) {
		this.ratingTimes = ratingTimes;
	}

	public float getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(float averageRating) {
		this.averageRating = averageRating;
	}
	
	public void addRating(int number) {
		if (number < 1 || number > 5) throw new IllegalArgumentException(
				"Rating should be between 1 and 5, but it is " + number);
		
		this.ratingTimes++;
		this.ratingNumber += number;
		this.averageRating = (float)ratingNumber / ratingTimes;
		switch (number) {
		case 1:
			this.ratingStars1++;
			break;
		case 2:
			this.ratingStars2++;
			break;
		case 3:
			this.ratingStars3++;
			break;
		case 4:
			this.ratingStars4++;
			break;
		case 5:
			this.ratingStars5++;
			break;
		}
	}
	
	public int getRatingStars1() {
		return ratingStars1;
	}

	public void setRatingStars1(int ratingStars1) {
		this.ratingStars1 = ratingStars1;
	}

	public int getRatingStars2() {
		return ratingStars2;
	}

	public void setRatingStars2(int ratingStars2) {
		this.ratingStars2 = ratingStars2;
	}

	public int getRatingStars3() {
		return ratingStars3;
	}

	public void setRatingStars3(int ratingStars3) {
		this.ratingStars3 = ratingStars3;
	}

	public int getRatingStars4() {
		return ratingStars4;
	}

	public void setRatingStars4(int ratingStars4) {
		this.ratingStars4 = ratingStars4;
	}

	public int getRatingStars5() {
		return ratingStars5;
	}

	public void setRatingStars5(int ratingStars5) {
		this.ratingStars5 = ratingStars5;
	}
}
