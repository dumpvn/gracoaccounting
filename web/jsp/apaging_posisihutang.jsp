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
  
  <tr valign="top"><td valign="top">
    
<!-- content -->    
      <TABLE height=50 cellSpacing=1 cellPadding=1 border=0 width="100%" valign="top"><BR><BR>
        <TR>
          <TD align=middle width="80%">
            <TABLE cellSpacing=0 cellPadding=0 width="92%" border=0>
              <TR>
                <TD width=9 height=25><html:img page="/images/left_title.gif"/></TD>
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.posisiHutangTitle.title"/></TD></TR>
            </TABLE>
          </TD>
        </TR>
        <TR>
          <TD align=middle width="80%" height=10>&nbsp;</TD></TR>
        <TR>
          <TD align=middle width="80%">
			
			<!-- CONTENT START -->
	      <table width="97%" border="0" cellspacing="2" cellpadding="0">
	      	<tr><td><html:errors/></td></tr>
					<logic:messagesNotPresent>
    			<tr><td>&nbsp;</td></tr>
    			<html:form action="/apAging/posisiHutang">
					<html:hidden property="orderBy"/>
					<html:hidden property="ascDesc"/>
    			<tr>
    				<td>
    					<table width="100%">
    						<tr>
    							<td><bean:message key="page.month.title"/></td><td>:</td><td>
    								<html:select property="month">
											<html:option value="0">January</html:option>
											<html:option value="1">February</html:option>
											<html:option value="2">March</html:option>
											<html:option value="3">April</html:option>
											<html:option value="4">May</html:option>
											<html:option value="5">June</html:option>
											<html:option value="6">July</html:option>
											<html:option value="7">August</html:option>
											<html:option value="8">September</html:option>
											<html:option value="9">October</html:option>
											<html:option value="10">November</html:option>
											<html:option value="11">December</html:option>
                  	</html:select>&nbsp;<html:text property="year" size="4"/>
                  </td>
    						</tr>
    						
    						<tr>
    							<td><bean:message key="page.currency.title"/></td><td>:</td><td>
    								<html:select property="currencyId">
											<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
											<html:options collection="currencyLst" property="id" labelProperty="name" />
                  	</html:select>
    							&nbsp;<html:submit styleClass="Button"><bean:message key="page.search.link"/></html:submit></td>
    						</tr>
    					</table>
    				</td>
    			</tr>
          <tr bgcolor="#FFFFFF"> 
            <td align="center"> 
              <table border="0" cellspacing=1 cellpadding=2 bgcolor="#cccccc" width="100%">
                <tr align="center"> 
                  <td width="5%" class=titleField>&nbsp;</td>
                  <td class=titleField><a href=javascript:sort('BillDue')><bean:message key="page.billDue.title"/></a>&nbsp;<logic:match name="generalLedgerForm" property="orderBy" value="BillDue"><logic:match name="generalLedgerForm" property="ascDesc" value="desc"><html:img page="/images/sort_terang.gif" border="0"/></logic:match><logic:match name="generalLedgerForm" property="ascDesc" value="asc"><html:img page="/images/sort2_terang.gif" border="0"/></logic:match></logic:match></td>
                  <td class=titleField><a href=javascript:sort('Vendor.Id')><bean:message key="page.vendor.title"/></a>&nbsp;<logic:match name="generalLedgerForm" property="orderBy" value="Vendor.Id"><logic:match name="generalLedgerForm" property="ascDesc" value="desc"><html:img page="/images/sort_terang.gif" border="0"/></logic:match><logic:match name="generalLedgerForm" property="ascDesc" value="asc"><html:img page="/images/sort2_terang.gif" border="0"/></logic:match></logic:match></td>
                  <td class=titleField><a href=javascript:sort('Number')>VB</a>&nbsp;<logic:match name="generalLedgerForm" property="orderBy" value="Number"><logic:match name="generalLedgerForm" property="ascDesc" value="desc"><html:img page="/images/sort_terang.gif" border="0"/></logic:match><logic:match name="generalLedgerForm" property="ascDesc" value="asc"><html:img page="/images/sort2_terang.gif" border="0"/></logic:match></logic:match></td>
                  <td class=titleField><a href=javascript:sort('BillDate')><bean:message key="page.billDate.title"/></a>&nbsp;<logic:match name="generalLedgerForm" property="orderBy" value="BillDate"><logic:match name="generalLedgerForm" property="ascDesc" value="desc"><html:img page="/images/sort_terang.gif" border="0"/></logic:match><logic:match name="generalLedgerForm" property="ascDesc" value="asc"><html:img page="/images/sort2_terang.gif" border="0"/></logic:match></logic:match></td>
                  <td class=titleField><a href=javascript:sort('Receiving')><bean:message key="page.purchaseOrder.title"/></a>&nbsp;<logic:match name="generalLedgerForm" property="orderBy" value="Receiving"><logic:match name="generalLedgerForm" property="ascDesc" value="desc"><html:img page="/images/sort_terang.gif" border="0"/></logic:match><logic:match name="generalLedgerForm" property="ascDesc" value="asc"><html:img page="/images/sort2_terang.gif" border="0"/></logic:match></logic:match></td>
                  <td class=titleField><a href=javascript:sort('Voucher')><bean:message key="page.voucher.title"/></a>&nbsp;<logic:match name="generalLedgerForm" property="orderBy" value="Voucher"><logic:match name="generalLedgerForm" property="ascDesc" value="desc"><html:img page="/images/sort_terang.gif" border="0"/></logic:match><logic:match name="generalLedgerForm" property="ascDesc" value="asc"><html:img page="/images/sort2_terang.gif" border="0"/></logic:match></logic:match></td>
                  <td class=titleField><bean:message key="page.description.title"/></td>
                  <td class=titleField><bean:message key="page.amount.title"/></td>
                </tr>
                
<%
int i = 0;
try {
i = Integer.parseInt(request.getParameter("start"));
}catch(Exception ex){
i = 0;
}
%>
                <logic:iterate id="vendorBill" name="vendorBillLst" type="com.mpe.financial.model.VendorBill">
								<% 
									sequence++;
									String bgcolor = "FFFFFF";
									if(sequence%2 == 0){
										bgcolor = "F5F9EE";
									}
								 %>
								<tr bgcolor="<% out.print(bgcolor); %>">
                	<td align="right"><%=++i%>.</td>
                	<td><bean:write name="vendorBill" property="formatedBillDue" scope="page"/></td>
									<td>
										<logic:present name="vendorBill" property="vendor">
										<bean:write name="vendorBill" property="vendor.company" scope="page"/>
										</logic:present>
									</td>
									<td><bean:write name="vendorBill" property="number" scope="page"/></td>
									<td><bean:write name="vendorBill" property="formatedBillDate" scope="page"/></td>
									<td>
										<logic:present name="vendorBill" property="receiving">
										<logic:present name="vendorBill" property="receiving.purchaseOrder">
										<bean:write name="vendorBill" property="receiving.purchaseOrder.number" scope="page"/>
										</logic:present>
										</logic:present>
									</td>
									<td><bean:write name="vendorBill" property="voucher" scope="page"/></td>
									<td><bean:write name="vendorBill" property="description" scope="page"/></td>
									<td align="right"><bean:write name="vendorBill" property="formatedDifferenceAmount" scope="page"/></td>
                </tr>
                </logic:iterate>
              </table>
            </td>
          </tr>
          
          <tr>
    				<td>
    					<table width="100%">
    						<tr><td>&nbsp;</td></tr>
    					</table>
    				</td>
    			</tr>
    			<tr>
    				<td align="center">
    					<table width="10%">
    						<tr>
    							<td align="center">
    								<input class="Button" type="button" name="create" onclick="window.open('<html:rewrite page="/apAging/posisiHutangPdf.do"/>?currencyId=<bean:write name="generalLedgerForm" property="currencyId"/>&month=<bean:write name="generalLedgerForm" property="month"/>&year=<bean:write name="generalLedgerForm" property="year"/>&orderBy=<bean:write name="generalLedgerForm" property="orderBy"/>&ascDesc=<bean:write name="generalLedgerForm" property="ascDesc"/>','gg','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,copyhistory=yes,width=800,height=600');" value="PDF">&nbsp;
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



