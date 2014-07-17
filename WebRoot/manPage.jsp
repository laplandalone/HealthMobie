<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	if(session.getAttribute("username") == null)
	{
		response.sendRedirect("/login.jsp");
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title></title>
  	<script type="text/javascript" src="/pub/js/jquery-1.9.1.min.js"></script>
  	<script type="text/javascript" src="/pub/js/art/artDialog.js?skin=default"></script>
  	<script type="text/javascript" src="/pub/js/util.js"></script>
  	<script type="text/javascript" src="/js/manPage.js"></script>
  	<link rel="stylesheet" href="/pub/css/bankList.css" type="text/css"></link>
  	<link rel="stylesheet" href="/pub/css/manPage.css" type="text/css"></link>
  </head>	
  <body>
  	<c:if test="${! empty novalue}">
  		<script language="javascript">
  			$(document).ready(function(){
  				art.dialog({
				    lock: true,
				    content: "查询结果为空!请核实信息后重新查询",
				    icon: 'error',
				    ok: function () {
				        return true;
				    },
				});	
  			});
  		</script>
  	</c:if>
    <div class="top">
    	<div class="logo">&nbsp;</div>
    </div>
    <div class="content">
    	<div class="conttop">
    		<div class="titleleft">&nbsp;</div>
    		<div class="titlemain">
    			<div class="titlemaintop">&nbsp;</div>
    			<div class="titlemaincont">
    				<form action="/platform/queryInfo.do" id="queryOrder" method="post">
						<span>商户订单号：</span><span><input type="text" id="merOrderId" name="merOrderId" style="width:150px;height:20px" value="${sessionScope.merOrderId}"/></span>&nbsp;&nbsp;<span>平台交易流水：</span><span><input type="text" id="platOrderFlow" name="platOrderFlow" style="width:150px;height:20px" value="${sessionScope.platOrderFlow}"/></span>&nbsp;&nbsp;<span>平台订单号：</span><span><input type="text" id="platOrderId" name="platOrderId" style="width:150px;height:20px" value="${sessionScope.platOrderId}"/></span>&nbsp;&nbsp;<span style="position:absolute;top:24px;right:20px;"><a href="javascript:void(0)" onclick="queryFunc()" ><img src="/pub/img/query.png" /></a></span>
					</form>
				</div>
    		</div>
    		<div class="titleright">&nbsp;</div>
    	</div>
    	<div class="contmain">
    		<div class="contmaint">
	    		<div class="contpayleft">&nbsp;</div>
	    		<div class="contpaymain">
	    			<c:if test="${! empty object}">
						<div class="contleftframe">&nbsp;</div>
						<div class="contmiddleframe">查询结果</div>
						<div class="contrightframe">&nbsp;</div>
					</c:if>
				</div>
	    		<div class="contpayright">&nbsp;</div>
    		</div>
    		<div class="contmainb">
	    		<div class="contbankleft">&nbsp;</div>
	    		<div class="contbankmain">
	    			<c:choose>
	    				<c:when test="${! empty object}">
							<table id="resPage" width="972" height="373" class="resPage" cellpadding="0" cellspacing="0">
								<tr>
									<td align="right" width="180" class="resKey">平台交易流水号：</td>
									<td align="left" width="792" class="resValue">${(object.pay_order_no=='null')?'':(object.pay_order_no)}</td>
								</tr>
								<tr>
									<td align="right" class="resKey">平台订单号：</td>
									<td align="left" class="resValue">${(object.tran_order_id=='null')?'':(object.tran_order_id)}</td>
								</tr>
								<tr>
									<td align="right" class="resKey">商户订单号：</td>
									<td align="left" class="resValue">${(object.out_trade_no=='null')?'':(object.out_trade_no)}</td>
								</tr>
								<tr>
									<td align="right" class="resKey">商品名称：</td>
									<td align="left" class="resValue">${(object.subject=='null')?'':(object.subject)}</td>
								</tr>
								<tr>
									<td align="right" class="resKey">商品价格（元）：</td>
									<td align="left" class="resValue">${(object.price=='null')?'':(object.price)}</td>
								</tr>
								<tr>
									<td align="right" class="resKey">购买数量：</td>
									<td align="left" class="resValue">${(object.quantity=='null')?'':(object.quantity)}</td>
								</tr>
								<tr>
									<td align="right" class="resKey">交易金额（元）：</td>
									<td align="left" class="resValue">${(object.total_fee=='null')?'':(object.total_fee)}</td>
								</tr>
								<tr>
									<td align="right" class="resKey">支付银行：</td>
									<td align="left" class="resValue">${(object.pay_bank_name=='null')?'':(object.pay_bank_name)}</td>
								</tr>
								<tr>
									<td align="right" class="resKey">商品描述：</td>
									<td align="left" class="resValue">${(object.body=='null')?'':(object.body)}</td>
								</tr>
								<tr>
									<td align="right" class="resKey">订单状态：</td>
									<td align="left" class="resValue">${(object.pay_state_name=='null')?'':(object.pay_state_name)}</td>
								</tr>
								<tr>
									<td align="right" class="resKey">订单生成时间：</td>
									<td align="left" class="resValue">${object.order_create_date}</td>
								</tr>
								<tr>
									<td align="right" class="resKey">订单状态时间：</td>
									<td align="left" class="resValue">${object.order_state_date}</td>
								</tr>
								<tr>
									<td align="right" class="resKey">商户已反馈：</td>
									<td align="left" class="resValue">${(object.seller_reception_name=='null')?'':(object.seller_reception_name)}</td>
								</tr>
								<tr>
									<td align="right" class="resKey" style="border-bottom:none">银行订单流水：</td>
									<td align="left" class="resValue" style="border-bottom:none">${(object.bank_order_seq=='null')?'':(object.bank_order_seq)}</td>
								</tr>
							</table>
	    				</c:when>
	    				<c:otherwise>
	    					<img src="/pub/img/pagelogo.jpg" width="238" height="71" style="margin-left: 380px;margin-top: 100px;"/>
	    				</c:otherwise>
	    			</c:choose>
				</div>
	    		<div class="contbankright">&nbsp;</div>
    		</div>
    	</div>
	    <div class="bottom">
	    	<div class="bottomleft"></div>
	    	<div class="bottomcont"></div>
	    	<div class="bottomright"></div>
	    </div>
	    <div style="clear:both;"></div>
	    <c:if test="${! empty object}">
	    	<c:if test="${object.pay_state == '02' || object.pay_state == '22'}">
    			<input type="hidden" id="tranOrderId" name="tranOrderId" value="${object.tran_order_id}" />
    			<input type="hidden" id="bankCode" name="bankCode" value="${object.pay_bank_code}" />
    			<input type="hidden" id="outTradeNo" name="outTradeNo" value="${object.out_trade_no}" />
			    <div style="width:980px;height:40px;margin-left:auto;margin-right:auto;margin-top:5px;padding-left:20px;">
			    	<a href="javascript:void(0)" onclick="refundClick()"><img src="/pub/img/refund.png" /></a>
			    </div>
		    </c:if>
	    </c:if>
    </div>
  </body>
</html>
