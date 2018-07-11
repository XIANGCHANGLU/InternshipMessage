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
@Table(name = "admin")
public class AdminVO {
	@Id
	@Column(name = "adminID", unique = true)
	private int adminID;

	@Column(name = "adminName")
	private String adminName;

	@Column(name = "adminNickname")
	private String adminNickname;

	@Column(name = "adminPassword")
	private String adminPassword;

	public AdminVO() {
		super();
	}

	public int getAdminID() {
		return adminID;
	}

	public void setAdminID(int adminID) {
		this.adminID = adminID;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminNickname() {
		return adminNickname;
	}

	public void setAdminNickname(String adminNickname) {
		this.adminNickname = adminNickname;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

}
