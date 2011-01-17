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
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.profitLossTitle.title"/></TD></TR>
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
    			<html:form action="/profitLoss/labaRugi">
					<html:hidden property="start"/>
					<html:hidden property="count"/>
					<html:hidden property="orderBy"/>
					<html:hidden property="ascDesc"/>
    			<tr>
    				<td>
    					<table width="100%">
    						<tr>
    							<td><bean:message key="page.date.title"/></td><td>:</td><td><html:text property="fromDate" size="12"/>&nbsp;-&nbsp;<html:text property="toDate" size="12"/>&nbsp;(dd/MM/yyyy)</td>
    						</tr>
    						<tr>
    							<td><bean:message key="page.department.title"/></td><td>:</td><td>
    								<html:select property="departmentId">
											<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
											<html:options collection="departmentLst" property="id" labelProperty="name" />
                  	</html:select>
    							</td>
    						</tr>
    						<tr>
    							<td><bean:message key="page.project.title"/></td><td>:</td><td>
    								<html:select property="projectId">
											<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
											<html:options collection="projectLst" property="id" labelProperty="name" />
                  	</html:select>
    							&nbsp;<html:submit styleClass="Button"><bean:message key="page.search.link"/></html:submit></td>
    						</tr>
    					</table>
    				</td>
    			</tr>
    			<!--
    			<tr>
    				<td align="center">
    					<input class="Button" type="button" name="back" onclick="window.open('<html:rewrite page="/profitLoss/labaRugiPdf.do"/>?fromDate=<bean:write name="generalLedgerForm" property="fromDate"/>&toDate=<bean:write name="generalLedgerForm" property="toDate"/>');" value="<bean:message key="page.print.link"/>"/>
    				</td>
    			</tr>
    			-->
    			<tr>
    				<td>&nbsp;</td>
    			</tr>
          <tr bgcolor="#FFFFFF"> 
            <td align="center"> 
              <table border="0" cellspacing=1 cellpadding=2 bgcolor="#cccccc" width="100%">
                <tr align="center"> 
                	<td class=titleField colspan="6"><bean:message key="page.revenue.title"/></td>
                </tr>
                <tr align="center"> 
                  <td width="5%" class=titleField>&nbsp;</td>
                  <td class=titleField><bean:message key="page.name.title"/></td>
                  <td class=titleField>Current&nbsp;<bean:message key="page.amount.title"/></td>
                  <td class=titleField>Last&nbsp;<bean:message key="page.amount.title"/></td>
                  <td class=titleField>YTD&nbsp;<bean:message key="page.amount.title"/></td>
                  <td class=titleField>From-To&nbsp;<bean:message key="page.amount.title"/></td>
                </tr>
<%
int i = 0;
try {
i = Integer.parseInt(request.getParameter("start"));
}catch(Exception ex){
i = 0;
}
%>
								<logic:present name="REVENUE">
                <logic:iterate id="revenue" name="REVENUE" type="com.mpe.financial.model.ProfitLoss3Report">
								<% 
									sequence++;
									String bgcolor = "FFFFFF";
									if(sequence%2 == 0){
										bgcolor = "F5F9EE";
									}
								 %>
								<tr bgcolor="<% out.print(bgcolor); %>">
									<td colspan="2"><a href="<html:rewrite page="/profitLoss/labaRugiDetail.do"/>?fromDate=<bean:write name="generalLedgerForm" property="fromDate"/>&toDate=<bean:write name="generalLedgerForm" property="toDate"/>&chartGroupId=<bean:write name="revenue" property="chartOfAccountId" scope="page"/>"><b><bean:write name="revenue" property="name" scope="page"/></b></a></td>
									<td align="right"><b><bean:write name="revenue" property="formatedCurrentAmount" scope="page"/></b></td>
									<td align="right"><b><bean:write name="revenue" property="formatedLastAmount" scope="page"/></b></td>
									<td align="right"><b><bean:write name="revenue" property="formatedYtdAmount" scope="page"/></b></td>
									<td align="right"><b><bean:write name="revenue" property="formatedFromToAmount" scope="page"/></b></td>
                </tr>
                
                <%int l =0;%>
                <logic:iterate id="equityParent" name="revenue" property="list" type="com.mpe.financial.model.ProfitLoss3Report">
                <% 
									sequence++;
									bgcolor = "FFFFFF";
									if(sequence%2 == 0){
										bgcolor = "F5F9EE";
									}
								 %>
                <tr bgcolor="<% out.print(bgcolor); %>">
                	<td align="right"><%=++l%>.</td>
									<td><bean:write name="equityParent" property="name" scope="page"/></td>
									<td align="right"><bean:write name="equityParent" property="formatedCurrentAmount" scope="page"/></td>
									<td align="right"><bean:write name="equityParent" property="formatedLastAmount" scope="page"/></td>
									<td align="right"><bean:write name="equityParent" property="formatedYtdAmount" scope="page"/></td>
									<td align="right"><bean:write name="equityParent" property="formatedFromToAmount" scope="page"/></td>
                </tr>
                </logic:iterate>
                
                </logic:iterate>
                </logic:present>
                <tr bgcolor="#FFFFFF">
                	<td colspan="2">&nbsp;</b></td>
                	<td align="right"><b><bean:write name="currentRevenueTotal" scope="request"/></b></td>
                	<td align="right"><b><bean:write name="lastRevenueTotal" scope="request"/></b></td>
                	<td align="right"><b><bean:write name="ytdRevenueTotal" scope="request"/></b></td>
                	<td align="right"><b><bean:write name="fromToRevenueTotal" scope="request"/></b></td>
                </tr>
                
                                
                <tr align="center"> 
                	<td class=titleField colspan="6"><bean:message key="page.expense.title"/></td>
                </tr>
                <tr align="center"> 
                  <td width="5%" class=titleField>&nbsp;</td>
                  <td class=titleField><bean:message key="page.name.title"/></td>
                  <td class=titleField>Current&nbsp;<bean:message key="page.amount.title"/></td>
                  <td class=titleField>Last&nbsp;<bean:message key="page.amount.title"/></td>
                  <td class=titleField>YTD&nbsp;<bean:message key="page.amount.title"/></td>
                  <td class=titleField>From-To&nbsp;<bean:message key="page.amount.title"/></td>
                </tr>
                <logic:present name="EXPENSE">
                <logic:iterate id="expense" name="EXPENSE" type="com.mpe.financial.model.ProfitLoss3Report">
								<% 
									sequence++;
									String bgcolor = "FFFFFF";
									if(sequence%2 == 0){
										bgcolor = "F5F9EE";
									}
								 %>
								<tr bgcolor="<% out.print(bgcolor); %>">
									<td colspan="2"><a href="<html:rewrite page="/profitLoss/labaRugiDetail.do"/>?fromDate=<bean:write name="generalLedgerForm" property="fromDate"/>&toDate=<bean:write name="generalLedgerForm" property="toDate"/>&chartGroupId=<bean:write name="expense" property="chartOfAccountId" scope="page"/>"><b><bean:write name="expense" property="name" scope="page"/></b></a></td>
									<td align="right"><b><bean:write name="expense" property="formatedCurrentAmount" scope="page"/></b></td>
									<td align="right"><b><bean:write name="expense" property="formatedLastAmount" scope="page"/></b></td>
									<td align="right"><b><bean:write name="expense" property="formatedYtdAmount" scope="page"/></b></td>
									<td align="right"><b><bean:write name="expense" property="formatedFromToAmount" scope="page"/></b></td>
                </tr>
                
                <%int g =0;%>
                <logic:iterate id="equityParent" name="expense" property="list" type="com.mpe.financial.model.ProfitLoss3Report">
                <% 
									sequence++;
									bgcolor = "FFFFFF";
									if(sequence%2 == 0){
										bgcolor = "F5F9EE";
									}
								 %>
                <tr bgcolor="<% out.print(bgcolor); %>">
                	<td align="right"><%=++g%>.</td>
									<td><bean:write name="equityParent" property="name" scope="page"/></td>
									<td align="right"><bean:write name="equityParent" property="formatedCurrentAmount" scope="page"/></td>
									<td align="right"><bean:write name="equityParent" property="formatedLastAmount" scope="page"/></td>
									<td align="right"><bean:write name="equityParent" property="formatedYtdAmount" scope="page"/></td>
									<td align="right"><bean:write name="equityParent" property="formatedFromToAmount" scope="page"/></td>
                </tr>
                </logic:iterate>
                
                </logic:iterate>
                </logic:present>
                <tr bgcolor="#FFFFFF">
                	<td colspan="2"></td>
                	<td align="right"><b><bean:write name="currentExpenseTotal" scope="request"/></b></td>
                	<td align="right"><b><bean:write name="lastExpenseTotal" scope="request"/></b></td>
                	<td align="right"><b><bean:write name="ytdExpenseTotal" scope="request"/></b></td>
                	<td align="right"><b><bean:write name="fromToExpenseTotal" scope="request"/></b></td>
                </tr>
                <tr bgcolor="#FFFFFF">
                	<td colspan="2"><b><bean:message key="page.profitLoss.title"/>&nbsp;</b></td>
                	<td align="right"><b><bean:write name="currentProfitLossTotal" scope="request"/></b></td>
                	<td align="right"><b><bean:write name="lastProfitLossTotal" scope="request"/></b></td>
                	<td align="right"><b><bean:write name="ytdProfitLossTotal" scope="request"/></b></td>
                	<td align="right"><b><bean:write name="fromToProfitLossTotal" scope="request"/></b></td>
                </tr>
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
    					<input class="Button" type="button" name="back" onclick="window.open('<html:rewrite page="/profitLoss/labaRugiPdf.do"/>?fromDate=<bean:write name="generalLedgerForm" property="fromDate"/>&toDate=<bean:write name="generalLedgerForm" property="toDate"/>&projectId=<bean:write name="generalLedgerForm" property="projectId"/>','gg','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,copyhistory=yes,width=800,height=600');" value="<bean:message key="page.print.link"/>"/>
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



