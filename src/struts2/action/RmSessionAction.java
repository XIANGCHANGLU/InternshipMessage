package struts2.action;


import java.io.IOException;

import struts2.action.support.StandardActionSupport;

@SuppressWarnings("serial")
public class RmSessionAction extends StandardActionSupport {
	public void removeSession() throws IOException {
		String indentity = (String) getSession().getAttribute("indentity");
		if (indentity != null) {
			getSession().invalidate();
		} else {
			System.out.println("error");
		}
		getResponse().sendRedirect("login.html");
	}
}
