package com.hbgz.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * DoctorRegisterT entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "DOCTOR_REGISTER_T")
public class DoctorRegisterT implements java.io.Serializable {

	// Fields

	private String registerId;
	private String doctorId;
	private String registerWeek;
	private String dayType;
	private String registerFee;
	private String registerNum;
	private String state;
	private Date caeateDate;
	private String teamId;

	// Constructors

	/** default constructor */
	public DoctorRegisterT() {
	}

	/** minimal constructor */
	public DoctorRegisterT(String registerId) {
		this.registerId = registerId;
	}

	/** full constructor */
	public DoctorRegisterT(String registerId, String doctorId,
			String registerWeek, String dayType, String registerFee,
			String registerNum, String state, Date caeateDate, String teamId) {
		this.registerId = registerId;
		this.doctorId = doctorId;
		this.registerWeek = registerWeek;
		this.dayType = dayType;
		this.registerFee = registerFee;
		this.registerNum = registerNum;
		this.state = state;
		this.caeateDate = caeateDate;
		this.teamId = teamId;
	}

	// Property accessors
	@Id
	@Column(name = "REGISTER_ID", unique = true, nullable = false, length = 20)
	public String getRegisterId() {
		return this.registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	@Column(name = "DOCTOR_ID", length = 20)
	public String getDoctorId() {
		return this.doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	@Column(name = "REGISTER_WEEK", length = 3)
	public String getRegisterWeek() {
		return this.registerWeek;
	}

	public void setRegisterWeek(String registerWeek) {
		this.registerWeek = registerWeek;
	}

	@Column(name = "DAY_TYPE", length = 6)
	public String getDayType() {
		return this.dayType;
	}

	public void setDayType(String dayType) {
		this.dayType = dayType;
	}

	@Column(name = "REGISTER_FEE", length = 20)
	public String getRegisterFee() {
		return this.registerFee;
	}

	public void setRegisterFee(String registerFee) {
		this.registerFee = registerFee;
	}

	@Column(name = "REGISTER_NUM", length = 2)
	public String getRegisterNum() {
		return this.registerNum;
	}

	public void setRegisterNum(String registerNum) {
		this.registerNum = registerNum;
	}

	@Column(name = "STATE", length = 3)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CAEATE_DATE", length = 7)
	public Date getCaeateDate() {
		return this.caeateDate;
	}

	public void setCaeateDate(Date caeateDate) {
		this.caeateDate = caeateDate;
	}

	@Column(name = "TEAM_ID", length = 20)
	public String getTeamId() {
		return this.teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

}