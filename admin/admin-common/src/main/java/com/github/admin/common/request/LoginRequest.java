package com.github.admin.common.request;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Schema(title = "登入请求对象")
public class LoginRequest extends BaseRequest {


    @NotBlank(message = "账户不能为空")
    @Schema(title = "登录账户")
    @Parameter(description = "登录账户")
    private String userName;

    @NotBlank(message = "密码不能为空")
    @Parameter(description = "登录密码")
    @Schema(title = "登录密码")
    private String password;
    @Schema(title= "验证码")
    @Parameter(description = "验证码")
    private String captcha;
    @Parameter(description = "记住我")
    @Schema(title = "记住我")
    private String rememberMe;

}
