<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>

<html:html>
<HEAD><TITLE><bean:message key="page.index.title"/></TITLE>
<LINK href="<html:rewrite page="/js/styleHeader.css"/>" type=text/css rel=stylesheet>
<html:base/>
</HEAD>
<BODY leftMargin=0 topMargin=0 rightMargin=0 marginwidth="0" marginheight="0">

<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0 valign="top">
  <!-- header start -->
  <tiles:insert template='/common/header.jsp'/>
  <!-- header end -->
  
  <tr valign="top"><td>
    
<!-- content -->    
      <TABLE height=50 cellSpacing=1 cellPadding=1 border=0 width="100%" valign="top"><BR><BR>
        <TR>
          <TD align=middle width="80%">
            <TABLE cellSpacing=0 cellPadding=0 width="92%" border=0>
              <TR>
                <TD width=9 height=25><html:img page="/images/left_title.gif"/></TD>
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.runningNumberTitle.title"/></TD></TR>
            </TABLE>
          </TD>
        </TR>
        <TR>
          <TD align=middle width="80%" height=10>&nbsp;</TD></TR>
        <TR>
          <TD align=middle width="80%">
			
			<!-- CONTENT START -->
	      <table width="97%" border="0" cellspacing="2" cellpadding="0">
	      	<tr valign="top"><td><html:errors/></td></tr>
    			<tr valign="top"><td>&nbsp;</td></tr>
    			<html:form action="/runningNumber/save">
					<html:hidden property="organizationId"/>
          <tr> 
            <td align="center"> 
              <table width="100%" border="0" cellspacing="1" cellpadding="0" class=abuabu>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.journalPrefix.title"/>/<bean:message key="page.numberOfDigit.title"/>/<bean:message key="page.suffix.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<html:text property="journalPrefix" size="5"/>/<html:text property="journalNumber" size="10"/>/<html:text property="journalSuffix" size="5"/>&nbsp;
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.bankTransactionPrefix.title"/>/<bean:message key="page.numberOfDigit.title"/>/<bean:message key="page.suffix.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<html:text property="bankTransactionPrefix" size="5"/>/<html:text property="bankTransactionNumber" size="10"/>/<html:text property="bankTransactionSuffix" size="5"/>&nbsp;
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.itemPrefix.title"/>/<bean:message key="page.numberOfDigit.title"/>/<bean:message key="page.suffix.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<html:text property="itemPrefix" size="5"/>/<html:text property="itemNumber" size="10"/>/<html:text property="itemSuffix" size="5"/>&nbsp;&nbsp;
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.warehousePrefix.title"/>/<bean:message key="page.numberOfDigit.title"/>/<bean:message key="page.suffix.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<html:text property="warehousePrefix" size="5"/>/<html:text property="warehouseNumber" size="10"/>/<html:text property="warehouseSuffix" size="5"/>&nbsp;
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.mutationPrefix.title"/>/<bean:message key="page.numberOfDigit.title"/>/<bean:message key="page.suffix.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<html:text property="mutationPrefix" size="5"/>/<html:text property="mutationNumber" size="10"/>/<html:text property="mutationSuffix" size="5"/>&nbsp;
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.stockOpnamePrefix.title"/>/<bean:message key="page.numberOfDigit.title"/>/<bean:message key="page.suffix.title"/></td>
                  <td width="5%" align="center">: </td>
                  <td width="65%">
                  	<html:text property="stockOpnamePrefix" size="5"/>/<html:text property="stockOpnameNumber" size="10"/>/<html:text property="stockOpnameSuffix" size="5"/>&nbsp;
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.itemUsagePrefix.title"/>/<bean:message key="page.numberOfDigit.title"/>/<bean:message key="page.suffix.title"/></td>
                  <td width="5%" align="center">: </td>
                  <td width="65%">
                  	<html:text property="itemUsagePrefix" size="5"/>/<html:text property="itemUsageNumber" size="10"/>/<html:text property="itemUsageSuffix" size="5"/>&nbsp;
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.lendingPrefix.title"/>/<bean:message key="page.numberOfDigit.title"/>/<bean:message key="page.suffix.title"/></td>
                  <td width="5%" align="center">: </td>
                  <td width="65%">
                  	<html:text property="lendingPrefix" size="5"/>/<html:text property="lendingNumber" size="10"/>/<html:text property="lendingSuffix" size="5"/>&nbsp;
                  </td>
                </tr>
                <tr>
                  <td width="30%" align="right"><bean:message key="page.purchaseRequestPrefix.title"/>/<bean:message key="page.numberOfDigit.title"/>/<bean:message key="page.suffix.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<html:text property="purchaseRequestPrefix" size="5"/>/<html:text property="purchaseRequestNumber" size="10"/>/<html:text property="purchaseRequestSuffix" size="5"/>&nbsp;
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.purchaseOrderPrefix.title"/>/<bean:message key="page.numberOfDigit.title"/>/<bean:message key="page.suffix.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<html:text property="purchaseOrderPrefix" size="5"/>/<html:text property="purchaseOrderNumber" size="10"/>/<html:text property="purchaseOrderSuffix" size="5"/>&nbsp;
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.receivingPrefix.title"/>/<bean:message key="page.numberOfDigit.title"/>/<bean:message key="page.suffix.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<html:text property="receivingPrefix" size="5"/>/<html:text property="receivingNumber" size="10"/>/<html:text property="receivingSuffix" size="5"/>&nbsp;
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.vendorBillPrefix.title"/>/<bean:message key="page.numberOfDigit.title"/>/<bean:message key="page.suffix.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<html:text property="vendorBillPrefix" size="5"/>/<html:text property="vendorBillNumber" size="10"/>/<html:text property="vendorBillSuffix" size="5"/>&nbsp;
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.paymentToVendorPrefix.title"/>/<bean:message key="page.numberOfDigit.title"/>/<bean:message key="page.suffix.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<html:text property="paymentToVendorPrefix" size="5"/>/<html:text property="paymentToVendorNumber" size="10"/>/<html:text property="paymentToVendorSuffix" size="5"/>&nbsp;
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.prepaymentToVendorPrefix.title"/>/<bean:message key="page.numberOfDigit.title"/>/<bean:message key="page.suffix.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<html:text property="prepaymentToVendorPrefix" size="5"/>/<html:text property="prepaymentToVendorNumber" size="10"/>/<html:text property="prepaymentToVendorSuffix" size="5"/>&nbsp;
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.returnToVendorPrefix.title"/>/<bean:message key="page.numberOfDigit.title"/>/<bean:message key="page.suffix.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<html:text property="returnToVendorPrefix" size="5"/>/<html:text property="returnToVendorNumber" size="10"/>/<html:text property="returnToVendorSuffix" size="5"/>&nbsp;
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.salesOrderPrefix.title"/>/<bean:message key="page.numberOfDigit.title"/>/<bean:message key="page.suffix.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<html:text property="salesOrderPrefix" size="5"/>/<html:text property="salesOrderNumber" size="10"/>/<html:text property="salesOrderSuffix" size="5"/>&nbsp;
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.deliveryOrderPrefix.title"/>/<bean:message key="page.numberOfDigit.title"/>/<bean:message key="page.suffix.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<html:text property="deliveryOrderPrefix" size="5"/>/<html:text property="deliveryOrderNumber" size="10"/>/<html:text property="deliveryOrderSuffix" size="5"/>&nbsp;
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.invoicePrefix.title"/>/<bean:message key="page.numberOfDigit.title"/>/<bean:message key="page.suffix.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<html:text property="invoicePrefix" size="5"/>/<html:text property="invoiceNumber" size="10"/>/<html:text property="invoiceSuffix" size="5"/>&nbsp;
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.customerPaymentPrefix.title"/>/<bean:message key="page.numberOfDigit.title"/>/<bean:message key="page.suffix.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<html:text property="customerPaymentPrefix" size="5"/>/<html:text property="customerPaymentNumber" size="10"/>/<html:text property="customerPaymentSuffix" size="5"/>&nbsp;
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.customerPrepaymentPrefix.title"/>/<bean:message key="page.numberOfDigit.title"/>/<bean:message key="page.suffix.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<html:text property="customerPrepaymentPrefix" size="5"/>/<html:text property="customerPrepaymentNumber" size="10"/>/<html:text property="customerPrepaymentSuffix" size="5"/>&nbsp;
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.customerReturnPrefix.title"/>/<bean:message key="page.numberOfDigit.title"/>/<bean:message key="page.suffix.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<html:text property="customerReturnPrefix" size="5"/>/<html:text property="customerReturnNumber" size="10"/>/<html:text property="customerReturnSuffix" size="5"/>&nbsp;
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.standartNpwpTaxNumber.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><html:text property="standartNpwpTaxNumber" size="7"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.simpleNpwpTaxNumber.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><html:text property="simpleNpwpTaxNumber" size="5"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.posOrderNumber.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><html:text property="posOrderNumber" size="10"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.memberNumber.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><html:text property="memberNumber" size="10"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
    				<td>
    					<table width="100%">
    						<tr valign="top"><td>&nbsp;</td></tr>
    					</table>
    				</td>
    			</tr>
    			<tr>
    				<td align="center">
							<html:submit styleClass="Button"><bean:message key="page.submit.link"/></html:submit>
							<html:cancel styleClass="Button"><bean:message key="page.cancel.link"/></html:cancel>
    				</td>
    			</tr>
          </html:form>
        </table> 
			<!-- CONTENT END -->
			
          </TD>
        </TR>
      </TABLE>
            
<!-- end content-->            
      </TD>
    </TR>
  <!-- footer start -->
  <tiles:insert template='/common/footer.jsp'/>
  <!-- footer end -->
 </TABLE>
</BODY>
</html:html>


<script language="javascript">

function selectAll(col1){
	col1 = eval('document.forms[0].'+col1);
  for(i=0; i<col1.options.length; i++ ){
		col1.options[i].selected = true;
	}
}

function up(col1){
	col1 = eval('document.forms[0].'+col1);
  index = col1.selectedIndex;
  //alert(index);
  if (index == -1) {
  	alert ('You haven\'t selected any options!');
  } else {
	  if(index <= 0) {
	  	alert("Can't move to up!");
	  } else {
		  toMoveX = col1.options[index-1];
		  toMoveY = col1.options[index];
		  optX = new Option(toMoveX.text,toMoveX.value,false,false);
		  optY = new Option(toMoveY.text,toMoveY.value,false,false);
		  col1.options[index] = optX;
		  col1.options[index-1] = optY;
		  col1.selectedIndex = index-1;
		}
	}
}

function down(col1){
	col1 = eval('document.forms[0].'+col1);
  index = col1.selectedIndex;
  if (index == -1) {
  	alert ('You haven\'t selected any options!');
  } else {
	  if(index+1 >=  col1.options.length) {
	  	alert("Can't move to down!");
	 	} else {
		  toMoveX = col1.options[index];
		  toMoveY = col1.options[index+1];
		  optX = new Option(toMoveX.text,toMoveX.value,false,false);
		  optY = new Option(toMoveY.text,toMoveY.value,false,false);
		  col1.options[index] = optY;
		  col1.options[index+1] = optX;
		  col1.selectedIndex = index+1;
		}
	}
}

function copyToList(from,to) {
  fromList = eval('document.forms[0].' + from);
  toList = eval('document.forms[0].' + to);
  if (toList.options.length > 0 && toList.options[0].value == 'temp') {
    toList.options.length = 0;
  }
  var sel = false;
  for (i=0;i<fromList.options.length;i++) {
    var current = fromList.options[i];
    if (current.selected) {
      sel = true;
      if (current.value == 'temp') {
        alert ('You cannot move this text!');
        return;
      }
      txt = current.text;
      val = current.value;
      toList.options[toList.length] = new Option(txt,val);
      fromList.options[i] = null;
      i--;
    }
  }
  if (!sel) alert ('You haven\'t selected any options!');
}

function page_submit() {
	document.forms[0].submit();
};
</script>