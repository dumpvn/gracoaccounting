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
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.customerReturTitle.title"/></TD></TR>
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
    			<html:form action="/customerRetur/save">
					<html:hidden property="customerReturId"/>
					<html:hidden property="createBy"/>
					<html:hidden property="changeBy"/>
					<html:hidden property="createOn"/>
					<html:hidden property="changeOn"/>
					<html:hidden property="subaction"/>
					<html:hidden property="salesOrderId"/>
					<html:hidden property="customerReturDetailCurrencyId"/>
			    <html:hidden property="exchangeRate"/>
			    <html:hidden property="costPrice"/>
			    <html:hidden property="costPriceExchangeRate"/>
			    <html:hidden property="discountProcent"/>
			    <html:hidden property="costPriceCurrencyId"/>
          <tr>
            <td align="center"> 
              <table width="100%" border="0" cellspacing="1" cellpadding="0" class=abuabu>
              	<tr valign="top">
              		<td width="45%" valign="top">
              			<table width="100%" border="0" cellspacing="1" cellpadding="0" class=abuabu>
              				<logic:present name="locationLst">
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.location.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<html:select property="locationId">
														<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
														<html:options collection="locationLst" property="id" labelProperty="name" />
			                  	</html:select>
			                  </td>
			                </tr>
			                </logic:present>
              				<logic:equal name="customerReturForm" property="customerReturId" value="0">
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.number.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><html:text property="number" size="20"/></td>
			                </tr>
			                </logic:equal>
			                <logic:greaterThan name="customerReturForm" property="customerReturId" value="0">
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.number.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><html:text property="number" readonly="true" size="20"/></td>
			                </tr>
			                </logic:greaterThan>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.returDate.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><html:text property="returDate" size="12"/>&nbsp;(dd/MM/yyyy)</td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.reference.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><html:text property="reference" size="50"/></td>
			                </tr>
			                <logic:present name="customerLst">
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.customer.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<html:select property="customerId" onchange="this.form.subaction.value='refresh';this.form.submit();">
														<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
														<html:options collection="customerLst" property="id" labelProperty="company" />
			                  	</html:select>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.customerAlias.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<html:select property="customerAliasId">
														<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
														<html:options collection="customerAliasLst" property="id" labelProperty="company" />
			                  	</html:select>
			                  </td>
			                </tr>
			                </logic:present>
											<logic:present name="deliveryOrderLst">
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.deliveryOrder.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<html:select property="deliveryOrderId" onchange="this.form.subaction.value='form';this.form.submit();">
														<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
														<html:options collection="deliveryOrderLst" property="id" labelProperty="number" />
			                  	</html:select>
			                  </td>
			                </tr>
			                </logic:present>
              			</table>
              		</td>
              		<td width="10%">&nbsp;</td>
              		<td width="45%" valign="top">
              			<table width="100%" border="0" cellspacing="1" cellpadding="0" class=abuabu>
              				<tr> 
			                  <td width="30%" align="right"><bean:message key="page.project.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="customerRetur" property="project">
													<bean:write name="customerRetur" property="project.name"/>
													</logic:present>
			                  </td>
			                </tr>
											<tr> 
			                  <td width="30%" align="right"><bean:message key="page.department.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="customerRetur" property="department">
													<bean:write name="customerRetur" property="department.name"/>
													</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.note.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><html:textarea property="note" cols="40" rows="5"/></td>
			                </tr>
              			</table>
              		</td>
              	</tr>
                
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
			                  <td class=titleField><bean:message key="page.item.title"/></td>
			                  <td class=titleField><bean:message key="page.price.title"/></td>
			                  <td class=titleField><bean:message key="page.quantity.title"/></td>
												<td class=titleField><bean:message key="page.warehouse.title"/></td>
			                  <td class=titleField><bean:message key="page.discount.title"/></td>
												<td class=titleField><bean:message key="page.tax.title"/></td>
			                  <td class=titleField><bean:message key="page.price.title"/>x<bean:message key="page.quantity.title"/></td>
												<td class=titleField><bean:message key="page.description.title"/></td>
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
											<logic:present name="customerReturDetailLst">
			                <logic:iterate id="customerReturDetail" name="customerReturDetailLst" type="com.mpe.financial.model.CustomerReturDetail">
			                <tr bgcolor="#FFFFFF" align=center>
			                	<td><%=++i%>.</td>
												<td>
													<logic:present name="customerReturDetail" property="id.item">
													<bean:write name="customerReturDetail" property="id.item.name" scope="page"/>
													</logic:present>
												</td>
												<td><bean:write name="customerReturDetail" property="formatedPrice" scope="page"/>
													<!--
													<logic:present name="customerReturDetail" property="currency">
													<bean:write name="customerReturDetail" property="currency.name" scope="page"/>
													</logic:present>-->
												</td>
												<td><bean:write name="customerReturDetail" property="quantity" scope="page"/>&nbsp;
													<logic:present name="customerReturDetail" property="itemUnit">
													<bean:write name="customerReturDetail" property="itemUnit.name" scope="page"/>
													</logic:present>
												</td>
												<td>
													<logic:present name="customerReturDetail" property="warehouse">
													<bean:write name="customerReturDetail" property="warehouse.name" scope="page"/>
													</logic:present>
												</td>
												<td><bean:write name="customerReturDetail" property="formatedDiscountAmount" scope="page"/>/<bean:write name="customerReturDetail" property="discountProcent" scope="page"/>%</td>
												<td>
													<logic:present name="customerReturDetail" property="tax">
													<bean:write name="customerReturDetail" property="tax.name" scope="page"/>
													</logic:present>&nbsp;<i>(<bean:write name="customerReturDetail" property="taxAmount"/>%)</i>
												</td>
												<td align="right">
													<!--
													<logic:present name="customerRetur" property="deliveryOrder">
													<logic:present name="customerRetur" property="deliveryOrder.salesOrder">
													<logic:present name="customerRetur" property="deliveryOrder.salesOrder.currency">
													<bean:write name="customerRetur" property="deliveryOrder.salesOrder.currency.symbol"/>&nbsp;
													</logic:present>
													</logic:present>
													</logic:present>
													-->
													<bean:write name="customerReturDetail" property="formatedPriceQuantity" scope="page"/>
												</td>
												<td><bean:write name="customerReturDetail" property="description" scope="page"/></td>
			                  <td align="center">
													<a href="<html:rewrite page="/customerRetur/form.do"/>?customerReturId=<bean:write name="customerReturForm" property="customerReturId"/>&itemId=<bean:write name="customerReturDetail" property="id.item.id"/>"><html:img page="/images/edit.gif" border="0" alt="Edit"/></a>&nbsp;
			                  	<a href="<html:rewrite page="/customerRetur/form.do"/>?customerReturId=<bean:write name="customerReturForm" property="customerReturId"/>&itemId=<bean:write name="customerReturDetail" property="id.item.id"/>&subaction=REMOVECUSTOMERRETURDETAIL&caller=/customerRetur/form.do"><html:img page="/images/trash.gif" border="0" alt="Remove"/></a>
			                  </td>
			                </tr>
			                </logic:iterate>
			                </logic:present>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="7"><b><bean:message key="page.total.title"/></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="customerRetur" property="deliveryOrder">
													<logic:present name="customerRetur" property="deliveryOrder.salesOrder">
													<logic:present name="customerRetur" property="deliveryOrder.salesOrder.currency">
													<bean:write name="customerRetur" property="deliveryOrder.salesOrder.currency.symbol"/>&nbsp;
													</logic:present>
													</logic:present>
													</logic:present>
													-->
			                		<bean:write name="customerReturDetailAmount"/></b></td>
			                	<td colspan="2">&nbsp;</td>
			                </tr>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="7"><b><bean:message key="page.discount.title"/>&nbsp;<logic:present name="customerRetur" property="deliveryOrder"><logic:present name="customerRetur" property="deliveryOrder.salesOrder">(<bean:write name="customerRetur" property="deliveryOrder.salesOrder.discountProcent"/>%)</logic:present></logic:present></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="customerRetur" property="deliveryOrder">
													<logic:present name="customerRetur" property="deliveryOrder.salesOrder">
													<logic:present name="customerRetur" property="deliveryOrder.salesOrder.currency">
													<bean:write name="customerRetur" property="deliveryOrder.salesOrder.currency.symbol"/>&nbsp;
													</logic:present>
													</logic:present>
													</logic:present>
													-->
			                		<bean:write name="amountDiscount"/></b></td>
			                	<td colspan="2">&nbsp;</td>
			                </tr>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="7"><b><bean:message key="page.subtotal.title"/></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="customerRetur" property="deliveryOrder">
													<logic:present name="customerRetur" property="deliveryOrder.salesOrder">
													<logic:present name="customerRetur" property="deliveryOrder.salesOrder.currency">
													<bean:write name="customerRetur" property="deliveryOrder.salesOrder.currency.symbol"/>&nbsp;
													</logic:present>
													</logic:present>
													</logic:present>
													-->
			                		<bean:write name="amountAfterDiscount"/></b></td>
			                	<td colspan="2">&nbsp;</td>
			                </tr>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="7"><b><bean:message key="page.tax.title"/>&nbsp;<logic:present name="customerRetur" property="deliveryOrder"><logic:present name="customerRetur" property="deliveryOrder.salesOrder">(<bean:write name="customerRetur" property="deliveryOrder.salesOrder.taxAmount"/>%)</logic:present></logic:present></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="customerRetur" property="deliveryOrder">
													<logic:present name="customerRetur" property="deliveryOrder.salesOrder">
													<logic:present name="customerRetur" property="deliveryOrder.salesOrder.currency">
													<bean:write name="customerRetur" property="deliveryOrder.salesOrder.currency.symbol"/>&nbsp;
													</logic:present>
													</logic:present>
													</logic:present>
													-->
			                		<bean:write name="amountTax"/></b></td>
			                	<td colspan="2">&nbsp;</td>
			                </tr>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="7"><b><bean:message key="page.total.title"/></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="customerRetur" property="deliveryOrder">
													<logic:present name="customerRetur" property="deliveryOrder.salesOrder">
													<logic:present name="customerRetur" property="deliveryOrder.salesOrder.currency">
													<bean:write name="customerRetur" property="deliveryOrder.salesOrder.currency.symbol"/>&nbsp;
													</logic:present>
													</logic:present>
													</logic:present>
													-->
			                		<bean:write name="amountAfterTaxAndDiscount"/></b></td>
			                	<td colspan="2">&nbsp;</td>
			                </tr>
			                <tr>
			                	<td>&nbsp;</td>
			                	<td>
			                		<html:hidden property="itemId"/>
			                		<html:text property="itemCode" size="30" readonly="true"/>
			                	</td>
			                	<td><html:text property="price" size="20" readonly="true"/></td>
			                	<td><html:text property="quantity" size="20"/>&nbsp;
			                		<html:select property="itemUnitId">
														<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
														<html:options collection="itemUnitLst" property="id" labelProperty="name" />
			                  	</html:select>
													<html:hidden property="taxDetailId"/>
													<html:hidden property="taxDetailAmount"/>
			                	</td>
												<td><html:hidden property="warehouseId"/></td>
			                	<td><html:text property="itemDiscountAmount" size="8" readonly="true"/>/<html:text property="itemDiscountProcent" size="3" readonly="true"/>%</td>
			                	<td>&nbsp;</td>
			                	<td>&nbsp;</td>
												<td><html:text property="itemDescription" size="20"/></td>
			                	<td><html:submit styleClass="button" onclick="this.form.subaction.value='ADDCUSTOMERRETURDETAIL'">ADD</html:submit></td>
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
							<html:submit styleClass="button" onclick="this.form.subaction.value='SAVEALL'"><bean:message key="page.submit.link"/></html:submit>
							<html:cancel styleClass="button" onclick="this.form.subaction.value='';bCancel=true;"><bean:message key="page.cancel.link"/></html:cancel>
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