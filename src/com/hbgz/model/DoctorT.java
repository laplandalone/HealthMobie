package com.hbgz.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "DOCTOR_T")
public class DoctorT implements java.io.Serializable {

	// Fields

	private String doctorId;
	private String hospitalId;
	private String name;
	private String sex;
	private String telephone;
	private String state;
	private Date createDate;
	private String post;
	private String expertFlag;
	private String onlineFlag;
	private String introduce;
	private String remark;
	private String skill;
	private String teamId;
	private String workTime;
	private String workAddress;
	private String registerFee;
	private String photoUrl;
	private BigDecimal registerNum;

	// Constructors

	/** default constructor */
	public DoctorT() {
	}

	/** minimal constructor */
	public DoctorT(String doctorId, String hospitalId, String name, String sex,
			Date createDate, String post, String expertFlag, String onlineFlag,
			String teamId, String workTime, String workAddress) {
		this.doctorId = doctorId;
		this.hospitalId = hospitalId;
		this.name = name;
		this.sex = sex;
		this.createDate = createDate;
		this.post = post;
		this.expertFlag = expertFlag;
		this.onlineFlag = onlineFlag;
		this.teamId = teamId;
		this.workTime = workTime;
		this.workAddress = workAddress;
	}

	/** full constructor */
	public DoctorT(String doctorId, String hospitalId, String name, String sex,
			String telephone, String state, Date createDate, String post,
			String expertFlag, String onlineFlag, String introduce,
			String remark, String skill, String teamId, String workTime,
			String workAddress, String registerFee, String photoUrl,
			BigDecimal registerNum) {
		this.doctorId = doctorId;
		this.hospitalId = hospitalId;
		this.name = name;
		this.sex = sex;
		this.telephone = telephone;
		this.state = state;
		this.createDate = createDate;
		this.post = post;
		this.expertFlag = expertFlag;
		this.onlineFlag = onlineFlag;
		this.introduce = introduce;
		this.remark = remark;
		this.skill = skill;
		this.teamId = teamId;
		this.workTime = workTime;
		this.workAddress = workAddress;
		this.registerFee = registerFee;
		this.photoUrl = photoUrl;
		this.registerNum = registerNum;
	}

	// Property accessors
	@Id
	@Column(name = "DOCTOR_ID",  nullable = false, length = 40)
	public String getDoctorId() {
		return this.doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	@Column(name = "HOSPITAL_ID", nullable = false, length = 20)
	public String getHospitalId() {
		return this.hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	@Column(name = "NAME", nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "SEX",  length = 4)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "TELEPHONE", length = 11)
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "STATE", length = 3)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_DATE", nullable = false, length = 7)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "POST",  length = 20)
	public String getPost() {
		return this.post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	@Column(name = "EXPERT_FLAG", length = 1)
	public String getExpertFlag() {
		return this.expertFlag;
	}

	public void setExpertFlag(String expertFlag) {
		this.expertFlag = expertFlag;
	}

	@Column(name = "ONLINE_FLAG", length = 1)
	public String getOnlineFlag() {
		return this.onlineFlag;
	}

	public void setOnlineFlag(String onlineFlag) {
		this.onlineFlag = onlineFlag;
	}

	@Column(name = "INTRODUCE", length = 1000)
	public String getIntroduce() {
		return this.introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	@Column(name = "REMARK", length = 1000)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "SKILL", length = 500)
	public String getSkill() {
		return this.skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	@Column(name = "TEAM_ID", nullable = false, length = 20)
	public String getTeamId() {
		return this.teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	@Column(name = "WORK_TIME",length = 40)
	public String getWorkTime() {
		return this.workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}

	@Column(name = "WORK_ADDRESS",  length = 400)
	public String getWorkAddress() {
		return this.workAddress;
	}

	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}

	@Column(name = "REGISTER_FEE", length = 2)
	public String getRegisterFee() {
		return this.registerFee;
	}

	public void setRegisterFee(String registerFee) {
		this.registerFee = registerFee;
	}

	@Column(name = "PHOTO_URL", length = 100)
	public String getPhotoUrl() {
		return this.photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	@Column(name = "REGISTER_NUM", precision = 22, scale = 0)
	public BigDecimal getRegisterNum() {
		return this.registerNum;
	}

	public void setRegisterNum(BigDecimal registerNum) {
		this.registerNum = registerNum;
	}

}