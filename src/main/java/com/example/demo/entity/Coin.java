package com.example.demo.entity;
import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "COIN")
public class Coin implements java.io.Serializable  {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "CID", unique = true, nullable = false)
	private Integer cId;

	@Column(name = "CURRENCY_EN_NAME")
	private String currencyEnName;

	@Column(name = "CURRENCY_CN_NAME")
	private String currencyCnName;


	public Integer getcId() {
		return cId;
	}

	public void setcId(Integer cId) {
		this.cId = cId;
	}

	public String getCurrencyEnName() {
		return currencyEnName;
	}

	public void setCurrencyEnName(String currencyEnName) {
		this.currencyEnName = currencyEnName;
	}

	public String getCurrencyCnName() {
		return currencyCnName;
	}

	public void setCurrencyCnName(String currencyCnName) {
		this.currencyCnName = currencyCnName;
	}

	@Override
	public String toString() {
		return "Coin{" +
				"cId=" + cId +
				", currencyEnName='" + currencyEnName + '\'' +
				", currencyCnName='" + currencyCnName + '\'' +
				'}';
	}
}
