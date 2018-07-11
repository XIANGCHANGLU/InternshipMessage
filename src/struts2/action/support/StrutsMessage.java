package struts2.action.support;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;
import exception.ExceptionTypes;

/**
 * 
 *
 * @author lihao lihao01@cfischina.com
 * Aug 3, 2015
 */
public class StrutsMessage implements Serializable {
	
	private static final long serialVersionUID = -3831797881796577143L;

	private boolean status;
	
	private String errorMsg;
	
	private List<String> errorMsgList;
	
	private JSONObject content;
	
	@SuppressWarnings("unused")
	private StrutsMessage() {
	}
	
	public StrutsMessage(boolean status) {
		this.status = status;
	}
	
	public StrutsMessage(boolean status, String errorMsg) {
		this.status = status;
		this.errorMsg = errorMsg;
	}
	
	public boolean isStatus() {
		return status;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public JSONObject getContent() {
		return content;
	}
	
	public void setContent(JSONObject content) {
		this.content = content;
	}
	
	public List<String> getErrorMsgList() {
		return errorMsgList;
	}
	
	/**
	 * 添加返回参数
	 * @param key
	 * @param value
	 */
	public StrutsMessage addParameter(String key, Object value) {
		if (this.content == null)
			this.content = new JSONObject();

		this.content.put(key, value);
		
		return this;
	}
	
	/**
	 * 增加错误信息
	 * @param errorMsg
	 * @return
	 */
	public StrutsMessage addErrorMsg(String errorMsg) {
		if (errorMsgList == null) 
			errorMsgList = new ArrayList<String>();
		if (status) 
			status = false;
		
		errorMsgList.add(errorMsg);
		
		return this;
	}
	
	/**
	 * 获取成功消息
	 * @return
	 */
	public static StrutsMessage successMessage() {
		return successMessage(null);
	}
	
	/**
	 * 获取成功消息
	 * @param content
	 * @return
	 */
	public static StrutsMessage successMessage(JSONObject content) {
		StrutsMessage message = new StrutsMessage(true);
		message.setContent(content == null ? new JSONObject() : content);
		return message;
	}
	
	/**
	 * 获取失败消息
	 * @param errorMsg
	 * @return
	 */
	public static StrutsMessage errorMessage(String errorMsg) {
		return new StrutsMessage(false, errorMsg);
	}
	

	/**
	 * 获取失败消息
	 * @param aweb
	 * @param e
	 * @return
	 */
	public static StrutsMessage errorMessage(ExceptionTypes.AWEB aweb, Throwable e) {
		return new StrutsMessage(false, aweb.getErrorMsg());
	}
	
	/**
	 * 获取string
	 * @return
	 */
	public String jsonString() {
		return JSONObject.fromObject(this).toString();
	}
	
}
