package com.hbgz.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HospitalManagerT entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "HOSPITAL_MANAGER_T")
public class HospitalManagerT implements java.io.Serializable {

	// Fields

	private String managerId;
	private String hospitalId;
	private String name;
	private String password;
	private Date createDate;
	private String state;
	private String privs;
	private String doctorId;

	// Constructors

	/** default constructor */
	public HospitalManagerT() {
	}

	/** minimal constructor */
	public HospitalManagerT(String managerId, String hospitalId, String name,
			String password) {
		this.managerId = managerId;
		this.hospitalId = hospitalId;
		this.name = name;
		this.password = password;
	}

	/** full constructor */
	public HospitalManagerT(String managerId, String hospitalId, String name,
			String password, Date createDate, String state, String privs,
			String doctorId) {
		this.managerId = managerId;
		this.hospitalId = hospitalId;
		this.name = name;
		this.password = password;
		this.createDate = createDate;
		this.state = state;
		this.privs = privs;
		this.doctorId = doctorId;
	}

	// Property accessors
	@Id
	@Column(updatable=false,name = "MANAGER_ID", unique = true, nullable = false, length = 50)
	public String getManagerId() {
		return this.managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	@Column(updatable=false,name = "HOSPITAL_ID", nullable = false, length = 20)
	public String getHospitalId() {
		return this.hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	@Column(updatable=false,name = "NAME", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PASSWORD", nullable = false, length = 50)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Temporal(TemporalType.DATE)
	@Column(updatable=false,name = "CREATE_DATE", length = 7)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(updatable=false,name = "STATE", length = 3)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(updatable=false,name = "PRIVS", length = 3)
	public String getPrivs() {
		return this.privs;
	}

	public void setPrivs(String privs) {
		this.privs = privs;
	}

	@Column(updatable=false,name = "DOCTOR_ID", length = 40)
	public String getDoctorId() {
		return this.doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

}