package com.github.admin.api.resolver;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

//@Service
public class MyLocaleResolver implements LocaleResolver {
	private static final Logger LOGGER = LoggerFactory.getLogger(MyLocaleResolver.class);

	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		String lan = request.getParameter("lan");
		LOGGER.info("获取请求lan:{}",lan);
        if (StringUtils.isEmpty(lan)) {
        	lan = "zh_CN";
        }
        String[] ii = lan.split("_");
        return new Locale(ii[0], ii[1]);
	}

	@Override
	public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

	}

}
