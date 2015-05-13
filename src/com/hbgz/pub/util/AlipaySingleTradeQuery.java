package com.hbgz.pub.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;

import com.hbgz.pub.apipay.AlipayCore;
import com.hbgz.pub.apipay.MD5;
import com.hbgz.pub.xml.XMLComm;

public class AlipaySingleTradeQuery 
{
	private static Log log = LogFactory.getLog(AlipaySingleTradeQuery.class);
	
	public static JSONObject qrySingleTrade(String tradeNo, String outTradeNo)
	{
		JSONObject obj = new JSONObject();
		if(ObjectCensor.isStrRegular(tradeNo) || ObjectCensor.isStrRegular(outTradeNo))
		{
			String param = getParam(tradeNo, outTradeNo);
			log.error(param);
			String sign = MD5.sign(param, Keys.PRIVATE_KEY, "UTF-8");
			param += "&sign=" + sign + "&sign_type=MD5";
			log.error(param);
			String tradeMsg = HttpUtil.http("https://mapi.alipay.com/gateway.do", param);
			log.error(tradeMsg);
			Document doc = XMLComm.loadXMLString(tradeMsg);
			List isSuccessList = XMLComm.findEleByKey(doc, "is_success");
			String isSuccess = String.valueOf(((Element) isSuccessList.get(0)).getText());
			if("T".equals(isSuccess))
			{
				List tradeList = XMLComm.findEleByKey(doc, "trade");
				Element trade = (Element) tradeList.get(0);
				obj.element("tradeNo", trade.getChildText("trade_no"));
				obj.element("outTradeNo", trade.getChildText("out_trade_no"));
				obj.element("tradeStatus", trade.getChildText("trade_status"));
			}
			else
			{
				List errorList = XMLComm.findEleByKey(doc, "error");
				obj.element("errorMsg", String.valueOf(((Element) errorList.get(0)).getText()));
			}
		}
		return obj;
	}
	
	private static String getParam(String tradeNo, String outTradeNo)
	{
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("service", "single_trade_query");
		paramMap.put("partner", Keys.DEFAULT_PARTNER);
		paramMap.put("_input_charset", "UTF-8");
		paramMap.put("trade_no", tradeNo);
		paramMap.put("out_trade_no", outTradeNo);
		paramMap = AlipayCore.paraFilter(paramMap);
		return AlipayCore.createLinkString(paramMap);
	}
	
	public static void main(String[] args) 
	{
		JSONObject obj = qrySingleTrade("", "ZF201407211004521193");
		System.err.println(obj);
	}
}
