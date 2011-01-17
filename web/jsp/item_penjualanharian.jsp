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
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25>PENJUALAN HARIAN</TD></TR>
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
    			<html:form action="/itemReport/penjualanHarian">
					<html:hidden property="start"/>
					<html:hidden property="count"/>
					<html:hidden property="orderBy"/>
					<html:hidden property="ascDesc"/>
					<html:hidden property="caller"/>
					<html:hidden property="subaction"/>
    			<tr>
    				<td>
    					<table width="100%">
    						<tr> 
                  <td><bean:message key="page.location.title"/></td><td>:</td>
                  <td>
                  	<html:select property="locationId">
											<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
											<html:options collection="locationLst" property="id" labelProperty="name" />
                  	</html:select>
                  </td>
                </tr>
    						<tr>
    							<td><bean:message key="page.date.title"/></td><td>:</td><td>
    								<html:text property="fromDate" size="12"/>&nbsp;<html:submit styleClass="button"><bean:message key="page.search.link"/></html:submit></td>
    						</tr>
    					</table>
    				</td>
    			</tr>
    			<tr>
    				<td>&nbsp;</td>
    			</tr>
          <tr bgcolor="#FFFFFF"> 
            <td align="center"> 
              <table border="0" cellspacing=1 cellpadding=2 bgcolor="#cccccc" width="100%">
                <tr align="center"> 
                  <td width="5%" class=titleField rowspan="2">&nbsp;</td>
                  <td class=titleField rowspan="2"><bean:message key="page.number.title"/></td>
                  <td class=titleField rowspan="2"><bean:message key="page.merk.title"/></td>
                  <td class=titleField rowspan="2"><bean:message key="page.type.title"/></td>
                  <td class=titleField rowspan="2"><bean:message key="page.color.title"/></td>
                  <td class=titleField rowspan="2"><bean:message key="page.code.title"/></td>
                  <td class=titleField rowspan="2"><bean:message key="page.price.title"/></td>
                  <td class=titleField colspan="2"><bean:message key="page.discount.title"/></td>
                  <td class=titleField colspan="2"><bean:message key="page.payment.title"/></td>
                </tr>
                <tr>
                	<td class=titleField><bean:message key="page.discount.title"/>(%)</td>
                	<td class=titleField><bean:message key="page.discount.title"/></td>
                	<td class=titleField><bean:message key="page.cash.title"/></td>
                	<td class=titleField><bean:message key="page.creditCard.title"/></td>
                </tr>
<%
int i = 0;
try {
i = Integer.parseInt(request.getParameter("start"));
}catch(Exception ex){
i = 0;
}
%>
                <logic:iterate id="penjualanHarian" name="penjualanHarianLst" type="com.mpe.financial.model.PosOrder">
								<% 
									sequence++;
									String bgcolor = "FFFFFF";
									if(sequence%2 == 0){
										bgcolor = "F5F9EE";
									}
								 %>
								<tr bgcolor="<% out.print(bgcolor); %>">
									<td align="right"><%=++i%>.</td>
									<td><bean:write name="penjualanHarian" property="posOrderNumber" scope="page"/></td>
									<td>
										<logic:iterate id="posOrderDetail" name="penjualanHarian" property="posOrderDetails" type="com.mpe.financial.model.PosOrderDetail">
										<bean:write name="posOrderDetail" property="id.item.itemMerk.code" scope="page"/><br>
										</logic:iterate>
									</td>
									<td>
										<logic:iterate id="posOrderDetail" name="penjualanHarian" property="posOrderDetails" type="com.mpe.financial.model.PosOrderDetail">
										<bean:write name="posOrderDetail" property="id.item.itemType.code" scope="page"/><br>
										</logic:iterate>
									</td>
									<td>
										<logic:iterate id="posOrderDetail" name="penjualanHarian" property="posOrderDetails" type="com.mpe.financial.model.PosOrderDetail">
										<bean:write name="posOrderDetail" property="id.item.itemColor.code" scope="page"/><br>
										</logic:iterate>
									</td>
									<td>
										<logic:iterate id="posOrderDetail" name="penjualanHarian" property="posOrderDetails" type="com.mpe.financial.model.PosOrderDetail">
										<bean:write name="posOrderDetail" property="id.item.code" scope="page"/><br>
										</logic:iterate>
									</td>
									<td align="right">
										<logic:iterate id="posOrderDetail" name="penjualanHarian" property="posOrderDetails" type="com.mpe.financial.model.PosOrderDetail">
										<bean:write name="posOrderDetail" property="formatedPrice" scope="page"/><br>
										</logic:iterate>
									</td>
									<td align="right"><bean:write name="penjualanHarian" property="discountProcent" scope="page"/></td>
									<td align="right"><bean:write name="penjualanHarian" property="formatedDiscountAmount" scope="page"/></td>
									<td align="right">
										<logic:match name="penjualanHarian" property="paymentMethod" value="CASH">
										<bean:write name="penjualanHarian" property="formatedTotalAmountAfterDiscountAndTax" scope="page"/>
										</logic:match>
									</td>
									<td align="right">
										<logic:notMatch name="penjualanHarian" property="paymentMethod" value="CASH">
										<bean:write name="penjualanHarian" property="formatedTotalAmountAfterDiscountAndTax" scope="page"/>
										</logic:notMatch>
									</td>
                </tr>
                </logic:iterate>
              </table>
            </td>
          </tr>
          <tr>
    				<td>&nbsp;</td>
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
    								<input class="Button" type="button" name="create" onclick="window.open('<html:rewrite page="/itemReport/penjualanHarianPdf.do"/>?locationId=<bean:write name="itemForm" property="locationId"/>&fromDate=<bean:write name="itemForm" property="fromDate"/>','gg','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,copyhistory=yes,width=800,height=600');" value="<bean:message key="page.print.link"/>">
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



