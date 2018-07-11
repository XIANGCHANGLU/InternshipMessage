package struts2.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;








import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import jxl.Sheet;
import jxl.Workbook;
import dao.ApplyJobVO;
import dao.ScoreVO;
import dao.StudentinfoVO;
import dao.TeacherinfoVO;
import dao.ThesisVO;
import dao.WeeklyreportVO;
import exception.DBSupportException;
import struts2.action.support.StandardActionSupport;

@SuppressWarnings("serial")
public class TeacherAction extends StandardActionSupport {
	private TeacherinfoVO teacherinfoVO;
	private List<TeacherinfoVO> teacherinfoVOs;
	private String delId;
	private String sid;
	private String filename;
	private String searchsort;
	private String searname;
	private int pageNow = 1;
	private int pageSize = 5;
	private String studentNum;
	private String studentName;
	private String companyName;
	private double score;
	private List<ScoreVO> scoreVOs;
	private File excelTea;
	private String excelTeaFileName;
	private String oldTeaPass;
	private String newTeaPass;
	private String newTeaPass1;
	//修改密码
	@SuppressWarnings("unchecked")
	public void updataStuPass() throws DBSupportException, IOException {
		String teaName = (String) getSession().getAttribute("username");
		List<TeacherinfoVO> tea = (List<TeacherinfoVO>) dbOperation.queryDataByClass(TeacherinfoVO.class, new String[]{"teacherName","teacherPass"}, new String[]{teaName,oldTeaPass});
		getResponse().setContentType("text/html; charset=GB2312");
		PrintWriter out = getResponse().getWriter();
		if (tea.size()!=0) {
			if (newTeaPass.equals(newTeaPass1)) {
				teacherinfoVO = (TeacherinfoVO) dbOperation.queryDataById(TeacherinfoVO.class, tea.get(0).getTeacherNum());
				teacherinfoVO.setTeacherPass(newTeaPass);
				dbOperation.updateSingleData(teacherinfoVO);
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
	
	//查看实习分数
	@SuppressWarnings("unchecked")
	public String showScore(){
		try {
			String teaName = (String) getSession().getAttribute("username");
			String count = dbOperation.queryParamPageCount("ScoreVO","teacherName",teaName);
			int modNum = Integer.parseInt(count)%pageSize;
			int PageCount = Integer.parseInt(count)/pageSize;
			getRequest().setAttribute("Pagecount",modNum==0?PageCount:PageCount+1);
			Map map = new HashMap<>();
			map.put("teacherName", teaName);
			String hql = "from ScoreVO where teacherName in (:teacherName)";
			scoreVOs=(List<ScoreVO>) dbOperation.queryByPage(hql, pageNow, pageSize, map);
		} catch (DBSupportException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return "score";
	}
	
	//实习打分
	public void makingScore(){
		try {
			ScoreVO o = new ScoreVO();
			o.setStudentNum(studentNum);
			o.setStudentName(studentName);
			o.setTeacherName((String) getSession().getAttribute("username"));
			o.setCompanyName(companyName);
			o.setScore(score);
			dbOperation.saveSingleData(o);
			if(o!=null){
				ApplyJobVO aVo =(ApplyJobVO) dbOperation.queryDataByClass(ApplyJobVO.class, new String[]{"studentNum","status"}, new String[]{studentNum,"已审核"}).get(0);
				aVo.setMakingScore("已评分");
				dbOperation.updateSingleData(aVo);
			}
		} catch (DBSupportException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	//显示个人资料
	@SuppressWarnings("unchecked")
	public String showPersonalData() {
		String num = (String) getSession().getAttribute("username");
		try {
			teacherinfoVOs = (List<TeacherinfoVO>) dbOperation
					.queryDataByClass(TeacherinfoVO.class, "teacherName", num);
			int i = dbOperation.queryNotIn(WeeklyreportVO.class, new String[]{"teacherName","reportStatus"}, new String[]{num,"未提交"}).size();
			getRequest().setAttribute("count", i);
			int j = dbOperation.queryDataByClass(ThesisVO.class, "teacherName", num).size();
			getRequest().setAttribute("num", j);
			return "person";
		} catch (DBSupportException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}

	//按条件查询教师信息
	public String searchTeacher() throws UnsupportedEncodingException {
		String searname = new String(getSearname().getBytes("ISO8859_1"),
				"utf-8").trim();

		String searchsort = getSearchsort().trim();
		if (!searchsort.equals(getSession().getAttribute("searchsort"))
				&& getSession().getAttribute("searchsort") != null) {
			getSession().removeAttribute("searname");
		}
		getSession().setAttribute("searchsort", searchsort);
		if (searchsort == null || searname.equals("")) {
			String count = dbOperation.queryPageCount("TeacherinfoVO");
			int modNum = Integer.parseInt(count)%pageSize;
			int PageCount = Integer.parseInt(count)/pageSize;
			getRequest().setAttribute("Pagecount",modNum==0?PageCount:PageCount+1);
			queryByPage(pageSize, pageNow);
			return SUCCESS;
		} else if (searchsort.equals("teacherNum")) {
			queryByData(TeacherinfoVO.class, "teacherNum", searname, pageSize,
					pageNow);
			return SUCCESS;
		} else if (searchsort.equals("teacherDept")) {
			if (getSession().getAttribute("searname") != null) {
				searname = (String) getSession().getAttribute("searname");
			}
			String count = dbOperation.queryParamPageCount("TeacherinfoVO","teacherDept",searname);
			int modNum = Integer.parseInt(count)%pageSize;
			int PageCount = Integer.parseInt(count)/pageSize;
			getRequest().setAttribute("Pagecount",modNum==0?PageCount:PageCount+1);
			queryByData(TeacherinfoVO.class, "teacherDept", searname, pageSize,
					pageNow);
			getSession().setAttribute("searname", searname);
			return SUCCESS;
		}
		return null;
	}
//查询教师信息
	public String searchTea() throws UnsupportedEncodingException {
		getSession().removeAttribute("searname");
		searchTeacher();
		return SUCCESS;
	}
	//教师页面分页处理
	public List<TeacherinfoVO> queryByPage(int pageSize, int pageNow) {
		if (pageSize > 0 && pageNow > 0) {
			teacherinfoVOs = dbOperation.setPageTeaAll(pageNow, pageSize);
			return teacherinfoVOs;
		}
		return null;
	}
//保存老师信息
	public String saveTeacher() throws DBSupportException, IOException {
		Object o = dbOperation.saveSingleData(teacherinfoVO);
		if (o ==null) {
			return null;
		} else {
			getResponse().setContentType("text/html; charset=GBK");
			PrintWriter out = getResponse().getWriter();
			out.print("<script language=javascript>alert('添加职工信息成功');window.location.href='addTeacher.jsp';</script>");
			out.close();
		}
		return null;
	}
//删除单个用户
	public String delTea() {
		try {
			dbOperation.deteleDataByClass(TeacherinfoVO.class, "teacherNum",
					delId);
			return SUCCESS;
		} catch (DBSupportException e) {
			e.printStackTrace();
		}
		return null;
	}
	//重置用户密码
	public String resetTeaPass() throws DBSupportException {
		for (String id : getSid().split(",")) {
			teacherinfoVO = (TeacherinfoVO) dbOperation.queryDataById(TeacherinfoVO.class, id);
			teacherinfoVO.setTeacherPass("111111");
			dbOperation.updateSingleData(teacherinfoVO);
		}
		return SUCCESS;
	}
	//删除已选的用户
	public String delAllTea() {
		List<String> ids = new ArrayList<String>();
		for (String id : getSid().split(",")) {
			ids.add(id);
		}
		try {
			dbOperation.deteleDataByClass(TeacherinfoVO.class, "id", ids);
			return SUCCESS;
		} catch (DBSupportException e) {
			e.printStackTrace();
		}
		return null;
	}
//更新用户信息
	public void updateTeacher() throws DBSupportException, IOException {
		TeacherinfoVO teacherinfoVO1 =  (TeacherinfoVO) dbOperation
				.queryDataByClass(TeacherinfoVO.class, "teacherNum",
						teacherinfoVO.getTeacherNum()).get(0);
		teacherinfoVO1.setPosition(teacherinfoVO.getPosition());
		teacherinfoVO1.setTeacherDept(teacherinfoVO.getTeacherDept());
		teacherinfoVO1.setTeacherMail(teacherinfoVO.getTeacherMail());
		teacherinfoVO1.setTeacherName(teacherinfoVO.getTeacherName());
		teacherinfoVO1.setTeacherNum(teacherinfoVO.getTeacherNum());
		teacherinfoVO1.setTeacherPhone(teacherinfoVO.getTeacherPhone());
		dbOperation.saveOrUpdateSingleData(teacherinfoVO1);
		getResponse().setContentType("text/html; charset=GB2312");
		PrintWriter out = getResponse().getWriter();
		out.print("<script language=javascript>alert('修改成功');self.location=document.referrer;</script>");
		out.close();
	}
//excel导入到数据库
	public String ExcelToDb() throws IOException{
		String inputPath = "upload/teacher";
		String realPath = ServletActionContext.getServletContext().getRealPath(inputPath);
		File file = new File(realPath);
		if (!file.exists()) {
			file.mkdir();
		}
		if (excelTea!=null) {
			FileUtils.copyFile(excelTea, new File(new File(realPath),excelTeaFileName));
		}
		List<TeacherinfoVO> listExcel = getAllByExcel(realPath+"/"+new File(new File(realPath),excelTeaFileName).getName());

		try {
			for (TeacherinfoVO stu : listExcel) {
				dbOperation.saveOrUpdateSingleData(stu);
			}
			getResponse().setContentType("text/html; charset=GBK");
			PrintWriter out = getResponse().getWriter();
			out.print("<script language=javascript>alert('成功导入数据！');window.location.href='addTeacher.jsp';</script>");
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
//获取教师excel表的所有信息
	public static List<TeacherinfoVO> getAllByExcel(String file) {
		List<TeacherinfoVO> list = new ArrayList<TeacherinfoVO>();
		try {
			Workbook rwb = Workbook.getWorkbook(new File(file));
			Sheet rs = rwb.getSheet(0);// ����
			int clos = rs.getColumns();// �õ����е���
			int rows = rs.getRows();// �õ����е���
			for (int i = 1; i < rows; i++) {
				for (int j = 0; j < clos; j++) {
					// ��һ��������ڶ���������
					/* String id = rs.getCell(j++, i).getContents(); */// Ĭ������߱��Ҳ��һ��
																		// ���������j++
					String teacherNum = rs.getCell(j++, i).getContents();
					String teacherName = rs.getCell(j++, i).getContents();
					String teacherPass = rs.getCell(j++, i).getContents();
					String position = rs.getCell(j++, i).getContents();
					String teacherDept = rs.getCell(j++, i).getContents();
					String teacherPhone = rs.getCell(j++, i).getContents();
					String teacherMail = rs.getCell(j++, i).getContents();
					list.add(new TeacherinfoVO(teacherNum,
							teacherName, teacherPass, position, teacherDept,
							teacherPhone, teacherMail));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}
//调用分页方法
	@SuppressWarnings("unchecked")
	public List<TeacherinfoVO> queryByData(final Class<?> clazz, String param,
			Object value, int pageSize, int pageNow) {
		if (pageSize > 0 && pageNow > 0) {
			teacherinfoVOs = dbOperation.setPageData(clazz, param, value,
					pageNow, pageSize);
			return teacherinfoVOs;
		}
		return null;

	}

	public TeacherinfoVO getTeacherinfoVO() {
		return teacherinfoVO;
	}

	public void setTeacherinfoVO(TeacherinfoVO teacherinfoVO) {
		this.teacherinfoVO = teacherinfoVO;
	}

	public List<TeacherinfoVO> getTeacherinfoVOs() {
		return teacherinfoVOs;
	}

	public void setTeacherinfoVOs(List<TeacherinfoVO> teacherinfoVOs) {
		this.teacherinfoVOs = teacherinfoVOs;
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public List<ScoreVO> getScoreVOs() {
		return scoreVOs;
	}

	public void setScoreVOs(List<ScoreVO> scoreVOs) {
		this.scoreVOs = scoreVOs;
	}

	public File getExcelTea() {
		return excelTea;
	}

	public void setExcelTea(File excelTea) {
		this.excelTea = excelTea;
	}

	public String getExcelTeaFileName() {
		return excelTeaFileName;
	}

	public void setExcelTeaFileName(String excelTeaFileName) {
		this.excelTeaFileName = excelTeaFileName;
	}

	public String getOldTeaPass() {
		return oldTeaPass;
	}

	public void setOldTeaPass(String oldTeaPass) {
		this.oldTeaPass = oldTeaPass;
	}

	public String getNewTeaPass() {
		return newTeaPass;
	}

	public void setNewTeaPass(String newTeaPass) {
		this.newTeaPass = newTeaPass;
	}

	public String getNewTeaPass1() {
		return newTeaPass1;
	}

	public void setNewTeaPass1(String newTeaPass1) {
		this.newTeaPass1 = newTeaPass1;
	}
	
}
