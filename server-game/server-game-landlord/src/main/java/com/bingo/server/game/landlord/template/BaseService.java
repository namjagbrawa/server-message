package com.bingo.server.game.landlord.template;

import com.bingo.server.game.landlord.annotation.PTAnnotation;
import com.bingo.server.game.landlord.annotation.PTStringAnnotation;
import com.bingo.server.game.landlord.navigation.Navigation;
import com.bingo.server.game.landlord.util.base.PackageUtil;
import com.bingo.server.game.landlord.util.base.StringUtils;
import com.bingo.server.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class BaseService implements BaseServiceInterface {

	/**
	 * 获得这个类的第一个接口
	 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass().getInterfaces()[0].getSimpleName());

	@Override
	public void init() {

	}

	@Override
	public void initService() {

	}

	@Override
	public void initNavigation() {
		String packageName = PackageUtil.getParent(getClass().getPackage().getName()) + ".action";
		List<Class<?>> classes = PackageUtil.getClasses(packageName);
		try {
			Field field = Navigation.class.getDeclaredField("navigate");
			field.setAccessible(true);
			for (Class<?> clazz : classes) {
				String key = this.getKeyName(clazz);
				if (key != null) {
					try {
						@SuppressWarnings("unchecked")
						Map<String, IActionSupport> navigate = (Map<String, IActionSupport>) field.get(null);
						String beanName = StringUtils.firstStrToLowerCase(clazz.getSimpleName());
						IActionSupport action = SpringUtils.getBean(beanName);
						navigate.put(key, action);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
			field.setAccessible(false);
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获得action的key
	 * 
	 * @param clazz
	 * @return
	 * @author wcy 2017年2月13日
	 */
	private String getKeyName(Class<?> clazz) {
		String key = null;
		PTAnnotation pt = clazz.getAnnotation(PTAnnotation.class);
		PTStringAnnotation ptString = clazz.getAnnotation(PTStringAnnotation.class);
		if (pt != null) {
			key = pt.value().getSimpleName();
		} else if (ptString != null) {
			key = ptString.value();
		}
		return key;
	}

}
