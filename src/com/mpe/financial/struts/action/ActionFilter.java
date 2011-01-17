/*
 *  Created on May 11, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.mpe.financial.struts.action;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpe.common.CommonConstants;
import com.mpe.financial.model.UserStream;
import com.mpe.financial.model.Users;

import java.io.IOException;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ActionFilter implements Filter {
	Log log = LogFactory.getFactory().getInstance(this.getClass());
	
	protected FilterConfig filterConfig;
	
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	public void destroy() {
	}
	
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
		throws IOException, ServletException {
			HttpServletRequest request = (HttpServletRequest) req;
			//HttpServletResponse response = (HttpServletResponse) resp;
			if (request.getRequestURI().indexOf("home")!=-1) {
				//log.info("Home filter section! ");
				HttpSession session = request.getSession();
				Users users = (Users)session.getAttribute(CommonConstants.USER);
				UserStream userstream = (UserStream)session.getAttribute("userstream");
				if (userstream!=null && users!=null) {
					//log.info("update session userstream : "+session.getId()+" // "+user.getUserName());
					userstream.updateUser(users);
				}
			}
			//log.info("Test filter : "+request.getRequestURI());
			chain.doFilter(req, resp);
	}
	
	

}
