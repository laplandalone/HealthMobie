/**
 * Service1Soap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.ISH_Service;

public interface Service1Soap_PortType extends java.rmi.Remote {
    public java.lang.String authtest() throws java.rmi.RemoteException;
    public void SQLExec(java.lang.Object[] SQLListHIS, java.lang.Object[] SQLListFEP, cn.ISH_Service.SQLExecDs ds, cn.ISH_Service.holders._returnHolder SQLExecResult, cn.ISH_Service.holders.SQLExecResponseDsHolder ds2) throws java.rmi.RemoteException;
    public java.lang.String SQLExecA(java.lang.String SQLFEP) throws java.rmi.RemoteException;
    public java.lang.String SQLExecH(java.lang.String SQLListHIS) throws java.rmi.RemoteException;
    public void SQLExec7(java.lang.Object[] SQLListHIS, java.lang.Object[] SQLListFEP, cn.ISH_Service.SQLExec7Ds ds, cn.ISH_Service.holders._returnHolder SQLExec7Result, cn.ISH_Service.holders.SQLExec7ResponseDsHolder ds2) throws java.rmi.RemoteException;
    public java.lang.String SQLExecH7(java.lang.String SQLListHIS) throws java.rmi.RemoteException;
    public java.lang.String appointment_GetDetail(java.lang.String begin_date, java.lang.String end_date, java.lang.String pernrs, java.lang.String instid, java.lang.String OU) throws java.rmi.RemoteException;
    public java.lang.String appointment_Create(java.lang.String name, java.lang.String sex, java.lang.String birthday, java.lang.String IDNo, java.lang.String instid, java.lang.String OU, java.lang.String doctor, java.lang.String appdate, java.lang.String AMPM, java.lang.String appway) throws java.rmi.RemoteException;
    public java.lang.String appointment_Cancel(java.lang.String appointmentid, java.lang.String cancelreason) throws java.rmi.RemoteException;
}
