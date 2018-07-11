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
@Table(name="aweb_test")
public class TestVO {
	@Id
	@Column(name="id",unique=true)
	private int id;

	@Column(name="username")
	private String username;
	
	@Column(name="nickname")
	private String nickname;
	
	@Column(name="mailbox")
	private String mailbox;
	
	@Column(name="telephone")
	private String telephone;
	
	@Column(name="address")
	private String address;
	
	@Column(name="remark")
	private String remark;
	
	public TestVO() {
		super();
	}

	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append(username != null ? username : "");
		s.append(nickname != null ? nickname : "");
		s.append(mailbox != null ? mailbox : "");
		s.append(telephone != null ? telephone : "");
		s.append(address != null ? address : "");
		s.append(remark != null ? remark : "");
		return s.toString();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMailbox() {
		return mailbox;
	}

	public void setMailbox(String mailbox) {
		this.mailbox = mailbox;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	

}
