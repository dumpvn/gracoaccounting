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
<html:form action="/posOrder/save" focus="itemCode">
<html:hidden property="posOrderId"/>
<html:hidden property="subaction"/>
<html:hidden property="organizationId"/>
<html:hidden property="currencyId"/>
<html:hidden property="status"/>
<html:hidden property="memberId"/>
<html:hidden property="discount"/>
<html:hidden property="tax"/>
<TABLE class=TableBg height=600 cellSpacing=40 cellPadding=1 width="100%" border=0>
  <TR valign="top">
    <TD valign="top">
      <TABLE bgcolor="#ECE9D8" height=100 cellSpacing=0 cellPadding=1 width="100%" border="0">
        <TR>
          <TD>
            <TABLE cellSpacing=1 cellPadding=1 width="90%" align=center border=0>
             <tr>
              <td align="center">
              	<font size="5">T O T A L :
              		<logic:present name="totalAmountAfterDiscountAndTax"><bean:write name="totalAmountAfterDiscountAndTax"/></logic:present>
              	</font>
              </td>
            </tr>
          </table>
        	<table width="100%"  border="0" cellspacing="1" cellpadding="1">
        	<html:errors/>
          <tr>
            <td width="23%" class="FontBold">Order number</td>
            <td width="22%" class="style1"><html:text property="posOrderNumber" size="20" readonly="true" styleClass="Form"/></td>
            <td width="23%" class="FontBold">Cashier</td>
            <td width="32%" class="style1">
            	<input type="text" name="cashier" size="20" class="Form" value="<bean:write name="user" property="userName" scope="session"/>"/>
            	<!--
            	<logic:present name="user"><bean:write name="user" property="userName" scope="session"/></logic:present>
            	-->
            </td>
          </tr>
          <tr>
            <td width="23%" class="FontBold">Order date</td>
            <td width="22%"><html:text property="posOrderDate" size="20" styleClass="Form"/></td>
            <td width="23%" class="FontBold">Member Number </td>
            <td width="32%" class="style1"><html:text property="memberNumber" size="20" styleClass="Form" onchange="this.form.subaction.value='form';this.form.submit();"/></td>
          </tr>
          <tr>
            <td width="23%" class="FontBold">Order time</td>
            <td width="22%"><html:text property="startTime" size="20" styleClass="Form"/></td>
	         	<td width="23%" class="FontBold">Member Name</td>
						<td width="32%" class="style1">
							<logic:present name="posOrders"><input type="text" name="memberName" size="20" class="Form" value="<bean:write name="posOrders" property="memberName" scope="session"/>"/></logic:present>
						</td>
          </tr>
          <tr>
            <td colspan="4">&nbsp;</td>
          </tr>
          <tr>
            <td height="21" colspan="4">
            <table width="100%"  border="1" cellspacing="1" cellpadding="1" bgcolor="#000000">
            <TBODY>
              <tr>
                <td width="6%"  align="center" class=CellHead><b>NO</b></td>
                <td width="32%" align="center" class=CellHead><b>ITEM</b></td>
                <td width="18%" align="center" class=CellHead><b>QUANTITY</b></td>
                <td width="22%" align="center" class=CellHead><b>PRICE</b></td>
                <td width="22%" align="center" class=CellHead><b>TOTAL</b></td>
              </tr>
<%int i=0; 
int f = 0;
%>
              <logic:present name="posOrderDetailLst">
              <logic:iterate id="posOrderDetail" name="posOrderDetailLst" type="com.mpe.financial.model.PosOrderDetail" scope="request">
              <bean:size id="x" name="posOrderDetailLst"/>
              <%f = Integer.parseInt(String.valueOf(x));%>
              <%if ((i+1) != f) {%>
              <tr>
                <td align="center" class="Cell1"><%=++i%>.</td>
                <td align="left" class="Cell1">
									<bean:write name="posOrderDetail" property="id.item.code" scope="page"/><br><bean:write name="posOrderDetail" property="id.item.name" scope="page"/>
                </td>
                <td align="right" class="Cell1">        	           
                	<bean:write name="posOrderDetail" property="quantity" scope="page"/>&nbsp;
                	<bean:write name="posOrderDetail" property="itemUnit.name" scope="page"/>
                </td>
                <td align="right" class="Cell1">
              		<bean:write name="posOrderDetail" property="formatedPrice"/>
                </td>
                <td align="right" class="Cell1">
                	<bean:write name="posOrderDetail" property="formatedQuantityPrice"/>
                </td>
              </tr>
           		<% } else if ((i+1) == f) {%>
              <tr>
                <td align="center" clasfs="Cell1"><%=++i%>.<input type="hidden" name="itemId" value="<bean:write name="posOrderDetail" property="id.item.id" scope="page"/>"/></td>
                <td align="left" class="Cell1">
                		<bean:write name="posOrderDetail" property="id.item.code" scope="page"/><br><bean:write name="posOrderDetail" property="id.item.name" scope="page"/>
                </td>            
                <td align="right" class="Cell1">
                	<input type="text" name="quantity" size="3" class="Form" value="<bean:write name="posOrderDetail" property="quantity" scope="page"/>" onchange="this.form.subaction.value='UPDATEPOSORDERDETAIL';this.form.submit();"/>&nbsp;
                	<bean:write name="posOrderDetail" property="itemUnit.name" scope="page"/>
                </td>
                <td align="right" class="Cell1">
              		<bean:write name="posOrderDetail" property="formatedPrice"/>
                </td>
                <td align="right" class="Cell1">
              		<bean:write name="posOrderDetail" property="formatedQuantityPrice"/>
                </td>
              </tr>
              <%}%>
              </logic:iterate>
              </logic:present>
              <tr>
                <td class="Cell1">&nbsp;</td>
                <td align="right" class="Cell1">
                	<logic:empty name="posOrderForm" property="subaction" scope="request">
                	<html:text styleClass="Form" property="itemCode" size="37" onchange="this.form.subaction.value='ADDPOSORDERDETAIL';this.form.submit();"/>
                	</logic:empty>
                	<logic:notEmpty name="posOrderForm" property="subaction" scope="request">
                	<logic:equal name="posOrderForm" property="subaction" value="xyz" scope="request">
                	<html:text styleClass="Form" property="itemCode" size="37" onchange="this.form.subaction.value='ADDPOSORDERDETAIL';this.form.submit();"/>
                	</logic:equal>
                	<logic:notEqual name="posOrderForm" property="subaction" value="xyz" scope="request">
                	<html:text styleClass="Form" property="itemCode" size="37" onchange="this.form.subaction.value='ADDPOSORDERDETAIL';this.form.submit();"/>
                	</logic:notEqual>
                	</logic:notEmpty>
                </td>
                <td align="right" class="Cell1"><html:text styleClass="Form" property="itemQuantity" size="15" /></td>
                <td align="right" class="Cell1">&nbsp;</td>
                <td align="right" class="Cell1">
                	<logic:present name="totalAmount"><bean:write name="totalAmount"/></logic:present>
                </td>
              </tr>
              <tr>
                <td class="Cell1" colspan="3">&nbsp;</td>
                <td align="right" class="Cell1"><b>DISCOUNT (<logic:present name="posOrder"><bean:write name="posOrder" property="discountProcent"/></logic:present>%)</b></td>
                <td align="right" class="Cell1">
                	<logic:present name="discount"><bean:write name="discount"/></logic:present>
                </td>
              </tr>
              <tr>
                <td class="Cell1" colspan="3">&nbsp;</td>
                <td align="right" class="Cell1"><b>SUB TOTAL</b></td>   
                <td align="right" class="Cell1">
                	<logic:present name="totalAmountAfterDiscount"><bean:write name="totalAmountAfterDiscount"/></logic:present>
                </td>
              </tr>
              <tr>
                <td class="Cell1" colspan="3">&nbsp;</td>
                <td align="right" class="Cell1"><b>TAX (<logic:present name="posOrder"><bean:write name="posOrder" property="taxProcent"/></logic:present>%)</b></td>   
                <td align="right" class="Cell1">
                	<logic:present name="tax"><bean:write name="tax"/></logic:present>
                </td>
              </tr>
              <tr>
                <td class="Cell1" colspan="3">&nbsp;</td>
                <td align="right" class="Cell1"><b>T O T A L</b></td>   
                <td align="right" class="Cell1">
                	<logic:present name="totalAmountAfterDiscountAndTax"><bean:write name="totalAmountAfterDiscountAndTax"/></logic:present>
                </td>
              </tr>
            </TBODY>
            </table>
            </td>
            </tr>
            <tr><td colspan="4" class=Desc>Keterangan :</td></tr>
          </TBODY>
       	 </table>
       	 
       	 <table width="100%" border="0" cellspacing="1" cellpadding="1" bgcolor="#cdcdcd">
            <tr bgcolor="#ECE9D8">
              <td width="10%" align="center"><b>SHIFT + A</b></td>
              <td width="22%" class="FontGen">Payment</td>
	            <td width="10%" align="center"><b>SHIFT + G</b></td>
              <td width="22%" class="FontGen">Member</td>
              <td width="10%" align="center"><b>SHIFT + N</b></td>
              <td width="22%" class="FontGen">New Member</td>
            </tr>
            <tr bgcolor="#ECE9D8">
            	<td width="10%" align="center"><b>SHIFT + B</b></td>
              <td width="22%" class="FontGen">Cancel</td>
              <td width="10%" align="center"><b>SHIFT + E</b></td>
              <td width="22%" class="FontGen">Cancel 1 previous order</td>
              <td width="10%" align="center"><b>SHIFT + H</b></td>
	            <td width="22%" class="FontGen">Cancel 1 item</td>
            </tr>
            <tr bgcolor="#ECE9D8">
              <td width="10%" align="center"><b>SHIFT + F</b></td>
              <td width="22%" class="FontGen">Edit quantity</td>
              <td width="10%" align="center"><b>SHIFT + X</b></td>
              <td width="22%" class="FontGen">Log off</td>
              <td width="10%" align="center"><b>SHIFT + N</b></td>
              <td width="22%" class="FontGen">New Member</td>
            </tr>            
           
        </table>
        </td>
      </tr>
    </TBODY>
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

		if(evt.keyCode == 65)
			window.location="<html:rewrite page="/posOrder/paymentForm.do"/>"
		if(evt.keyCode == 66)
			window.location="<html:rewrite page="/posOrder/form.do?subaction=CANCEL"/>"
		if(evt.keyCode == 67)
			window.location="<html:rewrite page="/posOrder/form.do?subaction=NEXTORDER"/>"
		if(evt.keyCode == 68)
			window.location="<html:rewrite page="/posOrder/form.do?subaction=PREVIOUSORDER"/>"
		if(evt.keyCode == 69)
			window.location="<html:rewrite page="/posOrder/form.do?subaction=CANCELPREVIOUSORDER"/>"
		if(evt.keyCode == 70) {
			document.forms[0].quantity.focus();
		}
		if(evt.keyCode == 71) {	
			document.forms[0].memberNumber.focus();
		}
		
		if(evt.keyCode == 72)
			window.location="<html:rewrite page="/posOrder/form.do?subaction=CANCELITEM"/>"
		if(evt.keyCode == 105)
			window.location="<html:rewrite page="/posOrder/form.do?subaction=xyz"/>"
			
		if(evt.keyCode == 88)
			window.location="<html:rewrite page="/logoff.do"/>"
			
		if(evt.keyCode == 78) {
			window.location="<html:rewrite page="/posOrder/memberForm.do"/>";	
		}

	

}

<!--
function x() {
var a = document.forms[0].quantity.value;
a = a.substring(0, a.length);
//alert (document.forms[0].quantity.value);
document.forms[0].quantity.value = a;
}

function y() {
var b= document.forms[0].memberNumber.value;
b = b.substring(0, b.length);
//alert (document.forms[0].memberNumber.value);
document.forms[0].memberNumber.value = b;
}
-->

</script>
