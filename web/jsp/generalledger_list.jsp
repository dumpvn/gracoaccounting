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
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.generalLedgerTitle.title"/></TD></TR>
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
    			<html:form action="/generalLedger/list">
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
    							<td><bean:message key="page.chartOfAccount.title"/></td><td>:</td><td>
    								<html:select property="chartOfAccountId" onchange="this.form.submit();">
											<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
											<html:options collection="chartOfAccountLst" property="id" labelProperty="numberName" />
                  	</html:select>
    							</td>
    						</tr>
    						<tr>
    							<td><bean:message key="page.vendor.title"/></td><td>:</td><td>
    								<html:select property="vendorId">
											<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
											<html:options collection="vendorLst" property="id" labelProperty="company" />
                  	</html:select>
    							</td>
    						</tr>
    						<tr>
    							<td><bean:message key="page.customer.title"/></td><td>:</td><td>
    								<html:select property="customerId">
											<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
											<html:options collection="customerLst" property="id" labelProperty="company" />
                  	</html:select>
    							</td>
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
    			<tr>
    				<td>&nbsp;</td>
    			</tr>
          <tr bgcolor="#FFFFFF"> 
            <td align="center"> 
              <table border="0" cellspacing=1 cellpadding=2 bgcolor="#cccccc" width="100%">
                <tr align="center"> 
                  <td width="3%" class=titleField rowspan="2">&nbsp;</td>
                  <td class=titleField rowspan="2"><a href=javascript:sort('Number')><bean:message key="page.number.title"/></a>&nbsp;<logic:match name="generalLedgerForm" property="orderBy" value="Number"><logic:match name="generalLedgerForm" property="ascDesc" value="desc"><html:img page="/images/sort_terang.gif" border="0"/></logic:match><logic:match name="generalLedgerForm" property="ascDesc" value="asc"><html:img page="/images/sort2_terang.gif" border="0"/></logic:match></logic:match></td>
                  <td class=titleField rowspan="2"><a href=javascript:sort('Name')><bean:message key="page.name.title"/></a>&nbsp;<logic:match name="generalLedgerForm" property="orderBy" value="Name"><logic:match name="generalLedgerForm" property="ascDesc" value="desc"><html:img page="/images/sort_terang.gif" border="0"/></logic:match><logic:match name="generalLedgerForm" property="ascDesc" value="asc"><html:img page="/images/sort2_terang.gif" border="0"/></logic:match></logic:match></td>
                  <td class=titleField rowspan="2"><a href=javascript:sort('Type')><bean:message key="page.type.title"/></a>&nbsp;<logic:match name="generalLedgerForm" property="orderBy" value="Type"><logic:match name="generalLedgerForm" property="ascDesc" value="desc"><html:img page="/images/sort_terang.gif" border="0"/></logic:match><logic:match name="generalLedgerForm" property="ascDesc" value="asc"><html:img page="/images/sort2_terang.gif" border="0"/></logic:match></logic:match></td>
                  <td class=titleField rowspan="2"><bean:message key="page.isDebit.title"/></td>
                  <td class=titleField rowspan="2"><bean:message key="page.firstSetup.title"/></td>
                  <td class=titleField colspan="2"><bean:message key="page.amount.title"/></td>
                  <td class=titleField rowspan="2"><bean:message key="page.endAmount.title"/></td>
                </tr>
                <tr align="center"> 
                  <td class=titleField><bean:message key="page.debit.title"/></td>
                  <td class=titleField><bean:message key="page.credit.title"/></td>
                </tr>
<%
int i = 0;
try {
i = Integer.parseInt(request.getParameter("start"));
}catch(Exception ex){
i = 0;
}
%>
								<logic:present name="GENERALLEDGER">
                <logic:iterate id="generalLedger" name="GENERALLEDGER" type="com.mpe.financial.model.GeneralLedgerReport">
								<% 
									sequence++;
									String bgcolor = "FFFFFF";
									if(sequence%2 == 0){
										bgcolor = "F5F9EE";
									}
								 %>
								<tr bgcolor="<% out.print(bgcolor); %>">
                	<td align="right"><%=++i%>.</td>
                	<td><bean:write name="generalLedger" property="number" scope="page"/></td>
									<td><bean:write name="generalLedger" property="name" scope="page"/></td>
									<td><bean:write name="generalLedger" property="type" scope="page"/></td>
									<td><bean:write name="generalLedger" property="debit" scope="page"/></td>
									<td align="right">
                  	<bean:write name="generalLedger" property="formatedFirstAmount" scope="page"/>
									</td>
									<td align="right">
										<!-- debit -->
										<logic:greaterThan name="generalLedger" property="amount" value="0">
										<logic:equal name="generalLedger" property="debit" value="true">
                  	<a href="<html:rewrite page="/generalLedger/journalDetailList.do"/>?chartOfAccountId=<bean:write name="generalLedger" property="chartOfAccountId" scope="page"/>&fromDate=<bean:write name="generalLedgerForm" property="fromDate"/>&toDate=<bean:write name="generalLedgerForm" property="toDate"/>&departmentId=<bean:write name="generalLedgerForm" property="departmentId"/>&projectId=<bean:write name="generalLedgerForm" property="projectId"/>&vendorId=<bean:write name="generalLedgerForm" property="vendorId"/>&customerId=<bean:write name="generalLedgerForm" property="customerId"/>"><bean:write name="generalLedger" property="formatedAmount" scope="page"/></a>
                  	</logic:equal>
                  	</logic:greaterThan>
                  	<logic:lessThan name="generalLedger" property="amount" value="0">
                  	<logic:notEqual name="generalLedger" property="debit" value="true">
                  	<a href="<html:rewrite page="/generalLedger/journalDetailList.do"/>?chartOfAccountId=<bean:write name="generalLedger" property="chartOfAccountId" scope="page"/>&fromDate=<bean:write name="generalLedgerForm" property="fromDate"/>&toDate=<bean:write name="generalLedgerForm" property="toDate"/>&departmentId=<bean:write name="generalLedgerForm" property="departmentId"/>&projectId=<bean:write name="generalLedgerForm" property="projectId"/>&vendorId=<bean:write name="generalLedgerForm" property="vendorId"/>&customerId=<bean:write name="generalLedgerForm" property="customerId"/>"><bean:write name="generalLedger" property="formatedAmount" scope="page"/></a>
                  	</logic:notEqual>
                  	</logic:lessThan>
									</td>
									<td align="right">
										<!-- credit -->
										<logic:greaterThan name="generalLedger" property="amount" value="0">
                  	<logic:notEqual name="generalLedger" property="debit" value="true">
                  	<a href="<html:rewrite page="/generalLedger/journalDetailList.do"/>?chartOfAccountId=<bean:write name="generalLedger" property="chartOfAccountId" scope="page"/>&fromDate=<bean:write name="generalLedgerForm" property="fromDate"/>&toDate=<bean:write name="generalLedgerForm" property="toDate"/>&departmentId=<bean:write name="generalLedgerForm" property="departmentId"/>&projectId=<bean:write name="generalLedgerForm" property="projectId"/>&vendorId=<bean:write name="generalLedgerForm" property="vendorId"/>&customerId=<bean:write name="generalLedgerForm" property="customerId"/>"><bean:write name="generalLedger" property="formatedAmount" scope="page"/></a>
                  	</logic:notEqual>
                  	</logic:greaterThan>
                  	<logic:lessThan name="generalLedger" property="amount" value="0">
										<logic:equal name="generalLedger" property="debit" value="true">
                  	<a href="<html:rewrite page="/generalLedger/journalDetailList.do"/>?chartOfAccountId=<bean:write name="generalLedger" property="chartOfAccountId" scope="page"/>&fromDate=<bean:write name="generalLedgerForm" property="fromDate"/>&toDate=<bean:write name="generalLedgerForm" property="toDate"/>&departmentId=<bean:write name="generalLedgerForm" property="departmentId"/>&projectId=<bean:write name="generalLedgerForm" property="projectId"/>&vendorId=<bean:write name="generalLedgerForm" property="vendorId"/>&customerId=<bean:write name="generalLedgerForm" property="customerId"/>"><bean:write name="generalLedger" property="formatedAmount" scope="page"/></a>
                  	</logic:equal>
                  	</logic:lessThan>
									</td>
									<td align="right">
                  	<bean:write name="generalLedger" property="formatedEndAmount" scope="page"/>
									</td>
                </tr>
                </logic:iterate>
                </logic:present>
                <tr bgcolor="#FFFFFF">
									<td colspan="5">&nbsp;</td>
									<td align="right"><b><bean:write name="firstAmount" scope="request"/></b></td>
									<td align="right"><b><bean:write name="debitAmount" scope="request"/></b></td>
									<td align="right"><b><bean:write name="creditAmount" scope="request"/></b></td>
									<td align="right"><b><bean:write name="endAmount" scope="request"/></b></td>
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
    					<input class="Button" type="button" name="create" onclick="window.open('<html:rewrite page="/generalLedger/pdf.do"/>?projectId=<bean:write name="generalLedgerForm" property="projectId"/>&departmentId=<bean:write name="generalLedgerForm" property="departmentId"/>&fromDate=<bean:write name="generalLedgerForm" property="fromDate"/>&toDate=<bean:write name="generalLedgerForm" property="toDate"/>&vendorId=<bean:write name="generalLedgerForm" property="vendorId"/>&customerId=<bean:write name="generalLedgerForm" property="customerId"/>&chartOfAccountId=<bean:write name="generalLedgerForm" property="chartOfAccountId"/>','gg','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,copyhistory=yes,width=800,height=600');" value="PDF">
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



