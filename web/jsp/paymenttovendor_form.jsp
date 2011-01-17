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
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.paymentToVendorTitle.title"/></TD></TR>
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
    			<html:form action="/paymentToVendor/save">
					<html:hidden property="paymentToVendorId"/>
					<html:hidden property="createBy"/>
					<html:hidden property="changeBy"/>
					<html:hidden property="createOn"/>
					<html:hidden property="changeOn"/>
					<html:hidden property="subaction"/>
					<html:hidden property="vendorBillExchangeRate"/>
          <tr>
            <td align="center"> 
              <table width="100%" border="0" cellspacing="1" cellpadding="0" class=abuabu>
                <tr>
              		<td width="45%">
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
			                <logic:equal name="paymentToVendorForm" property="paymentToVendorId" value="0">
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.number.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><html:text property="number" size="20"/></td>
			                </tr>
			                </logic:equal>
			                <logic:greaterThan name="paymentToVendorForm" property="paymentToVendorId" value="0">
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
              		<td width="10%"></td>
              		<td width="45%">
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
			                <logic:present name="vendorLst">
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.vendor.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<html:select property="vendorId" onchange="this.form.subaction.value='form';this.form.submit();">
														<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
														<html:options collection="vendorLst" property="id" labelProperty="company" />
			                  	</html:select>
			                  </td>
			                </tr>
			                </logic:present>
			                <!--
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.reference.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><html:text property="reference" size="50"/></td>
			                </tr>
			                -->
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
			                  <td class=titleField><bean:message key="page.vendorBill.title"/></td>
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
											<logic:present name="paymentToVendorDetailLst">
			                <logic:iterate id="paymentToVendorDetail" name="paymentToVendorDetailLst" type="com.mpe.financial.model.PaymentToVendorDetail">
			                <tr bgcolor="#FFFFFF" align=center>
			                	<td><%=++i%>.</td>
												<td>
													<logic:present name="paymentToVendorDetail" property="id.vendorBill">
													<bean:write name="paymentToVendorDetail" property="id.vendorBill.number" scope="page"/>
													</logic:present>
												</td>
												<td>
													<i>(<bean:write name="paymentToVendorDetail" property="id.vendorBill.formatedExchangeRate" scope="page"/>)</i>&nbsp;
													<logic:present name="paymentToVendorDetail" property="id.vendorBill">
													<logic:present name="paymentToVendorDetail" property="id.vendorBill.currency">
													<bean:write name="paymentToVendorDetail" property="id.vendorBill.currency.symbol"/>
													</logic:present>
													</logic:present>
													<bean:write name="paymentToVendorDetail" property="formatedVendorBillAmount" scope="page"/>
												</td>
												<td align="right">
													<i>(<bean:write name="paymentToVendorDetail" property="formatedVendorBillExchangeRate" scope="page"/>)</i>&nbsp;
													<logic:present name="paymentToVendor" property="currency">
													<bean:write name="paymentToVendor" property="currency.symbol"/>&nbsp;
													</logic:present>
													<bean:write name="paymentToVendorDetail" property="formatedPaymentAmount" scope="page"/>
													
												</td>
			                  <td align="center">
													<a href="<html:rewrite page="/paymentToVendor/form.do"/>?paymentToVendorId=<bean:write name="paymentToVendorForm" property="paymentToVendorId"/>&vendorBillId=<bean:write name="paymentToVendorDetail" property="id.vendorBill.id"/>"><html:img page="/images/edit.gif" border="0" alt="Edit"/></a>&nbsp;
			                  	<a href="<html:rewrite page="/paymentToVendor/form.do"/>?paymentToVendorId=<bean:write name="paymentToVendorForm" property="paymentToVendorId"/>&vendorBillId=<bean:write name="paymentToVendorDetail" property="id.vendorBill.id"/>&subaction=REMOVEVENDORBILL&caller=/paymentToVendor/form.do"><html:img page="/images/trash.gif" border="0" alt="Remove"/></a>
			                  </td>
			                </tr>
			                </logic:iterate>
			                </logic:present>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="3"><b><bean:message key="page.subtotal.title"/></b></td>
			                	<td align="right"><b>
			                		<logic:present name="paymentToVendor" property="currency">
													<bean:write name="paymentToVendor" property="currency.symbol"/>&nbsp;
													</logic:present>
													<bean:write name="paymentToVendorDetailAmount"/></b></td>
			                	<td>&nbsp;</td>
			                </tr>
			                <tr>
			                	<td>&nbsp;<html:hidden property="vendorBillId"/><html:hidden property="vendorBillAmount"/></td>
			                	<td><logic:present name="vendorBill"><bean:write name="vendorBill" property="number"/></logic:present></td>
			                	<td><logic:present name="paymentToVendorForm" property="vendorBillAmount"><bean:write name="paymentToVendorForm" property="vendorBillAmount"/></logic:present></td>
			                	<td><html:text property="paymentAmount" size="20"/></td>
			                	<td><html:submit styleClass="button" onclick="this.form.subaction.value='ADDVENDORBILL'">ADD</html:submit></td>
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
			                  <td class=titleField><bean:message key="page.returToVendor.title"/></td>
			                  <td class=titleField><bean:message key="page.amount.title"/></td>
			                  <td width="10%" class=titleField>&nbsp;</td>
			                </tr>
			<%
			int j = 0;
			%>
											<logic:present name="returToVendorLst">
			                <logic:iterate id="returToVendor" name="returToVendorLst" type="com.mpe.financial.model.ReturToVendor">
			                <tr bgcolor="#FFFFFF" align=center>
			                	<td><%=++j%>.</td>
												<td>
													<logic:present name="returToVendor" property="number">
													<bean:write name="returToVendor" property="number" scope="page"/>
													</logic:present>
												</td>
												<td align="right">
													<logic:present name="returToVendor" property="receiving">
													<logic:present name="returToVendor" property="receiving.purchaseOrder">
													<logic:present name="returToVendor" property="receiving.purchaseOrder.currency">
													<bean:write name="returToVendor" property="receiving.purchaseOrder.currency.symbol" scope="page"/>&nbsp;
													</logic:present>
													</logic:present>
													</logic:present>
													<bean:write name="returToVendor" property="formatedAmountAfterTaxAndDiscount" scope="page"/>
												</td>
			                  <td align="center">
			                  	<a href="<html:rewrite page="/paymentToVendor/form.do"/>?paymentToVendorId=<bean:write name="paymentToVendorForm" property="paymentToVendorId"/>&returToVendorId=<bean:write name="returToVendor" property="id"/>&subaction=REMOVERETURTOVENDOR&caller=/paymentToVendor/form.do"><html:img page="/images/trash.gif" border="0" alt="Remove"/></a>
			                  </td>
			                </tr>
			                </logic:iterate>
			                </logic:present>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="2"><b><bean:message key="page.subtotal.title"/></b></td>
			                	<td align="right"><b>
			                		<logic:present name="paymentToVendor" property="currency">
													<bean:write name="paymentToVendor" property="currency.symbol"/>&nbsp;
													</logic:present>
													<bean:write name="returToVendorAmount"/></b></td>
			                	<td>&nbsp;</td>
			                </tr>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="2"><b><bean:message key="page.total.title"/></b></td>
			                	<td align="right"><b>
			                		<logic:present name="paymentToVendor" property="currency">
													<bean:write name="paymentToVendor" property="currency.symbol"/>&nbsp;
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