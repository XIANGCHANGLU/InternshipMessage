package util;

import java.io.File;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import struts2.action.support.StandardActionSupport;

import dao.ThesisVO;

public class UploadAction extends StandardActionSupport implements Serializable {

	private File image;// 对应的就是表单中文件上传的那个输入域的名称，Struts2框架会封装成File类型的
	private String imageFileName;// 上传输入域FileName 文件名
	private String imageContentType;// 上传文件的MIME类型
	HttpSession session = getRequest().getSession(true);

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String getImageContentType() {
		return imageContentType;
	}

	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}

	public String execute() {
		try {
			// 处理实际的上传代码
			// 找到存储文件的真实路径
			ServletContext sc = ServletActionContext.getServletContext();
			String storePath = sc.getRealPath("files/"
					+ (String) session.getAttribute("teacher"));
			File file =new File(storePath);
			if (!file.exists()) {
				file.mkdir();
			}
			FileUtils.copyFile(image, new File(storePath, imageFileName));
			ThesisVO thesisVO = new ThesisVO();
			thesisVO.setStudentNum((String) session.getAttribute("studentNum"));
			thesisVO.setStudentName((String) session.getAttribute("username"));
			thesisVO.setTeacherName((String) session.getAttribute("teacher"));
			thesisVO.setStatus("是");
			dbOperation.saveOrUpdateSingleData(thesisVO);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print("<html><head><meta charset='UTF-8'></head>");
			out.print("<script language=javascript>alert('上传成功,等待老师批阅');window.location.href='uploadThesis.jsp';</script>");
			out.close();
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
}