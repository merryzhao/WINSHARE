package com.winxuan.ec.model.shop;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author sunflower
 *
 */
@Entity
@Table(name = "shop_service_time")
public class ShopServiceTime implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8149082468655843700L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shop")
	private Shop shop;

	@Column
	private int weekday;

	@Column(name = "weekday_start_hour")
	private int weekdayStartHour;

	@Column(name = "weekday_end_hour")
	private int weekdayEndHour;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public int getWeekday() {
		return weekday;
	}

	public void setWeekday(int weekday) {
		this.weekday = weekday;
	}

	public int getWeekdayStartHour() {
		return weekdayStartHour;
	}

	public void setWeekdayStartHour(int weekdayStartHour) {
		this.weekdayStartHour = weekdayStartHour;
	}

	public int getWeekdayEndHour() {
		return weekdayEndHour;
	}

	public void setWeekdayEndHour(int weekdayEndHour) {
		this.weekdayEndHour = weekdayEndHour;
	}

}
