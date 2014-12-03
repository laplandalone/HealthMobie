package com.hbgz.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * WakeT entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "WAKE_T")
public class WakeT implements java.io.Serializable {

	// Fields

	private BigDecimal wakeId;
	private String wakeType;
	private String wakeValue;
	private String wakeContent;
	private Date createDate;
	private Timestamp wakeDate;
	private String state;
	private String wakeName;

	// Constructors

	/** default constructor */
	public WakeT() {
	}

	/** minimal constructor */
	public WakeT(BigDecimal wakeId, String wakeType, Timestamp createDate,
			Timestamp wakeDate, String state) {
		this.wakeId = wakeId;
		this.wakeType = wakeType;
		this.createDate = createDate;
		this.wakeDate = wakeDate;
		this.state = state;
	}

	/** full constructor */
	public WakeT(BigDecimal wakeId, String wakeType, String wakeValue,
			String wakeContent, Date createDate, Timestamp wakeDate, String state,
			String wakeName) {
		this.wakeId = wakeId;
		this.wakeType = wakeType;
		this.wakeValue = wakeValue;
		this.wakeContent = wakeContent;
		this.createDate = createDate;
		this.wakeDate = wakeDate;
		this.state = state;
		this.wakeName = wakeName;
	}

	// Property accessors
	@Id
	@Column(name = "WAKE_ID", unique = true, nullable = false, precision = 20, scale = 0)
	public BigDecimal getWakeId() {
		return this.wakeId;
	}

	public void setWakeId(BigDecimal wakeId) {
		this.wakeId = wakeId;
	}

	@Column(name = "WAKE_TYPE", nullable = false, length = 20)
	public String getWakeType() {
		return this.wakeType;
	}

	public void setWakeType(String wakeType) {
		this.wakeType = wakeType;
	}

	@Column(name = "WAKE_VALUE", length = 200)
	public String getWakeValue() {
		return this.wakeValue;
	}

	public void setWakeValue(String wakeValue) {
		this.wakeValue = wakeValue;
	}

	@Column(name = "WAKE_CONTENT", length = 1000)
	public String getWakeContent() {
		return this.wakeContent;
	}

	public void setWakeContent(String wakeContent) {
		this.wakeContent = wakeContent;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_DATE", nullable = false, length = 7)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	@Column(name = "WAKE_DATE", nullable = false, length = 7)
	public Timestamp getWakeDate() {
		return this.wakeDate;
	}

	public void setWakeDate(Timestamp wakeDate) {
		this.wakeDate = wakeDate;
	}

	@Column(name = "STATE", nullable = false, length = 3)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "WAKE_NAME", length = 100)
	public String getWakeName() {
		return this.wakeName;
	}

	public void setWakeName(String wakeName) {
		this.wakeName = wakeName;
	}

}