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
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.customerTitle.title"/></TD></TR>
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
    			<html:form action="/customer/addressSave">
					<html:hidden property="customerId"/>
					<html:hidden property="createBy"/>
					<html:hidden property="changeBy"/>
					<html:hidden property="createOn"/>
					<html:hidden property="changeOn"/>
					<html:hidden property="subaction"/>
          <tr> 
            <td align="center"> 
              <table width="100%" border="0" cellspacing="1" cellpadding="0" class=abuabu>
                <logic:present name="customer">
								<tr> 
                  <td width="30%" align="right"><bean:message key="page.company.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="customer" property="company" scope="request"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.code.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="customer" property="code" scope="request"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.npwp.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="customer" property="npwp" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.address.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="customer" property="address" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.city.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="customer" property="city" scope="request"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.areaCode.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="customer" property="areaCode" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.postalCode.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="customer" property="postalCode" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.province.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="customer" property="province" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.country.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="customer" property="country" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.telephone.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="customer" property="telephone" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.fax.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="customer" property="fax" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.email.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="customer" property="email" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.isActive.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="customer" property="active" scope="request"/></td>
                </tr>
								</logic:present>
              </table>
            </td>
          </tr>
					<tr>
          	<td>&nbsp;</td>
          </tr>
          <tr>
    				<td>
    					<table width="100%">
    						<tr bgcolor="#FFFFFF"> 
			            <td align="center"> 
			              <table border="0" cellspacing=1 cellpadding=0 bgcolor="#cccccc" width="100%">
			                <tr align="center"> 
			                  <td width="5%" class=titleField>&nbsp;</td>
			                  <td class=titleField><bean:message key="page.addressType.title"/></td>
			                  <td class=titleField><bean:message key="page.code.title"/></td>
			                  <td class=titleField><bean:message key="page.address.title"/></td>
			                  <td class=titleField><bean:message key="page.city.title"/></td>
			                  <td class=titleField><bean:message key="page.postalCode.title"/></td>
			                  <td width="10%" class=titleField>&nbsp;</td>
			                </tr>
			<%
			int i = 0;
			try {
			i = Integer.parseInt(request.getParameter("start"));
			}catch(Exception ex){
			i = 0;
			}
			%>
											<logic:present name="customerAddressLst">
			                <logic:iterate id="customerAddress" name="customerAddressLst" type="com.mpe.financial.model.CustomersAddress">
			                <tr bgcolor="#FFFFFF" align=center>
			                	<td><%=++i%>.</td>
												<td>
													<logic:match name="customerAddress" property="id.invoiceTaxAddress" value="Y">INVOICE TAX</logic:match>
													<logic:match name="customerAddress" property="id.invoiceAddress" value="Y">INVOICE</logic:match>
													<logic:match name="customerAddress" property="id.deliveryAddress" value="Y">DELIVERY</logic:match>
												</td>
												<td><bean:write name="customerAddress" property="code" scope="page"/></td>
												<td><bean:write name="customerAddress" property="address" scope="page"/></td>
												<td><bean:write name="customerAddress" property="city" scope="page"/></td>
												<td><bean:write name="customerAddress" property="postalCode" scope="page"/></td>
			                  <td align="center">
													<a href="<html:rewrite page="/customer/addressForm.do"/>?customerId=<bean:write name="customersForm" property="customerId"/>&addressType=<logic:match name="customerAddress" property="id.invoiceTaxAddress" value="Y">INVOICE TAX</logic:match><logic:match name="customerAddress" property="id.invoiceAddress" value="Y">INVOICE</logic:match><logic:match name="customerAddress" property="id.deliveryAddress" value="Y">DELIVERY</logic:match>"><html:img page="/images/edit.gif" border="0" alt="Edit"/></a>&nbsp;
			                  	<a href="<html:rewrite page="/customer/addressForm.do"/>?customerId=<bean:write name="customersForm" property="customerId"/>&addressType=<logic:match name="customerAddress" property="id.invoiceTaxAddress" value="Y">INVOICE TAX</logic:match><logic:match name="customerAddress" property="id.invoiceAddress" value="Y">INVOICE</logic:match><logic:match name="customerAddress" property="id.deliveryAddress" value="Y">DELIVERY</logic:match>&subaction=REMOVEDETAIL&caller=/customer/addressForm.do"><html:img page="/images/trash.gif" border="0" alt="Remove"/></a>
			                  </td>
			                </tr>
			                </logic:iterate>
			                </logic:present>
			                <tr>
			                	<td>&nbsp;</td>
			                	<td>
			                		<html:select property="addressType" styleClass="Data">
														<html:option value="INVOICE TAX">INVOICE TAX</html:option>
														<html:option value="INVOICE">INVOICE</html:option>
														<html:option value="DELIVERY">DELIVERY</html:option>
			                  	</html:select>
			                	</td>
			                	<td><html:text property="code" size="10"/></td>
			                	<td><html:text property="address" size="40"/></td>
			                	<td><html:text property="city" size="30"/></td>
			                	<td><html:text property="postalCode" size="8"/></td>
			                	<td><html:submit styleClass="button" onclick="this.form.subaction.value='ADDDETAIL'">ADD</html:submit></td>
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
    			<tr>
    				<td align="center">
							<html:submit styleClass="Button" onclick="this.form.subaction.value='SAVEALL'"><bean:message key="page.submit.link"/></html:submit>
							<html:cancel styleClass="Button" onclick="bCancel=true;"><bean:message key="page.cancel.link"/></html:cancel>
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

<script language="javascript">

function selectAll(col1){
	col1 = eval('document.forms[0].'+col1);
  for(i=0; i<col1.options.length; i++ ){
		col1.options[i].selected = true;
	}
}

function up(col1){
	col1 = eval('document.forms[0].'+col1);
  index = col1.selectedIndex;
  //alert(index);
  if (index == -1) {
  	alert ('You haven\'t selected any options!');
  } else {
	  if(index <= 0) {
	  	alert("Can't move to up!");
	  } else {
		  toMoveX = col1.options[index-1];
		  toMoveY = col1.options[index];
		  optX = new Option(toMoveX.text,toMoveX.value,false,false);
		  optY = new Option(toMoveY.text,toMoveY.value,false,false);
		  col1.options[index] = optX;
		  col1.options[index-1] = optY;
		  col1.selectedIndex = index-1;
		}
	}
}

function down(col1){
	col1 = eval('document.forms[0].'+col1);
  index = col1.selectedIndex;
  if (index == -1) {
  	alert ('You haven\'t selected any options!');
  } else {
	  if(index+1 >=  col1.options.length) {
	  	alert("Can't move to down!");
	 	} else {
		  toMoveX = col1.options[index];
		  toMoveY = col1.options[index+1];
		  optX = new Option(toMoveX.text,toMoveX.value,false,false);
		  optY = new Option(toMoveY.text,toMoveY.value,false,false);
		  col1.options[index] = optY;
		  col1.options[index+1] = optX;
		  col1.selectedIndex = index+1;
		}
	}
}

function copyToList(from,to) {
  fromList = eval('document.forms[0].' + from);
  toList = eval('document.forms[0].' + to);
  if (toList.options.length > 0 && toList.options[0].value == 'temp') {
    toList.options.length = 0;
  }
  var sel = false;
  for (i=0;i<fromList.options.length;i++) {
    var current = fromList.options[i];
    if (current.selected) {
      sel = true;
      if (current.value == 'temp') {
        alert ('You cannot move this text!');
        return;
      }
      txt = current.text;
      val = current.value;
      toList.options[toList.length] = new Option(txt,val);
      fromList.options[i] = null;
      i--;
    }
  }
  if (!sel) alert ('You haven\'t selected any options!');
}

function page_submit() {
	document.forms[0].submit();
};
</script>

