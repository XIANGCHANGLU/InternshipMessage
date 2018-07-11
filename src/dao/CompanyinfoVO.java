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
@Table(name = "companyinfo")
public class CompanyinfoVO {
	@Id
	@Column(name = "companyNum")
	private String companyNum;

	@Column(name = "companyName")
	private String companyName;

	@Column(name = "companyPass")
	private String companyPass;

	@Column(name = "companyType")
	private String companyType;

	@Column(name = "companyIndustry")
	private String companyIndustry;

	@Column(name = "companyAddress")
	private String companyAddress;

	@Column(name = "companyPhone")
	private String companyPhone;

	@Column(name = "companyContacts")
	private String companyContacts;

	@Column(name = "companyDescription")
	private String companyDescription;

	public CompanyinfoVO() {
		super();
	}
	
	public CompanyinfoVO(String companyNum, String companyName,
			String companyPass, String companyAddress, String companyIndustry,
			String companyType, String companyContacts, String companyPhone,
			String companyDescription) {
		super();
		this.companyNum = companyNum;
		this.companyName = companyName;
		this.companyPass = companyPass;
		this.companyType = companyType;
		this.companyIndustry = companyIndustry;
		this.companyAddress = companyAddress;
		this.companyPhone = companyPhone;
		this.companyContacts = companyContacts;
		this.companyDescription = companyDescription;
	}

	public String getCompanyNum() {
		return companyNum;
	}

	public void setCompanyNum(String companyNum) {
		this.companyNum = companyNum;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyPass() {
		return companyPass;
	}

	public void setCompanyPass(String companyPass) {
		this.companyPass = companyPass;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getCompanyIndustry() {
		return companyIndustry;
	}

	public void setCompanyIndustry(String companyIndustry) {
		this.companyIndustry = companyIndustry;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	public String getCompanyContacts() {
		return companyContacts;
	}

	public void setCompanyContacts(String companyContacts) {
		this.companyContacts = companyContacts;
	}

	public String getCompanyDescription() {
		return companyDescription;
	}

	public void setCompanyDescription(String companyDescription) {
		this.companyDescription = companyDescription;
	}

}
