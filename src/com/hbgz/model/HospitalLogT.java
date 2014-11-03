package com.hbgz.model;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HospitalLogT entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "HOSPITAL_LOG_T")
public class HospitalLogT implements java.io.Serializable {

	// Fields

	private String logId;
	private String hospitalId;
	private String telephone;
	private Timestamp createDate;
	private String state;

	// Constructors

	/** default constructor */
	public HospitalLogT() {
	}

	/** minimal constructor */
	public HospitalLogT(String logId) {
		this.logId = logId;
	}

	/** full constructor */
	public HospitalLogT(String logId, String hospitalId, String telephone,
			Timestamp createDate, String state) {
		this.logId = logId;
		this.hospitalId = hospitalId;
		this.telephone = telephone;
		this.createDate = createDate;
		this.state = state;
	}

	// Property accessors
	@Id
	@Column(name = "LOG_ID", unique = true, nullable = false, length = 20)
	public String getLogId() {
		return this.logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	@Column(name = "HOSPITAL_ID", length = 20)
	public String getHospitalId() {
		return this.hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	@Column(name = "TELEPHONE", length = 11)
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "CREATE_DATE", nullable = false)
	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
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