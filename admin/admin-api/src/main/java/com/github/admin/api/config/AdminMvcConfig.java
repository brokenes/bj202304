package com.github.admin.api.config;

import com.github.admin.api.handler.AdminLoggerHandler;
import com.github.admin.api.resolver.MyLocaleResolver;
import com.github.framework.core.spring.SpringContextUtils;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@Configuration
public class AdminMvcConfig implements WebMvcConfigurer {


    @Resource
    private AdminLoggerHandler adminLoggerHandler;

    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleResolver();
    }

    @Bean
    public SpringContextUtils springContextUtils(){
        return new SpringContextUtils();
    }

//    @Bean
//    public LocalValidatorFactoryBean getValidatorFactory() {
//        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
//        localValidatorFactoryBean.getValidationPropertyMap().put("hibernate.validator.fail_fast", "true");
//        return localValidatorFactoryBean;
//    }

    @Bean
    public Validator validator(){
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class )
                .configure()
                .failFast(true)
                //.addProperty( "hibernate.validator.fail_fast", "true" )
                .buildValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        return validator;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminLoggerHandler)
                .excludePathPatterns("/login")
                .excludePathPatterns("/captcha")
                .excludePathPatterns("/css/**")
                .excludePathPatterns("/images/**")
                .excludePathPatterns("/lib/**")
                .excludePathPatterns("/favicon.ico")
                .excludePathPatterns("/**/system/actionLog/**")
                .excludePathPatterns("/js/**");
    }


}
