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
@Table(name = "WAKE_T", schema = "ORACLE")
public class WakeT implements java.io.Serializable {

	// Fields

	private BigDecimal wakeId;
	private String wakeType;
	private String wakeValue;
	private String wakeContent;
	private Timestamp createDate;
	private Timestamp wakeDate;
	private String state;
	private String wakeName;
	private String wakeFlag;
	private String userId;

	// Constructors

	/** default constructor */
	public WakeT() {
	}

	/** minimal constructor */
	public WakeT(BigDecimal wakeId, String wakeType, String state,
			String wakeFlag) {
		this.wakeId = wakeId;
		this.wakeType = wakeType;
		this.state = state;
		this.wakeFlag = wakeFlag;
	}

	/** full constructor */
	public WakeT(BigDecimal wakeId, String wakeType, String wakeValue,
			String wakeContent, Timestamp createDate, Timestamp wakeDate, String state,
			String wakeName, String wakeFlag, String userId) {
		this.wakeId = wakeId;
		this.wakeType = wakeType;
		this.wakeValue = wakeValue;
		this.wakeContent = wakeContent;
		this.createDate = createDate;
		this.wakeDate = wakeDate;
		this.state = state;
		this.wakeName = wakeName;
		this.wakeFlag = wakeFlag;
		this.userId = userId;
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

	@Column(name = "CREATE_DATE")
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	@Column(name = "WAKE_DATE")
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

	@Column(name = "WAKE_FLAG", nullable = false, length = 1)
	public String getWakeFlag() {
		return this.wakeFlag;
	}

	public void setWakeFlag(String wakeFlag) {
		this.wakeFlag = wakeFlag;
	}

	@Column(name = "USER_ID", length = 20)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}