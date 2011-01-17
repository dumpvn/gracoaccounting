/*
 * Created on May 12, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.mpe.financial.struts.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpe.financial.model.UserStream;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ActionListener implements ServletContextListener, HttpSessionListener {
	Log log = LogFactory.getFactory().getInstance(this.getClass());
	private Map userstreams = Collections.synchronizedMap(new HashMap());

	public ActionListener() {
		log.info("Constructor Action listener created");
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		log.info("ServletContext Initialised");
		arg0.getServletContext().setAttribute("userstreams",userstreams);
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent arg0) {
		HttpSession session = arg0.getSession();
		//log.info("Session created : "+session.getId());
		UserStream userstream = new UserStream();
		session.setAttribute("userstream",userstream);
		userstreams.put(session.getId(), userstream);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent arg0) {
		HttpSession session = arg0.getSession();
		//log.info("Session destroyed : "+session.getId());
		try {
			userstreams.remove(session.getId());
		}catch(Exception ex) {
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		log.info("ServletContext destroyed");
	}

	
}
