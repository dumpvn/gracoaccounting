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
    			<tr valign="top"><td>&nbsp;</td></tr>
    			<html:form action="/budget/detail">
					<html:hidden property="budgetId"/>								        			
          <tr> 
            <td align="center"> 
              <table width="100%" border="0" cellspacing="1" cellpadding="0" class=abuabu>

		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.name.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="budget" property="year" scope="request"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.department.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<logic:present name="budget" property="department">
                  	<bean:write name="budget" property="department.name" scope="request"/>
                  	</logic:present>
                  </td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.description.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="budget" property="description" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.createBy.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<logic:present name="budget" property="createBy">
                  	<bean:write name="budget" property="createBy.userName" scope="request"/>
                  	</logic:present>
                  </td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.createOn.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="budget" property="formatedCreateOn" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.changeBy.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<logic:present name="budget" property="changeBy">
                  	<bean:write name="budget" property="changeBy.userName" scope="request"/>
                  	</logic:present>
                  </td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.changeOn.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="budget" property="formatedChangeOn" scope="request"/></td>
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
    				<td>
    					<table width="100%">
    						<tr bgcolor="#FFFFFF"> 
			            <td align="center"> 
			              <table border="0" cellspacing=1 cellpadding=0 bgcolor="#cccccc" width="100%">
			                <tr align="center"> 
			                  <td width="5%" class=titleField rowspan="3">&nbsp;</td>
			                  <td class=titleField rowspan="3"><bean:message key="page.chartOfAccount.title"/></td>
			                  <td class=titleField rowspan="3"><bean:message key="page.revision.title"/></td>
			                  <td class=titleField colspan="6"><bean:message key="page.month.title"/></td>
			                </tr>
			                <tr align="center">
			                	<td class=titleField><bean:message key="page.january.title"/></td>
			                	<td class=titleField><bean:message key="page.february.title"/></td>
			                	<td class=titleField><bean:message key="page.march.title"/></td>
			                	<td class=titleField><bean:message key="page.april.title"/></td>
			                	<td class=titleField><bean:message key="page.may.title"/></td>
			                	<td class=titleField><bean:message key="page.june.title"/></td>
			                </tr>
			                <tr align="center">
			                	<td class=titleField><bean:message key="page.july.title"/></td>
			                	<td class=titleField><bean:message key="page.august.title"/></td>
			                	<td class=titleField><bean:message key="page.september.title"/></td>
			                	<td class=titleField><bean:message key="page.october.title"/></td>
			                	<td class=titleField><bean:message key="page.november.title"/></td>
			                	<td class=titleField><bean:message key="page.december.title"/></td>
			                </tr>
			<%
			int i = 0;
			try {
			i = Integer.parseInt(request.getParameter("start"));
			}catch(Exception ex){
			i = 0;
			}
			%>
											<logic:present name="budget" property="budgetDetails">
			                <logic:iterate id="budgetDetail" name="budget" property="budgetDetails" type="com.mpe.financial.model.BudgetDetail">
			                <tr bgcolor="#FFFFFF" align=center>
			                	<td rowspan="2"><%=++i%>.</td>
												<td rowspan="2">
													<logic:present name="budgetDetail" property="id.chartOfAccount">
													<bean:write name="budgetDetail" property="id.chartOfAccount.numberName" scope="page"/>
													</logic:present>
												</td>
												<td rowspan="2"><bean:write name="budgetDetail" property="id.revisionNumber" scope="page"/></td>
												<td><bean:write name="budgetDetail" property="formatedAmount1" scope="page"/></td>
												<td><bean:write name="budgetDetail" property="formatedAmount2" scope="page"/></td>
												<td><bean:write name="budgetDetail" property="formatedAmount3" scope="page"/></td>
												<td><bean:write name="budgetDetail" property="formatedAmount4" scope="page"/></td>
												<td><bean:write name="budgetDetail" property="formatedAmount5" scope="page"/></td>
												<td><bean:write name="budgetDetail" property="formatedAmount6" scope="page"/></td>
			                </tr>
			                <tr bgcolor="#FFFFFF" align=center>
			                	<td><bean:write name="budgetDetail" property="formatedAmount7" scope="page"/></td>
												<td><bean:write name="budgetDetail" property="formatedAmount8" scope="page"/></td>
												<td><bean:write name="budgetDetail" property="formatedAmount9" scope="page"/></td>
												<td><bean:write name="budgetDetail" property="formatedAmount10" scope="page"/></td>
												<td><bean:write name="budgetDetail" property="formatedAmount11" scope="page"/></td>
												<td><bean:write name="budgetDetail" property="formatedAmount12" scope="page"/></td>
			                </tr>
			                </logic:iterate>
			                </logic:present>
			                
			                
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
    			<tr>
    				<td align="center">
							<input class="Button" type="button" name="edit" onclick="window.location.href=('<html:rewrite page="/budget/form.do"/>?budgetId=<bean:write name="budget" property="id"/>');" value="<bean:message key="page.edit.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="delete" onclick="window.location.href=('<html:rewrite page="/budget/delete.do"/>?budgetId=<bean:write name="budget" property="id"/>');" value="<bean:message key="page.delete.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="back" onclick="window.location.href=('<html:rewrite page="/budget/list.do"/>?start=<bean:write name="start" scope="session"/>&count=<bean:write name="count" scope="session"/>');" value="<bean:message key="page.back.link"/>"/>
    				</td>
    			</tr>
          </html:form>
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








