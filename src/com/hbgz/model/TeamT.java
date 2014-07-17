package com.hbgz.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TeamT entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TEAM_T")
public class TeamT implements java.io.Serializable {

	// Fields

	private String teamId;
	private String hospitalId;
	private String teamName;
	private String expertFlag;
	private String introduce;
	private String state;
	private Date createDate;
	private String busRoute;
	private String phoneNum;
	private String address;
	private String x;
	private String y;
	private String teamType;

	// Constructors

	/** default constructor */
	public TeamT() {
	}

	/** minimal constructor */
	public TeamT(String teamId, String hospitalId, String teamName) {
		this.teamId = teamId;
		this.hospitalId = hospitalId;
		this.teamName = teamName;
	}

	/** full constructor */
	public TeamT(String teamId, String hospitalId, String teamName,
			String expertFlag, String introduce, String state, Date createDate,
			String busRoute, String phoneNum, String address, String x,
			String y, String teamType) {
		this.teamId = teamId;
		this.hospitalId = hospitalId;
		this.teamName = teamName;
		this.expertFlag = expertFlag;
		this.introduce = introduce;
		this.state = state;
		this.createDate = createDate;
		this.busRoute = busRoute;
		this.phoneNum = phoneNum;
		this.address = address;
		this.x = x;
		this.y = y;
		this.teamType = teamType;
	}

	// Property accessors
	@Id
	@Column(name = "TEAM_ID", unique = true, nullable = false, length = 20)
	public String getTeamId() {
		return this.teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	@Column(name = "HOSPITAL_ID", nullable = false, length = 20)
	public String getHospitalId() {
		return this.hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	@Column(name = "TEAM_NAME", nullable = false, length = 20)
	public String getTeamName() {
		return this.teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	@Column(name = "EXPERT_FLAG", length = 2)
	public String getExpertFlag() {
		return this.expertFlag;
	}

	public void setExpertFlag(String expertFlag) {
		this.expertFlag = expertFlag;
	}

	@Column(name = "INTRODUCE", length = 1000)
	public String getIntroduce() {
		return this.introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	@Column(name = "STATE", length = 3)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_DATE", length = 7)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "BUS_ROUTE", length = 200)
	public String getBusRoute() {
		return this.busRoute;
	}

	public void setBusRoute(String busRoute) {
		this.busRoute = busRoute;
	}

	@Column(name = "PHONE_NUM", length = 20)
	public String getPhoneNum() {
		return this.phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	@Column(name = "ADDRESS", length = 500)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "X", length = 30)
	public String getX() {
		return this.x;
	}

	public void setX(String x) {
		this.x = x;
	}

	@Column(name = "Y", length = 30)
	public String getY() {
		return this.y;
	}

	public void setY(String y) {
		this.y = y;
	}

	@Column(name = "TEAM_TYPE", length = 1)
	public String getTeamType() {
		return this.teamType;
	}

	public void setTeamType(String teamType) {
		this.teamType = teamType;
	}

}