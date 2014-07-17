package com.hbgz.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * UserQuestionT entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "USER_QUESTION_T")
public class UserQuestionT implements java.io.Serializable
{

	// Fields

	private String qestionId;
	private String userId;
	private String doctorId;
	private String userTelephone;
	private String recordType;
	private String authType;
	private String content;
	private String state;
	private Date createDate=new Date();
	private String imgUrl;

	// Constructors

	/** default constructor */
	public UserQuestionT()
	{
	}

	/** minimal constructor */
	public UserQuestionT(String qestionId)
	{
		this.qestionId = qestionId;
	}

	/** full constructor */
	public UserQuestionT(String qestionId, String userId, String doctorId, String userTelephone,
			String recordType, String authType, String content, String state, Date createDate,
			String imgUrl)
	{
		this.qestionId = qestionId;
		this.userId = userId;
		this.doctorId = doctorId;
		this.userTelephone = userTelephone;
		this.recordType = recordType;
		this.authType = authType;
		this.content = content;
		this.state = state;
		this.createDate = createDate;
		this.imgUrl = imgUrl;
	}

	// Property accessors
	@Id
	@Column(name = "QESTION_ID", unique = true, nullable = false, length = 20)
	public String getQestionId()
	{
		return this.qestionId;
	}

	public void setQestionId(String qestionId)
	{
		this.qestionId = qestionId;
	}

	@Column(name = "USER_ID", length = 20)
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	@Column(name = "DOCTOR_ID", length = 20)
	public String getDoctorId()
	{
		return this.doctorId;
	}

	public void setDoctorId(String doctorId)
	{
		this.doctorId = doctorId;
	}

	@Column(name = "USER_TELEPHONE", length = 11)
	public String getUserTelephone()
	{
		return this.userTelephone;
	}

	public void setUserTelephone(String userTelephone)
	{
		this.userTelephone = userTelephone;
	}

	@Column(name = "RECORD_TYPE", length = 4)
	public String getRecordType()
	{
		return this.recordType;
	}

	public void setRecordType(String recordType)
	{
		this.recordType = recordType;
	}

	@Column(name = "AUTH_TYPE", length = 10)
	public String getAuthType()
	{
		return this.authType;
	}

	public void setAuthType(String authType)
	{
		this.authType = authType;
	}

	@Column(name = "CONTENT", length = 1000)
	public String getContent()
	{
		return this.content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	@Column(name = "STATE", length = 1000)
	public String getState()
	{
		return this.state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_DATE", length = 7)
	public Date getCreateDate()
	{
		return this.createDate;
	}

	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}

	@Column(name = "IMG_URL", length = 50)
	public String getImgUrl()
	{
		return this.imgUrl;
	}

	public void setImgUrl(String imgUrl)
	{
		this.imgUrl = imgUrl;
	}

}