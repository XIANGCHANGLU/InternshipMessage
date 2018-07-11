package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 文件操作工具�? * 
 * @author lihao lihao01@cfischina.com Apr 24, 2015
 */
public class XMLUtil {

	/**
	 * ClassLoader获取资源�?	 * 
	 * @param cls
	 * @param registryPath
	 * @return
	 */
	public static InputStream getResourceAsStream(Class<?> cls, String registryPath) {
		if (cls == null) {
			return null;
		}
		return cls.getClassLoader().getResourceAsStream(registryPath);
	}
	
	/**
	 * 根据文件路径获取XML根节�?	 * 
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 */
	public static Element getRootElement(String filePath)
			throws FileNotFoundException, DocumentException {
		SAXReader saxReader = new SAXReader();

		File file = new File(filePath);
		if (!file.exists()) {
			throw new FileNotFoundException(filePath);
		}

		Document doc = saxReader.read(new File(filePath));
		return doc.getRootElement();
	}
	
	/**
	 * 根据文件流获取XML根节�?	 * 
	 * @param in
	 * @return
	 * @throws DocumentException
	 */
	public static Element getRootElement(InputStream in) throws DocumentException {
		SAXReader saxReader = new SAXReader();
		Document doc = saxReader.read(in);
		return doc.getRootElement();
	}
	
}
