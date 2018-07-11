package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


import org.apache.struts2.ServletActionContext;

import struts2.action.support.StandardActionSupport;

public class BatchDownloadAction extends StandardActionSupport {

	private void zip(String souceFileName, String destFileName) {
		File file = new File(souceFileName);
		try {
			zip(file, destFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void zip(File souceFile, String destFileName) throws IOException {
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(destFileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ZipOutputStream out = new ZipOutputStream(fileOut);
		zip(souceFile, out, "");
		out.close();
	}

	private void zip(File souceFile, ZipOutputStream out, String base)
			throws IOException {

		if (souceFile.isDirectory()) {
			File[] files = souceFile.listFiles();
			out.putNextEntry(new ZipEntry(base + "/"));
			base = base.length() == 0 ? "" : base + "/";
			for (File file : files) {
				zip(file, out, base + file.getName());
			}
		} else {
			if (base.length() > 0) {
				out.putNextEntry(new ZipEntry(base));
			} else {
				out.putNextEntry(new ZipEntry(souceFile.getName()));
			}
			System.out.println("filepath=" + souceFile.getPath());
			FileInputStream in = new FileInputStream(souceFile);

			int b;
			byte[] by = new byte[1024];
			while ((b = in.read(by)) != -1) {
				out.write(by, 0, b);
			}
			in.close();
		}
	}

	public InputStream getDownloadFile() {
		ZipData();
		return ServletActionContext.getServletContext().getResourceAsStream(
				"/files/" +CommonUtils.getUnfmtData()+ "Data1.zip");
	}

	public void ZipData() {
		String root = ServletActionContext.getServletContext().getRealPath(
				"files/" + (String) getSession().getAttribute("username"));
		zip(root,"D:/eclipse/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp2/wtpwebapps/InternshipMessage/files/"+CommonUtils.getUnfmtData()+"Data1.zip");
	}

	public String execute() throws Exception {
		return SUCCESS;
	}
}