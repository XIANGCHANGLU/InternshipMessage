package struts2.action;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.ApplyJobVO;
import exception.DBSupportException;
import struts2.action.support.StandardActionSupport;

public class ApplyAction extends StandardActionSupport {
	private List<ApplyJobVO> applyJobVOs;
	private int pageNow = 1; // 初始化为1,默认从第一页开始显示
	private int pageSize = 5; // 每页显示5条记录
	private String searchsort;
	private ApplyJobVO applyJobVO;
	private int id;
	private String studentNum;
//显示学生申请状况
	@SuppressWarnings("unchecked")
	public String ShowStudent() {
		try {
			String teaName= (String) getSession().getAttribute("username");
			String count = dbOperation.queryParamPageCount("ApplyJobVO", new String[]{"teacherName","status","makingScore"}, new String[]{teaName,"已审核","未评分"});
			int modNum = Integer.parseInt(count)%pageSize;
			int PageCount = Integer.parseInt(count)/pageSize;
			getRequest().setAttribute("Pagecount",modNum==0?PageCount:PageCount+1);
			Map map = new HashMap<>();
			map.put("teacherName",teaName );
			map.put("status", "已审核");
			map.put("makingScore", "未评分");
			String hql = "from ApplyJobVO where teacherName in (:teacherName) and status in (:status) and makingScore in (:makingScore)";
			applyJobVOs=(List<ApplyJobVO>) dbOperation.queryByPage(hql, pageNow, pageSize, map);
		} catch (DBSupportException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return "marking";
	}
//审批学生申请
	public String approvalApply() {
		try {
			applyJobVO = (ApplyJobVO) dbOperation.queryDataByClass(
					ApplyJobVO.class, "id", getId()).get(0);
			applyJobVO.setStatus("已审核");
			dbOperation.saveOrUpdateSingleData(applyJobVO);
			dbOperation.deteleDataByClass(ApplyJobVO.class, new String[] {
					"studentNum", "status" }, new String[] { getStudentNum(),
					"审核中" });
		} catch (DBSupportException e) {
			e.printStackTrace();
		}
		return SUCCESS;

	}
//查询申请信息
	@SuppressWarnings("unchecked")
	public String queryApplyList() throws UnsupportedEncodingException,
			DBSupportException {
		String comName = (String) getSession().getAttribute("companyNum");
		Map map = new HashMap();
		map.put("companyNum", comName);
		String hql = "";
		if (getSearchsort() == null) {
			searchsort = "";
		} else {
			searchsort = new String(getSearchsort().getBytes("ISO8859_1"),
					"utf-8").trim();
		}
		getSession().setAttribute("searchsort", searchsort);
		if (searchsort.equals("审核中")) {
			String count = dbOperation.queryParamPageCount("ApplyJobVO", new String[]{"companyNum","status"}, new String[]{comName,"审核中"});
			int modNum = Integer.parseInt(count)%pageSize;
			int PageCount = Integer.parseInt(count)/pageSize;
			getRequest().setAttribute("Pagecount",modNum==0?PageCount:PageCount+1);	
			map.put("status", "审核中");
			hql = "from ApplyJobVO where companyNum in (:companyNum) and status in (:status)";
		} else if (searchsort.equals("已审核")) {
			String count = dbOperation.queryParamPageCount("ApplyJobVO", new String[]{"companyNum","status"}, new String[]{comName,"已审核"});
			int modNum = Integer.parseInt(count)%pageSize;
			int PageCount = Integer.parseInt(count)/pageSize;
			getRequest().setAttribute("Pagecount",modNum==0?PageCount:PageCount+1);	
			map.put("status", "已审核");
			hql = "from ApplyJobVO where companyNum in (:companyNum) and status in (:status)";
		} else {
			String count = dbOperation.queryParamPageCount("ApplyJobVO", new String[]{"companyNum"}, new String[]{comName});
			int modNum = Integer.parseInt(count)%pageSize;
			int PageCount = Integer.parseInt(count)/pageSize;
			getRequest().setAttribute("Pagecount",modNum==0?PageCount:PageCount+1);	
			hql = "from ApplyJobVO where companyNum in (:companyNum) order by status asc";
		}
		applyJobVOs = (List<ApplyJobVO>) dbOperation.queryByPage(hql, pageNow,
				pageSize, map);
		return SUCCESS;
	}

	public List<ApplyJobVO> getApplyJobVOs() {
		return applyJobVOs;
	}

	public void setApplyJobVOs(List<ApplyJobVO> applyJobVOs) {
		this.applyJobVOs = applyJobVOs;
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

	public ApplyJobVO getApplyJobVO() {
		return applyJobVO;
	}

	public void setApplyJobVO(ApplyJobVO applyJobVO) {
		this.applyJobVO = applyJobVO;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStudentNum() {
		return studentNum;
	}

	public void setStudentNum(String studentNum) {
		this.studentNum = studentNum;
	}

}
