package dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zhanguohai zhanguohai@agree.com.cn
 * @date 2016年8月19日 2:59:42
 * @version V1.0
 */
@Entity
@Table(name = "studentinfo")
public class StudentinfoVO {
	@Id
	@Column(name = "id", unique = true)
	private int id;

	@Column(name = "studentNum")
	private String studentNum;
	@Column(name = "studentName")
	private String studentName;

	@Column(name = "studentPass")
	private String studentPass;

	@Column(name = "sex")
	private String sex;

	@Column(name = "studentBirthday")
	private String studentBirthday;

	@Column(name = "studentMail")
	private String studentMail;

	@Column(name = "studentAddress")
	private String studentAddress;

	@Column(name = "studentPhone")
	private String studentPhone;

	@Column(name = "studentMajor")
	private String studentMajor;

	@Column(name = "teacherName")
	private String teacherName;

	@Column(name = "graduationDate")
	private String graduationDate;

	@Column(name = "studyTeacher")
	private String studyTeacher;

	@Column(name = "supervisor")
	private String supervisor;

	public StudentinfoVO() {
		super();
	}

	public StudentinfoVO(int id ,String studentNum, String studentName,
			String studentPass, String sex, String studentBirthday,
			String studentMail, String studentAddress, String studentPhone,
			String studentMajor, String teacherName, String graduationDate,
			String studyTeacher, String supervisor) {
		super();
		this.id=id;
		this.studentNum = studentNum;
		this.studentName = studentName;
		this.studentPass = studentPass;
		this.sex = sex;
		this.studentBirthday = studentBirthday;
		this.studentMail = studentMail;
		this.studentAddress = studentAddress;
		this.studentPhone = studentPhone;
		this.studentMajor = studentMajor;
		this.teacherName = teacherName;
		this.graduationDate = graduationDate;
		this.studyTeacher = studyTeacher;
		this.supervisor = supervisor;
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

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentPass() {
		return studentPass;
	}

	public void setStudentPass(String studentPass) {
		this.studentPass = studentPass;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getStudentBirthday() {
		return studentBirthday;
	}

	public void setStudentBirthday(String studentBirthday) {
		this.studentBirthday = studentBirthday;
	}

	public String getStudentMail() {
		return studentMail;
	}

	public void setStudentMail(String studentMail) {
		this.studentMail = studentMail;
	}

	public String getStudentAddress() {
		return studentAddress;
	}

	public void setStudentAddress(String studentAddress) {
		this.studentAddress = studentAddress;
	}

	public String getStudentPhone() {
		return studentPhone;
	}

	public void setStudentPhone(String studentPhone) {
		this.studentPhone = studentPhone;
	}

	public String getStudentMajor() {
		return studentMajor;
	}

	public void setStudentMajor(String studentMajor) {
		this.studentMajor = studentMajor;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getGraduationDate() {
		return graduationDate;
	}

	public void setGraduationDate(String graduationDate) {
		this.graduationDate = graduationDate;
	}

	public String getStudyTeacher() {
		return studyTeacher;
	}

	public void setStudyTeacher(String studyTeacher) {
		this.studyTeacher = studyTeacher;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}
	
}
