/**
 * HospWebServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.HospWebService;

public class HospWebServiceLocator extends org.apache.axis.client.Service implements cn.HospWebService.HospWebService {

    public HospWebServiceLocator() {
    }


    public HospWebServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public HospWebServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for HospWebServiceSoap
    private java.lang.String HospWebServiceSoap_address = "http://www.wahh.com.cn/WebServices/HospWebService.asmx";

    public java.lang.String getHospWebServiceSoapAddress() {
        return HospWebServiceSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String HospWebServiceSoapWSDDServiceName = "HospWebServiceSoap";

    public java.lang.String getHospWebServiceSoapWSDDServiceName() {
        return HospWebServiceSoapWSDDServiceName;
    }

    public void setHospWebServiceSoapWSDDServiceName(java.lang.String name) {
        HospWebServiceSoapWSDDServiceName = name;
    }

    public cn.HospWebService.HospWebServiceSoap_PortType getHospWebServiceSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(HospWebServiceSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getHospWebServiceSoap(endpoint);
    }

    public cn.HospWebService.HospWebServiceSoap_PortType getHospWebServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            cn.HospWebService.HospWebServiceSoap_BindingStub _stub = new cn.HospWebService.HospWebServiceSoap_BindingStub(portAddress, this);
            _stub.setPortName(getHospWebServiceSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setHospWebServiceSoapEndpointAddress(java.lang.String address) {
        HospWebServiceSoap_address = address;
    }


    // Use to get a proxy class for HospWebServiceSoap12
    private java.lang.String HospWebServiceSoap12_address = "http://www.wahh.com.cn/WebServices/HospWebService.asmx";

    public java.lang.String getHospWebServiceSoap12Address() {
        return HospWebServiceSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String HospWebServiceSoap12WSDDServiceName = "HospWebServiceSoap12";

    public java.lang.String getHospWebServiceSoap12WSDDServiceName() {
        return HospWebServiceSoap12WSDDServiceName;
    }

    public void setHospWebServiceSoap12WSDDServiceName(java.lang.String name) {
        HospWebServiceSoap12WSDDServiceName = name;
    }

    public cn.HospWebService.HospWebServiceSoap_PortType getHospWebServiceSoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(HospWebServiceSoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getHospWebServiceSoap12(endpoint);
    }

    public cn.HospWebService.HospWebServiceSoap_PortType getHospWebServiceSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            cn.HospWebService.HospWebServiceSoap12Stub _stub = new cn.HospWebService.HospWebServiceSoap12Stub(portAddress, this);
            _stub.setPortName(getHospWebServiceSoap12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setHospWebServiceSoap12EndpointAddress(java.lang.String address) {
        HospWebServiceSoap12_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * This service has multiple ports for a given interface;
     * the proxy implementation returned may be indeterminate.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (cn.HospWebService.HospWebServiceSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                cn.HospWebService.HospWebServiceSoap_BindingStub _stub = new cn.HospWebService.HospWebServiceSoap_BindingStub(new java.net.URL(HospWebServiceSoap_address), this);
                _stub.setPortName(getHospWebServiceSoapWSDDServiceName());
                return _stub;
            }
            if (cn.HospWebService.HospWebServiceSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                cn.HospWebService.HospWebServiceSoap12Stub _stub = new cn.HospWebService.HospWebServiceSoap12Stub(new java.net.URL(HospWebServiceSoap12_address), this);
                _stub.setPortName(getHospWebServiceSoap12WSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("HospWebServiceSoap".equals(inputPortName)) {
            return getHospWebServiceSoap();
        }
        else if ("HospWebServiceSoap12".equals(inputPortName)) {
            return getHospWebServiceSoap12();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "HospWebService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "HospWebServiceSoap"));
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "HospWebServiceSoap12"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("HospWebServiceSoap".equals(portName)) {
            setHospWebServiceSoapEndpointAddress(address);
        }
        else 
if ("HospWebServiceSoap12".equals(portName)) {
            setHospWebServiceSoap12EndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
