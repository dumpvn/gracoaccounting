<%
	//response.reset();
	response.setHeader("Pragma", "No-cache");
	response.setDateHeader("Expires", 0);
	response.setHeader("Cache-Control", "no-cache");
	response.setContentType((String)request.getAttribute("contentType"));
	out.println(request.getAttribute("image"));
	response.flushBuffer();
%>