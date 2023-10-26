package com.github.admin.common.request;

import com.github.admin.common.group.UserGroup;
import com.github.admin.common.group.UserPasswordGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UserRequest extends BaseRequest{

    @NotNull(message = "用户id不能为空",groups = {UserGroup.UpdateGroup.Id.class, UserPasswordGroup.class})
    private Long id;

//    @NotEmpty(message = "用户id不能为空",groups = {UpdateGroup.class, InsertGroup.class})
    @NotBlank(message = "用户名不能为空",groups = {UserGroup.UpdateGroup.UserName.class, UserGroup.AddGroup.UserName.class})
    private String userName;
    @NotBlank(message="用户昵称不能为空",groups = {UserGroup.UpdateGroup.class, UserGroup.AddGroup.NickName.class})
    private String nickName;
    @NotBlank(message="密码不能为空",groups = {UserGroup.UpdateGroup.class, UserGroup.AddGroup.Password.class})
    private String password;
    @NotBlank(message="确认密码不能为空",groups = {UserGroup.UpdateGroup.class, UserGroup.AddGroup.Confirm.class})
    private String confirm;
    @NotBlank(message="电话号码不能为空",groups = {UserGroup.UpdateGroup.class, UserGroup.AddGroup.Phone.class})
    private String phone;
    @NotBlank(message="邮箱不能为空",groups = {UserGroup.UpdateGroup.class, UserGroup.AddGroup.Email.class})
    private String email;
    private Integer sex;
    private Integer status;
    private String remark;
    private List<Long> roleIds;

}
