package com.hbgz.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "HOSPITAL_USER_RELATIONSHIP_T")
public class HospitalUserRelationshipT implements java.io.Serializable {

	// Fields

	private String relationshipId;
	private String userId;
	private String hospitalId;
	private String state;
	private Date createDate;

	// Constructors

	/** default constructor */
	public HospitalUserRelationshipT() {
	}

	/** minimal constructor */
	public HospitalUserRelationshipT(String relationshipId) {
		this.relationshipId = relationshipId;
	}

	/** full constructor */
	public HospitalUserRelationshipT(String relationshipId, String userId,
			String hospitalId, String state, Date createDate) {
		this.relationshipId = relationshipId;
		this.userId = userId;
		this.hospitalId = hospitalId;
		this.state = state;
		this.createDate = createDate;
	}

	// Property accessors
	@Id
	@Column(name = "RELATIONSHIP_ID", unique = true, nullable = false, length = 20)
	public String getRelationshipId() {
		return this.relationshipId;
	}

	public void setRelationshipId(String relationshipId) {
		this.relationshipId = relationshipId;
	}

	@Column(name = "USER_ID", length = 20)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "HOSPITAL_ID", length = 20)
	public String getHospitalId() {
		return this.hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
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