package com.hbgz.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * UserContactT entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "USER_CONTACT_T")
public class UserContactT implements java.io.Serializable {

	// Fields

	private String contactId;
	private String userId;
	private String contactName;
	private String contactTelephone;
	private String contactSex;
	private String contactNo;
	private Date createDate;
	private String state;
	private String cardId;

	// Constructors

	/** default constructor */
	public UserContactT() {
	}

	/** minimal constructor */
	public UserContactT(String contactId, String userId, String contactTelephone) {
		this.contactId = contactId;
		this.userId = userId;
		this.contactTelephone = contactTelephone;
	}

	/** full constructor */
	public UserContactT(String contactId, String userId, String contactName,
			String contactTelephone, String contactSex, String contactNo,
			Date createDate, String state, String cardId) {
		this.contactId = contactId;
		this.userId = userId;
		this.contactName = contactName;
		this.contactTelephone = contactTelephone;
		this.contactSex = contactSex;
		this.contactNo = contactNo;
		this.createDate = createDate;
		this.state = state;
		this.cardId = cardId;
	}

	// Property accessors
	@Id
	@Column(name = "CONTACT_ID", unique = true, nullable = false, length = 20)
	public String getContactId() {
		return this.contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	@Column(name = "USER_ID", nullable = false, length = 60)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "CONTACT_NAME", length = 20)
	public String getContactName() {
		return this.contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	@Column(name = "CONTACT_TELEPHONE", nullable = false, length = 11)
	public String getContactTelephone() {
		return this.contactTelephone;
	}

	public void setContactTelephone(String contactTelephone) {
		this.contactTelephone = contactTelephone;
	}

	@Column(name = "CONTACT_SEX", length = 4)
	public String getContactSex() {
		return this.contactSex;
	}

	public void setContactSex(String contactSex) {
		this.contactSex = contactSex;
	}

	@Column(name = "CONTACT_NO", length = 4)
	public String getContactNo() {
		return this.contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
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

	@Column(name = "CARD_ID", length = 20)
	public String getCardId() {
		return this.cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

}