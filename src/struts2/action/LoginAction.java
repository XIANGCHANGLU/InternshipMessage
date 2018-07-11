package struts2.action;

import exception.DBSupportException;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import exception.AWebException;
import session.HttpSessionContainer;
import struts2.action.support.StandardActionSupport;
import dao.AdminVO;
import dao.CompanyinfoVO;
import dao.StudentinfoVO;
import dao.TeacherinfoVO;

@SuppressWarnings("serial")
public class LoginAction extends StandardActionSupport {
	private String username;
	private String password;
	private String indentity;
	ServletRequest request = ServletActionContext.getRequest();
	HttpSession session = getRequest().getSession(true);
	
	public String redirect() {
		if (getSession()!=null &&HttpSessionContainer.contains(getSession())) {
			return "redirect";
		}
		return "login";
	}
	
	//登陆
	@SuppressWarnings("unchecked")
	public String signIn() throws DBSupportException,
			IOException {
		request.setCharacterEncoding("utf-8");
		if((String) session.getAttribute("username")==null||(String) session.getAttribute("username")==""){
			indentity = request.getParameter("usertype");
			if (indentity==""||indentity==null) {
				return "login";
			}
			username = request.getParameter("username");
			password = request.getParameter("password");
			session.setAttribute("indentity", indentity);
			if (indentity.equals("学生")) {
				List<StudentinfoVO> userList = null;
	
				userList = (List<StudentinfoVO>) dbOperation.queryDataByClass(
						StudentinfoVO.class, new String[] { "studentNum",
								"studentPass" },
						new String[] { username, password });
				if (userList.size() == 1) {
					String studentName = userList.get(0).getStudentName();
					String studentNum = userList.get(0).getStudentNum();
					String teacher = userList.get(0).getTeacherName();
					getSession().setAttribute("studentNum", studentNum);
					getSession().setAttribute("username", studentName);
					getSession().setAttribute("teacher", teacher);
					return SUCCESS;
				} else {
					getResponse().setContentType("text/html; charset=GBK");
					PrintWriter out = getResponse().getWriter();
					out.print("<script language=javascript>alert('账号或密码有误');window.location.href='login.html';</script>");
					out.close();
				}
			} else if (indentity.equals("管理员")) {
				AdminVO aVo = new AdminVO();
				try {
					if ((aVo = checkpassword(username, password)) != null) {
						String adminNameString = aVo.getAdminName();
						getSession().setAttribute("username", adminNameString);
						return SUCCESS;
					} else {
						getResponse().setContentType("text/html; charset=GBK");
						PrintWriter out = getResponse().getWriter();
						out.print("<script language=javascript>alert('账号或密码有误');window.location.href='login.html';</script>");
						out.close();
					}
				} catch (AWebException e) {
					e.printStackTrace();
				}
				return SUCCESS;
			} else if (indentity.equals("教师")) {
				List<TeacherinfoVO> userList = null;
				userList = (List<TeacherinfoVO>) dbOperation.queryDataByClass(
						TeacherinfoVO.class, new String[] { "teacherNum",
								"teacherPass" },
						new String[] { username, password });
				if (userList.size() == 1) {
					String teacherName = userList.get(0).getTeacherName();
					getSession().setAttribute("username", teacherName);
					return SUCCESS;
				} else {
					getResponse().setContentType("text/html; charset=GBK");
					PrintWriter out = getResponse().getWriter();
					out.print("<script language=javascript>alert('账号或密码有误');window.location.href='login.html';</script>");
					out.close();
				}
			} else if (indentity.equals("企业")) {
				List<CompanyinfoVO> userList = null;
				userList = (List<CompanyinfoVO>) dbOperation.queryDataByClass(
						CompanyinfoVO.class, new String[] { "companyNum",
								"companyPass" },
						new String[] { username, password });
				if (userList.size() == 1) {
					String companyName = userList.get(0).getCompanyName();
					getSession().setAttribute("username", companyName);
					getSession().setAttribute("companyNum", userList.get(0).getCompanyNum());
					return SUCCESS;
				} else {
					getResponse().setContentType("text/html; charset=GBK");
					PrintWriter out = getResponse().getWriter();
					out.print("<script language=javascript>alert('账号或密码有误');window.location.href='login.html';</script>");
					out.close();
				}
			}
		}else {
			return SUCCESS;

		}
		return "login";

	}
//检查密码是否正确
	@SuppressWarnings({ "unchecked" })
	private AdminVO checkpassword(String username, String password)
			throws AWebException, DBSupportException {
		List<AdminVO> userList = null;

		userList = (List<AdminVO>) dbOperation.queryDataByClass(AdminVO.class,
				new String[] { "adminName", "adminPassword" }, new String[] {
						username.trim(), password.trim() });

		if (userList.size() == 1) {
			return userList.get(0);
		} else {
			return null;
		}
	}

	public LoginAction() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIndentity() {
		return indentity;
	}

	public void setIndentity(String indentity) {
		this.indentity = indentity;
	}

}
