package util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class CommonUtils {

	public static String getNowTime() {
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return sDateFormat.format(new Date());
	}

	public static String getUnfmtTime() {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return sDateFormat.format(new Date());
	}
	public static String getUnfmtData() {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd");
		return sDateFormat.format(new Date());
	}

	public static String formatTime(String format, long mills) {
		return new SimpleDateFormat(format).format(new Date(mills));
	}

    public static String formatTime(long mills){
        return formatTime("yyyy-MM-dd HH:mm:ss", mills);
    }

	/**
	 * @Description: 库表唯一ID
	 * @author Athrun tang.pm@cfischina.com
	 * @date 2015年9月11日 上午10:38:08 
	 * @version V1.0
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}
	
	/**
	 * @Description: 获取时间间隔
	 * @author zhanguohai 
	 * @date 2016年8月21日 上午11:54:20 
	 * @version V1.0
	 * @return  单位：秒 
	 */
	public static int jugeTimeSpacing(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long seconds = 0;
		try {
			java.util.Date date = myFormatter.parse(sj1);
			java.util.Date mydate = myFormatter.parse(sj2);

			seconds = (date.getTime() - mydate.getTime()) / 1000;

			// 这里精确到了秒，我们可以在做差的时候将时间精确到天
		} catch (Exception e) {
			return -1;
		}
		return (int)seconds;
	}

}
