package dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zhanguohai zhanguohai@agree.com.cn
 * @date 2016��8��19�� ����2:59:42
 * @version V1.0
 */
@Entity
@Table(name = "teacherinfo")
public class TeacherinfoVO {
	@Id
	@Column(name = "teacherNum", unique = true)
	private String teacherNum;

	@Column(name = "teacherName")
	private String teacherName;

	@Column(name = "teacherPass")
	private String teacherPass;

	@Column(name = "position")
	private String position;

	@Column(name = "teacherDept")
	private String teacherDept;

	@Column(name = "teacherPhone")
	private String teacherPhone;

	@Column(name = "teacherMail")
	private String teacherMail;

	public TeacherinfoVO() {
		super();
	}

	public String getTeacherNum() {
		return teacherNum;
	}

	public void setTeacherNum(String teacherNum) {
		this.teacherNum = teacherNum;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getTeacherPass() {
		return teacherPass;
	}

	public void setTeacherPass(String teacherPass) {
		this.teacherPass = teacherPass;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getTeacherDept() {
		return teacherDept;
	}

	public void setTeacherDept(String teacherDept) {
		this.teacherDept = teacherDept;
	}

	public String getTeacherPhone() {
		return teacherPhone;
	}

	public void setTeacherPhone(String teacherPhone) {
		this.teacherPhone = teacherPhone;
	}

	public String getTeacherMail() {
		return teacherMail;
	}

	public void setTeacherMail(String teacherMail) {
		this.teacherMail = teacherMail;
	}

	public TeacherinfoVO(String teacherNum, String teacherName,
			String teacherPass, String position, String teacherDept,
			String teacherPhone, String teacherMail) {
		super();
		this.teacherNum = teacherNum;
		this.teacherName = teacherName;
		this.teacherPass = teacherPass;
		this.position = position;
		this.teacherDept = teacherDept;
		this.teacherPhone = teacherPhone;
		this.teacherMail = teacherMail;
	}

}
