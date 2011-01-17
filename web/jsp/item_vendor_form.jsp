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
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.itemTitle.title"/></TD></TR>
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
    			<html:form action="/item/vendorSave" enctype="multipart/form-data">
					<html:hidden property="itemId"/>
					<html:hidden property="createBy"/>
					<html:hidden property="changeBy"/>
					<html:hidden property="createOn"/>
					<html:hidden property="changeOn"/>
					<html:hidden property="subaction"/>
          <tr> 
            <td align="center"> 
              <table width="100%" border="0" cellspacing="1" cellpadding="0" class=abuabu>
                <logic:present name="inventory">
								<tr> 
                  <td width="30%" align="right"><bean:message key="page.parent.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<logic:present name="inventory" property="parent">
										<bean:write name="inventory" property="parent.name"/>
										</logic:present>
                  </td>
                </tr>
								<tr> 
                  <td width="30%" align="right"><bean:message key="page.itemCategory.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<logic:present name="inventory" property="itemCategory">
										<bean:write name="inventory" property="itemCategory.name"/>
										</logic:present>
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.type.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="inventory" property="type"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.code.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="inventory" property="code"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.name.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="inventory" property="name"/></td>
                </tr>
								<tr> 
                  <td width="30%" align="right"><bean:message key="page.isActive.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="inventory" property="active"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.description.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="inventory" property="description"/></td>
                </tr>
								</logic:present>
                <tr> 
                  <td colspan="3">&nbsp;</td>
                </tr>
                <tr> 
                  <td colspan="3" align="center">
										<table border="0" cellspacing=1 cellpadding=0 bgcolor="#dddddd" width="100%">
											<tr align="center"> 
			                  <td width="5%" class=titleField>&nbsp;</td>
			                  <td class=titleField><bean:message key="page.vendor.title"/></td>
			                  <td class=titleField><bean:message key="page.costPrice.title"/></td>
			                  <td class=titleField><bean:message key="page.currency.title"/></td>
												<td class=titleField><bean:message key="page.purchaseTax.title"/></td>
			                  <td class=titleField>&nbsp;</td>
			                </tr>
			                <% int k=0; %>
			                <logic:present name="itemVendorsLst">
			                <logic:iterate id="itemVendor" name="itemVendorsLst" type="com.mpe.financial.model.ItemVendor" scope="request">
			                <tr bgcolor="#FFFFFF" align=center> 
			                  <td><%= ++k%></td>
			                  <td>
			                  	<logic:present name="itemVendor" property="id.vendor">
			                  	<bean:write name="itemVendor" property="id.vendor.company" scope="page"/>
			                  	</logic:present>
			                  </td>
			                  <td><bean:write name="itemVendor" property="formatedCostPrice" scope="page"/></td>
			                  <td>
			                  	<logic:present name="itemVendor" property="currency">
			                  	<bean:write name="itemVendor" property="currency.name" scope="page"/>
			                  	</logic:present>
			                  </td>
												<td>
			                  	<logic:present name="itemVendor" property="purchaseTax">
			                  	<bean:write name="itemVendor" property="purchaseTax.name" scope="page"/>
			                  	</logic:present>
			                  </td>
			                  <td>
			                  	<a href="<html:rewrite page="/item/vendorForm.do"/>?itemId=<bean:write name="itemForm" property="itemId"/>&vendorId=<bean:write name="itemVendor" property="id.vendor.id"/>"><html:img page="/images/edit.gif" border="0" alt="Edit"/></a>&nbsp;
			                  	<a href="<html:rewrite page="/item/vendorForm.do"/>?itemId=<bean:write name="itemForm" property="itemId"/>&vendorId=<bean:write name="itemVendor" property="id.vendor.id"/>&subaction=REMOVEITEMVENDOR&caller=/item/vendorForm.do"><html:img page="/images/trash.gif" border="0" alt="Remove"/></a>
			                  </td>
			                </tr>
			                </logic:iterate>
			                </logic:present>
			                <tr>
			                	<td>&nbsp;</td>
			                	<td>
			                		<html:select property="vendorId">
														<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
														<html:options collection="vendorLst" property="id" labelProperty="company" />
			                  	</html:select>
			                	</td>
			                	<td><html:text property="itemVendorPrice" size="20"/></td>
			                	<td>
			                  	<html:select property="itemVendorCurrencyId">
														<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
														<html:options collection="currencyLst" property="id" labelProperty="name" />
			                  	</html:select>
			                  </td>
												<td>
			                  	<html:select property="purchaseTaxId">
														<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
														<html:options collection="purchaseTaxLst" property="id" labelProperty="name" />
			                  	</html:select>
			                  </td>
			                 	<td>
			                 		<html:submit styleClass="button" onclick="this.form.subaction.value='ADDITEMVENDOR'">ADD</html:submit>
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
    			<tr>
    				<td align="center">
							<html:submit styleClass="button" onclick="this.form.subaction.value='SAVEALL'"><bean:message key="page.submit.link"/></html:submit>
							<html:cancel styleClass="button" onclick="bCancel=true;"><bean:message key="page.cancel.link"/></html:cancel>
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