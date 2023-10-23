package com.github.admin.common.domain;

import com.github.framework.core.entity.BaseEntity;
import lombok.Data;

@Data
public class UserEntity extends BaseEntity {

    private String userName;
    private String nickName;
    private String password;
    private String salt;
    private String picture;
    private Integer sex;
    private String email;
    private String phone;
    private String remark;
    private Integer status;

}
