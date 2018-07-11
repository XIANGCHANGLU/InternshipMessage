package dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zhanguohai
 * @date 2016年8月24日 2:59:42
 * @version V1.0
 */
@Entity
@Table(name = "thesis")
public class ThesisVO {
	@Id
	@Column(name = "studentNum")
	private String studentNum;

	@Column(name = "studentName")
	private String studentName;

	@Column(name = "teacherName")
	private String teacherName;

	@Column(name = "status")
	private String status;

	public ThesisVO() {
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
