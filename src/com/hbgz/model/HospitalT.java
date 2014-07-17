package com.hbgz.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HospitalT entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "HOSPITAL_T")
public class HospitalT implements java.io.Serializable {

	// Fields

	private String hospitalId;
	private String parentId;
	private String name;
	private String address;
	private String busRoute;
	private String wwwUrl;
	private String phoneNum;
	private String intorduce;
	private String featureTeam;
	private String state;
	private String remark;
	private String imageUrl;
	private Date createDate;
	private String x;
	private String y;

	// Constructors

	/** default constructor */
	public HospitalT() {
	}

	/** minimal constructor */
	public HospitalT(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	/** full constructor */
	public HospitalT(String hospitalId, String parentId, String name,
			String address, String busRoute, String wwwUrl, String phoneNum,
			String intorduce, String featureTeam, String state, String remark,
			String imageUrl, Date createDate, String x, String y) {
		this.hospitalId = hospitalId;
		this.parentId = parentId;
		this.name = name;
		this.address = address;
		this.busRoute = busRoute;
		this.wwwUrl = wwwUrl;
		this.phoneNum = phoneNum;
		this.intorduce = intorduce;
		this.featureTeam = featureTeam;
		this.state = state;
		this.remark = remark;
		this.imageUrl = imageUrl;
		this.createDate = createDate;
		this.x = x;
		this.y = y;
	}

	// Property accessors
	@Id
	@Column(name = "HOSPITAL_ID", unique = true, nullable = false, length = 20)
	public String getHospitalId() {
		return this.hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	@Column(name = "PARENT_ID", length = 20)
	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name = "NAME", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "ADDRESS", length = 20)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "BUS_ROUTE", length = 20)
	public String getBusRoute() {
		return this.busRoute;
	}

	public void setBusRoute(String busRoute) {
		this.busRoute = busRoute;
	}

	@Column(name = "WWW_URL", length = 20)
	public String getWwwUrl() {
		return this.wwwUrl;
	}

	public void setWwwUrl(String wwwUrl) {
		this.wwwUrl = wwwUrl;
	}

	@Column(name = "PHONE_NUM", length = 20)
	public String getPhoneNum() {
		return this.phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	@Column(name = "INTORDUCE", length = 20)
	public String getIntorduce() {
		return this.intorduce;
	}

	public void setIntorduce(String intorduce) {
		this.intorduce = intorduce;
	}

	@Column(name = "FEATURE_TEAM", length = 20)
	public String getFeatureTeam() {
		return this.featureTeam;
	}

	public void setFeatureTeam(String featureTeam) {
		this.featureTeam = featureTeam;
	}

	@Column(name = "STATE", length = 3)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "REMARK", length = 20)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "IMAGE_URL", length = 50)
	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_DATE", length = 7)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

}