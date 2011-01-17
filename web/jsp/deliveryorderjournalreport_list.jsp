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
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.deliveryOrderTitle.title"/>&nbsp;JOURNAL</TD></TR>
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
    			<html:form action="/deliveryOrderReport/journalList">
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
    							<td><bean:message key="page.date.title"/></td><td>:</td><td><html:text property="fromDeliveryDate" size="12"/>&nbsp;-&nbsp;<html:text property="toDeliveryDate" size="12"/>&nbsp;(dd/MM/yyyy)&nbsp;<html:submit styleClass="Button"><bean:message key="page.search.link"/></html:submit></td>
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
                  <td width="5%" class=titleField>&nbsp;</td>
                  <td class=titleField><a href=javascript:sort('DeliveryDate')><bean:message key="page.deliveryDate.title"/></a>&nbsp;<logic:match name="deliveryOrderForm" property="orderBy" value="DeliveryDate"><logic:match name="deliveryOrderForm" property="ascDesc" value="desc"><html:img page="/images/sort_terang.gif" border="0"/></logic:match><logic:match name="deliveryOrderForm" property="ascDesc" value="asc"><html:img page="/images/sort2_terang.gif" border="0"/></logic:match></logic:match></td>
                  <td class=titleField><a href=javascript:sort('Number')><bean:message key="page.number.title"/></a>&nbsp;<logic:match name="deliveryOrderForm" property="orderBy" value="Number"><logic:match name="deliveryOrderForm" property="ascDesc" value="desc"><html:img page="/images/sort_terang.gif" border="0"/></logic:match><logic:match name="deliveryOrderForm" property="ascDesc" value="asc"><html:img page="/images/sort2_terang.gif" border="0"/></logic:match></logic:match></td>
                  <td class=titleField><a href=javascript:sort('Description')><bean:message key="page.description.title"/></a>&nbsp;<logic:match name="deliveryOrderForm" property="orderBy" value="Description"><logic:match name="deliveryOrderForm" property="ascDesc" value="desc"><html:img page="/images/sort_terang.gif" border="0"/></logic:match><logic:match name="deliveryOrderForm" property="ascDesc" value="asc"><html:img page="/images/sort2_terang.gif" border="0"/></logic:match></logic:match></td>
                  <td class=titleField><bean:message key="page.item.title"/></td>
                  <td class=titleField><bean:message key="page.outstandingQuantity.title"/></td>
                  <td class=titleField><bean:message key="page.quantity.title"/></td>
                  <td class=titleField><bean:message key="page.itemUnit.title"/></td>
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
                <logic:iterate id="deliveryOrder" name="DELIVERYORDER" type="com.mpe.financial.model.DeliveryOrder">
								<% 
									sequence++;
									String bgcolor = "FFFFFF";
									if(sequence%2 == 0){
										bgcolor = "F5F9EE";
									}
								 %>
								<tr bgcolor="<% out.print(bgcolor); %>">
                	<td align="right"><%=++i%>.</td>
									<td><bean:write name="deliveryOrder" property="deliveryDate" scope="page"/></td>
									<td><bean:write name="deliveryOrder" property="number" scope="page"/></td>
									<td><bean:write name="deliveryOrder" property="description" scope="page"/></td>
									<td>
										<logic:iterate id="deliveryOrderDetail" name="deliveryOrder" property="deliveryOrderDetails" type="com.mpe.financial.model.DeliveryOrderDetail">
										<logic:present name="deliveryOrderDetail" property="id.item">
										<bean:write name="deliveryOrderDetail" property="id.item.code" scope="page"/><br>
										</logic:present>
										</logic:iterate>
									</td>
									<td>
										<logic:iterate id="deliveryOrderDetail" name="deliveryOrder" property="deliveryOrderDetails" type="com.mpe.financial.model.DeliveryOrderDetail">
										<bean:write name="deliveryOrderDetail" property="outstandingQuantity" scope="page"/><br>
										</logic:iterate>
									</td>
									<td>
										<logic:iterate id="deliveryOrderDetail" name="deliveryOrder" property="deliveryOrderDetails" type="com.mpe.financial.model.DeliveryOrderDetail">
										<bean:write name="deliveryOrderDetail" property="quantity" scope="page"/><br>
										</logic:iterate>
									</td>
									<td>
										<logic:iterate id="deliveryOrderDetail" name="deliveryOrder" property="deliveryOrderDetails" type="com.mpe.financial.model.DeliveryOrderDetail">
										<logic:present name="deliveryOrderDetail" property="itemUnit">
										<bean:write name="deliveryOrderDetail" property="itemUnit.symbol" scope="page"/><br>
										</logic:present>
										</logic:iterate>
									</td>
									<td>
										<logic:present name="deliveryOrder" property="salesOrder">
										<logic:present name="deliveryOrder" property="salesOrder.currency">
										<bean:write name="deliveryOrder" property="salesOrder.currency.symbol"/>&nbsp;
										</logic:present>
										</logic:present>
										<bean:write name="deliveryOrderDetail" property="formatedPriceQuantity" scope="page"/><br>
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
    								<input class="Button" type="button" name="create" onclick="window.open('<html:rewrite page="/deliveryOrderReport/journalPdfList.do"/>?number=<bean:write name="deliveryOrderForm" property="number"/>&fromDeliveryDate=<bean:write name="deliveryOrderForm" property="fromDeliveryDate"/>&toDeliveryDate=<bean:write name="deliveryOrderForm" property="toDeliveryDate"/>&orderBy=<bean:write name="deliveryOrderForm" property="orderBy"/>&ascDesc=<bean:write name="deliveryOrderForm" property="ascDesc"/>','gg','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,copyhistory=yes,width=800,height=600');" value="<bean:message key="page.print.link"/>">
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



