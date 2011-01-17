<%@ page contentType="text/html;charset=iso-8859-1" language="java" %>
<% response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>


<HTML>
<HEAD><TITLE><bean:message key="page.index.title"/></TITLE>
<LINK href="<html:rewrite page="/js/styleHeader.css"/>" type=text/css rel=stylesheet>
</HEAD>
<BODY leftMargin=0 topMargin=0 rightMargin=0 marginwidth="0" marginheight="0">

<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0 valign="top">
  <!-- header start -->
  <tiles:insert template='/common/header.jsp'/>
  <!-- header end -->
  
  <tr valign="top"><td>
  
    
<!-- content -->    
      <logic:notPresent scope="session" name="user">
      <TABLE height=50 cellSpacing=1 cellPadding=1 border=0 width="100%" valign="top"><BR><BR>
        <TR valign="top">
          <TD align=middle width="80%">
            <TABLE cellSpacing=0 cellPadding=0 width="92%" border=0>
              <TR>
                <TD width=9 height=25><html:img page="/images/left_title.gif"/></TD>
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25>LOGON</TD></TR>
            </TABLE>
          </TD>
        </TR>
        <TR>
          <TD align=middle width="80%" height=10>&nbsp;</TD></TR>
        <TR>
          <TD align=middle width="80%"> 
            <html:form action="/logon" focus="userName">
						<TABLE cellSpacing=0 cellPadding=0 width="92%" border=0 height="400">
						  <TR valign="top">
						    <TD><html:errors/>&nbsp;</TD>
						  </TR>
						  <TR valign="top">
						    <TD>
						      <TABLE width="100%" border=0>
						        <TR valign="top">
						          <TD class=brief><bean:message key="page.userName.title"/>&nbsp;:&nbsp;</TD></TR>
						        <TR>
						          <TD class=brief><html:text property="userName" size="20" styleClass="Data"/>
						          </TD></TR>
						        <TR>
						          <TD class=brief><bean:message key="page.userPass.title"/>&nbsp;:&nbsp;</TD></TR>
						        <TR>
						          <TD class=brief><html:password property="userPass" size="20" styleClass="Data"/></TD></TR>
						        <TR>
						          <TD class=brief>&nbsp;<html:submit styleClass="Button">LOGIN</html:submit>
						          </TD>
						        </TR>
						     	</TABLE>
						    </TD>
						  </TR>
						</TABLE>
						</html:form>
            
          </TD>
        </TR>
      </TABLE>
      </logic:notPresent>
      <logic:present scope="session" name="user">
      <TABLE height=50 cellSpacing=1 cellPadding=1 border=0 width="100%" valign="top"><BR><BR>
        <TR>
          <TD align=middle width="80%">
            <TABLE cellSpacing=0 cellPadding=0 width="92%" border=0>
              <TR>
                <TD width=9 height=25><html:img page="/images/left_title.gif"/></TD>
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25>HOME</TD></TR>
            </TABLE>
          </TD>
        </TR>
        <TR>
          <TD align=middle width="80%" height=10>&nbsp;</TD></TR>
        <TR>
          <TD align=middle width="80%"> 
						<TABLE cellSpacing=0 cellPadding=0 width="92%" border=0 height="100%">
						  <TR valign="top">
						    <TD><html:errors/>&nbsp;</TD>
						  </TR>
						  <tr>
						  	<td>
						  	
						  		<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr valign="top">
											<td width="48%">
												<table width="100%" border="0" cellspacing="0" cellpadding="0">


													<!-- bank balance -->
													
													<logic:present name="bankChartOfAccountLst">
													<tr valign="top">
														<td>
															<table border="0" cellspacing=1 cellpadding=0 bgcolor="#cccccc" width="100%">
																<tr align="center"> 
																	<td width="75%" class=titleField>Bank balance</td>
																	<td class=titleField>&nbsp;</td>
																</tr>
																<logic:present name="bankChartOfAccountLst">
																<logic:iterate id="generalLedgerReport" name="bankChartOfAccountLst" type="com.mpe.financial.model.GeneralLedgerReport">
																<tr bgcolor="#FFFFFF" align="center"> 
																	<td align="left">&nbsp;<bean:write name="generalLedgerReport" property="number" scope="page"/> :: <bean:write name="generalLedgerReport" property="name" scope="page"/></td>
																	<td align="right"><bean:write name="generalLedgerReport" property="formatedEndAmount" scope="page"/></td>
																</tr>
																</logic:iterate>
																</logic:present>
															</table>
														</td>
													</tr>
													<tr>
														<td>&nbsp;</td>
													</tr>
													</logic:present>
													
													<!-- bank chart -->
													
													<tr>
														<td>
															<table border="0" cellspacing=1 cellpadding=0 bgcolor="#cccccc" width="100%">
																<tr align="center"> 
																	<td class=titleField>Cash and Bank Chart</td>
																</tr>
																<tr bgcolor="#FFFFFF" align="center"> 
																	<td>
																		<img src="<%=request.getContextPath()%>/bankChartOfAccount/chart.do" border="0" height="200" width="300"/>
																	</td>
																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td>&nbsp;</td>
													</tr>
													
													<!-- receiveable due -->
													<!--
													<logic:present name="invoiceLst">
													<tr valign="top">
														<td>
															<table border="0" cellspacing=1 cellpadding=0 bgcolor="#cccccc" width="100%">
																<tr align="center"> 
																	<td width="75%" class=titleField>Receiveable Due</td>
																	<td class=titleField>&nbsp;</td>
																</tr>
																
																
																
															</table>
														</td>
													</tr>
													<tr>
														<td>&nbsp;</td>
													</tr>
													</logic:present>
													-->
													<!-- payable due -->
													<!--
													<logic:present name="vendorBillLst" scope="request">
													<tr valign="top">
														<td>
															<table border="0" cellspacing=1 cellpadding=0 bgcolor="#cccccc" width="100%">
																<tr align="center"> 
																	<td width="75%" class=titleField>Payable Due</td>
																	<td class=titleField>&nbsp;</td>
																</tr>
																
															</table>
														</td>
													</tr>
													<tr>
														<td>&nbsp;</td>
													</tr>
													</logic:present>
													-->
												</table>
											</td>
											<td>&nbsp;</td>
											<td width="48%">
												<table width="100%" border="0" cellspacing="0" cellpadding="0">
												
													<!-- profit loss chart -->
													
													<tr>
														<td>
															<table border="0" cellspacing=1 cellpadding=0 bgcolor="#cccccc" width="100%">
																<tr align="center"> 
																	<td class=titleField>Income and Expense Chart</td>
																</tr>
																<tr bgcolor="#FFFFFF" align="center"> 
																	<td>
																		<img src="<%=request.getContextPath()%>/profitLoss/chart.do" border="0" height="150" width="300"/>
																	</td>
																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td>&nbsp;</td>
													</tr>
													
													<!-- minimum inventory -->
													<tr valign="top">
														<td>
															<table border="0" cellspacing=1 cellpadding=0 bgcolor="#cccccc" width="100%">
																<tr align="center"> 
																	<td width="65%" class=titleField>Minimum inventory</td>
																	<td class=titleField>Current</td>
																	<td class=titleField>Minimum</td>
																</tr>
																<logic:present name="minimumStockLst">
																<logic:iterate id="mis" name="minimumStockLst" type="com.mpe.financial.model.MinimumItemStock">
																<tr bgcolor="#FFFFFF" align="center"> 
																	<td align="left">&nbsp;<bean:write name="mis" property="code" scope="page"/></td>
																	<td align="right"><bean:write name="mis" property="quantity" scope="page"/></td>
																	<td align="right"><bean:write name="mis" property="minimumQuantity" scope="page"/></td>
																</tr>
																</logic:iterate>
																</logic:present>
																
															</table>
														</td>
													</tr>
													<tr>
														<td>&nbsp;</td>
													</tr>

													<!-- Top item's sale -->
													
													<tr valign="top">
														<td>
															<table border="0" cellspacing=1 cellpadding=0 bgcolor="#cccccc" width="100%">
																<tr align="center"> 
																	<td width="100%" class=titleField>Top Item's Sales</td>
																</tr>
																<tr bgcolor="#FFFFFF" align="center"> 
																	<td>
																		<img src="<%=request.getContextPath()%>/itemTopSales/chart.do" border="0" height="150" width="300"/>
																	</td>
																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td>&nbsp;</td>
													</tr>
													
													
												</table>
											</td>
										</tr>
									</table>
									
						  	</td>
						  </td>						  
						</TABLE>
          </TD>
        </TR>
      </TABLE>
      </logic:present>
            
<!-- end content-->            
      </TD>
    </TR>
  <!-- footer start -->
  <tiles:insert template='/common/footer.jsp'/>
  <!-- footer end -->
 </TABLE>
</BODY></HTML>