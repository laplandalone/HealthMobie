package cn.ISH_Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.axis.message.MessageElement;
import org.apache.axis.message.SOAPHeaderElement;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.jdom.Document;
import org.jdom.Element;

import com.hbgz.pub.xml.XMLComm;

public class DotNetUtil 
{
	public static String invokeFunc(String webUrl) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\"><soap:Header><AuthHeader xmlns=\"http://ISH_Service.cn/\">");
		sb.append("<AuthorizationCode>pF8/tPHpkBoAgVxqLZyOMg==</AuthorizationCode>");
		sb.append("</AuthHeader></soap:Header><soap:Body>");
		sb.append("<SQLExecH  xmlns=\"http://ISH_Service.cn/\"><SQLFEP><DS><SQL><str>select xwxh + swdes from mz_ghde where id = 2229</str></SQL></DS></SQLExecH>");
		sb.append("</soap:Body></soap:Envelope>");
		PostMethod postMethod = new PostMethod(webUrl);
		System.out.println(sb);
		byte[] b = sb.toString().getBytes("utf-8");
		InputStream is = new ByteArrayInputStream(b,0,b.length);
		RequestEntity re = new InputStreamRequestEntity(is,b.length,"application/soap+xml; charset=utf-8");
		postMethod.setRequestEntity(re);
		HttpClient httpClient = new HttpClient();
		int statusCode = httpClient.executeMethod(postMethod);
		String soapResponseData =  postMethod.getResponseBodyAsString();
		return soapResponseData;
	}
	
	public static void main1(String[] args)
	{
		try {
			System.out.println(invokeFunc("http://27.17.0.42:8800/ISH_Service/service1.asmx?wsdl"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String GetTestQuestions(String TeacherName,String Subject){
		  String result = "";
		  try{
		   Service service = new Service();
		   
		   Call call = (Call) service.createCall();
		   
		   SOAPHeaderElement header = new SOAPHeaderElement(new QName("soap"));
		   MessageElement msgEl = new MessageElement(new QName("AuthHeader"));
		   MessageElement msgE2 = new MessageElement(new QName("AuthorizationCode"));
		   msgE2.setValue("pF8/tPHpkBoAgVxqLZyOMg==");
		   msgEl.addChild(msgE2);
		   header.addChild(msgEl);
		   
		   call.addHeader(header);
		   call.setSOAPActionURI("http://ISH_Service.cn/SQLExecH");
		   call.setOperationName(new QName("http://ISH_Service.cn/SQLExecH", "SQLExecH"));
		   call.addParameter("SQLListHIS", XMLType.XSD_STRING, ParameterMode.IN);
		   call.setTargetEndpointAddress(new URL(
		       "http://27.17.0.42:8800/ISH_Service/service1.asmx"));    
		   result  = (String) call.invoke(new Object[] { "select derq from mz_ghde"});   
		   
		  }catch(Exception e){
		   e.printStackTrace();
		  }
		  
		  return result;
		 }
		 
		 public static String invokeFunc() throws Exception{
				Service1 ser = new Service1Locator();
				Service1Soap12Stub servStub;
				URL url;
				try{
					url = new URL("http://27.17.0.42:8800/ISH_Service/service1.asmx?wsdl");
					servStub = (Service1Soap12Stub)ser.getService1Soap12(url);
					AuthHeader authHeader = new AuthHeader();
					authHeader.setAuthorizationCode("pF8/tPHpkBoAgVxqLZyOMg==");
					servStub.setHeader("http://ISH_Service.cn/","AuthHeader",authHeader);
					
//					String retVal =  servStub.SQLExecH("<DS><SQL><str>select * from mz_ghde a,mz_bzdyb b, comm_zgdm c where derq = '2013.02.27' and a.kszjdm=b.ysdm and a.kszjdm=c.zgid  </str></SQL></DS>");
//					String retVal =  servStub.SQLExecH("<DS><SQL><str>select * from comm_zgdm where zgxm ='÷‹∫Ï√∑'</str></SQL></DS>");
//					String retVal =  servStub.SQLExecH("<DS><SQL><str>select top 20 * from mz_ghde where kszjdm ='0392R' and derq > '2014.09.25'  </str></SQL></DS>");
//					String retVal =  servStub.SQLExecH("<DS><SQL><str>select * from mz_ghde where derq between  '2014.09.26' and '2014.10.01' </str></SQL></DS>");
//					String retVal =  servStub.SQLExecH("<DS><SQL><str>select distinct  kszjdm   from mz_ghde a,mz_bzdyb b where a.kszjdm=b.ysdm </str></SQL></DS>");
					String retVal =  servStub.SQLExecH("<DS><SQL><str>select distinct  kszjdm,zgxm    from mz_ghde a,comm_zgdm b where a.kszjdm=b.zgid </str></SQL></DS>");
					return retVal;
				}catch(Exception err)
				{
					err.printStackTrace();
				}
				return "error";
			}
			
			public static void main(String[] args)
			{
				try {
					String ss = invokeFunc();
					Document doc =XMLComm.loadXMLString(ss);
				    Element root = doc.getRootElement();
					List list = 	root.getChildren();
					for(int i=0;i<list.size();i++)
					{
						Element e = (Element) list.get(i);
						System.out.println(e.getChildText("kszjdm"));
						System.out.println(e.getChildText("zgxm"));
					}
					} catch (Exception e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}

}
