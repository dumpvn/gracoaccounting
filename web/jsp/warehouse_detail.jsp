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
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.warehouseTitle.title"/></TD></TR>
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
    			<html:form action="/warehouse/detail">
					<html:hidden property="warehouseId"/>								        			
          <tr> 
            <td align="center"> 
              <table width="100%" border="0" cellspacing="1" cellpadding="0" class=abuabu>
								<tr> 
                  <td width="30%" align="right"><bean:message key="page.number.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="warehouse" property="number" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.name.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="warehouse" property="name" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.location.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<logic:present name="warehouse" property="location">
										<bean:write name="warehouse" property="location.name" scope="request"/>
										</logic:present>
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.customer.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<logic:present name="warehouse" property="customer">
										<bean:write name="warehouse" property="customer.company" scope="request"/>
										</logic:present>
                  </td>
                </tr>
               	<tr> 
                  <td width="30%" align="right"><bean:message key="page.description.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="warehouse" property="description" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.createBy.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><logic:present name="warehouse" property="createBy">
                  	<bean:write name="warehouse" property="createBy.userName" scope="request"/>
                  	</logic:present></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.createOn.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="warehouse" property="formatedCreateOn" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.changeBy.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><logic:present name="warehouse" property="changeBy">
                  	<bean:write name="warehouse" property="changeBy.userName" scope="request"/>
                  	</logic:present></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.changeOn.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="warehouse" property="formatedChangeOn" scope="request"/></td>
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
							<table border="0" cellspacing=1 cellpadding=0 bgcolor="#cccccc" width="100%">
                <tr align="center"> 
                  <td width="5%" class=titleField>&nbsp;</td>
                  <td class=titleField><bean:message key="page.item.title"/></td>
                  <td class=titleField><bean:message key="page.quantity.title"/></td>
                  <td class=titleField><bean:message key="page.itemUnit.title"/></td>
                </tr>
<%
int i = 0;
%>
								<logic:present name="warehouse" property="inventories" scope="request">
                <logic:iterate id="warehouseInventoryFK" name="warehouse" property="inventories" type="com.mpe.financial.model.WarehouseInventoryFK">
                <tr bgcolor="#FFFFFF" align=center>
                	<td><%=++i%>.</td>
									<td>
										<logic:present name="warehouseInventoryFK" property="inventory">
										<bean:write name="warehouseInventoryFK" property="inventory.name" scope="page"/>
										</logic:present>
									</td>
									<td><bean:write name="warehouseInventoryFK" property="currentStock" scope="page"/></td>
									<td>
										<logic:present name="warehouseInventoryFK" property="inventory.itemUnit">
										<bean:write name="warehouseInventoryFK" property="inventory.itemUnit.name" scope="page"/>
										</logic:present>
									</td>
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
    			<tr>
    				<td align="center">
							<input class="Button" type="button" name="edit" onclick="window.location.href=('<html:rewrite page="/warehouse/form.do"/>?warehouseId=<bean:write name="warehouse" property="id"/>');" value="<bean:message key="page.edit.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="delete" onclick="window.location.href=('<html:rewrite page="/warehouse/delete.do"/>?warehouseId=<bean:write name="warehouse" property="id"/>');" value="<bean:message key="page.delete.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="back" onclick="window.location.href=('<html:rewrite page="/warehouse/list.do"/>?start=<bean:write name="start" scope="session"/>&count=<bean:write name="count" scope="session"/>');" value="<bean:message key="page.back.link"/>"/>
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








