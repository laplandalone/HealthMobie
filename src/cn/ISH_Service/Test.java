package cn.ISH_Service;

import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.axis.AxisFault;

import cn.HospWebService.HospWebService;
import cn.HospWebService.HospWebServiceLocator;
import cn.HospWebService.HospWebServiceSoap12Stub;

public class Test {

	public static void main1(String[] args) throws ServiceException,
			RemoteException {

		Service1 locator = new Service1Locator();

		Service1Soap_BindingStub bindingStub = new Service1Soap_BindingStub();
		AuthHeader header = new AuthHeader("pF8/tPHpkBoAgVxqLZyOMg==");

		Service1Soap_PortType portType = locator.getService1Soap12();

		portType.SQLExecH("SQLListHIS");
	}

	public static void main(String[] args) throws Exception {
		HospWebService hospWebService = new HospWebServiceLocator();
		URL url = new URL("http://www.wahh.com.cn/WebServices/HospWebService.asmx?wsdl");
		HospWebServiceSoap12Stub soap12Stub = (HospWebServiceSoap12Stub) hospWebService.getHospWebServiceSoap12(url);
		String ss = soap12Stub.getAllHospital("b07a200a29d74184af67032a2e19bf69");
		System.out.println(ss);
	}
}
