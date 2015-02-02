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
 * PatientVisitT entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "PATIENT_VISIT_T")
public class PatientVisitT implements java.io.Serializable {

	// Fields

	private String visitId;
	private String visitName;
	private String visitType;
	private String patientId;
	private String cardId;
	private String state;
	private Timestamp createDate;
	private String copyFlag;
	// Constructors

	@Column(name = "COPY_FLAG", length = 1)
	public String getCopyFlag() {
		return copyFlag;
	}

	public void setCopyFlag(String copyFlag) {
		this.copyFlag = copyFlag;
	}

	/** default constructor */
	public PatientVisitT() {
	}

	/** minimal constructor */
	public PatientVisitT(String visitId) {
		this.visitId = visitId;
	}

	/** full constructor */
	public PatientVisitT(String visitId, String visitName, String visitType,
			String patientId, String cardId, String state, Timestamp createDate,String copyFlag) {
		this.visitId = visitId;
		this.visitName = visitName;
		this.visitType = visitType;
		this.patientId = patientId;
		this.cardId = cardId;
		this.state = state;
		this.createDate = createDate;
		this.copyFlag=copyFlag;
	}

	// Property accessors
	@Id
	@Column(name = "VISIT_ID", unique = true, nullable = false, length = 20)
	public String getVisitId() {
		return this.visitId;
	}

	public void setVisitId(String visitId) {
		this.visitId = visitId;
	}

	@Column(name = "VISIT_NAME", length = 50)
	public String getVisitName() {
		return this.visitName;
	}

	public void setVisitName(String visitName) {
		this.visitName = visitName;
	}

	@Column(name = "VISIT_TYPE", length = 10)
	public String getVisitType() {
		return this.visitType;
	}

	public void setVisitType(String visitType) {
		this.visitType = visitType;
	}

	@Column(name = "PATIENT_ID", length = 20)
	public String getPatientId() {
		return this.patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	@Column(name = "CARD_ID", length = 20)
	public String getCardId() {
		return this.cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	@Column(name = "STATE", length = 3)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	 
	@Column(name = "CREATE_DATE", length = 7)
	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

}