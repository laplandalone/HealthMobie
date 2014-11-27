package com.hbgz.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * UserRelateT entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "USER_RELATE_T")
public class UserRelateT implements java.io.Serializable {

	// Fields

	private String relateId;
	private String userId;
	private String relatePhone;
	private String relatePass;
	private String channelId;
	private String pushUserId;
	private Date createDate;
	private String state;
	private String relateName;
	// Constructors

	/** default constructor */
	public UserRelateT() {
	}

	@Column(name = "RELATE_NAME", length = 20)
	public String getRelateName() {
		return relateName;
	}

	public void setRelateName(String relateName) {
		this.relateName = relateName;
	}

	/** minimal constructor */
	public UserRelateT(String relateId, String userId) {
		this.relateId = relateId;
		this.userId = userId;
	}

	/** full constructor */
	public UserRelateT(String relateId, String userId, String relatePhone,
			String relatePass, String channelId, String pushUserId,
			Date createDate, String state,String relateName) {
		this.relateId = relateId;
		this.userId = userId;
		this.relatePhone = relatePhone;
		this.relatePass = relatePass;
		this.channelId = channelId;
		this.pushUserId = pushUserId;
		this.createDate = createDate;
		this.state = state;
		this.relateName=relateName;
	}

	// Property accessors
	@Id
	@Column(name = "RELATE_ID", unique = true, nullable = false, length = 20)
	public String getRelateId() {
		return this.relateId;
	}

	public void setRelateId(String relateId) {
		this.relateId = relateId;
	}

	@Column(name = "USER_ID", nullable = false, length = 60)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "RELATE_PHONE", length = 12)
	public String getRelatePhone() {
		return this.relatePhone;
	}

	public void setRelatePhone(String relatePhone) {
		this.relatePhone = relatePhone;
	}

	@Column(name = "RELATE_PASS", length = 20)
	public String getRelatePass() {
		return this.relatePass;
	}

	public void setRelatePass(String relatePass) {
		this.relatePass = relatePass;
	}

	@Column(name = "CHANNEL_ID", length = 20)
	public String getChannelId() {
		return this.channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@Column(name = "PUSH_USER_ID", length = 20)
	public String getPushUserId() {
		return this.pushUserId;
	}

	public void setPushUserId(String pushUserId) {
		this.pushUserId = pushUserId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_DATE", length = 7)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "STATE", length = 3)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

}