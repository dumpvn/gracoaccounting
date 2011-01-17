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
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.customerPaymentTitle.title"/></TD></TR>
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
    			<html:form action="/customerPayment/save">
					<html:hidden property="customerPaymentId"/>
					<html:hidden property="createBy"/>
					<html:hidden property="changeBy"/>
					<html:hidden property="createOn"/>
					<html:hidden property="changeOn"/>
					<html:hidden property="subaction"/>
					<html:hidden property="invoiceExchangeRate"/>
					<html:hidden property="invoiceId"/>
					<html:hidden property="invoiceSimpleId"/>
					<html:hidden property="invoicePolosId"/>
					<html:hidden property="invoiceAmount"/>
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
			                <logic:equal name="customerPaymentForm" property="customerPaymentId" value="0">
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.number.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><html:text property="number" size="20"/></td>
			                </tr>
			                </logic:equal>
			                <logic:greaterThan name="customerPaymentForm" property="customerPaymentId" value="0">
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.number.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><html:text property="number" readonly="true" size="20"/></td>
			                </tr>
			                </logic:greaterThan>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.paymentDate.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><html:text property="paymentDate" size="12"/>&nbsp;(dd/MM/yyyy)</td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.method.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<html:radio property="method" value="Giro"/>&nbsp;Giro&nbsp;
			                  	<html:radio property="method" value="Transfer"/>&nbsp;Transfer&nbsp;
			                  	<html:radio property="method" value="Cash"/>&nbsp;Cash&nbsp;
			                  </td>
			                </tr>
			                <logic:present name="bankAccountLst">
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.bankAccount.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<html:select property="bankAccountId">
														<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
														<html:options collection="bankAccountLst" property="id" labelProperty="numberName" />
			                  	</html:select>
			                  </td>
			                </tr>
			                </logic:present>
			                <logic:present name="projectLst">
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.project.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<html:select property="projectId">
														<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
														<html:options collection="projectLst" property="id" labelProperty="name" />
			                  	</html:select>
			                  </td>
			                </tr>
			                </logic:present>
			                <logic:present name="chartOfAccountLst">
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.exceedAccount.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<html:select property="exceedAccountId">
														<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
														<html:options collection="chartOfAccountLst" property="id" labelProperty="numberName" />
			                  	</html:select>
			                  </td>
			                </tr>
			                </logic:present>
              			</table>
              		</td>
              		<td width="10%">&nbsp;</td>
              		<td width="45%" valign="top">
              			<table width="100%" border="0" cellspacing="1" cellpadding="0" class=abuabu>
              				<logic:present name="currencyLst">
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.currency.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<html:select property="currencyId">
														<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
														<html:options collection="currencyLst" property="id" labelProperty="name" />
			                  	</html:select>
			                  </td>
			                </tr>
			                </logic:present>
			                <logic:present name="customerLst">
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.customer.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<html:select property="customerId" onchange="this.form.subaction.value='form';this.form.submit();">
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
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.reference.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><html:text property="reference" size="50"/></td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.description.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><html:textarea property="description" cols="40" rows="4"/></td>
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
			                  <td class=titleField><bean:message key="page.invoice.title"/></td>
			                  <td class=titleField><bean:message key="page.amount.title"/></td>
			                  <td class=titleField><bean:message key="page.paymentAmount.title"/></td>
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
											<logic:present name="customerPaymentDetailLst">
			                <logic:iterate id="customerPaymentDetail" name="customerPaymentDetailLst" type="com.mpe.financial.model.CustomerPaymentDetail">
			                <tr bgcolor="#FFFFFF" align=center>
			                	<td><%=++i%>.</td>
												<td>
													<logic:present name="customerPaymentDetail" property="invoice">
													<bean:write name="customerPaymentDetail" property="invoice.number" scope="page"/>
													</logic:present>
													<logic:present name="customerPaymentDetail" property="invoiceSimple">
													<bean:write name="customerPaymentDetail" property="invoiceSimple.number" scope="page"/>
													</logic:present>
													<logic:present name="customerPaymentDetail" property="invoicePolos">
													<bean:write name="customerPaymentDetail" property="invoicePolos.number" scope="page"/>
													</logic:present>
												</td>
												<td>
													<logic:present name="customerPaymentDetail" property="invoice">
													<logic:present name="customerPaymentDetail" property="invoice.currency">
													<i>(<bean:write name="customerPaymentDetail" property="invoice.formatedExchangeRate" scope="page"/>)</i>&nbsp;
													<bean:write name="customerPaymentDetail" property="invoice.currency.symbol" scope="page"/>
													</logic:present>
													</logic:present>
													<logic:present name="customerPaymentDetail" property="invoiceSimple">
													<logic:present name="customerPaymentDetail" property="invoiceSimple.currency">
													<i>(<bean:write name="customerPaymentDetail" property="invoiceSimple.formatedExchangeRate" scope="page"/>)</i>&nbsp;
													<bean:write name="customerPaymentDetail" property="invoiceSimple.currency.symbol" scope="page"/>
													</logic:present>
													</logic:present>
													<logic:present name="customerPaymentDetail" property="invoicePolos">
													<logic:present name="customerPaymentDetail" property="invoicePolos.currency">
													<i>(<bean:write name="customerPaymentDetail" property="invoicePolos.formatedExchangeRate" scope="page"/>)</i>&nbsp;
													<bean:write name="customerPaymentDetail" property="invoicePolos.currency.symbol" scope="page"/>
													</logic:present>
													</logic:present>
													<bean:write name="customerPaymentDetail" property="formatedInvoiceAmount" scope="page"/>
												</td>
												<td align="right">
													<i>(<bean:write name="customerPaymentDetail" property="formatedInvoiceExchangeRate" scope="page"/>)</i>&nbsp;
													<logic:present name="customerPayment" property="currency">
													<bean:write name="customerPayment" property="currency.symbol"/>&nbsp;
													</logic:present>
													<bean:write name="customerPaymentDetail" property="formatedPaymentAmount" scope="page"/>
												</td>
			                  <td align="center">
			                  	<logic:present name="customerPaymentDetail" property="invoice">
														<a href="<html:rewrite page="/customerPayment/form.do"/>?customerPaymentId=<bean:write name="customerPaymentForm" property="customerPaymentId"/>&invoiceId=<bean:write name="customerPaymentDetail" property="invoice.id"/>"><html:img page="/images/edit.gif" border="0" alt="Edit"/></a>&nbsp;
				                  	<a href="<html:rewrite page="/customerPayment/form.do"/>?customerPaymentId=<bean:write name="customerPaymentForm" property="customerPaymentId"/>&invoiceId=<bean:write name="customerPaymentDetail" property="invoice.id"/>&subaction=REMOVEINVOICE&caller=/customerPayment/form.do"><html:img page="/images/trash.gif" border="0" alt="Remove"/></a>
			                  	</logic:present>
			                  	<logic:present name="customerPaymentDetail" property="invoiceSimple">
														<a href="<html:rewrite page="/customerPayment/form.do"/>?customerPaymentId=<bean:write name="customerPaymentForm" property="customerPaymentId"/>&invoiceSimpleId=<bean:write name="customerPaymentDetail" property="invoiceSimple.id"/>"><html:img page="/images/edit.gif" border="0" alt="Edit"/></a>&nbsp;
				                  	<a href="<html:rewrite page="/customerPayment/form.do"/>?customerPaymentId=<bean:write name="customerPaymentForm" property="customerPaymentId"/>&invoiceSimpleId=<bean:write name="customerPaymentDetail" property="invoiceSimple.id"/>&subaction=REMOVEINVOICE&caller=/customerPayment/form.do"><html:img page="/images/trash.gif" border="0" alt="Remove"/></a>
			                  	</logic:present>
			                  	<logic:present name="customerPaymentDetail" property="invoicePolos">
														<a href="<html:rewrite page="/customerPayment/form.do"/>?customerPaymentId=<bean:write name="customerPaymentForm" property="customerPaymentId"/>&invoicePolosId=<bean:write name="customerPaymentDetail" property="invoicePolos.id"/>"><html:img page="/images/edit.gif" border="0" alt="Edit"/></a>&nbsp;
				                  	<a href="<html:rewrite page="/customerPayment/form.do"/>?customerPaymentId=<bean:write name="customerPaymentForm" property="customerPaymentId"/>&invoicePolosId=<bean:write name="customerPaymentDetail" property="invoicePolos.id"/>&subaction=REMOVEINVOICE&caller=/customerPayment/form.do"><html:img page="/images/trash.gif" border="0" alt="Remove"/></a>
			                  	</logic:present>
			                  </td>
			                </tr>
			                </logic:iterate>
			                </logic:present>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="3"><b><bean:message key="page.subtotal.title"/></b></td>
			                	<td align="right"><b>
			                		<logic:present name="customerPayment" property="currency">
													<bean:write name="customerPayment" property="currency.symbol"/>&nbsp;
													</logic:present>
													<bean:write name="customerPaymentDetailAmount"/></b></td>
			                	<td>&nbsp;</td>
			                </tr>
			                <tr>
			                	<td>&nbsp;</td>
			                	<td>
			                		<logic:present name="invoice"><bean:write name="invoice" property="number"/></logic:present>
			                	</td>
			                	<td><logic:present name="customerPaymentForm" property="invoiceAmount"><bean:write name="customerPaymentForm" property="invoiceAmount"/></logic:present></td>
			                	<td><html:text property="paymentAmount" size="20"/></td>
			                	<td><html:submit styleClass="button" onclick="this.form.subaction.value='ADDINVOICE'">ADD</html:submit></td>
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
			                  <td class=titleField><bean:message key="page.customerRetur.title"/></td>
			                  <td class=titleField><bean:message key="page.amount.title"/></td>
			                  <td width="10%" class=titleField>&nbsp;</td>
			                </tr>
			<%
			int j = 0;
			%>
											<logic:present name="customerReturLst">
			                <logic:iterate id="customerRetur" name="customerReturLst" type="com.mpe.financial.model.CustomerRetur">
			                <tr bgcolor="#FFFFFF" align=center>
			                	<td><%=++j%>.</td>
												<td>
													<logic:present name="customerRetur" property="number">
													<bean:write name="customerRetur" property="number" scope="page"/>
													</logic:present>
												</td>
												<td align="right">
													<logic:present name="customerRetur" property="deliveryOrder">
													<logic:present name="customerRetur" property="deliveryOrder.salesOrder">
													<logic:present name="customerRetur" property="deliveryOrder.salesOrder.currency">
													<bean:write name="customerRetur" property="deliveryOrder.salesOrder.currency.symbol" scope="page"/>&nbsp;
													</logic:present>
													</logic:present>
													<logic:notPresent name="customerRetur" property="deliveryOrder.salesOrder">
													<logic:present name="customerRetur" property="deliveryOrder.currency">
													<bean:write name="customerRetur" property="deliveryOrder.currency.symbol" scope="page"/>&nbsp;
													</logic:present>
													</logic:notPresent>
													</logic:present>
													<bean:write name="customerRetur" property="formatedAmountAfterTaxAndDiscount" scope="page"/>
												</td>
			                  <td align="center">
			                  	<a href="<html:rewrite page="/customerPayment/form.do"/>?customerPaymentId=<bean:write name="customerPaymentForm" property="customerPaymentId"/>&customerReturId=<bean:write name="customerRetur" property="id"/>&subaction=REMOVECUSTOMERRETUR&caller=/customerRetur/form.do"><html:img page="/images/trash.gif" border="0" alt="Remove"/></a>
			                  </td>
			                </tr>
			                </logic:iterate>
			                </logic:present>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="2"><b><bean:message key="page.subtotal.title"/></b></td>
			                	<td align="right"><b>
			                		<logic:present name="customerPayment" property="currency">
													<bean:write name="customerPayment" property="currency.symbol"/>&nbsp;
													</logic:present>
													<bean:write name="customerReturAmount"/></b></td>
			                	<td>&nbsp;</td>
			                </tr>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="2"><b><bean:message key="page.total.title"/></b></td>
			                	<td align="right"><b>
			                		<logic:present name="customerPayment" property="currency">
													<bean:write name="customerPayment" property="currency.symbol"/>&nbsp;
													</logic:present>
													<bean:write name="paymentAmount"/></b></td>
			                	<td>&nbsp;</td>
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