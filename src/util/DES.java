package util;

import java.io.UnsupportedEncodingException;

/**
 * ���������
 * 
 * @ClassName: DES
 */
public class DES {

	private static final byte[] enkeystore = { 0x08, 0x02, 0x0b, 0x0c, 0x01,
			0x0a, 0x00, 0x0d, 0x07, 0x03, 0x0e, 0x05, 0x0f, 0x06, 0x04, 0x09 };

	private static final byte[] dekeystore = { 0x06, 0x04, 0x01, 0x09, 0x0e,
			0x0b, 0x0d, 0x08, 0x00, 0x0f, 0x05, 0x02, 0x03, 0x07, 0x0a, 0x0c };

	/**
	 * �ֽ��������
	 * 
	 * @Title: encode
	 * @param @param data
	 * @param @return ����
	 * @return byte[] ��������
	 * @throws
	 */
	public static byte[] encode(byte[] data) {
		byte[] result = new byte[data.length];

		for (int i = 0; i < data.length; i++) {
			result[i] += (enkeystore[(data[i] >>> 4) & 0x0F] << 4);
			result[i] += (enkeystore[data[i] & 0x0F]);
		}

		return result;
	}

	/**
	 * �ֽ��������
	 * 
	 * @Title: decode
	 * @param @param data
	 * @param @return ����
	 * @return byte[] ��������
	 * @throws
	 */
	public static byte[] decode(byte[] data) {
		byte[] result = new byte[data.length];

		for (int i = 0; i < data.length; i++) {
			result[i] += (dekeystore[(data[i] >>> 4) & 0x0F] << 4);
			result[i] += (dekeystore[data[i] & 0x0F]);
		}

		return result;
	}

	/**
	 * ����String��������,String�������
	 * 
	 * @Title��getEncString
	 * @param strMing
	 *            String�����ַ�������������
	 * @return String ������������
	 */
	public static String getEncString(String strMing) {
		byte[] byteenc = null;
		try {
			byteenc = DES.encode(strMing.getBytes("GB2312"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return byte2hex(byteenc);

	}

	/**
	 * ���� ��String��������,String�������
	 * 
	 * @Title��getDesString
	 * @param strMi
	 *            String���͵�����
	 * @return String ��������
	 */
	public static String getDesString(String strMi) {

		byte[] bytedeenc = null;
		try {
			bytedeenc = hex2byte(strMi.getBytes("GB2312"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteMing = DES.decode(bytedeenc);

		return new String(byteMing);

	}

	/**
	 * 
	 * @Title: byte2hex
	 * @param @param b
	 * @param @return ����
	 * @return String ��������
	 * @throws
	 */
	public static String byte2hex(byte[] b) { // һ���ֽڵ�����
	// ת��16�����ַ���
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			// ����ת��ʮ�����Ʊ�ʾ
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}

		return hs.toUpperCase(); // ת�ɴ�д
	}

	/**
	 * 
	 * @Title: hex2byte
	 * @param @param b
	 * @param @return ����
	 * @return byte[] ��������
	 * @throws
	 */
	public static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("���Ȳ���ż��");
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			// ��λһ�飬��ʾһ���ֽ�,��������ʾ��16�����ַ�������ԭ��һ�������ֽ�
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}

		return b2;
	}

	public static void main(String[] args) throws Exception {
		String strEnc = DES.getEncString("Aim");// �����ַ���,����String������
		System.out.println(strEnc);

		String strDes = DES.getDesString("120306120306");// ��String ���͵����Ľ���
		System.out.println(strDes);
	}

}
