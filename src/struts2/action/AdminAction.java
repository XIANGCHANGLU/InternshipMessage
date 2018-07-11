package struts2.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import dao.ApplyJobVO;
import dao.CompanyinfoVO;
import dao.ScoreVO;
import dao.StudentinfoVO;
import dao.ThesisVO;
import dao.WeeklyreportVO;
import exception.DBSupportException;
import struts2.action.support.StandardActionSupport;

/**
 * @author zhanguohai
 *
 */
@SuppressWarnings("serial")
public class AdminAction extends StandardActionSupport {
	private StudentinfoVO studentinfoVO;
	private String filename;
	private String searchsort;
	private String searname;
	private int delId;
	private List<StudentinfoVO> studentinfoVOs;
	private String sid;
	private int pageNow = 1; // 初始化为1,默认从第一页开始显示
	private int pageSize = 5; // 每页显示5条记录
	private List<ThesisVO> thesisVOs;
	private List<ApplyJobVO> applyJobVOs;
	private List<ScoreVO> scoreVOs;
	private String downloadFilePath;
	private File excelStu;
	private String excelStuFileName;
	//重置用户密码
	public String resetStuPass(){
		String hql = "from StudentinfoVO where id=?";
		for (String sid : getSid().split(",")) {
			try {
				studentinfoVO = (StudentinfoVO) dbOperation.queryDataById(StudentinfoVO.class, Integer.valueOf(sid));
				studentinfoVO.setStudentPass("111111");
				dbOperation.updateSingleData(studentinfoVO);
			} catch (DBSupportException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	//统计图表获取值
	public String countScoreLevel() throws DBSupportException {
		int Excellent = dbOperation.queryDataByScore("ScoreVO", "score", 85, 100).size();
		int good = dbOperation.queryDataByScore("ScoreVO", "score", 70, 85).size();
		int pass = dbOperation.queryDataByScore("ScoreVO", "score", 60, 70).size();
		int NoPass = dbOperation.queryDataByScore("ScoreVO", "score", 0, 60).size();
		int count = Excellent+good+pass+NoPass;
		getRequest().setAttribute("count", count);
		getRequest().setAttribute("Excellent", Excellent);
		getRequest().setAttribute("good", good);
		getRequest().setAttribute("pass", pass);
		getRequest().setAttribute("NoPass", NoPass);
		return "pie";
	}
	//查看实习分数
	@SuppressWarnings("unchecked")
	public void showScore() throws DBSupportException{
		try {
			Map map = new HashMap<>();
			String hql = "from ScoreVO";
			scoreVOs=(List<ScoreVO>) dbOperation.queryByPage(hql, pageNow, pageSize, map);
			String[] title ={"序号","学号","姓名","指导老师","实习单位","实习分数"};
			String[][] newtab = new String[scoreVOs.size()][6];
			for (int i = 0; i <scoreVOs.size() ; i++) {
					ScoreVO tVo =scoreVOs.get(i);
					newtab[i][0]=String.valueOf(tVo.getId());
					newtab[i][1]=tVo.getStudentNum();
					newtab[i][2]=tVo.getStudentName();
					newtab[i][3]=tVo.getTeacherName();
					newtab[i][4]=tVo.getCompanyName();
					newtab[i][5]=String.valueOf(tVo.getScore());
				}
			    JExcel(title,newtab);
		} catch (DBSupportException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	//导出excel
	public String downloadFile() throws DBSupportException{
		showScore();
		String path = ServletActionContext.getServletContext().getRealPath(downloadFilePath);
        HttpServletResponse response = ServletActionContext.getResponse();  
        try {  
            // path是指欲下载的文件的路径。  
            File file = new File(path);  
            // 取得文件名。  
            String filename = file.getName();  
            // 取得文件的后缀名。  
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();  
            // 以流的形式下载文件。  
            InputStream fis = new BufferedInputStream(new FileInputStream(path));  
            byte[] buffer = new byte[fis.available()];  
            fis.read(buffer);  
            fis.close();  
            //清空response  
            response.reset();  
            //设置response的Header  
            String filenameString = new String(filename.getBytes("gbk"),"iso-8859-1");  
            response.addHeader("Content-Disposition", "attachment;filename=" + filenameString);  
            response.addHeader("Content-Length", "" + file.length());  
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());  
            response.setContentType("application/octet-stream");  
            toClient.write(buffer);  
            toClient.flush();  
            toClient.close();  
        } catch (IOException ex) {  
            ex.printStackTrace();  
        }  
		return null;
	}
	
	//导出excel方法
	public void JExcel(String title[],String [][] newstr) {
		try {
			ServletContext servletContext = ServletActionContext.getServletContext();
			String filePath = servletContext.getRealPath("/");
			System.out.println(filePath);
			WritableWorkbook  workbook;
			OutputStream os = new FileOutputStream(filePath+File.separator+"score.xls");
			workbook = Workbook.createWorkbook(os);
			WritableCellFormat colorSet = null;
			WritableSheet sheet = workbook.createSheet("学生分数", 0);
			jxl.write.Label label;
			for (int i = 0; i < title.length; i++) {
				colorSet = new WritableCellFormat();
				colorSet.setAlignment(Alignment.CENTRE);
				colorSet.setBorder(Border.ALL, BorderLineStyle.THIN);
				colorSet.setBackground(jxl.format.Colour.GOLD);
				label = new jxl.write.Label(i,0,title[i],colorSet);
				sheet.addCell(label);
			}
			for (int i = 0; i < newstr.length; i++) {
				for (int j = 0; j < newstr[i].length; j++) {
					label = new jxl.write.Label(j, i+1, newstr[i][j]);
					sheet.addCell(label);
				}
			}
			workbook.write();
			workbook.close();

 		} catch (IOException | WriteException e) {
			e.printStackTrace();
		}
		
	}
	//按条件查询学生分数
	public String showAllScore() throws UnsupportedEncodingException, DBSupportException {
		String searname = new String(getSearname().getBytes("ISO8859_1"),"utf-8").trim();
		String searchsort = getSearchsort().trim();
		if (!searchsort.equals(getSession().getAttribute("searchsort"))
				&& getSession().getAttribute("searchsort") != null) {
			getSession().removeAttribute("searname");
		}
		getSession().setAttribute("searchsort", searchsort);

		if (searchsort == null || searname.equals("")) {
			String count = dbOperation.queryPageCount("ScoreVO");
			int modNum = Integer.parseInt(count)%pageSize;
			int PageCount = Integer.parseInt(count)/pageSize;
			getRequest().setAttribute("Pagecount",modNum==0?PageCount:PageCount+1);
			queryByDataScore(ScoreVO.class, "1", "1", pageSize,
					pageNow);
			return "score";
		} else if (searchsort.equals("studentNum")) {
			queryByDataScore(ScoreVO.class, "studentNum", searname, pageSize,
					pageNow);
			return "score";
		} else if (searchsort.equals("studentName")) {
			if (getSession().getAttribute("searname") != null) {
				searname = (String) getSession().getAttribute("searname");
			}
			queryByDataScore(ScoreVO.class, "studentName", searname, pageSize,
					pageNow);
			getSession().setAttribute("searname", searname);
			return "score";
		} else if (searchsort.equals("companyName")) {
			if (getSession().getAttribute("searname") != null) {
				searname = (String) getSession().getAttribute("searname");
			}
			String count = dbOperation.queryParamPageCount("ScoreVO","companyName",searname);
			int modNum = Integer.parseInt(count)%pageSize;
			int PageCount = Integer.parseInt(count)/pageSize;
			getRequest().setAttribute("Pagecount",modNum==0?PageCount:PageCount+1);
			queryByDataScore(ScoreVO.class, "companyName", searname,
					pageSize, pageNow);
			getSession().setAttribute("searname", searname);
			return "score";
		}
		return null;
	}
	//显示个人信息
	@SuppressWarnings("unchecked")
	public String showPersonalData() {
		String num = (String) getSession().getAttribute("studentNum");
		try {
			studentinfoVOs = (List<StudentinfoVO>) dbOperation
					.queryDataByClass(StudentinfoVO.class, "studentNum", num);
		//	int i = dbOperation.queryDataByClass(WeeklyreportVO.class,
		//			"studentNum", num).size();
			int i = dbOperation.queryDataByClass(WeeklyreportVO.class, new String[]{"studentNum","reportStatus"}, new String[]{num,"已审核"}).size();
			getRequest().setAttribute("num", i);
			thesisVOs = (List<ThesisVO>) dbOperation.queryDataByClass(
					ThesisVO.class, "studentNum", num);
			if (thesisVOs.size() != 0) {
				getRequest().setAttribute("status",
						thesisVOs.get(0).getStatus());
			} else {
				getRequest().setAttribute("status", "否");
			}
			List<ApplyJobVO> applyJobVO;
			applyJobVO = (List<ApplyJobVO>) dbOperation.queryDataByClass(
					ApplyJobVO.class, new String[] { "studentNum", "status" },
					new String[] { num, "已审核" });
			if (applyJobVO.size() != 0) {
				getRequest().setAttribute("companyName",
						applyJobVO.get(0).getCompanyName());
			} else {
				getRequest().setAttribute("companyName", "暂无");
			}
			return "person";
		} catch (DBSupportException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}
//按条件查询学生信息
	public String searchStudentinfo() throws DBSupportException,
			UnsupportedEncodingException {
		String searname = new String(getSearname().getBytes("ISO8859_1"),
				"utf-8").trim();
		String searchsort = getSearchsort().trim();
		if (!searchsort.equals(getSession().getAttribute("searchsort"))
				&& getSession().getAttribute("searchsort") != null) {
			getSession().removeAttribute("searname");
		}
		getSession().setAttribute("searchsort", searchsort);

		if (searchsort == null || searname.equals("")) {
			String count = dbOperation.queryPageCount("StudentinfoVO");
			int modNum = Integer.parseInt(count)%pageSize;
			int PageCount = Integer.parseInt(count)/pageSize;
			getRequest().setAttribute("Pagecount",modNum==0?PageCount:PageCount+1);
			queryByPage(pageSize, pageNow);
			return SUCCESS;
		} else if (searchsort.equals("studentNum")) {
			queryByData(StudentinfoVO.class, "studentNum", searname, pageSize,
					pageNow);
			return SUCCESS;
		} else if (searchsort.equals("teacherName")) {
			if (getSession().getAttribute("searname") != null) {
				searname = (String) getSession().getAttribute("searname");
			}
			String count = dbOperation.queryParamPageCount("StudentinfoVO","teacherName",searname);
			int modNum = Integer.parseInt(count)%pageSize;
			int PageCount = Integer.parseInt(count)/pageSize;
			getRequest().setAttribute("Pagecount",modNum==0?PageCount:PageCount+1);
			queryByData(StudentinfoVO.class, "teacherName", searname, pageSize,
					pageNow);
			getSession().setAttribute("searname", searname);
			return SUCCESS;
		} else if (searchsort.equals("studyTeacher")) {
			if (getSession().getAttribute("searname") != null) {
				searname = (String) getSession().getAttribute("searname");
			}
			String count = dbOperation.queryParamPageCount("StudentinfoVO","studyTeacher",searname);
			int modNum = Integer.parseInt(count)%pageSize;
			int PageCount = Integer.parseInt(count)/pageSize;
			getRequest().setAttribute("Pagecount",modNum==0?PageCount:PageCount+1);
			queryByData(StudentinfoVO.class, "studyTeacher", searname,
					pageSize, pageNow);
			getSession().setAttribute("searname", searname);
			return SUCCESS;
		} else if (searchsort.equals("supervisor")) {
			if (getSession().getAttribute("searname") != null) {
				searname = (String) getSession().getAttribute("searname");
			}
			String count = dbOperation.queryParamPageCount("StudentinfoVO","supervisor",searname);
			int modNum = Integer.parseInt(count)%pageSize;
			int PageCount = Integer.parseInt(count)/pageSize;
			getRequest().setAttribute("Pagecount",modNum==0?PageCount:PageCount+1);
			queryByData(StudentinfoVO.class, "studyTeacher", searname,
					pageSize, pageNow);
			queryByData(StudentinfoVO.class, "supervisor", searname, pageSize,
					pageNow);
			getSession().setAttribute("searname", searname);
			return SUCCESS;
		}
		return null;
	}
//查询学生信息
	public String searchStu() throws UnsupportedEncodingException,
			DBSupportException {
		getSession().removeAttribute("searname");
		searchStudentinfo();
		return SUCCESS;
	}
//按条件查询已申请实习的学生信息
	@SuppressWarnings("unchecked")
	public String seacherCmpWithStu() throws DBSupportException {
		try {
			if (searname.equals("") && searchsort != null) {
				applyJobVOs = (List<ApplyJobVO>) dbOperation.queryDataByClass(ApplyJobVO.class,new String[] { "companyNum", "status" },new String[] {(String) getSession().getAttribute("companyNum"),"已审核" });
				int l = applyJobVOs.size();
				String studentNum = "";
				for (int i = 0; i < l; i++) {
					if (i == 0)
						studentNum += "'";
					if (i == l - 1) {
						studentNum += applyJobVOs.get(i).getStudentNum() + "'";
					} else {
						studentNum += applyJobVOs.get(i).getStudentNum() + "','";
					}
				}
				if (studentNum!=""&&studentNum!=null) {
					String count = dbOperation.queryComStuPageCount("StudentinfoVO","studentNum",studentNum);
					int modNum = Integer.parseInt(count)%pageSize;
					int PageCount = Integer.parseInt(count)/pageSize;
					getRequest().setAttribute("Pagecount",modNum==0?PageCount:PageCount+1);
					Map map = new HashMap();
					String hql = " from StudentinfoVO where studentNum in ("+studentNum+")";
					studentinfoVOs = (List<StudentinfoVO>) dbOperation.queryByPage(hql, pageNow,
							pageSize, map);
				}
				return "student";
			}else if (searname != null && searchsort.equals("studentNum")) {
				applyJobVOs = (List<ApplyJobVO>) dbOperation.queryDataByClass(ApplyJobVO.class, new String[] { "studentNum","companyNum", "status" }, new String[] {getSearname(),(String) getSession().getAttribute("companyNum"),"已审核" });
						if (applyJobVOs.size()!=0) {
							studentinfoVOs = (List<StudentinfoVO>) dbOperation.queryDataByClass(StudentinfoVO.class, "studentNum", getSearname());
							return "student";
						}
			}else if (searname != null && searchsort.equals("studentName")) {
				String searname = new String(getSearname().getBytes("ISO8859_1"),
						"utf-8").trim();
				applyJobVOs = (List<ApplyJobVO>) dbOperation.queryDataByClass(ApplyJobVO.class, new String[] { "studentName","companyNum", "status" }, new String[] {searname,(String) getSession().getAttribute("companyNum"),"已审核" });
				if (applyJobVOs.size()!=0) {
					studentinfoVOs = (List<StudentinfoVO>) dbOperation.queryDataByClass(StudentinfoVO.class, "studentName", searname);
					return "student";
				}
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
//按条件查询学生信息
	@SuppressWarnings("unchecked")
	public String seacherTeaWithStu() throws DBSupportException {
		try {
			Map map = new HashMap();
			String searname = new String(getSearname().getBytes("ISO8859_1"),
					"utf-8").trim();
			String searchsort = getSearchsort().trim();
			String teaName = (String) getSession().getAttribute("username");
			map.put("teacherName", teaName);
			String hql = "";
			if (searname.equals("") && searchsort != null) {
				hql = "from StudentinfoVO where teacherName in (:teacherName)";
				String count = dbOperation.queryParamPageCount("StudentinfoVO","teacherName",teaName);
				int modNum = Integer.parseInt(count)%pageSize;
				int PageCount = Integer.parseInt(count)/pageSize;
				getRequest().setAttribute("Pagecount",modNum==0?PageCount:PageCount+1);
			} else if (searname != null && searchsort.equals("studentNum")) {
				map.put("studentNum", searname);
				hql = "from StudentinfoVO where teacherName in (:teacherName) and studentNum in (:studentNum)";
			} else if (searname != null && searchsort.equals("studentName")) {
				map.put("studentName", searname);
				hql = "from StudentinfoVO where teacherName in (:teacherName) and studentName in (:studentName)";
			}
			studentinfoVOs = (List<StudentinfoVO>) dbOperation.queryByPage(hql,
					pageNow, pageSize, map);
			return "result";
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;

	}

	public String searchPage() {
		queryByPage(pageSize, pageNow);
		return SUCCESS;
	}
//删除所选的学生信息
	public String delAllStudent() {
		List<Integer> ids = new ArrayList<Integer>();
		for (String id : getSid().split(",")) {
			ids.add(Integer.valueOf(id));
		}
		try {
			dbOperation.deteleDataByClass(StudentinfoVO.class, "id", ids);
			return SUCCESS;
		} catch (DBSupportException e) {
			e.printStackTrace();
		}
		return null;
	}
//删除学生信息
	public String delStudent() {
		try {
			dbOperation.deteleDataByClass(StudentinfoVO.class, "studentNum", String.valueOf(delId));
			return SUCCESS;
		} catch (DBSupportException e) {
			e.printStackTrace();
		}
		return null;
	}
	//更新学生信息
	public void updateStudentinfo() throws IOException {

		try {
			StudentinfoVO studentinfoVO1 = (StudentinfoVO) dbOperation
					.queryDataByClass(StudentinfoVO.class, "studentNum",
							studentinfoVO.getStudentNum()).get(0);
			studentinfoVO1.setStudentAddress(studentinfoVO.getStudentAddress());
			studentinfoVO1.setStudentMail(studentinfoVO.getStudentMail());
			studentinfoVO1.setStudentMajor(studentinfoVO.getStudentMajor());
			studentinfoVO1.setStudentName(studentinfoVO.getStudentName());
			studentinfoVO1.setStudentPhone(studentinfoVO.getStudentPhone());
			studentinfoVO1.setStudyTeacher(studentinfoVO.getStudyTeacher());
			studentinfoVO1.setSupervisor(studentinfoVO.getSupervisor());
			studentinfoVO1.setTeacherName(studentinfoVO.getTeacherName());
			dbOperation.updateSingleData(studentinfoVO1);
			getResponse().setContentType("text/html; charset=GB2312");
			PrintWriter out = getResponse().getWriter();
			out.print("<script language=javascript>alert('修改成功');self.location=document.referrer;</script>");
			out.close();
		} catch (DBSupportException e) {
			e.printStackTrace();
		}
	}
//学生信息excel导入到数据库
	public String ExcelToDb() throws IOException {
		String inputPath = "upload/student";
		String realPath = ServletActionContext.getServletContext().getRealPath(inputPath);
		File file = new File(realPath);
		if (!file.exists()) {
			file.mkdir();
		}
		if (excelStu!=null) {
			FileUtils.copyFile(excelStu, new File(new File(realPath),excelStuFileName));
		}
		List<StudentinfoVO> listExcel = getAllByExcel(realPath+"/"+new File(new File(realPath),excelStuFileName).getName());
		try {
			for (StudentinfoVO stu : listExcel) {
				dbOperation.saveOrUpdateSingleData(stu);
			}
			getResponse().setContentType("text/html; charset=GBK");
			PrintWriter out = getResponse().getWriter();
			out.print("<script language=javascript>alert('成功导入数据！');window.location.href='addStudent.jsp';</script>");
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
//获取学生excel表的所有信息
	public static List<StudentinfoVO> getAllByExcel(String file) {
		List<StudentinfoVO> list = new ArrayList<StudentinfoVO>();
		try {
			Workbook rwb = Workbook.getWorkbook(new File(file));
			Sheet rs = rwb.getSheet("Sheet1");// 或者rwb.getSheet(0)
			int clos = rs.getColumns();// 得到所有的列
			int rows = rs.getRows();// 得到所有的行

			// System.out.println(clos + " rows:" + rows);
			for (int i = 1; i < rows; i++) {
				for (int j = 0; j < clos; j++) {
					// 第一个是列数，第二个是行数
					String id = rs.getCell(j++, i).getContents();// 默认最左边编号也算一列
																	// 所以这里得j++
					String studentNum = rs.getCell(j++, i).getContents();
					String studentName = rs.getCell(j++, i).getContents();
					String studentPass = rs.getCell(j++, i).getContents();
					String sex = rs.getCell(j++, i).getContents();
					String studentBirthday = rs.getCell(j++, i).getContents();
					String studentMail = rs.getCell(j++, i).getContents()
							+ "@scse.com.cn";
					String studentAddress = rs.getCell(j++, i).getContents();
					String studentPhone = rs.getCell(j++, i).getContents();
					String studentMajor = rs.getCell(j++, i).getContents();
					String teacherName = rs.getCell(j++, i).getContents();
					String graduationDate = rs.getCell(j++, i).getContents();
					String studyTeacher = rs.getCell(j++, i).getContents();
					String supervisor = rs.getCell(j++, i).getContents();
					list.add(new StudentinfoVO(Integer.parseInt(id),
							studentNum, studentName, studentPass, sex,
							studentBirthday, studentMail, studentAddress,
							studentPhone, studentMajor, teacherName,
							graduationDate, studyTeacher, supervisor));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}
//保存学生信息
	public String saveStudent() throws DBSupportException, IOException {
		int i = (int) dbOperation.saveSingleData(studentinfoVO);
		if (i == 0) {
			getResponse().setContentType("text/html; charset=GBK");
			PrintWriter out = getResponse().getWriter();
			out.print("<script language=javascript>alert('添加成功！');window.location.href='addStudent.jsp';</script>");
			out.close();
		}
		return null;
	}

//学生页面分页处理	
	public List<StudentinfoVO> queryByPage(int pageSize, int pageNow) {

		if (pageSize > 0 && pageNow > 0) {
			studentinfoVOs = dbOperation.setPageAll(pageNow, pageSize);
			return studentinfoVOs;
		}
		return null;
	}
//调用分页方法
	@SuppressWarnings("unchecked")
	public List<StudentinfoVO> queryByData(final Class<?> clazz, String param,
			Object value, int pageSize, int pageNow) {
		if (pageSize > 0 && pageNow > 0) {
			studentinfoVOs = dbOperation.setPageData(clazz, param, value,
					pageNow, pageSize);
			return studentinfoVOs;
		}
		return null;

	}
	//分数页面分页处理
	@SuppressWarnings("unchecked")
	public List<ScoreVO> queryByDataScore(final Class<?> clazz, String param,
			Object value, int pageSize, int pageNow) {
		if (pageSize > 0 && pageNow > 0) {
			scoreVOs = dbOperation.setPageData(clazz, param, value,
					pageNow, pageSize);
			return scoreVOs;
		}
		return null;

	}

	public StudentinfoVO getStudentinfoVO() {
		return studentinfoVO;
	}

	public void setStudentinfoVO(StudentinfoVO studentinfoVO) {
		this.studentinfoVO = studentinfoVO;
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

	public List<StudentinfoVO> getStudentinfoVOs() {
		return studentinfoVOs;
	}

	public void setStudentinfoVOs(List<StudentinfoVO> studentinfoVOs) {
		this.studentinfoVOs = studentinfoVOs;
	}

	public int getDelId() {
		return delId;
	}

	public void setDelId(int delId) {
		this.delId = delId;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
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

	public List<ThesisVO> getThesisVOs() {
		return thesisVOs;
	}

	public void setThesisVOs(List<ThesisVO> thesisVOs) {
		this.thesisVOs = thesisVOs;
	}

	public List<ApplyJobVO> getApplyJobVOs() {
		return applyJobVOs;
	}

	public void setApplyJobVOs(List<ApplyJobVO> applyJobVOs) {
		this.applyJobVOs = applyJobVOs;
	}
	public List<ScoreVO> getScoreVOs() {
		return scoreVOs;
	}
	public void setScoreVOs(List<ScoreVO> scoreVOs) {
		this.scoreVOs = scoreVOs;
	}
	public String getDownloadFilePath() {
		return downloadFilePath;
	}
	public void setDownloadFilePath(String downloadFilePath) {
		this.downloadFilePath = downloadFilePath;
	}
	public File getExcelStu() {
		return excelStu;
	}
	public void setExcelStu(File excelStu) {
		this.excelStu = excelStu;
	}
	public String getExcelStuFileName() {
		return excelStuFileName;
	}
	public void setExcelStuFileName(String excelStuFileName) {
		this.excelStuFileName = excelStuFileName;
	}

}
