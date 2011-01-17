<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<HEAD><TITLE><bean:message key="page.index.title"/></TITLE>
<link href="<html:rewrite page="/js/general.css"/>" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!--
.style1 {font-family: "Comic Sans MS"}
-->
</head>

<body onKeyPress="keyCtrl(event);" class="BgForm">
	<html:form action="/posOrder/paymentSave">
	<html:hidden property="posOrderId"/>
	<html:hidden property="subaction"/>
	<html:hidden property="endTime"/>
	<TABLE class=TableBg height=600 cellSpacing=40 cellPadding=1 width="100%" border=0>
  <TR valign="top">
    <TD valign="top">
      <TABLE bgcolor="#ECE9D8" height=100 cellSpacing=0 cellPadding=1 width="100%" border="0">
	      <tr>
	        <td>
	        	<table width="100%"  border="0" cellspacing="1" cellpadding="1">
	        	<html:errors/>
	          <tr>
	            <td width="30%" class="FontBold" align="right"><font size="5">Total</font></td>
	            <td width="5%" class="FontBold" align="center"><font size="5">:</font></td>
	            <td width="65%" class="FontGen"><font size="5">
	            	<logic:present name="posOrder"><bean:write name="posOrder" property="formatedTotalAmountAfterDiscountAndTax"/></logic:present></font>
	            </td>      
	          </tr>
	          <tr>
	            <td width="30%" class="FontBold" align="right">Pembayaran</td>
	            <td width="5%" class="FontBold" align="center">:</td>
		          <td width="65%" class="FontBold">
		          	<html:radio property="paymentMethod" value="CASH" onclick="this.form.subaction.value='refresh';this.form.submit();"/>&nbsp;CASH
		          	<html:radio property="paymentMethod" value="CREDITCARD" onclick="this.form.subaction.value='refresh';this.form.submit();"/>&nbsp;CREDIT CARD
		          </td>
	          </tr>
	          <logic:present name="bankList">
	          <tr>
	            <td width="30%" class="FontBold" align="right">Bank Penerbit</td>
	            <td width="5%" class="FontBold" align="center">:</td>
		          <td width="65%" class="FontBold">
		          	<html:select property="bankId" styleClass="Form">
									<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
									<html:options collection="bankList" property="id" labelProperty="name" />
              	</html:select>
		          </td>
	          </tr>
	          </logic:present>
	          <tr>
	            <td width="30%" class="FontBold" align="right">No Credit Card</td>
	            <td width="5%" class="FontBold" align="center">:</td>
		          <td width="65%" class="FontBold">
		          	<html:text property="creditCardNumber" size="20" styleClass="Form"/>
		          </td>
	          </tr>
	          <tr>
	            <td width="30%" class="FontBold" align="right">Pemilik Credit Card</td>
	            <td width="5%" class="FontBold" align="center">:</td>
		          <td width="65%" class="FontBold">
		          	<html:text property="creditCardOwner" size="40" styleClass="Form"/>
		          </td>
	          </tr>
	          <tr>
	            <td width="30%" class="FontBold" align="right">Bayar</td>
	            <td width="5%" class="FontBold" align="center">:</td>
							<td width="65%" class="FontGen">
							<html:text property="cashAmount" size="20" styleClass="Form" onchange="javascript:hahahihi();" />
							</td>
					</tr>
	        <tr>
	            <td width="30%" class="FontBold" align="right">Kembalian</td>
	            <td width="5%" class="FontBold" align="center">:</td>
							<td width="65%" class="FontGen">
								<html:text property="changeAmount" size="20" styleClass="Form" readonly="true"/>
							</td>
	        </tr>
	        <tr>
	        	<td colspan="3">&nbsp;</td>
	        </tr>
	        <tr>
	        	<td colspan="3">&nbsp;</td>
	        </tr>
		    </TBODY>
		    </table>
		    <table width="100%" border="0" cellspacing="1" cellpadding="1" bgcolor="#cdcdcd">
	        <tr bgcolor="#ECE9D8">
	          <td width="100" align="center"><b>SHIFT + S</b></td>
	          <td width="30%" class="FontGen">Simpan</td>
	          <td width="100" align="center"><b>SHIFT + C</b></td>
	          <td width="30%" class="FontGen">Kembali</td>
	        </tr>
        </table>
	    </td>
	  </tr>
	</TBODY>
	</table>
	</html:form>
</body>
</html>

<script language="javascript">
function keyCtrl(evt){
	
	//alert("Key : "+evt.keyCode);
	
	if(evt.keyCode == 83) {
		document.forms[0].subaction.value='';
		document.forms[0].submit();
	}
	if(evt.keyCode == 67)
		window.location="<html:rewrite page="/posOrder/form.do"/>";
}

function hahahihi(){
	var x = document.forms[0].cashAmount.value;
	var z = <logic:present name="posOrder"><bean:write name="posOrder" property="totalAmountAfterDiscountAndTax"/></logic:present><logic:notPresent name="posOrder">0</logic:notPresent>;
	document.forms[0].changeAmount.value = x - z;
}

</script>
