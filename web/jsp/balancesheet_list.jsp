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
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.balanceSheetTitle.title"/></TD></TR>
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
    			<html:form action="/balanceSheet/list">
					<html:hidden property="start"/>
					<html:hidden property="count"/>
					<html:hidden property="orderBy"/>
					<html:hidden property="ascDesc"/>
    			<tr>
    				<td>
    					<table width="100%">
    						<tr>
    							<td><bean:message key="page.date.title"/></td><td>:</td><td><html:text property="toDate" size="12"/>&nbsp;(dd/MM/yyyy)&nbsp;
    								<html:submit styleClass="Button"><bean:message key="page.search.link"/></html:submit>
    							</td>
    						</tr>
    					</table>
    				</td>
    			</tr>
    			<tr>
    				<td>&nbsp;</td>
    			</tr>
    			<tr>
    				<td width="100%">
    					<table width="100%">
			    			<tr>
			    				<td width="49%" valign="top">
			    					<table width="100%" valign="top">
			    						<tr bgcolor="#FFFFFF" valign="top"> 
						            <td align="center" width="100%" valign="top"> 
						              <table border="0" cellspacing=1 cellpadding=2 bgcolor="#cccccc" width="100%">
						                <tr align="center"> 
						                  <td class=titleField colspan="3"><bean:message key="page.asset.title"/></td>
						                </tr>
						                <tr align="center"> 
						                  <td width="5%" class=titleField>&nbsp;</td>
						                  <td class=titleField><bean:message key="page.chartGroup.title"/></td>
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
														<logic:present name="ASSET">
						                <logic:iterate id="asset" name="ASSET" type="com.mpe.financial.model.ProfitLossReport">
														<% 
															sequence++;
															String bgcolor = "FFFFFF";
															if(sequence%2 == 0){
																bgcolor = "F5F9EE";
															}
														 %>
														<tr bgcolor="<% out.print(bgcolor); %>">
						                	<td align="right"><%=++i%>.</td>
															<td><bean:write name="asset" property="name" scope="page"/></td>
															<td align="right"><bean:write name="asset" property="formatedAmount" scope="page"/></td>
						                </tr>
						                </logic:iterate>
						                </logic:present>
						                <tr bgcolor="#FFFFFF">
						                	<td colspan="2">&nbsp;</td>
						                	<td align="right"><b><bean:write name="assetTotal" scope="request"/></b></td>
						                </tr>
						                <tr bgcolor="#FFFFFF">
						                	<td colspan="2">&nbsp;</td>
						                	<td>&nbsp;</td>
						                </tr>
						                <tr align="center"> 
						                  <td class=titleField colspan="3"><bean:message key="page.liability.title"/></td>
						                </tr>
						                <tr align="center"> 
						                  <td width="5%" class=titleField>&nbsp;</td>
						                  <td class=titleField><bean:message key="page.chartGroup.title"/></td>
						                  <td class=titleField><bean:message key="page.amount.title"/></td>
						                </tr>
						                <logic:present name="LIABILITY">
						                <logic:iterate id="liability" name="LIABILITY" type="com.mpe.financial.model.ProfitLossReport">
														<% 
															sequence++;
															String bgcolor = "FFFFFF";
															if(sequence%2 == 0){
																bgcolor = "F5F9EE";
															}
														 %>
														<tr bgcolor="<% out.print(bgcolor); %>">
						                	<td align="right"><%=++i%>.</td>
															<td><bean:write name="liability" property="name" scope="page"/></td>
															<td align="right"><bean:write name="liability" property="formatedAmount" scope="page"/></td>
						                </tr>
						                </logic:iterate>
						                </logic:present>
						                <tr bgcolor="#FFFFFF">
						                	<td colspan="2">&nbsp;</td>
						                	<td align="right"><b><bean:write name="liabilityTotal" scope="request"/></b></td>
						                </tr>
						                <tr bgcolor="#FFFFFF">
						                	<td colspan="2">&nbsp;</td>
						                	<td align="right"><b><bean:write name="assetMinusLiabilityTotal" scope="request"/></b></td>
						                </tr>
						              </table>
						            </td>
						          </tr>
			    					</table>
			    				</td>
			    				<td>&nbsp;</td>
			    				<td width="49%" valign="top">
			    					<table width="100%" valign="top">
			    						<tr bgcolor="#FFFFFF" valign="top"> 
						            <td align="center" valign="top" width="100%"> 
						              <table border="0" cellspacing=1 cellpadding=2 bgcolor="#cccccc" width="100%">
						                <tr align="center"> 
						                  <td class=titleField colspan="3"><bean:message key="page.equity.title"/></td>
						                </tr>
						                <tr align="center"> 
						                  <td width="5%" class=titleField>&nbsp;</td>
						                  <td class=titleField><bean:message key="page.chartGroup.title"/></td>
						                  <td class=titleField><bean:message key="page.amount.title"/></td>
						                </tr>
<%
int k = 0;
%>
														<logic:present name="EQUITY">
						                <logic:iterate id="equity" name="EQUITY" type="com.mpe.financial.model.ProfitLossReport">
														<% 
															sequence++;
															String bgcolor = "FFFFFF";
															if(sequence%2 == 0){
																bgcolor = "F5F9EE";
															}
														 %>
														<tr bgcolor="<% out.print(bgcolor); %>">
						                	<td align="right"><%=++k%>.</td>
															<td><bean:write name="equity" property="name" scope="page"/></td>
															<td align="right"><bean:write name="equity" property="formatedAmount" scope="page"/></td>
						                </tr>
						                </logic:iterate>
						                </logic:present>
						                <tr bgcolor="#FFFFFF">
						                	<td colspan="2">&nbsp;</td>
						                	<td align="right"><b><bean:write name="equityTotal" scope="request"/></b></td>
						                </tr>
						                <tr bgcolor="#FFFFFF">
						                	<td colspan="2">&nbsp;</td>
						                	<td>&nbsp;</td>
						                </tr>
						              </table>
						            </td>
						          </tr>
			    					</table>
			    				</td>
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



