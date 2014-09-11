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
	private String hospitalName;
	private String state;
	private String remark;
	private String imageUrl;
	private Date createDate;
	private String introduce;

	// Constructors

	/** default constructor */
	public HospitalT() {
	}

	/** minimal constructor */
	public HospitalT(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	/** full constructor */
	public HospitalT(String hospitalId, String hospitalName, String state,
			String remark, String imageUrl, Date createDate, String introduce) {
		this.hospitalId = hospitalId;
		this.hospitalName = hospitalName;
		this.state = state;
		this.remark = remark;
		this.imageUrl = imageUrl;
		this.createDate = createDate;
		this.introduce = introduce;
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

	@Column(name = "HOSPITAL_NAME", length = 200)
	public String getHospitalName() {
		return this.hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	@Column(name = "STATE", length = 3)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "REMARK", length = 600)
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

	@Column(name = "INTRODUCE", length = 500)
	public String getIntroduce() {
		return this.introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

}