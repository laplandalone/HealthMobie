package com.hbgz.pub.apipay;

import java.net.URLEncoder;

import com.hbgz.pub.util.Keys;
import com.hbgz.pub.util.Rsa;
import com.tools.pub.utils.ObjectCensor;

public class AlipaySign 
{
	public static String sign(String subject, String body, String totalFee, String outTradeNo)
	{
		String orderInfo = "";
		if(ObjectCensor.isStrRegular(subject, body, totalFee))
		{
			String payOrderInfo = getNewOrderInfo(subject, body, totalFee, outTradeNo);
			String sign = Rsa.sign(payOrderInfo, Keys.PRIVATE);
			sign = URLEncoder.encode(sign);
			payOrderInfo += "&sign=\"" + sign + "\"&" + getSignType();
			orderInfo = payOrderInfo;
		}
		return orderInfo;
	}
	
	private static String getSignType() 
	{
		return "sign_type=\"RSA\"";
	}

	private static String getNewOrderInfo(String subject, String body, String totalFee, String outTradeNo) 
	{
		StringBuilder sb = new StringBuilder();
		sb.append("partner=\"");
		sb.append(Keys.DEFAULT_PARTNER);
		sb.append("\"&out_trade_no=\"");
		sb.append(outTradeNo);
		sb.append("\"&subject=\"");
		sb.append(subject);
		sb.append("\"&body=\"");
		sb.append(body);
		sb.append("\"&total_fee=\"");
		sb.append(totalFee);
		sb.append("\"&notify_url=\"");

		// 网址需要做URL编码
		sb.append(URLEncoder.encode("http://www.hiseemedical.com:10821/alipay/planPayRst.htm"));
		sb.append("\"&service=\"mobile.securitypay.pay");
		sb.append("\"&_input_charset=\"UTF-8");
//		sb.append("\"&return_url=\"");
		sb.append("\"&show_url=\"");
		sb.append(URLEncoder.encode("http://m.alipay.com"));
		sb.append("\"&payment_type=\"1");
		sb.append("\"&seller_id=\"");
		sb.append(Keys.DEFAULT_SELLER);

		// 如果show_url值为空，可不传
		// sb.append("\"&show_url=\"");
		sb.append("\"&it_b_pay=\"30m");
		sb.append("\"");

		return new String(sb);
	}

//	private static String getOutTradeNo() 
//	{
//		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss");
//		Date date = new Date();
//		String key = format.format(date);
//
//		java.util.Random r = new java.util.Random();
//		key += r.nextInt();
//		key = key.substring(0, 15);
//		return key;
//	}
}
