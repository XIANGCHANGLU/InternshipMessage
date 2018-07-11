package dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zhanguohai
 *
 */
@Entity
@Table(name = "evaluationStu")
public class EvaluationStuVO {
	@Id
	@Column(name = "studentNum",unique=true)
	private String studentNum;
	
	@Column(name = "studentName")
	private String studentName;
	
	@Column(name = "teacherName")
	private String teacherName;
	
	@Column(name = "companyName")
	private String companyName;
	
	@Column(name = "evaStatus")
	private String evaStatus;
	
	@Column(name = "evaContent")
	private String evaContent;
	
	public EvaluationStuVO() {
		super();
		// TODO 自动生成的构造函数存根
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
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getEvaStatus() {
		return evaStatus;
	}
	public void setEvaStatus(String evaStatus) {
		this.evaStatus = evaStatus;
	}
	public String getEvaContent() {
		return evaContent;
	}
	public void setEvaContent(String evaContent) {
		this.evaContent = evaContent;
	}
	
}
