/**
 * HospWebServiceSoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.HospWebService;

public interface HospWebServiceSoap_PortType extends java.rmi.Remote {

    /**
     * 获取医院列表
     */
    public java.lang.String getAllHospital(java.lang.String authorizationCode) throws java.rmi.RemoteException;

    /**
     * 获取科室列表
     */
    public java.lang.String getAllSection(java.lang.String authorizationCode, java.lang.String hospNo) throws java.rmi.RemoteException;

    /**
     * 获取医生列表
     */
    public java.lang.String getDoctorBySectionNo(java.lang.String authorizationCode, java.lang.String sectionNo) throws java.rmi.RemoteException;

    /**
     * 获取指定医生排班
     */
    public java.lang.String getScheduleByExpertNo(java.lang.String authorizationCode, java.lang.String expertNo) throws java.rmi.RemoteException;

    /**
     * 获取指定科室下所有医生的排班
     */
    public java.lang.String getScheduleBySectionNo(java.lang.String authorizationCode, java.lang.String sectionNo) throws java.rmi.RemoteException;

    /**
     * 获取指定排班流水ID的排班信息
     */
    public java.lang.String getScheduleByScheduleId(java.lang.String authorizationCode, java.lang.String scheduleId) throws java.rmi.RemoteException;

    /**
     * 挂号
     */
    public java.lang.String guaHao(java.lang.String authorizationCode, java.lang.String scheduleId, java.lang.String IDCard, java.lang.String linkPhone, java.lang.String isPay, java.lang.String estimateTime, java.lang.String trueName, java.lang.String sex, java.lang.String qq, java.lang.String email) throws java.rmi.RemoteException;

    /**
     * 挂号-无身份证独立接口
     */
    public java.lang.String guaHao_HanWang(java.lang.String authorizationCode, java.lang.String scheduleId, java.lang.String linkPhone, java.lang.String estimateTime, java.lang.String trueName, java.lang.String sex, java.lang.String qq, java.lang.String email) throws java.rmi.RemoteException;

    /**
     * 退号
     */
    public java.lang.String tuiHao(java.lang.String authorizationCode, java.lang.String bookId, java.lang.String IDCard, java.lang.String linkPhone) throws java.rmi.RemoteException;

    /**
     * 查询预约流水信息
     */
    public java.lang.String getBookingState(java.lang.String authorizationCode, java.lang.String bookid) throws java.rmi.RemoteException;

    /**
     * 支付
     */
    public java.lang.String pay(java.lang.String authorizationCode, java.lang.String bookid, java.lang.String idCard, java.lang.String trueName) throws java.rmi.RemoteException;

    /**
     * 查询会员注册信息
     */
    public java.lang.String getUserInfo(java.lang.String authorizationCode, java.lang.String cardno) throws java.rmi.RemoteException;

    /**
     * 查询
     */
    public cn.HospWebService.GetResultResponseGetResultResult getResult(java.lang.String authorString, java.lang.String runSql) throws java.rmi.RemoteException;
}
