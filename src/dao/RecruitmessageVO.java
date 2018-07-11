package dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zhanguohai zhanguohai@agree.com.cn
 * @date 2016年8月19日 下午2:59:42
 * @version V1.0
 */
@Entity
@Table(name = "recruitmessage")
public class RecruitmessageVO {
	
	@Column(name="companyNum")
	private String companyNum;
	
	@Id
	@Column(name="jobNum",unique=true)
	private String jobNum;
	
	@Column(name="jobName")
	private String jobName;

	@Column(name="jobRequire")
	private String jobRequire;
	
	@Column(name="jobIntroduce")
	private String jobIntroduce;
	
	@Column(name="applyNum")
	private int applyNum;
	
	@Column(name="sumNum")
	private int sumNum;
	
	@Column(name="startDate")
	private String startDate;
	
	@Column(name="deadline")
	private String deadline;

	public RecruitmessageVO() {
		super();
		// TODO 自动生成的构造函数存根
	}

	public String getCompanyNum() {
		return companyNum;
	}

	public void setCompanyNum(String companyNum) {
		this.companyNum = companyNum;
	}

	public String getJobNum() {
		return jobNum;
	}

	public void setJobNum(String jobNum) {
		this.jobNum = jobNum;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobRequire() {
		return jobRequire;
	}

	public void setJobRequire(String jobRequire) {
		this.jobRequire = jobRequire;
	}

	public String getJobIntroduce() {
		return jobIntroduce;
	}

	public void setJobIntroduce(String jobIntroduce) {
		this.jobIntroduce = jobIntroduce;
	}

	public int getApplyNum() {
		return applyNum;
	}

	public void setApplyNum(int applyNum) {
		this.applyNum = applyNum;
	}

	public int getSumNum() {
		return sumNum;
	}

	public void setSumNum(int sumNum) {
		this.sumNum = sumNum;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

}
