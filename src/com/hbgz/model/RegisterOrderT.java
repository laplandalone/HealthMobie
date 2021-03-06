package com.hbgz.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "REGISTER_ORDER_T")
public class RegisterOrderT implements java.io.Serializable {

	// Fields

	private String orderId;
	private String userId;
	private String registerId;
	private String doctorId;
	private String doctorName;
	private String orderNum;
	private String orderState;
	private String orderFee;
	private String registerTime;
	private String userName;
	private String userNo;
	private String userTelephone;
	private String sex;
	private String teamId;
	private String teamName;
	private String state;
	private Timestamp createDate;
	private String hospitalId;
	private String payState;
	private String detailTime;
	private String platformOrderId;
	// Constructors

	
	@Column(name = "DETAIL_TIME", nullable = false, length = 20)
	public String getDetailTime() {
		return detailTime;
	}

	@Column(name = "Platform_Order_Id", nullable = true, length = 20)
	public String getPlatformOrderId() {
		return platformOrderId;
	}

	public void setPlatformOrderId(String platformOrderId) {
		this.platformOrderId = platformOrderId;
	}

	public void setDetailTime(String detailTime) {
		this.detailTime = detailTime;
	}

	/** default constructor */
	public RegisterOrderT() {
	}

	/** minimal constructor */
	public RegisterOrderT(String orderId, String userId, String registerId,
			String orderState, String registerTime, String userName,
			String userNo, String userTelephone, String sex, String teamId,
			String teamName,String detailTime) {
		this.orderId = orderId;
		this.userId = userId;
		this.registerId = registerId;
		this.orderState = orderState;
		this.registerTime = registerTime;
		this.userName = userName;
		this.userNo = userNo;
		this.userTelephone = userTelephone;
		this.sex = sex;
		this.teamId = teamId;
		this.teamName = teamName;
		this.detailTime=detailTime;
	}

	/** full constructor */
	public RegisterOrderT(String orderId, String userId, String registerId,
			String doctorId, String doctorName, String orderNum,
			String orderState, String orderFee, String registerTime,
			String userName, String userNo, String userTelephone, String sex,
			String teamId, String teamName, String state, Timestamp createDate,
			String hospitalId, String payState,String platformOrderId) {
		this.orderId = orderId;
		this.userId = userId;
		this.registerId = registerId;
		this.doctorId = doctorId;
		this.doctorName = doctorName;
		this.orderNum = orderNum;
		this.orderState = orderState;
		this.orderFee = orderFee;
		this.registerTime = registerTime;
		this.userName = userName;
		this.userNo = userNo;
		this.userTelephone = userTelephone;
		this.sex = sex;
		this.teamId = teamId;
		this.teamName = teamName;
		this.state = state;
		this.createDate = createDate;
		this.hospitalId = hospitalId;
		this.payState = payState;
		this.platformOrderId=platformOrderId;
	}

	// Property accessors
	@Id
	@Column(name = "ORDER_ID", unique = true, nullable = false, length = 20)
	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Column(name = "USER_ID", nullable = false, length = 20)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "REGISTER_ID", nullable = false, length = 20)
	public String getRegisterId() {
		return this.registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	@Column(name = "DOCTOR_ID", length = 20)
	public String getDoctorId() {
		return this.doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	@Column(name = "DOCTOR_NAME", length = 20)
	public String getDoctorName() {
		return this.doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	@Column(name = "ORDER_NUM", length = 20)
	public String getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	@Column(name = "ORDER_STATE", nullable = false, length = 3)
	public String getOrderState() {
		return this.orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	@Column(name = "ORDER_FEE", length = 3)
	public String getOrderFee() {
		return this.orderFee;
	}

	public void setOrderFee(String orderFee) {
		this.orderFee = orderFee;
	}

	@Column(name = "REGISTER_TIME", nullable = false, length = 30)
	public String getRegisterTime() {
		return this.registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	@Column(name = "USER_NAME", nullable = false, length = 20)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "USER_NO", nullable = false, length = 18)
	public String getUserNo() {
		return this.userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	@Column(name = "USER_TELEPHONE", nullable = false, length = 11)
	public String getUserTelephone() {
		return this.userTelephone;
	}

	public void setUserTelephone(String userTelephone) {
		this.userTelephone = userTelephone;
	}

	@Column(name = "SEX", nullable = false, length = 4)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "TEAM_ID", nullable = false, length = 20)
	public String getTeamId() {
		return this.teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	@Column(name = "TEAM_NAME", nullable = false, length = 50)
	public String getTeamName() {
		return this.teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	@Column(name = "STATE", length = 3)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "CREATE_DATE")
	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	@Column(name = "HOSPITAL_ID", length = 20)
	public String getHospitalId() {
		return this.hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	@Column(name = "PAY_STATE", length = 3)
	public String getPayState() {
		return this.payState;
	}

	public void setPayState(String payState) {
		this.payState = payState;
	}

}