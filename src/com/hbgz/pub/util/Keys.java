﻿/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 * 
 *  提示：如何获取安全校验码和合作身份者id
 *  1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *  2.点击“商家服务”(https://b.alipay.com/order/myorder.htm)
 *  3.点击“查询合作者身份(pid)”、“查询安全校验码(key)”
 */
package com.hbgz.pub.util;
//
// 请参考 Android平台安全支付服务(msp)应用开发接口(4.2 RSA算法签名)部分，并使用压缩包中的openssl RSA密钥生成工具，生成一套RSA公私钥。
// 这里签名时，只需要使用生成的RSA私钥。
// Note: 为安全起见，使用RSA私钥进行签名的操作过程，应该尽量放到商家服务器端去进行。
public final class Keys {
	
	public static final String APP_ID = "2088411973102512";

	//合作身份者id，以2088开头的16位纯数字
	public static final String DEFAULT_PARTNER = "2088411973102512";

	//收款支付宝账号
	public static final String DEFAULT_SELLER = "18907181680@189.cn";

	//商户私钥，自助生成
	public static final String PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAOEt5vsnyRgDk87yChRTASb3iFDVTkmMGRPUw23lyi46W9rqHgSB3q5Q74wr1qNReQ3ix1xfaGkZbnCUV2RLRYYLhUlIjmk+XsBelo/Yvc8K8R/iFjqXgWY31IXqlpHFyAmVVtRewsR0LWTY6brJL+dkg7wgHo9lZPxr+RJV7zkzAgMBAAECgYBFtE7fCjbOrzTPB8+k8PjXViKlWxJL5AlTQTZpy4slej066+P04zrKXRV6H4vmrG67pKqv5nzMo+mzAch3rHFNc47SicVlg/U496ZH/S0EQEahhpPE+RM4ZIdxJBWFamgX5L6P+ocqgocClWv01PUM25JA7QRz0sz3axhCm8y7OQJBAPV++AxrszD7fP26FAu41sMlyiRWr6z7pccJZMhCPYaL0pPR8lFEkXIVeqCn7he0zSvoyo0COAVejBTuittONC8CQQDq0GU9s387Mp5C7RHFXBKmebsbObZKDtAAvVQYY9TY6iM78Fyhub9Y14gj26iq2EUuQZ7kXyxQ74Hq2b+LS1Y9AkAE0TcA12Quw+CSAgK+sCPWtHkg+Wp9FHsOSsrlexdF+pxpNidxHM1V1cIQQPtVD8Eu6WSAG9kJke9hwcSLxR8VAkEAi/3yXfbFw+VJtVzhW2ipNdcdVf3yT/TEzawSlvftzeTJXNLQZxr2mWDmKXqr9C88D3fP4xdatGYWbo2jRMiFgQJANpfeTLCVAYOwIbXkfwNSdzgCWRnNCUXIz5MteDvz06Tklg3eh4Vz4iG4Y4oXMxHEqQmkRaAFAz2QjJUWiMR1HQ==";

	public static final String PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDhLeb7J8kYA5PO8goUUwEm94hQ1U5JjBkT1MNt5couOlva6h4Egd6uUO+MK9ajUXkN4sdcX2hpGW5wlFdkS0WGC4VJSI5pPl7AXpaP2L3PCvEf4hY6l4FmN9SF6paRxcgJlVbUXsLEdC1k2Om6yS/nZIO8IB6PZWT8a/kSVe85MwIDAQAB";

	public static final String URL = "https://openapi.alipaydev.com/gateway.do";
}
