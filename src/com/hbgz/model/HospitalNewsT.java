package com.hbgz.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * HospitalNewsT entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "HOSPITAL_NEWS_T")
public class HospitalNewsT implements java.io.Serializable
{

	// Fields

	private String newsId;
	private String hospitalId;
	private String newsTitle;
	private byte[] newsContent;
	private String newsImages;
	private String state;
	private Date createDate;
	private String newsType;
	private String content;
	private String typeId;

	// Constructors

	/** default constructor */
	public HospitalNewsT()
	{
	}

	/** minimal constructor */
	public HospitalNewsT(String newsId, String hospitalId, String newsTitle, byte[] newsContent)
	{
		this.newsId = newsId;
		this.hospitalId = hospitalId;
		this.newsTitle = newsTitle;
		this.newsContent = newsContent;
	}

	/** full constructor */
	public HospitalNewsT(String newsId, String hospitalId, String newsTitle, byte[] newsContent,
			String newsImages, String state, Date createDate, String newsType, String typeId)
	{
		this.newsId = newsId;
		this.hospitalId = hospitalId;
		this.newsTitle = newsTitle;
		this.newsContent = newsContent;
		this.newsImages = newsImages;
		this.state = state;
		this.createDate = createDate;
		this.newsType = newsType;
		this.typeId = typeId;
	}

	// Property accessors
	@Id
	@Column(name = "NEWS_ID", unique = true, nullable = false, length = 20)
	public String getNewsId()
	{
		return this.newsId;
	}

	public void setNewsId(String newsId)
	{
		this.newsId = newsId;
	}

	@Column(name = "HOSPITAL_ID", nullable = false, length = 20)
	public String getHospitalId()
	{
		return this.hospitalId;
	}

	public void setHospitalId(String hospitalId)
	{
		this.hospitalId = hospitalId;
	}

	@Column(name = "NEWS_TITLE", nullable = false, length = 200)
	public String getNewsTitle()
	{
		return this.newsTitle;
	}

	public void setNewsTitle(String newsTitle)
	{
		this.newsTitle = newsTitle;
	}

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "NEWS_CONTENT", columnDefinition = "BLOB", nullable = false)
	public byte[] getNewsContent()
	{
		return this.newsContent;
	}

	@Transient
	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public void setNewsContent(byte[] newsContent)
	{
		this.newsContent = newsContent;
	}

	@Column(name = "NEWS_IMAGES", length = 50)
	public String getNewsImages()
	{
		return this.newsImages;
	}

	public void setNewsImages(String newsImages)
	{
		this.newsImages = newsImages;
	}

	@Column(name = "STATE", length = 3)
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

	@Column(name = "NEWS_TYPE", length = 5)
	public String getNewsType()
	{
		return this.newsType;
	}

	public void setNewsType(String newsType)
	{
		this.newsType = newsType;
	}

	@Column(name = "TYPE_ID", length = 20)
	public String getTypeId()
	{
		return this.typeId;
	}

	public void setTypeId(String typeId)
	{
		this.typeId = typeId;
	}

}