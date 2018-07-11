package struts2.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import struts2.action.support.StrutsMessage;
import jxl.Sheet;
import jxl.Workbook;
import dao.ApplyJobVO;
import dao.CompanyinfoVO;
import dao.EvaluationStuVO;
import dao.TeacherinfoVO;
import exception.DBSupportException;
import struts2.action.support.StandardActionSupport;

@SuppressWarnings("serial")
public class CompanyAction extends StandardActionSupport {
	private CompanyinfoVO companyinfoVO;
	private List<CompanyinfoVO> companyinfoVOs;
	private String filename;
	private String searchsort;
	private String searname;
	private String studentNum;
	private String studentName;
	private String teacherName;
	private String content;
	private int pageNow = 1;
	private int pageSize = 5;
	private String delId;
	private String sid;
	private int id;
	private List<ApplyJobVO> applyJobVOs;
	private StrutsMessage strutsMessage;
	private List<EvaluationStuVO> evaluationStuVOs;
	private File excelCmy;
	private String excelCmyFileName;
	private String oldCmpPass;
	private String newCmpPass;
	private String newCmpPass1;
	
	//重置用户密码
	public String resetCmpPass() throws DBSupportException {
		for (String id : getSid().split(",")) {
			companyinfoVO = (CompanyinfoVO) dbOperation.queryDataById(CompanyinfoVO.class, id);
			companyinfoVO.setCompanyPass("111111");
			dbOperation.updateSingleData(companyinfoVO);
		}
		return SUCCESS;
	}
	
	//修改密码
	@SuppressWarnings("unchecked")
	public void updataCmpPass() throws DBSupportException, IOException {
		String cmpName = (String) getSession().getAttribute("username");
		List<CompanyinfoVO> cmp = (List<CompanyinfoVO>) dbOperation.queryDataByClass(CompanyinfoVO.class, new String[]{"companyName","companyPass"}, new String[]{cmpName,oldCmpPass});
		getResponse().setContentType("text/html; charset=GB2312");
		PrintWriter out = getResponse().getWriter();
		if (cmp.size()!=0) {
			if (newCmpPass.equals(newCmpPass1)) {
				companyinfoVO = (CompanyinfoVO) dbOperation.queryDataById(CompanyinfoVO.class, cmp.get(0).getCompanyNum());
				companyinfoVO.setCompanyPass(newCmpPass);
				dbOperation.updateSingleData(companyinfoVO);
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
	
	//教师查询已被评价的学生
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String showEvaStu() {
		try {
			String teaName = (String) getSession().getAttribute("username");
			String count = dbOperation.queryParamPageCount("EvaluationStuVO", new String[]{"evaStatus","teacherName"}, new String[]{"已评价",teaName});
			int modNum = Integer.parseInt(count)%pageSize;
			int PageCount = Integer.parseInt(count)/pageSize;
			getRequest().setAttribute("Pagecount",modNum==0?PageCount:PageCount+1);
			Map map = new HashMap();
			map.put("teacherName",teaName);
			map.put("evaStatus", "已评价");
			String hql = "from EvaluationStuVO where evaStatus in (:evaStatus) and teacherName in (:teacherName)";
			evaluationStuVOs = (List<EvaluationStuVO>) dbOperation.queryByPage(hql, pageNow,
					pageSize, map);
		} catch (DBSupportException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return "evastu";
	}
	//公司查询已评价的学生信息
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String showEvaStus() {
		try {
			String companyName = (String) getSession().getAttribute("username");
			String count = dbOperation.queryParamPageCount("EvaluationStuVO", new String[]{"evaStatus","companyName"}, new String[]{"已评价",companyName});
			int modNum = Integer.parseInt(count)%pageSize;
			int PageCount = Integer.parseInt(count)/pageSize;
			getRequest().setAttribute("Pagecount",modNum==0?PageCount:PageCount+1);
			Map map = new HashMap();
			map.put("companyName", companyName);
			map.put("evaStatus", "已评价");
			String hql = "from EvaluationStuVO where evaStatus in (:evaStatus) and companyName in (:companyName)";
			evaluationStuVOs = (List<EvaluationStuVO>) dbOperation.queryByPage(hql, pageNow,
					pageSize, map);
		} catch (DBSupportException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return "showEvaStu";
	}
	//保存评价内容
	public void saveEvaStu() throws IOException{
		try {
			EvaluationStuVO vo = new EvaluationStuVO();
			String companyName = (String) getSession().getAttribute("username");
			vo.setStudentNum(studentNum);
			vo.setTeacherName(teacherName);
			vo.setCompanyName(companyName);
			vo.setEvaContent(content);
			vo.setStudentName(studentName);
			vo.setEvaStatus("已评价");
			dbOperation.saveSingleData(vo);
			if(vo!=null){
				ApplyJobVO aVo =(ApplyJobVO) dbOperation.queryDataByClass(ApplyJobVO.class, "id", id).get(0);
				aVo.setEvaStatus("已评价");
				dbOperation.updateSingleData(aVo);
			}
		} catch (DBSupportException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	//显示公司的已审核的实习生
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String showComwithStu() {
		try {
			String companyNum =  (String) getSession().getAttribute("companyNum");
			String count = dbOperation.queryParamPageCount("ApplyJobVO", new String[]{"companyNum","status","evaStatus"}, new String[]{companyNum,"已审核","待评价"});
			int modNum = Integer.parseInt(count)%pageSize;
			int PageCount = Integer.parseInt(count)/pageSize;
			getRequest().setAttribute("Pagecount",modNum==0?PageCount:PageCount+1);	
			Map map = new HashMap();
			map.put("companyNum",companyNum);
			map.put("status", "已审核");
			map.put("evaStatus", "待评价");
			String hql = "from ApplyJobVO where companyNum in (:companyNum) and status in (:status) and evaStatus in (:evaStatus)";
			//applyJobVOs = (List<ApplyJobVO>)dbOperation.queryDataByClass(ApplyJobVO.class, new String[]{"status","evaStatus"}, new String[]{"已审核","待评价"});
			applyJobVOs = (List<ApplyJobVO>) dbOperation.queryByPage(hql, pageNow,
					pageSize, map);
		} catch (DBSupportException e) {
 			e.printStackTrace();
		}
		return "evaluation";
	}
	//显示公司的信息
	@SuppressWarnings("unchecked")
	public String showCompanyInfo() throws DBSupportException {
		String companyNum= (String) getSession().getAttribute("companyNum");
		companyinfoVOs=(List<CompanyinfoVO>) dbOperation.queryDataByClass(CompanyinfoVO.class, "companyNum", companyNum);
		return "companyinfo";
	}
	//按条件查询企业信息
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
//企业信息插入数据库
	public String insertCmp() throws DBSupportException, IOException {
		Serializable i = dbOperation.saveSingleData(companyinfoVO);
		if (i != null) {
			getResponse().setContentType("text/html; charset=GBK");
			PrintWriter out = getResponse().getWriter();
			out.print("<script language=javascript>alert('添加成功！');window.location.href='addCompany.jsp';</script>");
			out.close();
		} else {
			return null;
		}
		return null;
	}
//excel导入到数据库
	public String ExcelToDb() throws IOException {
		
		String inputPath = "upload/company";
		String realPath = ServletActionContext.getServletContext().getRealPath(inputPath);
		File file = new File(realPath);
		if (!file.exists()) {
			file.mkdir();
		}
		if (excelCmy!=null) {
			FileUtils.copyFile(excelCmy, new File(new File(realPath),excelCmyFileName));
		}
		List<CompanyinfoVO> listExcel = getAllByExcel(realPath+"/"+new File(new File(realPath),excelCmyFileName).getName());

		try {
			for (CompanyinfoVO cm : listExcel) {
				dbOperation.saveOrUpdateSingleData(cm);
			}
			getResponse().setContentType("text/html; charset=GBK");
			PrintWriter out = getResponse().getWriter();
			out.print("<script language=javascript>alert('导入数据成功！');window.location.href='addCompany.jsp';</script>");
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
//删除所选的子项
	public String delAllCmp() {
		List<String> ids = new ArrayList<String>();
		for (String id : getSid().split(",")) {
			ids.add(id);
		}
		try {
			dbOperation.deteleDataByClass(CompanyinfoVO.class, "id", ids);
			return SUCCESS;
		} catch (DBSupportException e) {
			e.printStackTrace();
		}
		return null;
	}
//获取excel的所有内容
	public static List<CompanyinfoVO> getAllByExcel(String file) {
		List<CompanyinfoVO> list = new ArrayList<CompanyinfoVO>();
		try {
			Workbook rwb = Workbook.getWorkbook(new File(file));
			Sheet rs = rwb.getSheet(0);
			int clos = rs.getColumns();
			int rows = rs.getRows();
			for (int i = 1; i < rows; i++) {
				for (int j = 0; j < clos; j++) {
					String companyNum = rs.getCell(j++, i).getContents();
					String companyName = rs.getCell(j++, i).getContents();
					String companyPass = rs.getCell(j++, i).getContents();
					String companyAddress = rs.getCell(j++, i).getContents();
					String companyIndustry = rs.getCell(j++, i).getContents();
					String companyType = rs.getCell(j++, i).getContents();
					String companyContacts = rs.getCell(j++, i).getContents();
					String companyPhone = rs.getCell(j++, i).getContents();
					String companyDescription = rs.getCell(j++, i)
							.getContents();
					list.add(new CompanyinfoVO(companyNum, companyName,
							companyPass, companyAddress, companyIndustry,
							companyType, companyContacts, companyPhone,
							companyDescription));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}
//更新企业信息
	public void updateCmp() throws DBSupportException, IOException {
		CompanyinfoVO companyinfoVO1 = (CompanyinfoVO) dbOperation.queryDataByClass(CompanyinfoVO.class, "companyNum", companyinfoVO.getCompanyNum()).get(0);
		companyinfoVO1.setCompanyName(companyinfoVO.getCompanyName());
		companyinfoVO1.setCompanyAddress(companyinfoVO.getCompanyAddress());
		companyinfoVO1.setCompanyContacts(companyinfoVO.getCompanyContacts());
		companyinfoVO1.setCompanyDescription(companyinfoVO.getCompanyDescription());
		companyinfoVO1.setCompanyIndustry(companyinfoVO.getCompanyIndustry());
		companyinfoVO1.setCompanyPhone(companyinfoVO.getCompanyPhone());
		companyinfoVO1.setCompanyType(companyinfoVO.getCompanyType());
		dbOperation.saveOrUpdateSingleData(companyinfoVO1);
		getResponse().setContentType("text/html; charset=GB2312");
		PrintWriter out = getResponse().getWriter();
		out.print("<script language=javascript>alert('修改成功');self.location=document.referrer;</script>");
		out.close();
	}
//删除企业
	public String delCmp() {
		try {
			dbOperation.deteleDataByClass(CompanyinfoVO.class, "companyNum",
					delId);
			return SUCCESS;
		} catch (DBSupportException e) {
			e.printStackTrace();
		}
		return null;
	}
//条件查询企业信息
	public String searchCmp() throws UnsupportedEncodingException {
		getSession().removeAttribute("searname");
		searchCompany();
		return SUCCESS;
	}
//分页处理
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

	public CompanyinfoVO getCompanyinfoVO() {
		return companyinfoVO;
	}

	public void setCompanyinfoVO(CompanyinfoVO companyinfoVO) {
		this.companyinfoVO = companyinfoVO;
	}

	public List<CompanyinfoVO> getCompanyinfoVOs() {
		return companyinfoVOs;
	}

	public void setCompanyinfoVOs(List<CompanyinfoVO> companyinfoVOs) {
		this.companyinfoVOs = companyinfoVOs;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
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

	public String getDelId() {
		return delId;
	}

	public void setDelId(String delId) {
		this.delId = delId;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public List<ApplyJobVO> getApplyJobVOs() {
		return applyJobVOs;
	}

	public void setApplyJobVOs(List<ApplyJobVO> applyJobVOs) {
		this.applyJobVOs = applyJobVOs;
	}

	public String getStudentNum() {
		return studentNum;
	}

	public void setStudentNum(String studentNum) {
		this.studentNum = studentNum;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public StrutsMessage getStrutsMessage() {
		return strutsMessage;
	}

	public void setStrutsMessage(StrutsMessage strutsMessage) {
		this.strutsMessage = strutsMessage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<EvaluationStuVO> getEvaluationStuVOs() {
		return evaluationStuVOs;
	}

	public void setEvaluationStuVOs(List<EvaluationStuVO> evaluationStuVOs) {
		this.evaluationStuVOs = evaluationStuVOs;
	}
	public File getExcelCmy() {
		return excelCmy;
	}
	public void setExcelCmy(File excelCmy) {
		this.excelCmy = excelCmy;
	}
	public String getExcelCmyFileName() {
		return excelCmyFileName;
	}
	public void setExcelCmyFileName(String excelCmyFileName) {
		this.excelCmyFileName = excelCmyFileName;
	}

	public String getOldCmpPass() {
		return oldCmpPass;
	}

	public void setOldCmpPass(String oldCmpPass) {
		this.oldCmpPass = oldCmpPass;
	}

	public String getNewCmpPass() {
		return newCmpPass;
	}

	public void setNewCmpPass(String newCmpPass) {
		this.newCmpPass = newCmpPass;
	}

	public String getNewCmpPass1() {
		return newCmpPass1;
	}

	public void setNewCmpPass1(String newCmpPass1) {
		this.newCmpPass1 = newCmpPass1;
	}
	
}
