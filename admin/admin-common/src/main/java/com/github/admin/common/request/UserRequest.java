package com.github.admin.common.request;

import com.github.admin.common.group.InsertGroup;
import com.github.admin.common.group.UpdateGroup;
import com.github.admin.common.group.UserPasswordGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UserRequest extends BaseRequest{

    @NotNull(message = "用户id不能为空",groups = {UpdateGroup.class, UserPasswordGroup.class})
    private Long id;

    @NotEmpty(message = "用户id不能为空",groups = {UpdateGroup.class, InsertGroup.class})
    @NotBlank(message="用户名称不能为空")
    private String userName;
    @NotBlank(message="用户昵称不能为空",groups = {UpdateGroup.class, InsertGroup.class})
    private String nickName;
    @NotBlank(message="密码不能为空",groups = {InsertGroup.class,UserPasswordGroup.class})
    private String password;
    @NotBlank(message="确认密码不能为空",groups = {InsertGroup.class,UserPasswordGroup.class})
    private String confirm;
    private String phone;
    private String email;
    private Integer sex;
    private Integer status;
    private String remark;
    private List<Long> roleIds;

}
