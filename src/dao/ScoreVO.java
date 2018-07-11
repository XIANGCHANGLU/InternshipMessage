package dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zhanguohai 
 * @date 2016年8月24日  2:59:42
 * @version V1.0
 */
@Entity
@Table(name = "score")
public class ScoreVO {
	@Id
	@Column(name = "id", unique = true)
	private int id;

	@Column(name = "studentNum")
	private String studentNum;
	
	@Column(name = "studentName")
	private String studentName;
	
	@Column(name = "companyName")
	private String companyName;
	
	@Column(name = "teacherName")
	private String teacherName;
	
	@Column(name = "score")
	private double score;

	public ScoreVO() {
		super();
		// TODO 自动生成的构造函数存根
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

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

}
