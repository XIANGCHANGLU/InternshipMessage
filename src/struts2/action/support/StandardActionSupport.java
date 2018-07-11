package struts2.action.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;

import exception.AWebException;
import exception.ExceptionTypes;
import dao.support.StandardDbSupport;

import com.opensymphony.xwork2.ActionSupport;

public class StandardActionSupport extends ActionSupport {

	private static final long serialVersionUID = 2386522933505206017L;

	/**
	 * Hibernate Support
	 * 
	 * spring 
	 */
	protected StandardDbSupport dbOperation;

	/**
	 * HttpServletRequest
	 * 
	 * @return
	 */
	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * ��ȡHttpServletResponse
	 * 
	 * @return
	 */
	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * ��ȡ���λỰHttpSession
	 * 
	 * @return
	 */
	protected HttpSession getSession() {
		return getRequest().getSession(false);
	}

	/**
	 * 
	 * 
	 * @param code
	 * @param cause
	 * @throws Throwable
	 */
	protected void handleException(Object code, Throwable cause)
			throws AWebException {
		if (code instanceof ExceptionTypes.AWEB) {
			ExceptionTypes.AWEB afaCode = (ExceptionTypes.AWEB) code;
			throw new AWebException(afaCode.getErrorCode(),
					afaCode.getErrorMsg(), cause);
		} else {
			throw new AWebException(ExceptionTypes.AWEB.AWEB99.getErrorCode(),
					ExceptionTypes.AWEB.AWEB99.getErrorMsg(), cause);
		}

	}

	/**
	 * 
	 * 
	 * @param code
	 * @param cause
	 * @throws Throwable
	 */
	protected void handleException(Throwable cause) throws Throwable {
		handleException(null, cause);
	}

	/**
	 * ־
	 * 
	 * @param log
	 * @param debug
	 */
	protected void logDebug(Logger log, String debug) {
		if (log.isDebugEnabled())
			log.debug(debug);
	}

	public StandardDbSupport getDbOperation() {
		return dbOperation;
	}

	public void setDbOperation(StandardDbSupport dbOperation) {
		this.dbOperation = dbOperation;
	}

}
