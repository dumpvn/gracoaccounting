<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%
	int sequence = 0;
%>
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
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25>PAYMENT</TD></TR>
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
					<logic:messagesNotPresent>
    			<tr valign="top"><td>&nbsp;</td></tr>
    			<html:form action="/customerPaymentReport/list">
					<html:hidden property="start"/>
					<html:hidden property="count"/>
					<html:hidden property="orderBy"/>
					<html:hidden property="ascDesc"/>
    			<tr>
    				<td>
    					<table width="100%">
    						<tr>
    							<td><bean:message key="page.number.title"/></td><td>:</td><td><html:text property="number" size="30"/></td>
    						</tr>
    						<tr>
    							<td><bean:message key="page.date.title"/></td><td>:</td><td><html:text property="fromPaymentDate" size="12"/>&nbsp;-&nbsp;<html:text property="toPaymentDate" size="12"/>&nbsp;(dd/MM/yyyy)<html:submit styleClass="Button"><bean:message key="page.search.link"/></html:submit></td>
    						</tr>
    					</table>
    				</td>
    			</tr>
    			<tr>
    				<td>
    					<table width="100%">
    						<tr>
    							<td align="left"><font face="Verdana,Arial,Helvetica,sans-serif" size="1"><bean:write name="PAGER" scope="request" filter="false"/></font></td>
    							<td align="center"><font face="Verdana,Arial,Helvetica,sans-serif" size="1">Goto page : <html:text property="gotoPage" size="4" styleClass="Data" onchange="gotoPager();"/>&nbsp;</font></td>
    							<td align="right"><font face="Verdana,Arial,Helvetica,sans-serif" size="1"><bean:write name="PAGERITEM" scope="request" filter="false"/></font></td>
    						</tr>
    					</table>
    				</td>
    			</tr>
          <tr bgcolor="#FFFFFF"> 
            <td align="center"> 
              <table border="0" cellspacing=1 cellpadding=2 bgcolor="#cccccc" width="100%">
                <tr align="center"> 
                  <td width="5%" class=titleField rowspan="2">&nbsp;</td>
                  <td class=titleField rowspan="2"><a href=javascript:sort('Customer.Id')><bean:message key="page.customer.title"/></a>&nbsp;<logic:match name="customerPaymentForm" property="orderBy" value="Customer.Id"><logic:match name="customerPaymentForm" property="ascDesc" value="desc"><html:img page="/images/sort_terang.gif" border="0"/></logic:match><logic:match name="customerPaymentForm" property="ascDesc" value="asc"><html:img page="/images/sort2_terang.gif" border="0"/></logic:match></logic:match></td>
                  <td class=titleField rowspan="2"><a href=javascript:sort('PaymentDate')><bean:message key="page.paymentDate.title"/></a>&nbsp;<logic:match name="customerPaymentForm" property="orderBy" value="PaymentDate"><logic:match name="customerPaymentForm" property="ascDesc" value="desc"><html:img page="/images/sort_terang.gif" border="0"/></logic:match><logic:match name="customerPaymentForm" property="ascDesc" value="asc"><html:img page="/images/sort2_terang.gif" border="0"/></logic:match></logic:match></td>
                  <td class=titleField rowspan="2"><a href=javascript:sort('Number')><bean:message key="page.number.title"/></a>&nbsp;<logic:match name="customerPaymentForm" property="orderBy" value="Number"><logic:match name="customerPaymentForm" property="ascDesc" value="desc"><html:img page="/images/sort_terang.gif" border="0"/></logic:match><logic:match name="customerPaymentForm" property="ascDesc" value="asc"><html:img page="/images/sort2_terang.gif" border="0"/></logic:match></logic:match></td>
                  <td class=titleField colspan="4"><bean:message key="page.invoice.title"/></td>
                  <td class=titleField rowspan="2"><bean:message key="page.cash.title"/></td>
                  <td class=titleField rowspan="2"><bean:message key="page.giro.title"/></td>
                  <td class=titleField rowspan="2"><bean:message key="page.bank.title"/></td>
                  <td class=titleField rowspan="2"><bean:message key="page.bankAccount.title"/></td>
                </tr>
                <tr>
                	<td class=titleField><bean:message key="page.invoiceDate.title"/></td>
                	<td class=titleField><bean:message key="page.discount.title"/></td>
                  <td class=titleField><bean:message key="page.tax.title"/></td>
                  <td class=titleField><bean:message key="page.total.title"/></td>
                </tr>
<%
int i = 0;
try {
i = Integer.parseInt(request.getParameter("start"));
}catch(Exception ex){
i = 0;
}
%>
                <logic:iterate id="customerPayment" name="CUSTOMERPAYMENT" type="com.mpe.financial.model.CustomerPayment">
								<% 
									sequence++;
									String bgcolor = "FFFFFF";
									if(sequence%2 == 0){
										bgcolor = "F5F9EE";
									}
								 %>
								<tr bgcolor="<% out.print(bgcolor); %>">
                	<td align="right"><%=++i%>.</td>
                	<td>
										<logic:present name="customerPayment" property="customer">
										<bean:write name="customerPayment" property="customer.company" scope="page"/>
										</logic:present>
									</td>
									<td><bean:write name="customerPayment" property="paymentDate" scope="page"/></td>
									<td><bean:write name="customerPayment" property="number" scope="page"/></td>
									
									<td>
										<logic:iterate id="customerPaymentDetail" name="customerPayment" property="customerPaymentDetails" type="com.mpe.financial.model.CustomerPaymentDetail">
										<logic:present name="customerPaymentDetail" property="invoice">
										<bean:write name="customerPaymentDetail" property="invoice.invoiceDate" scope="page"/>
										</logic:present>
										<logic:present name="customerPaymentDetail" property="invoiceSimple">
										<bean:write name="customerPaymentDetail" property="invoiceSimple.invoiceDate" scope="page"/>
										</logic:present>
										<logic:present name="customerPaymentDetail" property="invoicePolos">
										<bean:write name="customerPaymentDetail" property="invoicePolos.invoiceDate" scope="page"/>
										</logic:present>
										</logic:iterate>
									</td>
									<td>
										<logic:iterate id="customerPaymentDetail" name="customerPayment" property="customerPaymentDetails" type="com.mpe.financial.model.CustomerPaymentDetail">
										<logic:present name="customerPaymentDetail" property="invoice">
										<bean:write name="customerPaymentDetail" property="invoice.discountProcent" scope="page"/>%
										</logic:present>
										<logic:present name="customerPaymentDetail" property="invoiceSimple">
										<bean:write name="customerPaymentDetail" property="invoiceSimple.discountProcent" scope="page"/>%
										</logic:present>
										<logic:present name="customerPaymentDetail" property="invoicePolos">
										<bean:write name="customerPaymentDetail" property="invoicePolos.discountProcent" scope="page"/>%
										</logic:present>
										</logic:iterate>
									</td>
									<td>
										<logic:iterate id="customerPaymentDetail" name="customerPayment" property="customerPaymentDetails" type="com.mpe.financial.model.CustomerPaymentDetail">
										<logic:present name="customerPaymentDetail" property="invoice">
										<bean:write name="customerPaymentDetail" property="invoice.taxAmount" scope="page"/>%
										</logic:present>
										<logic:present name="customerPaymentDetail" property="invoiceSimple">
										<bean:write name="customerPaymentDetail" property="invoiceSimple.taxAmount" scope="page"/>%
										</logic:present>
										<logic:present name="customerPaymentDetail" property="invoicePolos">
										<bean:write name="customerPaymentDetail" property="invoicePolos.taxAmount" scope="page"/>%
										</logic:present>
										</logic:iterate>
									</td>
									<td>
										<logic:iterate id="customerPaymentDetail" name="customerPayment" property="customerPaymentDetails" type="com.mpe.financial.model.CustomerPaymentDetail">
										<logic:present name="customerPaymentDetail" property="invoice">
										<bean:write name="customerPaymentDetail" property="invoice.formatedInvoiceAfterDiscountAndTaxAndPrepayment" scope="page"/>
										</logic:present>
										<logic:present name="customerPaymentDetail" property="invoiceSimple">
										<bean:write name="customerPaymentDetail" property="invoiceSimple.formatedInvoiceSimpleAfterDiscountAndTaxAndPrepayment" scope="page"/>
										</logic:present>
										<logic:present name="customerPaymentDetail" property="invoicePolos">
										<bean:write name="customerPaymentDetail" property="invoicePolos.formatedInvoicePolosAfterDiscountAndTaxAndPrepayment" scope="page"/>
										</logic:present>
										</logic:iterate>
									</td>
									
									<td>
										<logic:present name="customerPayment" property="method">
										<logic:match name="customerPayment" property="method" value="Cash">
										<logic:present name="customerPayment" property="currency">
										<bean:write name="customerPayment" property="currency.symbol" scope="page"/>&nbsp;
										</logic:present>
										<bean:write name="customerPayment" property="formatedPaymentAmount" scope="page"/>
										</logic:match>
										</logic:present>
									</td>
									<td>
										<logic:present name="customerPayment" property="method">
										<logic:notMatch name="customerPayment" property="method" value="Cash">
										<logic:present name="customerPayment" property="currency">
										<bean:write name="customerPayment" property="currency.symbol" scope="page"/>&nbsp;
										</logic:present>
										<bean:write name="customerPayment" property="formatedPaymentAmount" scope="page"/>
										</logic:notMatch>
										</logic:present>
									</td>
									<td>
										<logic:present name="customerPayment" property="bankAccount">
										<logic:present name="customerPayment" property="bankAccount.bank">
										<bean:write name="customerPayment" property="bankAccount.bank.name" scope="page"/>
										</logic:present>
										</logic:present>
									</td>
									<td>
										<logic:present name="customerPayment" property="bankAccount">
										<bean:write name="customerPayment" property="bankAccount.number" scope="page"/>
										</logic:present>
									</td>
                </tr>
                </logic:iterate>
              </table>
            </td>
          </tr>
          <tr>
    				<td>
    					<table width="100%">
    						<tr>
    							<td align="left"><font face="Verdana,Arial,Helvetica,sans-serif" size="1"><bean:write name="PAGER" scope="request" filter="false"/></font></td>
    							<td align="center"><font face="Verdana,Arial,Helvetica,sans-serif" size="1">Goto page : <html:text property="gotoPage2" size="4" styleClass="Data" onchange="gotoPager2();"/>&nbsp;</font></td>
    							<td align="right"><font face="Verdana,Arial,Helvetica,sans-serif" size="1"><bean:write name="PAGERITEM" scope="request" filter="false"/></font></td>
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
    					<table width="10%">
    						<tr>
    							<td align="center">
    								<input class="Button" type="button" name="create" onclick="window.open('<html:rewrite page="/customerPaymentReport/pdfList.do"/>?number=<bean:write name="customerPaymentForm" property="number"/>&fromPaymentDate=<bean:write name="customerPaymentForm" property="fromPaymentDate"/>&toPaymentDate=<bean:write name="customerPaymentForm" property="toPaymentDate"/>&orderBy=<bean:write name="customerPaymentForm" property="orderBy"/>&ascDesc=<bean:write name="customerPaymentForm" property="ascDesc"/>','gg','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,copyhistory=yes,width=800,height=600');" value="<bean:message key="page.print.link"/>">
    							</td>
    						</tr>
    					</table>
    				</td>
    			</tr>
          </html:form>
          </logic:messagesNotPresent>
        </table> 
			<!-- CONTENT END -->
			
          </TD>
        </TR>
      </TABLE>
                    
      </TD>
    </TR>
  <!-- footer start -->
  <tiles:insert template='/common/footer.jsp'/>
  <!-- footer end -->
 </TABLE>
</BODY>
</html:html>

<script language="javascript">
function page(start,count) {
	document.forms[0].gotoPage.value = '';
	document.forms[0].start.value = start;
	document.forms[0].count.value = count;
	document.forms[0].submit();
};

function gotoPager() {
	if (document.forms[0].gotoPage.value >= 1) {
		if (document.forms[0].count.value == '' || document.forms[0].count.value == 0) document.forms[0].count.value = 10; 
		var a = (document.forms[0].gotoPage.value - 1) * document.forms[0].count.value;
		document.forms[0].start.value = a;
		document.forms[0].gotoPage2.value = document.forms[0].gotoPage.value;
		document.forms[0].submit();
	}
};

function gotoPager2() {
	if (document.forms[0].gotoPage2.value >= 1) {
		if (document.forms[0].count.value == '' || document.forms[0].count.value == 0) document.forms[0].count.value = 10; 
		var a = (document.forms[0].gotoPage2.value - 1) * document.forms[0].count.value;
		document.forms[0].start.value = a;
		document.forms[0].gotoPage.value = document.forms[0].gotoPage2.value;
		document.forms[0].submit();
	}
};

function sort(orderBy) {
	document.forms[0].orderBy.value = orderBy;
	if (document.forms[0].ascDesc.value=='') {
		document.forms[0].ascDesc.value='desc';
	} else if (document.forms[0].ascDesc.value=='desc') {
		document.forms[0].ascDesc.value='asc';
	} else {
		document.forms[0].ascDesc.value='desc';
	}
	document.forms[0].submit();
};

</script>



