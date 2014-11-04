/**
 * HospWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.HospWebService;

public interface HospWebService extends javax.xml.rpc.Service {
    public java.lang.String getHospWebServiceSoapAddress();

    public cn.HospWebService.HospWebServiceSoap_PortType getHospWebServiceSoap() throws javax.xml.rpc.ServiceException;

    public cn.HospWebService.HospWebServiceSoap_PortType getHospWebServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getHospWebServiceSoap12Address();

    public cn.HospWebService.HospWebServiceSoap_PortType getHospWebServiceSoap12() throws javax.xml.rpc.ServiceException;

    public cn.HospWebService.HospWebServiceSoap_PortType getHospWebServiceSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
