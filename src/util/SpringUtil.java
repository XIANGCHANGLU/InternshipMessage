package util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;

/**
 * æ™®é?ç±»è°ƒç”¨Bean
 * @author chenrui  chenrui@cfischina.com
 * 2015å¹?1æœ?7æ—?
 *
 */
public class SpringUtil extends ApplicationObjectSupport {
	private static ApplicationContext applicationContext = null;
	@Override
	protected void initApplicationContext(ApplicationContext context)
			throws BeansException {
		super.initApplicationContext(context);
		if(SpringUtil.applicationContext == null){
			SpringUtil.applicationContext = context;
		}
	}
	public static ApplicationContext getAppContext() {
		return applicationContext;
	}
	public static Object getBean(String name){
		return getAppContext().getBean(name);
	}

}
