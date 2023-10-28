package com.github.admin.common.request;

import com.github.admin.common.group.InsertGroup;
import com.github.admin.common.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MenuRequest extends BaseRequest{

    @NotNull(message="状态不能为空",groups = {InsertGroup.class, UpdateGroup.class})
    private Integer status;
    @NotBlank(message="标题不能为空",groups = {InsertGroup.class, UpdateGroup.class})
    private String title;
    @NotBlank(message="地址不能为空",groups = {InsertGroup.class, UpdateGroup.class})
    private String url;
    @NotBlank(message="权限标识不能为空",groups = {InsertGroup.class, UpdateGroup.class})
    private String perms;
    @NotNull(message="pid不能为空",groups = {InsertGroup.class, UpdateGroup.class})
    private Long pid;
    @NotNull(message="菜单不能为空",groups = {InsertGroup.class, UpdateGroup.class})
    private Integer type;
    @NotNull(message="排序不能为空",groups = {InsertGroup.class, UpdateGroup.class})
    private Integer sort;
    @NotNull(message="id不能为空",groups = {UpdateGroup.class})
    private Long id;
    private String icon;
    private String remark;
    private Long createBy;
    private Long updateBy;

}
