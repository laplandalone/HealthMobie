package cn.ISH_Service;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.axis.AxisFault;

public class Test {

public static void main(String[] args) throws ServiceException, RemoteException {
	
	Service1 locator = new Service1Locator();
	
	Service1Soap_BindingStub bindingStub = new Service1Soap_BindingStub();
	AuthHeader header = new AuthHeader("pF8/tPHpkBoAgVxqLZyOMg==");
	
	Service1Soap_PortType portType = locator.getService1Soap12();
	
	portType.SQLExecH("SQLListHIS");
}
}
