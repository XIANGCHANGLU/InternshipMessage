package dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "applyJob")
public class ApplyJobVO {
	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "studentNum")
	private String studentNum;

	@Column(name = "studentName")
	private String studentName;

	@Column(name = "teacherName")
	private String teacherName;
	
	@Column(name = "studentTel")
	private String studentTel;
	
	@Column(name = "companyNum")
	private String companyNum;

	@Column(name = "companyName")
	private String companyName;

	@Column(name = "data")
	private String data;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "evaStatus")
	private String evaStatus;
	
	@Column(name = "makingScore")
	private String makingScore;
	
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

	public String getCompanyNum() {
		return companyNum;
	}

	public void setCompanyNum(String companyNum) {
		this.companyNum = companyNum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getEvaStatus() {
		return evaStatus;
	}

	public void setEvaStatus(String evaStatus) {
		this.evaStatus = evaStatus;
	}

	public String getMakingScore() {
		return makingScore;
	}

	public void setMakingScore(String makingScore) {
		this.makingScore = makingScore;
	}

	public String getStudentTel() {
		return studentTel;
	}

	public void setStudentTel(String studentTel) {
		this.studentTel = studentTel;
	}
	
}
