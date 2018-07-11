package struts2.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.WeeklyreportVO;
import exception.DBSupportException;
import struts2.action.support.StandardActionSupport;
import util.CommonUtils;

@SuppressWarnings("serial")
public class JournalAction extends StandardActionSupport {
	private WeeklyreportVO weeklyreportVO;
	private List<WeeklyreportVO> weeklyreportVOs;
	private String reportContent;
	private String searchsort;
	private String searname;
	private int reportID;
	private int pageNow = 1;
	private int pageSize = 5;
	//提交周报信息
	public String SaveJournal() {
		CommonUtils c = new CommonUtils();
		String teacher = (String) getSession().getAttribute("teacher");
		String studentNum = (String) getSession().getAttribute("studentNum");
		String studentName = (String) getSession().getAttribute("username");
		weeklyreportVO.setTeacherName(teacher);
		weeklyreportVO.setStudentNum(studentNum);
		weeklyreportVO.setStudentName(studentName);
		weeklyreportVO.setSubmitData(c.getNowTime());
		weeklyreportVO.setReportStatus("待审核");
		try {
			dbOperation.saveSingleData(weeklyreportVO);
			return SUCCESS;
		} catch (DBSupportException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}
	//周报信息保存为草稿
	public void SaveDraft() {
		try {
			WeeklyreportVO weeklyreportVO = new WeeklyreportVO();
			String teacher = (String) getSession().getAttribute("teacher");
			String studentNum = (String) getSession()
					.getAttribute("studentNum");
			String studentName = (String) getSession().getAttribute("username");
			weeklyreportVO.setTeacherName(teacher);
			weeklyreportVO.setStudentNum(studentNum);
			weeklyreportVO.setStudentName(studentName);
			weeklyreportVO.setSubmitData("");
			weeklyreportVO.setReportStatus("未提交");
			weeklyreportVO.setReportContent(reportContent);
			dbOperation.saveSingleData(weeklyreportVO);
		} catch (DBSupportException e) {
			e.printStackTrace();
		}
	}
	//提交周报草稿
	public void SubmitDraft() throws DBSupportException, IOException {
		CommonUtils c = new CommonUtils();
		WeeklyreportVO weeklyreportVO = new WeeklyreportVO();
		String teacher = (String) getSession().getAttribute("teacher");
		String studentNum = (String) getSession().getAttribute("studentNum");
		String studentName = (String) getSession().getAttribute("username");
		weeklyreportVO.setReportID(getReportID());
		weeklyreportVO.setTeacherName(teacher);
		weeklyreportVO.setStudentNum(studentNum);
		weeklyreportVO.setStudentName(studentName);
		weeklyreportVO.setSubmitData(c.getNowTime());
		weeklyreportVO.setReportStatus("待审核");
		weeklyreportVO.setReportContent(reportContent);
		dbOperation.updateSingleData(weeklyreportVO);

	}
//审批周报信息
	public void approvalDraft() {
		try {
			weeklyreportVO = (WeeklyreportVO) dbOperation.queryDataByClass(
					WeeklyreportVO.class, "reportID", getReportID()).get(0);
			weeklyreportVO.setReportStatus("已审核");
			dbOperation.updateSingleData(weeklyreportVO);
		} catch (DBSupportException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
//草稿保存为草稿不提交
	public void updateDraft() throws DBSupportException {
		WeeklyreportVO weeklyreportVO = new WeeklyreportVO();
		String teacher = (String) getSession().getAttribute("teacher");
		String studentNum = (String) getSession().getAttribute("studentNum");
		String studentName = (String) getSession().getAttribute("username");
		weeklyreportVO.setReportID(getReportID());
		weeklyreportVO.setTeacherName(teacher);
		weeklyreportVO.setStudentNum(studentNum);
		weeklyreportVO.setStudentName(studentName);
		weeklyreportVO.setSubmitData("");
		weeklyreportVO.setReportStatus("未提交");
		weeklyreportVO.setReportContent(reportContent);
		dbOperation.updateSingleData(weeklyreportVO);
	}
//显示周报信息
	@SuppressWarnings("unchecked")
	public String showJournal() throws IOException {
		try {String studenNum = (String) getSession().getAttribute("studentNum");
			String count = dbOperation.queryParamPageCount("WeeklyreportVO","studentNum",studenNum);
			int modNum = Integer.parseInt(count)%pageSize;
			int PageCount = Integer.parseInt(count)/pageSize;
			getRequest().setAttribute("Pagecount",modNum==0?PageCount:PageCount+1);
			Map map = new HashMap();
			map.put("studentNum", studenNum);
			weeklyreportVOs = (List<WeeklyreportVO>) dbOperation.queryByPage("from WeeklyreportVO where studentNum in (:studentNum) order by reportStatus desc", pageNow, pageSize, map);
			int let = weeklyreportVOs.size();
			String[] content1 = new String[let];
			String[] content2 = new String[let];
			for (int i = 0; i < let; i++) {
				content1[i] = ((weeklyreportVOs.get(i).getReportContent()
						.split(">")[2]).split("<")[0]);
				weeklyreportVOs.get(i).setTeacherName(content1[i]);
				content2[i] = weeklyreportVOs.get(i).getReportContent().trim()
						.replaceAll("\\<.*?>", "");
				weeklyreportVOs.get(i).setReportContent(content2[i]);
			}
			return "show";
		} catch (DBSupportException e) {
			e.printStackTrace();
		}
		return null;
	}
//教师查看自己学生的周报信息
	public String TeashowJournal() throws UnsupportedEncodingException {
		String searname = new String(getSearname().getBytes("ISO8859_1"),
				"utf-8").trim();
		String searchsort = getSearchsort().trim();
		if (!searchsort.equals(getSession().getAttribute("searchsort"))
				&& getSession().getAttribute("searchsort") != null) {
			getSession().removeAttribute("searname");
		}
		getSession().setAttribute("searchsort", searchsort);
		if (searname.equals("") && searchsort != null) {
			String teaName = (String) getSession().getAttribute("username");
			String count = dbOperation.queryPageCount("WeeklyreportVO where teacherName ='"+teaName+"' and reportStatus != '未提交'");
			int modNum = Integer.parseInt(count)%pageSize;
			int PageCount = Integer.parseInt(count)/pageSize;
			getRequest().setAttribute("Pagecount",modNum==0?PageCount:PageCount+1);			
			weeklyreportVOs = queryByData(WeeklyreportVO.class, new String[] {
					"teacherName", "reportStatus" }, new String[] {teaName, "未提交" },
					pageSize, pageNow);
		} else if (searname != null && searchsort.equals("studentNum")) {
			String count = dbOperation.queryPageCount("WeeklyreportVO where studentNum ='"+searname+"' and reportStatus != '未提交'");
			int modNum = Integer.parseInt(count)%pageSize;
			int PageCount = Integer.parseInt(count)/pageSize;
			getRequest().setAttribute("Pagecount",modNum==0?PageCount:PageCount+1);	
			weeklyreportVOs = queryByData(WeeklyreportVO.class, new String[] {
					"studentNum", "reportStatus" }, new String[] { searname,
					"未提交" }, pageSize, pageNow);

		} else if (searname != null && searchsort.equals("studentName")) {
			String count = dbOperation.queryPageCount("WeeklyreportVO where studentName ='"+searname+"' and reportStatus != '未提交'");
			int modNum = Integer.parseInt(count)%pageSize;
			int PageCount = Integer.parseInt(count)/pageSize;
			getRequest().setAttribute("Pagecount",modNum==0?PageCount:PageCount+1);	
			weeklyreportVOs = queryByData(WeeklyreportVO.class, new String[] {
					"studentName", "reportStatus" }, new String[] { searname,
					"未提交" }, pageSize, pageNow);
		}
		int let = weeklyreportVOs.size();
		String[] content1 = new String[let];
		String[] content2 = new String[let];
		for (int i = 0; i < let; i++) {
			content1[i] = ((weeklyreportVOs.get(i).getReportContent()
					.split(">")[2]).split("<")[0]);
			weeklyreportVOs.get(i).setTeacherName(content1[i]);
			content2[i] = weeklyreportVOs.get(i).getReportContent().trim()
					.replaceAll("\\<.*?>", "");
			weeklyreportVOs.get(i).setReportContent(content2[i]);
		}
		return "approval";
	}
//周报页面分页处理
	@SuppressWarnings("unchecked")
	public List<WeeklyreportVO> queryByData(final Class<?> clazz,
			String[] param, Object[] value, int pageSize, int pageNow) {
		if (pageSize > 0 && pageNow > 0) {
			weeklyreportVOs = dbOperation.setPageData(clazz, param, value,
					pageNow, pageSize);
			return weeklyreportVOs;
		}
		return null;

	}
//审核周报
	@SuppressWarnings("unchecked")
	public String approvalJournal() {
		reportID = getReportID();
		try {
			weeklyreportVOs = (List<WeeklyreportVO>) dbOperation
					.queryDataByClass(WeeklyreportVO.class, "reportID",
							reportID);
			return "edit";
		} catch (DBSupportException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public String ToDraft() {
		reportID = getReportID();
		try {
			weeklyreportVOs = (List<WeeklyreportVO>) dbOperation
					.queryDataByClass(WeeklyreportVO.class, "reportID",
							reportID);
			return "update";
		} catch (DBSupportException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}

	public WeeklyreportVO getWeeklyreportVO() {
		return weeklyreportVO;
	}

	public void setWeeklyreportVO(WeeklyreportVO weeklyreportVO) {
		this.weeklyreportVO = weeklyreportVO;
	}

	public List<WeeklyreportVO> getWeeklyreportVOs() {
		return weeklyreportVOs;
	}

	public void setWeeklyreportVOs(List<WeeklyreportVO> weeklyreportVOs) {
		this.weeklyreportVOs = weeklyreportVOs;
	}

	public String getReportContent() {
		return reportContent;
	}

	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
	}

	public int getReportID() {
		return reportID;
	}

	public void setReportID(int reportID) {
		this.reportID = reportID;
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

}
