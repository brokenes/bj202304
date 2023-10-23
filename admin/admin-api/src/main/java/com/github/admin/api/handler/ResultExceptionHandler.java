package com.github.admin.api.handler;

import com.github.admin.common.enums.AdminErrorMsgEnum;
import com.github.framework.core.Result;
import com.github.framework.core.spring.SpringContextUtils;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Objects;

@ControllerAdvice
@Slf4j
public class ResultExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResultExceptionHandler.class);

    /** 拦截表单验证异常 */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Result bindException(BindException ex){
        BindingResult bindingResult = ex.getBindingResult();
        LOGGER.error("bindException表单验证异常信息:",ex);
        return Result.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleMethodArgumentNotValidException(HttpServletRequest request, HandlerMethod handlerMethod,MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder sb = new StringBuilder("参数校验失败:");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getField()).append("：").append(fieldError.getDefaultMessage()).append(", ");
        }
        LOGGER.error("handleMethodArgumentNotValidException方法参数异常信息:",ex);
        return Result.fail(AdminErrorMsgEnum.REQUEST_METHOD_NOT_ALLOW);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleConstraintViolationException(HttpServletRequest request, HandlerMethod handlerMethod,ConstraintViolationException ex) {
        LOGGER.error("handleConstraintViolationException表单验证异常信息:",ex);
        return Result.fail(AdminErrorMsgEnum.REQUEST_PARAMS_ERROR);
    }


    /**
     * 拦截访问权限异常
     */
    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    public Result authorizationException(AuthorizationException e, HttpServletRequest request,
                                         HttpServletResponse response){
        // 获取异常信息
        Throwable cause = e.getCause();
        String message = cause.getMessage();
        Class<Result> resultVoClass = Result.class;

        // 判断无权限访问的方法返回对象是否为ResultVo
        if(!message.contains(resultVoClass.getName())){
            try {
                // 重定向到无权限页面
                String contextPath = request.getContextPath();
                ShiroFilterFactoryBean shiroFilter = SpringContextUtils.getBean(ShiroFilterFactoryBean.class);
                response.sendRedirect(contextPath + shiroFilter.getUnauthorizedUrl());
            } catch (IOException e1) {
                return Result.fail(AdminErrorMsgEnum.USER_NO_PERMISSIONS);
            }
        }
        return Result.fail(AdminErrorMsgEnum.USER_NO_PERMISSIONS);
    }


    /** 拦截未知的运行时异常 */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Result runtimeException(HttpServletRequest request, HandlerMethod handlerMethod,RuntimeException ex) {
        LOGGER.error("runtimeException运行异常信息:",ex);
        return Result.fail(AdminErrorMsgEnum.SYSTEM_EXCEPTION);
    }

    /** 拦截未知的运行时异常 */
    @ExceptionHandler(FeignException.class)
    @ResponseBody
    public Result feignException(HttpServletRequest request, HandlerMethod handlerMethod,FeignException ex) {
        String msg = ex.getMessage();
        String code = String.valueOf(ex.status());
        LOGGER.error("feignException远程接口调用异常信息:",ex);
        return Result.fail(code, msg);
    }

    /**
     * 默认全局异常处理。
     * @param ex the ex
     * @return ResultData
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Result exception(HttpServletRequest request, HandlerMethod handlerMethod,Exception ex) {
        LOGGER.error("exception全局异常信息:",ex);
        return Result.fail(AdminErrorMsgEnum.SYSTEM_EXCEPTION);
    }


    public static boolean isAjax(HandlerMethod handlerMethod) {
        ResponseBody responseBody = handlerMethod.getMethodAnnotation(ResponseBody.class);
        if (null != responseBody) {
            return true;
        }
        // 获取类上面的Annotation，可能包含组合注解，故采用spring的工具类
        Class<?> beanType = handlerMethod.getBeanType();
        responseBody = AnnotationUtils.getAnnotation(beanType, ResponseBody.class);
        if (null != responseBody) {
            return true;
        }
        return false;
    }

}
