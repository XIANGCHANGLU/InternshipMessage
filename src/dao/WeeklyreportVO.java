package dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zhanguohai
 * @date 2016年8月19日 2:59:42
 * @version V1.0
 */
@Entity
@Table(name = "weeklyreport")
public class WeeklyreportVO {

	@Id
	@Column(name = "reportID")
	private int reportID;

	@Column(name = "studentNum")
	private String studentNum;

	@Column(name = "studentName")
	private String studentName;

	@Column(name = "teacherName")
	private String teacherName;

	@Column(name = "reportContent")
	private String reportContent;

	@Column(name = "submitData")
	private String submitData;

	@Column(name = "reportStatus")
	private String reportStatus;

	public WeeklyreportVO() {
		super();
	}

	public int getReportID() {
		return reportID;
	}

	public void setReportID(int reportID) {
		this.reportID = reportID;
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

	public String getReportContent() {
		return reportContent;
	}

	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
	}

	public String getSubmitData() {
		return submitData;
	}

	public void setSubmitData(String submitData) {
		this.submitData = submitData;
	}

	public String getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}

}
