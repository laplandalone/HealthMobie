package com.hbgz.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HospitalUserT entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "HOSPITAL_USER_T")
public class HospitalUserT implements java.io.Serializable {

	// Fields

	private String userId;
	private String userName;
	private String telephone;
	private String sex;
	private Date createDate=new Date();
	private String state="00A";
	private String password;
	private String userNo;
	private String cardNo;

	// Constructors

	/** default constructor */
	public HospitalUserT() {
	}

	/** minimal constructor */
	public HospitalUserT(String userId, String userName, String telephone,
			String password, String userNo) {
		this.userId = userId;
		this.userName = userName;
		this.telephone = telephone;
		this.password = password;
		this.userNo = userNo;
	}

	/** full constructor */
	public HospitalUserT(String userId, String userName, String telephone,
			String sex, Date createDate, String state, String password,
			String userNo, String cardNo) {
		this.userId = userId;
		this.userName = userName;
		this.telephone = telephone;
		this.sex = sex;
		this.createDate = createDate;
		this.state = state;
		this.password = password;
		this.userNo = userNo;
		this.cardNo = cardNo;
	}

	// Property accessors
	@Id
	@Column(name = "USER_ID", unique = true, nullable = false, length = 20)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "USER_NAME", length = 20)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "TELEPHONE", nullable = false, length = 11)
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "SEX", length = 2)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
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

	@Column(name = "PASSWORD", nullable = false, length = 20)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "USER_NO", length = 18)
	public String getUserNo() {
		return this.userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	@Column(name = "CARD_NO", length = 20)
	public String getCardNo() {
		return this.cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

}