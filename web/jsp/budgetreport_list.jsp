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
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.budgetTitle.title"/></TD></TR>
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
    			<html:form action="/budgetReport/list">
					<html:hidden property="start"/>
					<html:hidden property="count"/>
					<html:hidden property="orderBy"/>
					<html:hidden property="ascDesc"/>
    			<tr>
    				<td>
    					<table width="100%">
    						<tr>
    							<td><bean:message key="page.year.title"/></td><td>:</td><td><html:text property="year" size="4"/>&nbsp;<html:submit styleClass="Button"><bean:message key="page.search.link"/></html:submit></td>
    						</tr>
    					</table>
    				</td>
    			</tr>
    			
          <tr bgcolor="#FFFFFF"> 
            <td align="center"> 
              <table border="0" cellspacing=1 cellpadding=2 bgcolor="#cccccc" width="100%">
                <tr align="center">
                  <td width="5%" class=titleField rowspan="3">&nbsp;</td>
                  <td class=titleField rowspan="3"><bean:message key="page.chartOfAccount.title"/></td>
                  <td class=titleField colspan="24"><bean:message key="page.month.title"/></td>
                </tr>
                <tr align="center">
                	<td class=titleField colspan="2"><bean:message key="page.january.title"/></td>
                	<td class=titleField colspan="2"><bean:message key="page.february.title"/></td>
                	<td class=titleField colspan="2"><bean:message key="page.march.title"/></td>
                	<td class=titleField colspan="2"><bean:message key="page.april.title"/></td>
                	<td class=titleField colspan="2"><bean:message key="page.may.title"/></td>
                	<td class=titleField colspan="2"><bean:message key="page.june.title"/></td>
                	<td class=titleField colspan="2"><bean:message key="page.july.title"/></td>
                	<td class=titleField colspan="2"><bean:message key="page.august.title"/></td>
                	<td class=titleField colspan="2"><bean:message key="page.september.title"/></td>
                	<td class=titleField colspan="2"><bean:message key="page.october.title"/></td>
                	<td class=titleField colspan="2"><bean:message key="page.november.title"/></td>
                	<td class=titleField colspan="2"><bean:message key="page.december.title"/></td>
                </tr>
                <tr align="center">
                	<td class=titleField><bean:message key="page.budget.title"/></td>
                	<td class=titleField><bean:message key="page.actual.title"/></td>
                	<td class=titleField><bean:message key="page.budget.title"/></td>
                	<td class=titleField><bean:message key="page.actual.title"/></td>
                	<td class=titleField><bean:message key="page.budget.title"/></td>
                	<td class=titleField><bean:message key="page.actual.title"/></td>
                	<td class=titleField><bean:message key="page.budget.title"/></td>
                	<td class=titleField><bean:message key="page.actual.title"/></td>
                	<td class=titleField><bean:message key="page.budget.title"/></td>
                	<td class=titleField><bean:message key="page.actual.title"/></td>
                	<td class=titleField><bean:message key="page.budget.title"/></td>
                	<td class=titleField><bean:message key="page.actual.title"/></td>
                	<td class=titleField><bean:message key="page.budget.title"/></td>
                	<td class=titleField><bean:message key="page.actual.title"/></td>
                	<td class=titleField><bean:message key="page.budget.title"/></td>
                	<td class=titleField><bean:message key="page.actual.title"/></td>
                	<td class=titleField><bean:message key="page.budget.title"/></td>
                	<td class=titleField><bean:message key="page.actual.title"/></td>
                	<td class=titleField><bean:message key="page.budget.title"/></td>
                	<td class=titleField><bean:message key="page.actual.title"/></td>
                	<td class=titleField><bean:message key="page.budget.title"/></td>
                	<td class=titleField><bean:message key="page.actual.title"/></td>
                	<td class=titleField><bean:message key="page.budget.title"/></td>
                	<td class=titleField><bean:message key="page.actual.title"/></td>
                </tr>
                
<%
int i = 0;
try {
i = Integer.parseInt(request.getParameter("start"));
}catch(Exception ex){
i = 0;
}
%>
								<logic:present name="budgetReportLst">
                <logic:iterate id="budgetReport" name="budgetReportLst" type="com.mpe.financial.model.BudgetReport">
                <% 
									sequence++;
									String bgcolor = "FFFFFF";
									if(sequence%2 == 0){
										bgcolor = "F5F9EE";
									}
								 %>
								<tr bgcolor="<% out.print(bgcolor); %>">
                	<td class="Cell" align="right"><%=++i%>.</td>
                	<td class="Cell">
                		<bean:write name="budgetReport" property="number" scope="page"/><br>
                		<bean:write name="budgetReport" property="name" scope="page"/>
                	</td>
                	<td class="Cell" align="right"><bean:write name="budgetReport" property="formatedBudgetJanuary" scope="page"/></td>
                	<td class="Cell" align="right"><bean:write name="budgetReport" property="formatedGeneralLedgerJanuary" scope="page"/></td>
                	<td class="Cell" align="right"><bean:write name="budgetReport" property="formatedBudgetFebruary" scope="page"/></td>
                	<td class="Cell" align="right"><bean:write name="budgetReport" property="formatedGeneralLedgerFebruary" scope="page"/></td>
                	<td class="Cell" align="right"><bean:write name="budgetReport" property="formatedBudgetMarch" scope="page"/></td>
                	<td class="Cell" align="right"><bean:write name="budgetReport" property="formatedGeneralLedgerMarch" scope="page"/></td>
                	<td class="Cell" align="right"><bean:write name="budgetReport" property="formatedBudgetApril" scope="page"/></td>
                	<td class="Cell" align="right"><bean:write name="budgetReport" property="formatedGeneralLedgerApril" scope="page"/></td>
                	<td class="Cell" align="right"><bean:write name="budgetReport" property="formatedBudgetMay" scope="page"/></td>
                	<td class="Cell" align="right"><bean:write name="budgetReport" property="formatedGeneralLedgerMay" scope="page"/></td>
                	<td class="Cell" align="right"><bean:write name="budgetReport" property="formatedBudgetJune" scope="page"/></td>
                	<td class="Cell" align="right"><bean:write name="budgetReport" property="formatedGeneralLedgerJune" scope="page"/></td>
                	<td class="Cell" align="right"><bean:write name="budgetReport" property="formatedBudgetJuly" scope="page"/></td>
                	<td class="Cell" align="right"><bean:write name="budgetReport" property="formatedGeneralLedgerJuly" scope="page"/></td>
                	<td class="Cell" align="right"><bean:write name="budgetReport" property="formatedBudgetAugust" scope="page"/></td>
                	<td class="Cell" align="right"><bean:write name="budgetReport" property="formatedGeneralLedgerAugust" scope="page"/></td>
                	<td class="Cell" align="right"><bean:write name="budgetReport" property="formatedBudgetSeptember" scope="page"/></td>
                	<td class="Cell" align="right"><bean:write name="budgetReport" property="formatedGeneralLedgerSeptember" scope="page"/></td>
                	<td class="Cell" align="right"><bean:write name="budgetReport" property="formatedBudgetOctober" scope="page"/></td>
                	<td class="Cell" align="right"><bean:write name="budgetReport" property="formatedGeneralLedgerOctober" scope="page"/></td>
                	<td class="Cell" align="right"><bean:write name="budgetReport" property="formatedBudgetNovember" scope="page"/></td>
                	<td class="Cell" align="right"><bean:write name="budgetReport" property="formatedGeneralLedgerNovember" scope="page"/></td>
                	<td class="Cell" align="right"><bean:write name="budgetReport" property="formatedBudgetDecember" scope="page"/></td>
                	<td class="Cell" align="right"><bean:write name="budgetReport" property="formatedGeneralLedgerDecember" scope="page"/></td>
                </tr>
                </logic:iterate>
                </logic:present>
                
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
    			<!--
    			<tr>
    				<td align="center">
    					<table width="10%">
    						<tr>
    							<td align="center">
    								<input class="Button" type="button" name="create" onclick="window.location.href=('<html:rewrite page="/budget/form.do"/>');" value="<bean:message key="page.new.link"/>">
    							</td>
    						</tr>
    					</table>
    				</td>
    			</tr>
    			-->
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



