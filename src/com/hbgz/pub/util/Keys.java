/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 * 
 *  ��ʾ����λ�ȡ��ȫУ����ͺ��������id
 *  1.������ǩԼ֧�����˺ŵ�¼֧������վ(www.alipay.com)
 *  2.������̼ҷ���(https://b.alipay.com/order/myorder.htm)
 *  3.�������ѯ���������(pid)��������ѯ��ȫУ����(key)��
 */
package com.hbgz.pub.util;
//
// ��ο� Androidƽ̨��ȫ֧������(msp)Ӧ�ÿ����ӿ�(4.2 RSA�㷨ǩ��)���֣���ʹ��ѹ�����е�openssl RSA��Կ���ɹ��ߣ�����һ��RSA��˽Կ��
// ����ǩ��ʱ��ֻ��Ҫʹ�����ɵ�RSA˽Կ��
// Note: Ϊ��ȫ�����ʹ��RSA˽Կ����ǩ���Ĳ������̣�Ӧ�þ����ŵ��̼ҷ�������ȥ���С�
public final class Keys {
	
//	��������ݣ�PID��2088901022394613 
//	��ȫУ���루Key���� 1afffck50krd32ao0w4zppejvqridi2l
//	֧�����˺ţ�1848881936@qq.com
	
//	public static final String APP_ID = "2088411973102512";
//	public static final String APP_ID = "2088901022394613";
	//���������id����2088��ͷ��16λ������
//	public static final String DEFAULT_PARTNER = "2088411973102512";
	public static final String DEFAULT_PARTNER = "2088901022394613";
	//�տ�֧�����˺�
//	public static final String DEFAULT_SELLER = "18907181680@189.cn";
	public static final String DEFAULT_SELLER = "1848881936@qq.com";
	//�̻�˽Կ����������
//	public static final String PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAOEt5vsnyRgDk87yChRTASb3iFDVTkmMGRPUw23lyi46W9rqHgSB3q5Q74wr1qNReQ3ix1xfaGkZbnCUV2RLRYYLhUlIjmk+XsBelo/Yvc8K8R/iFjqXgWY31IXqlpHFyAmVVtRewsR0LWTY6brJL+dkg7wgHo9lZPxr+RJV7zkzAgMBAAECgYBFtE7fCjbOrzTPB8+k8PjXViKlWxJL5AlTQTZpy4slej066+P04zrKXRV6H4vmrG67pKqv5nzMo+mzAch3rHFNc47SicVlg/U496ZH/S0EQEahhpPE+RM4ZIdxJBWFamgX5L6P+ocqgocClWv01PUM25JA7QRz0sz3axhCm8y7OQJBAPV++AxrszD7fP26FAu41sMlyiRWr6z7pccJZMhCPYaL0pPR8lFEkXIVeqCn7he0zSvoyo0COAVejBTuittONC8CQQDq0GU9s387Mp5C7RHFXBKmebsbObZKDtAAvVQYY9TY6iM78Fyhub9Y14gj26iq2EUuQZ7kXyxQ74Hq2b+LS1Y9AkAE0TcA12Quw+CSAgK+sCPWtHkg+Wp9FHsOSsrlexdF+pxpNidxHM1V1cIQQPtVD8Eu6WSAG9kJke9hwcSLxR8VAkEAi/3yXfbFw+VJtVzhW2ipNdcdVf3yT/TEzawSlvftzeTJXNLQZxr2mWDmKXqr9C88D3fP4xdatGYWbo2jRMiFgQJANpfeTLCVAYOwIbXkfwNSdzgCWRnNCUXIz5MteDvz06Tklg3eh4Vz4iG4Y4oXMxHEqQmkRaAFAz2QjJUWiMR1HQ==";
	public static final String PRIVATE =
			"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKummTo1qlmqwPexCi4d2YJz399aipL6ERyfx0BYdjgyt/sL6xmzLr5rTTxh59uI4Wh6AK0lspr/5Hj9r2TRwmGK4EdMpStobNW5EteiuIh0EJUiVnQkNX9/qzJDlI6C9DUGBYnKHzkb4X6ACWSPa8UB4WXnPetO1iP/rHHF7Nt3AgMBAAECgYB0cR28+S7IiSdCX90SD7m/3y9dayRaND1rd5BJPDlmQjHAogMoef8Zudy5O4l3ydFveGQBEXOp5jFtSlqzQABl4eU5lhORZHWnWMtDS63gNaHPdhieLL/FFYrBhGmGy31dbAdWkuCTv22RPKm7pdOG0x7J7MJiTkrAAsRWlrf0AQJBAOMyvxIQ9OT0r3bea0uurcL+WHUVvhTQggaweJxGbuhC8WTr8IVMXEu0LQU5OPmGc20W5zFKT/Fj3XAUjHQCfncCQQDBaSxatAwtxOV8wEdmtKfrF1gTNTFwnfOeURe/fsv0REo4YE503SSnK7ayYtqdmuU4ByVy9VnsqMr95XCj7ssBAkABfLqNdrjzqrpfT9Np+mm+xgV0NsE4x6iiPJN9imR9drq3y2eWp8pO4I4O47IAyCWHSEgZJYBidyHi8u98buu/AkEAvfL46KvjOiAh8f81IJ1UPQLUMSkQwTWfSWEDHcL9s4xOCEgtRYDauoOoDlIfuqGhdQEvulNUWaT8l5Z6pcWkAQJBAMekMMILHx6Shz9iJLAsmXNdX8xTk2I4AQqYroqGGU7bG6IH/T88NGegCVigyRlOrG/YHBpvBBep+beX3D144WQ=";
//	public static final String PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDhLeb7J8kYA5PO8goUUwEm94hQ1U5JjBkT1MNt5couOlva6h4Egd6uUO+MK9ajUXkN4sdcX2hpGW5wlFdkS0WGC4VJSI5pPl7AXpaP2L3PCvEf4hY6l4FmN9SF6paRxcgJlVbUXsLEdC1k2Om6yS/nZIO8IB6PZWT8a/kSVe85MwIDAQAB";

	public static final String URL  = "https://openapi.alipaydev.com/gateway.do";
}
