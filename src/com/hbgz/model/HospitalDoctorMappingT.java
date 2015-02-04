package com.hbgz.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

 
@Entity
@Table(name = "HOSPITAL_DOCTOR_MAPPING_T", schema = "ORACLE")
public class HospitalDoctorMappingT implements java.io.Serializable {

	// Fields

	private String id;
	private String hospitalId;
	private String hospitalName;
	private String hospitalDoctorId;
	private String hospitalTeamId;
	private String mappingDoctorId;
	private String mappingTeamId;
	private String state;
	private Date createDate;

	// Constructors

	/** default constructor */
	public HospitalDoctorMappingT() {
	}

	/** minimal constructor */
	public HospitalDoctorMappingT(String id) {
		this.id = id;
	}

	/** full constructor */
	public HospitalDoctorMappingT(String id, String hospitalId,
			String hospitalName, String hospitalDoctorId,
			String hospitalTeamId, String mappingDoctorId,
			String mappingTeamId, String state, Date createDate) {
		this.id = id;
		this.hospitalId = hospitalId;
		this.hospitalName = hospitalName;
		this.hospitalDoctorId = hospitalDoctorId;
		this.hospitalTeamId = hospitalTeamId;
		this.mappingDoctorId = mappingDoctorId;
		this.mappingTeamId = mappingTeamId;
		this.state = state;
		this.createDate = createDate;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 20)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "HOSPITAL_ID", length = 10)
	public String getHospitalId() {
		return this.hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	@Column(name = "HOSPITAL_NAME", length = 20)
	public String getHospitalName() {
		return this.hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	@Column(name = "HOSPITAL_DOCTOR_ID", length = 20)
	public String getHospitalDoctorId() {
		return this.hospitalDoctorId;
	}

	public void setHospitalDoctorId(String hospitalDoctorId) {
		this.hospitalDoctorId = hospitalDoctorId;
	}

	@Column(name = "HOSPITAL_TEAM_ID", length = 20)
	public String getHospitalTeamId() {
		return this.hospitalTeamId;
	}

	public void setHospitalTeamId(String hospitalTeamId) {
		this.hospitalTeamId = hospitalTeamId;
	}

	@Column(name = "MAPPING_DOCTOR_ID", length = 20)
	public String getMappingDoctorId() {
		return this.mappingDoctorId;
	}

	public void setMappingDoctorId(String mappingDoctorId) {
		this.mappingDoctorId = mappingDoctorId;
	}

	@Column(name = "MAPPING_TEAM_ID", length = 20)
	public String getMappingTeamId() {
		return this.mappingTeamId;
	}

	public void setMappingTeamId(String mappingTeamId) {
		this.mappingTeamId = mappingTeamId;
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

}