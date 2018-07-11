package struts2.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import dao.ApplyJobVO;
import dao.CompanyinfoVO;
import dao.StudentinfoVO;
import exception.DBSupportException;
import struts2.action.support.StandardActionSupport;
import util.CommonUtils;

public class StudentAction extends StandardActionSupport {
	private List<CompanyinfoVO> companyinfoVOs;
	private String searchsort;
	private String searname;
	private int pageNow = 1;
	private int pageSize = 5;
	private String companyNum;
	private String companyName;
	private List<ApplyJobVO> applyJobVOs;
	private String oldStuPass;
	private String newStuPass;
	private String newStuPass1;
	private StudentinfoVO students;
	
	//更新密码
	@SuppressWarnings("unchecked")
	public void updataStuPass() throws DBSupportException, IOException {
		String stuName = (String) getSession().getAttribute("studentNum");
		List<StudentinfoVO> student = (List<StudentinfoVO>) dbOperation.queryDataByClass(StudentinfoVO.class, new String[]{"studentNum","studentPass"}, new String[]{stuName,oldStuPass});
		getResponse().setContentType("text/html; charset=GB2312");
		PrintWriter out = getResponse().getWriter();
		if (student.size()!=0) {
			if (newStuPass.equals(newStuPass1)) {
				students = (StudentinfoVO) dbOperation.queryDataById(StudentinfoVO.class, student.get(0).getId());
				students.setStudentPass(newStuPass);
				dbOperation.updateSingleData(students);
				out.print("修改成功");	
			}else {
				out.print("新密码和确认密码不匹配!");
				out.close();
			}
		}else {
			out.print("旧密码输入有误，无法更改!");
			out.close();
		}
	}
	//显示申请的企业
	@SuppressWarnings("unchecked")
	public String showMyApply() throws IOException {
		try {
			applyJobVOs = (List<ApplyJobVO>) dbOperation.queryDataByClass(
					ApplyJobVO.class, "studentNum",
					getSession().getAttribute("studentNum"));
			return "apply";
		} catch (DBSupportException e) {
			e.printStackTrace();
		}
		return null;
	}
	//检查是否已申请
	public int checkStatus(String studentNum) throws DBSupportException {
		int i =dbOperation.queryDataByClass(ApplyJobVO.class, new String[] {
							"studentNum", "status" }, new String[] {
			studentNum, "已审核" }).size();
		return i;
	}
	@SuppressWarnings("unchecked")
	public List<ApplyJobVO> checkAgain(String studentNum,String companyNum) throws DBSupportException {
		 applyJobVOs = (List<ApplyJobVO>) dbOperation
				.queryDataByClass(ApplyJobVO.class, new String[] {
						"studentNum", "companyNum" }, new String[] {
						studentNum, companyNum });
		 return applyJobVOs;
	}
	//申请实习
	@SuppressWarnings("unchecked")
	public String applyJob() throws IOException {
		try {
			String studentNum = (String) getSession()
					.getAttribute("studentNum");
			int i =checkStatus(studentNum);
			String companyNum = getCompanyNum();
			if (i==0) {
			applyJobVOs=checkAgain(studentNum, companyNum);
			if (applyJobVOs.size() != 0) {
			
				getResponse().setContentType("text/html;charset=GBK");
				PrintWriter out = getResponse().getWriter();
				out.print("你已经申请过了");
				out.flush();
				out.close();
			} else {
				CommonUtils c = new CommonUtils();
				ApplyJobVO a = new ApplyJobVO();
				List<StudentinfoVO> studentTel = (List<StudentinfoVO>)dbOperation.queryDataByClass(StudentinfoVO.class, "studentNum", studentNum);
				a.setStudentTel(studentTel.get(0).getStudentPhone());
				a.setCompanyNum(companyNum);
				a.setStudentName((String) getSession().getAttribute("username"));
				a.setStudentNum(studentNum);
				a.setTeacherName((String) getSession().getAttribute("teacher"));
				a.setData(c.getNowTime());
				a.setCompanyName(getCompanyName());
				a.setStatus("审核中");
				a.setEvaStatus("待评价");
				a.setMakingScore("未评分");
				dbOperation.saveSingleData(a);
				getResponse().setContentType("text/html;charset=GBK");
				PrintWriter out = getResponse().getWriter();
				out.print("申请成功，请等待公司面试电话！");
				out.flush();
				out.close();
			}		
			}else {
				getResponse().setContentType("text/html;charset=GBK");
				PrintWriter out = getResponse().getWriter();
				out.print("你已经被录用了！");
				out.flush();
				out.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	//查询企业信息
	public String searchCompany() throws UnsupportedEncodingException {
		String searname = new String(getSearname().getBytes("ISO8859_1"),
				"utf-8").trim();

		String searchsort = getSearchsort().trim();
		if (!searchsort.equals(getSession().getAttribute("searchsort"))
				&& getSession().getAttribute("searchsort") != null) {
			getSession().removeAttribute("searname");
		}
		getSession().setAttribute("searchsort", searchsort);
		if (searchsort == null || searname.equals("")) {
			String count = dbOperation.queryPageCount("CompanyinfoVO");
			int modNum = Integer.parseInt(count)%pageSize;
			int PageCount = Integer.parseInt(count)/pageSize;
			getRequest().setAttribute("Pagecount",modNum==0?PageCount:PageCount+1);
			queryByPage(pageSize, pageNow);
			return SUCCESS;
		} else if (searchsort.equals("companyNum")) {
			queryByData(CompanyinfoVO.class, "companyNum", searname, pageSize,
					pageNow);
			return SUCCESS;
		} else if (searchsort.equals("companyName")) {
			if (getSession().getAttribute("searname") != null) {
				searname = (String) getSession().getAttribute("searname");
			}
			queryByData(CompanyinfoVO.class, "companyName", searname, pageSize,
					pageNow);
			getSession().setAttribute("searname", searname);
			return SUCCESS;
		} else if (searchsort.equals("companyType")) {
			if (getSession().getAttribute("searname") != null) {
				searname = (String) getSession().getAttribute("searname");
			}
			String count = dbOperation.queryParamPageCount("CompanyinfoVO","companyType",searname);
			int modNum = Integer.parseInt(count)%pageSize;
			int PageCount = Integer.parseInt(count)/pageSize;
			getRequest().setAttribute("Pagecount",modNum==0?PageCount:PageCount+1);
			queryByData(CompanyinfoVO.class, "companyType", searname, pageSize,
					pageNow);
			getSession().setAttribute("searname", searname);
			return SUCCESS;
		}
		return null;
	}

	public String searchCmp() throws UnsupportedEncodingException {
		getSession().removeAttribute("searname");
		searchCompany();
		return SUCCESS;
	}
	//企业页面分页处理
	public List<CompanyinfoVO> queryByPage(int pageSize, int pageNow) {
		if (pageSize > 0 && pageNow > 0) {
			companyinfoVOs = dbOperation.setPageCmpAll(pageNow, pageSize);
			return companyinfoVOs;
		}
		return null;
	}
	//调用分页方法
	@SuppressWarnings("unchecked")
	public List<CompanyinfoVO> queryByData(final Class<?> clazz, String param,
			Object value, int pageSize, int pageNow) {
		if (pageSize > 0 && pageNow > 0) {
			companyinfoVOs = dbOperation.setPageData(clazz, param, value,
					pageNow, pageSize);
			return companyinfoVOs;
		}
		return null;

	}

	public List<CompanyinfoVO> getCompanyinfoVOs() {
		return companyinfoVOs;
	}

	public void setCompanyinfoVOs(List<CompanyinfoVO> companyinfoVOs) {
		this.companyinfoVOs = companyinfoVOs;
	}

	public String getSearchsort() {
		return searchsort;
	}

	public void setSearchsort(String searchsort) {
		this.searchsort = searchsort;
	}

	public String getSearname() {
		return searname;
	}

	public void setSearname(String searname) {
		this.searname = searname;
	}

	public int getPageNow() {
		return pageNow;
	}

	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getCompanyNum() {
		return companyNum;
	}

	public void setCompanyNum(String companyNum) {
		this.companyNum = companyNum;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public List<ApplyJobVO> getApplyJobVOs() {
		return applyJobVOs;
	}

	public void setApplyJobVOs(List<ApplyJobVO> applyJobVOs) {
		this.applyJobVOs = applyJobVOs;
	}

	public String getOldStuPass() {
		return oldStuPass;
	}

	public void setOldStuPass(String oldStuPass) {
		this.oldStuPass = oldStuPass;
	}

	public String getNewStuPass() {
		return newStuPass;
	}

	public void setNewStuPass(String newStuPass) {
		this.newStuPass = newStuPass;
	}

	public String getNewStuPass1() {
		return newStuPass1;
	}

	public void setNewStuPass1(String newStuPass1) {
		this.newStuPass1 = newStuPass1;
	}

	public StudentinfoVO getStudents() {
		return students;
	}

	public void setStudents(StudentinfoVO students) {
		this.students = students;
	}

}
